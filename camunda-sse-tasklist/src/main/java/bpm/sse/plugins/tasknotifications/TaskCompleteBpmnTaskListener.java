package bpm.sse.plugins.tasknotifications;

import java.util.HashMap;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import bpm.sse.rest.SseTasklistNotifier;

public class TaskCompleteBpmnTaskListener implements TaskListener {

    private SseTasklistNotifier tasklistNotifier;

    public TaskCompleteBpmnTaskListener(SseTasklistNotifier tasklistNotifier) {
        super();
        this.tasklistNotifier = tasklistNotifier;
    }

    @Override
    public void notify(DelegateTask delegateTask) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("task", delegateTask.getId());
        hashMap.put("type", "cleartask");
        hashMap.put("user", delegateTask.getAssignee());
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
        try {
            json = objectMapper.writeValueAsString(hashMap);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        tasklistNotifier.notifyClient(delegateTask.getAssignee(), json);
    }

}