/*    */ package com.ptibiscuit.onechance.data;
/*    */ 
/*    */ import com.ptibiscuit.framework.mysql.mysqlCore;
/*    */ import com.ptibiscuit.onechance.models.Stats;
/*    */ import java.net.MalformedURLException;
/*    */ import java.sql.ResultSet;
/*    */ import java.util.logging.Level;
/*    */ import java.util.logging.Logger;
import org.bukkit.configuration.ConfigurationSection;
/*    */ 
/*    */ public class MysqlDataHandler
/*    */   implements DataHandler
/*    */ {
/*    */   private mysqlCore mysql;
/*    */ 
/*    */   public MysqlDataHandler(ConfigurationSection c)
/*    */   {
/*    */     try
/*    */     {
/* 26 */       this.mysql = new mysqlCore(Logger.getLogger("Minecraft"), "OneChance", c.getString("host"), c.getString("database"), c.getString("login"), c.getString("password"));
/* 27 */       this.mysql.initialize();
/* 28 */       if (this.mysql.checkConnection().booleanValue())
/* 29 */         this.mysql.createTable("CREATE TABLE IF NOT EXISTS `oc_players` (`player_name` varchar(255) NOT NULL,`player_log_bought_lives` int(11) NOT NULL DEFAULT '0',`player_count_lives` int(11) NOT NULL,`player_id` int(11) NOT NULL AUTO_INCREMENT,`is_staff` tinyint(1) DEFAULT '0', PRIMARY KEY (`player_id`)) ENGINE=InnoDB  DEFAULT CHARSET=latin1;");
/*    */     } catch (MalformedURLException ex) {
/* 31 */       Logger.getLogger(MysqlDataHandler.class.getName()).log(Level.SEVERE, null, ex);
/*    */     } catch (InstantiationException ex) {
/* 33 */       Logger.getLogger(MysqlDataHandler.class.getName()).log(Level.SEVERE, null, ex);
/*    */     } catch (IllegalAccessException ex) {
/* 35 */       Logger.getLogger(MysqlDataHandler.class.getName()).log(Level.SEVERE, null, ex);
/*    */     }
/*    */   }
/*    */ 
/*    */   public Stats createStats(String name, int lives)
/*    */   {
/*    */     try {
/* 42 */       if (this.mysql.checkConnection().booleanValue())
/* 43 */         this.mysql.insertQuery("INSERT INTO oc_players(player_name, player_count_lives) VALUES(\"" + name + "\", \"" + lives + "\");");
/*    */       else
/* 45 */         return null;
/* 46 */       return new Stats(name, lives);
/*    */     } catch (Exception ex) {
/* 48 */       Logger.getLogger(MysqlDataHandler.class.getName()).log(Level.SEVERE, null, ex);
/*    */     }
/*    */ 
/* 51 */     return null;
/*    */   }
/*    */ 
/*    */   public void deleteStats(String name)
/*    */   {
/* 56 */     throw new UnsupportedOperationException("Not supported yet.");
/*    */   }
/*    */ 
/*    */   public void updateStats(String name, int lives)
/*    */   {
/*    */     try {
/* 62 */       if (this.mysql.checkConnection().booleanValue())
/* 63 */         this.mysql.insertQuery("UPDATE oc_players SET player_count_lives = \"" + lives + "\" WHERE player_name = \"" + name + "\";");
/*    */     } catch (Exception ex) {
/* 65 */       Logger.getLogger(MysqlDataHandler.class.getName()).log(Level.SEVERE, null, ex);
/*    */     }
/*    */   }
/*    */ 
/*    */   public Stats getStats(String name)
/*    */   {
/*    */     try
/*    */     {
/*    */       ResultSet rs;
/* 73 */       if (this.mysql.checkConnection().booleanValue())
/* 74 */         rs = this.mysql.sqlQuery("SELECT * FROM oc_players WHERE player_name = \"" + name + "\";");
/*    */       else
/* 76 */         return null;

/* 77 */       if (rs.next())
/*    */       {
/* 79 */         return new Stats(rs.getString("player_name"), rs.getInt("player_count_lives"));
/*    */       }
/* 81 */       return null;
/*    */     } catch (Exception ex) {
/* 83 */       Logger.getLogger(MysqlDataHandler.class.getName()).log(Level.SEVERE, null, ex);
/*    */     }
/* 85 */     return null;
/*    */   }
/*    */ 
/*    */   public boolean checkActivity()
/*    */   {
/*    */     try {
/* 91 */       return this.mysql.checkConnection().booleanValue();
/*    */     } catch (Exception ex) {
/* 93 */       Logger.getLogger(MysqlDataHandler.class.getName()).log(Level.SEVERE, null, ex);
/*    */     }
/* 95 */     return false;
/*    */   }
/*    */ }

/* Location:           C:\Users\ANNA\Documents\Frederic\netbeanspace\OneChance\dist\OneChance.jar
 * Qualified Name:     com.ptibiscuit.onechance.data.MysqlDataHandler
 * JD-Core Version:    0.6.0
 */