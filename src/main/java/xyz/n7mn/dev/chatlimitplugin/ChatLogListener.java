package xyz.n7mn.dev.chatlimitplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

class ChatLogListener implements Listener {

    private final Plugin plugin = Bukkit.getPluginManager().getPlugin("ChatLimitPlugin");

    private List<ChatData> ChatDataList = Collections.synchronizedList(new ArrayList<>());

    @EventHandler
    public void PlayerJoinEvent (PlayerJoinEvent e){

        synchronized (ChatDataList){
            new BukkitRunnable() {
                @Override
                public void run() {
                    ChatData data = new ChatData(e.getPlayer().getName(), new Date());
                    ChatDataList.add(data);
                }
            }.runTaskLaterAsynchronously(plugin, 0L);
        }

    }

    @EventHandler
    public void PlayerQuitEvent (PlayerQuitEvent e){

        synchronized (ChatDataList){
            new BukkitRunnable() {
                @Override
                public void run() {

                    int i = 0;
                    for (ChatData data : ChatDataList){

                        if (data.getUserName().equals(e.getPlayer().getName())){

                            ChatDataList.remove(i);
                            return;
                        }

                        i++;
                    }

                }
            }.runTaskLaterAsynchronously(plugin, 0L);
        }

    }

    @EventHandler
    public void AsyncPlayerChatEvent (AsyncPlayerChatEvent e){

        System.out.println("test debug");
        int limitSec = plugin.getConfig().getInt("LimitSec");

        synchronized (ChatDataList){

            for (ChatData data : ChatDataList){
                if (data.getUserName().equals(e.getPlayer().getName())){

                    long prev = data.getDate().getTime();
                    long now = (new Date()).getTime();

                    System.out.println("prev : " + prev);
                    System.out.println("now : " + now);

                    if (now - prev <= (limitSec * 1000)){
                        e.getPlayer().sendMessage(ChatColor.YELLOW + "[ChatLimit] 連投規制中です！");
                        e.setCancelled(true);
                        return;
                    } else {
                        data.setDate(new Date());
                        return;
                    }
                }
            }

            ChatData data = new ChatData(e.getPlayer().getName(), new Date());
            ChatDataList.add(data);

        }

    }


}
