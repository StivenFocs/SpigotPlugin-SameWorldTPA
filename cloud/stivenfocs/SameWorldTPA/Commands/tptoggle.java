package cloud.stivenfocs.SameWorldTPA.Commands;

import cloud.stivenfocs.SameWorldTPA.Loader;
import cloud.stivenfocs.SameWorldTPA.Requests.RequestsHandler;
import cloud.stivenfocs.SameWorldTPA.Vars;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class tptoggle implements CommandExecutor, TabCompleter {

    private final Loader plugin;

    public tptoggle(Loader plugin) {
        this.plugin = plugin;
    }

    //////////////////////////////////////////////

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (Vars.hasPermission("tptoggle", sender)) {
            if (args.length > 0) {
                if (Vars.hasPermission("tptoggle.others", sender)) {
                    Player p = Bukkit.getPlayerExact(args[0]);

                    if (p != null) {
                        RequestsHandler.setPlayerRequestsAllow(p.getUniqueId(), !RequestsHandler.doesPlayerAllowRequests(p.getUniqueId()));
                        if (RequestsHandler.doesPlayerAllowRequests(p.getUniqueId())) {
                            Vars.sendString(Vars.requests_receive_enabled, sender);
                        } else {
                            Vars.sendString(Vars.requests_receive_disabled, sender);
                        }
                    } else {
                        Vars.sendString(Vars.unknown_player, sender);
                    }
                } else {
                    Vars.sendString(Vars.no_permission, sender);
                }
            } else {
                if (sender instanceof Player) {
                    Player p = (Player) sender;

                    RequestsHandler.setPlayerRequestsAllow(p.getUniqueId(), !RequestsHandler.doesPlayerAllowRequests(p.getUniqueId()));
                    if (RequestsHandler.doesPlayerAllowRequests(p.getUniqueId())) {
                        Vars.sendString(Vars.requests_receive_enabled, sender);
                    } else {
                        Vars.sendString(Vars.requests_receive_disabled, sender);
                    }
                } else {
                    Vars.sendString(Vars.only_players, sender);
                }
            }
        } else {
            Vars.sendString(Vars.no_permission, sender);
        }
        return false;
    }

    //////////////////////////////////////////////

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String s, String[] args) {
        List<String> su = new ArrayList<>();

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("")) {
                if (Vars.hasPermission("tptoggle.others", sender)) {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        su.add(p.getName());
                    }
                }
            } else {
                if (Vars.hasPermission("tptoggle.others", sender)) {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        if (p.getName().toLowerCase().startsWith(args[0].toLowerCase())) {
                            su.add(p.getName());
                        }
                    }
                }
            }
        }

        return su;
    }
}
