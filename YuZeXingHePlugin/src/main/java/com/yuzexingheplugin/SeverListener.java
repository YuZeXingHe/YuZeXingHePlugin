package com.yuzexingheplugin;

import io.papermc.paper.event.player.PlayerDeepSleepEvent;
import net.md_5.bungee.api.ChatMessageType;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerCommandSendEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Furnace;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import static org.bukkit.Bukkit.getLogger;

public class SeverListener implements Listener {
    @EventHandler
    public void Player_Join(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String player_name = player.getName();
        event.setJoinMessage(ChatColor.YELLOW + "加入服务器：欢迎" + ChatColor.AQUA + player_name + ChatColor.YELLOW + "加入服务器！");
        player.sendMessage(ChatColor.YELLOW + "使用/ui help来查看必要指令帮助！");
    }

    @EventHandler
    public void Player_Quit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        String player_name = player.getName();
        event.setQuitMessage(ChatColor.YELLOW + "离开服务器：" + ChatColor.AQUA + player_name + ChatColor.YELLOW + "离开了服务器！");
    }

    @EventHandler
    public void Player_Death(PlayerDeathEvent event) {
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

    @EventHandler
    public void Player_DeepSleep(PlayerDeepSleepEvent event) {
        Player player = event.getPlayer();
        player.sendMessage(ChatColor.YELLOW + "睡一觉，明天就没有幻翼啦，晚安！");
    }

    @EventHandler
    public void check(InventoryClickEvent left_click) {
        Player player = (Player) left_click.getWhoClicked();
        if (left_click.getWhoClicked().getOpenInventory().getTitle().equals("YuZeXingHe快捷UI：第1页")) {
            left_click.setCancelled(true);
            if (left_click.getRawSlot() == 0) {
                if (player.isOp()) {
                    player.setGameMode(GameMode.CREATIVE);
                    player.sendMessage(ChatColor.GREEN + "所选功能执行成功！");
                    player.sendMessage(ChatColor.YELLOW + "成功将" + player.getName() + "的游戏模式设置为创造模式！");
                }
                else {
                    player.sendMessage(ChatColor.RED + "你无权使用这个命令！");
                }
            }
            else if (left_click.getRawSlot() == 1) {
                if (player.isOp()) {
                    player.setGameMode(GameMode.SURVIVAL);
                    player.sendMessage(ChatColor.GREEN + "所选功能执行成功！");
                    player.sendMessage(ChatColor.YELLOW + "成功将" + player.getName() + "的游戏模式设置为生存模式！");
                }
                else {
                    player.sendMessage(ChatColor.RED + "你无权使用这个命令！");
                }
            }
            else if (left_click.getRawSlot() == 2) {
                if (player.isOp()) {
                    player.setGameMode(GameMode.ADVENTURE);
                    player.sendMessage(ChatColor.GREEN + "所选功能执行成功！");
                    player.sendMessage(ChatColor.YELLOW + "成功将" + player.getName() + "的游戏模式设置为冒险模式！");
                }
                else {
                    player.sendMessage(ChatColor.RED + "你无权使用这个命令！");
                }
            }
            else if (left_click.getRawSlot() == 3) {
                if (player.isOp()) {
                    player.setGameMode(GameMode.SPECTATOR);
                    player.sendMessage(ChatColor.GREEN + "所选功能执行成功！");
                    player.sendMessage(ChatColor.YELLOW + "成功将" + player.getName() + "的游戏模式设置为旁观者模式！");
                }
                else {
                    player.sendMessage(ChatColor.RED + "你无权使用这个命令！");
                }
            }
            else if (left_click.getRawSlot() == 4) {
                if (player.isOp()) {
                    player.sendMessage(ChatColor.YELLOW + "所选功能执行成功，即将关闭服务器");
                    Bukkit.getServer().shutdown();
                }
                else {
                    player.sendMessage(ChatColor.RED + "你无权使用这个命令！");
                }
            }
            else if (left_click.getRawSlot() == 5) {
                player.openLoom(null, true);
                player.sendMessage(ChatColor.GREEN + "所选功能执行成功！");
            }
            else if (left_click.getRawSlot() == 6) {
                player.openAnvil(null, true);
                player.sendMessage(ChatColor.GREEN + "所选功能执行成功！");
            }
            else if (left_click.getRawSlot() == 7) {
                player.openWorkbench(null, true);
                player.sendMessage(ChatColor.GREEN + "所选功能执行成功！");
            }
            else if (left_click.getRawSlot() == 8) {
                player.sendMessage(ChatColor.GREEN + "自杀成功！");
                player.setHealth(0);
            }
            else if (left_click.getRawSlot() == 35) {
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
