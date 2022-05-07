package cloud.stivenfocs.SameWorldTPA.Requests;

import cloud.stivenfocs.SameWorldTPA.Vars;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Request {

    public final UUID sender;
    public final UUID recipient;
    public final RequestType request_type;

    protected Integer taskId = 0;

    private RequestStatus status = RequestStatus.PENDING;
    private Integer counter;

    private Integer cooldown_counter;
    private Integer cooldown_counter_ticks = 20;
    private Integer cooldown_counter_taskId = 0;

    public Request(UUID sender, UUID recipient, RequestType request_type, Integer request_duration, Integer request_teleport_cooldown) {
        this.sender = sender;
        this.recipient = recipient;
        this.request_type = request_type;
        this.counter = request_duration;
        this.cooldown_counter = request_teleport_cooldown;

        taskId = Bukkit.getScheduler().runTaskTimer(Vars.plugin, () -> {
            if (Bukkit.getPlayer(sender) == null) {
                setStatus(RequestStatus.CANCELED);
                Vars.sendString(Vars.setPlaceholders(Vars.request_sender_left, sender, recipient), Bukkit.getPlayer(recipient));
            }
            if (Bukkit.getPlayer(recipient) == null) {
                setStatus(RequestStatus.CANCELED);
                Vars.sendString(Vars.setPlaceholders(Vars.request_recipient_left, sender, recipient), Bukkit.getPlayer(sender));
            }

            if (getStatus().equals(RequestStatus.CANCELED)) {
                RequestsHandler.requests.remove(this);
                Bukkit.getScheduler().cancelTask(taskId);
            } else if (getStatus().equals(RequestStatus.EXPIRED)) {
                RequestsHandler.requests.remove(this);
                Bukkit.getScheduler().cancelTask(taskId);
            } else if (getStatus().equals(RequestStatus.DENIED)) {
                RequestsHandler.requests.remove(this);
            } else if (getStatus().equals(RequestStatus.ACCEPTED)) {
                teleport();
                RequestsHandler.requests.remove(this);
                Bukkit.getScheduler().cancelTask(taskId);
            } else if (getStatus().equals(RequestStatus.PENDING)) {
                counter--;

                if (counter < 1) {
                    setStatus(RequestStatus.EXPIRED);
                    Vars.sendString(Vars.setPlaceholders(Vars.request_to_player_expired, sender, recipient), Bukkit.getPlayer(sender));
                    Vars.sendString(Vars.setPlaceholders(Vars.request_from_player_expired, sender, recipient), Bukkit.getPlayer(recipient));
                }
            } else {
                Vars.plugin.getLogger().severe("An exception occurred while retrieving a request status.. request deleted");
                RequestsHandler.requests.remove(this);
                cancel();
            }
        }, 0L, 20L).getTaskId();
    }

    public void cancel() {
        setStatus(RequestStatus.CANCELED);
    }

    public void accept() {
        setStatus(RequestStatus.ACCEPTED);
    }

    public void deny() {
        setStatus(RequestStatus.DENIED);
    }

    public void teleport() {
        try {
            if (cooldown_counter > 0) {
                Location initial_location = Bukkit.getPlayer(sender).getLocation();
                if (request_type.equals(RequestType.TPA)) {
                    Vars.sendString(Vars.setPlaceholders(Vars.dont_move.replaceAll("%cooldown_seconds%", String.valueOf(cooldown_counter)), sender, recipient), Bukkit.getPlayer(sender));
                }
                if (request_type.equals(RequestType.TPAHERE)) {
                    Vars.sendString(Vars.setPlaceholders(Vars.dont_move.replaceAll("%cooldown_seconds%", String.valueOf(cooldown_counter)), sender, recipient), Bukkit.getPlayer(recipient));
                    initial_location = Bukkit.getPlayer(recipient).getLocation();
                }
                Location finalInitial_location = initial_location;

                cooldown_counter_taskId = Bukkit.getScheduler().runTaskTimer(Vars.plugin, () -> {
                    if (!getStatus().equals(RequestStatus.CANCELED)) {
                        Player to_teleport_player = Bukkit.getPlayer(sender);
                        if (request_type.equals(RequestType.TPAHERE)) {
                            to_teleport_player = Bukkit.getPlayer(recipient);
                        }
                        if (!to_teleport_player.getLocation().equals(finalInitial_location)) {
                            setStatus(RequestStatus.CANCELED);

                            if (request_type.equals(RequestType.TPA)) {
                                Vars.sendString(Vars.setPlaceholders(Vars.you_moved, sender, recipient), Bukkit.getPlayer(sender));
                                Vars.sendString(Vars.setPlaceholders(Vars.player_moved.replaceAll("%player_name%", Bukkit.getPlayer(sender).getName()).replaceAll("%player_displayname%", Bukkit.getPlayer(sender).getDisplayName()), sender, recipient), Bukkit.getPlayer(recipient));
                            } else if (request_type.equals(RequestType.TPAHERE)) {
                                Vars.sendString(Vars.setPlaceholders(Vars.player_moved.replaceAll("%player_name%", Bukkit.getPlayer(recipient).getName()).replaceAll("%player_displayname%", Bukkit.getPlayer(recipient).getDisplayName()), sender, recipient), Bukkit.getPlayer(sender));
                                Vars.sendString(Vars.setPlaceholders(Vars.you_moved, sender, recipient), Bukkit.getPlayer(recipient));
                            }

                            Vars.playSound("player_moved", Bukkit.getPlayer(sender));
                            Vars.playSound("player_moved", Bukkit.getPlayer(recipient));
                        }

                        cooldown_counter_ticks--;
                        if (cooldown_counter_ticks < 1) {
                            cooldown_counter_ticks = 20;
                            cooldown_counter--;
                        }

                        if (cooldown_counter < 1) {
                            if (request_type.equals(RequestType.TPA)) {
                                Bukkit.getPlayer(sender).teleport(Bukkit.getPlayer(recipient));
                            } else if (request_type.equals(RequestType.TPAHERE)) {
                                Bukkit.getPlayer(recipient).teleport(Bukkit.getPlayer(sender));
                            }

                            Bukkit.getScheduler().cancelTask(cooldown_counter_taskId);
                        }
                    } else {
                        Bukkit.getScheduler().cancelTask(cooldown_counter_taskId);
                    }
                }, 0L, 0L).getTaskId();
            } else {
                if (request_type.equals(RequestType.TPA)) {
                    Bukkit.getPlayer(sender).teleport(Bukkit.getPlayer(recipient));
                } else if (request_type.equals(RequestType.TPAHERE)) {
                    Bukkit.getPlayer(recipient).teleport(Bukkit.getPlayer(sender));
                }
            }
        } catch (Exception ex) {
            Vars.plugin.getLogger().severe("Couldn't complete a teleport for a request from " + Bukkit.getPlayer(sender).getName() + " to " + Bukkit.getPlayer(recipient).getName() + ", type: " + request_type.toString());
            ex.printStackTrace();
        }
    }

    public Integer getCounter() {
        return counter;
    }

    public RequestStatus getStatus() {
        return this.status;
    }

    protected void setStatus(RequestStatus new_status) {
        this.status = new_status;
    }

}
