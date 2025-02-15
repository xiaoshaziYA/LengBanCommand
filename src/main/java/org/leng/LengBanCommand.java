package org.leng;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.leng.commands.LBCCommand; 
import org.leng.listeners.CommandListener; 

import java.io.File;
import java.io.IOException;

public class LengBanCommand extends JavaPlugin {

    private File configFile;
    private FileConfiguration config;
    private File lbcFile;
    private FileConfiguration lbcConfig;

    @Override
    public void onEnable() {
        // 初始化配置文件
        configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            saveDefaultConfig();
        }
        config = YamlConfiguration.loadConfiguration(configFile);

        lbcFile = new File(getDataFolder(), "LBC.yml");
        if (!lbcFile.exists()) {
            try {
                lbcFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        lbcConfig = YamlConfiguration.loadConfiguration(lbcFile);

        // 注册指令
        getCommand("lbc").setExecutor(new LBCCommand(this)); 

        // 注册监听器
        getServer().getPluginManager().registerEvents(new CommandListener(this), this); 

        getLogger().info("LengBanCommand 已启用！");
    }

    @Override
    public void onDisable() {
        getLogger().info("LengBanCommand 已禁用！");
    }

    public void reloadConfigs() {
        config = YamlConfiguration.loadConfiguration(configFile);
        lbcConfig = YamlConfiguration.loadConfiguration(lbcFile);
        getLogger().info("配置文件已重新加载！");
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public FileConfiguration getLBCConfig() {
        return lbcConfig;
    }
}