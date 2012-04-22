 package com.ptibiscuit.onechance.listeners;
 
 import com.ptibiscuit.framework.MyLogger;
 import com.ptibiscuit.onechance.OneChance;
 import com.ptibiscuit.onechance.data.DataHandler;
 import com.ptibiscuit.onechance.models.Stats;
 import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
 import org.bukkit.event.entity.EntityDeathEvent;
 
 public class EntityManager implements Listener
 {
	@EventHandler(priority = EventPriority.HIGHEST)
   public void onEntityDeath(EntityDeathEvent e)
   {
     if ((e.getEntity() instanceof Player))
     {
       Player p = (Player)e.getEntity();
       Stats stat = OneChance.getInstance().getDataHandler().getStats(p.getName());
       if (stat != null)
       {
         OneChance.getInstance().getDataHandler().updateStats(stat.getName(), stat.getLives() - 1);
         Stats newStat = new Stats(stat.getName(), stat.getLives() - 1);
         if (!OneChance.getInstance().checkPlayer(p, newStat))
         {
           p.kickPlayer(OneChance.getInstance().getSentence("kick_message"));
         }
       }
       else
       {
         OneChance.getInstance().getMyLogger().warning(p.getName() + " has not entry in Database, big problem ! :3");
       }
     }
   }
 }