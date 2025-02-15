package org.leng.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.configuration.file.FileConfiguration; 
import org.leng.LengBanCommand;

import java.util.List;

public class LBCCommand implements CommandExecutor {

    private final LengBanCommand plugin;

    public LBCCommand(LengBanCommand plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§c此命令只能由玩家使用。");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage("§c用法：/lbc <add|remove|list>");
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "add":
                if (!player.hasPermission("lbc.manage")) {
                    player.sendMessage("§c你没有权限管理LBC名单。");
                    return true;
                }
                if (args.length < 2) {
                    player.sendMessage("§c用法：/lbc add <玩家名>");
                    return true;
                }
                String targetPlayer = args[1];
                List<String> lbcList = plugin.getLBCConfig().getStringList("lbc-list");
                if (!lbcList.contains(targetPlayer)) {
                    lbcList.add(targetPlayer);
                    plugin.getLBCConfig().set("lbc-list", lbcList);
                    saveConfig(plugin.getLBCConfig(), plugin.getDataFolder() + "/LBC.yml");
                    player.sendMessage("§a玩家 " + targetPlayer + " 已添加到LBC名单。");
                } else {
                    player.sendMessage("§c玩家 " + targetPlayer + " 已经在LBC名单中。");
                }
                return true;

            case "remove":
                if (!player.hasPermission("lbc.manage")) {
                    player.sendMessage("§c你没有权限管理LBC名单。");
                    return true;
                }
                if (args.length < 2) {
                    player.sendMessage("§c用法：/lbc remove <玩家名>");
                    return true;
                }
                targetPlayer = args[1];
                lbcList = plugin.getLBCConfig().getStringList("lbc-list");
                if (lbcList.contains(targetPlayer)) {
                    lbcList.remove(targetPlayer);
                    plugin.getLBCConfig().set("lbc-list", lbcList);
                    saveConfig(plugin.getLBCConfig(), plugin.getDataFolder() + "/LBC.yml");
                    player.sendMessage("§a玩家 " + targetPlayer + " 已从LBC名单中移除。");
                } else {
                    player.sendMessage("§c玩家 " + targetPlayer + " 不在LBC名单中。");
                }
                return true;

            case "list":
                if (!player.hasPermission("lbc.manage")) {
                    player.sendMessage("§c你没有权限查看LBC名单。");
                    return true;
                }
                lbcList = plugin.getLBCConfig().getStringList("lbc-list");
                if (lbcList.isEmpty()) {
                    player.sendMessage("§cLBC名单为空。");
                } else {
                    player.sendMessage("§aLBC名单：");
                    for (String p : lbcList) {
                        player.sendMessage("§7- " + p);
                    }
                }
                return true;

            default:
                player.sendMessage("§c未知子命令。用法：/lbc <add|remove|list>");
                return true;
        }
    }

    private void saveConfig(FileConfiguration config, String path) {
        try {
            config.save(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}