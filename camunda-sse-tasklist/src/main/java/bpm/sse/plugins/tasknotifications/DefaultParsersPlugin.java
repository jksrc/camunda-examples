package bpm.sse.plugins.tasknotifications;

import java.util.ArrayList;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.impl.bpmn.parser.BpmnParseListener;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.cfg.ProcessEnginePlugin;
import org.springframework.beans.factory.annotation.Autowired;

import bpm.sse.rest.SseTasklistNotifier;

public class DefaultParsersPlugin implements ProcessEnginePlugin {

    private ProcessEngine initBean;

    SseTasklistNotifier tasklistNotifier;

    @Autowired
    public DefaultParsersPlugin(ProcessEngine initBean, SseTasklistNotifier tasklistNotifier) {
        this.tasklistNotifier = tasklistNotifier;
    }

    @Override
    public void preInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
        if (processEngineConfiguration.getCustomPostBPMNParseListeners() == null) {
            processEngineConfiguration.setCustomPostBPMNParseListeners(new ArrayList<BpmnParseListener>());
        }

        processEngineConfiguration.getCustomPostBPMNParseListeners()
                .add(new TaskNotificationBpmnParseListener(tasklistNotifier));

    }

    @Override
    public void postProcessEngineBuild(ProcessEngine processEngine) {
    }

    @Override
    public void postInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
    }

}