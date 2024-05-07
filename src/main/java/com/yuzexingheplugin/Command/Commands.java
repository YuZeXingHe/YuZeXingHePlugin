package com.yuzexingheplugin.Command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Commands implements CommandExecutor, TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = (Player) sender;
        Plugin plugin = com.yuzexingheplugin.YuZeXingHePlugin.getProvidingPlugin(com.yuzexingheplugin.YuZeXingHePlugin.class);
        String hand = "[YuZeXingHePlugin]";

        boolean All_UI = plugin.getConfig().getBoolean("all_ui");

        if (All_UI) {
            String Command1 = args[0];
            if (Command1.equals("open")) {
                sender.sendMessage(ChatColor.GREEN + "成功打开插件快捷菜单。");
                Inventory GUI = Bukkit.createInventory(null, 6 * 9, "YuZeXingHe快捷UI：第1页");

                // 获取物品信息
                ItemStack grass_block = new ItemStack(Material.GRASS_BLOCK);
                ItemStack iron_sword = new ItemStack(Material.IRON_SWORD);
                ItemStack map = new ItemStack(Material.MAP);
                ItemStack ender_eye = new ItemStack(Material.ENDER_EYE);
                ItemStack barrier = new ItemStack(Material.BARRIER);
                ItemStack chafting_table = new ItemStack(Material.CRAFTING_TABLE);
                ItemStack anvil = new ItemStack(Material.ANVIL);
                ItemStack loom = new ItemStack(Material.LOOM);
                ItemStack wooden_axe = new ItemStack(Material.WOODEN_AXE);
                ItemStack chest = new ItemStack(Material.CHEST);

                // 设置UI中物品的一个name标签，用于申明点击物品产生什么作用（非常友好地提示和警醒玩家不要乱特么点！）
                ItemMeta grass_block_meta = grass_block.getItemMeta();
                grass_block_meta.setDisplayName("§a切换成创造模式§4（管理员）");
                grass_block.setItemMeta(grass_block_meta);

                ItemMeta iron_sword_meta = iron_sword.getItemMeta();
                iron_sword_meta.setDisplayName("§a切换成生存模式§4（管理员）");
                iron_sword.setItemMeta(iron_sword_meta);

                ItemMeta map_meta = map.getItemMeta();
                map_meta.setDisplayName("§a切换成冒险模式§4（管理员）");
                map.setItemMeta(map_meta);

                ItemMeta ender_eye_meta = ender_eye.getItemMeta();
                ender_eye_meta.setDisplayName("§a切换成旁观者模式§4（管理员）");
                ender_eye.setItemMeta(ender_eye_meta);

                ItemMeta barrier_meta = barrier.getItemMeta();
                barrier_meta.setDisplayName("§a关闭服务器§4（管理员）");
                barrier.setItemMeta(barrier_meta);

                ItemMeta chafting_table_meta = chafting_table.getItemMeta();
                chafting_table_meta.setDisplayName("§a随身工作台");
                chafting_table.setItemMeta(chafting_table_meta);

                ItemMeta anvil_meta = anvil.getItemMeta();
                anvil_meta.setDisplayName("§a随身铁砧");
                anvil.setItemMeta(anvil_meta);

                ItemMeta loom_meta = loom.getItemMeta();
                loom_meta.setDisplayName("§a随身织布机");
                loom.setItemMeta(loom_meta);

                ItemMeta wooden_axe_meta = wooden_axe.getItemMeta();
                wooden_axe_meta.setDisplayName("§4杀死自己");
                wooden_axe.setItemMeta(wooden_axe_meta);

                ItemMeta chest_meta = chest.getItemMeta();
                chest_meta.setDisplayName("§4清空背包");
                chest.setItemMeta(chest_meta);

                // 设置物品Lore标签内容的地方（必要时可以加入颜色来分别身份组作用和一些重要功能及一些谨慎触及的功能！）
                List<String> grass_block_lore = new ArrayList<>();
                grass_block_lore.add("§a切换玩家的游戏模式");
                grass_block.setLore(grass_block_lore);

                List<String> iron_sword_lore = new ArrayList<>();
                iron_sword_lore.add("§a切换玩家的游戏模式");
                iron_sword.setLore(iron_sword_lore);

                List<String> map_lore = new ArrayList<>();
                map_lore.add("§a切换玩家的游戏模式");
                map.setLore(map_lore);

                List<String> ender_eye_lore = new ArrayList<>();
                ender_eye_lore.add("§a切换玩家的游戏模式");
                ender_eye.setLore(ender_eye_lore);

                List<String> barrier_lore = new ArrayList<>();
                barrier_lore.add("§a一键关闭服务器");
                barrier.setLore(barrier_lore);

                List<String> chafting_table_lore = new ArrayList<>();
                chafting_table_lore.add("§a打开自己的随身工作台");
                chafting_table.setLore(chafting_table_lore);

                List<String> anvil_lore = new ArrayList<>();
                anvil_lore.add("§a打开自己的随身铁砧");
                anvil.setLore(anvil_lore);

                List<String> loom_lore = new ArrayList<>();
                loom_lore.add("§a打开自己的随身织布机");
                loom.setLore(loom_lore);

                List<String> wooden_axe_lore = new ArrayList<>();
                wooden_axe_lore.add("建议不要随便使用！");
                wooden_axe.setLore(wooden_axe_lore);

                List<String> chest_lore = new ArrayList<>();
                chest_lore.add("真的会将背包清空，推荐清理垃圾用！");
                chest.setLore(chest_lore);

                // 将物品放置在UI的哪个位置，建议不要随意更改（在SeverLister中绑定了这些事项！）
                GUI.setItem(0, grass_block);
                GUI.setItem(1, iron_sword);
                GUI.setItem(2, map);
                GUI.setItem(3, ender_eye);
                GUI.setItem(4, barrier);
                GUI.setItem(5, loom);
                GUI.setItem(6, anvil);
                GUI.setItem(7, chafting_table);
                GUI.setItem(8, wooden_axe);
                GUI.setItem(53, chest);

                player.openInventory(GUI);
                return true;
            }

            // 帮助指令和页面（仅一些重要指令！）
            else if (Command1.equals("help")) {
                sender.sendMessage(ChatColor.YELLOW + "感谢您使用YuZePlugin，下面是一些服务器常用指令帮助：");
                sender.sendMessage(ChatColor.DARK_AQUA + "/ui open：打开本插件的随身ui界面，含随身工作台，管理员等功能。");
                sender.sendMessage(ChatColor.AQUA + "/ui help：弹出本帮助。");
                sender.sendMessage(ChatColor.DARK_AQUA + "/reg 密码 确认密码：注册指令，第一次进入必须要进行注册。");
                sender.sendMessage(ChatColor.AQUA + "/login 密码：登录到服务器。");
                if (player.isOp()) {
                    sender.sendMessage(ChatColor.DARK_AQUA + "/uiconfig start：加载配置文件（须在服务器中以管理员身份使用该指令）。");
                    sender.sendMessage(ChatColor.AQUA + "/uiconfig change：修改配置文件（须在服务器中以管理员身份使用该指令）。");
                }
                return true;
            }
            else {
                sender.sendMessage(ChatColor.RED + "未知的指令，请检查指令是否拼写正确！");
            }
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            List<String> list = new ArrayList<>();
            list.add("open");
            list.add("help");
            return list;
        }
        return null;
    }
}
