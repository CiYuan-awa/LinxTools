package net.ciyuan.linxtools.Utils;

import net.md_5.bungee.api.ChatColor;

import java.awt.*;

public class ColorUtil
{
    public static String Gradient(String text, String... colors) {
    StringBuilder output = new StringBuilder();
    int colorIndex = 0;
    int colorCount = colors.length;

    for (int i = 0; i < text.length(); i++) {
        char c = text.charAt(i);
        if (c == '&') {
            ChatColor color = ChatColor.getByChar(text.charAt(i + 1));
            if (color != null) {
                output.append(color);
                i++;
            } else {
                output.append(c);
            }
        } else {
            if (i + 6 <= text.length() && text.charAt(i) == '#') {
                String hex = text.substring(i + 1, i + 7);
                try {
                    int rgb = Integer.parseInt(hex, 16);
                    output.append(ChatColor.of(new Color(rgb)));
                } catch (NumberFormatException e) {
                    output.append(c);
                }
                i += 6;
            } else {
                output.append(ChatColor.of(new Color(getRGB(i, text, colors[colorIndex]))));
                colorIndex = (colorIndex + 1) % colorCount;
            }
            output.append(c);
        }
    }
    return output.toString();
}

    private static int getRGB(int i, String text, String color) {
        double frequency = 0.15;
        double r = Math.sin(frequency * i + 0) * 127 + 128;
        double g = Math.sin(frequency * i + 2) * 127 + 128;
        double b = Math.sin(frequency * i + 4) * 127 + 128;
        return ((int) r << 16) + ((int) g << 8) + (int) b;
    }/*
    public static String Gradient(String text, String... colors) {
        StringBuilder sb = new StringBuilder();
        int length = text.length();
        int numColors = colors.length;
        for (int i = 0; i < length; i++) {
            double ratio = (double) i / (length - 1);
            int colorIndex = (int) (ratio * (numColors - 1));
            double localRatio = (ratio * (numColors - 1)) - colorIndex;

            String startColor = colors[colorIndex];
            String endColor = colors[Math.min(colorIndex + 1, numColors - 1)];

            int red = (int) (Integer.parseInt(startColor.substring(1, 3), 16) * (1 - localRatio) + Integer.parseInt(endColor.substring(1, 3), 16) * localRatio);
            int green = (int) (Integer.parseInt(startColor.substring(3, 5), 16) * (1 - localRatio) + Integer.parseInt(endColor.substring(3, 5), 16) * localRatio);
            int blue = (int) (Integer.parseInt(startColor.substring(5, 7), 16) * (1 - localRatio) + Integer.parseInt(endColor.substring(5, 7), 16) * localRatio);

            sb.append(String.format("§x§%x§%x§%x§%x§%x§%x", red >> 4, red & 0xF, green >> 4, green & 0xF, blue >> 4, blue & 0xF)).append(text.charAt(i));
        }
        return sb.toString();
    }*/
}
