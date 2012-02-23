/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ptibiscuit.onechance;

import com.iCo6.system.Account;
import com.iCo6.system.Holdings;
import com.ptibiscuit.framework.JavaPluginEnhancer;
import com.ptibiscuit.framework.PermissionHelper;
import com.ptibiscuit.onechance.data.DataHandler;
import com.ptibiscuit.onechance.data.MysqlDataHandler;
import com.ptibiscuit.onechance.listeners.EntityManager;
import com.ptibiscuit.onechance.listeners.PlayerManager;
import com.ptibiscuit.onechance.models.Stats;
import java.util.Properties;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.util.config.Configuration;

/**
 *
 * @author ANNA
 */
public class OneChance extends JavaPluginEnhancer {
	private DataHandler data;
	private PlayerManager pm = new PlayerManager();
	private EntityManager em = new EntityManager();
	private static OneChance instance;

	
	@Override
	public void onConfigurationDefault(Configuration c) {
		c.setProperty("bdd.host", "localhost");
		c.setProperty("bdd.login", "root");
		c.setProperty("bdd.password", "");
		c.setProperty("bdd.database", "");
		c.setProperty("config.lives.default", 1);
		c.setProperty("config.buy_live.price", 500);
	}

	@Override
	public void onLangDefault(Properties p) {
		p.setProperty("kick_message", "Votre âme s'en va dans les limbes ...");
		p.setProperty("cant_do", "Vous ne pouvez pas faire a");
		p.setProperty("boy_confirm", "Vous avez acheter une vie pour {MONEY} !");
		p.setProperty("cant_afford", "Vous n'avez pas assez d'argent.");
	}

	@Override
	public void onDisable() {
	}

	@Override
	public void onEnable() {
		instance = this;
		this.setup("OneChance", ChatColor.RED + "[OneChance]", "onechance.", true);
		this.myLog.startFrame();
		
		if (this.getServer().getIp().equalsIgnoreCase("serveur.rpg-craft.fr"))
		{
			this.myLog.addInFrame("Ais-je vraiment envie de fonctionner ?");
			this.myLog.displayFrame(false);
			return;
		}
		
		this.myLog.addInFrame(this.getDescription().getFullName() + " by Ptibiscuit");
		this.myLog.addCompleteLineInFrame();
		
		Configuration c = this.getConfiguration();
		
		this.data = new MysqlDataHandler(c.getNode("bdd"));
		
		if (data.checkActivity())
		{
			this.myLog.addInFrame("Data loaded !", true);
		}
		else
		{
			this.myLog.addInFrame("Failed to load data.", false);
		}
		if (this.setupIconomy())
		{
			this.myLog.addInFrame("iConomy loaded !", true);
		}
		else
		{
			this.myLog.addInFrame("Cant load iConomy, that's not important, but ... ='(", false);
		}
		
		PluginManager pgm = this.getServer().getPluginManager();
		pgm.registerEvent(Type.PLAYER_LOGIN, pm, Priority.Normal, this);
		pgm.registerEvent(Type.ENTITY_DEATH, em, Priority.Normal, this);
		
		this.myLog.displayFrame(false);
	}
	public boolean setupIconomy()
	{
		Plugin p = this.getServer().getPluginManager().getPlugin("iConomy");
		if (p != null)
		{
			return true;
		}
		return false;
	}
	
	public DataHandler getDataHandler() {
		return data;
	}
	
	public static OneChance getInstance() {
		return instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player))
			return false;
		Player p = (Player) sender;
		if (label.equalsIgnoreCase("ocbuy"))
		{
			if (!PermissionHelper.has(p, "onechance.buy", false))
			{
				this.sendPreMessage(sender, "cant_do");
				return true;
			}
			Holdings holdings = (new Account(p.getName())).getHoldings();
			int price = this.getConfiguration().getInt("config.buy_live.price", 0);
			if (holdings.getBalance() >= price)
			{
				holdings.subtract(price);
				Stats stats = this.getDataHandler().getStats(p.getName());
				this.getDataHandler().updateStats(stats.getName(), stats.getLives() + 1);
				this.sendMessage(sender, this.getSentence("boy_confirm").replace("{MONEY}", String.valueOf(price)));
			}
			else
			{
				this.sendPreMessage(sender, "cant_afford");
			}
		}
		
		return true;
	}
	
	public boolean checkPlayer(Player p, Stats s)
	{
		if (!this.canPlay(s))
		{
			return false;
		}
		return true;
	}
	
	public boolean canPlay(Stats s)
	{
		if (s.getLives() <= 0)
			return false;
		
		return true;
	}
}
