/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ptibiscuit.onechance.data;

import com.ptibiscuit.framework.mysql.mysqlCore;
import com.ptibiscuit.onechance.models.Stats;
import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.util.config.ConfigurationNode;

/**
 *
 * @author ANNA
 */
public class MysqlDataHandler implements DataHandler {

	private mysqlCore mysql;
	
	public MysqlDataHandler(ConfigurationNode c)
	{
		try {
			this.mysql = new mysqlCore(Logger.getLogger("Minecraft"), "OneChance", c.getString("host"), c.getString("database"), c.getString("login"), c.getString("password"));
			this.mysql.initialize();
			if (this.mysql.checkConnection())
				this.mysql.createTable("CREATE TABLE IF NOT EXISTS `oc_players` (`player_name` varchar(255) NOT NULL,`player_log_bought_lives` int(11) NOT NULL DEFAULT '0',`player_count_lives` int(11) NOT NULL,`player_id` int(11) NOT NULL AUTO_INCREMENT,`is_staff` tinyint(1) DEFAULT '0', PRIMARY KEY (`player_id`)) ENGINE=InnoDB  DEFAULT CHARSET=latin1;");
		} catch (MalformedURLException ex) {
			Logger.getLogger(MysqlDataHandler.class.getName()).log(Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			Logger.getLogger(MysqlDataHandler.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			Logger.getLogger(MysqlDataHandler.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	@Override
	public Stats createStats(String name, int lives) {
		try {
			if (this.mysql.checkConnection())
				this.mysql.insertQuery("INSERT INTO oc_players(player_name, player_count_lives) VALUES(\"" + name + "\", \"" + lives + "\");");
			else
				return null;
			return new Stats(name, lives);
		} catch (Exception ex) {
			Logger.getLogger(MysqlDataHandler.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		return null;
	}

	@Override
	public void deleteStats(String name) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void updateStats(String name, int lives) {
		try {
			if (this.mysql.checkConnection())
				this.mysql.insertQuery("UPDATE oc_players SET player_count_lives = \"" + lives + "\" WHERE player_name = \"" + name + "\";");
		} catch (Exception ex) {
			Logger.getLogger(MysqlDataHandler.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@Override
	public Stats getStats(String name) {
		try {
			ResultSet rs;
			if (this.mysql.checkConnection())
				rs = this.mysql.sqlQuery("SELECT * FROM oc_players WHERE player_name = \"" + name + "\";");
			else
				return null;
			if (rs.next())
			{
				return new Stats(rs.getString("player_name"), rs.getInt("player_count_lives"));
			}
			return null;
		} catch (Exception ex) {
			Logger.getLogger(MysqlDataHandler.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}

	@Override
	public boolean checkActivity() {
		try {
			return this.mysql.checkConnection();
		} catch (Exception ex) {
			Logger.getLogger(MysqlDataHandler.class.getName()).log(Level.SEVERE, null, ex);
		}
		return false;
	}
	
}
