package br.com.realtime.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import br.com.realtime.model.Greeting;

@Service
public class RealtimeService {

	@Autowired
	private SimpMessagingTemplate template;

	// this will send a message to an endpoint on which a client can subscribe
	@Scheduled(fixedRate = 5000)
	public void trigger() {
		Greeting content = new Greeting("Real time!");
//		String content = "Date: Realtime";
		System.out.println(content);
		this.template.convertAndSend("/topic/greetings", content);
	}
}
