package cloud.stivenfocs.SameWorldTPA.Commands;

import cloud.stivenfocs.SameWorldTPA.Loader;
import cloud.stivenfocs.SameWorldTPA.Requests.Request;
import cloud.stivenfocs.SameWorldTPA.Requests.RequestType;
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

public class tpa implements CommandExecutor, TabCompleter {

    private final Loader plugin;

    public tpa(Loader plugin) {
        this.plugin = plugin;
    }

    //////////////////////////////////////////////

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (Vars.hasPermission("tpa", sender)) {
            if (sender instanceof Player) {
                Player p = (Player) sender;

                if (args.length > 0) {
                    Player recipient = Bukkit.getPlayerExact(args[0]);

                    if (recipient != null) {
                        if (!p.getUniqueId().equals(recipient.getUniqueId())) {
                            if (RequestsHandler.doesPlayerAllowRequests(recipient.getUniqueId())) {
                                if (p.getWorld().getName().equals(recipient.getWorld().getName())) {
                                    Request existing_request = RequestsHandler.getRequest(p.getUniqueId(), recipient.getUniqueId());
                                    if (existing_request != null) {
                                        if (existing_request.request_type.equals(RequestType.TPA)) {
                                            Vars.sendString(Vars.setPlaceholders(Vars.request_already_sent, p, recipient), sender);
                                            return true;
                                        }

                                        existing_request.cancel();
                                    }

                                    RequestsHandler.createRequest(p.getUniqueId(), recipient.getUniqueId(), RequestType.TPA, Vars.request_duration, Vars.teleport_cooldown);
                                    Vars.sendString(Vars.setPlaceholders(Vars.tpa_request_sent, p, recipient), sender);
                                    Vars.sendString(Vars.setPlaceholders(Vars.tpa_request_received, p, recipient), recipient);
                                    Vars.playSound("tpa_received", recipient);
                                } else {
                                    Vars.sendString(Vars.setPlaceholders(Vars.not_same_world, p, recipient), sender);
                                }
                            } else {
                                Vars.sendString(Vars.setPlaceholders(Vars.player_disallow_requests, p, recipient), sender);
                            }
                        } else {
                            Vars.sendString(Vars.cant_request_yourself, sender);
                        }
                    } else {
                        Vars.sendString(Vars.unknown_player.replaceAll("%recipient_name%", args[0]).replaceAll("%recipient_displayname%", args[0]), sender);
                    }
                } else {
                    Vars.sendString(Vars.tpa_usage, sender);
                }
            } else {
                Vars.sendString(Vars.only_players, sender);
            }
        } else {
            Vars.sendString(Vars.no_permission, sender);
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String s, String[] args) {
        List<String> su = new ArrayList<>();

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("")) {
                if (Vars.hasPermission("tpa", sender)) {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        su.add(p.getName());
                    }
                }
            } else {
                if (Vars.hasPermission("tpa", sender)) {
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
