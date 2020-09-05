package ru.durnov.taco.web;

import java.util.ArrayList;

import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import lombok.Data;

import lombok.extern.slf4j.Slf4j;

import ru.durnov.taco.Order;

@Slf4j
@Controller
@RequestMapping("/orders")
public class OrderController {
	private ArrayList <Order> orders = new ArrayList<>();
	
	@GetMapping("/current")
	public String orderForm(Model model) {
		model.addAttribute("order", new Order());
		return "orderForm";
	}
	
	@GetMapping
	public String orderRoot(Model model) {
		model.addAttribute(orders);
		return "orders";
	}
	
	@PostMapping
	public String processOrder(@Valid Order order, Errors errors) {
		if (errors.hasErrors()) {
			log.info(errors.toString());
			return "orderForm";
		}
		log.info("Order submitted" + order);
		orders.add(order);
		return "redirect:/orders";
	}

}
