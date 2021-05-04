package eu.imposdev.syndicategames.listener;

import eu.imposdev.syndicategames.SyndicateGames;
import eu.imposdev.syndicategames.gamehandler.GameState;
import eu.imposdev.syndicategames.util.Utils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlayerInteractListener implements Listener {

    private static final HashMap<Location, Inventory> chests = new HashMap();
    private static final ArrayList<Location> blockPlacedLoc = new ArrayList<>();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (e.getAction() == null) return;
        if (e.getClickedBlock() == null) return;
        if ((Utils.LIVING_PLAYERS.contains(p)) && (SyndicateGames.getInstance().getGameState().equals(GameState.INGAME)) &&
                (e.getClickedBlock().getType() == Material.CHEST)) {
            if (!blockPlacedLoc.contains(e.getClickedBlock().getLocation())) {
                if (!chests.containsKey(e.getClickedBlock().getLocation())) {
                    Chest chest = (Chest) e.getClickedBlock().getState();
                    Inventory chestInv = chest.getInventory();
                    List<ItemStack> loots = SyndicateGames.getInstance().getChestLoot().getLoot();
                    SyndicateGames.getInstance().getChestLoot().fillChest(chest);
                    chests.put(e.getClickedBlock().getLocation(), chestInv);
                }
            }
        }
        if ((Utils.LIVING_PLAYERS.contains(p)) && (SyndicateGames.getInstance().getGameState().equals(GameState.INGAME)) &&
                (e.getClickedBlock().getType() == Material.TRAPPED_CHEST)) {
            if (!blockPlacedLoc.contains(e.getClickedBlock().getLocation())) {
                if (!chests.containsKey(e.getClickedBlock().getLocation())) {
                    Chest chest = (Chest) e.getClickedBlock().getState();
                    Inventory chestInv = chest.getInventory();
                    List<ItemStack> loots = SyndicateGames.getInstance().getChestLoot().getPremiumLoot();
                    SyndicateGames.getInstance().getChestLoot().fillPremiumChest(chest);
                    chests.put(e.getClickedBlock().getLocation(), chestInv);
                }
            }
        }
    }

}
