package ru.durnov.taco.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import lombok.extern.slf4j.Slf4j;

import ru.durnov.taco.Ingredient;
import ru.durnov.taco.Ingredient.Type;
import ru.durnov.taco.Order;
import ru.durnov.taco.Taco;
import ru.durnov.taco.data.IngredientRepository;
import ru.durnov.taco.data.TacoRepository;
import lombok.Data;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {
	private final IngredientRepository ingredientRepo;
	private TacoRepository designRepo;

	@Autowired
	public DesignTacoController(IngredientRepository ingredientRepo, TacoRepository designRepo) {
		this.ingredientRepo = ingredientRepo;
		this.designRepo = designRepo;
	}

	@GetMapping
	public String showDesignForm(Model model) {
		List<Ingredient> ingredients = new ArrayList<>();
		ingredientRepo.findAll().forEach(i-> ingredients.add(i));
		Type[] types = Ingredient.Type.values();
		for(Type type:types) {
			model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
		}
		Taco currentTaco = new Taco();
		log.info(currentTaco.toString());
		model.addAttribute("design", currentTaco);
		log.info(model.toString());
		return "design";
	}

	private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {

		return ingredients.stream()
				.filter(x -> x.getType().equals(type))
				.collect(Collectors.toList());

	}

	@ModelAttribute(name = "order")
	public Order order() {
		return new Order();
	}

	@ModelAttribute(name = "design")
	public Taco taco() {
		return new Taco();
	}

	@PostMapping
	public String processDesign( @ModelAttribute Taco design, Errors errors, @ModelAttribute Order order) {
		if (errors.hasErrors()) {
			log.info(errors.toString());
			return "design";
		}
		Taco saved = designRepo.save(design);
		order.addDesign(saved);
		return "redirect:/orders/current";
	}

}
