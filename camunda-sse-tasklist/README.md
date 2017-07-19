# about
this examples shows how to use Server Sent Events (SSE) to update the camunda tasklist immediately. E.g. when the task was completed or canceled in the background

# run
- run bpm.sse.SseApplication
- open http://localhost:8080/app and got to tasklist
- login with admin/password
- start a new process (SSE Process)
- see how the focus goes directly to the first task. You can also check the browsers log console.
   You can also cancel the process via cockpit and see how the tasklist updates accordingly

# note
this works only with browser supporting SSE (all should do, except IE)


# How does it work
Process engine:
- In the package bpm.sse.plugins.tasknotifications, there is a process-engine plug-in that listens for task changes (create, complete and delete) and forwards them to the SseTasklistNotifier
- The package bpm.sse.rest contains this notifier and also the REST resource for providing SSE (ServerSentEventsResource)
- And after program start, the process 'sse.bpmn' is deployed (auto-deploy didn't work somehow)

Tasklist:
- Implement a plug-in with bpm.sse.tasklist.plugin.sse.SsePlugin
- Register this plug-in using that file:  src/main/resources/META-INF/services/org.camunda.bpm.tasklist.plugin.spi.TasklistPlugin
- The Folder /camunda-sse-tasklist/src/main/resources/plugin-webapp contains the plugins web content (especially SSE handling)