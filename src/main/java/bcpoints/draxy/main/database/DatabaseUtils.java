package bcpoints.draxy.main.database;

import bcpoints.draxy.main.CloverPoints;
import bcpoints.draxy.main.utils.HologramCreator;
import bcpoints.draxy.main.utils.MenuConstructor;
import com.sun.corba.se.pept.transport.ConnectionCache;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class DatabaseUtils {

    public int getPoints(UUID uuid) {
       try {
           PreparedStatement stm = Connection.con.prepareStatement("SELECT points FROM player WHERE uuid = ?");
           stm.setString(1, uuid.toString());
           ResultSet set = stm.executeQuery();
           int points = set.getInt("points");
           stm.close();
           set.close();
           return points;
       } catch (SQLException e) {
           e.printStackTrace();
       }
       return 0;
    }

    public void setPoints(UUID player, int newpoints) {
        Bukkit.getScheduler().runTaskAsynchronously(CloverPoints.getInstance(), () -> {
            HologramCreator creator = new HologramCreator();
            try {
                PreparedStatement stm = Connection.con.prepareStatement("UPDATE player SET points = " + newpoints + " WHERE uuid = ?");
                stm.setString(1, player.toString());
                stm.executeUpdate();
                creator.setHologramRank();
                stm.close();
            }catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public void addPoints(UUID player, int points) {
        Bukkit.getScheduler().runTaskAsynchronously(CloverPoints.getInstance(), () -> {
            HologramCreator creator = new HologramCreator();
            int soma = points + getPoints(player);
            try {
                PreparedStatement stm = Connection.con.prepareStatement("UPDATE player SET points = ? WHERE uuid = ?");
                stm.setInt(1, soma);
                stm.setString(2, player.toString());
                stm.executeUpdate();
                creator.setHologramRank();
                stm.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }











}
