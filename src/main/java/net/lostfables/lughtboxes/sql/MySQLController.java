package net.lostfables.lughtboxes.sql;

import net.lostfables.lughtboxes.Lughtbox;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;


import java.sql.*;
import java.util.UUID;

public class MySQLController implements Listener  {

    private Lughtbox plugin = Lughtbox.getPlugin(Lughtbox.class);


    public MySQLController() {

    }




    public void mysqlSetup() {
        try {
            plugin.getServer().getPluginManager().registerEvents(this, plugin);
            plugin.setHost(plugin.getConfig().getString("host"));
            plugin.setPort(plugin.getConfig().getInt("port"));
            plugin.setDatabase(plugin.getConfig().getString("database"));
            plugin.setUsername(plugin.getConfig().getString("username"));
            plugin.setPassword(plugin.getConfig().getString("password"));
            plugin.setTable(plugin.getConfig().getString("table"));
            plugin.getLogger().info(ChatColor.GREEN + "Successfully pulled information from config.yml");
            if(openConnection()) {
                PreparedStatement create = plugin.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS " + plugin.getTable() + "(UUID varchar(36), COINS INT, PREMCOINS INT, TOTALVOTES INT, RECCURENTVOTES INT, PRIMARY KEY(UUID))");
                create.executeUpdate();
            }



        } catch (Exception e) {
            plugin.getLogger().info("Exception while setting up mySQL.");
            e.printStackTrace();
        }
    }

    public boolean openConnection() {
        try {
            synchronized (this) {

                if (plugin.getConnection() != null && !plugin.getConnection().isClosed()) {
                    return true;
                }

                Class.forName("com.mysql.jdbc.Driver");
                plugin.setConnection(DriverManager.getConnection("jdbc:mysql://" + plugin.getHost() + ":" + plugin.getPort() + "/" + plugin.getDatabase(), plugin.getUsername(), plugin.getPassword()));

                plugin.getLogger().info(ChatColor.GREEN + "mySQL Connected");

                return true;


            }

        } catch(Exception e){
            plugin.getLogger().info(ChatColor.DARK_RED + "Could not connect to SQL");
            return false;
        }
    }


}
