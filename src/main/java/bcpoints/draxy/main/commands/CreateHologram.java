package bcpoints.draxy.main.commands;

import bcpoints.draxy.main.utils.HologramCreator;
import bcpoints.draxy.main.utils.MenuConstructor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreateHologram implements CommandExecutor {
    HologramCreator creator = new HologramCreator();
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!commandSender.isOp()) return false;
        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;
            creator.spawnRankingHologram(player);
        }
        return false;
    }
}
