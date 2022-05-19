package io.extact.sample.person.persistence.file;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.extact.sample.core.io.FileUtilts;
import io.extact.sample.core.io.PathResolver;
import io.extact.sample.core.io.ext.CsvPersister;
import io.extact.sample.person.entity.Person;
import io.extact.sample.person.persistence.PersonRepository;

@ApplicationScoped
public class FilePersonRepository implements PersonRepository {

    private static final Logger LOG = LoggerFactory.getLogger(FilePersonRepository.class);
    private Path filePath;

    @PostConstruct
    public void init() {
        var pathResolver = new PathResolver.TempDirPathResolver();
        filePath =  FileUtilts.copyResourceToRealPath("person.csv", pathResolver);
        LOG.debug("initialized.");
    }

    @Override
    public Person get(long id) {
        return CsvPersister.load(filePath).stream()
                .filter(columns -> Long.parseLong(columns[0]) == id)
                .map(this::csvToEntity)
                .findFirst()
                .orElse(null);
    }
    @Override
    public List<Person> findAll() {
        return CsvPersister.load(filePath).stream()
                .map(this::csvToEntity)
                .collect(Collectors.toList());
    }
    @Override
    public Person add(Person person) {
        var csvLine = entityToCsv(person);
        csvLine[0] = String.valueOf(getNextSequence());
        CsvPersister.save(filePath, csvLine);
        return csvToEntity(csvLine);
    }

    private Person csvToEntity(String[] colmuns) {
        return new Person(Long.parseLong(colmuns[0]), colmuns[1], Integer.parseInt(colmuns[2]));
    }
    private String[] entityToCsv(Person person) {
        var columns = new String[3];
        columns[0] = String.valueOf(person.getId());
        columns[1] = person.getName();
        columns[2] = String.valueOf(person.getAge());
        return columns;
    }
    private long getNextSequence() {
        return CsvPersister.load(filePath).stream()
                .map(items -> Long.parseLong(items[0]))
                .collect(Collectors.maxBy(Long::compareTo))
                .orElse(0L)
                + 1L;
    }
}
