package bpm.sse.plugins.tasknotifications;

import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.camunda.bpm.engine.impl.bpmn.parser.AbstractBpmnParseListener;
import org.camunda.bpm.engine.impl.pvm.delegate.ActivityBehavior;
import org.camunda.bpm.engine.impl.pvm.process.ActivityImpl;
import org.camunda.bpm.engine.impl.pvm.process.ScopeImpl;
import org.camunda.bpm.engine.impl.util.xml.Element;

import bpm.sse.rest.SseTasklistNotifier;

public class TaskNotificationBpmnParseListener extends AbstractBpmnParseListener {

    private SseTasklistNotifier tasklistNotifier;

    public TaskNotificationBpmnParseListener(SseTasklistNotifier tasklistNotifier) {
        super();
        this.tasklistNotifier = tasklistNotifier;
    }

    @Override
    public void parseUserTask(Element userTaskElement, ScopeImpl scope, ActivityImpl activity) {
        ActivityBehavior behavior = activity.getActivityBehavior();
        if (behavior instanceof UserTaskActivityBehavior) {
            TaskCompleteBpmnTaskListener taskListener = new TaskCompleteBpmnTaskListener(tasklistNotifier);
            ((UserTaskActivityBehavior) behavior).getTaskDefinition().addTaskListener(TaskListener.EVENTNAME_COMPLETE,
                    taskListener);
            ((UserTaskActivityBehavior) behavior).getTaskDefinition().addTaskListener(TaskListener.EVENTNAME_DELETE,
                    taskListener);
            ((UserTaskActivityBehavior) behavior).getTaskDefinition().addTaskListener(TaskListener.EVENTNAME_CREATE,
                    new TaskCreateBpmnTaskListener(tasklistNotifier));
        }
    }

}