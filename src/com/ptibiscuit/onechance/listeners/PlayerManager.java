 package com.ptibiscuit.onechance.listeners;
 
 import com.ptibiscuit.onechance.OneChance;
 import com.ptibiscuit.onechance.data.DataHandler;
 import com.ptibiscuit.onechance.models.Stats;
 import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
 import org.bukkit.event.player.PlayerLoginEvent;
 
 public class PlayerManager implements Listener
 {
	@EventHandler(priority = EventPriority.LOWEST)
   public void onPlayerLogin(PlayerLoginEvent e)
   {
     Stats stat = OneChance.getInstance().getDataHandler().getStats(e.getPlayer().getName());
     if (stat == null)
     {
       stat = OneChance.getInstance().getDataHandler().createStats(e.getPlayer().getName(), OneChance.getInstance().getConfig().getInt("config.lives.default", 1));
     }
 
     if (!OneChance.getInstance().checkPlayer(e.getPlayer(), stat))
     {
       e.setKickMessage(OneChance.getInstance().getSentence("kick_message"));
       e.setResult(PlayerLoginEvent.Result.KICK_OTHER);
     }
   }
 }