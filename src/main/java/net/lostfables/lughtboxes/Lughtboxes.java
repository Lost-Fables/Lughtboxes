package net.lostfables.lughtboxes;

import net.lostfables.lughtboxes.commands.Lughtbox;
import net.lostfables.lughtboxes.commands.Vote;
import net.lostfables.lughtboxes.events.AddingNewPlayerToSQLDatabase;
import net.lostfables.lughtboxes.util.MySQLController;
import net.lostfables.lughtboxes.util.LughUtil;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.util.List;


public final class Lughtboxes extends JavaPlugin {

    private static Lughtboxes instance;
    private MySQLController SQLControl;
    private Connection connection;
    private String host,database,username,password;
    private List<String> table;
    private int port;

    public static Lughtboxes get() {
        return instance;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info(LughUtil.chat("&2The Artificer begins his work. . ."));
        try {
            saveDefaultConfig();
            SQLControl = new MySQLController();
            SQLControl.mysqlSetup();
            new Vote();
            new Lughtbox();
            new AddingNewPlayerToSQLDatabase();
            getConnection().close();

        } catch(Exception e) {
            e.printStackTrace();
        }

        System.out.println(getTable());

        instance = this;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        try {
            connection.close();
            getLogger().info(LughUtil.chat("&2The Artificer closes shop for the day."));
        } catch(Exception e) {
            getLogger().info(LughUtil.chat("&4Error closing down shop!"));
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

    public List<String> getTable() {
        return table;
    }

    public void setTable(List<String> table) {
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
