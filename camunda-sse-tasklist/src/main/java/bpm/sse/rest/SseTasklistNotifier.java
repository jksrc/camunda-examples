package bpm.sse.rest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.OutboundEvent;

public class SseTasklistNotifier {

    private Map<String, EventOutput> sequenceMap = new HashMap<>();

    public void notifyClient(String id, Object message) {
        try {
            getSequence(id).write(new OutboundEvent.Builder().name("message").data(String.class, message).build());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public EventOutput getSequence(String id) {
        EventOutput sequence;
        if (sequenceMap.containsKey(id)) {
            sequence = sequenceMap.get(id);
        } else {
            sequence = new EventOutput();
            sequenceMap.put(id, sequence);
        }
        return sequence;
    }

}
