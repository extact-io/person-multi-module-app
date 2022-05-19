package io.extact.sample.core.io;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtilts {
    public static Path copyResourceToRealPath(String resourcePath, PathResolver resolver) {
        String[] resourcePathNodes = resourcePath.split("/");
        String outputFileName = resourcePathNodes[resourcePathNodes.length - 1];
        return copyResourceToRealPath(resourcePath, resolver, outputFileName);
    }
    public static Path copyResourceToRealPath(String resourcePath, PathResolver resolver, String outputFileName) {
        try (InputStream in = FileUtilts.class.getResourceAsStream("/" + resourcePath)) {
            if (!Files.exists(resolver.getBaseDir())) {
                Files.createDirectory(resolver.getBaseDir());
            }
            var outputFilePath = resolver.resolve(outputFileName);
            Files.copy(in, outputFilePath);
            return outputFilePath;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
