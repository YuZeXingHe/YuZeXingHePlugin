package com.yuzexingheplugin;

import com.yuzexingheplugin.Custom_Formulations.Custom_Formulations;
import com.yuzexingheplugin.Plugin_UI.OpenUI_CMD;
import com.yuzexingheplugin.Plugin_UI.config;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public final class YuZeXingHePlugin extends JavaPlugin {
    private static YuZeXingHePlugin instance;

    @Override
    public void onEnable() {
        // 插件成功启动并运行
        getLogger().info("插件成功运行，感谢您使用YuZeXingHePlugin！开发者：YuZeXingHe！");
        getLogger().info("一款自己写的涵盖事件、指令优化的插件");
        getLogger().info("当前插件版本：1.8.4");

        getServer().getScheduler().runTaskTimer(this, this::sendActionBar, 0, 20);
        // 监听器存放处
        getServer().getPluginManager().registerEvents(new SeverListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        // 自定义配方存放处
        Custom_Formulations.registerRecipes_enchanted_golden_apple();
        Custom_Formulations.block_andesite();
        Custom_Formulations.block_diorite();
        Custom_Formulations.block_granite();
        // 指令存放处
        getCommand("ui").setExecutor(new OpenUI_CMD());
        getCommand("uiconfig").setExecutor(new config());
        // 指令Tab补全存放处
        getCommand("ui").setTabCompleter(new OpenUI_CMD());
        getCommand("uiconfig").setTabCompleter(new config());
        // 配置文件
        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        // 插件成功关闭
        getLogger().info("插件关闭成功，感谢您使用本插件");
        getLogger().info("如果您在使用过程中发现任何Bug，请在群聊中联系插件开发者！");
    }

    private void sendActionBar() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            int health = (int) player.getHealth();
            int tps = (int) Bukkit.getServer().getTPS()[0];
            int mspt = (int) Bukkit.getAverageTickTime();
            int ping = getPlayerPing(player);
            String pingColor = (ping >= 60) ? "§4" : "§a";
            ItemStack itemInHand = player.getInventory().getItemInMainHand();
            int itemDurability = itemInHand.getType().getMaxDurability() - itemInHand.getDurability();
            String itemDurability_color = (itemDurability >= 128) ? "§a" : "§4";
            player.sendActionBar("§4Health: " + health + " §6TPS: " + tps + " §6MSPT: " + mspt + pingColor + " Ping：" + ping + " §6Durability：" + itemDurability_color + itemDurability);
        }
    }

    private int getPlayerPing(Player player) {
        return player.spigot().getPing();
    }

    public static YuZeXingHePlugin getInstance() {
        return instance;
    }
}