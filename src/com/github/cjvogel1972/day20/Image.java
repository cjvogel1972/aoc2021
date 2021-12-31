package com.github.cjvogel1972.day20;

import java.util.List;

public class Image {
    private final List<List<Integer>> pixels;
    private final int height;
    private final int width;
    private final int outsidePixel;

    public Image(List<List<Integer>> pixels, int outsidePixel) {
        this.pixels = pixels;
        height = pixels.size();
        width = pixels.get(0)
                .size();
        this.outsidePixel = outsidePixel;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getOutsidePixel() {
        return outsidePixel;
    }

    public int getPixel(int x, int y) {
        int result = outsidePixel;

        if (x >= 0 && x < width && y >= 0 && y < height) {
            result = pixels.get(y)
                    .get(x);
        }

        return result;
    }

    public int countLitPixels() {
        return pixels.stream()
                .mapToInt(line -> line.stream()
                        .mapToInt(i -> i)
                        .sum())
                .sum();
    }
}
