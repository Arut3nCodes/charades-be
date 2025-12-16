package com.example.koornikbe.dto;
import java.util.List;
public class PixelColorData {
    private String color;       // e.g., "#383838"
    private List<int[]> pixels; // list of [x, y] coordinates

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public List<int[]> getPixels() { return pixels; }
    public void setPixels(List<int[]> pixels) { this.pixels = pixels; }
}


