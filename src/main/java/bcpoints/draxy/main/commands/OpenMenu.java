package bcpoints.draxy.main.commands;

import bcpoints.draxy.main.utils.MenuConstructor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class OpenMenu implements CommandExecutor {
    MenuConstructor menu = new MenuConstructor();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            menu.menuConstructor(player);
        }
        return false;
    }
}
