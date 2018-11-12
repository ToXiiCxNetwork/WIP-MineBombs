package net.toxiic.bombs;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldguard.bukkit.BukkitUtil;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import javax.imageio.ImageIO;
import net.toxiic.bombs.commands.CmdMinebomb;
import net.toxiic.bombs.commands.CmdMinerocket;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.block.Block;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class Main
  extends JavaPlugin
{
  public static JavaPlugin pluginInstance;
  
  public static void main(String[] args)
  {
    System.out.println(Material.EMERALD_BLOCK.toString());
  }
  
  public void onEnable()
  {
    pluginInstance = this;
    
    getCommand("minebomb").setExecutor(new CmdMinebomb());
    getCommand("minerocket").setExecutor(new CmdMinerocket());
    
    getServer().getPluginManager().registerEvents(new PlayerListener(), this);
    
    Config.setupConfig();
  }
  
  public void onDisable()
  {
    getServer().getScheduler().cancelTasks(this);
    Config.noPickup.clear();
    Config.cooldown.clear();
    
    pluginInstance = null;
  }
  
  public static WorldGuardPlugin getWorldGuard()
  {
    Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
    if ((plugin == null) || (!(plugin instanceof WorldGuardPlugin))) {
      return null;
    }
    return (WorldGuardPlugin)plugin;
  }
  
  public static boolean isWithinRegion(Block block, String region)
  {
    return isWithinRegion(block.getLocation(), region);
  }
  
  public static boolean isWithinRegion(Location loc, String region)
  {
    WorldGuardPlugin worldGuard = getWorldGuard();
    if (worldGuard == null) {
      return true;
    }
    Vector v = BukkitUtil.toVector(loc);
    RegionManager manager = worldGuard.getRegionManager(loc.getWorld());
    ApplicableRegionSet set = manager.getApplicableRegions(v);
    for (ProtectedRegion each : set) {
      if (each.getId().equalsIgnoreCase(region)) {
        return true;
      }
    }
    return false;
  }

}
