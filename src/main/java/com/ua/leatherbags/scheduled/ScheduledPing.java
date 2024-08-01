package com.ua.leatherbags.scheduled;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class ScheduledPing {
//	@Scheduled(fixedDelay = 60000)
//	public void ping() {
//		try {
//			RestTemplate restTemplate = new RestTemplate();
//			restTemplate.getForEntity("https://leatherbags.onrender.com/api/ping", String.class);
//		} catch (Exception ignored) {
//		}
//	}
}
