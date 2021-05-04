package eu.imposdev.syndicategames;

import eu.imposdev.syndicategames.api.LanguageManager;
import eu.imposdev.syndicategames.chests.ChestAPI;
import eu.imposdev.syndicategames.api.LocationAPI;
import eu.imposdev.syndicategames.chests.ChestLoot;
import eu.imposdev.syndicategames.commands.Setup_Command;
import eu.imposdev.syndicategames.gamehandler.GameState;
import eu.imposdev.syndicategames.listener.DisabledListener;
import eu.imposdev.syndicategames.listener.PlayerInteractListener;
import eu.imposdev.syndicategames.listener.PlayerJoinListener;
import eu.imposdev.syndicategames.userutil.APIManager;
import eu.imposdev.syndicategames.userutil.UpdateChecker;
import eu.imposdev.syndicategames.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class SyndicateGames extends JavaPlugin {

    private static SyndicateGames instance;
    private LocationAPI locationAPI;
    private ChestAPI chestAPI;
    private ChestLoot chestLoot;
    private UpdateChecker updateChecker;
    private GameState gameState;

    private String version = "";

    @Override
    public void onEnable() {
        instance = this;

        version = this.getDescription().getVersion();

        getConfig().options().copyDefaults(true);
        getConfig().options().header("SyndicateGames by ukeo. Copyright (c) 2021 Espen da Silva. All rights reserved. DO NOT CHANGE THE TRACKING KEY!");
        getConfig().addDefault("Settings.Tracking-Key", "OKHH-TP61-4YBX-063C");
        getConfig().addDefault("Settings.Prefix", "§8[§3SyndicateGames§8] ");
        getConfig().addDefault("Settings.locale", "en");
        getConfig().addDefault("Settings.LobbyCountdown", 10);
        getConfig().addDefault("Settings.RebootCountdown", 10);
        saveConfig();

        if (!new APIManager(getConfig().getString("Settings.Tracking-Key"), "https://api.imposdev.eu/verify.php", this).register());

        gameState = GameState.LOBBY;
        locationAPI = new LocationAPI();
        chestAPI = new ChestAPI();
        chestLoot = new ChestLoot();
        updateChecker = new UpdateChecker();

        updateChecker.check();
        if (updateChecker.isAvailable()) {
            getLogger().info("There is an update available! Go and check it out on HIERLINK");
        } else {
            getLogger().info("You are running the latest release of SyndicateGames");
        }

        Utils.LOCALE = getConfig().getString("Settings.locale");
        Utils.PREFIX = getConfig().getString("Settings.Prefix");

        LanguageManager.loadMessages();

        chestAPI.createDefaultChestLoot();
        chestAPI.readNormalLoot();
        chestAPI.readPremiumLoot();

        registerListener();
        registerCommands();

    }

    @Override
    public void onDisable() {

    }

    private void registerListener() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new PlayerInteractListener(), instance);
        pluginManager.registerEvents(new PlayerJoinListener(), instance);
        pluginManager.registerEvents(new DisabledListener(), instance);
    }

    private void registerCommands() {
        getCommand("setup").setExecutor(new Setup_Command());
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
}
