package me.acidviper.kitpvp.player;

import lombok.Getter;
import lombok.Setter;
import me.acidviper.kitpvp.KitPVP;
import me.acidviper.kitpvp.kit.KitBase;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class KitPlayer {
    @Getter private static final HashMap<UUID, KitPlayer> kitPlayers = new HashMap<>();

    @Getter private UUID uuid;
    @Getter private Player playerObject;

    @Getter @Setter private int currentKills;
    @Getter @Setter private int currentDeaths;

    @Getter @Setter private int allTimeKills;
    @Getter @Setter private int allTimeDeaths;

    @Getter @Setter private int money;

    @Getter private List<String> ownedKits = new ArrayList<>();

    String prefix;

    public KitPlayer(Player p) {
        this.uuid = p.getUniqueId();
        if (KitPVP.getInstance().getDatabase().get(uuid.toString() + "." + uuid.toString()) == null) createConfig(); else {
            prefix = uuid.toString() + ".";
            allTimeKills = KitPVP.getInstance().getDatabase().getInt(prefix + "ALLTIMEKILLS");
            allTimeDeaths = KitPVP.getInstance().getDatabase().getInt(prefix + "ALLTIMEDEATHS");
            money = KitPVP.getInstance().getDatabase().getInt(prefix + "MONEY");
            ownedKits = KitPVP.getInstance().getDatabase().getStringList(prefix + "OWNEDKITS");

            updateKits();
        }

        playerObject = p;

        kitPlayers.put(uuid, this);
    }

    public void createConfig() {
        prefix = uuid.toString() + ".";

        KitPVP.getInstance().getDatabase().set(prefix + uuid.toString(), uuid.toString());

        KitPVP.getInstance().getDatabase().set(prefix + "ALLTIMEKILLS", 0);
        KitPVP.getInstance().getDatabase().set(prefix + "ALLTIMEDEATHS", 0);
        KitPVP.getInstance().getDatabase().set(prefix + "MONEY", KitPVP.getInstance().getBConfig().getInt("DEFAULT_MONEY"));
        money = KitPVP.getInstance().getBConfig().getInt("DEFAULT_MONEY");

        List<String> defaultKits = new ArrayList<>();
        for (KitBase kit : KitBase.getKits()) if (kit.isDefault()) defaultKits.add(kit.getName());

        KitPVP.getInstance().getDatabase().set(prefix + "OWNEDKITS", defaultKits);

        KitPVP.getInstance().saveDatabase();
    }

    public void saveConfig() {
        KitPVP.getInstance().getDatabase().set(prefix + uuid.toString(), uuid.toString());

        KitPVP.getInstance().getDatabase().set(prefix + "ALLTIMEKILLS", allTimeKills);
        KitPVP.getInstance().getDatabase().set(prefix + "ALLTIMEDEATHS", allTimeDeaths);
        KitPVP.getInstance().getDatabase().set(prefix + "MONEY", money);
        KitPVP.getInstance().getDatabase().set(prefix + "OWNEDKITS", ownedKits);

        KitPVP.getInstance().saveDatabase();
    }

    public void updateKits() {
        for (KitBase kits : KitBase.getKits()) {
            if (kits.isDefault()) {
                if (!(ownedKits.contains(kits.getName()))) {
                    ownedKits.add(kits.getName());
                }
            }
        }
    }
}
