package org.leng.listeners;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.Plugin;
import org.leng.LengBanCommand;
import org.bukkit.entity.Player; 

import java.util.List;

public class CommandListener implements Listener {

    private final Plugin plugin;
    private final FileConfiguration config;
    private final FileConfiguration lbcConfig;

    public CommandListener(Plugin plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
        this.lbcConfig = ((LengBanCommand) plugin).getLBCConfig();
    }

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String command = event.getMessage().split(" ")[0].toLowerCase();

        List<String> blacklistedCommands = config.getStringList("blacklisted-commands");
        List<String> lbcList = lbcConfig.getStringList("lbc-list");

        boolean isLBC = lbcList.contains(player.getName());
        boolean hasBypassPermission = player.hasPermission("lbc.bypass");

        if (blacklistedCommands.contains(command)) {
            if (!isLBC && !hasBypassPermission) {
                player.sendMessage("§b[LBC] §c这不是你的工作！");
                event.setCancelled(true); // 
            }
        }
    }
}