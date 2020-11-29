package me.acidviper.kitpvp.listener;

import me.acidviper.kitpvp.KitPVP;
import me.acidviper.kitpvp.player.KitPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class KillListener implements Listener {

    @EventHandler
    public void playerDeathEvent(PlayerDeathEvent e) {
        KitPlayer player = KitPlayer.getKitPlayers().get(e.getEntity().getUniqueId());
        player.setCurrentKills(0);
        player.setCurrentDeaths(player.getCurrentDeaths() + 1);
        player.setAllTimeDeaths(player.getAllTimeDeaths() + 1);

        if (e.getEntity().getKiller() != null) {
            KitPlayer killer = KitPlayer.getKitPlayers().get(e.getEntity().getKiller().getUniqueId());
            killer.setMoney(killer.getMoney() + KitPVP.getInstance().getBConfig().getInt("KILLCOINS"));
            killer.setCurrentKills(killer.getCurrentKills() + 1);
            killer.setAllTimeKills(killer.getAllTimeKills() + 1);

            // TODO: Kill streak rewards.
            switch (killer.getCurrentKills()) {
                case 5:
                    break;
                case 10:
                    break;
                case 15:
                    break;
                case 20:
                    break;
            }
        }
    }
}
