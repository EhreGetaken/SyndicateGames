package eu.imposdev.syndicategames.util;

import eu.imposdev.syndicategames.SyndicateGames;
import eu.imposdev.syndicategames.gamehandler.GameState;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

public class Utils {

    public static String PREFIX = "";
    public static String LOCALE = "en";

    public static int MAP_INT;
    public static int LOBBY_COUNTDOWN;
    public static int PRE_COUNTDOWN;
    public static int ENDING_COUNTDOWN;
    public static int KILLS_TO_WIN;

    public static boolean PRE_COUNTDOWN_AFTER_KILL;
    public static boolean IS_PRE_STATE = false;
    public static boolean REBOOT;

    public static String MAP_NAME;

    public static ArrayList<Player> LIVING_PLAYERS = new ArrayList<>();

    public static HashMap<Player, Integer> PLAYER_KILLS = new HashMap<>();
    public static HashMap<Player, Integer> PLAYER_DEATHS = new HashMap<>();

    public static ItemStack FORCE_MAP;
    public static ItemStack BACK_TO_LOBBY;

    public static void setupInventory(Player player) {
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        if (SyndicateGames.getInstance().getGameState().equals(GameState.LOBBY)) {
            if (player.hasPermission("syndicategames.forcemap")) {
                player.getInventory().setItem(3, FORCE_MAP);
                player.getInventory().setItem(5, BACK_TO_LOBBY);
            } else {
                player.getInventory().setItem(4, BACK_TO_LOBBY);
            }
        } else {
            player.getInventory().setItem(4, BACK_TO_LOBBY);
        }
    }

}
