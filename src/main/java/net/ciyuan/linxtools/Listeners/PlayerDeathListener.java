package net.ciyuan.linxtools.Listeners;

import net.ciyuan.linxtools.Commands.SuicideCommand;
import net.ciyuan.linxtools.Wrappers.DataWrapper;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

public class PlayerDeathListener implements Listener
{
    private static void AddDeathLocation(String Value, String UUID)
    {
        DataWrapper.AddUserProfile("DeathLocations", Value, UUID);
    }

    public static int GetDeaths(String UUID)
    {
        DataWrapper.UpdateData();
        return Integer.parseInt(DataWrapper.GetUserProfile(UUID).get("Deaths").toString());
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event)
    {
        DataWrapper.UpdateData();
        Location DeathLocation = event.getEntity().getLocation();
        String DeathLocationString = Objects.requireNonNull(DeathLocation.getWorld()).getName() + " " + DeathLocation.getX() + " " + DeathLocation.getY() + " " + DeathLocation.getZ() + " " + DeathLocation.getYaw() + " " + DeathLocation.getPitch();
        AddDeathLocation(DeathLocationString, event.getEntity().getUniqueId().toString());

        if (!SuicideCommand.DisplayDeathMessage)
        {
            event.setDeathMessage(null);
            return;
        }
        if (Bukkit.getPluginManager().getPlugin("ManHunt") != null)
        {
            return;
        }
        String PName = event.getEntity().getName();
        EntityDamageEvent damageEvent = event.getEntity().getLastDamageCause();
        if (damageEvent instanceof EntityDamageByEntityEvent)
        {

            Entity damager = ((EntityDamageByEntityEvent) damageEvent).getDamager();
            if (damager instanceof Player player)
            {
                ItemStack[] armor = player.getInventory().getArmorContents();
                for (ItemStack item : armor)
                {
                    if (item != null && item.getType().name().contains("THORNS"))
                    {
                        String playerName = damager.getName();
                        event.setDeathMessage(ChatColor.RED + PName + ChatColor.BLUE + " 被 " + ChatColor.GREEN + playerName + ChatColor.BLUE + " 反杀了！" + " §f这是Ta的第 §e#" + GetDeaths(event.getEntity().getUniqueId().toString()) + " §f次死亡！");
                        return;
                    }
                }
                String attackerName = damager.getName();
                event.setDeathMessage(ChatColor.RED + PName + ChatColor.WHITE + " 被 " + ChatColor.GREEN + attackerName + (!(GetMainHandItemName(player).isEmpty()) ? ChatColor.WHITE + " 用 " + ChatColor.AQUA + ChatColor.ITALIC + "[" + GetMainHandItemName(player) + "]" : "") + ChatColor.GOLD + " 猎杀" + ChatColor.WHITE + "。" + " §f这是Ta的第 §e#" + GetDeaths(event.getEntity().getUniqueId().toString()) + " §f次死亡！");
                event.getEntity().sendTitle(ChatColor.RED + "你已被" + ChatColor.GREEN + attackerName + ChatColor.GOLD + "猎杀！", ChatColor.AQUA + "菜！", 10, 100, 20);
                return;
            }

            if (damager instanceof Projectile && ((Projectile) damager).getShooter() instanceof Player player) {
                String attackerName = ((LivingEntity) ((Projectile) damager).getShooter()).getName();
                event.setDeathMessage(ChatColor.RED + PName + ChatColor.WHITE + " 被 " + ChatColor.GREEN + attackerName + ChatColor.WHITE + " 用 " + ChatColor.AQUA + ChatColor.ITALIC + "[" + GetMainHandItemName(player) + "]" + ChatColor.GOLD + " 射杀" + ChatColor.WHITE + "。" + " §f这是Ta的第 §e#" + GetDeaths(event.getEntity().getUniqueId().toString()) + " §f次死亡！");
                event.getEntity().sendTitle(ChatColor.RED + "你已被" + ChatColor.GREEN + attackerName + ChatColor.GOLD + "射杀！", ChatColor.AQUA + "菜！", 10, 100, 20);
                return;
            }

            if (damager instanceof Projectile && ((Projectile) damager).getShooter() instanceof LivingEntity) {
                String attackerName = ((LivingEntity) ((Projectile) damager).getShooter()).getName();
                event.setDeathMessage(ChatColor.RED + PName + ChatColor.WHITE + " 被 " + ChatColor.GREEN + attackerName + ChatColor.GOLD + " 射♂死" + ChatColor.WHITE + "了！" + " §f这是Ta的第 §e#" + GetDeaths(event.getEntity().getUniqueId().toString()) + " §f次死亡！");
                return;
            }
        }
        switch (Objects.requireNonNull(event.getEntity().getLastDamageCause()).getCause())
        {
            case FALL -> event.setDeathMessage(ChatColor.RED + PName + ChatColor.WHITE + " 落地时忘了自救！" + " §f这是Ta的第 §e#" + GetDeaths(event.getEntity().getUniqueId().toString()) + " §f次死亡！");
            case LAVA -> event.setDeathMessage(ChatColor.RED + PName + ChatColor.WHITE + " 想要在" + ChatColor.DARK_RED + "开水" + ChatColor.WHITE + "中泡澡！" + " §f这是Ta的第 §e#" + GetDeaths(event.getEntity().getUniqueId().toString()) + " §f次死亡！");
            case FIRE -> event.setDeathMessage(ChatColor.RED + PName + ChatColor.WHITE + " 试图火化自己！" + " §f这是Ta的第 §e#" + GetDeaths(event.getEntity().getUniqueId().toString()) + " §f次死亡！");
            case VOID -> event.setDeathMessage(ChatColor.RED + PName + ChatColor.WHITE + " 跌入" + ChatColor.LIGHT_PURPLE + "万丈深渊" + ChatColor.WHITE + "。" + " §f这是Ta的第 §e#" + GetDeaths(event.getEntity().getUniqueId().toString()) + " §f次死亡！");
            case MAGIC -> event.setDeathMessage(ChatColor.RED + PName + ChatColor.WHITE + " 被" + ChatColor.BLACK + "黑魔法" + ChatColor.WHITE + "残忍杀死！" + " §f这是Ta的第 §e#" + GetDeaths(event.getEntity().getUniqueId().toString()) + " §f次死亡！");
            case CUSTOM -> event.setDeathMessage(ChatColor.RED + PName + ChatColor.WHITE + " 被" + ChatColor.GOLD + "神力" + ChatColor.WHITE + "杀死！" + " §f这是Ta的第 §e#" + GetDeaths(event.getEntity().getUniqueId().toString()) + " §f次死亡！");
            case DRYOUT -> event.setDeathMessage(ChatColor.RED + PName + ChatColor.BLUE + " 忘记它是海洋生物！" + " §f这是Ta的第 §e#" + GetDeaths(event.getEntity().getUniqueId().toString()) + " §f次死亡！");
            case FREEZE -> event.setDeathMessage(ChatColor.RED + PName + ChatColor.DARK_BLUE + " 想要去南极度假！" + " §f这是Ta的第 §e#" + GetDeaths(event.getEntity().getUniqueId().toString()) + " §f次死亡！");
            case POISON -> event.setDeathMessage(ChatColor.RED + PName + ChatColor.WHITE + " 因喝了过多" + ChatColor.GOLD + "鸡汤" + ChatColor.WHITE + "而死！" + " §f这是Ta的第 §e#" + GetDeaths(event.getEntity().getUniqueId().toString()) + " §f次死亡！");
            case WITHER -> event.setDeathMessage(ChatColor.RED + PName + ChatColor.BLACK + " 悲惨地凋零而死！" + " §f这是Ta的第 §e#" + GetDeaths(event.getEntity().getUniqueId().toString()) + " §f次死亡！");
            case CONTACT -> event.setDeathMessage(ChatColor.RED + PName + ChatColor.WHITE + " 被" + ChatColor.GOLD + "戳♂死" + ChatColor.WHITE + "了！" + " §f这是Ta的第 §e#" + GetDeaths(event.getEntity().getUniqueId().toString()) + " §f次死亡！");
            case MELTING -> event.setDeathMessage(ChatColor.RED + PName + ChatColor.BLUE + " 融化" + ChatColor.WHITE + "了！" + " §f这是Ta的第 §e#" + GetDeaths(event.getEntity().getUniqueId().toString()) + " §f次死亡！");
            case SUICIDE -> event.setDeathMessage(ChatColor.RED + PName + ChatColor.DARK_RED + " 想不开了，自杀了！" + " §f这是Ta的第 §e#" + GetDeaths(event.getEntity().getUniqueId().toString()) + " §f次死亡！");
            case CRAMMING -> event.setDeathMessage(ChatColor.RED + PName + ChatColor.WHITE + " 被" + ChatColor.GOLD + "挤♂死" + ChatColor.WHITE + "了！" + " §f这是Ta的第 §e#" + GetDeaths(event.getEntity().getUniqueId().toString()) + " §f次死亡！");
            case DROWNING -> event.setDeathMessage(ChatColor.RED + PName + ChatColor.BLUE + " 试图变为溺尸！" + " §f这是Ta的第 §e#" + GetDeaths(event.getEntity().getUniqueId().toString()) + " §f次死亡！");
            case FIRE_TICK -> event.setDeathMessage(ChatColor.RED + PName + ChatColor.WHITE + " 试图火化自己后醒悟了，最终还是寄了！" + " §f这是Ta的第 §e#" + GetDeaths(event.getEntity().getUniqueId().toString()) + " §f次死亡！");
            case HOT_FLOOR -> event.setDeathMessage(ChatColor.RED + PName + ChatColor.WHITE + " 站在岩浆块上没按Shift！" + " §f这是Ta的第 §e#" + GetDeaths(event.getEntity().getUniqueId().toString()) + " §f次死亡！");
            case LIGHTNING -> event.setDeathMessage(ChatColor.RED + PName + ChatColor.GOLD + " 渡劫失败！" + " §f这是Ta的第 §e#" + GetDeaths(event.getEntity().getUniqueId().toString()) + " §f次死亡！");
            case STARVATION -> event.setDeathMessage(ChatColor.RED + PName + ChatColor.GOLD + " 啃树皮失败！" + " §f这是Ta的第 §e#" + GetDeaths(event.getEntity().getUniqueId().toString()) + " §f次死亡！");
            case SUFFOCATION -> event.setDeathMessage(ChatColor.RED + PName + ChatColor.GREEN + " 被卡方块了！" + " §f这是Ta的第 §e#" + GetDeaths(event.getEntity().getUniqueId().toString()) + " §f次死亡！");
            case SONIC_BOOM -> event.setDeathMessage(ChatColor.RED + PName + ChatColor.WHITE + " 在与监守者" + ChatColor.GOLD + "决♂斗" + ChatColor.WHITE + "时被监守者" + ChatColor.GOLD + "射♂死" + ChatColor.WHITE + "了！" + " §f这是Ta的第 §e#" + GetDeaths(event.getEntity().getUniqueId().toString()) + " §f次死亡！");
            case DRAGON_BREATH -> event.setDeathMessage(ChatColor.RED + PName + ChatColor.LIGHT_PURPLE + " 没打过龙，真菜！" + " §f这是Ta的第 §e#" + GetDeaths(event.getEntity().getUniqueId().toString()) + " §f次死亡！");
            case FALLING_BLOCK -> event.setDeathMessage(ChatColor.RED + PName + ChatColor.GREEN + " 被砸死了！" + " §f这是Ta的第 §e#" + GetDeaths(event.getEntity().getUniqueId().toString()) + " §f次死亡！");
            case BLOCK_EXPLOSION -> event.setDeathMessage(ChatColor.RED + PName + ChatColor.WHITE + " 悲惨地被" + ChatColor.DARK_RED + "炸死" + ChatColor.WHITE + "。" + " §f这是Ta的第 §e#" + GetDeaths(event.getEntity().getUniqueId().toString()) + " §f次死亡！");
            case ENTITY_EXPLOSION -> event.setDeathMessage(ChatColor.RED + PName + ChatColor.WHITE + " 悲惨地被" + ChatColor.DARK_RED + "炸死" + ChatColor.WHITE + "。" + " §f这是Ta的第 §e#" + GetDeaths(event.getEntity().getUniqueId().toString()) + " §f次死亡！");
            case FLY_INTO_WALL -> event.setDeathMessage(ChatColor.RED + PName + ChatColor.WHITE + " 飞行时忘记规避障碍！" + " §f这是Ta的第 §e#" + GetDeaths(event.getEntity().getUniqueId().toString()) + " §f次死亡！");
            case ENTITY_SWEEP_ATTACK -> event.setDeathMessage(ChatColor.RED + PName + ChatColor.WHITE + " 悲惨地被" + ChatColor.GOLD + "横扫" + ChatColor.WHITE + "。" + " §f这是Ta的第 §e#" + GetDeaths(event.getEntity().getUniqueId().toString()) + " §f次死亡！");
            default -> event.setDeathMessage(ChatColor.RED + PName + ChatColor.WHITE + " 死得不明不白！" + " §f这是Ta的第 §e#" + GetDeaths(event.getEntity().getUniqueId().toString()) + " §f次死亡！");
        }
    }
    public static String GetMainHandItemName(Player player)
    {
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
        if (itemInMainHand.hasItemMeta())
        {
            ItemMeta itemMeta = itemInMainHand.getItemMeta();
            if (itemMeta != null && itemMeta.hasDisplayName()) return itemMeta.getDisplayName();
        }
        return "";
    }
}
