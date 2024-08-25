package net.ciyuan.linxtools.Commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.ItemTag;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Item;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public class ShowCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(@NotNull CommandSender Sender, @NotNull Command Cmd, @NotNull String Label, @NotNull String[] Args)
    {

        Player player;
        if (!(Sender instanceof Player))
        {
            Sender.sendMessage("§c该命令只能由玩家使用。");
            return false;
        }
        player = (Player) Sender;
        if (Args.length != 0)
        {
            Sender.sendMessage("§c该命令不需要输入参数。");
            return false;
        }
        ItemStack itemInHand = player.getInventory().getItemInMainHand();

        if (itemInHand != null && itemInHand.getType() != org.bukkit.Material.AIR) {
            ItemMeta meta = itemInHand.getItemMeta();
            String itemName = itemInHand.getType().name();

            BaseComponent[] hoverEventComponents = new ComponentBuilder(itemName)
                    .color(net.md_5.bungee.api.ChatColor.GOLD)
                    .append("\n")
                    .append("Type: " + itemInHand.getType().name())
                    .color(net.md_5.bungee.api.ChatColor.WHITE)
                    .create();

            TextComponent message = new TextComponent(player.getName() + " is showing: ");
            TextComponent itemComponent = new TextComponent("[" + itemName + "]");
            itemComponent.setColor(net.md_5.bungee.api.ChatColor.AQUA);
            itemComponent.setHoverEvent(new net.md_5.bungee.api.chat.HoverEvent(
                    net.md_5.bungee.api.chat.HoverEvent.Action.SHOW_ITEM,
                    new Item(itemInHand.getType().getKey().toString(), itemInHand.getAmount(), ItemTag.ofNbt(meta.getAsString()))
            ));

            message.addExtra(itemComponent);
            Bukkit.spigot().broadcast(message);
        } else {
            player.sendMessage(ChatColor.RED + "You are not holding any item!");
        }
        return true;
    }
}
