package cloud.stivenfocs.SameWorldTPA;

import cloud.stivenfocs.SameWorldTPA.Requests.Request;
import cloud.stivenfocs.SameWorldTPA.Requests.RequestsHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class EventListener implements Listener {

    private Loader plugin;

    public EventListener(Loader plugin) {
        this.plugin = plugin;
    }

    ////////////////////////////////////////////////

    @EventHandler
    public void onPlayerWorldChange(PlayerChangedWorldEvent event) {
        Player p = event.getPlayer();

        if (RequestsHandler.getSentRequests(p.getUniqueId()).size() > 0 || RequestsHandler.getReceivedRequests(p.getUniqueId()).size() > 0) {
            Vars.sendString(Vars.changed_world, p);

            for (Request sent_request : RequestsHandler.getSentRequests(p.getUniqueId())) {
                sent_request.cancel();
                Vars.sendString(Vars.setPlaceholders(Vars.request_sender_changed_world, sent_request.sender, sent_request.recipient), Bukkit.getPlayer(sent_request.recipient));
            }

            for (Request received_request : RequestsHandler.getReceivedRequests(p.getUniqueId())) {
                received_request.cancel();
                Vars.sendString(Vars.setPlaceholders(Vars.request_recipient_changed_world, received_request.sender, received_request.recipient), Bukkit.getPlayer(received_request.sender));
            }
        }
    }

}
