package me.acidviper.kitpvp;

import lombok.Getter;
import lombok.Setter;
import me.acidviper.kitpvp.command.CreateKit;
import me.acidviper.kitpvp.kit.KitBase;
import me.acidviper.kitpvp.listener.ConnectionListener;
import me.acidviper.kitpvp.listener.KillListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class KitPVP extends JavaPlugin {
    @Getter private static KitPVP instance;

    @Getter @Setter private FileConfiguration database;

    @Override
    public void onEnable() {
        instance = this;

        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "KitPVP has been enabled");
        // TODO: Find out the command for disabling new pvp
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "");

        database = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "database.yml"));

        registerCommands();
        registerListeners();

        setUpKits();
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "KitPVP has been disabled");
    }

    public void saveDatabase() {
        try {
            database.save(new File(getDataFolder(), "database.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        database = (YamlConfiguration.loadConfiguration(new File(getDataFolder(), "database.yml")));
    }

    public void registerCommands() {
        getCommand("createkit").setExecutor(new CreateKit());
    }

    public void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new ConnectionListener(), this);
        Bukkit.getPluginManager().registerEvents(new KillListener(), this);
    }

    public void setUpKits() {
        for (String name : KitPVP.getInstance().getDatabase().getStringList("KITS")) {
            new KitBase(name, database.getInt(name + ".PRICE"), null, database.getBoolean(name + ".DEFAULT")); // TODO: get items from database
        }
    }
}
