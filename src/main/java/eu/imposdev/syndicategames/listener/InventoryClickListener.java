package eu.imposdev.syndicategames.listener;

import eu.imposdev.syndicategames.SyndicateGames;
import eu.imposdev.syndicategames.api.LanguageManager;
import eu.imposdev.syndicategames.util.Utils;
import net.core.api.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onInventoryClickForceMap(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            if (event.getCurrentItem() == null) return;
            if (event.getClickedInventory() == null) return;
            if (event.getAction() == null) return;
            if (event.getInventory() == null) return;
            if (event.getClickedInventory().getTitle().equalsIgnoreCase(LanguageManager.getMessage(Utils.LOCALE, "forceMap").replaceAll("&", "§"))) {
                for (int i = 0; i < SyndicateGames.getInstance().getMaps().size(); i++) {
                    ItemStack itemStack = new ItemBuilder(Material.MAP).setName("§3" + SyndicateGames.getInstance().getMaps().get(i)).setAmount(i).build();
                    if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(itemStack.getItemMeta().getDisplayName())) {
                        Utils.MAP_NAME = SyndicateGames.getInstance().getMaps().get(i);
                        Utils.MAP_INT = i;
                        Bukkit.getOnlinePlayers().forEach(all -> {
                            SyndicateGames.getInstance().getScoreboardAPI().sendScoreboard(all);
                        });
                        player.closeInventory();
                        player.sendMessage(Utils.PREFIX + LanguageManager.getMessage(Utils.LOCALE, "mapChanged").replaceAll("%map%", Utils.MAP_NAME).replaceAll("&", "§"));
                        player.playSound(player.getLocation(), Sound.VILLAGER_YES, 0.75f, 0.75f);
                        return;
                    }
                }
            }
        }
    }

}
