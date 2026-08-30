package com.eleven.track;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@MapperScan("com.eleven.track.mapper")
public class ElevenTrackApplication {
	public static void main(String[] args) {
		SpringApplication.run(ElevenTrackApplication.class, args);
	}
}