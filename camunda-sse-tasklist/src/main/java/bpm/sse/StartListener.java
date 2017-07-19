package bpm.sse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.camunda.bpm.engine.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public class StartListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private RepositoryService repositoryService;

    public void onApplicationEvent(ContextRefreshedEvent event) {
        File file = new File(getClass().getClassLoader().getResource("sse.bpmn").getFile());

        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(file);
            repositoryService.createDeployment().addInputStream(file.getName(), fileInputStream).deploy();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}