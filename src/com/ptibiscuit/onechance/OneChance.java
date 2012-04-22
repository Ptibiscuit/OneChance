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
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
 
 public class OneChance extends JavaPluginEnhancer
 {
   private DataHandler data;
   private PlayerManager pm = new PlayerManager();
   private EntityManager em = new EntityManager();
   private static OneChance instance;
 
   public void onConfigurationDefault(FileConfiguration c)
   {
     c.set("bdd.host", "localhost");
     c.set("bdd.login", "root");
     c.set("bdd.password", "");
     c.set("bdd.database", "");
     c.set("config.lives.default", Integer.valueOf(1));
     c.set("config.buy_live.price", Integer.valueOf(500));
   }
 
   public void onLangDefault(Properties p)
   {
     p.setProperty("kick_message", "Votre âme s'en va dans les limbes ...");
     p.setProperty("cant_do", "Vous ne pouvez pas faire a");
     p.setProperty("boy_confirm", "Vous avez acheter une vie pour {MONEY} !");
     p.setProperty("cant_afford", "Vous n'avez pas assez d'argent.");
   }
 
   public void onDisable()
   {
   }
 
   public void onEnable()
   {
     instance = this;
     setup(ChatColor.RED + "[OneChance]", "onechance", true);
     this.myLog.startFrame();
     this.myLog.addInFrame(getDescription().getFullName() + " by Ptibiscuit");
     this.myLog.addCompleteLineInFrame();
 
     FileConfiguration c = getConfig();
 
     this.data = new MysqlDataHandler(c.getConfigurationSection("bdd"));
 
     if (this.data.checkActivity())
     {
       this.myLog.addInFrame("Data loaded !", true);
     }
     else
     {
       this.myLog.addInFrame("Failed to load data.", false);
     }
     if (setupIconomy())
     {
       this.myLog.addInFrame("iConomy loaded !", true);
     }
     else
     {
       this.myLog.addInFrame("Cant load iConomy, that's not important, but ... ='(", false);
     }
 
     PluginManager pgm = getServer().getPluginManager();
     pgm.registerEvents(this.em, this);
	  pgm.registerEvents(this.pm, this);
 
     this.myLog.displayFrame(false);
   }
 
   public boolean setupIconomy() {
     Plugin p = getServer().getPluginManager().getPlugin("iConomy");
 
     return p != null;
   }
 
   public DataHandler getDataHandler()
   {
     return this.data;
   }
 
   public static OneChance getInstance() {
     return instance;
   }
 
   public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
   {
     if (!(sender instanceof Player))
       return false;
     Player p = (Player)sender;
     if (label.equalsIgnoreCase("ocbuy"))
     {
       if (!this.permissionHandler.has(p, "buy", false))
       {
         sendPreMessage(sender, "cant_do");
         return true;
       }
       Holdings holdings = new Account(p.getName()).getHoldings();
       int price = getConfig().getInt("config.buy_live.price", 0);
       if (holdings.getBalance().doubleValue() >= price)
       {
         holdings.subtract(price);
         Stats stats = getDataHandler().getStats(p.getName());
         getDataHandler().updateStats(stats.getName(), stats.getLives() + 1);
         sendMessage(sender, getSentence("boy_confirm").replace("{MONEY}", String.valueOf(price)));
       }
       else
       {
         sendPreMessage(sender, "cant_afford");
       }
     }
     return true;
   }
 
   public boolean checkPlayer(Player p, Stats s)
   {
     return canPlay(s);
   }
 
   public boolean canPlay(Stats s)
   {
     return s.getLives() > 0;
   }
 }

/* Location:           C:\Users\ANNA\Documents\Frederic\netbeanspace\OneChance\dist\OneChance.jar
 * Qualified Name:     com.ptibiscuit.onechance.OneChance
 * JD-Core Version:    0.6.0
 */