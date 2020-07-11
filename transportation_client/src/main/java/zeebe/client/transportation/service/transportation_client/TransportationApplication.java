package zeebe.client.transportation.service.transportation_client;

import java.time.Instant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import io.zeebe.client.api.response.ActivatedJob;
import io.zeebe.client.api.worker.JobClient;
import io.zeebe.spring.client.EnableZeebeClient;
import io.zeebe.spring.client.annotation.ZeebeWorker;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableZeebeClient
@EnableDiscoveryClient
@Slf4j
public class TransportationApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransportationApplication.class, args);
	}

	private static void logJob(final ActivatedJob job) {
		log.info(
		  "complete job\n>>> [type: {}, key: {}, element: {}, workflow instance: {}]\n{deadline; {}]\n[headers: {}]\n[variables: {}]",
		  job.getType(),
		  job.getKey(),
		  job.getElementId(),
		  job.getWorkflowInstanceKey(),
		  Instant.ofEpochMilli(job.getDeadline()),
		  job.getCustomHeaders(),
		  job.getVariables());
	  }
	
	@ZeebeWorker(type = "transportation_foo")
		public void handleFooJob(final JobClient client, final ActivatedJob job) {
		logJob(job);
		client.newCompleteCommand(job.getKey()).variables("{\"foo\": 1}").send().join();
	}

	@ZeebeWorker(type = "transportation_bar")
		public void handleBarJob(final JobClient client, final ActivatedJob job) {
		logJob(job);
		client.newCompleteCommand(job.getKey()).send().join();
	}

}
