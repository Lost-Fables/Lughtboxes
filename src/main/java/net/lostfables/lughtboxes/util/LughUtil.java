package net.lostfables.lughtboxes.util;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    //TODO
    public static String itemStackListToString(List<ItemStack> item) {
        YamlConfiguration config = new YamlConfiguration();


        for(int x = 0; x < item.size(); x++) {

            if(item.get(x) != null && item.get(x).getAmount() > 0) {
                config.set(String.valueOf(x), item.get(x));
            } else {
                ItemStack placeholder = new ItemStack(Material.STONE);
                ItemMeta im = placeholder.getItemMeta();
                im.setDisplayName("item"+Integer.toBinaryString(x));
                placeholder.setItemMeta(im);
                config.set(String.valueOf(x), placeholder);
            }

        }
        return config.saveToString();

    }

    public static List<ItemStack> stringToItemStackList(String str) {
        YamlConfiguration config = new YamlConfiguration();
        List<ItemStack> itemStackList = new ArrayList<>();
        try {
            config.loadFromString(str);
        } catch(Exception e) {
            System.out.println("[LughUtils] Was unable to load Yamlconfigurator from String.");
            return null;
        }

        Set<String> keys = config.getKeys(false);
        for(int x = 0; x < keys.size(); x++) {
            ItemStack placeholder = new ItemStack(Material.STONE);
            ItemMeta im = placeholder.getItemMeta();
            im.setDisplayName("item"+Integer.toBinaryString(x));
            placeholder.setItemMeta(im);
            config.set(String.valueOf(x), placeholder);
            if(config.getItemStack(String.valueOf(x)).isSimilar(placeholder)) {
                config.getItemStack(String.valueOf(x)).getItemMeta().toString();
                itemStackList.add(config.getItemStack(String.valueOf(x)));

            } else {
                itemStackList.add(null);
            }

        }
        return itemStackList;
    }


}
