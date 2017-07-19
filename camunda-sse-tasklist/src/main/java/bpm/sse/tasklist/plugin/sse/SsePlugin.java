package bpm.sse.tasklist.plugin.sse;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.camunda.bpm.tasklist.plugin.spi.impl.AbstractTasklistPlugin;
import org.camunda.bpm.tasklist.resource.AbstractTasklistPluginRootResource;

public class SsePlugin extends AbstractTasklistPlugin {

    public static final String ID = "sse-plugin";

    public String getId() {
        return ID;
    }

    @Override
    public Set<Class<?>> getResourceClasses() {
        Set<Class<?>> classes = new HashSet<Class<?>>();

        classes.add(SseRestResource.class);

        return classes;

    }

    @Path("plugin/" + SsePlugin.ID)
    static class SseRestResource extends AbstractTasklistPluginRootResource {

        public SseRestResource() {
            super(SsePlugin.ID);
        }

        @GET
        @Produces(MediaType.APPLICATION_JSON)
        public String getProcessInstanceCounts() {
            return "hello world";
        }
    }

}
