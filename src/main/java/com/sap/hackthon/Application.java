package com.sap.hackthon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;

import com.sap.hackthon.config.MvcConfig;

@SpringBootApplication
@EnableAutoConfiguration
@PropertySources({
	@PropertySource("classpath:application.properties"),
	@PropertySource("classpath:application_${app.env}.properties")
})
public class Application {

	public static void main(String[] args) {
		/**
		 * by default, we adopt local configuration
		 * in PROD env, we will setup app.env with -D option while running the Application.
		 */
		String env = System.getProperty("app.env");
		if(env == null || env.equals("")) {
			System.setProperty("app.env", "local");
		}
		
		SpringApplication.run(Application.class, args);			
	}

}