package com.ricardo.demoajax.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ricardo.demoajax.domain.SocialMetaTag;
import com.ricardo.demoajax.service.SocialMetaTagService;

@Controller
@RequestMapping("/meta")
public class SocialMetaTagController {

	@Autowired
	private SocialMetaTagService service;
	
	@PostMapping("/info")
	public ResponseEntity<SocialMetaTag> getDadoViaUrl(@RequestParam("url") String url){
		
		SocialMetaTag socialMetaTag = service.getSocialMetaTagByUrl(url);
		return socialMetaTag != null 
				? ResponseEntity.ok(socialMetaTag)
				: ResponseEntity.notFound().build();		
	}
}
