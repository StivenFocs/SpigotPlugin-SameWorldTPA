package cloud.stivenfocs.SameWorldTPA.Commands;

import cloud.stivenfocs.SameWorldTPA.Loader;
import cloud.stivenfocs.SameWorldTPA.Vars;
import org.bukkit.command.*;

import java.util.ArrayList;
import java.util.List;

public class sameworldtpa implements CommandExecutor, TabCompleter {

    private final Loader plugin;

    public sameworldtpa(Loader plugin) {
        this.plugin = plugin;
    }

    //////////////////////////////////////////////

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (args.length == 0) {
            if (Vars.hasPermission("admin.help", sender)) {
                Vars.sendColoredList(Vars.help, sender);
            } else {
                Vars.sendString(Vars.no_permission, sender);
            }
        } else {
            if (args[0].equalsIgnoreCase("reload")) {
                if (Vars.hasPermission("admin.reload", sender)) {
                    if (!(sender instanceof ConsoleCommandSender)) {
                        if (Vars.getVars().reloadVars()) {
                            Vars.sendString(Vars.configuration_reloaded, sender);
                        } else {
                            Vars.sendString(Vars.an_error_occurred, sender);
                        }
                    } else {
                        Vars.getVars().reloadVars();
                    }
                } else {
                    Vars.sendString(Vars.no_permission, sender);
                }
                return true;
            }

            Vars.sendString(Vars.unknown_subcommand, sender);
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String s, String[] args) {
        List<String> su = new ArrayList<>();

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("")) {
                if (Vars.hasPermission("admin.reload", sender)) {
                    su.add("reload");
                }
            } else {
                if ("reload".startsWith(args[0].toLowerCase())) {
                    if (Vars.hasPermission("admin.reload", sender)) {
                        su.add("reload");
                    }
                }
            }
        }

        return su;
    }
}
