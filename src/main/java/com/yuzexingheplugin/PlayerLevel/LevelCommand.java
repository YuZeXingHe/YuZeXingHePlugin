package com.yuzexingheplugin.PlayerLevel;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Nullable;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.apache.logging.log4j.LogManager.getLogger;

public class LevelCommand implements CommandExecutor, TabCompleter {
    Plugin plugin = com.yuzexingheplugin.YuZeXingHePlugin.getProvidingPlugin(com.yuzexingheplugin.YuZeXingHePlugin.class);
    // 数据库连接相关
    final String username = plugin.getConfig().getString("username");
    final String password = plugin.getConfig().getString("password");
    final String url = plugin.getConfig().getString("url");
    static Connection connection;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        lineMySQL();
        // 使用指令查询服务器所有玩家等级（包括不在线的）并排行
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length > 0) {
                String LevelCommand1 = args[0];
                // 主要方法
                if (LevelCommand1.equals("list")) {
                    try {
                        String Query = "SELECT PlayerName, PlayerLevel FROM PlayerData";
                        PreparedStatement statement = connection.prepareStatement(Query);
                        ResultSet resultSet = statement.executeQuery();

                        List<String> leaderboard = new ArrayList<>();
                        while (resultSet.next()) {
                            String playerName = resultSet.getString("PlayerName");
                            int level = resultSet.getInt("PlayerLevel");
                            leaderboard.add(playerName + "玩家等级： " + level);
                        }

                        // 对获取到的内容排序
                        leaderboard.sort((s1, s2) -> {
                            int level1 = Integer.parseInt(s1.split("玩家等级： ")[1]);
                            int level2 = Integer.parseInt(s2.split("玩家等级： ")[1]);
                            return Integer.compare(level2, level1);
                        });

                        // 发送排序后的排行榜信息给玩家（仅前10）
                        int count = 0;
                        player.sendMessage(ChatColor.AQUA + "-------玩家等级排行榜（仅展示前10）-------");
                        for (String entry : leaderboard) {
                            if (count < 10) {
                                player.sendMessage(ChatColor.GREEN + entry);
                            }
                            else {
                                break;
                            }
                        }
                        resultSet.close();
                        statement.close();
                    }
                    catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            // 当玩家未输入参数时，向该玩家反馈错误
            else {
                player.sendMessage(ChatColor.RED + "请输入正确的参数！");
                return false;
            }
        }
        // 服务器终端查询
        else {
            if (args.length > 0) {
                String LevelCommand1 = args[0];
                // 主要方法
                if (LevelCommand1.equals("list")) {
                    try {
                        String Query = "SELECT PlayerName, PlayerLevel FROM PlayerData";
                        PreparedStatement statement = connection.prepareStatement(Query);
                        ResultSet resultSet = statement.executeQuery();

                        List<String> leaderboard = new ArrayList<>();
                        while (resultSet.next()) {
                            String playerName = resultSet.getString("PlayerName");
                            int level = resultSet.getInt("PlayerLevel");
                            leaderboard.add(playerName + "玩家等级： " + level);
                        }

                        // 对获取到的内容排序
                        leaderboard.sort((s1, s2) -> {
                            int level1 = Integer.parseInt(s1.split("玩家等级： ")[1]);
                            int level2 = Integer.parseInt(s2.split("玩家等级： ")[1]);
                            return Integer.compare(level2, level1);
                        });

                        // 发送排序后的排行榜信息给控制台
                        getLogger().info("----------玩家等级排行榜----------");
                        for (String entry : leaderboard) {
                            getLogger().info(entry);
                        }
                        resultSet.close();
                        statement.close();
                    }
                    catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            // 当服务器终端未输入参数时，则向终端发送错误反馈
            else {
                getLogger().error("请输入正确的参数！");
                return false;
            }
        }
        closeMySQL();
        return false;
    }


    @Override
    public @Nullable List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            List<String> list = new ArrayList<>();
            list.add("list");
            return list;
        }
        return null;
    }

    // 连接数据库
    public void lineMySQL() {
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 关闭数据库
    public void closeMySQL() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
