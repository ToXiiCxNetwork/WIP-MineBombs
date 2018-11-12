package net.toxiic.bombs;

import java.util.List;

import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.toxiic.bombs.util.LegitMainUtil;
import net.toxiic.bombs.util.ParticleEffect;
import net.toxiic.bombs.util.ParticleEffect.ParticleType;

public class Task
{
  private Item theItem = null;
  private Player player = null;
  private int radius = 0;
  private long startTime = 0L;
  private int step = 0;
  
  public Task(Item item, Player player, int radius)
  {
    this.theItem = item;
    this.player = player;
    this.radius = radius;
    this.step = 0;
  }
  
  public Task startBomb()
  {
    this.step = 0;
    this.startTime = System.currentTimeMillis();
    new BukkitRunnable()
    {
      public void run()
      {
        if ((Task.this.step >= 4) || (Task.this.theItem == null))
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
        long timeElapsed = System.currentTimeMillis() - Task.this.startTime;
        if (Task.this.step == 0)
        {
          if (timeElapsed >= 1750L)
          {
            ParticleEffect p = new ParticleEffect(ParticleEffect.ParticleType.CLOUD, 0.10000000149011612D, 20, 0.2F, 0.2F, 0.2F);
            p.sendToLocation(Task.this.theItem.getLocation());
            Task.this.theItem.getWorld().playSound(Task.this.theItem.getLocation(), Sound.ENTITY_CREEPER_PRIMED, 1.0F, 1.0F);
            Task.this.startTime = System.currentTimeMillis();
            //Task.access$008(Task.this);

          }
        }
        else if (Task.this.step == 1)
        {
          if (timeElapsed >= 1500L)
          {
            ParticleEffect p = new ParticleEffect(ParticleEffect.ParticleType.CLOUD, 0.10000000149011612D, 30, 0.5F, 0.5F, 0.5F);
            p.sendToLocation(Task.this.theItem.getLocation());
            ParticleEffect px = new ParticleEffect(ParticleEffect.ParticleType.FIREWORKS_SPARK, 0.10000000149011612D, 20, 0.5F, 0.5F, 0.5F);
            px.sendToLocation(Task.this.theItem.getLocation());
            Task.this.theItem.getWorld().playSound(Task.this.theItem.getLocation(), Sound.ENTITY_CREEPER_PRIMED, 1.3F, 1.3F);
            Task.access$008(Task.this);
          }
        }
        else if (Task.this.step == 2)
        {
          if (timeElapsed >= 1250L)
          {
            ParticleEffect p = new ParticleEffect(ParticleEffect.ParticleType.CLOUD, 0.10000000149011612D, 40, 0.5F, 0.5F, 0.5F);
            p.sendToLocation(Task.this.theItem.getLocation());
            ParticleEffect p2 = new ParticleEffect(ParticleEffect.ParticleType.FIREWORKS_SPARK, 0.10000000149011612D, 30, 0.5F, 0.5F, 0.5F);
            p2.sendToLocation(Task.this.theItem.getLocation());
            ParticleEffect p3 = new ParticleEffect(ParticleEffect.ParticleType.SMOKE_LARGE, 0.10000000149011612D, 30, 0.5F, 0.5F, 0.5F);
            p3.sendToLocation(Task.this.theItem.getLocation());
            Task.this.theItem.getWorld().playSound(Task.this.theItem.getLocation(), Sound.ENTITY_CREEPER_PRIMED, 1.5F, 1.5F);
            Task.access$008(Task.this);
          }
        }
        else if ((Task.this.step == 3) && (timeElapsed >= 1000L))
        {
          ParticleEffect p = new ParticleEffect(ParticleEffect.ParticleType.CLOUD, 0.10000000149011612D, 40, 0.5F, 0.5F, 0.5F);
          p.sendToLocation(Task.this.theItem.getLocation());
          ParticleEffect p2 = new ParticleEffect(ParticleEffect.ParticleType.FIREWORKS_SPARK, 0.10000000149011612D, 30, 0.5F, 0.5F, 0.5F);
          p2.sendToLocation(Task.this.theItem.getLocation());
          ParticleEffect p3 = new ParticleEffect(ParticleEffect.ParticleType.SMOKE_LARGE, 0.10000000149011612D, 30, 0.5F, 0.5F, 0.5F);
          p3.sendToLocation(Task.this.theItem.getLocation());
          ParticleEffect p4 = new ParticleEffect(ParticleEffect.ParticleType.EXPLOSION_LARGE, 0.10000000149011612D, 40, 0.5F, 0.5F, 0.5F);
          p4.sendToLocation(Task.this.theItem.getLocation());
          Task.this.theItem.getWorld().playSound(Task.this.theItem.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 2.0F, 1.0F);
          Task.this.theItem.remove();
          if (Task.this.player != null) {
            LegitMainUtil.magic(Task.this.player, Task.this.theItem.getLocation(), Task.this.radius);
          }
          Config.noPickup.remove(Task.this.theItem.getUniqueId());
          
          Task.access$008(Task.this);
        }
        if (Task.this.step >= 4) {
          cancel();
        }
      }
    }.runTaskTimer(Main.pluginInstance, 5L, 5L);
    
    return this;
  }
  
  public Item getItem()
  {
    return this.theItem;
  }
  
  public Player getPlayer()
  {
    return this.player;
  }
  
  public int getRadius()
  {
    return this.radius;
  }
  
  public static Task startBomb(Item i, Player p, int radius)
  {
    return new Task(i, p, radius).startBomb();
  }
}
