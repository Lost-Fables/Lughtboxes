package net.lostfables.lughtboxes.commands;

import net.lostfables.lughtboxes.Lughtboxes;
import net.lostfables.lughtboxes.objects.LughtboxOB;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class Lughtbox implements CommandExecutor {

    private Lughtboxes plugin = Lughtboxes.getPlugin(Lughtboxes.class);

    public Lughtbox() {
        plugin.getCommand("lughtbox").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player) {
            Player player = (Player) sender;

            //TODO add in responses and finish Lughtbox command

            if (args.length == 2) {
                if (args[0].equalsIgnoreCase("create")) {
                    new LughtboxOB(args[1]);
                } else if (args[0].equalsIgnoreCase("delete")) {

                } else if (args[0].equalsIgnoreCase("additem")) {
                    try {
                        LughtboxOB.addItemToLughtBox(new LughtboxOB(args[1]), player.getInventory().getItemInMainHand());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }
            }

        }

        return false;
    }
}
