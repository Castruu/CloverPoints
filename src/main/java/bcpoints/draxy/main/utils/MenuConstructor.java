package bcpoints.draxy.main.utils;

import bcpoints.draxy.main.CloverPoints;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class MenuConstructor {
    GetSquadPoint point = new GetSquadPoint();
    public void menuConstructor(Player player) {
        Squads squad = getSquad(player);
        if (squad == null) {
            player.sendMessage(ChatColor.RED + "You do not have a Squad!");
            return;
        }
        Inventory inventory = Bukkit.createInventory(null, 27, ChatColor.AQUA + "Squad Information");
        String name = ChatColor.GREEN + "Squad Information";
        String sname = ChatColor.RED + "Squad: " + squad.getColor() + squad.getNome();
        for (int i = 0; i < 9; i++) {
            ItemStack glasspanel = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 14).name(" ").build();
            if (inventory.getContents()[i] == null) {
                inventory.setItem(i, glasspanel);
                inventory.setItem(i + 9, glasspanel);
            }
        }
        Bukkit.getScheduler().runTaskAsynchronously(CloverPoints.getInstance(), () -> {
            int ppoints = point.utils.getPoints(player.getUniqueId());
            ItemStack skull =  new ItemBuilder(Material.SKULL_ITEM).skullOwner(player.getName()).name(ChatColor.RED + player.getName()).lore(ChatColor.BLUE + "Personal Points: " + ChatColor.DARK_AQUA + "" + ppoints).build();
            inventory.setItem(4, skull);
            int points = point.getSquadPoints(squad.getGrupo());
            HashMap<OfflinePlayer, Integer> players = point.getBestPlayers(squad.getGrupo());;
            List<String[]> playersList = new ArrayList<>();
            ItemStack book = new ItemBuilder(Material.BOOK).name(name).lore(sname, ChatColor.DARK_RED + "Squad Points: " + ChatColor.DARK_AQUA + "" + points).build();
            inventory.setItem(13, book);
            players.forEach((offlinePlayer, integer) -> playersList.add(new String[]{offlinePlayer.getName(), "" + ChatColor.RED + "Points: " + ChatColor.GRAY + integer}));
            for (int i = 0; i < 9; i++) {
                ItemStack stack = null;
                try {
                    String nome = ChatColor.AQUA + "Name: " + ChatColor.LIGHT_PURPLE + playersList.get(i)[0];
                   stack = new ItemBuilder(Material.SKULL_ITEM).skullOwner(playersList.get(i)[0]).name(ChatColor.GOLD + "Top " + (i+1)).lore(nome, playersList.get(i)[1]).build();
                } catch (Exception ignored) {}
                if(stack != null)
                inventory.setItem(i + 18, stack);
            }
        });
        player.openInventory(inventory);
    }

    private Squads getSquad(Player player) {
        for(Squads squads : Squads.values()) {
            if (CloverPoints.getInstance().perms.playerInGroup(player, squads.getGrupo())) {
                return squads;
            }
        }
        return null;
    }


}
