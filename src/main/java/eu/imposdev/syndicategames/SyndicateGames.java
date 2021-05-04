package eu.imposdev.syndicategames;

import eu.imposdev.syndicategames.api.LanguageManager;
import eu.imposdev.syndicategames.chests.ChestAPI;
import eu.imposdev.syndicategames.api.LocationAPI;
import eu.imposdev.syndicategames.chests.ChestLoot;
import eu.imposdev.syndicategames.commands.Setup_Command;
import eu.imposdev.syndicategames.gamehandler.GameManager;
import eu.imposdev.syndicategames.gamehandler.GameState;
import eu.imposdev.syndicategames.listener.*;
import eu.imposdev.syndicategames.userutil.APIManager;
import eu.imposdev.syndicategames.userutil.UpdateChecker;
import eu.imposdev.syndicategames.util.Utils;
import net.core.api.ItemBuilder;
import net.core.api.LanguageAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Random;

public class SyndicateGames extends JavaPlugin {

    private static SyndicateGames instance;
    private LocationAPI locationAPI;
    private ChestAPI chestAPI;
    private ChestLoot chestLoot;
    private UpdateChecker updateChecker;
    private GameState gameState;
    private GameManager gameManager;

    private String version = "";

    private ArrayList<String> maps = new ArrayList<>();

    @Override
    public void onEnable() {
        instance = this;

        version = this.getDescription().getVersion();

        ArrayList<String> mapz = new ArrayList<>();
        mapz.add("Forrest");

        getConfig().options().copyDefaults(true);
        getConfig().options().header("SyndicateGames by ukeo. Copyright (c) 2021 Espen da Silva. All rights reserved. DO NOT CHANGE THE TRACKING KEY!");
        getConfig().addDefault("Settings.Tracking-Key", "OKHH-TP61-4YBX-063C");
        getConfig().addDefault("Settings.Prefix", "§8[§3SyndicateGames§8] ");
        getConfig().addDefault("Settings.locale", "en");
        getConfig().addDefault("Settings.LobbyCountdown", 10);
        getConfig().addDefault("Settings.PreCountdown", 5);
        getConfig().addDefault("Settings.RebootCountdown", 10);
        getConfig().addDefault("Maps", mapz);
        saveConfig();

        maps = (ArrayList<String>) getConfig().getStringList("Maps");

        if (!new APIManager(getConfig().getString("Settings.Tracking-Key"), "https://api.imposdev.eu/verify.php", this).register());

        gameState = GameState.LOBBY;
        locationAPI = new LocationAPI();
        chestAPI = new ChestAPI();
        chestLoot = new ChestLoot();
        updateChecker = new UpdateChecker();
        gameManager = new GameManager();

        updateChecker.check();
        if (updateChecker.isAvailable()) {
            getLogger().info("There is an update available! Go and check it out on HIERLINK");
        } else {
            getLogger().info("You are running the latest release of SyndicateGames");
        }

        Utils.LOCALE = getConfig().getString("Settings.locale");
        Utils.PREFIX = getConfig().getString("Settings.Prefix");
        Utils.LOBBY_COUNTDOWN = getConfig().getInt("Settings.LobbyCountdown") + 1;
        Utils.PRE_COUNTDOWN = getConfig().getInt("Settings.PreCountdown") + 1;
        Utils.ENDING_COUNTDOWN = getConfig().getInt("Settings.RebootCountdown") + 1;

        LanguageManager.loadMessages();

        chestAPI.createDefaultChestLoot();
        chestAPI.readNormalLoot();
        chestAPI.readPremiumLoot();

        registerListener();
        registerCommands();
        registerItems();

        gameManager.launchLobby();

    }

    @Override
    public void onDisable() {

    }

    private void registerListener() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new PlayerInteractListener(), instance);
        pluginManager.registerEvents(new PlayerJoinListener(), instance);
        pluginManager.registerEvents(new PlayerQuitListener(), instance);
        pluginManager.registerEvents(new DisabledListener(), instance);
        pluginManager.registerEvents(new InventoryClickListener(), instance);
    }

    private void registerCommands() {
        getCommand("setup").setExecutor(new Setup_Command());
    }

    private void registerItems() {
        Utils.BACK_TO_LOBBY = new ItemBuilder(Material.NETHER_STAR).setName(LanguageManager.getMessage(Utils.LOCALE, "backToLobby").replaceAll("&", "§")).build();
        Utils.FORCE_MAP = new ItemBuilder(Material.CHEST).setName(LanguageManager.getMessage(Utils.LOCALE, "forceMap").replaceAll("&", "§")).build();
    }

    private void getRandomMap() {
        Random rnd = new Random();
        int pos = rnd.nextInt(getMaps().size());
        String random = getMaps().get(pos);
        Utils.MAP_INT = pos;
        Utils.MAP_NAME = random;
        getLogger().info("Set map to " + Utils.MAP_NAME);
    }

    public static SyndicateGames getInstance() {
        return instance;
    }

    public LocationAPI getLocationAPI() {
        return locationAPI;
    }

    public ChestAPI getChestAPI() {
        return chestAPI;
    }

    public ChestLoot getChestLoot() {
        return chestLoot;
    }

    public String getVersion() {
        return version;
    }

    public UpdateChecker getUpdateChecker() {
        return updateChecker;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public ArrayList<String> getMaps() {
        return maps;
    }
}
