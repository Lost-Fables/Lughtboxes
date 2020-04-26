package net.lostfables.lughtboxes.commands;

import co.lotc.core.bukkit.menu.Menu;
import co.lotc.core.bukkit.menu.MenuAction;
import co.lotc.core.bukkit.menu.MenuAgent;
import co.lotc.core.bukkit.menu.icon.Button;
import co.lotc.core.bukkit.menu.icon.Icon;
import co.lotc.core.bukkit.util.ItemUtil;
import co.lotc.core.bukkit.util.PlayerUtil;
import net.lostfables.lughtboxes.Lughtbox;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;


public class Vote implements CommandExecutor {

    private Lughtbox plugin = Lughtbox.getPlugin(Lughtbox.class);

    public Vote() {
        plugin.getCommand("vote").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player) {
            Player player = (Player) sender;
            Menu voteMenu = null;


            if(args.length == 0) {
                voteMenu = voteMenuBuilder(player, voteMenu);
                voteMenu.openSession(player);
            }

        }

        return false;
    }

    private Menu voteMenuBuilder(Player player, Menu voteMenuBase) {

        ArrayList<Icon> icons = new ArrayList<>();

        Icon playerIcon = new Button() {
            @Override
            public ItemStack getItemStack(MenuAgent menuAgent) {

                ItemStack playerItem = ItemUtil.getSkullFromTexture(PlayerUtil.getPlayerTexture(player.getUniqueId()));
                ItemMeta playerItemMeta = playerItem.getItemMeta();
                playerItemMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.BOLD + player.getDisplayName());


                playerItem.setItemMeta(playerItemMeta);
                return playerItem;
            }

            @Override
            public void click(MenuAction menuAction) {

            }
        };

        Icon coinsIcon = new Button() {
            @Override
            public ItemStack getItemStack(MenuAgent menuAgent) {

                ItemStack coinsItem = ItemUtil.getSkullFromTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzk2Y2UxM2ZmNjE1NWZkZjMyMzVkOGQyMjE3NGM1ZGU0YmY1NTEyZjFhZGVkYTFhZmEzZmMyODE4MGYzZjcifX19");
                ItemMeta coinsItemMeta = coinsItem.getItemMeta();
                coinsItemMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.GOLD + "Coins");

                coinsItem.setItemMeta(coinsItemMeta);
                return coinsItem;
            }

            @Override
            public void click(MenuAction menuAction) {

            }
        };

        Icon premCoinsIcon = new Button() {
            @Override
            public ItemStack getItemStack(MenuAgent menuAgent) {

                ItemStack premCoinsItem = ItemUtil.getSkullFromTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjZhNjg4NmU4NGVhZTViYTJiMmI2MDI0MzI0MjljNmZiMjg2OTFmYzAyZTlmOWNjYjVjNTdmMmNkZDBmMWQ4In19fQ==");
                ItemMeta premCoinsItemMeta = premCoinsItem.getItemMeta();
                premCoinsItemMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.GOLD + "Premium Coins");

                premCoinsItem.setItemMeta(premCoinsItemMeta);
                return premCoinsItem;
            }

            @Override
            public void click(MenuAction menuAction) {

            }
        };

        Icon totalVotesIcon = new Button() {
            @Override
            public ItemStack getItemStack(MenuAgent menuAgent) {

                ItemStack totalVotesItem = new ItemStack(Material.PLAYER_HEAD);

                return totalVotesItem;
            }

            @Override
            public void click(MenuAction menuAction) {

            }
        };

        Icon recurrentVotesIcon = new Button() {
            @Override
            public ItemStack getItemStack(MenuAgent menuAgent) {

                ItemStack recurrentVotesItem = new ItemStack(Material.PLAYER_HEAD);

                return recurrentVotesItem;
            }

            @Override
            public void click(MenuAction menuAction) {

            }
        };

        icons.add(playerIcon);
        icons.add(coinsIcon);
        icons.add(premCoinsIcon);
        icons.add(totalVotesIcon);
        icons.add(recurrentVotesIcon);

        voteMenuBase = voteMenuBase.fromIcons(ChatColor.BOLD + "" + ChatColor.DARK_GREEN + "Vote Menu",icons);

        return voteMenuBase;
    }


}
