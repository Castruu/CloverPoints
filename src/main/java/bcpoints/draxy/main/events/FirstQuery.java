package bcpoints.draxy.main.events;

import bcpoints.draxy.main.CloverPoints;
import bcpoints.draxy.main.database.Connection;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FirstQuery implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Bukkit.getScheduler().runTaskAsynchronously(CloverPoints.getInstance(), () -> {
            try {
                PreparedStatement stm = Connection.con.prepareStatement("INSERT INTO player (uuid, points) VALUES (?, 10)");
                stm.setString(1, e.getPlayer().getUniqueId().toString());
                stm.execute();
                stm.close();
            } catch (SQLException x) {
                x.printStackTrace();
            }
        });

    }
}
