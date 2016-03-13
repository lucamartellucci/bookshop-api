package com.absontheweb.bookshop.application;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
	
	public static void main(String[] args) {
	    SpringApplication app = new SpringApplication(Application.class);
	    app.setBannerMode(Banner.Mode.LOG);
	    app.run(args);
    }

}
