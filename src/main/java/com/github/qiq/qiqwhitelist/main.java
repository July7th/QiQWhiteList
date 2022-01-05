
package com.github.qiq.qiqwhitelist;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class main extends JavaPlugin {
    private static String ip;
    private static String port;
    private static String user;
    private static String password;
    private static String database;
    private static String table;
    public static String sqlkickmessage;
    public static String ymlkickmessage;
    public static MySQL link;
    public static Boolean Enable;
    public static Boolean use_mysql;
    public static FileConfiguration playerconfigfile;
    public static File playerConfig;
    public static FileConfiguration config;

    public main() {
    }

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        config = this.getConfig();
        this.saveResource("player.yml", false);
        Enable = config.getBoolean("Enable", true);
        use_mysql = config.getBoolean("MySQL.use", false);
        sqlkickmessage = config.getString("sqlkickmessage", "你没有获得本服的白名单!请在www.xxxx.com申请。");
        ymlkickmessage = config.getString("ymlkickmessage", "你没有获得本服的白名单!请联系管理人员。");
        if (use_mysql) {
            ip = config.getString("MySQL.host", "localhost");
            port = config.getString("MySQL.port", "3306");
            user = config.getString("MySQL.user", "root");
            password = config.getString("MySQL.password", "root");
            database = config.getString("MySQL.database", "whitelist");
            table = config.getString("MySQL.table", "whitelist");
            onLogin();
        } else {
            playerConfig = new File(this.getDataFolder(), "player.yml");
            playerconfigfile = YamlConfiguration.loadConfiguration(playerConfig);
            this.getLogger().info(ChatColor.GREEN + "QiQWhiteList加载成功!");
        }

        Bukkit.getPluginManager().registerEvents(new Listeners(), this);
        Bukkit.getPluginCommand("qiqwhitelist").setExecutor(new Commander());

    }

    @Override
    public void onDisable() {
        saveConfig();
        this.getLogger().info(ChatColor.RED + "插件已卸载!");
    }

    public static void onLogin() {
        link = new MySQL();
        link.savesql(ip, port, database, user, password, table);
        link.connect();
        link.createtable();
        link.closeConn();
        Bukkit.getLogger().info("数据库链接成功");
    }
}
