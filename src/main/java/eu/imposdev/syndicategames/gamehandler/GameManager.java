package eu.imposdev.syndicategames.gamehandler;

import eu.imposdev.syndicategames.SyndicateGames;
import eu.imposdev.syndicategames.api.LanguageManager;
import eu.imposdev.syndicategames.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;

public class GameManager {

    public int lobbyScheduler;
    public int preScheduler;
    public int endingScheduler;

    public void launchLobby() {
        if (SyndicateGames.getInstance().getGameState().equals(GameState.LOBBY)) {
            lobbyScheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(SyndicateGames.getInstance(), () -> {
                if (Bukkit.getOnlinePlayers().size() == 2) {
                    Utils.LOBBY_COUNTDOWN--;
                    String format = "seconds";
                    if (Utils.LOBBY_COUNTDOWN < 2) {
                        format = "second";
                    }
                    String finalFormat = format;
                    Bukkit.getOnlinePlayers().forEach(all -> {
                        float exp = all.getExp();
                        float remove = (float) 1 / SyndicateGames.getInstance().getConfig().getInt("Settings.LobbyCountdown");
                        float newExp = exp - remove;
                        all.setExp(newExp);
                        all.setLevel(Utils.LOBBY_COUNTDOWN);
                        if (Utils.LOBBY_COUNTDOWN == 60) {
                            all.sendMessage(Utils.PREFIX + LanguageManager.getMessage(Utils.LOCALE, "countdown").replaceAll("&", "§").replaceAll("%seconds%", String.valueOf(Utils.LOBBY_COUNTDOWN)).replaceAll("%unit%", finalFormat));
                            all.playSound(all.getLocation(), Sound.PISTON_EXTEND, 1.35f, 1.35f);
                        }
                        if (Utils.LOBBY_COUNTDOWN == 30) {
                            all.sendMessage(Utils.PREFIX + LanguageManager.getMessage(Utils.LOCALE, "countdown").replaceAll("&", "§").replaceAll("%seconds%", String.valueOf(Utils.LOBBY_COUNTDOWN)).replaceAll("%unit%", finalFormat));
                            all.playSound(all.getLocation(), Sound.PISTON_EXTEND, 1.35f, 1.35f);
                        }
                        if (Utils.LOBBY_COUNTDOWN == 15) {
                            all.sendMessage(Utils.PREFIX + LanguageManager.getMessage(Utils.LOCALE, "countdown").replaceAll("&", "§").replaceAll("%seconds%", String.valueOf(Utils.LOBBY_COUNTDOWN)).replaceAll("%unit%", finalFormat));
                            all.playSound(all.getLocation(), Sound.PISTON_EXTEND, 1.35f, 1.35f);
                        }
                        if (Utils.LOBBY_COUNTDOWN == 10) {
                            all.sendMessage(Utils.PREFIX + LanguageManager.getMessage(Utils.LOCALE, "countdown").replaceAll("&", "§").replaceAll("%seconds%", String.valueOf(Utils.LOBBY_COUNTDOWN)).replaceAll("%unit%", finalFormat));
                            all.playSound(all.getLocation(), Sound.PISTON_EXTEND, 1.35f, 1.35f);
                        }
                        if (Utils.LOBBY_COUNTDOWN == 5) {
                            all.sendMessage(Utils.PREFIX + LanguageManager.getMessage(Utils.LOCALE, "countdown").replaceAll("&", "§").replaceAll("%seconds%", String.valueOf(Utils.LOBBY_COUNTDOWN)).replaceAll("%unit%", finalFormat));
                            all.playSound(all.getLocation(), Sound.PISTON_EXTEND, 1.35f, 1.35f);
                        }
                        if (Utils.LOBBY_COUNTDOWN == 4) {
                            all.sendMessage(Utils.PREFIX + LanguageManager.getMessage(Utils.LOCALE, "countdown").replaceAll("&", "§").replaceAll("%seconds%", String.valueOf(Utils.LOBBY_COUNTDOWN)).replaceAll("%unit%", finalFormat));
                            all.playSound(all.getLocation(), Sound.PISTON_EXTEND, 1.35f, 1.35f);
                        }
                        if (Utils.LOBBY_COUNTDOWN == 3) {
                            all.sendMessage(Utils.PREFIX + LanguageManager.getMessage(Utils.LOCALE, "countdown").replaceAll("&", "§").replaceAll("%seconds%", String.valueOf(Utils.LOBBY_COUNTDOWN)).replaceAll("%unit%", finalFormat));
                            all.playSound(all.getLocation(), Sound.PISTON_EXTEND, 1.35f, 1.35f);
                        }
                        if (Utils.LOBBY_COUNTDOWN == 2) {
                            all.sendMessage(Utils.PREFIX + LanguageManager.getMessage(Utils.LOCALE, "countdown").replaceAll("&", "§").replaceAll("%seconds%", String.valueOf(Utils.LOBBY_COUNTDOWN)).replaceAll("%unit%", finalFormat));
                            all.playSound(all.getLocation(), Sound.PISTON_EXTEND, 1.35f, 1.35f);
                        }
                        if (Utils.LOBBY_COUNTDOWN == 1) {
                            all.sendMessage(Utils.PREFIX + LanguageManager.getMessage(Utils.LOCALE, "countdown").replaceAll("&", "§").replaceAll("%seconds%", String.valueOf(Utils.LOBBY_COUNTDOWN)).replaceAll("%unit%", finalFormat));
                            all.playSound(all.getLocation(), Sound.PISTON_EXTEND, 1.35f, 1.35f);
                        }
                    });
                    if (Utils.LOBBY_COUNTDOWN == 0) {
                        Bukkit.broadcastMessage(Utils.PREFIX + LanguageManager.getMessage(Utils.LOCALE, "teleporting").replaceAll("&", "§"));

                        SyndicateGames.getInstance().setGameState(GameState.INGAME);

                        Utils.LIVING_PLAYERS.addAll(Bukkit.getOnlinePlayers());

                        Utils.LIVING_PLAYERS.get(0).teleport(SyndicateGames.getInstance().getLocationAPI().getLocation("Map." + Utils.MAP_INT + ".Spawn.1"));
                        Utils.LIVING_PLAYERS.get(1).teleport(SyndicateGames.getInstance().getLocationAPI().getLocation("Map." + Utils.MAP_INT + ".Spawn.2"));

                        Bukkit.getOnlinePlayers().forEach(all -> {
                            all.getInventory().clear();
                            all.setLevel(0);
                            all.setExp(0f);
                            all.playSound(all.getLocation(), Sound.ENDERMAN_TELEPORT, 2, 2);
                        });

                        /*
                        TODO:
                        Start Pre-Scheduler
                         */

                        Bukkit.getScheduler().cancelTask(lobbyScheduler);

                    }
                }
            }, 20L, 20L);
        }
    }

}
