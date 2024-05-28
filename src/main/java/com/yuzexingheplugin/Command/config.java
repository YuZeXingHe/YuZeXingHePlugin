package com.yuzexingheplugin.Command;

import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.ArrayList;
import java.util.List;

public class config implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "该指令只能由玩家执行！");
            return false;
        }

        Player player = (Player) sender;
        Plugin plugin = com.yuzexingheplugin.YuZeXingHePlugin.getProvidingPlugin(com.yuzexingheplugin.YuZeXingHePlugin.class);
        String hand = "[YuZeXingHePlugin]";

        // UI界面配置
        Boolean All_UI = plugin.getConfig().getBoolean("all_ui");

        // 世界监听配置
        Boolean Server_Listener = plugin.getConfig().getBoolean("server_listener");
        Boolean Player_Join = plugin.getConfig().getBoolean("player_join");
        Boolean Player_Quit = plugin.getConfig().getBoolean("player_quit");
        Boolean Player_Death = plugin.getConfig().getBoolean("player_death");
        Boolean Player_Deep_Sleep = plugin.getConfig().getBoolean("player_deep_sleep");

        // 玩家活动监听配置
        Boolean Player_Listenter = plugin.getConfig().getBoolean("player_listenter");

        if (args.length < 1) {
            player.sendMessage(ChatColor.RED + "请输入正确的参数！");
            return false;
        }

        if (!player.isOp()) {
            player.sendMessage(ChatColor.RED + "你没有权限执行此命令！");
            return false;
        }

        if (player.isOp()) {
            String config_command = args[0];
            if (config_command.equals("start")) {
                player.sendMessage(ChatColor.YELLOW + hand + ChatColor.GREEN + plugin.getConfig().getString("start"));
            }
            // 列出设置项
            else if (config_command.equals("list")) {
                // UI界面开关列表
                sender.sendMessage(ChatColor.YELLOW + hand + ChatColor.GREEN + "UI界面开关列表：");
                // UI界面功能总开关
                if (All_UI) {
                    sender.sendMessage(ChatColor.AQUA + plugin.getConfig().getString("all_ui_true"));
                }
                if (!All_UI) {
                    sender.sendMessage(ChatColor.AQUA + plugin.getConfig().getString("all_ui_false"));
                }
                // 服务器监听总开关
                sender.sendMessage(ChatColor.YELLOW + hand + ChatColor.GREEN + "服务器监听开关列表：");
                if (Server_Listener) {
                    sender.sendMessage(ChatColor.AQUA + plugin.getConfig().getString("server_listener_true"));
                }
                if (!Server_Listener) {
                    sender.sendMessage(ChatColor.AQUA + plugin.getConfig().getString("server_listener_false"));
                }
                // 服务器监听玩家加入游戏开关
                if (Player_Join) {
                    sender.sendMessage(ChatColor.DARK_AQUA + plugin.getConfig().getString("player_join_true"));
                }
                if (!Player_Join) {
                    sender.sendMessage(ChatColor.DARK_AQUA + plugin.getConfig().getString("player_join_false"));
                }
                // 服务器监听玩家离开游戏开关
                if (Player_Quit) {
                    sender.sendMessage(ChatColor.AQUA + plugin.getConfig().getString("player_quit_true"));
                }
                if (!Player_Quit) {
                    sender.sendMessage(ChatColor.AQUA + plugin.getConfig().getString("player_quit_false"));
                }
                // 服务器监听玩家死亡事件开关
                if (Player_Death) {
                    sender.sendMessage(ChatColor.DARK_AQUA + plugin.getConfig().getString("player_death_true"));
                }
                if (!Player_Death) {
                    sender.sendMessage(ChatColor.DARK_AQUA + plugin.getConfig().getString("player_death_false"));
                }
                // 服务器监听玩家深度睡眠事件开关
                if (Player_Deep_Sleep) {
                    sender.sendMessage(ChatColor.AQUA + plugin.getConfig().getString("player_deep_sleep_true"));
                }
                if (!Player_Deep_Sleep) {
                    sender.sendMessage(ChatColor.AQUA + plugin.getConfig().getString("player_deep_sleep_false"));
                }
                // 服务器监听玩家活动内容总开关
                sender.sendMessage(ChatColor.YELLOW + hand + ChatColor.GREEN + "玩家行为活动监听列表：");
                if (Player_Listenter) {
                    sender.sendMessage(ChatColor.AQUA + plugin.getConfig().getString("player_listenter_true"));
                }
                if (!Player_Listenter) {
                    sender.sendMessage(ChatColor.AQUA + plugin.getConfig().getString("player_listenter_false"));
                }
            }
            // 修改配置文件
            else if (config_command.equals("change")) {
                if (args.length < 3) {
                    player.sendMessage(ChatColor.RED + "请输入完整的修改指令！");
                    return false;
                }

                String config_changes = args[1];
                // 加载插件参数修改
                if (config_changes.equals("start") || config_changes.equals("start_def")) {
                    plugin.getConfig().set(config_changes, args[2]);
                }

                // 快捷UI界面参数修改
                else if (config_changes.equals("all_ui")) {
                    if (args[2].equalsIgnoreCase("true") || args[2].equalsIgnoreCase("false")) {
                        plugin.getConfig().set(config_changes, Boolean.valueOf(args[2]));
                    } else {
                        player.sendMessage(ChatColor.RED + "参数只能为 true 或 false！");
                        return false;
                    }
                }

                // 服务器监听参数修改
                else if (config_changes.equals("server_listener") || config_changes.equals("player_join") || config_changes.equals("player_quit") || config_changes.equals("player_death") || config_changes.equals("player_deep_sleep")) {
                    if (args[2].equalsIgnoreCase("true") || args[2].equalsIgnoreCase("false")) {
                        plugin.getConfig().set(config_changes, Boolean.valueOf(args[2]));
                    }
                    else {
                        player.sendMessage(ChatColor.RED + "参数只能为 true 或 false！");
                        return false;
                    }
                }

                // 服务器玩家活动监听参数修改
                else if (config_changes.equals("player_listenter")) {
                    if (args[2].equalsIgnoreCase("true") || args[2].equalsIgnoreCase("false")) {
                        plugin.getConfig().set(config_changes, Boolean.valueOf(args[2]));
                    }
                    else {
                        player.sendMessage(ChatColor.RED + "参数只能为 true 或 false！");
                        return false;
                    }
                }

                // 服务器等级系统配置内容
                else if (config_changes.equals("blockbreak") || config_changes.equals("killedCreatures")) {
                    plugin.getConfig().set(config_changes, Integer.valueOf(args[2]));
                }

                // 当找不到config配置时发送
                else {
                    player.sendMessage(ChatColor.RED + "未知的配置项！");
                    return false;
                }

                plugin.saveConfig();
                plugin.reloadConfig();
                player.sendMessage(ChatColor.GREEN + "修改配置成功！");
            }
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            completions.add("start");
            completions.add("list");
            completions.add("change");
        }

        else if (args.length == 2 && args[0].equals("change")) {
            completions.add("start");
            completions.add("start_def");
            completions.add("all_ui");
            completions.add("server_listener");
            completions.add("player_join");
            completions.add("player_quit");
            completions.add("player_death");
            completions.add("player_deep_sleep");
            completions.add("player_listenter");
        }

        else if (args.length == 3 && (args[1].equals("all_ui"))) {
            completions.add("true");
            completions.add("false");
        }

        else if (args.length == 3 && (args[1].equals("server_listener") || args[1].equals("player_join") || args[1].equals("player_quit") || args[1].equals("player_death") || args[1].equals("player_deep_sleep"))) {
            completions.add("true");
            completions.add("false");
        }

        else if (args.length == 3 && (args[1].equals("player_listenter"))) {
            completions.add("true");
            completions.add("false");
        }

        return completions;
    }
}
