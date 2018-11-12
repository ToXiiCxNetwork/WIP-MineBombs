package net.toxiic.bombs.commands;

import java.util.List;

import net.toxiic.bombs.Main;
import net.toxiic.bombs.util.LegitMainUtil;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class CmdMinerocket
  implements CommandExecutor
{
  public boolean onCommand(CommandSender cs, Command cmd, String commandLabel, String[] args)
  {
    if (cmd.getName().equalsIgnoreCase("minerocket"))
    {
      if (!(cs instanceof Player))
      {
        int amount;
        int power;
        if (args.length == 4)
        {
          if (args[0].equalsIgnoreCase("give"))
          {
            amount = 0;
            power = 0;
            try
            {
              amount = Integer.parseInt(args[2]);
              power = Integer.parseInt(args[3]);
            }
            catch (NumberFormatException ex)
            {
              getUsageGive(cs);
              return true;
            }
            Player targetPlayer = Bukkit.getPlayer(args[1]);
            if (targetPlayer != null) {
              LegitMainUtil.giveMinerocket(amount, power, new Player[] { targetPlayer });
            } else if ((args[1].equals("*")) || (args[1].equals("**"))) {
              for (World w : Bukkit.getWorlds())
              {
                List<Player> worldPlayers = w.getPlayers();
                LegitMainUtil.giveMinerocket(amount, power, (Player[])worldPlayers.toArray(new Player[worldPlayers.size()]));
              }
            }
          }
        }
        else {
          cs.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&lToXiiCBombs plugin, &aCreated by ToXiiC)"));
        }
        return true;
      }
      Player p = (Player)cs;
      if (!cs.hasPermission("minebomb.admin"))
      {
        return true;
      }
      if (args.length == 0) {
      } else if (args.length == 1)
      {
        if (args[0].equalsIgnoreCase("reload"))
        {
          Main.pluginInstance.reloadConfig();
          cs.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.pluginInstance.getConfig().getString("Main.prefix") + " " + Main.pluginInstance.getConfig().getString("Main.reloadconfigmsg")));
        }
        else
        {
          getUsage(cs, cmd);
        }
      }
      else if (args.length == 2)
      {
        if (args[0].equalsIgnoreCase("addregion"))
        {
          List<String> r = Main.pluginInstance.getConfig().getStringList("Main.regions");
          r.add(args[1]);
          Main.pluginInstance.getConfig().set("Main.regions", r);
          Main.pluginInstance.saveConfig();
          
          cs.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.pluginInstance.getConfig().getString("Main.prefix") + " " + Main.pluginInstance.getConfig().getString("Main.addregionmsg").replace("%region%", args[1])));
        }
        else if (args[0].equalsIgnoreCase("removeregion"))
        {
          List<String> r = Main.pluginInstance.getConfig().getStringList("Main.regions");
          r.remove(args[1]);
          Main.pluginInstance.getConfig().set("Main.regions", r);
          Main.pluginInstance.saveConfig();
          
          cs.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.pluginInstance.getConfig().getString("Main.prefix") + " " + Main.pluginInstance.getConfig().getString("Main.removeregionmsg").replace("%region%", args[1])));
        }
        else
        {
          getUsage(cs, cmd);
        }
      }
      else if (args.length == 4)
      {
        if (args[0].equalsIgnoreCase("give"))
        {
          int amount = 0;
          int power = 0;
          try
          {
            amount = Integer.parseInt(args[2]);
            power = Integer.parseInt(args[3]);
          }
          catch (NumberFormatException ex)
          {
            getUsageGive(cs);
            return true;
          }
          Player targetPlayer = Bukkit.getPlayer(args[1]);
          if (targetPlayer != null) {
            LegitMainUtil.giveMinerocket(amount, power, new Player[] { targetPlayer });
          } else if ((args[1].equals("*")) || (args[1].equals("**"))) {
            for (World w : Bukkit.getWorlds())
            {
              List<Player> worldPlayers = w.getPlayers();
              LegitMainUtil.giveMinerocket(amount, power, (Player[])worldPlayers.toArray(new Player[worldPlayers.size()]));
            }
          } else {
            getUsageGive(cs);
          }
        }
        else
        {
          getUsage(cs, cmd);
        }
      }
      else {
        getUsage(cs, cmd);
      }
      return true;
    }
    return false;
  }
  
  private static void getUsage(CommandSender sender, Command cmd)
  {
    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cUsage: /" + cmd.getName() + " addregion [region name]"));
    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cUsage: /" + cmd.getName() + " removeregion [region name]"));
    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cUsage:  /" + cmd.getName() + " give [player / *] [amount] [power]"));
    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cUsage: /" + cmd.getName() + " reload"));
  }
  
  private static void getUsageGive(CommandSender cs)
  {
    cs.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cUsage:  /mb give [player / *] [amount] [power]"));
  }
}
