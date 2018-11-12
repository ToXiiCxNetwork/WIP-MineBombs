package net.toxiic.bombs.util;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MiscDisguise;
import net.toxiic.bombs.Config;
import net.toxiic.bombs.Main;
import net.toxiic.bombs.RocketTask;
import net.toxiic.bombs.Task;

import org.apache.commons.lang.math.RandomUtils;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Builder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.FireworkEffectMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class LegitMainUtil
{
  private static final Random random = new Random();
  
  public static void throwItem(Player p, int r, Color c)
  {
    if (p == null) {
      return;
    }
    Item dropped = p.getLocation().getWorld().dropItem(p.getLocation(), new ItemStack(Material.getMaterial(Main.pluginInstance.getConfig().getString("Main.bombitem.ID"))));
    FireworkEffectMeta i = (FireworkEffectMeta)dropped.getItemStack().getItemMeta();
    FireworkEffect.Builder builder = FireworkEffect.builder();
    builder.withColor(c);
    i.setEffect(builder.build());
    i.setDisplayName("" + RandomUtils.nextInt(9999));
    dropped.getItemStack().setItemMeta(i);
    
    dropped.setVelocity(p.getLocation().clone().add(0.0D, 1.5D, 0.0D).getDirection().normalize());
    Config.noPickup.add(dropped.getUniqueId());
    Task.startBomb(dropped, p, r);
  }
  
  public static void throwRocket(Player p, int r)
  {
    if (p == null) {
      return;
    }
    Arrow a = p.getWorld().spawnArrow(p.getEyeLocation().add(0.0D, 1.0D, 0.0D), p.getLocation().clone().add(0.0D, 2.0D, 0.0D).getDirection().normalize(), 1.0F, 0.0F);
    a.setMetadata("bouncing", new FixedMetadataValue(Main.pluginInstance, Integer.valueOf(1)));
    a.setShooter(p);
    MiscDisguise miscDisguise = new MiscDisguise(DisguiseType.DROPPED_ITEM, 401);
    Config.noPickup.add(a.getUniqueId());
    DisguiseAPI.disguiseToAll(a, miscDisguise);
    RocketTask.startBomb(a, p, r);
  }
  
  public static void magic(Player p, Location loc, int radius)
  {
    if ((p == null) || (loc == null) || (loc.getWorld() == null)) {
      return;
    }
    if (radius < 0) {
      radius = 0;
    }
    for (int y = -radius; y <= radius; y++) {
      for (int x = -radius; x <= radius; x++) {
        for (int z = -radius; z <= radius; z++)
        {
          Location nLoc = new Location(loc.getWorld(), loc.getBlockX() + x, loc.getBlockY() + y, loc.getBlockZ() + z);
          if (nLoc.distance(loc) < radius)
          {
            Block block = loc.getWorld().getBlockAt(nLoc);
            
            boolean a = false;
            List<String> r = Main.pluginInstance.getConfig().getStringList("Main.regions");
            for (String s : r) {
              if (Main.isWithinRegion(block, s))
              {
                a = true;
                break;
              }
            }
            if ((Main.getWorldGuard().canBuild(p, nLoc)) && (a) && 
              (block.getType() != Material.AIR))
            {
              boolean multiply = false;
              for (String s : Main.pluginInstance.getConfig().getStringList("Main.multipliedblocks")) {
                if (s.equalsIgnoreCase(block.getType().toString()))
                {
                  multiply = true;
                  break;
                }
              }
              if (multiply)
              {
                ItemStack i = new ItemStack(block.getType(), randInt(Main.pluginInstance.getConfig().getInt("Main.minDrops"), Main.pluginInstance.getConfig().getInt("Main.maxDrops")), (short)block.getData());
                if (!p.getInventory().addItem(new ItemStack[] { i }).isEmpty()) {
                  p.getLocation().getWorld().dropItem(block.getLocation(), i);
                }
                block.setType(Material.AIR);
                ParticleEffect px = new ParticleEffect(ParticleEffect.ParticleType.CLOUD, 0.10000000149011612D, 5, 0.2F, 0.2F, 0.2F);
                px.sendToLocation(block.getLocation());
                ParticleEffect p2 = new ParticleEffect(ParticleEffect.ParticleType.FIREWORKS_SPARK, 0.10000000149011612D, 3, 0.2F, 0.2F, 0.2F);
                p2.sendToLocation(block.getLocation());
                ParticleEffect p3 = new ParticleEffect(ParticleEffect.ParticleType.SMOKE_LARGE, 0.10000000149011612D, 3, 0.2F, 0.2F, 0.2F);
                p3.sendToLocation(block.getLocation());
              }
              else
              {
                ItemStack i = new ItemStack(block.getType(), 1, (short)block.getData());
                if (!p.getInventory().addItem(new ItemStack[] { i }).isEmpty()) {
                  p.getLocation().getWorld().dropItem(block.getLocation(), i);
                }
                block.setType(Material.AIR);
                ParticleEffect px = new ParticleEffect(ParticleEffect.ParticleType.CLOUD, 0.10000000149011612D, 5, 0.2F, 0.2F, 0.2F);
                px.sendToLocation(block.getLocation());
                ParticleEffect p2 = new ParticleEffect(ParticleEffect.ParticleType.FIREWORKS_SPARK, 0.10000000149011612D, 3, 0.2F, 0.2F, 0.2F);
                p2.sendToLocation(block.getLocation());
                ParticleEffect p3 = new ParticleEffect(ParticleEffect.ParticleType.SMOKE_LARGE, 0.10000000149011612D, 3, 0.2F, 0.2F, 0.2F);
                p3.sendToLocation(block.getLocation());
              }
            }
          }
        }
      }
    }
  }
  
  public static void magic2(Player p, Location loc, int radius)
  {
    if ((p == null) || (loc == null) || (loc.getWorld() == null)) {
      return;
    }
    if (radius < 0) {
      radius = 0;
    }
    for (int y = -radius; y <= radius; y++) {
      for (int x = -radius; x <= radius; x++) {
        for (int z = -radius; z <= radius; z++)
        {
          Location nLoc = new Location(loc.getWorld(), loc.getBlockX() + x, loc.getBlockY() + y, loc.getBlockZ() + z);
          if (nLoc.distance(loc) < radius)
          {
            Block block = loc.getWorld().getBlockAt(nLoc);
            
            boolean a = false;
            List<String> r = Main.pluginInstance.getConfig().getStringList("Main.regions");
            for (String s : r) {
              if (Main.isWithinRegion(block, s))
              {
                a = true;
                break;
              }
            }
            if ((Main.getWorldGuard().canBuild(p, nLoc)) && (a) && 
              (block.getType() != Material.AIR))
            {
              boolean multiply = false;
              for (String s : Main.pluginInstance.getConfig().getStringList("Main.multipliedblocks")) {
                if (s.equalsIgnoreCase(block.getType().toString()))
                {
                  multiply = true;
                  break;
                }
              }
              if (multiply)
              {
                ItemStack i = new ItemStack(block.getType(), randInt(Main.pluginInstance.getConfig().getInt("Main.rminDrops"), Main.pluginInstance.getConfig().getInt("Main.rmaxDrops")) / 2, (short)block.getData());
                if (!p.getInventory().addItem(new ItemStack[] { i }).isEmpty()) {
                  p.getLocation().getWorld().dropItem(block.getLocation(), i);
                }
                block.setType(Material.AIR);
              }
              else
              {
                ItemStack i = new ItemStack(block.getType(), 1, (short)block.getData());
                if (!p.getInventory().addItem(new ItemStack[] { i }).isEmpty()) {
                  p.getLocation().getWorld().dropItem(block.getLocation(), i);
                }
                block.setType(Material.AIR);
              }
            }
          }
        }
      }
    }
  }
  
  public static int randInt(int min, int max)
  {
    return random.nextInt(max - min + 1) + min;
  }
  
  public static void giveMinebomb(int a, int p, Player... pl)
  {
    ItemStack i = new ItemStack(Material.getMaterial(Main.pluginInstance.getConfig().getString("Main.bombitem.ID")));
    ItemMeta im = i.getItemMeta();
    im.setDisplayName(ChatColor.translateAlternateColorCodes('&', Main.pluginInstance.getConfig().getString("Main.bombitem.name").replace("%power%", "" + p)));
    i.setItemMeta(im);
    i.setDurability((short)(10 * p));
    i.setAmount(a);
    FireworkEffectMeta newmeta = (FireworkEffectMeta)i.getItemMeta();
    i.setItemMeta(giveMeta(newmeta));
    for (Player player : pl)
    {
      HashMap<Integer, ItemStack> nope = player.getInventory().addItem(new ItemStack[] { i });
      for (Map.Entry<Integer, ItemStack> entry : nope.entrySet())
      {
        player.getWorld().dropItemNaturally(player.getLocation(), (ItemStack)entry.getValue());
        player.getInventory().remove(i);
        player.getLocation().getWorld().dropItem(player.getLocation(), i);
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.pluginInstance.getConfig().getString("Main.invfull").replace("%prefix%", Main.pluginInstance.getConfig().getString("Main.prefix")).replace("%type%", "MineBomb")));
      }
      player.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.pluginInstance.getConfig().getString("Main.givenmsg").replace("%prefix%", Main.pluginInstance.getConfig().getString("Main.prefix")).replace("%amount%", "" + a).replace("%power%", "" + p).replace("%type%", "MineBomb")));
    }
  }
  
  public static void giveMinerocket(int a, int p, Player... pl)
  {
    ItemStack i = new ItemStack(Material.getMaterial(Main.pluginInstance.getConfig().getString("Main.rocketitem.ID")));
    ItemMeta im = i.getItemMeta();
    im.setDisplayName(ChatColor.translateAlternateColorCodes('&', Main.pluginInstance.getConfig().getString("Main.rocketitem.name").replace("%power%", "" + p)));
    i.setItemMeta(im);
    i.setDurability((short)(10 * p));
    i.setAmount(a);
    for (Player player : pl)
    {
      HashMap<Integer, ItemStack> nope = player.getInventory().addItem(new ItemStack[] { i });
      for (Map.Entry<Integer, ItemStack> entry : nope.entrySet())
      {
        player.getWorld().dropItemNaturally(player.getLocation(), (ItemStack)entry.getValue());
        player.getInventory().remove(i);
        player.getLocation().getWorld().dropItem(player.getLocation(), i);
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.pluginInstance.getConfig().getString("Main.invfull").replace("%prefix%", Main.pluginInstance.getConfig().getString("Main.prefix")).replace("%type%", "MineRocket")));
      }
      player.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.pluginInstance.getConfig().getString("Main.givenmsg").replace("%prefix%", Main.pluginInstance.getConfig().getString("Main.prefix")).replace("%amount%", "" + a).replace("%power%", "" + p).replace("%type%", "MineRocket")));
    }
  }
  
  public static FireworkEffectMeta giveMeta(FireworkEffectMeta input)
  {
    FireworkEffect.Builder builder = FireworkEffect.builder();
    
    ArrayList<Color> colors = new ArrayList();
    colors.add(Color.WHITE);colors.add(Color.PURPLE);colors.add(Color.RED);colors.add(Color.GREEN);colors.add(Color.AQUA);
    colors.add(Color.BLUE);colors.add(Color.FUCHSIA);colors.add(Color.GRAY);colors.add(Color.LIME);colors.add(Color.MAROON);
    colors.add(Color.YELLOW);colors.add(Color.SILVER);colors.add(Color.TEAL);colors.add(Color.ORANGE);colors.add(Color.OLIVE);
    colors.add(Color.NAVY);colors.add(Color.BLACK);
    
    builder.withColor((Color)colors.get(RandomUtils.nextInt(colors.size())));
    
    FireworkEffectMeta data = input;
    data.setEffect(builder.build());
    
    return data;
  }
}
