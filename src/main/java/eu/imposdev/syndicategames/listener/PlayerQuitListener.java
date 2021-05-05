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
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        event.setQuitMessage(null);
        if (SyndicateGames.getInstance().getGameState().equals(GameState.LOBBY)) {
            Bukkit.broadcastMessage(Utils.PREFIX + LanguageManager.getMessage(Utils.LOCALE, "quit").replaceAll("&", "§").replace("%player%", player.getName()));
            Utils.PLAYER_KILLS.remove(player);
            Utils.PLAYER_DEATHS.remove(player);
            Utils.LIVING_PLAYERS.remove(player);
            Bukkit.getScheduler().scheduleSyncDelayedTask(SyndicateGames.getInstance(), () -> {
                if (Bukkit.getOnlinePlayers().size() < 2) {
                    Bukkit.getOnlinePlayers().forEach(all -> {
                        all.setLevel(SyndicateGames.getInstance().getConfig().getInt("Settings.LobbyCountdown"));
                        all.setExp(0.99f);
                        Utils.LOBBY_COUNTDOWN = SyndicateGames.getInstance().getConfig().getInt("Settings.LobbyCountdown") + 1;
                    });
                    Bukkit.broadcastMessage(Utils.PREFIX + LanguageManager.getMessage(Utils.LOCALE, "countdownCancel").replaceAll("&", "§"));
                }
            }, 3L);
        }
        if (SyndicateGames.getInstance().getGameState().equals(GameState.ENDING)) {
            Bukkit.broadcastMessage(Utils.PREFIX + LanguageManager.getMessage(Utils.LOCALE, "quit").replaceAll("&", "§").replace("%player%", player.getName()));
        }
        if (SyndicateGames.getInstance().getGameState().equals(GameState.INGAME)) {
            if (Utils.LIVING_PLAYERS.contains(player)) {
                Utils.LIVING_PLAYERS.remove(player);
                Utils.PLAYER_DEATHS.remove(player);
                Utils.PLAYER_KILLS.remove(player);
                Bukkit.getOnlinePlayers().forEach(all -> {
                    all.teleport(SyndicateGames.getInstance().getLocationAPI().getLocation("Lobby"));
                    all.setGameMode(GameMode.ADVENTURE);
                    all.playSound(all.getLocation(), Sound.VILLAGER_YES, 1f, 1f);
                });
                Bukkit.broadcastMessage(Utils.PREFIX + LanguageManager.getMessage(Utils.LOCALE, "quitIngame").replaceAll("&", "§").replace("%player%", player.getName()).replace("%winner%", Utils.LIVING_PLAYERS.get(0).getName()));
                SyndicateGames.getInstance().getGameManager().startReboot();
            }
        }

    }

}
