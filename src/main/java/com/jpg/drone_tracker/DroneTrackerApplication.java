package com.jpg.drone_tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DroneTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DroneTrackerApplication.class, args);
	}

}
