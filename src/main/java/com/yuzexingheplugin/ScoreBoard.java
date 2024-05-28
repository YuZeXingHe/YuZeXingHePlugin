package com.yuzexingheplugin;


import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.bukkit.Bukkit.getServer;

public class ScoreBoard implements Listener {
    @EventHandler
    public void onPlayerJoinServer(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Scoreboard scoreboard = MakeScore();
        Objective objective = scoreboard.registerNewObjective("PlayerData","dummy", "§a玩家信息");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        // 计分板基本内容
        Score onPlayerLevel = objective.getScore("§a当前活跃等级：");
        onPlayerLevel.setScore(4);

        Score onPlayerEXP = objective.getScore("§a当前经验：");
        onPlayerEXP.setScore(3);

        Score severTps = objective.getScore("§a当前TPS：");
        severTps.setScore(2);

        Score severMspt = objective.getScore("§a当前MSPT：");
        severMspt.setScore(1);

        player.setScoreboard(scoreboard);
    }
    static Scoreboard MakeScore() {
        return Bukkit.getScoreboardManager().getNewScoreboard();
    }
}
