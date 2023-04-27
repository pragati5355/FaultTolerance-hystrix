package com.hystrix.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;


@RestController
@RequestMapping()
public class HystrixController {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@GetMapping("/home")
	public String home() {
		return "This is a Home page of Hystrix Server";
	}
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@GetMapping("/purchase")
	@HystrixCommand(fallbackMethod = "handleCartPurchase")
	public Map<String, String> cartPurchase(){
		
		Map<String, String>  value= new HashMap<>();
		
		String purchase = "This is a cart purchase page ";
		
		String product = restTemplate.getForObject("http://localhost:8082/product", String.class);
		String order = restTemplate.getForObject("http://localhost:8081/order", String.class);
		
		value.put("purchase", purchase);
		value.put("Order", order);
		value.put("Product", product);
		return value;
	}
	
	public String handleCartPurchase() {
		
		return "This is a cart purchase page ";
	}

}
