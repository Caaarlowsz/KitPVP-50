package me.acidviper.kitpvp.kit;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class KitBase {
    @Getter private static final ArrayList<KitBase> kits = new ArrayList<>();

    @Getter private final String name;
    @Getter private final int price;
    @Getter private final ItemStack[] kitItems;

    @Getter @Setter private ItemStack icon;
    @Getter private final ItemStack helmet;
    @Getter private final ItemStack chestPlate;
    @Getter private final ItemStack leggings;
    @Getter private final ItemStack boots;

    @Getter private final boolean isDefault;

    public KitBase(String name, int price, ItemStack[] kitItems, boolean isDefault, ItemStack helmet, ItemStack chestPlate, ItemStack leggings, ItemStack boots, ItemStack icon) {
        this.name = name;
        this.price = price;
        this.kitItems = kitItems;
        this.isDefault = isDefault;

        this.helmet = helmet;
        this.chestPlate = chestPlate;
        this.leggings = leggings;
        this.boots = boots;

        this.icon = icon;

        kits.add(this);
    }

}
