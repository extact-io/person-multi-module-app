package io.extact.sample.core.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public interface PathResolver {

    Path resolve(String file);
    Path getBaseDir();

    public static class FixedDirPathResolver implements PathResolver {
        private Path baseDir;
        public FixedDirPathResolver() {
            this.baseDir = Paths.get("./data");
        }
        @Override
        public Path resolve(String file) {
            return baseDir.resolve(file);
        }
        @Override
        public Path getBaseDir() {
            return this.baseDir;
        }
    }

    public static class TempDirPathResolver implements PathResolver {
        private Path tempDir;
        public TempDirPathResolver() {
            try {
                this.tempDir = Files.createTempDirectory("person_");
                this.tempDir.toFile().deleteOnExit();
            } catch (IOException e) {
               throw new RuntimeException(e);
            }
        }
        @Override
        public Path resolve(String file) {
            Path tempFile = tempDir.resolve(file);
            tempFile.toFile().deleteOnExit();
            return tempFile;
        }
        @Override
        public Path getBaseDir() {
            return this.tempDir;
        }
    }
}
