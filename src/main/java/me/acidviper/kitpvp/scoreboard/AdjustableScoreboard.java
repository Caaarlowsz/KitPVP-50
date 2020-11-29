package me.acidviper.kitpvp.scoreboard;

import io.github.thatkawaiisam.assemble.AssembleAdapter;
import me.acidviper.kitpvp.KitPVP;
import me.acidviper.kitpvp.player.KitPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class AdjustableScoreboard implements AssembleAdapter {

    public String getTitle(Player player) {
        return KitPVP.getInstance().getBConfig().getString("TITLE");
    }

    public List<String> getLines(Player player) {
        List<String> toReturn = new ArrayList<>();
        for (String string : KitPVP.getInstance().getBConfig().getStringList("SCOREBOARD")) {
            string = ChatColor.translateAlternateColorCodes('&', string);

            string = string.replace("<CKills>",  KitPlayer.getKitPlayers().get(player.getUniqueId()).getCurrentKills() + "");
            string = string.replace("<AKills>",  KitPlayer.getKitPlayers().get(player.getUniqueId()).getAllTimeKills() + "");
            string = string.replace("<CDeaths>",  KitPlayer.getKitPlayers().get(player.getUniqueId()).getCurrentDeaths() + "");
            string = string.replace("<ADeaths>",  KitPlayer.getKitPlayers().get(player.getUniqueId()).getAllTimeDeaths() + "");
            string = string.replace("<Money>", KitPlayer.getKitPlayers().get(player.getUniqueId()).getMoney() + "");
            toReturn.add(string);
        }
        return toReturn;
    }
}
