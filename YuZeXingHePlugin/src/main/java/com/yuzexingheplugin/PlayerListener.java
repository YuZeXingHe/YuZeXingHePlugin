package com.yuzexingheplugin;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.Lever;
import org.bukkit.plugin.Plugin;

import javax.sound.midi.MidiFileFormat;
import java.util.Map;

public class PlayerListener implements Listener {

    public static Plugin pl = com.yuzexingheplugin.YuZeXingHePlugin.getProvidingPlugin(com.yuzexingheplugin.YuZeXingHePlugin.class);

    public static Boolean Player_Listenter() {
        return pl.getConfig().getBoolean("player_listenter");
    }

    // 玩家破坏方块事件
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (Player_Listenter()) {
            Player player = event.getPlayer();
            Block block = event.getBlock();
            Location location = block.getLocation();

            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + player.getName() + ChatColor.RED + "破坏了方块：" + ChatColor.DARK_AQUA + block.getType().toString() + "。" + ChatColor.GREEN + "破坏的方块坐标为：" + location.toString());
        }
    }

    // 玩家放置方块事件
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (Player_Listenter()) {
            Player player = event.getPlayer();
            Block block = event.getBlock();
            Location location = block.getLocation();

            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + player.getName() + ChatColor.RED + "放置了方块：" + ChatColor.DARK_AQUA + block.getType().toString() + "。" + ChatColor.GREEN + "放置的方块坐标为：" + location.toString());
        }
    }

    // 玩家倒水事件
    @EventHandler
    public void onPlayerBucketEmpty(PlayerBucketEmptyEvent event) {
        if (Player_Listenter()) {
            Player player = event.getPlayer();
            Block block = event.getBlockClicked().getRelative(event.getBlockFace());
            Location location = block.getLocation();

            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + player.getName() + ChatColor.YELLOW + "在方块：" + ChatColor.DARK_AQUA + block.getType().toString() + ChatColor.GREEN + "上倒水，坐标为：" + location.toString());
        }
    }

    // 玩家使用空桶收集水事件
    @EventHandler
    public void onPlayerBucketFill(PlayerBucketFillEvent event) {
        if (Player_Listenter()) {
            Player player = event.getPlayer();
            Block block = event.getBlockClicked().getRelative(event.getBlockFace());
            Location location = block.getLocation();

            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + player.getName() + ChatColor.YELLOW + "在方块：" + ChatColor.DARK_AQUA + block.getType().toString() + ChatColor.GREEN + "中采集了坐标为：" + location.toString() + "的水");
        }
    }

    // 玩家倾倒和收集岩浆事件
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (Player_Listenter()) {
            Player player = event.getPlayer();
            Location location = player.getLocation();

            if (event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.MAGMA_BLOCK) {
                Location magmaLocation = event.getClickedBlock().getLocation();
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + player.getName() + ChatColor.GREEN + "在坐标" + location.getX() + ", " + location.getY() + ", " + location.getZ() + ChatColor.RED + "倒岩浆了。" + ChatColor.GREEN + "岩浆坐标为" + magmaLocation.getX() + ", " + magmaLocation.getY() + ", " + magmaLocation.getZ());
            }

            if (event.getMaterial() == Material.BUCKET && event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.MAGMA_BLOCK) {
                Location magmaLocation = event.getClickedBlock().getLocation();
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + player.getName() + ChatColor.GREEN + "在坐标" + location.getX() + ", " + location.getY() + ", " + location.getZ() + ChatColor.RED + "收集了岩浆。" + ChatColor.GREEN + "岩浆坐标为" + magmaLocation.getX() + ", " + magmaLocation.getY() + ", " + magmaLocation.getZ());
            }
        }
    }

    // 玩家丢弃物品事件
    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        if (Player_Listenter()) {
            Player player = event.getPlayer();
            Item droppedItem = event.getItemDrop();
            Location location = droppedItem.getLocation();

            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + player.getName() + ChatColor.YELLOW + "丢弃了物品：" + ChatColor.DARK_AQUA + droppedItem.getItemStack().getType().toString() + ChatColor.GREEN + "物品丢在：" + location.toString());
        }
    }

    // 玩家拾取物品事件
    @EventHandler
    public void onPlayerPickupItem(PlayerPickupItemEvent event) {
        if (Player_Listenter()) {
            Player player = event.getPlayer();
            Item pickedItem = event.getItem();
            Location location = pickedItem.getLocation();

            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + player.getName() + ChatColor.GREEN + "在坐标：" + location.toString() + ChatColor.YELLOW + "拾取了物品：" + ChatColor.DARK_AQUA + pickedItem.getItemStack().getType().toString());
        }
    }

    // 玩家打开箱子事件
    @EventHandler
    public void onPlayerOpenChest(PlayerInteractEvent event) {
        if (Player_Listenter()) {
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                Block clickedBlock = event.getClickedBlock();
                if (clickedBlock.getState() instanceof Chest) {
                    Player player = event.getPlayer();
                    Chest chest = (Chest) clickedBlock.getState();
                    Location location = chest.getLocation();

                    Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + player.getName() + ChatColor.RED + "打开了箱子。" + ChatColor.GREEN + "箱子坐标：" + location.toString());
                }
            }
        }
    }

    // 玩家从箱子中取出物品事件
    @EventHandler
    public void onPlayerTakeFromChest(InventoryClickEvent event) {
        if (Player_Listenter()) {
            Player player = (Player) event.getWhoClicked();
            Inventory clickedInventory = event.getClickedInventory();
            ClickType clickType = event.getClick();

            if (clickedInventory != null && clickedInventory.getType() == InventoryType.CHEST && clickType == ClickType.LEFT) {
                ItemStack clickedItem = event.getCurrentItem();

                if (clickedItem != null && event.getRawSlot() < clickedInventory.getSize()) {
                    Location location = clickedInventory.getLocation();

                    if (location != null) {
                        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + player.getName() + ChatColor.RED + "从箱子中取出了物品 " + ChatColor.DARK_AQUA + clickedItem.getType().toString() + "。" + ChatColor.GREEN + "坐标：" + location.toString());
                    }
                    else {
                        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + player.getName() + ChatColor.RED + "从随身UI界面取出了物品 " + ChatColor.DARK_AQUA + clickedItem.getType().toString());
                    }
                }
            }
        }
    }

    // 玩家打开木桶事件
    @EventHandler
    public void onPlayerOpenBarrel(PlayerInteractEvent event) {
        if (Player_Listenter()) {
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                Block clickedBlock = event.getClickedBlock();
                if (clickedBlock.getState() instanceof Barrel) {
                    Player player = event.getPlayer();
                    Barrel barrel = (Barrel) clickedBlock.getState();
                    Location location = barrel.getLocation();

                    Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + player.getName() + ChatColor.RED + "打开了木桶。" + "木桶坐标：" + ChatColor.GREEN + location.toString());
                }
            }
        }
    }

    // 玩家从木桶中取出物品
    @EventHandler
    public void onPlayerTakeFromBarrel(InventoryClickEvent event) {
        if (Player_Listenter()) {
            Player player = (Player) event.getWhoClicked();
            Inventory clickedInventory = event.getClickedInventory();
            ClickType clickType = event.getClick();

            if (clickedInventory != null && clickedInventory.getType() == InventoryType.BARREL && clickType == ClickType.LEFT) {
                ItemStack clickedItem = event.getCurrentItem();

                if (clickedItem != null && event.getRawSlot() < clickedInventory.getSize()) {
                    Location location = clickedInventory.getLocation();

                    Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + player.getName() + ChatColor.RED + "从木桶中取出了物品 " + ChatColor.DARK_AQUA + clickedItem.getType().toString() + "。" + ChatColor.GREEN + "坐标：" + location.toString());
                }
            }
        }
    }

    // 玩家打开潜影盒事件
    @EventHandler
    public void onPlayerOpenShulkerBox(PlayerInteractEvent event) {
        if (Player_Listenter()) {
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                Block clickedBlock = event.getClickedBlock();
                if (clickedBlock.getState() instanceof ShulkerBox) {
                    Player player = event.getPlayer();
                    ShulkerBox shulkerBox = (ShulkerBox) clickedBlock.getState();
                    Location location = shulkerBox.getLocation();

                    Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + player.getName() + ChatColor.RED + "打开了潜影盒。" + ChatColor.GREEN + "潜影盒坐标：" + location.toString());
                }
            }
        }
    }

    // 玩家从潜影盒中取出物品事件
    @EventHandler
    public void onPlayerTakeFromShulkerBox(InventoryClickEvent event) {
        if (Player_Listenter()) {
            Player player = (Player) event.getWhoClicked();
            Inventory clickedInventory = event.getClickedInventory();
            ClickType clickType = event.getClick();

            if (clickedInventory != null && clickedInventory.getType() == InventoryType.SHULKER_BOX && clickType == ClickType.LEFT) {
                ItemStack clickedItem = event.getCurrentItem();

                if (clickedItem != null && event.getRawSlot() < clickedInventory.getSize()) {
                    Location location = clickedInventory.getLocation();

                    Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + player.getName() + ChatColor.RED + "从潜影盒中取出了物品 " + ChatColor.DARK_AQUA + clickedItem.getType().toString() + "。" + ChatColor.GREEN + "坐标：" + location.toString());
                }
            }
        }
    }

    // 玩家打开漏斗事件
    @EventHandler
    public void onPlayerOpenHopper(PlayerInteractEvent event) {
        if (Player_Listenter()) {
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                Block clickedBlock = event.getClickedBlock();
                if (clickedBlock.getState() instanceof Hopper) {
                    Player player = event.getPlayer();
                    Hopper hopper = (Hopper) clickedBlock.getState();
                    Location location = hopper.getLocation();

                    Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + player.getName() + ChatColor.RED + "打开了漏斗。" + ChatColor.GREEN + "漏斗坐标：" + location.toString());
                }
            }
        }
    }

    // 玩家从漏斗取出物品事件
    @EventHandler
    public void onPlayerTakeFromHopper(InventoryMoveItemEvent event) {
        if (Player_Listenter()) {
            Inventory sourceInventory = event.getSource();
            Inventory destinationInventory = event.getDestination();

            if (sourceInventory.getType() == InventoryType.HOPPER && destinationInventory.getType() == InventoryType.PLAYER) {
                ItemStack movedItem = event.getItem();

                if (movedItem != null) {
                    Location location = sourceInventory.getLocation();
                    Player player = (Player) destinationInventory.getHolder();

                    Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + player.getName() + ChatColor.RED + "从漏斗中取出了物品 " + ChatColor.DARK_AQUA + movedItem.getType().toString() + "。" + ChatColor.GREEN + "坐标：" + location.toString());
                }
            }
        }
    }

    // 玩家打开陷阱箱事件
    @EventHandler
    public void onPlayerOpenTrappedChest(PlayerInteractEvent event) {
        if (Player_Listenter()) {
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                Block clickedBlock = event.getClickedBlock();
                if (clickedBlock.getType() == Material.TRAPPED_CHEST) {
                    Player player = event.getPlayer();
                    Location location = clickedBlock.getLocation();

                    Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + player.getName() + ChatColor.RED + "打开了陷阱箱。" + ChatColor.GREEN + "陷阱箱坐标：" + location.toString());
                }
            }
        }
    }

    // 玩家从陷阱箱取出物品事件
    @EventHandler
    public void onPlayerTakeFromTrappedChest(InventoryClickEvent event) {
        if (Player_Listenter()) {
            Player player = (Player) event.getWhoClicked();
            Inventory clickedInventory = event.getClickedInventory();
            ClickType clickType = event.getClick();

            if (clickedInventory != null && clickedInventory.getType() == InventoryType.CHEST && isTrappedChest(clickedInventory) && clickType == ClickType.LEFT) {
                ItemStack clickedItem = event.getCurrentItem();

                if (clickedItem != null && event.getRawSlot() < clickedInventory.getSize()) {
                    Location location = clickedInventory.getLocation();

                    Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + player.getName() + ChatColor.RED + "从陷阱箱中取出了物品 " + ChatColor.DARK_AQUA + clickedItem.getType().toString() + "。" + ChatColor.GREEN + "坐标：" + location.toString());
                }
            }
        }
    }

    private boolean isTrappedChest(Inventory inventory) {
        ItemStack[] contents = inventory.getContents();

        for (ItemStack item : contents) {
            if (item != null && item.getType() == Material.TRAPPED_CHEST) {
                return true;
            }
        }
        return false;
    }

    // 玩家打开发射器（不是激活）
    @EventHandler
    public void onPlayerOpenDropper(PlayerInteractEvent event) {
        if (Player_Listenter()) {
            // 检查玩家是否是右键点击方块
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                Block clickedBlock = event.getClickedBlock();
                // 检查被点击的方块是否为发射器
                if (clickedBlock != null && clickedBlock.getState() instanceof Dropper) {
                    Player player = event.getPlayer();
                    Dropper dropper = (Dropper) clickedBlock.getState();
                    Location location = dropper.getLocation();

                    Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + player.getName() + ChatColor.RED + "打开了发射器。" + ChatColor.GREEN + "发射器坐标：" + location.toString());
                }
            }
        }
    }

    // 玩家从发射器中取出物品
    @EventHandler
    public void onPlayerTakeFromDropper(InventoryClickEvent event) {
        if (Player_Listenter()) {
            InventoryHolder holder = event.getInventory().getHolder();
            if (holder instanceof Dropper) {
                // 确保是玩家操作
                if (event.getWhoClicked() instanceof Player) {
                    Player player = (Player) event.getWhoClicked();
                    ItemStack movedItem = event.getCurrentItem();

                    if (movedItem != null) {
                        Location location = ((Dropper) holder).getLocation();
                        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + player.getName() + ChatColor.RED + "从发射器中取出了物品 " + ChatColor.DARK_AQUA + movedItem.getType().toString() + "。" + ChatColor.GREEN + "坐标：" + location.toString());
                    }
                }
            }
        }
    }

    // 玩家打开投掷器（不是激活）
    @EventHandler
    public void onPlayerOpenDispenser(PlayerInteractEvent event) {
        if (Player_Listenter()) {
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                Block clickedBlock = event.getClickedBlock();
                if (clickedBlock != null && clickedBlock.getState() instanceof Dispenser) {
                    Player player = event.getPlayer();
                    Dispenser dispenser = (Dispenser) clickedBlock.getState();
                    Location location = dispenser.getLocation();

                    Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + player.getName() + ChatColor.RED + "打开了投掷器。" + ChatColor.GREEN + "投掷器坐标：" + location.toString());
                }
            }
        }
    }

    // 玩家从投掷器中取出物品
    @EventHandler
    public void onPlayerTakeFromDispenser(InventoryClickEvent event) {
        if (Player_Listenter()) {
            InventoryHolder holder = event.getInventory().getHolder();
            if (holder instanceof Dispenser) { // 确认操作对象为Dispenser
                // 确保是玩家操作
                if (event.getWhoClicked() instanceof Player) {
                    Player player = (Player) event.getWhoClicked();
                    ItemStack movedItem = event.getCurrentItem();

                    if (movedItem != null) {
                        Location location = ((Dispenser) holder).getLocation();
                        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + player.getName() + ChatColor.RED + "从投掷器中取出了物品 " + ChatColor.DARK_AQUA + movedItem.getType().toString() + "。" + ChatColor.GREEN + "坐标：" + location.toString());
                    }
                }
            }
        }
    }

    // 玩家使用工作台事件
    @EventHandler
    public void onPlayerUseCraftingTable(PlayerInteractEvent event) {
        if (Player_Listenter()) {
            Player player = event.getPlayer();
            Action action = event.getAction();
            Block clickedBlock = event.getClickedBlock();

            if (action == Action.RIGHT_CLICK_BLOCK && clickedBlock != null && clickedBlock.getType() == Material.CRAFTING_TABLE) {
                Location location = clickedBlock.getLocation();

                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + player.getName() + ChatColor.YELLOW + "使用了工作台。" + ChatColor.GREEN + "坐标：" + location.toString());
            }
        }
    }

    // 玩家使用工作台制作了什么物品
    @EventHandler
    public void onCraftItem(CraftItemEvent event) {
        if (Player_Listenter()) {
            Player player = (Player) event.getWhoClicked();
            ItemStack craftedItem = event.getCurrentItem();

            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + player.getName() + ChatColor.YELLOW + "使用工作台制作了" + ChatColor.DARK_AQUA + craftedItem.getType().toString());
        }
    }

    // 监听玩家使用熔炉
    @EventHandler
    public void onPlayerUseFurnace(PlayerInteractEvent event) {
        if (Player_Listenter()) {
            Player player = event.getPlayer();
            Action action = event.getAction();
            Block clickedBlock = event.getClickedBlock();

            if (action == Action.RIGHT_CLICK_BLOCK && clickedBlock != null && clickedBlock.getType() == Material.FURNACE) {
                Location location = clickedBlock.getLocation();

                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + player.getName() + ChatColor.YELLOW + "使用了熔炉。" + ChatColor.GREEN + "坐标：" + location.toString());
            }
        }
    }

    // 监听玩家使用高炉
    @EventHandler
    public void onPlayerUseBlastFurnace(PlayerInteractEvent event) {
        if (Player_Listenter()) {
            Player player = event.getPlayer();
            Block clickedBlock = event.getClickedBlock();

            if (event.getAction() == Action.RIGHT_CLICK_BLOCK && clickedBlock != null && clickedBlock.getType() == Material.BLAST_FURNACE) {
                Location location = clickedBlock.getLocation();

                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + player.getName() + ChatColor.YELLOW + "使用了高炉。" + ChatColor.GREEN + "坐标：" + location.toString());
            }
        }
    }

    // 监听玩家使用烟熏台
    @EventHandler
    public void onPlayerUseSmoker(PlayerInteractEvent event) {
        if (Player_Listenter()) {
            Player player = event.getPlayer();
            Block clickedBlock = event.getClickedBlock();

            if (event.getAction() == Action.RIGHT_CLICK_BLOCK && clickedBlock != null && clickedBlock.getType() == Material.SMOKER) {
                Location location = clickedBlock.getLocation();

                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + player.getName() + ChatColor.YELLOW + "使用了烟熏台。" + ChatColor.GREEN + "坐标：" + location.toString());
            }
        }
    }

    // 监听玩家使用篝火
    @EventHandler
    public void onPlayerUseCampfire(PlayerInteractEvent event) {
        if (Player_Listenter()) {
            Player player = event.getPlayer();
            Block clickedBlock = event.getClickedBlock();

            if (event.getAction() == Action.RIGHT_CLICK_BLOCK && clickedBlock != null && clickedBlock.getType() == Material.CAMPFIRE) {
                Location location = clickedBlock.getLocation();

                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + player.getName() + ChatColor.YELLOW + "使用了篝火。" + ChatColor.GREEN + "坐标：" + location.toString());
            }
        }
    }

    // 监听玩家熄灭篝火和灵魂篝火
    @EventHandler
    public void onPlayerExtinguishCampfire(BlockBreakEvent event) {
        if (Player_Listenter()) {
            Player player = event.getPlayer();
            Block brokenBlock = event.getBlock();

            if (brokenBlock.getType() == Material.CAMPFIRE || brokenBlock.getType() == Material.SOUL_CAMPFIRE) {
                Location location = brokenBlock.getLocation();

                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + player.getName() + ChatColor.YELLOW + "熄灭了篝火。" + ChatColor.GREEN + "坐标：" + location.toString());
            }
        }
    }

    // 监听玩家使用铁砧
    @EventHandler
    public void onPlayerUseAnvil(PlayerInteractEvent event) {
        if (Player_Listenter()) {
            Player player = event.getPlayer();
            Block clickedBlock = event.getClickedBlock();

            if (event.getAction() == Action.RIGHT_CLICK_BLOCK && clickedBlock != null && clickedBlock.getType() == Material.ANVIL) {
                Location location = clickedBlock.getLocation();

                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + player.getName() + ChatColor.YELLOW + "使用了铁砧。" + ChatColor.GREEN + "坐标：" + location.toString());
            }
        }
    }

    // 监听玩家使用制图台
    @EventHandler
    public void onPlayerInteract9(PlayerInteractEvent event) {
        if (Player_Listenter()) {
            Player player = event.getPlayer();
            Block clickedBlock = event.getClickedBlock();

            if (event.getAction() == Action.RIGHT_CLICK_BLOCK && clickedBlock != null && clickedBlock.getType() == Material.CARTOGRAPHY_TABLE) {
                Location location = clickedBlock.getLocation();

                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + player.getName() + ChatColor.YELLOW + "使用了制图台。" + ChatColor.GREEN + "坐标：" + location.toString());
            }
        }
    }

    // 监听玩家使用砂轮
    @EventHandler
    public void onPlayerUseGrindstone(InventoryClickEvent event) {
        if (Player_Listenter()) {
            if (!(event.getWhoClicked() instanceof Player)) {
                return;
            }

            Player player = (Player) event.getWhoClicked();
            Inventory clickedInventory = event.getClickedInventory();

            if (clickedInventory != null && clickedInventory.getType() == InventoryType.GRINDSTONE) {
                ItemStack clickedItem = event.getCurrentItem();

                // 判断玩家所放置的物品是否为特定物品
                if (clickedItem != null && clickedItem.getType() != Material.AIR) {
                    Location location = clickedInventory.getLocation();

                    StringBuilder enchantmentString = new StringBuilder();
                    int totalExp = 0;

                    if (clickedItem.hasItemMeta() && clickedItem.getItemMeta() instanceof EnchantmentStorageMeta) {
                        EnchantmentStorageMeta enchantmentMeta = (EnchantmentStorageMeta) clickedItem.getItemMeta();
                        Map<Enchantment, Integer> enchantments = enchantmentMeta.getStoredEnchants();

                        for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
                            Enchantment enchantment = entry.getKey();
                            int level = entry.getValue();

                            enchantmentString.append(enchantment.getKey().getKey()).append(" (等级 ").append(level).append("), ");
                            totalExp += enchantment.getStartLevel() + (enchantment.getMaxLevel() * (level - 1));
                        }
                    }

                    Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + player.getName() + ChatColor.YELLOW + "使用了砂轮。" + ChatColor.GREEN + "坐标：" + location.toString());
                    Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + player.getName() + ChatColor.YELLOW + "对" + ChatColor.DARK_AQUA + clickedItem.getType().toString() + ChatColor.YELLOW + "进行了祛魔。");
                    Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + player.getName() + ChatColor.YELLOW + "去掉了以下附魔：" + ChatColor.DARK_PURPLE + enchantmentString.toString());
                    Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + player.getName() + "通过祛魔获得了 " + ChatColor.GREEN + totalExp + ChatColor.YELLOW + "经验值。");
                }
            }
        }
    }

    // 监听玩家使用织布机
    @EventHandler
    public void onPlayerUseLoom(PlayerInteractEvent event) {
        if (Player_Listenter()) {
            Player player = event.getPlayer();
            Action action = event.getAction();
            Block clickedBlock = event.getClickedBlock();

            if (action == Action.RIGHT_CLICK_BLOCK && clickedBlock != null && clickedBlock.getType() == Material.LOOM) {
                Location location = clickedBlock.getLocation();

                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + player.getName() + ChatColor.YELLOW + "使用了织布机。" + ChatColor.GREEN + "坐标：" + location.toString());
            }
        }
    }

    // 玩家对生物造成伤害
    @EventHandler
    public void onPlayerDamages(EntityDamageByEntityEvent event) {
        if (Player_Listenter()) {
            Entity attacker = event.getDamager();
            Entity defender = event.getEntity();

            if (attacker instanceof Player && defender != null) {
                Player player = (Player) attacker;
                double damage = event.getFinalDamage();
                Location location = player.getLocation();

                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + player.getName() + ChatColor.GREEN + "在坐标" + location.getX() + ", " + location.getY() + ", " + location.getZ() + ChatColor.YELLOW + "对" + ChatColor.DARK_AQUA + defender.getType().toString() + ChatColor.YELLOW + "造成了" + ChatColor.RED + damage + ChatColor.YELLOW + "点伤害！");
            }
        }
    }

    // 玩家击杀生物
    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (Player_Listenter()) {
            if (event.getEntity().getKiller() instanceof Player) {
                Player player = event.getEntity().getKiller();
                Location deathLocation = event.getEntity().getLocation();
                EntityType entityType = event.getEntityType();

                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + player.getName() + ChatColor.GREEN + "在坐标" + deathLocation.getX() + "，" + deathLocation.getY() + "，" + deathLocation.getZ() + ChatColor.RED + "击杀了" + ChatColor.DARK_AQUA + entityType.toString());
            }
        }
    }

    // 玩家被生物伤害
    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        if (Player_Listenter()) {
            Entity attacker = event.getDamager();
            Entity defender = event.getEntity();

            if (defender instanceof Player && attacker != null) {
                Player player = (Player) defender;
                double damage = event.getFinalDamage();
                Location location = player.getLocation();

                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + player.getName() + ChatColor.GREEN + "在坐标" + location.getX() + ", " + location.getY() + ", " + location.getZ() + ChatColor.RED + "受到了来自" + ChatColor.DARK_AQUA + attacker.getType().toString() + ChatColor.YELLOW + "的伤害" + ChatColor.RED + damage + ChatColor.YELLOW + "点！");
            }
        }
    }

    // 玩家使用盾牌格挡伤害
    @EventHandler
    public void onPlayerDamageShield(EntityDamageByEntityEvent event) {
        if (Player_Listenter()) {
            Entity attacker = event.getDamager();
            Entity defender = event.getEntity();

            if (defender instanceof Player && attacker != null) {
                Player player = (Player) defender;
                double damage = event.getFinalDamage();
                Location location = player.getLocation();
                ItemStack shield = player.getInventory().getItemInOffHand();

                if (shield.getType().toString().contains("SHIELD")) {
                    double blockedDamage = event.getDamage() - damage;

                    Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + player.getName() + ChatColor.GREEN + "在坐标" + location.getX() + ", " + location.getY() + ", " + location.getZ() + ChatColor.GOLD + "使用盾牌成功格挡了来自" + ChatColor.DARK_AQUA + attacker.getType().toString() + ChatColor.YELLOW + "的攻击，格挡了" + ChatColor.RED + blockedDamage + ChatColor.YELLOW + "点伤害！");
                }
            }
        }
    }

    // 玩家被雷劈
    @EventHandler
    public void onPlayerStruckByLightning(EntityDamageEvent event) {
        if (Player_Listenter()) {
            if (event.getEntity() instanceof Player) {
                Player player = (Player) event.getEntity();
                if (event.getCause() == EntityDamageEvent.DamageCause.LIGHTNING) {

                    YuZeXingHePlugin plugin = YuZeXingHePlugin.getInstance();
                    plugin.getServer().broadcastMessage(ChatColor.AQUA + player.getName() + ChatColor.YELLOW + "被雷劈了！大家快笑他！");
                    Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + player.getName() + ChatColor.YELLOW + "被雷劈了！大家快笑他！");
                }
            }
        }
    }

    // 玩家使用弓箭射出箭矢
    @EventHandler
    public void onEntityShootBow(EntityShootBowEvent event) {
        if (Player_Listenter()) {
            if (event.getEntity() instanceof Player) {
                Player player = (Player) event.getEntity();
                Arrow arrow = (Arrow) event.getProjectile();

                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + player.getDisplayName() + ChatColor.YELLOW + "射出了一支箭，类型为" + ChatColor.DARK_AQUA + arrow.getName());
            }
        }
    }

    // 玩家投掷雪球
    @EventHandler
    public void onPlayerInteract5(PlayerInteractEvent event) {
        if (Player_Listenter()) {
            Player player = event.getPlayer();
            if (player.getItemInHand().getType() == Material.SNOWBALL) {
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + player.getDisplayName() + ChatColor.YELLOW + "投掷了一个" + "雪球！");
            }
        }
    }

    // 玩家投掷末影珍珠
    @EventHandler
    public void onPlayerInteract8(PlayerInteractEvent event) {
        if (Player_Listenter()) {
            Player player = event.getPlayer();
            if (event.getItem() != null && event.getItem().getType().name().equals("ENDER_PEARL")) {
                EnderPearl enderPearl = player.launchProjectile(EnderPearl.class);
                Location pearlLocation = enderPearl.getLocation();

                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + player.getDisplayName() + ChatColor.GREEN + "在x:" + player.getLocation().getBlockX() + ", y:" + player.getLocation().getBlockY() + ", z:" + player.getLocation().getBlockZ() + ChatColor.YELLOW + "处投掷了" + ChatColor.DARK_AQUA + "末影珍珠！" + ChatColor.GREEN + "珍珠位置: x:" + pearlLocation.getBlockX() + ", y:" + pearlLocation.getBlockY() + ", z:" + pearlLocation.getBlockZ());
            }
        }
    }

    // 凋零生成
    @EventHandler
    public void onPlayerInteract6(PlayerInteractEvent event) {
        if (Player_Listenter()) {
            Player player = event.getPlayer();
            ItemStack item = player.getItemInHand();

            if (item.getType() == Material.PLAYER_HEAD) {
                SkullMeta meta = (SkullMeta) item.getItemMeta();
                if (meta.hasOwner() && meta.getOwningPlayer() != null && meta.getOwningPlayer().getName().equals("MHF_WSkeleton")) {
                    Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + player.getDisplayName() + ChatColor.GREEN + "在x:" + player.getLocation().getBlockX() + ", y:" + player.getLocation().getBlockY() + ", z:" + player.getLocation().getBlockZ() + ChatColor.RED + "召唤了一个凋零");
                }
            }
        }
    }

    // 雪傀儡生成
    @EventHandler
    public void onPlayerInteract7(PlayerInteractEvent event) {
        if (Player_Listenter()) {
            Player player = event.getPlayer();
            ItemStack item = player.getItemInHand();

            if (item.getType() == Material.PLAYER_HEAD) {
                SkullMeta meta = (SkullMeta) item.getItemMeta();
                if (meta.hasOwner() && meta.getOwningPlayer() != null && meta.getOwningPlayer().getName().equals("MHF_SnowGolem")) {
                    Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + player.getDisplayName() + ChatColor.GREEN + "在x:" + player.getLocation().getBlockX() + ", y:" + player.getLocation().getBlockY() + ", z:" + player.getLocation().getBlockZ() + ChatColor.RED + "召唤了一个雪傀儡");
                }
            }
        }
    }

    // 玩家钓鱼行为
    @EventHandler
    public void onPlayerFish(PlayerFishEvent event) {
        if (Player_Listenter()) {
            Player player = event.getPlayer();
            Location fishLocation = event.getHook().getLocation();
            Material fishingRodType = event.getPlayer().getInventory().getItemInMainHand().getType();

            if (event.getState() == PlayerFishEvent.State.FISHING) {
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + player.getName() + ChatColor.GREEN + "在坐标" + fishLocation.getX() + ", " + fishLocation.getY() + ", " + fishLocation.getZ() + ChatColor.YELLOW + "抛出了钓鱼竿");
            }
            else if (event.getState() == PlayerFishEvent.State.REEL_IN) {
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + player.getName() + ChatColor.GREEN + "在坐标" + fishLocation.getX() + ", " + fishLocation.getY() + ", " + fishLocation.getZ() + ChatColor.YELLOW + "收回了钓鱼竿");
            }
            else if (event.getState() == PlayerFishEvent.State.CAUGHT_FISH) {
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + player.getName() + ChatColor.GREEN + "在坐标" + fishLocation.getX() + ", " + fishLocation.getY() + ", " + fishLocation.getZ() + ChatColor.YELLOW + "钓到了一条鱼");
            }
            else if (event.getState() == PlayerFishEvent.State.FAILED_ATTEMPT) {
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + player.getName() + ChatColor.GREEN + "在坐标" + fishLocation.getX() + ", " + fishLocation.getY() + ", " + fishLocation.getZ() + ChatColor.YELLOW + "钓鱼失败了");
            }
        }
    }

    // 玩家和木门互动
    @EventHandler
    public void onPlayerInteract1(PlayerInteractEvent event) {
        if (Player_Listenter()) {
            Player player = event.getPlayer();
            Block clickedBlock = event.getClickedBlock();
            if (clickedBlock != null && clickedBlock.getType().name().contains("DOOR")) {
                int x = clickedBlock.getX();
                int y = clickedBlock.getY();
                int z = clickedBlock.getZ();
                String action = event.getAction().toString().toLowerCase();
                String playerName = player.getDisplayName();
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + playerName + ChatColor.YELLOW + action + ChatColor.GREEN + "位于 (" + x + ", " + y + ", " + z + ") " + ChatColor.YELLOW + "的木门");
            }
        }
    }

    // 玩家与拉杆互动
    @EventHandler
    public void onPlayerInteract2(PlayerInteractEvent event) {
        if (Player_Listenter()) {
            Player player = event.getPlayer();
            Block clickedBlock = event.getClickedBlock();
            if (clickedBlock != null && clickedBlock.getType().name().contains("LEVER")) {
                BlockFace leverFace = ((Lever) clickedBlock.getState().getData()).getAttachedFace();
                Block leverBlock = clickedBlock.getRelative(leverFace);

                int x = leverBlock.getX();
                int y = leverBlock.getY();
                int z = leverBlock.getZ();
                String action = event.getAction().toString().toLowerCase();
                String playerName = player.getDisplayName();
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + playerName + ChatColor.YELLOW + action + ChatColor.GREEN + "位于 (" + x + ", " + y + ", " + z + ")" + ChatColor.YELLOW + "的拉杆");
            }
        }
    }

    // 玩家按下按钮
    @EventHandler
    public void onPlayerInteract3(PlayerInteractEvent event) {
        if (Player_Listenter()) {
            Player player = event.getPlayer();
            Block clickedBlock = event.getClickedBlock();
            if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && clickedBlock != null && clickedBlock.getType().name().contains("BUTTON")) {
                int x = clickedBlock.getX();
                int y = clickedBlock.getY();
                int z = clickedBlock.getZ();
                String action = "pressed"; // 因为只有右键才能激活按钮，所以这里只会是按下
                String playerName = player.getDisplayName();
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + playerName + ChatColor.YELLOW + action + ChatColor.GREEN + "位于 (" + x + ", " + y + ", " + z + ")" + ChatColor.YELLOW + "的按钮");
            }
        }
    }

    // 玩家踩下压力板
    @EventHandler
    public void onPlayerInteract4(PlayerInteractEvent event) {
        if (Player_Listenter()) {
            Player player = event.getPlayer();
            Block clickedBlock = event.getClickedBlock();
            if (event.getAction().equals(Action.PHYSICAL) && clickedBlock != null && clickedBlock.getType().name().contains("PRESSURE_PLATE")) {
                int x = clickedBlock.getX();
                int y = clickedBlock.getY();
                int z = clickedBlock.getZ();
                String playerName = player.getDisplayName();
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + playerName + ChatColor.RED + "踩下了" + ChatColor.GREEN + "位于 (" + x + ", " + y + ", " + z + ")" + ChatColor.YELLOW + "的压力板");
            }
        }
    }

    // 玩家激活TNT
    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        if (Player_Listenter()) {
            Entity entity = event.getEntity();
            if (entity instanceof org.bukkit.entity.TNTPrimed) {
                int x = entity.getLocation().getBlockX();
                int y = entity.getLocation().getBlockY();
                int z = entity.getLocation().getBlockZ();

                Player player = null;
                for (Entity nearbyEntity : entity.getNearbyEntities(5, 5, 5)) {
                    if (nearbyEntity instanceof Player) {
                        player = (Player) nearbyEntity;
                        break;
                    }
                }

                String playerName = player != null ? player.getDisplayName() : "Unknown Player";
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + playerName + ChatColor.GREEN + "在 (" + x + ", " + y + ", " + z + ")" + ChatColor.RED + "激活了TNT并导致爆炸");
            }
        }
    }

    // 苦力怕和闪电苦力怕爆炸行为
    @EventHandler
    public void onEntityExplode2(EntityExplodeEvent event) {
        if (Player_Listenter()) {
            if (event.getEntityType() == EntityType.CREEPER) {
                Creeper creeper = (Creeper) event.getEntity();
                if (creeper.getTarget() != null && creeper.getTarget() instanceof Player) {
                    Player player = (Player) creeper.getTarget();
                    Location explosionLocation = event.getLocation();

                    Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + player.getDisplayName() + ChatColor.GREEN + "在 x:" + player.getLocation().getBlockX() + ", y:" + player.getLocation().getBlockY() + ", z:" + player.getLocation().getBlockZ() + ChatColor.RED + "引爆了苦力怕！" + ChatColor.GREEN + "爆炸位置: x:" + explosionLocation.getBlockX() + ", y:" + explosionLocation.getBlockY() + ", z:" + explosionLocation.getBlockZ());
                } else {
                    Location explosionLocation = event.getLocation();

                    Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "苦力怕在" + ChatColor.GREEN + "x:" + explosionLocation.getBlockX() + ", y:" + explosionLocation.getBlockY() + ", z:" + explosionLocation.getBlockZ() + ChatColor.RED + "自爆！");
                }
            }
        }
    }

    // 玩家使用食物
    @EventHandler
    public void onPlayerItemConsume(PlayerItemConsumeEvent event) {
        if (Player_Listenter()) {
            Player player = event.getPlayer();
            String itemName = event.getItem().getType().name();

            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + player.getName() + ChatColor.YELLOW + "食用了" + ChatColor.DARK_AQUA + itemName);
        }
    }

    // 玩家从主世界传送到地狱
    @EventHandler
    public void onPlayerPortal(PlayerPortalEvent event) {
        if (Player_Listenter()) {
            Player player = event.getPlayer();
            Location from = event.getFrom();
            Location to = event.getTo();

            if (from.getWorld().getName().equals("world") && to.getWorld().getName().equals("world_nether")) {
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + player.getDisplayName() + "在" + ChatColor.GREEN + "主世界x:" + from.getBlockX() + ", y:" + from.getBlockY() + ", z:" + from.getBlockZ() + ChatColor.YELLOW + "传送到了地狱。即将在" + ChatColor.GREEN + "x:" + to.getBlockX() + ", y:" + to.getBlockY() + ", z:" + to.getBlockZ() + ChatColor.YELLOW + "处传送完成");
            }
        }
    }

    // 玩家从地狱传送到主世界
    @EventHandler
    public void onPlayerPortal1(PlayerPortalEvent event) {
        if (Player_Listenter()) {
            Player player = event.getPlayer();
            Location from = event.getFrom();
            Location to = event.getTo();

            if (from.getWorld().getName().equals("world_nether") && to.getWorld().getName().equals("world")) {
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + player.getDisplayName() + "在" + ChatColor.GREEN + "地狱x:" + from.getBlockX() + ", y:" + from.getBlockY() + ", z:" + from.getBlockZ() + ChatColor.YELLOW + "传送回了主世界。即将在" + ChatColor.GREEN + "x:" + to.getBlockX() + ", y:" + to.getBlockY() + ", z:" + to.getBlockZ() + ChatColor.YELLOW + "处传送完成");
            }
        }
    }

    // 玩家从主世界传送到末地
    @EventHandler
    public void onPlayerPortal2(PlayerPortalEvent event) {
        if (Player_Listenter()) {
            Player player = event.getPlayer();
            Location from = event.getFrom();
            Location to = event.getTo();

            if (from.getWorld().getName().equals("world") && to.getWorld().getName().equals("world_the_end")) {
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + player.getDisplayName() + "在" + ChatColor.GREEN + "主世界x:" + from.getBlockX() + ", y:" + from.getBlockY() + ", z:" + from.getBlockZ() + ChatColor.YELLOW + "传送到了末地。即将在" + ChatColor.GREEN + "x:" + to.getBlockX() + ", y:" + to.getBlockY() + ", z:" + to.getBlockZ() + ChatColor.YELLOW + "处传送完成");
            }
        }
    }

    // 玩家从末地传送回主世界
    @EventHandler
    public void onPlayerPortal3(PlayerPortalEvent event) {
        if (Player_Listenter()) {
            Player player = event.getPlayer();
            Location from = event.getFrom();
            Location to = event.getTo();

            if (from.getWorld().getName().equals("world_the_end") && to.getWorld().getName().equals("world")) {
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + player.getDisplayName() + "在" + ChatColor.GREEN + "末地x:" + from.getBlockX() + ", y:" + from.getBlockY() + ", z:" + from.getBlockZ() + ChatColor.YELLOW + "传送回了主世界。即将在" + ChatColor.GREEN + "x:" + to.getBlockX() + ", y:" + to.getBlockY() + ", z:" + to.getBlockZ() + ChatColor.YELLOW + "处传送完成");
            }
        }
    }
}