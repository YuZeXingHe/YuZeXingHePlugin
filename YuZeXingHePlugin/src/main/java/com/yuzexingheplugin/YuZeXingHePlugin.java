package com.yuzexingheplugin;

import com.yuzexingheplugin.Listener.PlayerListener;
import com.yuzexingheplugin.Listener.SeverListener;
import com.yuzexingheplugin.Command.Commands;
import com.yuzexingheplugin.Command.config;
import com.yuzexingheplugin.PlayerLevel.LevelCommand;
import com.yuzexingheplugin.PlayerLevel.LevelConfig;
import com.yuzexingheplugin.PlayerLevel.LevelListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.*;

import java.sql.*;

public final class YuZeXingHePlugin extends JavaPlugin implements Listener {
    private static YuZeXingHePlugin instance;
    String version = "1.9.2";
    private static Plugin plugin;
    static Connection connection;

    private boolean papiEnabled;

    @Override
    public void onEnable() {
        // 插件成功启动并运行
        getLogger().info("插件成功运行，感谢您使用YuZeXingHePlugin！开发者：YuZeXingHe！");
        getLogger().info("当前插件版本：" + version + "插件支持版本：1.20.1----1.20.4");
        getLogger().info("如果您在使用过程中发现任何Bug，请在GitHub中联系插件开发者！");

        if (getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            papiEnabled = true;
        }


        plugin = getProvidingPlugin(YuZeXingHePlugin.class);

        // 数据库
        final String username = plugin.getConfig().getString("username");
        final String password = plugin.getConfig().getString("password");
        final String url = plugin.getConfig().getString("url");

        try {
            connection = DriverManager.getConnection(url, username, password);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        // 如果没有表格，则在数据库中创建一个表格，并在表格中创建一个含有玩家信息的列（玩家名称，玩家等级，玩家经验，玩家挖掘方块数，玩家击杀生物数）
        String sql = "CREATE TABLE IF NOT EXISTS PlayerData(PlayerName varchar(64) primary key, PlayerLevel int DEFAULT 1, PlayerExperience int DEFAULT 0, PlayerMinedBlocks int DEFAULT 0, PlayerKilledCreatures int DEFAULT 0)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        // 监听器存放处
        getServer().getPluginManager().registerEvents(new SeverListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new LevelListener(), this);
        // 指令存放处
        getCommand("open").setExecutor(new Commands());
        getCommand("configs").setExecutor(new config());
        getCommand("level").setExecutor(new LevelCommand());
        getCommand("levelconfig").setExecutor(new LevelConfig());
        // 指令Tab补全存放处
        getCommand("open").setTabCompleter(new Commands());
        getCommand("configs").setTabCompleter(new config());
        getCommand("level").setTabCompleter(new LevelCommand());
        getCommand("levelconfig").setTabCompleter(new LevelConfig());
        // 配置文件（保存）
        saveDefaultConfig();

        // 计分板相关
        getServer().getPluginManager().registerEvents(new ScoreBoard(), this);
        Bukkit.getScheduler().runTaskTimer(this, new Runnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    updateScoreboard(player);
                }
            }
        }, 0, 20);
    }

    @Override
    public void onDisable() {
        // 插件成功关闭
        getLogger().info("插件关闭成功，感谢您使用本插件！");
        getLogger().info("如果您在使用过程中发现任何Bug，请在GitHub中联系插件开发者！");

        try {
            if (connection!=null && !connection.isClosed()){
                connection.close();
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static YuZeXingHePlugin getInstance() {
        return instance;
    }

    // 计分板内容
    private void updateScoreboard(Player player) {
        // 获取玩家的计分板
        Scoreboard scoreboard = player.getScoreboard();
        Objective objective = scoreboard.getObjective("PlayerData");
        String playerName = player.getName();

        // 清除原有内容
        for (String entry : scoreboard.getEntries()) {
            scoreboard.resetScores(entry);
        }

        // 更新计分板内容
        if (objective != null) {
            int onLevelUpEXP = plugin.getConfig().getInt("LevelUp");
            int onMaxLevel = plugin.getConfig().getInt("MaxLevel");
            lineMySQL();
            int tps = (int) Bukkit.getServer().getTPS()[0];
            int mspt = (int) Bukkit.getAverageTickTime();

            // 获取玩家经验值
            try {
                String expQuery = "SELECT PlayerExperience FROM PlayerData WHERE PlayerName = ?";
                PreparedStatement EXPstmt = connection.prepareStatement(expQuery);
                EXPstmt.setString(1, playerName);
                ResultSet rsEXP = EXPstmt.executeQuery();
                if (rsEXP.next()) {
                    int playerEXP = rsEXP.getInt("PlayerExperience");
                    // 计分板内容刷新
                    Score onPlayerEXP = objective.getScore(ChatColor.AQUA + "当前经验：" + playerEXP + "/" + onLevelUpEXP);
                    onPlayerEXP.setScore(3);
                    // 查询玩家等级
                    String levelQuery = "SELECT PlayerLevel FROM PlayerData WHERE PlayerName = ?";
                    PreparedStatement Levelstmt = connection.prepareStatement(levelQuery);
                    Levelstmt.setString(1, playerName);
                    ResultSet rsLevel = Levelstmt.executeQuery();
                    if (rsLevel.next()) {
                        int playerLevel = rsLevel.getInt("PlayerLevel");
                        // 计分板内容刷新
                        Score onPlayerLevel = objective.getScore(ChatColor.AQUA + "当前活跃等级：" + playerLevel);
                        onPlayerLevel.setScore(4);

                        // 玩家升级操作
                        if (playerEXP >= onLevelUpEXP && playerLevel < onMaxLevel) {
                            int playerLevelUpdate = playerLevel + 1;
                            int EXPUpdate = playerEXP - onLevelUpEXP;
                            String onLevelUpdate = "UPDATE PlayerData SET PlayerLevel = ?, PlayerExperience = ? WHERE PlayerName = ?";
                            PreparedStatement LevelUpdatestmt = connection.prepareStatement(onLevelUpdate);
                            LevelUpdatestmt.setInt(1, playerLevelUpdate);
                            LevelUpdatestmt.setInt(2, EXPUpdate);
                            LevelUpdatestmt.setString(3, playerName);
                            player.sendMessage(ChatColor.GREEN + "活跃等级提升至" + playerLevelUpdate + "级！");
                            LevelUpdatestmt.executeUpdate();
                        }

                        // 如果玩家到达最大等级上限，则提醒玩家到达最大等级上限
                        if (playerLevel >= onMaxLevel) {
                            Score LevelMax = objective.getScore(ChatColor.RED + "当前等级已达到上限！");
                            LevelMax.setScore(0);
                        }
                    }
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }

            // 计分板基本内容
            Score severTps = objective.getScore(ChatColor.GREEN + "当前TPS：" + tps);
            severTps.setScore(2);

            Score severMspt = objective.getScore(ChatColor.GREEN + "当前MSPT：" + mspt);
            severMspt.setScore(1);
            closeMySQL();
        }
    }

    // 连接数据库
    public void lineMySQL() {
        try {
            // 数据库
            final String username = plugin.getConfig().getString("username");
            final String password = plugin.getConfig().getString("password");
            final String url = plugin.getConfig().getString("url");
            connection = DriverManager.getConnection(url, username, password);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 关闭数据库
    public void closeMySQL() {
        try {
            if (connection!=null && !connection.isClosed()){
                connection.close();
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    // PAPI
    public boolean isPAPIEnabled() {
        return papiEnabled;
    }
}