package br.com.realtime.service;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import br.com.realtime.model.Greeting;

@Service
public class RealtimeService {

	@Autowired
	private SimpMessagingTemplate template;
	
	private static final OperatingSystemMXBean osMBean;

	static {
		try {
			osMBean = ManagementFactory.newPlatformMXBeanProxy(ManagementFactory.getPlatformMBeanServer(),
					ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME, OperatingSystemMXBean.class);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	// this will send a message to an endpoint on which a client can subscribe
	@Scheduled(fixedRate = 5000)
	public void trigger() {
		Greeting content = new Greeting(String.valueOf(osMBean.getSystemLoadAverage()));
		this.template.convertAndSend("/topic/greetings", content);
	}
}
