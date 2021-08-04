package bcpoints.draxy.main.utils;

import org.bukkit.ChatColor;

public enum Squads {
    GOLDENDAWN(ChatColor.GOLD, "goldendawn", "Golden Dawn"),
    BLACKBULLS(ChatColor.DARK_GRAY, "blackbulls", "Black Bulls"),
    SILVEREAGLE(ChatColor.GRAY, "silvereagle", "Silver Eagle"),
    BLUEROSE(ChatColor.BLUE, "bluerose", "Blue Rose"),
    CRIMSONLION(ChatColor.RED, "crimsonlion", "Crimson Lion"),
    GREENMANTIS(ChatColor.GREEN, "greenmantis", "Green Mantis"),
    CORALPEACOCK(ChatColor.LIGHT_PURPLE, "coralpeacock", "Coral Peacock"),
    PURPLEORCA(ChatColor.DARK_PURPLE, "purpleorca", "Purple Orca"),
    AZUREDEER(ChatColor.AQUA, "azuredeer", "Azure Deer");



    private String grupo;
    private String nome;
    private ChatColor color;
    Squads(ChatColor color, String grupo, String nome){
        this.grupo = grupo;
        this.color = color;
        this.nome = nome;
    }
    public String getGrupo() {
        return grupo;
    }

    public String getNome() {
        return nome;
    }

    public ChatColor getColor() {
        return color;
    }

}
