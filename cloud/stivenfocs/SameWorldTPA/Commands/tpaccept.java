package cloud.stivenfocs.SameWorldTPA.Commands;

import cloud.stivenfocs.SameWorldTPA.Loader;
import cloud.stivenfocs.SameWorldTPA.Requests.Request;
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

public class tpaccept implements CommandExecutor, TabCompleter {

    private final Loader plugin;

    public tpaccept(Loader plugin) {
        this.plugin = plugin;
    }

    //////////////////////////////////////////////

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (Vars.hasPermission("tpaccept", sender)) {
            if (sender instanceof Player) {
                Player p = (Player) sender;

                if (args.length > 0) {
                    Player request_sender = Bukkit.getPlayerExact(args[0]);

                    if (request_sender != null) {
                        Request request = RequestsHandler.getRequest(request_sender.getUniqueId(), p.getUniqueId());

                        if (request != null) {
                            request.accept();
                            Vars.sendString(Vars.setPlaceholders(Vars.request_accepted, request_sender, p), sender);
                            Vars.sendString(Vars.setPlaceholders(Vars.your_request_accepted, request_sender, p), request_sender);
                        } else {
                            Vars.sendString(Vars.setPlaceholders(Vars.no_requests_from_this_player.replaceAll("%player_name%", request_sender.getName()).replaceAll("%player_displayname%", request_sender.getDisplayName()), request_sender, p), sender);
                        }
                    } else {
                        Vars.sendString(Vars.unknown_player.replaceAll("%sender_name%", args[0]).replaceAll("%sender_displayname%", args[0]), sender);
                    }
                } else {
                    List<Request> received_requests = RequestsHandler.getReceivedRequests(p.getUniqueId());
                    if (received_requests.size() == 1) {
                        Request request = received_requests.get(0);

                        request.accept();
                        Vars.sendString(Vars.setPlaceholders(Vars.request_accepted, request.sender, request.recipient), sender);
                        Vars.sendString(Vars.setPlaceholders(Vars.your_request_accepted, request.sender, request.recipient), Bukkit.getPlayer(request.sender));
                    } else {
                        Vars.sendString(Vars.tpaccept_usage, sender);
                    }
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
                if (Vars.hasPermission("tpaccept", sender)) {
                    if (sender instanceof Player) {
                        Player p = (Player) sender;

                        for (Request user_pending_requests : RequestsHandler.getReceivedRequests(p.getUniqueId())) {
                            su.add(Bukkit.getPlayer(user_pending_requests.sender).getName());
                        }
                    }
                }
            } else {
                if (Vars.hasPermission("tpaccept", sender)) {
                    if (sender instanceof Player) {
                        Player p = (Player) sender;

                        for (Request user_pending_requests : RequestsHandler.getReceivedRequests(p.getUniqueId())) {
                            String sender_name = Bukkit.getPlayer(user_pending_requests.sender).getName();

                            if (sender_name.toLowerCase().startsWith(args[0].toLowerCase())) {
                                su.add(sender_name);
                            }
                        }
                    }
                }
            }
        }

        return su;
    }
}
