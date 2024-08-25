package net.ciyuan.linxtools.Commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.api.chat.hover.content.Content;
import net.md_5.bungee.api.chat.hover.content.Item;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class ShowCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(@NotNull CommandSender Sender, @NotNull Command Cmd, @NotNull String Label, @NotNull String[] Args)
    {
        if (!(Sender instanceof Player player))
        {
            Sender.sendMessage("§c该命令只能由玩家使用。");
            return false;
        }

        @Nullable Slot slot;

        switch (Args.length)
        {
            case 0:
            {
                slot = Slot.SlotItem;
                break;
            }

            case 1:
            {
                slot = Slot.GetSlot(Args[0]);
                break;
            }

            default:
            {
                return false;
            }
        }

        if (slot == null)
        {
            Sender.sendMessage("§c此物品格不存在！");
            return false;
        }

        var item = slot.GetItemStack(player);

        if (item == null || item.getType() == Material.AIR)
        {
            Sender.sendMessage("\u00A7cCouldn't find the item you were trying to link!");
            return true;
        }

        var message = new TextComponent(player.getName() + "\u00A77 " + slot.GetVerb() + " ");
        var itemComponent = new TextComponent("\u00A78[\u00A7f" + GetItemName(item) + "\u00A78]");

        itemComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, GetItemDisplay(item)));
        message.addExtra(itemComponent);

        Bukkit.spigot().broadcast(message);
        return true;
    }

    private static @NotNull String GetItemName(ItemStack itemStack)
    {
        @NotNull var amountString = itemStack.getAmount() == 1 ? "" : ("\u00A77 x" + itemStack.getAmount());

        var meta = itemStack.getItemMeta();

        if (meta != null)
        {
            return meta.getDisplayName();
        }

        return itemStack.getTranslationKey() + amountString;
    }

    private static @NotNull Content GetItemDisplay(ItemStack itemStack)
    {
        var builder = new StringBuilder(GetItemName(itemStack));

        var meta = itemStack.getItemMeta();

        if (meta != null)
        {
            var lore = meta.getLore();

            if (lore != null)
            {
                for (var loreLine : meta.getLore())
                {
                    builder.append('\n');
                    builder.append(loreLine);
                }
            }
        }

        return new Text(builder.toString());
    }

    private enum Slot
    {
        SlotItem(98, "is holding"),
        SlotOffhand(99, "is holding"),
        SlotHelmet(103, "is wearing"),
        SlotChestplate(102, "is wearing"),
        SlotLeggings(101, "is wearing"),
        SlotBoots(100, "is wearing"),
        Slot0(0, "has"),
        Slot1(1, "has"),
        Slot2(2, "has"),
        Slot3(3, "has"),
        Slot4(4, "has"),
        Slot5(5, "has"),
        Slot6(6, "has"),
        Slot7(7, "has"),
        Slot8(8, "has");

        private static final @NotNull Map<String, Slot> SlotMap = new HashMap<>();

        private final @NotNull Function<@NotNull Player, @Nullable ItemStack> Getter;

        private final @NotNull String Verb;

        Slot(@NotNull Function<@NotNull Player, @Nullable ItemStack> getter, @NotNull String verb)
        {
            Getter = getter;
            Verb = verb;
            PutMap(this);
        }

        Slot(int slot, @NotNull String verb)
        {
            this(player -> player.getInventory().getItem(slot), verb);
        }

        public @Nullable ItemStack GetItemStack(@NotNull Player player)
        {
            return Getter.apply(player);
        }

        public @NotNull String GetName()
        {
            return name().substring(4).toLowerCase();
        }

        public @NotNull String GetVerb()
        {
            return Verb;
        }

        private static void PutMap(@NotNull Slot slot)
        {
            SlotMap.put(slot.GetName(), slot);
        }

        public static @Nullable Slot GetSlot(@NotNull String name)
        {
            return SlotMap.get(name.toLowerCase());
        }
    }
}
