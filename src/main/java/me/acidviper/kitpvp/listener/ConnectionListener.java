package me.acidviper.kitpvp.listener;

import me.acidviper.kitpvp.player.KitPlayer;
import org.bukkit.attribute.Attribute;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ConnectionListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        new KitPlayer(e.getPlayer());

        e.getPlayer().getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(16.0);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        KitPlayer.getKitPlayers().get(e.getPlayer().getUniqueId()).saveConfig();
        KitPlayer.getKitPlayers().remove(e.getPlayer().getUniqueId());
    }
}
