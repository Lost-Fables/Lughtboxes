package net.lostfables.lughtboxes.util;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;


public class LughUtil {

    public static String chat (String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static String itemStackToString(ItemStack item) {
        YamlConfiguration config = new YamlConfiguration();
        config.set("item", item);
        return config.saveToString();
    }

    public static ItemStack stringToItemStack(String str) {
        YamlConfiguration config = new YamlConfiguration();
        try {
            config.loadFromString(str);
        } catch(Exception e) {
            System.out.println("[LughUtils] Was unable to load Yamlconfigurator from String.");
            return null;
        }
        return config.getItemStack("item");
    }



}
