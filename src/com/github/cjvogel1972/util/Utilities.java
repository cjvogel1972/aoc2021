package com.github.cjvogel1972.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Function;

public class Utilities {

    public static <T> List<T> parseFile(String fileName, Function<String, T> mapper) throws IOException {
        var path = Paths.get(fileName);

        var lines = Files.lines(path);
        var depths = lines
                .map(mapper::apply)
                .toList();
        lines.close();

        return depths;
    }

    public static List<String> readFileLines(String fileName) throws IOException {
        var path = Paths.get(fileName);

        var lines = Files.lines(path);
        var result = lines.toList();
        lines.close();

        return result;
    }

    public static int charToInt(char aChar) {
        return Integer.parseInt(String.valueOf(aChar));
    }

    public static int binaryToInt(String binaryValue) {
        return Integer.parseInt(binaryValue, 2);
    }
}
