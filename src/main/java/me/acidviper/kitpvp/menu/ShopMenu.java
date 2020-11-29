package me.acidviper.kitpvp.menu;

import me.acidviper.kitpvp.kit.KitBase;
import me.acidviper.kitpvp.player.KitPlayer;
import me.acidviper.kitpvp.util.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShopMenu implements Listener {
    Inventory inv;

    public ShopMenu(Player p) {
        inv = Bukkit.createInventory(null, 27, "Shop");

        for (KitBase base : KitBase.getKits()) {
                if (base.isDefault()) continue;

                if (base.getIcon() == null) {
                    inv.addItem(ItemCreator.createItem(Material.LEATHER_HELMET, base.getName(), Collections.singletonList("Price: " + base.getPrice())));
                    continue;
                }

                List<String> description = new ArrayList<>();
                if (base.getIcon().getItemMeta().getLore() != null) description.addAll(base.getIcon().getItemMeta().getLore());
                description.add(ChatColor.WHITE + "Price: " + base.getPrice());

                inv.addItem(ItemCreator.createItem(base.getIcon().getType(), base.getIcon().getItemMeta().getDisplayName(), description));
        }

        p.openInventory(inv);
    }

    @EventHandler
    public void onInventoryInteract(InventoryClickEvent e) {
        if (inv == null) return;
        if (!(e.getInventory().equals(inv))) return;
        if (e.getCurrentItem() == null) return;

        e.setCancelled(true);

        for (KitBase base : KitBase.getKits()) {
            if (e.getCurrentItem().getItemMeta().getDisplayName().contains(base.getName())) {
                if (base.getPrice() > KitPlayer.getKitPlayers().get(e.getWhoClicked().getUniqueId()).getMoney()) {
                    e.getWhoClicked().sendMessage(ChatColor.RED + "You can't afford the " + base.getName() + " kit!");
                    e.getWhoClicked().closeInventory();
                    return;
                } else {
                    if (KitPlayer.getKitPlayers().get(e.getWhoClicked().getUniqueId()).getOwnedKits().contains(base.getName())) {
                        e.getWhoClicked().sendMessage(ChatColor.RED + "You already own this kit!");
                        e.getWhoClicked().closeInventory();
                        return;
                    }

                    e.getWhoClicked().sendMessage(ChatColor.GREEN + "You have purchased the " + base.getName() + " kit for " + base.getPrice() + " coins!");
                    e.getWhoClicked().closeInventory();

                    KitPlayer p =  KitPlayer.getKitPlayers().get(e.getWhoClicked().getUniqueId());
                    p.setMoney(p.getMoney() - base.getPrice());

                    p.getOwnedKits().add(base.getName());
                }
            }
        }
    }
}
