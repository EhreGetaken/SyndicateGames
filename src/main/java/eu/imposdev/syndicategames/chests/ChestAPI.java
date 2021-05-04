package eu.imposdev.syndicategames.chests;

import eu.imposdev.syndicategames.SyndicateGames;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ChestAPI {

    private final File f = new File("plugins//SyndicateGames", "loot.yml");
    private final YamlConfiguration cfg = YamlConfiguration.loadConfiguration((File)f);

    public void createDefaultChestLoot() {
        cfg.options().copyDefaults(true);
        cfg.options().header("SyndicateGames by ukeo. Copyright (c) 2021 Espen da Silva. All rights reserved.");
        ArrayList<String> list = new ArrayList<>();
        list.add("AIR,20;1");
        list.add("STONE_SWORD,4;1");
        list.add("WEB,2;6");
        list.add("COOKED_BEEF,3;6");
        list.add("IRON_LEGGINGS,1;1");
        list.add("AIR,20;1");
        list.add("CHAINMAIL_HELMET,1;1");
        list.add("CHAINMAIL_BOOTS,2;1");
        list.add("LEATHER_HELMET,2;1");
        list.add("LEATHER_CHESTPLATE,2;1");
        list.add("LEATHER_BOOTS,2;1");
        list.add("AIR,20;1");

        ArrayList<String> list2 = new ArrayList<>();
        list2.add("AIR,23;1");
        list2.add("IRON_SWORD,10;1");
        list2.add("WEB,2;6");
        list2.add("COOKED_BEEF,3;6");
        list.add("IRON_LEGGINGS,1;1");
        list.add("CHAINMAIL_HELMET,1;1");
        list.add("CHAINMAIL_BOOTS,2;1");
        list2.add("AIR,23;1");
        list.add("LEATHER_HELMET,2;1");
        list.add("IRON_CHESTPLATE,1;1");
        list.add("LEATHER_BOOTS,2;1");
        list.add("IRON_BOOTS,2;1");

        cfg.addDefault("Loot.normal", list);
        cfg.addDefault("Loot.premium", list2);
        saveConfig();
    }

    public void readNormalLoot() {
        ArrayList<String> lootNormal = (ArrayList<String>) cfg.getStringList("Loot.normal");
        HashMap<ItemStack, String> lootHash = new HashMap<>();
        lootNormal.forEach(s -> {
            String[] data = s.split(",");
            String materialString = data[0];
            try {
                ItemStack itemStack = new ItemStack(Material.valueOf(materialString));
                lootHash.put(itemStack, data[1]);
            } catch (Exception exception) {
                SyndicateGames.getInstance().getLogger().warning("Could not find material '" + materialString + "'!");
            }
        });
        ChestLoot.LOOT_SET = lootHash;
    }

    public void readPremiumLoot() {
        ArrayList<String> lootPremium = (ArrayList<String>) cfg.getStringList("Loot.premium");
        HashMap<ItemStack, String> lootHash = new HashMap<>();
        lootPremium.forEach(s -> {
            String[] data = s.split(",");
            String materialString = data[0];
            try {
                ItemStack itemStack = new ItemStack(Material.valueOf(materialString));
                lootHash.put(itemStack, data[1]);
            } catch (Exception exception) {
                SyndicateGames.getInstance().getLogger().warning("Could not find material '" + materialString + "'!");
            }
        });
        ChestLoot.LOOT_SET_PREMIUM = lootHash;
    }

    private void saveConfig() {
        try {
            cfg.save(f);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
