package com.mainapplication.spring_boot.zeebe_module.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.zeebe.client.api.response.WorkflowInstanceEvent;
import io.zeebe.spring.client.EnableZeebeClient;
import io.zeebe.spring.client.ZeebeClientLifecycle;
import io.zeebe.spring.client.annotation.ZeebeDeployment;
import lombok.extern.slf4j.Slf4j;

@EnableZeebeClient
@ZeebeDeployment(classPathResources = "demoProcess.bpmn")
@Slf4j
@Controller
@RequestMapping(path = "/zeebe")
public class ZeebeController {
    @Autowired
	private ZeebeClientLifecycle client;

    @GetMapping("/startDemoProcess")
	public void startDemoProcess() {
		if (!client.isRunning()) {
		return;
		}

		final WorkflowInstanceEvent event =
		client
			.newCreateInstanceCommand()
			.bpmnProcessId("demoProcess")
			.latestVersion()
			.variables("{\"a\": \"" + UUID.randomUUID().toString() + "\"}")
			.send()
			.join();

		log.info("started instance for workflowKey='{}', bpmnProcessId='{}', version='{}' with workflowInstanceKey='{}'",
		event.getWorkflowKey(), event.getBpmnProcessId(), event.getVersion(), event.getWorkflowInstanceKey());
	}
}