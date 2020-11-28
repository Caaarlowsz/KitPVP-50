package me.acidviper.kitpvp.listener;

import me.acidviper.kitpvp.player.KitPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class KillListener implements Listener {
    @EventHandler
    public void onPlayerKill(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player)) return;
        if (!(e.getEntity() instanceof Player)) return;

        KitPlayer killer = KitPlayer.getKitPlayers().get(e.getDamager().getUniqueId());
        killer.setCurrentKills(killer.getCurrentKills() + 1);
        killer.setAllTimeKills(killer.getAllTimeKills() + 1);


        KitPlayer player = KitPlayer.getKitPlayers().get(e.getDamager().getUniqueId());
        player.setCurrentDeaths(player.getCurrentDeaths() + 1);
        player.setAllTimeDeaths(player.getAllTimeDeaths() + 1);
    }

}
