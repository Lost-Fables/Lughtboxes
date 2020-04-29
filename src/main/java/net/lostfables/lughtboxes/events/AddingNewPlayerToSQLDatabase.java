package net.lostfables.lughtboxes.events;

import net.lostfables.lughtboxes.Lughtboxes;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class AddingNewPlayerToSQLDatabase implements Listener {

    Lughtboxes plugin = Lughtboxes.getPlugin(Lughtboxes.class);

    public AddingNewPlayerToSQLDatabase() {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        try {
            createPlayer(event.getPlayer().getUniqueId());
        } catch(SQLException sqle) {
            sqle.printStackTrace();
        }




    }

    public boolean playerExists(UUID uuid) throws SQLException {

        PreparedStatement statement = plugin.getConnection().prepareStatement("SELECT * FROM " + plugin.getTable().get(0) + " WHERE UUID=?");
        statement.setString(1, uuid.toString());

        ResultSet results = statement.executeQuery();
        if (results.next()) {
            //If player is found
            return true;
        }

        //If player is not found
        return false;
    }

    public void createPlayer(final UUID uuid) throws SQLException {
        if(!plugin.getSQLControl().openConnection()) {
            return;
        }
        PreparedStatement statement = plugin.getConnection().prepareStatement("SELECT * FROM " + plugin.getTable().get(0) + " WHERE UUID=?");
        statement.setString(1, uuid.toString());

        ResultSet results = statement.executeQuery();
        results.next();
        if (!(playerExists(uuid))) {
            PreparedStatement insert = plugin.getConnection().prepareStatement("INSERT INTO " + plugin.getTable().get(0) + " (UUID,COINS,PREMCOINS,TOTALVOTES,RECCURENTVOTES) VALUES (?,?,?,?,?)");
            insert.setString(1, uuid.toString());
            insert.setInt(2, 0);
            insert.setInt(3, 0);
            insert.setInt(4, 0);
            insert.setInt(5,0);
            insert.executeUpdate();

            plugin.getLogger().info(ChatColor.GREEN + "New player has been inserted into " + plugin.getTable().get(0) + " table.");
        }
        plugin.getConnection().close();
    }
}
