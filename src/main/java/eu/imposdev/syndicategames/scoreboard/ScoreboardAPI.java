package eu.imposdev.syndicategames.scoreboard;

import eu.imposdev.syndicategames.api.LanguageManager;
import eu.imposdev.syndicategames.util.Utils;
import org.bukkit.entity.Player;

public class ScoreboardAPI {

    public void sendScoreboard(Player player) {
        FastBoard board = new FastBoard(player);
        board.updateTitle(LanguageManager.getMessage(Utils.LOCALE, "title").replaceAll("&", "§"));
        board.updateLine(0, LanguageManager.getMessage(Utils.LOCALE, "line1").replaceAll("&", "§").replaceAll("%map%", Utils.MAP_NAME).replaceAll("%kills%", String.valueOf(Utils.PLAYER_KILLS.get(player))).replaceAll("%deaths%", String.valueOf(Utils.PLAYER_DEATHS.get(player))));
        board.updateLine(1, LanguageManager.getMessage(Utils.LOCALE, "line2").replaceAll("&", "§").replaceAll("%map%", Utils.MAP_NAME).replaceAll("%kills%", String.valueOf(Utils.PLAYER_KILLS.get(player))).replaceAll("%deaths%", String.valueOf(Utils.PLAYER_DEATHS.get(player))));
        board.updateLine(2, LanguageManager.getMessage(Utils.LOCALE, "line3").replaceAll("&", "§").replaceAll("%map%", Utils.MAP_NAME).replaceAll("%kills%", String.valueOf(Utils.PLAYER_KILLS.get(player))).replaceAll("%deaths%", String.valueOf(Utils.PLAYER_DEATHS.get(player))));
        board.updateLine(3, LanguageManager.getMessage(Utils.LOCALE, "line4").replaceAll("&", "§").replaceAll("%map%", Utils.MAP_NAME).replaceAll("%kills%", String.valueOf(Utils.PLAYER_KILLS.get(player))).replaceAll("%deaths%", String.valueOf(Utils.PLAYER_DEATHS.get(player))));
        board.updateLine(4, LanguageManager.getMessage(Utils.LOCALE, "line5").replaceAll("&", "§").replaceAll("%map%", Utils.MAP_NAME).replaceAll("%kills%", String.valueOf(Utils.PLAYER_KILLS.get(player))).replaceAll("%deaths%", String.valueOf(Utils.PLAYER_DEATHS.get(player))));
        board.updateLine(5, LanguageManager.getMessage(Utils.LOCALE, "line6").replaceAll("&", "§").replaceAll("%map%", Utils.MAP_NAME).replaceAll("%kills%", String.valueOf(Utils.PLAYER_KILLS.get(player))).replaceAll("%deaths%", String.valueOf(Utils.PLAYER_DEATHS.get(player))));
        board.updateLine(6, LanguageManager.getMessage(Utils.LOCALE, "line7").replaceAll("&", "§").replaceAll("%map%", Utils.MAP_NAME).replaceAll("%kills%", String.valueOf(Utils.PLAYER_KILLS.get(player))).replaceAll("%deaths%", String.valueOf(Utils.PLAYER_DEATHS.get(player))));
        board.updateLine(7, LanguageManager.getMessage(Utils.LOCALE, "line8").replaceAll("&", "§").replaceAll("%map%", Utils.MAP_NAME).replaceAll("%kills%", String.valueOf(Utils.PLAYER_KILLS.get(player))).replaceAll("%deaths%", String.valueOf(Utils.PLAYER_DEATHS.get(player))));
        board.updateLine(8, LanguageManager.getMessage(Utils.LOCALE, "line9").replaceAll("&", "§").replaceAll("%map%", Utils.MAP_NAME).replaceAll("%kills%", String.valueOf(Utils.PLAYER_KILLS.get(player))).replaceAll("%deaths%", String.valueOf(Utils.PLAYER_DEATHS.get(player))));
    }

}
