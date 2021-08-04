package bcpoints.draxy.main.commands;

import bcpoints.draxy.main.database.DatabaseUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChangePoints implements CommandExecutor {
    DatabaseUtils databaseUtils = new DatabaseUtils();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 0) {
            Player player = (Player) sender;
            sender.sendMessage(ChatColor.AQUA + "You have: " + ChatColor.RED + databaseUtils.getPoints(player.getUniqueId()) + ChatColor.AQUA + " points!");
        }
        else if(args.length == 3) {
            if(!sender.isOp()) return false;
            OfflinePlayer target = null;
            try {
                target = Bukkit.getServer().getOfflinePlayer(args[1]);
            } catch (Exception exception) {
                sender.sendMessage(ChatColor.RED + "Insert a valid player name!");
            }
            int soma = 0;
            try {
                soma = Integer.parseInt(args[2]);
                if(soma < 0) throw new Exception();
            } catch (Exception e) {
                sender.sendMessage("Insert a valid number!");
            }
            assert target != null;
                if(args[0].equalsIgnoreCase("add")) {
                    databaseUtils.addPoints(target.getUniqueId(), soma);
                } else if(args[0].equalsIgnoreCase("set")) {
                    databaseUtils.setPoints(target.getUniqueId(), soma);
            }
        }
        return false;
    }


}
