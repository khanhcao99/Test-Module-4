package com.example.module4;

import com.example.module4.service.impl.FilesSVStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.Date;


@SpringBootApplication
public class Module4Application {
	public static void main(String[] args) {
		SpringApplication.run(Module4Application.class, args);
	}
}
