package xyz.n7mn.dev.chatlimitplugin;

import org.bukkit.plugin.java.JavaPlugin;

public final class ChatLimitPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new ChatLogListener(),this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
