package eu.imposdev.syndicategames.listener;

import eu.imposdev.syndicategames.SyndicateGames;
import eu.imposdev.syndicategames.api.LanguageManager;
import eu.imposdev.syndicategames.gamehandler.GameState;
import eu.imposdev.syndicategames.util.Utils;
import net.core.api.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
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
    public void onPlayerInteractAtChest(PlayerInteractEvent e) {
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

    @EventHandler
    public void onPlayerInteractWithItem(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction() == null) return;
        if (event.getItem() == null) return;
        if (event.getItem().equals(Utils.BACK_TO_LOBBY)) {
            event.setCancelled(true);
            player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 2, 2);
            player.sendMessage(Utils.PREFIX + LanguageManager.getMessage(Utils.LOCALE, "sendingToLobby"));
            player.kickPlayer(null);
        }
        if (event.getItem().equals(Utils.FORCE_MAP)) {
            Inventory inventory = Bukkit.createInventory(null, 9 * 3, LanguageManager.getMessage(Utils.LOCALE, "forceMap").replaceAll("&", "§"));
            for (int i = 0; i < SyndicateGames.getInstance().getMaps().size(); i++) {
                ItemStack itemStack = new ItemBuilder(Material.MAP).setName("§3" + SyndicateGames.getInstance().getMaps().get(i)).setAmount(i).build();
                inventory.addItem(itemStack);
            }
            event.setCancelled(true);
            player.openInventory(inventory);
            player.playSound(player.getLocation(), Sound.CHEST_OPEN, 0.5f, 0.5f);
        }
    }

}
