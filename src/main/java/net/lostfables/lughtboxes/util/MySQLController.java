package net.lostfables.lughtboxes.util;

import net.lostfables.lughtboxes.Lughtboxes;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.event.Listener;



import java.sql.*;
import java.util.List;


public class MySQLController  {

    private Lughtboxes plugin = Lughtboxes.getPlugin(Lughtboxes.class);


    public MySQLController() {

    }




    public void mysqlSetup() {
        try {
            plugin.setHost(plugin.getConfig().getString("host"));
            plugin.setPort(plugin.getConfig().getInt("port"));
            plugin.setDatabase(plugin.getConfig().getString("database"));
            plugin.setUsername(plugin.getConfig().getString("username"));
            plugin.setPassword(plugin.getConfig().getString("password"));
            plugin.setTable((List<String>) plugin.getConfig().getList("table"));
            plugin.getLogger().info(ChatColor.GREEN + "Successfully pulled information from config.yml");
            if(openConnection()) {
                PreparedStatement create = plugin.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS " + plugin.getTable().get(0) + "(UUID varchar(36), COINS INT, PREMCOINS INT, TOTALVOTES INT, RECCURENTVOTES INT, PRIMARY KEY(UUID))");
                create.executeUpdate();
                create = plugin.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS " + plugin.getTable().get(1) + "(BOXNAME varchar(255), ITEMS MEDIUMTEXT, PRIMARY KEY(BOXNAME))");
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

                return true;


            }

        } catch(Exception e){
            plugin.getLogger().info(ChatColor.DARK_RED + "Could not connect to SQL");
            return false;
        }
    }


}
