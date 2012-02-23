/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ptibiscuit.onechance.listeners;

import com.ptibiscuit.onechance.OneChance;
import com.ptibiscuit.onechance.models.Stats;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.player.PlayerPreLoginEvent;

/**
 *
 * @author ANNA
 */
public class PlayerManager extends PlayerListener {
	
	@Override
	public void onPlayerLogin(PlayerLoginEvent e) {
		// On va d'abord créer les stats si celles-ci n'existent pas.
		Stats stat = OneChance.getInstance().getDataHandler().getStats(e.getPlayer().getName());
		if (stat == null)
		{
			// Si elles n'existent pas, on les crées.
			stat = OneChance.getInstance().getDataHandler().createStats(e.getPlayer().getName(), OneChance.getInstance().getConfiguration().getInt("config.lives.default", 1));
		}
		
		if (!OneChance.getInstance().checkPlayer(e.getPlayer(), stat))
		{
			e.setKickMessage(OneChance.getInstance().getSentence("kick_message"));
			e.setResult(Result.KICK_OTHER);
		}
	}
	
}
