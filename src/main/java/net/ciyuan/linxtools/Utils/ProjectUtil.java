package net.ciyuan.linxtools.Utils;

import net.ciyuan.linxtools.LinxTools;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ProjectUtil
{
    public static String GetVersion() {
        Properties Prop = new Properties();
        try (InputStream inputStream = ProjectUtil.class.getResourceAsStream("/META-INF/maven/net.ciyuan/LinxTools/pom.properties")) {
            if (inputStream != null) {
                Prop.load(inputStream);
                return Prop.getProperty("version");
            }
        } catch (IOException e) {
            LinxTools.ConsoleLogger.info(e.toString());
        }
        return "[Unknown Version]";
    }
}
