package me.reach.utils;

import dev.slickcollections.kiwizin.plugin.KPlugin;
import me.reach.utils.cmd.Commands;
import me.reach.utils.listener.Listeners;
import me.reach.utils.listener.player.PluginMessageListener;
import me.reach.utils.lobby.UpgradeNPC;
import me.reach.utils.manager.VanishManager;
import me.reach.utils.objects.Upgrade;

public class Main extends KPlugin {

    public static Main instance;
    private boolean validInit;

    @Override
    public void start() {
        instance = this;
    }

    @Override
    public void load() {

    }

    @Override
    public void enable() {
        Language.setupLanguage();
        Listeners.setupListeners();
        Commands.setupCommands();
        VanishManager.setupVanishManager();
        Upgrade.setupUpgrades();
        UpgradeNPC.setupNPCs();

        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getServer().getMessenger().registerOutgoingPluginChannel(this, "MESSAGE_UTILS");
        getServer().getMessenger().registerIncomingPluginChannel(this, "MESSAGE_UTILS", new PluginMessageListener());

        validInit = true;
        this.getLogger().info("O plugin foi ativado.");
    }

    @Override
    public void disable() {
        if (validInit) {
            UpgradeNPC.listNPCs().forEach(UpgradeNPC::destroy);
        }

        this.getLogger().info("O plugin foi desativado");
    }

    public static Main getInstance() {
        return instance;
    }
}
