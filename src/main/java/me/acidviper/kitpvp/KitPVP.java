package me.acidviper.kitpvp;

import io.github.thatkawaiisam.assemble.Assemble;
import lombok.Getter;
import lombok.Setter;
import me.acidviper.kitpvp.command.CreateKit;
import me.acidviper.kitpvp.command.KitSelector;
import me.acidviper.kitpvp.command.SetIcon;
import me.acidviper.kitpvp.command.Shop;
import me.acidviper.kitpvp.kit.KitBase;
import me.acidviper.kitpvp.listener.ConnectionListener;
import me.acidviper.kitpvp.listener.KillListener;
import me.acidviper.kitpvp.player.KitPlayer;
import me.acidviper.kitpvp.scoreboard.AdjustableScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

public final class KitPVP extends JavaPlugin {
    @Getter private static KitPVP instance;

    @Getter @Setter private FileConfiguration database;
    @Getter private FileConfiguration bConfig;

    @Override
    public void onEnable() {
        instance = this;

        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "KitPVP has been enabled");

        for (Player p : Bukkit.getOnlinePlayers()) {
            p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(16.0);
        }

        database = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "database.yml"));
        bConfig = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "config.yml"));

        registerCommands();
        registerListeners();

        setUpKits();

        new Assemble(this, new AdjustableScoreboard());
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "KitPVP has been disabled");
        for (UUID player : KitPlayer.getKitPlayers().keySet()) {
            KitPlayer p = KitPlayer.getKitPlayers().get(player);
            p.saveConfig();
        }
        saveDatabase();
        saveConfig();
    }

    public void saveDatabase() {
        try {
            database.save(new File(getDataFolder(), "database.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        database = (YamlConfiguration.loadConfiguration(new File(getDataFolder(), "database.yml")));

        reloadConfig();
    }

    public void saveConfig() {
        try {
            bConfig.save(new File(getDataFolder(), "config.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        bConfig = (YamlConfiguration.loadConfiguration(new File(getDataFolder(), "config.yml")));
    }

    public void registerCommands() {
        getCommand("createkit").setExecutor(new CreateKit());
        getCommand("seticon").setExecutor(new SetIcon());
        getCommand("kits").setExecutor(new KitSelector());
        getCommand("shop").setExecutor(new Shop());
    }

    public void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new ConnectionListener(), this);
        Bukkit.getPluginManager().registerEvents(new KillListener(), this);
    }

    public void setUpKits() {
        saveDatabase();
        saveConfig();
        for (String name : KitPVP.getInstance().getDatabase().getStringList("KITS")) {
            ItemStack[] kitArray = database.getList(name + ".KIT").toArray(new ItemStack[0]);

            new KitBase(name, database.getInt(name + ".PRICE"), kitArray, database.getBoolean(name + ".DEFAULT"),
                    database.getItemStack(name + ".HELMET"), database.getItemStack(name + ".CHESTPLATE"), database.getItemStack(name + ".LEGGINGS"),
                    database.getItemStack(name + ".BOOTS"), database.getItemStack(name + ".ICON"));
        }

        for (UUID pUUID : KitPlayer.getKitPlayers().keySet()) {
            KitPlayer p = KitPlayer.getKitPlayers().get(pUUID);
            p.updateKits();
        }
    }

    public void reloadConfig() {
        database = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "database.yml"));
    }
}
