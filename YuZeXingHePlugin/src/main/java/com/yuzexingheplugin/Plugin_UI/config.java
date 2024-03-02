package com.yuzexingheplugin.Plugin_UI;

import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class config implements CommandExecutor, TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        Plugin pl = com.yuzexingheplugin.YuZeXingHePlugin.getProvidingPlugin(com.yuzexingheplugin.YuZeXingHePlugin.class);
        String hand = "[YuZeXingHePlugin]";

        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "请输入正确的参数！");
            return false;
        }

        String config_command = args[0];
        if (player.isOp()) {
            if (config_command.equals("start")) {
                sender.sendMessage(ChatColor.AQUA + hand + ChatColor.GREEN + pl.getConfig().getString("start"));
            }
            else if (config_command.equals("change")) {

                if (args.length < 3) {
                    sender.sendMessage(ChatColor.RED + "请输入完整的修改指令！");
                    return false;
                }

                String config_changes = args[1];
                if (config_changes.equals("start")) {
                    pl.getConfig().set("start", args[2]);
                    sender.sendMessage(ChatColor.GREEN + "修改config文件成功！");
                }
                else if (config_changes.equals("start_def")) {
                    pl.getConfig().set("start_def", args[2]);
                    sender.sendMessage(ChatColor.GREEN + "修改config文件成功！");
                }
                else {
                    sender.sendMessage(ChatColor.RED + "未知的指令，请确认指令是否输入完整且正确！");
                }
                pl.saveConfig();
                pl.reloadConfig();
            }
        }
        else {
            sender.sendMessage(ChatColor.AQUA + hand + ChatColor.RED + pl.getConfig().getString("start_def"));
        }

        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            List<String> list = new ArrayList<>();
            list.add("start");
            list.add("change");
            return list;
        }
        else if (args.length == 2 && args[0].equals("change")) {
            List<String> list = new ArrayList<>();
            list.add("start");
            list.add("start_def");
            return list;
        }
        return null;
    }
}
