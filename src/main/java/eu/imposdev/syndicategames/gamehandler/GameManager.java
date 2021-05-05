package eu.imposdev.syndicategames.gamehandler;

import eu.imposdev.syndicategames.SyndicateGames;
import eu.imposdev.syndicategames.api.LanguageManager;
import eu.imposdev.syndicategames.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;

public class GameManager {

    public int lobbyScheduler;
    public int preScheduler;
    public int endingScheduler;

    public void launchLobby() {
        if (SyndicateGames.getInstance().getGameState().equals(GameState.LOBBY)) {
            Bukkit.getOnlinePlayers().forEach(all -> {
                all.setExp(0.99f);
            });
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
                        float remove = (float) 1 / (SyndicateGames.getInstance().getConfig().getInt("Settings.LobbyCountdown") + 1);
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
                            all.getInventory().setArmorContents(null);
                            all.playSound(all.getLocation(), Sound.ENDERMAN_TELEPORT, 2, 2);
                            all.setGameMode(GameMode.SURVIVAL);
                            Utils.PLAYER_DEATHS.put(all, 0);
                            Utils.PLAYER_KILLS.put(all, 0);
                            SyndicateGames.getInstance().getScoreboardAPI().sendScoreboard(all);
                        });

                        if (Utils.PRE_COUNTDOWN_AFTER_KILL) {
                            launchPreCountdown();
                        } else {
                            Bukkit.broadcastMessage(Utils.PREFIX + LanguageManager.getMessage(Utils.LOCALE, "start").replaceAll("&", "§"));
                            Bukkit.getOnlinePlayers().forEach(all -> {
                                all.playSound(all.getLocation(), Sound.NOTE_PLING, 2, 2);
                            });
                        }

                        Bukkit.getScheduler().cancelTask(lobbyScheduler);

                    }
                }
            }, 20L, 20L);
        }
    }

    public void launchPreCountdown() {
        Utils.IS_PRE_STATE = true;
        if (SyndicateGames.getInstance().getGameState().equals(GameState.INGAME)) {
            Bukkit.getOnlinePlayers().forEach(all -> {
                all.setExp(0.99f);
            });
            preScheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(SyndicateGames.getInstance(), () -> {
                if (Utils.LIVING_PLAYERS.size() == 2) {
                    Utils.PRE_COUNTDOWN--;
                    String format = "seconds";
                    if (Utils.PRE_COUNTDOWN < 2) {
                        format = "second";
                    }
                    String finalFormat = format;
                    Bukkit.getOnlinePlayers().forEach(all -> {
                        float exp = all.getExp();
                        float remove = (float) 1 / (SyndicateGames.getInstance().getConfig().getInt("Settings.PreCountdown") + 1);
                        float newExp = exp - remove;
                        all.setExp(newExp);
                        all.setLevel(Utils.PRE_COUNTDOWN);
                        if (Utils.PRE_COUNTDOWN == 60) {
                            all.sendMessage(Utils.PREFIX + LanguageManager.getMessage(Utils.LOCALE, "preCountDown").replaceAll("&", "§").replaceAll("%seconds%", String.valueOf(Utils.PRE_COUNTDOWN)).replaceAll("%unit%", finalFormat));
                            all.playSound(all.getLocation(), Sound.PISTON_EXTEND, 1.35f, 1.35f);
                        }
                        if (Utils.PRE_COUNTDOWN == 30) {
                            all.sendMessage(Utils.PREFIX + LanguageManager.getMessage(Utils.LOCALE, "preCountDown").replaceAll("&", "§").replaceAll("%seconds%", String.valueOf(Utils.PRE_COUNTDOWN)).replaceAll("%unit%", finalFormat));
                            all.playSound(all.getLocation(), Sound.PISTON_EXTEND, 1.35f, 1.35f);
                        }
                        if (Utils.PRE_COUNTDOWN == 15) {
                            all.sendMessage(Utils.PREFIX + LanguageManager.getMessage(Utils.LOCALE, "preCountDown").replaceAll("&", "§").replaceAll("%seconds%", String.valueOf(Utils.PRE_COUNTDOWN)).replaceAll("%unit%", finalFormat));
                            all.playSound(all.getLocation(), Sound.PISTON_EXTEND, 1.35f, 1.35f);
                        }
                        if (Utils.PRE_COUNTDOWN == 10) {
                            all.sendMessage(Utils.PREFIX + LanguageManager.getMessage(Utils.LOCALE, "preCountDown").replaceAll("&", "§").replaceAll("%seconds%", String.valueOf(Utils.PRE_COUNTDOWN)).replaceAll("%unit%", finalFormat));
                            all.playSound(all.getLocation(), Sound.PISTON_EXTEND, 1.35f, 1.35f);
                        }
                        if (Utils.PRE_COUNTDOWN == 5) {
                            all.sendMessage(Utils.PREFIX + LanguageManager.getMessage(Utils.LOCALE, "preCountDown").replaceAll("&", "§").replaceAll("%seconds%", String.valueOf(Utils.PRE_COUNTDOWN)).replaceAll("%unit%", finalFormat));
                            all.playSound(all.getLocation(), Sound.PISTON_EXTEND, 1.35f, 1.35f);
                        }
                        if (Utils.PRE_COUNTDOWN == 4) {
                            all.sendMessage(Utils.PREFIX + LanguageManager.getMessage(Utils.LOCALE, "preCountDown").replaceAll("&", "§").replaceAll("%seconds%", String.valueOf(Utils.PRE_COUNTDOWN)).replaceAll("%unit%", finalFormat));
                            all.playSound(all.getLocation(), Sound.PISTON_EXTEND, 1.35f, 1.35f);
                        }
                        if (Utils.PRE_COUNTDOWN == 3) {
                            all.sendMessage(Utils.PREFIX + LanguageManager.getMessage(Utils.LOCALE, "preCountDown").replaceAll("&", "§").replaceAll("%seconds%", String.valueOf(Utils.PRE_COUNTDOWN)).replaceAll("%unit%", finalFormat));
                            all.playSound(all.getLocation(), Sound.PISTON_EXTEND, 1.35f, 1.35f);
                        }
                        if (Utils.PRE_COUNTDOWN == 2) {
                            all.sendMessage(Utils.PREFIX + LanguageManager.getMessage(Utils.LOCALE, "preCountDown").replaceAll("&", "§").replaceAll("%seconds%", String.valueOf(Utils.PRE_COUNTDOWN)).replaceAll("%unit%", finalFormat));
                            all.playSound(all.getLocation(), Sound.PISTON_EXTEND, 1.35f, 1.35f);
                        }
                        if (Utils.PRE_COUNTDOWN == 1) {
                            all.sendMessage(Utils.PREFIX + LanguageManager.getMessage(Utils.LOCALE, "preCountDown").replaceAll("&", "§").replaceAll("%seconds%", String.valueOf(Utils.PRE_COUNTDOWN)).replaceAll("%unit%", finalFormat));
                            all.playSound(all.getLocation(), Sound.PISTON_EXTEND, 1.35f, 1.35f);
                        }
                    });
                    if (Utils.PRE_COUNTDOWN == 0) {
                        Utils.IS_PRE_STATE = false;
                        Bukkit.broadcastMessage(Utils.PREFIX + LanguageManager.getMessage(Utils.LOCALE, "start").replaceAll("&", "§"));
                        Bukkit.getOnlinePlayers().forEach(all -> {
                            all.playSound(all.getLocation(), Sound.NOTE_PLING, 2, 2);
                            all.setGameMode(GameMode.SURVIVAL);
                        });
                        Bukkit.getScheduler().cancelTask(preScheduler);
                        if (Utils.PRE_COUNTDOWN_AFTER_KILL) {
                            Utils.PRE_COUNTDOWN = SyndicateGames.getInstance().getConfig().getInt("Settings.PreCountdown") + 1;
                        }
                    }
                }
            }, 20L, 20L);
        }
    }

    public void startReboot() {
        SyndicateGames.getInstance().setGameState(GameState.ENDING);
        Bukkit.getOnlinePlayers().forEach(Utils::setupInventory);
        Bukkit.broadcastMessage(Utils.PREFIX + LanguageManager.getMessage(Utils.LOCALE, "serverReboot").replaceAll("&", "§"));
        endingScheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(SyndicateGames.getInstance(), () -> {
            if (Bukkit.getOnlinePlayers().size() >= 1) {
                Utils.ENDING_COUNTDOWN--;
                if (Utils.ENDING_COUNTDOWN == 0) {
                    Bukkit.getScheduler().cancelTask(endingScheduler);
                    Bukkit.getOnlinePlayers().forEach(all -> {
                        all.kickPlayer(null);
                    });
                    if (Utils.REBOOT) {
                        Bukkit.getScheduler().scheduleSyncDelayedTask(SyndicateGames.getInstance(), Bukkit::shutdown, 10L);
                    } else {
                        Bukkit.getScheduler().scheduleSyncDelayedTask(SyndicateGames.getInstance(), Bukkit::reload, 10L);
                    }
                }
            } else {
                if (Utils.REBOOT) {
                    Bukkit.shutdown();
                } else {
                    Bukkit.reload();
                    Bukkit.getScheduler().cancelTask(endingScheduler);
                }
            }
        }, 20L, 20L);
    }

}
