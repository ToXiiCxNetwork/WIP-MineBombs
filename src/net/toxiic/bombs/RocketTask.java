package net.toxiic.bombs;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import net.toxiic.bombs.util.LegitMainUtil;
import net.toxiic.bombs.util.ParticleEffect;
import net.toxiic.bombs.util.ParticleEffect.ParticleType;

public class RocketTask
{
  private Arrow theArrow = null;
  private Player player = null;
  private int radius = 0;
  private long startTime = 0L;
  private UUID u = null;
  private Vector vec = null;
  
  public RocketTask(Arrow arrow, Player player, int radius)
  {
    this.theArrow = arrow;
    this.player = player;
    this.radius = radius;
    this.vec = arrow.getVelocity();
    this.u = this.theArrow.getUniqueId();
  }
  
  public RocketTask startBomb()
  {
    this.startTime = System.currentTimeMillis();
    new BukkitRunnable()
    {
      public void run()
      {
        if (RocketTask.this.theArrow == null)
        {
          try
          {
            cancel();
          }
          catch (Exception ex)
          {
            ex.printStackTrace();
          }
          return;
        }
        if ((Config.arrows.get(RocketTask.this.player) != null) && (!((Arrow)Config.arrows.get(RocketTask.this.player)).getUniqueId().equals(RocketTask.this.theArrow.getUniqueId()))) {
          RocketTask.this.theArrow = ((Arrow)Config.arrows.get(RocketTask.this.player));
        }
        ParticleEffect p = new ParticleEffect(ParticleEffect.ParticleType.CLOUD, 0.10000000149011612D, 20, 0.2F, 0.2F, 0.2F);
        p.sendToLocation(RocketTask.this.theArrow.getLocation());
        ParticleEffect p2 = new ParticleEffect(ParticleEffect.ParticleType.FIREWORKS_SPARK, 0.10000000149011612D, 15, 0.2F, 0.2F, 0.2F);
        p2.sendToLocation(RocketTask.this.theArrow.getLocation());
        ParticleEffect p3 = new ParticleEffect(ParticleEffect.ParticleType.SMOKE_LARGE, 0.10000000149011612D, 15, 0.2F, 0.2F, 0.2F);
        p3.sendToLocation(RocketTask.this.theArrow.getLocation());
        
        RocketTask.this.theArrow.getWorld().playSound(RocketTask.this.theArrow.getLocation(), Sound.ENTITY_CREEPER_PRIMED, 0.6F, 0.6F);
        
        LegitMainUtil.magic2(RocketTask.this.player, RocketTask.this.theArrow.getLocation(), (int)(RocketTask.this.radius * 1.5D));
        
        long timeElapsed = System.currentTimeMillis() - RocketTask.this.startTime;
        if (timeElapsed > Main.pluginInstance.getConfig().getLong("Main.rocketlife"))
        {
          RocketTask.this.theArrow.remove();
          if (Config.arrows.get(RocketTask.this.player) != null) {
            Config.arrows.remove(RocketTask.this.player);
          }
          Config.noPickup.remove(RocketTask.this.theArrow.getUniqueId());
          LegitMainUtil.magic(RocketTask.this.player, RocketTask.this.theArrow.getLocation(), RocketTask.this.radius * 2);
          RocketTask.this.theArrow.getWorld().playSound(RocketTask.this.theArrow.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 2.0F, 1.0F);
          cancel();
        }
      }
    }.runTaskTimer(Main.pluginInstance, 0L, 2L);
    
    return this;
  }
  
  public Arrow getArrow()
  {
    return this.theArrow;
  }
  
  public Player getPlayer()
  {
    return this.player;
  }
  
  public int getRadius()
  {
    return this.radius;
  }
  
  public static RocketTask startBomb(Arrow i, Player p, int radius)
  {
    return new RocketTask(i, p, radius).startBomb();
  }
}
