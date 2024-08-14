package net.ciyuan.linxtools.Utils;

import net.md_5.bungee.api.ChatColor;

import java.awt.*;

public class ColorUtil
{
    public static String Gradient(String Text, String... Colors)
    {
        StringBuilder Output = new StringBuilder();
        int ColorIndex = 0;
        int ColorCount = Colors.length;

        for (int i = 0; i < Text.length(); i++)
        {
            char c = Text.charAt(i);
            if (c == '&')
            {
                ChatColor color = ChatColor.getByChar(Text.charAt(i + 1));
                if (color != null)
                {
                    Output.append(color);
                    i++;
                }
                else
                {
                    Output.append(c);
                }
            }
            else
            {
                if (i + 6 <= Text.length() && Text.charAt(i) == '#')
                {
                    String hex = Text.substring(i + 1, i + 7);
                    try
                    {
                        int rgb = Integer.parseInt(hex, 16);
                        Output.append(ChatColor.of(new Color(rgb)));
                    }
                    catch (NumberFormatException e)
                    {
                        Output.append(c);
                    }
                    i += 6;
                }
                else
                {
                    Output.append(ChatColor.of(new Color(GetRGB(i))));
                    ColorIndex = (ColorIndex + 1) % ColorCount;
                }
                Output.append(c);
            }
        }
        return Output.toString();
    }

    private static int GetRGB(int i) {
        double Frequency = 0.15;
        double R = Math.sin(Frequency * i + 0) * 127 + 128;
        double G = Math.sin(Frequency * i + 2) * 127 + 128;
        double B = Math.sin(Frequency * i + 4) * 127 + 128;
        return ((int) R << 16) + ((int) G << 8) + (int) B;
    }
}
