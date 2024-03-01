package com.yuzexingheplugin.Custom_Formulations;

import com.yuzexingheplugin.YuZeXingHePlugin;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class Custom_Formulations {
    // 附魔金苹果
    public static void registerRecipes_enchanted_golden_apple() {
        ShapedRecipe enchantedGoldenAppleRecipe = new ShapedRecipe(new ItemStack(Material.ENCHANTED_GOLDEN_APPLE));
        enchantedGoldenAppleRecipe.shape("xxx", "xyx", "xxx");
        enchantedGoldenAppleRecipe.setIngredient('x', Material.GOLD_BLOCK);
        enchantedGoldenAppleRecipe.setIngredient('y', Material.APPLE);
        YuZeXingHePlugin.getPlugin(YuZeXingHePlugin.class).getServer().addRecipe(enchantedGoldenAppleRecipe);
    }

    // 安山岩
    public static void block_andesite() {
        ShapedRecipe andesite = new ShapedRecipe(new ItemStack(Material.ANDESITE));
        andesite.shape("xxx", "xxx", "xxx");
        andesite.setIngredient('x', Material.COBBLESTONE);
        YuZeXingHePlugin.getPlugin(YuZeXingHePlugin.class).getServer().addRecipe(andesite);
    }

    // 花岗岩
    public static void block_granite() {
        ShapedRecipe granite = new ShapedRecipe(new ItemStack(Material.GRANITE));
        granite.shape("xxx", "xxx", "xxx");
        granite.setIngredient('x', Material.STONE);
        YuZeXingHePlugin.getPlugin(YuZeXingHePlugin.class).getServer().addRecipe(granite);
    }

    // 闪长岩
    public static void block_diorite() {
        ShapedRecipe diortie = new ShapedRecipe(new ItemStack(Material.DIORITE));
        diortie.shape("xxx", "xxx", "xxx");
        diortie.setIngredient('x', Material.SMOOTH_STONE);
        YuZeXingHePlugin.getPlugin(YuZeXingHePlugin.class).getServer().addRecipe(diortie);
    }
}
