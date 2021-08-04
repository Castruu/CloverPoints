package bcpoints.draxy.main.utils;

import bcpoints.draxy.main.CloverPoints;
import bcpoints.draxy.main.database.DatabaseUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class GetSquadPoint {
    DatabaseUtils utils = new DatabaseUtils();
    public int getSquadPoints(String grupo) {
        int soma = 0;
        for(OfflinePlayer player : Bukkit.getServer().getOfflinePlayers()) {
            if(player.hasPlayedBefore()) {
                if(CloverPoints.getInstance().perms.playerInGroup("world", player, grupo)) {
                    soma += utils.getPoints(player.getUniqueId());
                }
            }
        }
        return soma;
    }

    public HashMap<OfflinePlayer, Integer> getBestPlayers(String grupo) {
        HashMap<OfflinePlayer, Integer> bestPlayer = new HashMap<>();
        for(OfflinePlayer player : Bukkit.getServer().getOfflinePlayers()) {
            if(player.hasPlayedBefore()) {
                if(CloverPoints.getInstance().perms.playerInGroup("world", player, grupo)) {
                    bestPlayer.put(player, utils.getPoints(player.getUniqueId()));
                }
            }
        }
        return bestPlayer.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
    }

}
