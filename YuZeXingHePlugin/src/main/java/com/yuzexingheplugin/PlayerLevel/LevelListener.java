package com.yuzexingheplugin.PlayerLevel;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;

import java.sql.*;

public class LevelListener implements Listener {
    Plugin plugin = com.yuzexingheplugin.YuZeXingHePlugin.getProvidingPlugin(com.yuzexingheplugin.YuZeXingHePlugin.class);
    // 数据库连接相关
    final String username = plugin.getConfig().getString("username");
    final String password = plugin.getConfig().getString("password");
    final String url = plugin.getConfig().getString("url");
    static Connection connection;

    // 玩家加入游戏时，连接数据库，获取玩家名称并创建玩家数据（如果没有）
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // 连接数据库
        try {
            connection = DriverManager.getConnection(url, username, password);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        // 导入玩家数据（如果玩家在数据库表格中没有存在过的话）
        Player player = event.getPlayer();
        String playerName = player.getName();
        String sql = "INSERT INTO PlayerData(PlayerName, PlayerLevel, PlayerExperience, PlayerMinedBlocks, PlayerKilledCreatures) VALUES (?, 1, 0, 0, 0) ON DUPLICATE KEY UPDATE PlayerName = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, playerName);
            stmt.setString(2, playerName);
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        // 使用完成后关闭数据库
        try {
            if (connection!=null && !connection.isClosed()){
                connection.close();
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    // 玩家通过破坏方块获得经验值
    @EventHandler
    public void onPlayerBlockBreak(BlockBreakEvent event) {
        int ExperienceUP = plugin.getConfig().getInt("blockbreakExperience");
        int blockbreakUpdate = plugin.getConfig().getInt("blockbreak");
        Player player = event.getPlayer();
        String playerName = player.getName();
        int Zero = 0;

        lineMySQL();

        int playerMinedBlocks;
        int playerEXP;
        try {
            String sql = "SELECT PlayerMinedBlocks FROM PlayerData WHERE PlayerName = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, playerName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                playerMinedBlocks = rs.getInt("PlayerMinedBlocks");
                // 增加玩家挖掘方块的数量
                int MinedBlocksUpdate = playerMinedBlocks + 1;

                // 当玩家方块挖掘到一定数量时（根据配置文件设定），使玩家获得经验
                String expQuery = "SELECT PlayerExperience FROM PlayerData WHERE PlayerName = ?";
                PreparedStatement EXPstmt = connection.prepareStatement(expQuery);
                EXPstmt.setString(1, playerName);
                ResultSet rsEXP = EXPstmt.executeQuery();
                if (rsEXP.next()) {
                    playerEXP = rsEXP.getInt("PlayerExperience");
                    // 当玩家挖掘足够的方块时，增加玩家经验，同时重新计算玩家挖掘方块数量
                    if (playerMinedBlocks >= blockbreakUpdate) {
                        int playerEXPUpdate = playerEXP + ExperienceUP;
                        String expUpdate = "UPDATE PlayerData SET PlayerExperience = ?, PlayerMinedBlocks = ? WHERE PlayerName = ?";
                        PreparedStatement expUpdate_stmt = connection.prepareStatement(expUpdate);
                        expUpdate_stmt.setInt(1, playerEXPUpdate);
                        expUpdate_stmt.setInt(2, Zero);
                        expUpdate_stmt.setString(3, playerName);
                        expUpdate_stmt.executeUpdate();
                        player.sendMessage("你获得了" + ExperienceUP + "经验！");
                    }
                    // 当玩家挖掘方块数量未达到标准，则同步并保存方块数量
                    else {
                        String sqlMinedBlocksUpdate = "UPDATE PlayerData SET PlayerMinedBlocks = ? WHERE PlayerName = ?";
                        PreparedStatement MinedBlocksUpdate_stmt = connection.prepareStatement(sqlMinedBlocksUpdate);
                        MinedBlocksUpdate_stmt.setInt(1, MinedBlocksUpdate);
                        MinedBlocksUpdate_stmt.setString(2, playerName);
                        MinedBlocksUpdate_stmt.executeUpdate();
                    }
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        closeMySQL();
    }

    // 玩家通过击杀生物获得经验值
    @EventHandler
    public void onPlayerKill(EntityDeathEvent event) {
        int ExperienceUP = plugin.getConfig().getInt("killedCreaturesExperience");
        int killedCreaturesUpdate = plugin.getConfig().getInt("killedCreatures");
        Player player = event.getEntity().getKiller();
        int Zero = 0;

        lineMySQL();

        int playerKilledCreatures;
        int playerEXP;
        // 判断生物死亡时是否是玩家击杀，防止玩家名称原因报错
        if (player != null) {
            String playerName = player.getName();
            try {
                String sql = "SELECT PlayerKilledCreatures FROM PlayerData WHERE PlayerName = ?";
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setString(1, playerName);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    playerKilledCreatures = rs.getInt("PlayerKilledCreatures");
                    // 增加玩家击杀生物数量
                    int KilledCreaturesUpdate = playerKilledCreatures + 1;

                    // 当玩家击杀生物到一定数量时（根据配置文件设定），使玩家获得经验
                    String expQuery = "SELECT PlayerExperience FROM PlayerData WHERE PlayerName = ?";
                    PreparedStatement EXPstmt = connection.prepareStatement(expQuery);
                    EXPstmt.setString(1, playerName);
                    ResultSet rsEXP = EXPstmt.executeQuery();
                    if (rsEXP.next()) {
                        playerEXP = rsEXP.getInt("PlayerExperience");
                        // 当玩家击杀数达到给予经验的标准时，增加经验
                        if (playerKilledCreatures >= killedCreaturesUpdate) {
                            int playerEXPUpdate = playerEXP + ExperienceUP;
                            String expUpdate = "UPDATE PlayerData SET PlayerExperience = ?, PlayerKilledCreatures = ? WHERE PlayerName = ?";
                            PreparedStatement expUpdate_stmt = connection.prepareStatement(expUpdate);
                            expUpdate_stmt.setInt(1, playerEXPUpdate);
                            expUpdate_stmt.setInt(2, Zero);
                            expUpdate_stmt.setString(3, playerName);
                            expUpdate_stmt.executeUpdate();
                            player.sendMessage("你获得了" + ExperienceUP + "经验！");
                        }
                        // 当玩家击杀生物未达到标准，则同步并保存玩家击杀数
                        else {
                            String sqlKilledCreaturesUpdate = "UPDATE PlayerData SET PlayerKilledCreatures = ? WHERE PlayerName = ?";
                            PreparedStatement KilledCreaturesUpdate_stmt = connection.prepareStatement(sqlKilledCreaturesUpdate);
                            KilledCreaturesUpdate_stmt.setInt(1, KilledCreaturesUpdate);
                            KilledCreaturesUpdate_stmt.setString(2, playerName);
                            KilledCreaturesUpdate_stmt.executeUpdate();
                        }
                    }
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        closeMySQL();
    }

    // 连接数据库
    public void lineMySQL() {
        try {
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
}
