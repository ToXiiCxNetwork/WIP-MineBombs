package net.toxiic.bombs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Config
{
  public static HashMap<Player, Arrow> arrows = new HashMap();
  public static List<UUID> noPickup = new ArrayList();
  public static HashMap<UUID, Long> cooldown = new HashMap();
  public static HashMap<UUID, Long> rcooldown = new HashMap();
  
  public static void setupConfig()
  {
    List<String> list = new ArrayList();
    list.add("amine");
    
    List<String> list2 = new ArrayList();
    list2.add("STONE");
    list2.add("COAL_ORE");
    list2.add("IRON_ORE");
    list2.add("COAL_BLOCK");
    list2.add("IRON_BLOCK");
    list2.add("GOLD_ORE");
    list2.add("GOLD_BLOCK");
    list2.add("REDSTONE_BLOCK");
    list2.add("DIAMOND_ORE");
    list2.add("LAPIS_BLOCK");
    list2.add("DIAMOND_BLOCK");
    list2.add("EMERALD_ORE");
    list2.add("EMERALD_BLOCK");
    
    Main.pluginInstance.getConfig().options().header("MineBomb configuration");
    Main.pluginInstance.getConfig().addDefault("Main.regions", list);
    Main.pluginInstance.getConfig().addDefault("Main.multipliedblocks", list2);
    
    Main.pluginInstance.getConfig().addDefault("Main.cooldown", Integer.valueOf(5));
    Main.pluginInstance.getConfig().addDefault("Main.radius", Integer.valueOf(3));
    Main.pluginInstance.getConfig().addDefault("Main.bombitem.ID", "firework_charge");
    
    Main.pluginInstance.getConfig().addDefault("Main.bombitem.name", "&7&l>>> &a&lMineBomb &7&l<<<");
    
    Main.pluginInstance.getConfig().addDefault("Main.minDrops", Integer.valueOf(5));
    Main.pluginInstance.getConfig().addDefault("Main.maxDrops", Integer.valueOf(10));
    
    Main.pluginInstance.getConfig().addDefault("Main.prefix", "&7[&aToXiiCxBombs&7]");
    Main.pluginInstance.getConfig().addDefault("Main.cooldownmsg", "&cYou must wait a total of %time% seconds inbetween using a minebomb!");
    Main.pluginInstance.getConfig().addDefault("Main.usedmsg", "&aYou've thrown a Minebomb! You must wait %cooldown% seconds to throw another one!");
    Main.pluginInstance.getConfig().addDefault("Main.regionmsg", "&cYou are not permitted to use a Minebomb in this area!");
    Main.pluginInstance.getConfig().addDefault("Main.givenmsg", "%prefix% &aYou received %amount% power %power% %type%!");
    Main.pluginInstance.getConfig().addDefault("Main.invfull", "%prefix% &aYour %type% has been dropped on the ground, because your inventory was full!");
    
    Main.pluginInstance.getConfig().addDefault("Main.nopermmsg", "&cYou do not have permission to do this!");
    Main.pluginInstance.getConfig().addDefault("Main.reloadconfigmsg", "&aYou have reloaded Minebombs configuration!");
    Main.pluginInstance.getConfig().addDefault("Main.addregionmsg", "&aYou have added the region %region%!");
    Main.pluginInstance.getConfig().addDefault("Main.removeregionmsg", "&aYou have removed the region %region%!");
    
    Main.pluginInstance.getConfig().addDefault("Main.rcooldownmsg", "&cYou must wait a total of %time% seconds inbetween using a MineRocket!");
    Main.pluginInstance.getConfig().addDefault("Main.rusedmsg", "&aYou've shot a MineRocket! You must wait %cooldown% seconds to throw another one!");
    Main.pluginInstance.getConfig().addDefault("Main.rregionmsg", "&cYou are not permitted to use a MineRocket in this area!");
    Main.pluginInstance.getConfig().addDefault("Main.rcooldown", Integer.valueOf(10));
    Main.pluginInstance.getConfig().addDefault("Main.rocketitem.ID", "fireworks");
    Main.pluginInstance.getConfig().addDefault("Main.rocketitem.name", "&b&l>>> &e&lMineRocket &b&l<<<");
    Main.pluginInstance.getConfig().addDefault("Main.rminDrops", Integer.valueOf(2));
    Main.pluginInstance.getConfig().addDefault("Main.rmaxDrops", Integer.valueOf(5));
    
    Main.pluginInstance.getConfig().addDefault("Main.rocketlife", Integer.valueOf(3000));
    
    Main.pluginInstance.getConfig().options().copyHeader(true);
    Main.pluginInstance.getConfig().options().copyDefaults(true);
    Main.pluginInstance.saveConfig();
  }
}
