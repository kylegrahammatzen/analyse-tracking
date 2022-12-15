package net.analyse.base.platform.bukkit.util;

import org.bukkit.ChatColor;

public class ColorUtil {
    public static String parse(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }
}
