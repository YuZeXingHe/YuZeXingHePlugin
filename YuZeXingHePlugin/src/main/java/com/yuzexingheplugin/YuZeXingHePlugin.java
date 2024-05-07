package com.yuzexingheplugin;

import com.yuzexingheplugin.Listener.PlayerListener;
import com.yuzexingheplugin.Listener.SeverListener;
import com.yuzexingheplugin.Command.Commands;
import com.yuzexingheplugin.Command.config;
import com.yuzexingheplugin.PlayerLevel.LevelCommand;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

public final class YuZeXingHePlugin extends JavaPlugin implements Listener {
    private static YuZeXingHePlugin instance;
    String version = "1.9.1Beta-1";

    private File dataFile;
    private YamlConfiguration data;

    @Override
    public void onEnable() {
        // 插件成功启动并运行
        getLogger().info("插件成功运行，感谢您使用YuZeXingHePlugin！开发者：YuZeXingHe！");
        getLogger().info("一款涵盖大部分事件监听、指令优化的插件");
        getLogger().info("当前插件版本：" + version);
        getLogger().info("如果您在使用过程中发现任何Bug，请在GitHub中联系插件开发者！");

        getServer().getScheduler().runTaskTimer(this, this::sendActionBar, 0, 20);
        // 监听器存放处
        getServer().getPluginManager().registerEvents(new SeverListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        getServer().getPluginManager().registerEvents(this, this);
        // 指令存放处
        getCommand("open").setExecutor(new Commands());
        getCommand("configs").setExecutor(new config());
        getCommand("level").setExecutor(new LevelCommand());
        // 指令Tab补全存放处
        getCommand("open").setTabCompleter(new Commands());
        getCommand("configs").setTabCompleter(new config());
        getCommand("level").setTabCompleter(new LevelCommand());
        // 配置文件（保存）
        saveDefaultConfig();

        // 加载或创建数据文件（等级系统）
        dataFile = new File(getDataFolder(), "level.yml");
        if (!dataFile.exists()) {
            saveResource("level.yml", false);
        }
        data = YamlConfiguration.loadConfiguration(dataFile);
    }

    @Override
    public void onDisable() {
        // 插件成功关闭
        getLogger().info("插件关闭成功，感谢您使用本插件！");
        getLogger().info("如果您在使用过程中发现任何Bug，请在GitHub中联系插件开发者！");
    }

    private void sendActionBar() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            int tps = (int) Bukkit.getServer().getTPS()[0];
            int mspt = (int) Bukkit.getAverageTickTime();
            int ping = getPlayerPing(player);
            String pingColor = (ping >= 60) ? "§4" : "§a";
            ItemStack itemInHand = player.getInventory().getItemInMainHand();
            int itemDurability = itemInHand.getType().getMaxDurability() - itemInHand.getDurability();
            String itemDurability_color = (itemDurability >= 128) ? "§a" : "§4";
            // 等级功能
            int level = data.getInt(player.getName() + ".Level");
            double experience = data.getDouble(player.getName() + ".experience");
            String actionBarMessage = "§6手持工具耐久: " + itemDurability_color + itemDurability + " §6活跃等级: §e" + level + " §6经验: §e" + String.format("%.2f", experience) + " §aTPS: " + tps + " MSPT: " + mspt + pingColor + " 延迟: " + ping;
            player.sendActionBar(actionBarMessage);
        }
    }

    private int getPlayerPing(Player player) {
        return player.spigot().getPing();
    }

    public static YuZeXingHePlugin getInstance() {
        return instance;
    }

    // 玩家加入游戏时的处理（等级系统）
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        String playerName = event.getPlayer().getName();

        // 检查玩家数据是否已存在
        if (!data.contains(playerName)) {
            data.set(playerName + ".Level", 0);
            data.set(playerName + ".experience", 0.000000);
            saveData();
        }
    }

    // 保存文件（等级系统）
    public void saveData() {
        try {
            data.save(dataFile);
        }
        catch (IOException e) {
            getLogger().warning("无法保存数据文件: " + dataFile.getName());
        }
    }
}