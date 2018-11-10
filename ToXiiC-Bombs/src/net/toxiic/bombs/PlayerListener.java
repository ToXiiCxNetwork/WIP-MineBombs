package net.toxiic.bombs;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

import net.toxiic.bombs.util.LegitMainUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.FireworkEffectMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

public class PlayerListener
  implements Listener
{
  @EventHandler
  public void onPlayerInteract(PlayerInteractEvent event)
  {
    Player p = event.getPlayer();
    if ((!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) && (!event.getAction().equals(Action.RIGHT_CLICK_AIR))) {
      return;
    }
    if (event.getItem() != null)
    {
      if (event.getItem().getType() == Material.getMaterial(Main.pluginInstance.getConfig().getString("Main.bombitem.ID")))
      {
        int ix = event.getItem().getDurability();
        if (ix == 0) {
          return;
        }
        boolean a = false;
        List<String> r = Main.pluginInstance.getConfig().getStringList("Main.regions");
        for (String s : r)
        {
          if (Main.isWithinRegion(p.getLocation().add(0.0D, -2.0D, 0.0D), s))
          {
            a = true;
            break;
          }
          if (Main.isWithinRegion(p.getLocation().add(0.0D, 2.0D, 0.0D), s))
          {
            a = true;
            break;
          }
        }
        Color c = Color.WHITE;
        try
        {
          c = (Color)((FireworkEffectMeta)p.getItemInHand().getItemMeta()).getEffect().getColors().get(0);
        }
        catch (NullPointerException e) {}
        if (!a)
        {
          p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.pluginInstance.getConfig().getString("Main.prefix") + " " + Main.pluginInstance.getConfig().getString("Main.regionmsg")));
          return;
        }
        if (Config.cooldown.containsKey(p.getUniqueId())) {
          if (System.currentTimeMillis() - ((Long)Config.cooldown.get(p.getUniqueId())).longValue() > Main.pluginInstance.getConfig().getLong("Main.cooldown") * 1000L)
          {
            Config.cooldown.remove(p.getUniqueId());
          }
          else
          {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.pluginInstance.getConfig().getString("Main.prefix") + " " + Main.pluginInstance.getConfig().getString("Main.cooldownmsg").replace("%time%", new StringBuilder().append("").append(Main.pluginInstance.getConfig().getLong("Main.cooldown")).toString())));
            return;
          }
        }
        if ((p.getGameMode().equals(GameMode.SURVIVAL)) || (p.getGameMode().equals(GameMode.ADVENTURE))) {
          if (event.getItem().getAmount() <= 1) {
            p.getInventory().setItemInHand(new ItemStack(Material.AIR));
          } else {
            p.getInventory().getItemInHand().setAmount(p.getInventory().getItemInHand().getAmount() - 1);
          }
        }
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.pluginInstance.getConfig().getString("Main.prefix") + " " + Main.pluginInstance.getConfig().getString("Main.usedmsg").replace("%cooldown%", new StringBuilder().append("").append(Main.pluginInstance.getConfig().getLong("Main.cooldown")).toString())));
        Config.cooldown.put(p.getUniqueId(), Long.valueOf(System.currentTimeMillis()));
        if (ix != 0) {
          LegitMainUtil.throwItem(p, ix > 9 ? ix / 10 : Main.pluginInstance.getConfig().getInt("Main.radius"), c);
        }
      }
      if (event.getItem().getType() == Material.getMaterial(Main.pluginInstance.getConfig().getString("Main.rocketitem.ID")))
      {
        int ix = event.getItem().getDurability();
        if (ix == 0) {
          return;
        }
        event.setCancelled(true);
        
        boolean a = false;
        List<String> r = Main.pluginInstance.getConfig().getStringList("Main.regions");
        for (String s : r)
        {
          if (Main.isWithinRegion(p.getLocation().add(0.0D, -2.0D, 0.0D), s))
          {
            a = true;
            break;
          }
          if (Main.isWithinRegion(p.getLocation().add(0.0D, 2.0D, 0.0D), s))
          {
            a = true;
            break;
          }
        }
        if (!a)
        {
          p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.pluginInstance.getConfig().getString("Main.prefix") + " " + Main.pluginInstance.getConfig().getString("Main.rregionmsg")));
          return;
        }
        if (Config.rcooldown.containsKey(p.getUniqueId())) {
          if (System.currentTimeMillis() - ((Long)Config.rcooldown.get(p.getUniqueId())).longValue() > Main.pluginInstance.getConfig().getLong("Main.rcooldown") * 1000L)
          {
            Config.rcooldown.remove(p.getUniqueId());
          }
          else
          {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.pluginInstance.getConfig().getString("Main.prefix") + " " + Main.pluginInstance.getConfig().getString("Main.rcooldownmsg").replace("%time%", new StringBuilder().append("").append(Main.pluginInstance.getConfig().getLong("Main.rcooldown")).toString())));
            return;
          }
        }
        if ((p.getGameMode().equals(GameMode.SURVIVAL)) || (p.getGameMode().equals(GameMode.ADVENTURE))) {
          if (event.getItem().getAmount() <= 1) {
            p.getInventory().setItemInHand(new ItemStack(Material.AIR));
          } else {
            p.getInventory().getItemInHand().setAmount(p.getInventory().getItemInHand().getAmount() - 1);
          }
        }
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.pluginInstance.getConfig().getString("Main.prefix") + " " + Main.pluginInstance.getConfig().getString("Main.rusedmsg").replace("%cooldown%", new StringBuilder().append("").append(Main.pluginInstance.getConfig().getLong("Main.rcooldown")).toString())));
        Config.rcooldown.put(p.getUniqueId(), Long.valueOf(System.currentTimeMillis()));
        
        LegitMainUtil.throwRocket(p, ix > 9 ? ix / 10 : Main.pluginInstance.getConfig().getInt("Main.radius"));
      }
    }
  }
  
  @EventHandler
  public void onPlayerPickupItem(PlayerPickupItemEvent event)
  {
    if (Config.noPickup.contains(event.getItem().getUniqueId())) {
      event.setCancelled(true);
    }
  }
  
  @EventHandler
  public void onItemDespawn(ItemDespawnEvent event)
  {
    if (Config.noPickup.contains(event.getEntity().getUniqueId())) {
      event.setCancelled(true);
    }
  }
  
  @EventHandler(priority=EventPriority.HIGHEST)
  public void onArrowLand(ProjectileHitEvent event)
  {
    if (Config.noPickup.contains(event.getEntity().getUniqueId()))
    {
      Location vLoc = new Location(event.getEntity().getWorld(), 0.0D, 0.0D, 0.0D);
      vLoc.setYaw(randf(0, 360));
      vLoc.setPitch(randf(-45, 0));
      event.getEntity().setVelocity(vLoc.getDirection());
    }
  }
  
  @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=false)
  public void onProjectileHitEvent(ProjectileHitEvent event)
  {
    Projectile entity = event.getEntity();
    
    ProjectileSource shooter = entity.getShooter();
    if (((entity instanceof Projectile)) && (entity.hasMetadata("bouncing")) && (Config.noPickup.contains(entity.getUniqueId())))
    {
      Vector arrowVector = entity.getVelocity();
      
      double magnitude = Math.sqrt(Math.pow(arrowVector.getX(), 2.0D) + Math.pow(arrowVector.getY(), 2.0D) + Math.pow(arrowVector.getZ(), 2.0D));
      
      Location hitLoc = entity.getLocation();
      
      BlockIterator b = new BlockIterator(hitLoc.getWorld(), hitLoc.toVector(), arrowVector, 0.0D, 3);
      
      Block hitBlock = event.getEntity().getLocation().getBlock();
      
      boolean a = false;
      List<String> r = Main.pluginInstance.getConfig().getStringList("Main.regions");
      for (String s : r) {
        if (Main.isWithinRegion(hitBlock, s))
        {
          a = true;
          break;
        }
      }
      if ((!Main.getWorldGuard().canBuild((Player)shooter, hitBlock.getLocation())) || (!a)) {
        return;
      }
      Block blockBefore = hitBlock;
      Block nextBlock = b.next();
      while ((b.hasNext()) && (nextBlock.getType() == Material.AIR))
      {
        blockBefore = nextBlock;
        nextBlock = b.next();
      }
      BlockFace blockFace = nextBlock.getFace(blockBefore);
      if (blockFace != null)
      {
        if (blockFace == BlockFace.SELF) {
          blockFace = BlockFace.UP;
        }
        Vector hitPlain = new Vector(blockFace.getModX(), blockFace.getModY(), blockFace.getModZ());
        
        double dotProduct = arrowVector.dot(hitPlain);
        Vector u = hitPlain.multiply(dotProduct).multiply(2.0D);
        
        float speed = (float)magnitude;
        speed *= 1.0F;
        Projectile newProjectile;
        Projectile newProjectile1;
        if (entity.getType() == EntityType.ARROW)
        {
          newProjectile1 = entity.getWorld().spawnArrow(entity.getLocation(), arrowVector.subtract(u), speed, 1.0F);
        }
        else
        {
          newProjectile1 = (Projectile)entity.getWorld().spawnEntity(entity.getLocation(), entity.getType());
          newProjectile1.setVelocity(arrowVector.subtract(u).normalize().multiply(speed));
        }
        newProjectile1.setMetadata("bouncing", new FixedMetadataValue(Main.pluginInstance, Integer.valueOf(1)));
        
        Config.arrows.remove(shooter);
        Config.arrows.put((Player)shooter, (Arrow)newProjectile1);
        Config.noPickup.remove(entity.getUniqueId());
        Config.noPickup.add(newProjectile1.getUniqueId());
        
        newProjectile1.setShooter(shooter);
        newProjectile1.setFireTicks(entity.getFireTicks());
        
        entity.remove();
      }
    }
  }
  
  @EventHandler(priority=EventPriority.HIGHEST)
  public void hopper(InventoryPickupItemEvent e)
  {
    if (Config.noPickup.contains(e.getItem().getUniqueId())) {
      e.setCancelled(true);
    }
  }
  
  public float randf(int min, int max)
  {
    return min + new Random().nextInt(max - min);
  }
}
