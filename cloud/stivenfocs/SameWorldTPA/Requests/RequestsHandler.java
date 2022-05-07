package cloud.stivenfocs.SameWorldTPA.Requests;

import cloud.stivenfocs.SameWorldTPA.Vars;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RequestsHandler {

    static List<Request> requests = new ArrayList<>();

    public static List<Request> getSentRequests(UUID pUID) {
        List<Request> sent_requests = new ArrayList<>();

        for (Request request : new ArrayList<>(requests)) {
            if (request.sender.equals(pUID)) {
                sent_requests.add(request);
            }
        }

        return sent_requests;
    }

    public static List<Request> getReceivedRequests(UUID pUID) {
        List<Request> received_requests = new ArrayList<>();

        for (Request request : new ArrayList<>(requests)) {
            if (request.recipient.equals(pUID)) {
                received_requests.add(request);
            }
        }

        return received_requests;
    }

    public static Request getRequest(UUID from, UUID to) {
        for (Request request : new ArrayList<>(requests)) {
            if (request.sender.equals(from)) {
                if (request.recipient.equals(to)) {
                    return request;
                }
            }
        }

        return null;
    }

    public static Request createRequest(UUID from, UUID to, RequestType request_type, Integer request_duration, Integer teleport_cooldown) {
        Request new_request = new Request(from, to, request_type, request_duration, teleport_cooldown);
        requests.add(new_request);
        return new_request;
    }

    public static List<Request> getAllRequests() {
        return new ArrayList<>(requests);
    }

    public static boolean doesPlayerAllowRequests(UUID pUID) {
        return !Vars.getVars().disallowed_requests.contains(pUID);
    }

    public static void setPlayerRequestsAllow(UUID pUID, Boolean allow) {
        if (allow) {
            Vars.getVars().disallowed_requests.remove(pUID);
        } else {
            if (!Vars.getVars().disallowed_requests.contains(pUID)) {
                Vars.getVars().disallowed_requests.add(pUID);
            }
        }
    }

}
