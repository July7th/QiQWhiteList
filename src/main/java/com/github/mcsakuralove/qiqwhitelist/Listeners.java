
package com.github.mcsakuralove.qiqwhitelist;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

public class Listeners implements Listener {
    public String user;

    public Listeners() {
    }

    @EventHandler(
            priority = EventPriority.LOWEST
    )
    public void onJoin(PlayerLoginEvent e) {
        if (main.Enable){
            this.user = "users." + e.getPlayer().getName();
            if (main.use_mysql) {
                main.link.connect();
                String name = e.getPlayer().getName();
                if (main.link.exists(name)) {
                    e.allow();
                } else {
                    e.disallow(Result.KICK_WHITELIST, ChatColor.translateAlternateColorCodes('&', main.sqlkickmessage));
                }

                main.link.closeConn();
            } else if (main.playerconfigfile.contains(this.user)) {
                e.allow();
            } else {
                e.disallow(Result.KICK_WHITELIST, ChatColor.translateAlternateColorCodes('&', main.ymlkickmessage));
            }
        }
    }
}
