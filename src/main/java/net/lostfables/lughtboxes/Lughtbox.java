package net.lostfables.lughtboxes;

import net.lostfables.lughtboxes.commands.NewMenu;
import net.lostfables.lughtboxes.commands.Vote;
import net.lostfables.lughtboxes.util.MySQLController;
import net.lostfables.lughtboxes.util.Utils;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;


public final class Lughtbox extends JavaPlugin {

    private MySQLController SQLControl;
    private Vote vote;
    private Connection connection;
    private String host,database,username,password,table;
    private int port;


    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info(Utils.chat("&2The Artificer begins his work. . ."));
        try {
            saveDefaultConfig();
            vote = new Vote();
            SQLControl = new MySQLController();
            SQLControl.mysqlSetup();
            getCommand("newmenu").setExecutor(new NewMenu());
        } catch(Exception e) {
            e.printStackTrace();
        }



    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        try {
            connection.close();
            getLogger().info(Utils.chat("&2The Artificer closes shop for the day."));
        } catch(Exception e) {
            getLogger().info(Utils.chat("&4Error closing down shop!"));
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public MySQLController getSQLControl() {
        return SQLControl;
    }










}