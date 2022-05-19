package io.extact.sample.core.io.ext;

import static java.nio.file.StandardOpenOption.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

// 特定のライブラリに個別に依存しているものは除外
public class CsvPersister {
    public static List<String[]> load(Path filePath) {
        try (CSVParser parser = CSVParser.parse(filePath, StandardCharsets.UTF_8, CSVFormat.RFC4180);) {
            return parser.getRecords().stream()
                    .map(record -> StreamSupport.stream(record.spliterator(), false).collect(Collectors.toList()))
                    .map(values -> {
                        var array = new String[values.size()];
                        values.toArray(array);
                        return array;
                    })
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void save(Path filePath, String[] addData) {
        List<String> singleLine = new ArrayList<>();
        singleLine.add(CSVFormat.RFC4180.format((Object[]) addData));
        try {
            Files.write(filePath, singleLine, StandardCharsets.UTF_8, WRITE, APPEND);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveAll(Path filePath, List<String[]> allData) {
        Stream<CharSequence> allLines = allData.stream()
                .map(items -> CSVFormat.RFC4180.format((Object[]) items)); // Memory-friendly and lazy stringification
        try {
            Files.write(filePath, allLines::iterator, StandardCharsets.UTF_8, CREATE, TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
