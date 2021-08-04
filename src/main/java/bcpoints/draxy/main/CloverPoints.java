package bcpoints.draxy.main;

import bcpoints.draxy.main.commands.ChangePoints;
import bcpoints.draxy.main.commands.CreateHologram;
import bcpoints.draxy.main.commands.OpenMenu;
import bcpoints.draxy.main.database.Connection;
import bcpoints.draxy.main.database.DataManager;
import bcpoints.draxy.main.events.FirstQuery;
import bcpoints.draxy.main.events.MenuListener;
import bcpoints.draxy.main.utils.HologramCreator;
import bcpoints.draxy.main.utils.MenuConstructor;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class CloverPoints extends JavaPlugin {
    Connection con;
    public Permission perms = null;
    private DataManager data;
    @Override
    public void onEnable() {
        con = new Connection();
        data = new DataManager(this);
        saveDefaultConfig();
        setupPermissions();
        Bukkit.getPluginManager().registerEvents(new FirstQuery(), this);
        Bukkit.getPluginManager().registerEvents(new MenuListener(), this);
        getCommand("squadinfo").setExecutor(new OpenMenu());
        getCommand("points").setExecutor(new ChangePoints());
        getCommand("pointholo").setExecutor(new CreateHologram());
        loadRankStand();

    }

    @Override
    public void onDisable() {
        Connection.close();
        getArmorStandList().forEach(Entity::remove);
    }

    public HashMap<ArmorStand, String> getArmorStandMap() {
        return armorStandMap;
    }

    public List<ArmorStand> getArmorStandList() {
        return armorStandList;
    }

    private HashMap<ArmorStand, String> armorStandMap = new HashMap<>();

    public HashMap<String, List<ArmorStand>> getArmorStandNumber() {
        return armorStandNumber;
    }

    private HashMap<String, List<ArmorStand>> armorStandNumber = new HashMap<>();
    private List<ArmorStand> armorStandList = new ArrayList<>();

    public FileConfiguration getData() {
        return data.getConfig();
    }
    public void saveData(){
        data.saveConfig();
    }
    public static CloverPoints getInstance() {return CloverPoints.getPlugin(CloverPoints.class);}
    public void setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = Bukkit.getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
    }

    HologramCreator creator = new HologramCreator();
    public void loadRankStand(){
        FileConfiguration file = data.getConfig();
        if(!data.getConfig().getConfigurationSection("ranking").getKeys(true).isEmpty())
            data.getConfig().getConfigurationSection("ranking").getKeys(false).forEach(key -> {
                ArrayList<ArmorStand> standlist = new ArrayList<>();
                if(!data.getConfig().getConfigurationSection("ranking").getConfigurationSection(key).getKeys(true).isEmpty())
                    data.getConfig().getConfigurationSection("ranking").getConfigurationSection(key).getKeys(false).forEach(stand -> {
                        Location location = new Location(Bukkit.getWorld(file.getString("ranking." + key + "." + stand + ".world")),
                                file.getDouble("ranking." + key + "." + stand + ".x"), file.getDouble("ranking." + key + "." + stand + ".y"), file.getDouble("ranking." + key + "." + stand + ".z"));
                        String name = file.getString("ranking." + key + "." + stand + ".name");
                        String grupo = file.getString("ranking." + key + "." + stand + ".group");
                        standlist.add((ArmorStand) Bukkit.getEntity(creator.loadEcoHolo(location, name, grupo)));
                    });
                getArmorStandNumber().put(key, standlist);
            });

    }

}
