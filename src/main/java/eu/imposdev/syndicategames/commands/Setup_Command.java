package eu.imposdev.syndicategames.commands;

import eu.imposdev.syndicategames.SyndicateGames;
import eu.imposdev.syndicategames.api.LanguageManager;
import eu.imposdev.syndicategames.util.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Setup_Command implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("syndicategames.setup")) {
                if (args.length == 0) {
                    player.sendMessage(Utils.PREFIX + LanguageManager.getMessage(Utils.LOCALE, "setupUsage").replaceAll("&", "§"));
                }
                if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("setLobby")) {
                        SyndicateGames.getInstance().getLocationAPI().saveLocation("Lobby", player.getLocation());
                        player.sendMessage(Utils.PREFIX + LanguageManager.getMessage(Utils.LOCALE, "setupLobby").replaceAll("&", "§"));
                    }
                }
                if (args.length == 3) {
                    if (args[0].equalsIgnoreCase("setSpawn")) {
                        try {
                            int spawn = Integer.parseInt(args[1]);
                            int map = Integer.parseInt(args[2]);
                            map = map - 1;
                            SyndicateGames.getInstance().getLocationAPI().saveLocation("Map." + map + ".Spawn." + spawn, player.getLocation());
                            player.sendMessage(Utils.PREFIX + LanguageManager.getMessage(Utils.LOCALE, "setupSpawn").replaceAll("&", "§").replaceAll("%spawn%", String.valueOf(spawn)).replaceAll("%map%", String.valueOf(map + 1)));
                        } catch (NumberFormatException exception) {
                            player.sendMessage(Utils.PREFIX + LanguageManager.getMessage(Utils.LOCALE, "setupSpawnFailed").replaceAll("&", "§"));
                        }
                    }
                }
            }
        }
        return true;
    }
}
