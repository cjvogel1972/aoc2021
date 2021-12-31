package com.github.cjvogel1972.day20;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.github.cjvogel1972.util.Utilities.binaryToInt;
import static com.github.cjvogel1972.util.Utilities.readFileLines;

public class Day20 {

    private static List<Integer> imageEnhancementAlgorithm;

    public static void main(String[] args) throws IOException {
        var data = readFileLines("input-files/day20.txt");
        imageEnhancementAlgorithm = parseLine(data.get(0));
        var pixels = data.subList(2, data.size())
                .stream()
                .map(Day20::parseLine)
                .toList();

        var image = new Image(pixels, 0);

        enhanceImage(image, 2);
        enhanceImage(image, 50);
    }

    private static List<Integer> parseLine(String line) {
        return line.chars()
                .mapToObj(c -> c == '.' ? 0 : 1)
                .toList();
    }

    private static void enhanceImage(final Image image, final int iterations) {
        var currentImage = image;

        for (var i = 0; i < iterations; i++) {
            currentImage = enhanceImageOnce(currentImage);
        }

        var litPixels = currentImage.countLitPixels();

        System.out.println("There are " + litPixels + " pixels lit");
    }

    private static Image enhanceImageOnce(final Image image) {
        var newPixels = new ArrayList<List<Integer>>();

        for (var y = -1; y < image.getHeight() + 1; y++) {
            var line = new ArrayList<Integer>();
            for (var x = -1; x < image.getWidth() + 1; x++) {
                int newPixelIndex = binaryToInt(getPixelBinary(image, x, y));
                line.add(imageEnhancementAlgorithm.get(newPixelIndex));
            }
            newPixels.add(line);
        }

        var newOutsidePixel = image.getOutsidePixel() == 0 ? imageEnhancementAlgorithm.get(
                0) : imageEnhancementAlgorithm.get(imageEnhancementAlgorithm.size() - 1);

        return new Image(newPixels, newOutsidePixel);
    }

    private static String getPixelBinary(final Image image, int x, int y) {
        var sb = new StringBuilder();

        for (var i = y - 1; i <= y + 1; i++) {
            for (var j = x - 1; j <= x + 1; j++) {
                sb.append(image.getPixel(j, i));
            }
        }

        return sb.toString();
    }
}
