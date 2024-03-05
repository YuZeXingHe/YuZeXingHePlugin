package com.yuzexingheplugin.Plugin_UI;

import com.yuzexingheplugin.Custom_Formulations.Custom_Formulations;
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

        Boolean All_Formulation = plugin.getConfig().getBoolean("all_formulation");
        Boolean RegisterRecipes_Enchanted_Golden_Apple = plugin.getConfig().getBoolean("registerRecipes_enchanted_golden_apple");
        Boolean Block_Andesite = plugin.getConfig().getBoolean("block_andesite");
        Boolean Block_Granite = plugin.getConfig().getBoolean("block_granite");
        Boolean Block_Diorite = plugin.getConfig().getBoolean("block_diorite");

        if (args.length < 1) {
            player.sendMessage(ChatColor.RED + "请输入正确的参数！");
            return false;
        }

        if (!player.isOp()) {
            player.sendMessage(ChatColor.RED + "你没有权限执行此命令！");
            return false;
        }

        String config_command = args[0];
        if (config_command.equals("start")) {
            player.sendMessage(ChatColor.YELLOW + hand + ChatColor.GREEN + plugin.getConfig().getString("start"));
        }
        else if (config_command.equals("list")) {
            // 自定义配方开关列表
            sender.sendMessage(ChatColor.YELLOW + hand + ChatColor.GREEN + "自定义配方开关列表");
            // 自定义配方总开关状态列表
            if (All_Formulation) {
                sender.sendMessage(ChatColor.AQUA + plugin.getConfig().getString("all_formulation_true"));
            }
            if (!All_Formulation) {
                sender.sendMessage(ChatColor.AQUA + plugin.getConfig().getString("all_formulation_false"));
            }
            // 自定义配方（附魔金苹果）
            if (RegisterRecipes_Enchanted_Golden_Apple) {
                sender.sendMessage(ChatColor.DARK_AQUA + plugin.getConfig().getString("registerRecipes_enchanted_golden_apple_true"));
            }
            if (!RegisterRecipes_Enchanted_Golden_Apple) {
                sender.sendMessage(ChatColor.DARK_AQUA + plugin.getConfig().getString("registerRecipes_enchanted_golden_apple_false"));
            }
            // 自定义配方（安山岩）
            if (Block_Andesite) {
                sender.sendMessage(ChatColor.AQUA + plugin.getConfig().getString("block_andesite_true"));
            }
            if (!Block_Andesite) {
                sender.sendMessage(ChatColor.AQUA + plugin.getConfig().getString("block_andesite_false"));
            }
            // 自定义配方（花岗岩）
            if (Block_Granite) {
                sender.sendMessage(ChatColor.DARK_AQUA + plugin.getConfig().getString("block_granite_true"));
            }
            if (!Block_Granite) {
                sender.sendMessage(ChatColor.DARK_AQUA + plugin.getConfig().getString("block_granite_false"));
            }
            // 自定义配方（闪长岩）
            if (Block_Diorite) {
                sender.sendMessage(ChatColor.AQUA + plugin.getConfig().getString("block_diorite_true"));
            }
            if (!Block_Diorite) {
                sender.sendMessage(ChatColor.AQUA + plugin.getConfig().getString("block_diorite_false"));
            }
        }
        else if (config_command.equals("change")) {
            if (args.length < 3) {
                player.sendMessage(ChatColor.RED + "请输入完整的修改指令！");
                return false;
            }

            String config_changes = args[1];
            if (config_changes.equals("start") || config_changes.equals("start_def")) {
                plugin.getConfig().set(config_changes, args[2]);
            }
            else if (config_changes.equals("all_formulation") || config_changes.equals("registerRecipes_enchanted_golden_apple") || config_changes.equals("block_andesite") || config_changes.equals("block_granite") || config_changes.equals("block_diorite")) {
                if (args[2].equalsIgnoreCase("true") || args[2].equalsIgnoreCase("false")) {
                    plugin.getConfig().set(config_changes, Boolean.valueOf(args[2]));
                }
                else {
                    player.sendMessage(ChatColor.RED + "参数只能为 true 或 false！");
                    return false;
                }
            }
            else {
                player.sendMessage(ChatColor.RED + "未知的配置项！");
                return false;
            }

            plugin.saveConfig();
            plugin.reloadConfig();
            player.sendMessage(ChatColor.GREEN + "修改配置成功！");
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
            completions.add("all_formulation");
            completions.add("registerRecipes_enchanted_golden_apple");
            completions.add("block_andesite");
            completions.add("block_granite");
            completions.add("block_diorite");
        }
        else if (args.length == 3 && (args[1].equals("all_formulation") || args[1].equals("registerRecipes_enchanted_golden_apple") ||
                args[1].equals("block_andesite") || args[1].equals("block_granite") || args[1].equals("block_diorite"))) {
            completions.add("true");
            completions.add("false");
        }

        return completions;
    }
}
