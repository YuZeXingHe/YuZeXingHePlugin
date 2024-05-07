package com.yuzexingheplugin.PlayerLevel;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LevelCommand implements CommandExecutor, TabCompleter {
    private File file;
    private FileConfiguration data;
    public LevelCommand() {
        file = new File("plugins/YuZeXingHePlugin/level.yml");
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        data = YamlConfiguration.loadConfiguration(file);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        String LevelCommand1 = args[0];
        String LevelCommand2 = args[1];
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "该指令只能由玩家执行！");
            return false;
        }
        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "请输入正确的参数！");
            return false;
        }

        // 查询全部玩家等级（不论在不在线，只要数据文件里面有）
        if (LevelCommand1.equals("list")) {
            List<String> playerNames = new ArrayList<>(data.getKeys(false));
            playerNames.sort(Comparator.comparingInt(name -> data.getInt(name + ".Level")));
            player.sendMessage("===== 玩家等级排行榜 =====");
            for (String playerName : playerNames) {
                int level = data.getInt(playerName + ".Level");
                player.sendMessage(playerName + " - 等级: " + level);
            }
        }
        // 查询单个在线玩家的等级信息
        if (LevelCommand1.equals("player")) {
            String playerName = LevelCommand2;
            Player targetPlayer = Bukkit.getServer().getPlayer(playerName);
            if (targetPlayer != null) {
                int level = data.getInt(playerName + ".Level", 0);
                sender.sendMessage(ChatColor.GREEN + playerName + " 的等级为：" + level);
            }
            else {
                sender.sendMessage(ChatColor.RED + "玩家 " + playerName + " 不在线！");
            }
        }
        else {
            player.sendMessage("未知的指令，请检查指令是否拼写完整！");
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            List<String> completions = new ArrayList<>();
            completions.add("list");
            completions.add("player");
            return completions;
        }
        if (args.length == 2 && args[0].equals("player")) {
            List<String> onlinePlayers = new ArrayList<>();
            for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                onlinePlayers.add(player.getName());
            }
            return onlinePlayers;
        }
        return Collections.emptyList();
    }
}
