package com.yuzexingheplugin;

import io.papermc.paper.event.player.PlayerDeepSleepEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

public class SeverListener implements Listener {

    public static Plugin pl = com.yuzexingheplugin.YuZeXingHePlugin.getProvidingPlugin(com.yuzexingheplugin.YuZeXingHePlugin.class);
    String hand = "[YuZeXingHePlugin]";

    // UI界面监听开关
    public static boolean All_UI() {
        return pl.getConfig().getBoolean("all_ui");
    }
    // 服务器监听开关
    public static boolean Server_Listener() {
        return pl.getConfig().getBoolean("server_listener");
    }
    // 服务器玩家加入事件监听开关
    public static boolean Player_Join_Game() {
        return pl.getConfig().getBoolean("player_join");
    }
    // 服务器玩家离开事件开关
    public static boolean Player_Quit_Game() {
        return pl.getConfig().getBoolean("player_quit");
    }
    // 服务器玩家死亡事件开关
    public static boolean Players_Death() {
        return pl.getConfig().getBoolean("player_death");
    }
    // 服务器玩家进入深度睡眠事件
    public static boolean Players_Deep_Sleep() {
        return pl.getConfig().getBoolean("player_deep_sleep");
    }

    // UI界面监听（玩家使用UI界面时触发）
    @EventHandler
    public void check(InventoryClickEvent left_click) {
        Player player = (Player) left_click.getWhoClicked();
            if (Server_Listener()) {
                if (All_UI()) {
                if (left_click.getWhoClicked().getOpenInventory().getTitle().equals("YuZeXingHe快捷UI：第1页")) {
                    left_click.setCancelled(true);
                    // UI界面：将玩家的游戏切换为创造模式（管理员）
                    if (left_click.getRawSlot() == 0) {
                        if (player.isOp()) {
                            player.setGameMode(GameMode.CREATIVE);
                            player.sendMessage(ChatColor.GREEN + "所选功能执行成功！");
                            player.sendMessage(ChatColor.YELLOW + "成功将" + player.getName() + "的游戏模式设置为创造模式！");
                        } else {
                            player.sendMessage(ChatColor.RED + "你无权使用这个命令！");
                        }
                    }

                    // UI界面：将玩家的游戏模式设置为生存模式（管理员）
                    else if (left_click.getRawSlot() == 1) {
                        if (player.isOp()) {
                            player.setGameMode(GameMode.SURVIVAL);
                            player.sendMessage(ChatColor.GREEN + "所选功能执行成功！");
                            player.sendMessage(ChatColor.YELLOW + "成功将" + player.getName() + "的游戏模式设置为生存模式！");
                        } else {
                            player.sendMessage(ChatColor.RED + "你无权使用这个命令！");
                        }
                    }

                    // UI界面：将玩家的游戏模式设置为冒险模式（管理员）
                    else if (left_click.getRawSlot() == 2) {
                        if (player.isOp()) {
                            player.setGameMode(GameMode.ADVENTURE);
                            player.sendMessage(ChatColor.GREEN + "所选功能执行成功！");
                            player.sendMessage(ChatColor.YELLOW + "成功将" + player.getName() + "的游戏模式设置为冒险模式！");
                        } else {
                            player.sendMessage(ChatColor.RED + "你无权使用这个命令！");
                        }
                    }

                    // UI界面：将玩家的游戏模式设置为旁观者模式（管理员）
                    else if (left_click.getRawSlot() == 3) {
                        if (player.isOp()) {
                            player.setGameMode(GameMode.SPECTATOR);
                            player.sendMessage(ChatColor.GREEN + "所选功能执行成功！");
                            player.sendMessage(ChatColor.YELLOW + "成功将" + player.getName() + "的游戏模式设置为旁观者模式！");
                        } else {
                            player.sendMessage(ChatColor.RED + "你无权使用这个命令！");
                        }
                    }

                    // UI界面：关闭服务器功能（管理员）
                    else if (left_click.getRawSlot() == 4) {
                        if (player.isOp()) {
                            player.sendMessage(ChatColor.YELLOW + "所选功能执行成功，即将关闭服务器");
                            Bukkit.getServer().shutdown();
                        } else {
                            player.sendMessage(ChatColor.RED + "你无权使用这个命令！");
                        }
                    }

                    // UI界面：随身织布机功能（所有玩家）
                    else if (left_click.getRawSlot() == 5) {
                        player.openLoom(null, true);
                        player.sendMessage(ChatColor.GREEN + "所选功能执行成功！");
                    }

                    // UI界面：随身铁砧功能（所有玩家）
                    else if (left_click.getRawSlot() == 6) {
                        player.openAnvil(null, true);
                        player.sendMessage(ChatColor.GREEN + "所选功能执行成功！");
                    }

                    // UI界面：随身工作台功能（所有玩家）
                    else if (left_click.getRawSlot() == 7) {
                        player.openWorkbench(null, true);
                        player.sendMessage(ChatColor.GREEN + "所选功能执行成功！");
                    }

                    // UI界面：玩家自杀功能（所有玩家）
                    else if (left_click.getRawSlot() == 8) {
                        player.sendMessage(ChatColor.GREEN + "自杀成功！");
                        player.setHealth(0);
                    }

                    // UI界面：清空自己的背包（所有玩家）
                    else if (left_click.getRawSlot() == 53) {
                        player.getInventory().clear();
                        player.saveData();
                        player.sendMessage(ChatColor.RED + "成功清空你的背包！");
                    }
                }
                if (left_click.getRawSlot() > left_click.getInventory().getSize()) {
                    return;
                }
                if (left_click.getRawSlot() < 0) {
                    return;
                }
            }
        }
        else {
            player.sendMessage(ChatColor.RED + "服务器尚未启用该命令！");
        }
    }

    // 玩家加入服务器事件
    @EventHandler
    public void Player_Join(PlayerJoinEvent event) {
            if (Server_Listener()) {
                if (Player_Join_Game()) {
                Player player = event.getPlayer();
                String player_name = player.getName();
                event.setJoinMessage(ChatColor.YELLOW + "加入服务器：欢迎" + ChatColor.AQUA + player_name + ChatColor.YELLOW + "加入服务器！");
                player.sendMessage(ChatColor.YELLOW + "使用/ui help来查看必要指令帮助！");
            }
        }
    }

    // 玩家离开服务器事件
    @EventHandler
    public void Player_Quit(PlayerQuitEvent event) {
            if (Server_Listener()) {
                if (Player_Quit_Game()) {
                Player player = event.getPlayer();
                String player_name = player.getName();
                event.setQuitMessage(ChatColor.YELLOW + "离开服务器：" + ChatColor.AQUA + player_name + ChatColor.YELLOW + "离开了服务器！");
            }
        }
    }

    // 玩家死亡事件
    @EventHandler
    public void Player_Death(PlayerDeathEvent event) {
        if (Server_Listener()) {
            if (Players_Death()) {
                Player player = event.getPlayer();
                String player_name = player.getName();
                int deathCount = player.getStatistic(org.bukkit.Statistic.DEATHS);
                Location playerLocation = player.getLocation();
                if (deathCount <= 1) {
                    event.setDeathMessage(ChatColor.YELLOW + "死亡消息：" + player_name + "给自己玩没了，服务器直接笑TA！当前死亡次数：" + deathCount);
                } else {
                    event.setDeathMessage(ChatColor.YELLOW + "死亡消息：" + player_name + "又给自己玩没了，服务器已经笑岔气了！当前死亡次数：" + deathCount);
                }
            }
        }
    }

    // 玩家进入深度睡眠事件
    @EventHandler
    public void Player_DeepSleep(PlayerDeepSleepEvent event) {
        if (Server_Listener()) {
            if (Players_Deep_Sleep()) {
                Player player = event.getPlayer();
                player.sendMessage(ChatColor.YELLOW + "睡一觉，明天就没有幻翼啦，晚安！");
            }
        }
    }
}
