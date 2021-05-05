package eu.imposdev.syndicategames.listener;

import eu.imposdev.syndicategames.SyndicateGames;
import eu.imposdev.syndicategames.api.LanguageManager;
import eu.imposdev.syndicategames.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        event.setDeathMessage(null);
        event.getDrops().clear();

        PlayerInteractListener.blockPlacedLoc.clear();
        PlayerInteractListener.chests.clear();

        if (player.getKiller() != null) {
            Player killer = player.getKiller();
            int kills = Utils.PLAYER_KILLS.get(killer);
            kills++;
            Utils.PLAYER_KILLS.put(killer, kills);

            int deaths = Utils.PLAYER_DEATHS.get(player);
            deaths++;
            Utils.PLAYER_DEATHS.put(player, deaths);

            player.getInventory().clear();
            killer.getInventory().clear();
            player.getInventory().setArmorContents(null);
            killer.getInventory().setArmorContents(null);
            player.setFoodLevel(40);
            killer.setFoodLevel(40);

            Utils.LIVING_PLAYERS.forEach(all -> {
                SyndicateGames.getInstance().getScoreboardAPI().sendScoreboard(all);
            });
            int finalKills = kills;

            Bukkit.broadcastMessage(Utils.PREFIX + LanguageManager.getMessage(Utils.LOCALE, "kill").replaceAll("&", "§").replace("%player%", player.getName()).replace("%killer%", killer.getName()));

            Bukkit.getScheduler().scheduleSyncDelayedTask(SyndicateGames.getInstance(), () -> {
                player.spigot().respawn();

                if (finalKills == Utils.KILLS_TO_WIN) {
                    Bukkit.getOnlinePlayers().forEach(all -> {
                        all.teleport(SyndicateGames.getInstance().getLocationAPI().getLocation("Lobby"));
                        all.setGameMode(GameMode.ADVENTURE);
                    });
                    player.playSound(player.getLocation(), Sound.ANVIL_BREAK, 0.5f, 0.5f);
                    killer.playSound(killer.getLocation(), Sound.VILLAGER_YES, 1, 1);
                    Bukkit.broadcastMessage(Utils.PREFIX + LanguageManager.getMessage(Utils.LOCALE, "win").replaceAll("&", "§").replace("%player%", killer.getName()));
                    SyndicateGames.getInstance().getGameManager().startReboot();
                    return;
                }

                if (Utils.LIVING_PLAYERS.get(0).equals(player)) {
                    player.teleport(SyndicateGames.getInstance().getLocationAPI().getLocation("Map." + Utils.MAP_INT + ".Spawn.1"));
                    killer.teleport(SyndicateGames.getInstance().getLocationAPI().getLocation("Map." + Utils.MAP_INT + ".Spawn.2"));
                } else {
                    player.teleport(SyndicateGames.getInstance().getLocationAPI().getLocation("Map." + Utils.MAP_INT + ".Spawn.2"));
                    killer.teleport(SyndicateGames.getInstance().getLocationAPI().getLocation("Map." + Utils.MAP_INT + ".Spawn.1"));
                }

                player.playSound(player.getLocation(), Sound.ANVIL_BREAK, 0.5f, 0.5f);
                killer.playSound(killer.getLocation(), Sound.VILLAGER_YES, 1, 1);

                if (Utils.PRE_COUNTDOWN_AFTER_KILL) {
                    SyndicateGames.getInstance().getGameManager().launchPreCountdown();
                }
            }, 2L);

        } else {
            Player killer;
            if (Utils.LIVING_PLAYERS.get(0).equals(player)) {
                killer = Utils.LIVING_PLAYERS.get(1);
            } else {
                killer = Utils.LIVING_PLAYERS.get(0);
            }
            int kills = Utils.PLAYER_KILLS.get(killer);
            kills++;
            Utils.PLAYER_KILLS.put(killer, kills);

            int deaths = Utils.PLAYER_DEATHS.get(player);
            deaths++;
            Utils.PLAYER_DEATHS.put(player, deaths);

            Utils.LIVING_PLAYERS.forEach(all -> {
                SyndicateGames.getInstance().getScoreboardAPI().sendScoreboard(all);
            });
            int finalKills = kills;

            Bukkit.broadcastMessage(Utils.PREFIX + LanguageManager.getMessage(Utils.LOCALE, "kill").replaceAll("&", "§").replace("%player%", player.getName()).replace("%killer%", killer.getName()));

            Bukkit.getScheduler().scheduleSyncDelayedTask(SyndicateGames.getInstance(), () -> {
                player.spigot().respawn();

                if (finalKills == Utils.KILLS_TO_WIN) {
                    Bukkit.getOnlinePlayers().forEach(all -> {
                        all.teleport(SyndicateGames.getInstance().getLocationAPI().getLocation("Lobby"));
                        all.setGameMode(GameMode.ADVENTURE);
                    });
                    player.playSound(player.getLocation(), Sound.ANVIL_BREAK, 0.5f, 0.5f);
                    killer.playSound(killer.getLocation(), Sound.VILLAGER_YES, 1, 1);
                    Bukkit.broadcastMessage(Utils.PREFIX + LanguageManager.getMessage(Utils.LOCALE, "win").replaceAll("&", "§").replace("%player%", killer.getName()));
                    SyndicateGames.getInstance().getGameManager().startReboot();
                    return;
                }

                if (Utils.LIVING_PLAYERS.get(0).equals(player)) {
                    player.teleport(SyndicateGames.getInstance().getLocationAPI().getLocation("Map." + Utils.MAP_INT + ".Spawn.1"));
                    killer.teleport(SyndicateGames.getInstance().getLocationAPI().getLocation("Map." + Utils.MAP_INT + ".Spawn.2"));
                } else {
                    player.teleport(SyndicateGames.getInstance().getLocationAPI().getLocation("Map." + Utils.MAP_INT + ".Spawn.2"));
                    killer.teleport(SyndicateGames.getInstance().getLocationAPI().getLocation("Map." + Utils.MAP_INT + ".Spawn.1"));
                }

                player.playSound(player.getLocation(), Sound.ANVIL_BREAK, 0.5f, 0.5f);
                killer.playSound(killer.getLocation(), Sound.VILLAGER_YES, 1, 1);

                if (Utils.PRE_COUNTDOWN_AFTER_KILL) {
                    SyndicateGames.getInstance().getGameManager().launchPreCountdown();
                }
            }, 2L);
        }
    }

}
