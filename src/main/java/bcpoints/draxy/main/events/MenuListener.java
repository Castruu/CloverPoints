package bcpoints.draxy.main.events;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class MenuListener implements Listener {

    @EventHandler
    public void onInteract(InventoryClickEvent e) {
        if(ChatColor.translateAlternateColorCodes('&', ChatColor.AQUA + "Squad Information").equals(e.getView().getTitle())) {
            e.setCancelled(true);
        }
    }
}
