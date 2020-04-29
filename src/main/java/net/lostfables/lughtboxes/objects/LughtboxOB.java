package net.lostfables.lughtboxes.objects;

import co.lotc.core.bukkit.util.InventoryUtil;
import net.lostfables.lughtboxes.Lughtboxes;
import net.lostfables.lughtboxes.commands.Lughtbox;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.inventory.ItemStack;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class LughtboxOB {

//TODO Finish up LughtboxOB

    private String name;
    private ItemStack[] items;
    private static Lughtboxes plugin = Lughtboxes.getPlugin(Lughtboxes.class);

    public LughtboxOB(String boxName) {
        name = boxName.toUpperCase();
        items = new ItemStack[0];
        try {
            createLughtbox(this);
        } catch(SQLException e) {
            e.printStackTrace();
        }

    }

    public LughtboxOB(String boxName, ItemStack...items) {
        name = boxName.toUpperCase();
        this.items = items;
        try {
            createLughtbox(this);
        } catch(SQLException e) {

        }
    }

    public boolean lughtboxExists(LughtboxOB box) throws SQLException {
        PreparedStatement statement = plugin.getConnection().prepareStatement("SELECT * FROM " + plugin.getTable().get(1) + " WHERE BOXNAME=?");
        statement.setString(1, box.getName());

        ResultSet results = statement.executeQuery();
        if (results.next()) {
            //If lughtbox is found
            return true;
        }

        //If lughtbox is not found
        return false;
    }

    public void createLughtbox(LughtboxOB box) throws SQLException {
        if(!plugin.getSQLControl().openConnection()) {
            return;
        }

        PreparedStatement statement = plugin.getConnection().prepareStatement("SELECT * FROM " + plugin.getTable().get(1) + " WHERE BOXNAME=?");
        statement.setString(1, box.getName());

        ResultSet results = statement.executeQuery();
        results.next();
        if (!lughtboxExists(box)) {
            PreparedStatement insert = plugin.getConnection().prepareStatement("INSERT INTO " + plugin.getTable().get(1) + " (BOXNAME,ITEMS) VALUES (?,?)");
            insert.setString(1, box.getName());
            insert.setString(2, InventoryUtil.serializeItems(box.getItems()));
            insert.executeUpdate();

            plugin.getLogger().info(ChatColor.GREEN + "New LughtboxOB has been inserted into " + plugin.getTable().get(1) + " table.");
        }
        plugin.getConnection().close();
    }

    public static boolean addItemToLughtBox(LughtboxOB box, ItemStack item) throws SQLException {

        List<ItemStack> itemList = InventoryUtil.deserializeItems(box.getItemsFromSQL());
        itemList.add(item);
        box.setItemsToSQL(box, InventoryUtil.serializeItems(itemList));


        return false;
    }

    public String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    public ItemStack[] getItems() {
        return items;
    }

    protected void setItems(ItemStack[] items) {
        this.items = items;
    }

    public String getItemsFromSQL() throws SQLException {
        if(!plugin.getSQLControl().openConnection()) {
            return null;
        }

        PreparedStatement statement = plugin.getConnection().prepareStatement("SELECT ITEMS FROM " + plugin.getTable().get(1) + " WHERE BOXNAME=?");
        statement.setString(1, getName());
        ResultSet adder = statement.executeQuery();
        adder.next();
        String answer = adder.getString(1);

        plugin.getConnection().close();
        return answer;
    }

    private static void setItemsToSQL(LughtboxOB box, String itemsToSQL) throws SQLException {
        if(!plugin.getSQLControl().openConnection()) {
            return;
        }
        PreparedStatement statement = plugin.getConnection().prepareStatement("UPDATE " + plugin.getTable().get(1) + " SET ITEMS=? WHERE BOXNAME=?");
        statement.setString(1, itemsToSQL);
        statement.setString(2, box.getName());
        statement.executeUpdate();
        plugin.getConnection().close();
    }


}
