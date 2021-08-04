package bcpoints.draxy.main.database;

import bcpoints.draxy.main.CloverPoints;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.io.File;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Connection {
    public static java.sql.Connection con = null;
    public Connection() {
        openSQLConnection();
    }
    File file = new File(CloverPoints.getInstance().getDataFolder(), "point.db");
    final String URL = "jdbc:sqlite:" + file;
    public void openSQLConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection(URL);
            createTable();
            System.out.println("-----------------------------------------\n" + ChatColor.DARK_BLUE + "SQLite Connection managed with success\n" + ChatColor.WHITE + " -----------------------------------");
        } catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
            System.out.println("The plugin disconnected for an error during connecting to SQLite Table");
            Bukkit.getPluginManager().disablePlugin(CloverPoints.getInstance());
        }
    }

    private void createTable() {
        try {
            PreparedStatement stm = con.prepareStatement("CREATE TABLE IF NOT EXISTS player (uuid CHAR(36), points INT)");
            stm.execute();
            stm.close();
        } catch (SQLException e) {
            e.printStackTrace();
            Bukkit.getPluginManager().disablePlugin(CloverPoints.getInstance());
        }

    }

    public static void close(){
        if(con != null) {
            try {
                con.close();
                System.out.println("Connection closed with DATABASE");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
