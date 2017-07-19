package bpm.sse.plugins.tasknotifications;

import java.util.HashMap;
import java.util.Map;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import bpm.sse.rest.SseTasklistNotifier;

public class TaskCreateBpmnTaskListener implements TaskListener {

    private SseTasklistNotifier tasklistNotifier;

    public TaskCreateBpmnTaskListener(SseTasklistNotifier tasklistNotifier) {
        super();
        this.tasklistNotifier = tasklistNotifier;
    }

    @Override
    public void notify(DelegateTask delegateTask) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("user", delegateTask.getAssignee());
        map.put("taskId", delegateTask.getId());
        map.put("type", "newtask");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
        try {
            json = objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        tasklistNotifier.notifyClient(delegateTask.getAssignee(), json);
    }

}