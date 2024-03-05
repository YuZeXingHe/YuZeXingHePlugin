package com.yuzexingheplugin.Custom_Formulations;

import com.yuzexingheplugin.YuZeXingHePlugin;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;

public class Custom_Formulations {
    public static Plugin pl = com.yuzexingheplugin.YuZeXingHePlugin.getProvidingPlugin(com.yuzexingheplugin.YuZeXingHePlugin.class);
    String hand = "[YuZeXingHePlugin]";

    // 自定义配方总开关
    public static boolean isAllFormulationsEnabled() {
        return pl.getConfig().getBoolean("all_formulation");
    }
    // 附魔金苹果配方开关
    public static boolean isRegisterRecipes_Enchanted_Golden_Apple() {
        return pl.getConfig().getBoolean("registerRecipes_enchanted_golden_apple");
    }
    // 安山岩配方开关
    public static boolean isBlock_Andesite() {
        return pl.getConfig().getBoolean("block_andesite");
    }
    // 花岗岩自定义配方
    public static boolean isBlock_Granite() {
        return pl.getConfig().getBoolean("block_granite");
    }
    // 闪长岩自定义配方
    public static boolean isBlock_Diorite() {
        return pl.getConfig().getBoolean("block_diorite");
    }

    // 附魔金苹果
    public static void registerRecipes_enchanted_golden_apple() {
        if (isAllFormulationsEnabled()) {
            if (isRegisterRecipes_Enchanted_Golden_Apple()) {
                ShapedRecipe enchantedGoldenAppleRecipe = new ShapedRecipe(new ItemStack(Material.ENCHANTED_GOLDEN_APPLE));
                enchantedGoldenAppleRecipe.shape("xxx", "xyx", "xxx");
                enchantedGoldenAppleRecipe.setIngredient('x', Material.GOLD_BLOCK);
                enchantedGoldenAppleRecipe.setIngredient('y', Material.APPLE);
                YuZeXingHePlugin.getPlugin(YuZeXingHePlugin.class).getServer().addRecipe(enchantedGoldenAppleRecipe);
            }
        }
    }

    // 安山岩
    public static void block_andesite() {
        if (isAllFormulationsEnabled()) {
            if (isBlock_Andesite()) {
                ShapedRecipe andesite = new ShapedRecipe(new ItemStack(Material.ANDESITE));
                andesite.shape("xxx", "xxx", "xxx");
                andesite.setIngredient('x', Material.COBBLESTONE);
                YuZeXingHePlugin.getPlugin(YuZeXingHePlugin.class).getServer().addRecipe(andesite);
            }
        }
    }

    // 花岗岩
    public static void block_granite() {
        if (isAllFormulationsEnabled()) {
            if (isBlock_Granite()) {
                ShapedRecipe granite = new ShapedRecipe(new ItemStack(Material.GRANITE));
                granite.shape("xxx", "xxx", "xxx");
                granite.setIngredient('x', Material.STONE);
                YuZeXingHePlugin.getPlugin(YuZeXingHePlugin.class).getServer().addRecipe(granite);
            }
        }
    }

    // 闪长岩
    public static void block_diorite() {
        if (isAllFormulationsEnabled()) {
            if (isBlock_Diorite()) {
                ShapedRecipe diortie = new ShapedRecipe(new ItemStack(Material.DIORITE));
                diortie.shape("xxx", "xxx", "xxx");
                diortie.setIngredient('x', Material.SMOOTH_STONE);
                YuZeXingHePlugin.getPlugin(YuZeXingHePlugin.class).getServer().addRecipe(diortie);
            }
        }
    }
}
