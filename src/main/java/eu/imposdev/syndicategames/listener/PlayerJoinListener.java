package eu.imposdev.syndicategames.listener;

import eu.imposdev.syndicategames.SyndicateGames;
import eu.imposdev.syndicategames.api.LanguageManager;
import eu.imposdev.syndicategames.gamehandler.GameState;
import eu.imposdev.syndicategames.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.setJoinMessage(null);

        player.setLevel(Utils.LOBBY_COUNTDOWN - 1);
        player.setExp(0.99f);

        if (SyndicateGames.getInstance().getGameState().equals(GameState.LOBBY)) {
            player.setHealthScale(20D);
            player.setHealth(20D);
            player.setFoodLevel(40);
            player.setGameMode(GameMode.ADVENTURE);
            Utils.setupInventory(player);
            player.teleport(SyndicateGames.getInstance().getLocationAPI().getLocation("Lobby"));
            player.playSound(player.getLocation(), Sound.LEVEL_UP, 0.5f, 0.5f);
            Utils.PLAYER_KILLS.put(player, 0);
            Utils.PLAYER_DEATHS.put(player, 0);
            Bukkit.broadcastMessage(Utils.PREFIX + LanguageManager.getMessage(Utils.LOCALE, "join").replaceAll("&", "§").replace("%player%", player.getName()));
        } else {
            Utils.PLAYER_KILLS.put(player, 0);
            Utils.PLAYER_DEATHS.put(player, 0);
            player.setHealthScale(20D);
            player.setHealth(20D);
            player.setFoodLevel(20);
            player.getInventory().clear();
            player.getInventory().setArmorContents(null);

            player.setGameMode(GameMode.SPECTATOR);
            Bukkit.getOnlinePlayers().forEach(all -> {
                if (all != player && all.getGameMode() != GameMode.SPECTATOR) {
                    all.hidePlayer(player);
                }
            });
            player.teleport(Utils.LIVING_PLAYERS.get(0).getLocation());
        }

        SyndicateGames.getInstance().getScoreboardAPI().sendScoreboard(player);

    }

}
