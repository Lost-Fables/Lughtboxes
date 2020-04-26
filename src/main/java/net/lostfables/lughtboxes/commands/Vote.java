package net.lostfables.lughtboxes.commands;

import co.lotc.core.bukkit.menu.Menu;
import co.lotc.core.bukkit.menu.MenuAction;
import co.lotc.core.bukkit.menu.MenuAgent;
import co.lotc.core.bukkit.menu.icon.Button;
import co.lotc.core.bukkit.menu.icon.Icon;
import co.lotc.core.bukkit.util.ItemUtil;
import co.lotc.core.util.TimeUtil;
import net.lostfables.lughtboxes.Lughtbox;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Vote implements CommandExecutor {

    private Lughtbox plugin = Lughtbox.getPlugin(Lughtbox.class);

    public Vote() {
        plugin.getCommand("vote").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player && args.length == 0) {




        } else if(args[1].equalsIgnoreCase("set") || args[1].equalsIgnoreCase("remove") || args[1].equalsIgnoreCase("add")) {

            try {
                if(plugin.getConnection().isClosed() || plugin.getConnection().equals(null)) {
                    if(!plugin.getSQLControl().openConnection()) {
                        sender.sendMessage(ChatColor.DARK_RED + "An Internal Error has occurred.");
                        return false;
                    }

                }



                if(args[1].equalsIgnoreCase("set")) {
                    PreparedStatement setStatement = plugin.getConnection().prepareStatement("INSERT INTO " + plugin.getTable() + "");
                    sender.sendMessage("Suck cock.");
                    setStatement.executeUpdate();

                } else if(args[1].equalsIgnoreCase("remove")) {

                } else if(args[1].equalsIgnoreCase("add")) {

                }
            } catch(SQLException e) {

            }

        } else {
            plugin.getLogger().info(ChatColor.DARK_RED + "You need to be a player to execute this command.");
        }
        return false;
    }


}
