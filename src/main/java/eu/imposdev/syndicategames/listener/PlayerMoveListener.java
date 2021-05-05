package eu.imposdev.syndicategames.listener;

import eu.imposdev.syndicategames.SyndicateGames;
import eu.imposdev.syndicategames.util.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (Utils.IS_PRE_STATE) {
            if (event.getFrom().getX() != event.getTo().getX() || event.getFrom().getZ() != event.getTo().getZ()) {
                Player player1 = Utils.LIVING_PLAYERS.get(0);
                Player player2 = Utils.LIVING_PLAYERS.get(1);
                if (player.equals(player1)) {
                    player.teleport(SyndicateGames.getInstance().getLocationAPI().getLocation("Map." + Utils.MAP_INT + ".Spawn.1"));
                } else if (player.equals(player2)) {
                    player.teleport(SyndicateGames.getInstance().getLocationAPI().getLocation("Map." + Utils.MAP_INT + ".Spawn.2"));
                }
            }
        }
    }

}
