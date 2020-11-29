package me.acidviper.kitpvp.menu;

import me.acidviper.kitpvp.kit.KitBase;
import me.acidviper.kitpvp.player.KitPlayer;
import me.acidviper.kitpvp.util.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import java.util.Collections;

public class KitMenu implements Listener {
    Inventory inv;

    public KitMenu(Player p) {
        inv = Bukkit.createInventory(null, 27, "Kit Selector");

        for (String kitName : KitPlayer.getKitPlayers().get(p.getUniqueId()).getOwnedKits()) {
            for (KitBase base : KitBase.getKits()) {
                if (kitName.equalsIgnoreCase(base.getName())) {
                    if (base.getIcon() == null) {
                        inv.addItem(ItemCreator.createItem(Material.LEATHER_HELMET, base.getName(), Collections.singletonList("")));
                        continue;
                    }
                    inv.addItem(base.getIcon());
                }
            }
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
                e.getWhoClicked().closeInventory();

                e.getWhoClicked().getInventory().setContents(base.getKitItems());

                e.getWhoClicked().getInventory().setHelmet(base.getHelmet());
                e.getWhoClicked().getInventory().setChestplate(base.getChestPlate());
                e.getWhoClicked().getInventory().setLeggings(base.getLeggings());
                e.getWhoClicked().getInventory().setBoots(base.getBoots());
            }
        }
    }
}
