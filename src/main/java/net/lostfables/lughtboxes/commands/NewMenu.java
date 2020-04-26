package net.lostfables.lughtboxes.commands;

import co.lotc.core.bukkit.menu.Menu;
import co.lotc.core.bukkit.menu.MenuAction;
import co.lotc.core.bukkit.menu.MenuAgent;
import co.lotc.core.bukkit.menu.icon.Button;
import co.lotc.core.bukkit.menu.icon.Icon;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class NewMenu implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player p = (Player)sender;
        Menu homeMenu = null;
        ArrayList<Icon> icons = new ArrayList<>();

        Button icon = new Button() {
            @Override
            public ItemStack getItemStack(MenuAgent menuAgent) {
                return p.getInventory().getItemInMainHand();
            }

            @Override
            public void click(MenuAction menuAction) {

            }
        };

        icons.add(icon);
        sender.sendMessage(String.valueOf(icons.size()));

        homeMenu = homeMenu.fromIcons("I hate black people", icons);

        homeMenu.openSession(p);

        return false;
    }
}
