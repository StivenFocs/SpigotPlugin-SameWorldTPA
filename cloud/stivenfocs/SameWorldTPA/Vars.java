package cloud.stivenfocs.SameWorldTPA;

import cloud.stivenfocs.SameWorldTPA.Requests.Request;
import cloud.stivenfocs.SameWorldTPA.Requests.RequestsHandler;
import com.google.gson.JsonParser;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Vars {

    public static Loader plugin;
    public FileConfiguration getConfig() {
        return plugin.getConfig();
    }

    static Vars vars;
    public static Vars getVars() {
        if (vars == null) {
            vars = new Vars();
        }

        return vars;
    }

    public List<UUID> disallowed_requests = new ArrayList<>();

    //////////////////////////////////////////////

    public static Boolean debug = false;
    public static Integer request_duration = 30;
    public static Integer teleport_cooldown = 0;

    public static String prefix = "";
    public static String no_permission = "";
    public static String unknown_subcommand = "";
    public static String only_players = "";
    public static String unknown_player = "";
    public static String configuration_reloaded = "";
    public static String an_error_occurred = "";
    public static String cant_request_yourself = "";
    public static String no_requests_to_this_player = "";
    public static String no_requests_from_this_player = "";
    public static String not_same_world = "";
    public static String changed_world = "";
    public static String request_sender_changed_world = "";
    public static String request_recipient_changed_world = "";
    public static String tpa_usage = "";
    public static String tpa_request_sent = "";
    public static String tpa_request_received = "";
    public static String tpahere_usage = "";
    public static String tpahere_request_sent = "";
    public static String tpahere_request_received = "";
    public static String tpaccept_usage = "";
    public static String tpdeny_usage = "";
    public static String tpcancel_usage = "";
    public static String request_already_sent = "";
    public static String request_to_player_expired = "";
    public static String request_from_player_expired = "";
    public static String request_to_player_canceled = "";
    public static String request_from_player_canceled = "";
    public static String request_sender_left = "";
    public static String request_recipient_left = "";
    public static String requests_receive_enabled = "";
    public static String requests_receive_disabled = "";
    public static String player_disallow_requests = "";
    public static String request_accepted = "";
    public static String request_denied = "";
    public static String your_request_accepted = "";
    public static String your_request_denied = "";
    public static String dont_move = "";
    public static String you_moved = "";
    public static String player_moved = "";
    public static List<String> help = new ArrayList<>();

    //////////////////////////////////////////////

    public boolean reloadVars() {
        try {
            plugin.reloadConfig();

            getConfig().options().copyDefaults(true);
            getConfig().options().header("Developed with LOV by StivenFocs with the help of Mr_Depa");

            getConfig().addDefault("options.request_duration", 30);
            getConfig().addDefault("options.teleport_cooldown", 0);

            getConfig().addDefault("messages.prefix", "");
            getConfig().addDefault("messages.no_permission", "&cYou're not permitted to do this.");
            getConfig().addDefault("messages.unknown_subcommand", "&cUnknown subcommand! See the subcommands list with /sameworldtpa");
            getConfig().addDefault("messages.only_players", "&cOnly players can use this command");
            getConfig().addDefault("messages.unknown_player", "&cPlayer not found..");
            getConfig().addDefault("messages.configuration_reloaded", "&aConfiguration successfully reloaded");
            getConfig().addDefault("messages.an_error_occurred", "&cAn error occurred while executing this task..");
            getConfig().addDefault("messages.cant_request_yourself", "&cYou can't send a teleport request to yourself!");
            getConfig().addDefault("messages.no_requests_to_this_player", "&cNo active requests sent to &f%player_displayname%");
            getConfig().addDefault("messages.no_requests_from_this_player", "&cNo active requests from &f%player_displayname%");
            getConfig().addDefault("messages.not_same_world", "&cYou are allowed to send a teleport request to this player only if you and the player are in the same world.");
            getConfig().addDefault("messages.changed_world", "&cYOU CHANGED WORLD! All the teleport requests you sent or received were canceled.");
            getConfig().addDefault("messages.request_sender_changed_world", "&f%sender_displayname% &cchanged world, so the request that has sent to you, has been canceled.");
            getConfig().addDefault("messages.request_recipient_changed_world", "&f%recipient_displayname% &cchanged world, so the request that you sent to him, has been canceled.");
            getConfig().addDefault("messages.tpa_usage", "&eTo send a tpa request, use the command:/n&7/tpa <player name>");
            getConfig().addDefault("messages.tpa_request_sent", "&aTpa request sent to &f%recipient_displayname%");
            getConfig().addDefault("messages.tpa_request_received", "[\"\",{\"text\":\" [\",\"color\":\"gray\"},{\"text\":\"Same_World_Tpa\",\"bold\":true,\"color\":\"dark_blue\"},{\"text\":\"]\",\"color\":\"gray\"},{\"text\":\" %sender_displayname%\",\"bold\":true,\"color\":\"white\"},{\"text\":\" sent you a TPA request!\",\"color\":\"aqua\"},{\"text\":\"\\n\"},{\"text\":\"[Y]\",\"bold\":true,\"color\":\"dark_green\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/tpaccept %sender_name%\"}},{\"text\":\"   \",\"bold\":true,\"color\":\"dark_green\"},{\"text\":\"[N]\",\"bold\":true,\"color\":\"dark_red\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/tpdeny %sender_name%\"}}]");
            getConfig().addDefault("messages.tpahere_usage", "&eTo send a tpahere request, use the command:/n&7/tpa <player name>");
            getConfig().addDefault("messages.tpahere_request_sent", "&aTpahere request sent to &f%recipient_displayname%");
            getConfig().addDefault("messages.tpahere_request_received", "[\"\",{\"text\":\" [\",\"color\":\"gray\"},{\"text\":\"Same_World_Tpa\",\"bold\":true,\"color\":\"dark_blue\"},{\"text\":\"]\",\"color\":\"gray\"},{\"text\":\" %sender_displayname%\",\"bold\":true,\"color\":\"white\"},{\"text\":\" sent you a TPAHERE request!\",\"color\":\"aqua\"},{\"text\":\"\\n\"},{\"text\":\"[Y]\",\"bold\":true,\"color\":\"dark_green\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/tpaccept %sender_name%\"}},{\"text\":\"   \",\"bold\":true,\"color\":\"dark_green\"},{\"text\":\"[N]\",\"bold\":true,\"color\":\"dark_red\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/tpdeny %sender_name%\"}}]");
            getConfig().addDefault("messages.tpaccept_usage", "&eTo accept a request, use the command:/n&7/tpaccept [player name]");
            getConfig().addDefault("messages.tpdeny_usage", "&eTo deny a request, use the command:/n&7/tpdeny [player name]");
            getConfig().addDefault("messages.request_already_sent", "&cThis request has already been sent.. wait for the recipient to accept or deny.");
            getConfig().addDefault("messages.request_to_player_expired", "&cThe last request you sent to &f%recipient_displayname% &cis expired.");
            getConfig().addDefault("messages.request_from_player_expired", "&cThe teleport request from &f%sender_displayname% &cis expired.");
            getConfig().addDefault("messages.request_to_player_canceled", "&cThe last teleport request you sent to &f%recipient_displayname% &chas been canceled.");
            getConfig().addDefault("messages.request_from_player_canceled", "&cThe last teleport request from &f%sender_displayname% &chas been canceled.");
            getConfig().addDefault("messages.request_sender_left", "&f%sender_name% &chas left the server, so your teleport request has been canceled.");
            getConfig().addDefault("messages.request_recipient_left", "&f%recipient_name% &chas left the server, so your teleport request has been canceled.");
            getConfig().addDefault("messages.requests_receive_enabled", "&aFrom now, you allow to receive tpa/tpahere requests.");
            getConfig().addDefault("messages.requests_receive_disabled", "&cFrom now, you don't allow to receive tpa/tpahere requests.");
            getConfig().addDefault("messages.player_disallow_requests", "&cThis player does not allow to receive tpa/tpahere requests.");
            getConfig().addDefault("messages.request_accepted", "&aTeleport request accepted for &f%sender_displayname%");
            getConfig().addDefault("messages.request_denied", "&cTeleport request denied for &f%sender_displayname%");
            getConfig().addDefault("messages.your_request_accepted", "&f%recipient_displayname% &aaccepted your teleport request.");
            getConfig().addDefault("messages.your_request_denied", "&f%recipient_displayname% &cdenied your teleport request.");
            getConfig().addDefault("messages.dont_move", "&eYou will be teleported in &f%cooldown_seconds% &eseconds. &cDON'T MOVE!");
            getConfig().addDefault("messages.you_moved", "&cWhoops! you moved! your teleport has been canceled.");
            getConfig().addDefault("messages.player_moved", "&f%player_displayname% &cmoved! teleport canceled.");
            List<String> new_help = new ArrayList<>();
            new_help.add("&8&m======================================");
            new_help.add("&7* &8SameWorldTPA&7 - %version%");
            new_help.add("");
            new_help.add("&7* /sameworldtpa reload &8&m|&7 Reload the whole configuration");
            new_help.add("&7* /tptoggle <player name> &8&m|&7 Toggle the request allow for a player");
            new_help.add("");
            new_help.add("&8&m======================================");
            getConfig().addDefault("messages.help", new_help);

            plugin.saveConfig();
            plugin.reloadConfig();

            debug = getConfig().getBoolean("options.debug");
            request_duration = getConfig().getInt("options.request_duration", 30);
            teleport_cooldown = getConfig().getInt("options.teleport_cooldown", 0);

            prefix = getConfig().getString("messages.prefix", "");
            no_permission = getConfig().getString("messages.no_permission", "&cYou're not permitted to do this.");
            unknown_subcommand = getConfig().getString("messages.unknown_subcommand", "&cUnknown subcommand! See the subcommands list with /sameworldtpa");
            only_players = getConfig().getString("messages.only_players", "&cOnly players can use this command");
            unknown_player = getConfig().getString("messages.unknown_player", "&cPlayer not found..");
            configuration_reloaded = getConfig().getString("messages.configuration_reloaded", "&aConfiguration successfully reloaded");
            an_error_occurred = getConfig().getString("messages.an_error_occurred", "&cAn error occurred while executing this task..");
            cant_request_yourself = getConfig().getString("messages.cant_request_yourself", "&cYou can't send a teleport request to yourself!");
            no_requests_to_this_player = getConfig().getString("messages.no_requests_to_this_player", "&cNo active requests sent to &f%player_displayname%");
            no_requests_from_this_player = getConfig().getString("messages.no_requests_from_this_player", "&cNo active requests from &f%player_displayname%");
            not_same_world = getConfig().getString("messages.not_same_world", "&cYou're allowed to send a teleport request to this player if you and the player are in the same world.");
            changed_world = getConfig().getString("messages.changed_world", "&cYOU CHANGED WORLD! All the teleport requests you sent or received were canceled.");
            request_sender_changed_world = getConfig().getString("messages.request_sender_changed_world", "&f%sender_displayname% &cchanged world, so the request that has sent to you, has been canceled.");
            request_recipient_changed_world = getConfig().getString("messages.request_recipient_changed_world", "&f%recipient_displayname% &cchanged world, so the request that you sent to him, has been canceled.");
            tpa_usage = getConfig().getString("messages.tpa_usage", "&eTo send a tpa request, use the command:/n&7/tpa <player name>");
            tpa_request_sent = getConfig().getString("messages.tpa_request_sent", "&aTpa request sent to &f%recipient_displayname%");
            tpa_request_received = getConfig().getString("messages.tpa_request_received", "[\"\",{\"text\":\" [\",\"color\":\"gray\"},{\"text\":\"Same_World_Tpa\",\"bold\":true,\"color\":\"dark_blue\"},{\"text\":\"]\",\"color\":\"gray\"},{\"text\":\" %sender_displayname%\",\"bold\":true,\"color\":\"white\"},{\"text\":\" sent you a TPA request!\",\"color\":\"aqua\"},{\"text\":\"\\n\"},{\"text\":\"[Y]\",\"bold\":true,\"color\":\"dark_green\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/tpaccept %sender_name%\"}},{\"text\":\"   \",\"bold\":true,\"color\":\"dark_green\"},{\"text\":\"[N]\",\"bold\":true,\"color\":\"dark_red\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/tpdeny %sender_name%\"}}]");
            tpahere_usage = getConfig().getString("messages.tpahere_usage", "&eTo send a tpahere request, use the command:/n&7/tpa <player name>");
            tpahere_request_sent = getConfig().getString("messages.tpahere_request_sent", "&aTpahere request sent to &f%recipient_displayname%");
            tpahere_request_received = getConfig().getString("messages.tpahere_request_received", "[\"\",{\"text\":\" [\",\"color\":\"gray\"},{\"text\":\"Same_World_Tpa\",\"bold\":true,\"color\":\"dark_blue\"},{\"text\":\"]\",\"color\":\"gray\"},{\"text\":\" %sender_displayname%\",\"bold\":true,\"color\":\"white\"},{\"text\":\" sent you a TPAHERE request!\",\"color\":\"aqua\"},{\"text\":\"\\n\"},{\"text\":\"[Y]\",\"bold\":true,\"color\":\"dark_green\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/tpaccept %sender_name%\"}},{\"text\":\"   \",\"bold\":true,\"color\":\"dark_green\"},{\"text\":\"[N]\",\"bold\":true,\"color\":\"dark_red\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/tpdeny %sender_name%\"}}]");
            tpaccept_usage = getConfig().getString("messages.tpaccept_usage", "&eTo accept a request, use the command:/n&7/tpaccept [player name]");
            tpdeny_usage = getConfig().getString("messages.tpdeny_usage", "&eTo deny a request, use the command:/n&7/tpdeny [player name]");
            tpcancel_usage = getConfig().getString("messages.tpcancel_usage", "&eTo cancel a sent request, use the command:/n&7/tpcancel <player name>");
            request_already_sent = getConfig().getString("messages.request_already_sent", "&cThis request has already been sent.. wait for the recipient to accept or deny.");
            request_to_player_expired = getConfig().getString("messages.request_to_player_expired", "&cThe request you sent to &f%recipient_displayname% &cis expired.");
            request_from_player_expired = getConfig().getString("messages.request_from_player_expired", "&cThe teleport request from &f%sender_displayname% &cis expired.");
            request_to_player_canceled = getConfig().getString("messages.request_to_player_canceled", "&cThe last teleport request you sent to &f%recipient_displayname% &chas been canceled.");
            request_from_player_canceled = getConfig().getString("messages.request_from_player_canceled", "&cThe last teleport request from &f%sender_displayname% &chas been canceled.");
            request_sender_left = getConfig().getString("messages.request_sender_left", "&f%sender_name% &chas left the server, so your teleport request has been canceled.");
            request_recipient_left = getConfig().getString("messages.request_recipient_left", "&f%recipient_name% &chas left the server, so your teleport request has been canceled.");
            requests_receive_enabled = getConfig().getString("messages.requests_receive_enabled", "&aFrom now, you allow to receive tpa/tpahere requests.");
            requests_receive_disabled = getConfig().getString("messages.requests_receive_disabled", "&cFrom now, you don't allow to receive tpa/tpahere requests.");
            player_disallow_requests = getConfig().getString("messages.player_disallow_requests", "&cThis player does not allow to receive tpa/tpahere requests.");
            request_accepted = getConfig().getString("messages.request_accepted", "&aTeleport request accepted for &f%sender_displayname%");
            request_denied = getConfig().getString("messages.request_denied", "&cTeleport request denied for &f%sender_displayname%");
            your_request_accepted = getConfig().getString("messages.your_request_accepted", "&f%recipient_displayname% &aaccepted your teleport request.");
            your_request_denied = getConfig().getString("messages.your_request_denied", "&f%recipient_displayname% &cdenied your teleport request.");
            dont_move = getConfig().getString("messages.dont_move", "&eYou will be teleported in &f%cooldown_seconds% &eseconds. &cDON'T MOVE!");
            you_moved = getConfig().getString("messages.you_moved", "&cWhoops! you moved! your teleport has been canceled.");
            player_moved = getConfig().getString("messages.player_moved", "&f%player_displayname% &cmoved! teleport canceled.");
            help = getConfig().getStringList("messages.help");

            plugin.getLogger().info("Configuration successfully reloaded");
            return true;
        } catch (Exception ex) {
            plugin.getLogger().severe("An exception occurred while trying to reload the whole configuration.. disabling the plugin");
            ex.printStackTrace();
            Bukkit.getPluginManager().disablePlugin(plugin);
            return false;
        }
    }

    //////////////////////////////////////////////

    public static boolean hasPermission(String permission, CommandSender sender) {
        return sender.hasPermission("sameworldtpa." + permission);
    }

    public static String setPlaceholders(String text, OfflinePlayer sender, OfflinePlayer recipient) {
        text = text.replaceAll("%sender_name%", sender.getName());
        text = text.replaceAll("%recipient_name%", recipient.getName());
        if (sender.isOnline()) {
            text = text.replaceAll("%sender_displayname%", Bukkit.getPlayer(sender.getUniqueId()).getDisplayName());
        }
        if (recipient.isOnline()) {
            text = text.replaceAll("%recipient_displayname%", Bukkit.getPlayer(recipient.getUniqueId()).getDisplayName());
        }

        Request request = RequestsHandler.getRequest(sender.getUniqueId(), recipient.getUniqueId());
        if (request != null) {
            text = text.replaceAll("%seconds%", String.valueOf(request.getCounter()));
        }

        return text;
    }
    public static String setPlaceholders(String text, UUID sender_uuid, UUID recipient_uuid) {
        return setPlaceholders(text, Bukkit.getOfflinePlayer(sender_uuid), Bukkit.getOfflinePlayer(recipient_uuid));
    }

    //////////////////////////////////////////////

    public static void playSound(String sound_name, Player p) {
        if (debug) plugin.getLogger().info("Getting the sound: " + sound_name);
        String path = "sounds." + sound_name + ".";
        if (debug) plugin.getLogger().info("path: " + path);

        try {
            Sound sound;
            try {
                sound = Sound.valueOf(plugin.getConfig().getString(path + "name"));
            } catch (Exception ex) {
                if (debug) plugin.getLogger().severe("Couldn't find the minecraft sound named " + plugin.getConfig().getString(path + "name") + " in the configuration sound: " + sound_name);
                return;
            }

            p.getLocation().getWorld().playSound(p.getLocation(), sound, plugin.getConfig().getInt(path + "pitch"), plugin.getConfig().getInt(path + "volume"));
        } catch (Exception ex) {
            if (debug) plugin.getLogger().warning("An exception occurred while trying to retrieve a sound from the configuration (" + sound_name + ")");
            ex.printStackTrace();
        }
    }

    public static void sendString(String text, CommandSender sender) {
        if (sender == null) {
            if (debug) {
                plugin.getLogger().warning("Couldn't send a message to a player due to null sender");
                plugin.getLogger().warning("\"" + text + "\"");
            }
            return;
        }

        Player p = null;
        if (sender instanceof Player) p = (Player) sender;

        if (text.length() > 0) {
            if (plugin.getConfig().getString(text) != null) {
                text = plugin.getConfig().getString(text);
            }

            for (String line : text.split("/n")) {
                line = prefix + line;
                line = line.replaceAll("%version%", plugin.getDescription().getVersion()).replaceAll("%name%", sender.getName());
                if (p != null) {
                    line = line.replaceAll("%displayname%", ((Player) sender).getDisplayName());

                    if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
                        line = PlaceholderAPI.setPlaceholders(p, line);
                    }
                }

                if (isValidJson(line)) {
                    if (sender instanceof Player) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + sender.getName() + " " + line);
                    } else {
                        sender.sendMessage(line);
                    }
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', line));
                }
            }
        }
    }

    public static void sendColoredList(List<String> uncoloredList, CommandSender sender) {
        Player p = null;
        if (sender instanceof Player) p = (Player) sender;

        for (String line : uncoloredList) {
            line = prefix + line;
            line = line.replaceAll("%version%", plugin.getDescription().getVersion()).replaceAll("%name%", sender.getName());
            if (p != null) {
                line = line.replaceAll("%displayname%", ((Player) sender).getDisplayName());

                if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
                    line = PlaceholderAPI.setPlaceholders(p, line);
                }
            }

            if (isValidJson(line)) {
                if (sender instanceof Player) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + sender.getName() + " " + line);
                } else {
                    sender.sendMessage(line);
                }
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', line));
            }
        }
    }

    //////////////////////////////////////////////

    public static boolean isValidJson(String json) {
        try {
            return new JsonParser().parse(json).getAsJsonObject() != null;
        } catch (Throwable ignored) {}
        try {
            return new JsonParser().parse(json).getAsJsonArray() != null;
        } catch (Throwable ignored) {}
        return false;
    }

}
