package cloud.stivenfocs.SameWorldTPA;

import cloud.stivenfocs.SameWorldTPA.Commands.*;
import cloud.stivenfocs.SameWorldTPA.Requests.Request;
import cloud.stivenfocs.SameWorldTPA.Requests.RequestsHandler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Loader extends JavaPlugin {

    public void onEnable() {
        Vars.plugin = this;
        Vars vars = Vars.getVars();

        vars.reloadVars();

        getCommand("sameworldtpa").setExecutor(new sameworldtpa(this));
        getCommand("tpa").setExecutor(new tpa(this));
        getCommand("tpaccept").setExecutor(new tpaccept(this));
        getCommand("tpdeny").setExecutor(new tpdeny(this));
        getCommand("tpahere").setExecutor(new tpahere(this));
        getCommand("tpcancel").setExecutor(new tpcancel(this));
        getCommand("tptoggle").setExecutor(new tptoggle(this));

        getCommand("sameworldtpa").setTabCompleter(new sameworldtpa(this));
        getCommand("tpa").setTabCompleter(new tpa(this));
        getCommand("tpaccept").setTabCompleter(new tpaccept(this));
        getCommand("tpdeny").setTabCompleter(new tpdeny(this));
        getCommand("tpahere").setTabCompleter(new tpahere(this));
        getCommand("tpcancel").setTabCompleter(new tpcancel(this));
        getCommand("tptoggle").setTabCompleter(new tptoggle(this));

        Bukkit.getPluginManager().registerEvents(new EventListener(this), this);
    }

}
