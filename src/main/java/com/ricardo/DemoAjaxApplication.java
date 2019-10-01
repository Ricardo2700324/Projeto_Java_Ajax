package com.ricardo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ricardo.demoajax.domain.SocialMetaTag;
import com.ricardo.demoajax.service.SocialMetaTagService;

@SpringBootApplication
public class DemoAjaxApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(DemoAjaxApplication.class, args);
	}

	@Autowired
	SocialMetaTagService service;
	
	@Override
	public void run(String... args) throws Exception {
		
		SocialMetaTag og = service.getOpenGraphByUrl("https://www.pichau.com.br/placa-de-video-sapphire-radeon-rx-580-4gb-gddr5-nitro-256-bit-11265-07-20g");
		System.out.println(og.toString());
		
		SocialMetaTag twitter = service.getTwitterCardByUrl("https://www.pichau.com.br/placa-de-video-sapphire-radeon-rx-580-4gb-gddr5-nitro-256-bit-11265-07-20g");
		System.out.println(twitter.toString());
		
	}

}
