package me.acidviper.kitpvp.kit;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class KitBase {
    @Getter private static final ArrayList<KitBase> kits = new ArrayList<>();

    @Getter private String name;
    @Getter private int price;
    @Getter private List<ItemStack> kitItems;
    @Getter private boolean isDefault;

    public KitBase(String name, int price, List<ItemStack> kitItems, boolean isDefault) {
        this.name = name;
        this.price = price;
        this.kitItems = kitItems;
        this.isDefault = true;

        kits.add(this);
    }

}
