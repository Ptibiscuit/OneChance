/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ptibiscuit.onechance.listeners;

import com.ptibiscuit.onechance.OneChance;
import com.ptibiscuit.onechance.models.Stats;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityListener;

/**
 *
 * @author ANNA
 */
public class EntityManager extends EntityListener {

	@Override
	public void onEntityDeath(EntityDeathEvent e) {
		if (e.getEntity() instanceof Player)
		{
			Player p = (Player) e.getEntity();
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
