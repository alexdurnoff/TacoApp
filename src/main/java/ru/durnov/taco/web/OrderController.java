package ru.durnov.taco.web;

import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import ru.durnov.taco.Order;
import ru.durnov.taco.User;
import ru.durnov.taco.data.OrderRepository;

import java.security.Principal;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
public class OrderController {
	private OrderRepository orderRepo;
	
	 public OrderController(OrderRepository orderRepo) {
		   this.orderRepo = orderRepo;
		 }
	
	@GetMapping("/current")
	public String orderForm() {
		return "orderForm";
	}


	
	@PostMapping
	public String processOrder(@Valid Order order, Errors errors, SessionStatus sessionStatus,
							   @AuthenticationPrincipal User user) {
		if (errors.hasErrors()) {
			return "orderForm";
		}
		order.setUser(user);
		orderRepo.save(order);
		sessionStatus.setComplete();
		return "redirect:/";
	}

	/*@PostMapping
	public String processOrder(@Valid Order order, Errors errors, SessionStatus sessionStatus,
							   Authentication authentication){
		if (errors.hasErrors()) {
			return "orderForm";
		}
		User user = (User) authentication.getPrincipal();
		log.info(user.getAuthorities().toString());
		order.setUser(user);
		sessionStatus.setComplete();
		return "redirect:/";
	}*/

}
