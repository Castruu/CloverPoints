package bcpoints.draxy.main.utils;
import bcpoints.draxy.main.CloverPoints;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

public class HologramCreator {
    GetSquadPoint point = new GetSquadPoint();

    public void spawnRankingHologram(Player player) {
        double height = 3.0;
        ArmorStand stands = (ArmorStand) player.getWorld().spawnEntity(player.getLocation().add(0, height, 0), EntityType.ARMOR_STAND);
        setupStand(stands, ChatColor.DARK_GREEN + "Squad Rankings");
        height-=0.3;
        int var = 1;
        int var2 = 1;
        List<ArmorStand> list = new ArrayList<>();
        if(CloverPoints.getInstance().getData().contains("ranking"))
            var = CloverPoints.getInstance().getData().getConfigurationSection("ranking").getKeys(false).size() + 1;
        for(String nome : getPointsRank()) {
            ArmorStand stand = (ArmorStand) player.getWorld().spawnEntity(player.getLocation().add(0, height, 0), EntityType.ARMOR_STAND);
            setupStand(stand, nome);
            CloverPoints.getInstance().getArmorStandList().add(stand);
            CloverPoints.getInstance().getArmorStandMap().put(stand, getPointsRankMap().get(nome));
            list.add(stand);
                if(var2 != 1)
                var2 = CloverPoints.getInstance().getData().getConfigurationSection("ranking").getConfigurationSection("" + var).getKeys(false).size() + 1;
                CloverPoints.getInstance().getData().set("ranking." + var + "." + var2 + ".x", player.getLocation().getX());
                CloverPoints.getInstance().getData().set("ranking." + var + "." + var2 + ".y", player.getLocation().getY() + height);
                CloverPoints.getInstance().getData().set("ranking." + var + "." + var2 + ".z", player.getLocation().getZ());
                CloverPoints.getInstance().getData().set("ranking." + var + "." + var2 + ".world", player.getLocation().getWorld().getName());
                CloverPoints.getInstance().getData().set("ranking." + var + "." + var2 + ".name", nome);
                CloverPoints.getInstance().getData().set("ranking." + var + "." + var2 + ".group", getPointsRankMap().get(nome));
                CloverPoints.getInstance().saveData();
                CloverPoints.getInstance().getArmorStandNumber().put(String.valueOf(var), list);
                if(var2 == 1) var2 = 2;

            height-=0.3;
        }
    }

    public void setHologramRank() {
        Bukkit.getScheduler().runTaskAsynchronously(CloverPoints.getInstance(), () -> {
            CloverPoints.getInstance().getArmorStandNumber().forEach((integer, armorStands) -> {
                CloverPoints.getInstance().getData().getConfigurationSection("ranking").getConfigurationSection(integer).getKeys(false).forEach(s -> {
                    int i = 0;
                    for (ArmorStand armorStand : armorStands) {
                        CloverPoints.getInstance().getData().set("ranking." + integer + "." + (i + 1) + ".name", new ArrayList<>(getPointsRank()).get(i));
                        CloverPoints.getInstance().getData().set("ranking." + integer + "." + (i + 1) + ".group", getPointsRankMap().get(new ArrayList<>(getPointsRank()).get(i)));
                        armorStand.setCustomName(ChatColor.translateAlternateColorCodes('&', CloverPoints.getInstance().getData().getString("ranking." + integer + "." + (i + 1) + ".name")));
                        i++;
                    }
                });
            });
            CloverPoints.getInstance().saveData();
        });
    }

    public UUID loadEcoHolo(Location location, String name, String grupo) {
        ArmorStand stand = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        setupStand(stand, name);
        CloverPoints.getInstance().getArmorStandList().add(stand);
        CloverPoints.getInstance().getArmorStandMap().put(stand, grupo);
        stand.getUniqueId();
        return stand.getUniqueId();
    }

    
    private void setupStand(ArmorStand stand, String name) {
        stand.setVisible(false);
        stand.setGravity(false);
        stand.setInvulnerable(true);
        stand.setCustomNameVisible(true);
        stand.setCustomName(name);
    }
    private Set<String> getPointsRank() {
        HashMap<String, Integer> keeping = new HashMap<>();
        for(Squads squads : Squads.values()) {
            keeping.put(getBestPlayerInSquad(squads), point.getSquadPoints(squads.getGrupo()));
        }
        HashMap<String, Integer> sortedRoutes;
        sortedRoutes = keeping.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
        return sortedRoutes.keySet();
    }
    private String getBestPlayerInSquad(Squads squads) {
        String name = squads.getColor() + squads.getNome();
            List<OfflinePlayer> list = new ArrayList<>(point.getBestPlayers(squads.getGrupo()).keySet());
            try {
                name = squads.getColor() + squads.getNome() + " -> " +  list.get(0).getName() + " Points:" + point.utils.getPoints(list.get(0).getUniqueId());
            }  catch (Exception e) {}
            getGroup.put(name, squads.getGrupo());
        return name;
    }

    private HashMap<String, String> getPointsRankMap() {
        HashMap<String, String> ecoarray = new HashMap<>();
        for(Squads squads : Squads.values()) {
            ecoarray.put(getBestPlayerInSquad(squads), squads.getGrupo());
        }
        return ecoarray;
    }

    private HashMap<String, String> getGroup = new HashMap<>();
    private HashMap<String, String> standGroup = new HashMap<>();
}
