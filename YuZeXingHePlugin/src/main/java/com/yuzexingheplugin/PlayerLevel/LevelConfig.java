package com.yuzexingheplugin.PlayerLevel;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.checkerframework.checker.units.qual.C;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static org.bukkit.Bukkit.getLogger;
import static org.bukkit.Bukkit.getUpdateFolder;

public class LevelConfig implements CommandExecutor, TabExecutor {
    Plugin plugin = com.yuzexingheplugin.YuZeXingHePlugin.getProvidingPlugin(com.yuzexingheplugin.YuZeXingHePlugin.class);
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            // 判断玩家是否为服务器管理员
            if (player.isOp()) {
                if (args.length > 1) {
                    String Command1 = args[0];
                    String Command2 = args[1];
                    // 修改配置文件相关内容
                    if (Command1.equals("blockbreak") || Command1.equals("killedCreatures") || Command1.equals("LevelUp") || Command1.equals("MaxLevel")) {
                        plugin.getConfig().set(Command1, Integer.valueOf(Command2));
                        player.sendMessage(ChatColor.GREEN + "修改配置文件成功！");
                    }
                }
                else {
                    player.sendMessage(ChatColor.RED + "请输入正确的参数！");
                }
            }
        }
        else {
            if (args.length > 1) {
                String Command1 = args[0];
                String Command2 = args[1];
                // 修改配置文件相关内容
                if (Command1.equals("blockbreak") || Command1.equals("killedCreatures") || Command1.equals("LevelUp") || Command1.equals("MaxLevel") || Command1.equals("blockbreakExperience") || Command1.equals("killedCreaturesExperience")) {
                    plugin.getConfig().set(Command1, Integer.valueOf(Command2));
                    Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "修改配置文件成功！");
                }
            }
            else {
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "请输入正确的参数！");
            }
        }

        // 等级设定帮助
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.isOp()) {
                if (args.length > 0) {
                    String Command1 = args[0];
                    // 玩家帮助列表
                    if (Command1.equals("help")) {
                        player.sendMessage(ChatColor.GREEN + "等级功能配置帮助列表");
                        player.sendMessage(ChatColor.AQUA + "/levelconfig blockbreak <数字数值>：修改玩家需要挖掘多少方块才能获得经验。");
                        player.sendMessage(ChatColor.DARK_AQUA + "/levelconfig killedCreatures <数字数值>：修改玩家需要击杀多少生物才能获得经验。");
                        player.sendMessage(ChatColor.AQUA + "/levelconfig LevelUp <数字数值>：修改玩家需要多少经验才能进行升级。");
                        player.sendMessage(ChatColor.DARK_AQUA + "/levelconfig MaxLevel <数字数值>：修改玩家的最大等级。");
                        player.sendMessage(ChatColor.AQUA + "/levelconfig blockbreakExperience <数字数值>：修改挖掘方块增加的经验值。");
                        player.sendMessage(ChatColor.DARK_AQUA + "/levelconfig killedCreaturesExperience <数字数值>：修改击杀生物增加的经验值。");
                    }
                }
                else {
                    player.sendMessage(ChatColor.RED + "请输入正确的参数！");
                }
            }
        }
        else {
            if (args.length > 0) {
                String Command1 = args[0];
                if (Command1.equals("help")) {
                    Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "等级功能配置帮助列表");
                    Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "/levelconfig blockbreak <数字数值>：修改玩家需要挖掘多少方块才能获得经验。");
                    Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.DARK_AQUA + "/levelconfig killedCreatures <数字数值>：修改玩家需要击杀多少生物才能获得经验。");
                    Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "/levelconfig LevelUp <数字数值>：修改玩家需要多少经验才能进行升级。");
                    Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.DARK_AQUA + "/levelconfig MaxLevel <数字数值>：修改玩家的最大等级。");
                    Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "/levelconfig blockbreakExperience <数字数值>：修改挖掘方块增加的经验值。");
                    Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.DARK_AQUA + "/levelconfig killedCreaturesExperience <数字数值>：修改击杀生物增加的经验值。");
                }
            }
            else {
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "请输入正确的参数！");
            }
        }
        plugin.saveConfig();
        plugin.reloadConfig();
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = (Player) sender;
        if (player.isOp()) {
            if (args.length == 1) {
                List<String> list = new ArrayList<>();
                list.add("blockbreak");
                list.add("killedCreatures");
                list.add("LevelUp");
                list.add("MaxLevel");
                list.add("blockbreakExperience");
                list.add("killedCreaturesExperience");
                list.add("help");
                return list;
            }
        }
        return null;
    }
}
