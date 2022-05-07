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

public class tpcancel implements CommandExecutor, TabCompleter {

    private final Loader plugin;

    public tpcancel(Loader plugin) {
        this.plugin = plugin;
    }

    //////////////////////////////////////////////

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (Vars.hasPermission("tpcancel", sender)) {
            if (sender instanceof Player) {
                Player p = (Player) sender;

                if (args.length > 0) {
                    Player request_recipient = Bukkit.getPlayerExact(args[1]);

                    if (request_recipient != null) {
                        Request request = RequestsHandler.getRequest(request_recipient.getUniqueId(), p.getUniqueId());

                        if (request != null) {
                            request.deny();
                            Vars.sendString(Vars.setPlaceholders(Vars.request_to_player_canceled, p, request_recipient), sender);
                            Vars.sendString(Vars.setPlaceholders(Vars.request_from_player_canceled, p, request_recipient), request_recipient);
                        } else {
                            Vars.sendString(Vars.setPlaceholders(Vars.no_requests_to_this_player.replaceAll("%player_name%", request_recipient.getName()).replaceAll("%player_displayname%", request_recipient.getDisplayName()), p, request_recipient), sender);
                        }
                    } else {
                        Vars.sendString(Vars.unknown_player.replaceAll("%recipient_name%", args[0]).replaceAll("%recipient_displayname%", args[0]), sender);
                    }
                } else {
                    Vars.sendString(Vars.tpcancel_usage, sender);
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
                if (Vars.hasPermission("tpcancel", sender)) {
                    if (sender instanceof Player) {
                        Player p = (Player) sender;

                        for (Request user_pending_requests : RequestsHandler.getSentRequests(p.getUniqueId())) {
                            su.add(Bukkit.getPlayer(user_pending_requests.sender).getName());
                        }
                    }
                }
            } else {
                if (Vars.hasPermission("tpcancel", sender)) {
                    if (sender instanceof Player) {
                        Player p = (Player) sender;

                        for (Request user_pending_requests : RequestsHandler.getSentRequests(p.getUniqueId())) {
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
