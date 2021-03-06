package net.lostfables.lughtboxes.commands;

import co.lotc.core.bukkit.menu.Menu;
import co.lotc.core.bukkit.menu.MenuAction;
import co.lotc.core.bukkit.menu.MenuAgent;
import co.lotc.core.bukkit.menu.icon.Button;
import co.lotc.core.bukkit.menu.icon.Icon;
import co.lotc.core.bukkit.util.ItemUtil;
import co.lotc.core.bukkit.util.PlayerUtil;
import net.lostfables.lughtboxes.Lughtboxes;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class Vote implements CommandExecutor {

    private Lughtboxes plugin = Lughtboxes.getPlugin(Lughtboxes.class);

    public Vote() {
        plugin.getCommand("vote").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        //Checks to see
        if(sender instanceof Player) {
            Player player = (Player) sender;
            Menu voteMenu = null;


            //Opens the vote menu
            if(args.length == 0) {
                voteMenu = voteMenuBuilder(player, voteMenu);
                voteMenu.openSession(player);
                return false;
            }


            if(!player.hasPermission("lughtboxes.staff")) {
                player.sendMessage(ChatColor.DARK_RED + "[Lughtboxes] You do not have the permissions to access this command.");
                return false;
            }

            if(args.length == 4)   {

                UUID uuid = PlayerUtil.getPlayerUUID(args[2]);

                String targetName;

                int value;
                try { value = Integer.parseInt(args[3]); } catch(Exception e) { value = 0; }

                if(uuid == null) { player.sendMessage(ChatColor.DARK_RED + "[Lughtboxes] You must choose a valid player.");
                return false; }
                else { targetName = args[2]; }

                if(args[3].isEmpty() || value <= 0) { player.sendMessage(ChatColor.DARK_RED + "[Lughtboxes] You must insert a valid value.");
                    return false; }

                try {

                    plugin.getSQLControl().openConnection();
                    PreparedStatement statement = plugin.getConnection().prepareStatement("SELECT * FROM " + plugin.getTable().get(0) + " WHERE UUID=?");
                    int currentAmount;
                    statement.setString(1, uuid.toString());
                    ResultSet s = statement.executeQuery();
                    ResultSet adder;
                    s.next();

                    if (args[1].equalsIgnoreCase("coins")) {

                        if (args[0].equalsIgnoreCase("set")) {

                            statement = plugin.getConnection().prepareStatement("UPDATE " + plugin.getTable().get(0) + " SET COINS=? WHERE UUID=?");
                            statement.setInt(1, value);
                            statement.setString(2, uuid.toString());
                            player.sendMessage(ChatColor.DARK_GREEN + "[Lughtboxes] " + targetName + "'s voting coins has been set to " + value);

                        } else if (args[0].equalsIgnoreCase("add")) {

                            statement = plugin.getConnection().prepareStatement("SELECT COINS FROM " + plugin.getTable().get(0) + " WHERE UUID=?");
                            statement.setString(1, uuid.toString());
                            adder = statement.executeQuery();
                            adder.next();
                            currentAmount = adder.getInt(1);

                            statement = plugin.getConnection().prepareStatement("UPDATE " + plugin.getTable().get(0) + " SET COINS=? WHERE UUID=?");
                            statement.setInt(1, value + currentAmount);
                            statement.setString(2, uuid.toString());
                            player.sendMessage(ChatColor.DARK_GREEN + "[Lughtboxes] " + value + " has been added to " + targetName + "'s voting coins.");

                        } else if (args[0].equalsIgnoreCase("remove")) {

                            statement = plugin.getConnection().prepareStatement("SELECT COINS FROM " + plugin.getTable().get(0) + " WHERE UUID=?");
                            statement.setString(1, uuid.toString());
                            adder = statement.executeQuery();
                            adder.next();
                            currentAmount = adder.getInt(1);

                            statement = plugin.getConnection().prepareStatement("UPDATE " + plugin.getTable().get(0) + " SET COINS=? WHERE UUID=?");
                            statement.setInt(1, currentAmount - value);
                            statement.setString(2, uuid.toString());
                            player.sendMessage(ChatColor.DARK_GREEN + "[Lughtboxes] " + value + " has been removed to " + targetName + "'s voting coins.");

                        }
                    } else if (args[1].equalsIgnoreCase("premcoins")) {

                        if (args[0].equalsIgnoreCase("set")) {

                            statement = plugin.getConnection().prepareStatement("UPDATE " + plugin.getTable().get(0) + " SET PREMCOINS=? WHERE UUID=?");
                            statement.setInt(1, value);
                            statement.setString(2, uuid.toString());
                            player.sendMessage(ChatColor.DARK_GREEN + "[Lughtboxes] " + targetName + "'s voting coins has been set to " + value);

                        } else if (args[0].equalsIgnoreCase("add")) {

                            statement = plugin.getConnection().prepareStatement("SELECT PREMCOINS FROM " + plugin.getTable().get(0) + " WHERE UUID=?");
                            statement.setString(1, uuid.toString());
                            adder = statement.executeQuery();
                            adder.next();
                            currentAmount = adder.getInt(1);

                            statement = plugin.getConnection().prepareStatement("UPDATE " + plugin.getTable().get(0) + " SET PREMCOINS=? WHERE UUID=?");
                            statement.setInt(1, value + currentAmount);
                            statement.setString(2, uuid.toString());
                            player.sendMessage(ChatColor.DARK_GREEN + "[Lughtboxes] " + value + " has been added to " + targetName + "'s premium coins.");

                        } else if (args[0].equalsIgnoreCase("remove")) {

                            statement = plugin.getConnection().prepareStatement("SELECT PREMCOINS FROM " + plugin.getTable().get(0) + " WHERE UUID=?");
                            statement.setString(1, uuid.toString());
                            adder = statement.executeQuery();
                            adder.next();
                            currentAmount = adder.getInt(1);

                            statement = plugin.getConnection().prepareStatement("UPDATE " + plugin.getTable().get(0) + " SET PREMCOINS=? WHERE UUID=?");
                            statement.setInt(1, currentAmount - value);
                            statement.setString(2, uuid.toString());
                            player.sendMessage(ChatColor.DARK_GREEN + "[Lughtboxes] " + value + " has been removed from " + targetName + "'s premium coins.");

                        }
                    }

                    statement.executeUpdate();
                    plugin.getConnection().close();

                } catch(SQLException e) {
                    e.printStackTrace();
                }
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
                    List<String> lore = new ArrayList<>();

                    try {
                        plugin.getSQLControl().openConnection();
                        PreparedStatement statement = plugin.getConnection().prepareStatement("SELECT * FROM " + plugin.getTable().get(0) + " WHERE UUID=?");
                        statement.setString(1, String.valueOf(player.getUniqueId()));
                        ResultSet s = statement.executeQuery();
                        s.next();
                        int coinsAmount = Integer.parseInt(s.getString("COINS"));
                        lore.add(ChatColor.GRAY + "Coins: " + coinsAmount);
                        plugin.getConnection().close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    coinsItemMeta.setLore(lore);

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
                    List<String> lore = new ArrayList<>();

                    try {
                        plugin.getSQLControl().openConnection();
                        PreparedStatement statement = plugin.getConnection().prepareStatement("SELECT * FROM " + plugin.getTable().get(0) + " WHERE UUID=?");
                        statement.setString(1, String.valueOf(player.getUniqueId()));
                        ResultSet s = statement.executeQuery();
                        s.next();
                        int coinsAmount = Integer.parseInt(s.getString("PREMCOINS"));
                        lore.add(ChatColor.GRAY + "Premium Coins: " + coinsAmount);
                        plugin.getConnection().close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    premCoinsItemMeta.setLore(lore);

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

        voteMenuBase = Menu.fromIcons(ChatColor.BOLD + "" + ChatColor.DARK_GREEN + "Vote Menu",icons);

        return voteMenuBase;
    }


}
