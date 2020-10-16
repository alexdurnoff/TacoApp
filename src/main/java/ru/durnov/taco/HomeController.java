package ru.durnov.taco;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.durnov.taco.data.IngredientRepository;
import ru.durnov.taco.data.TacoRepository;

@Controller
public class HomeController {
	private IngredientRepository ingredientRepo;
	private TacoRepository tacoRepo;

	public HomeController(TacoRepository tacoRepo, IngredientRepository ingredientRepo){
		this.tacoRepo = tacoRepo;
		this.ingredientRepo = ingredientRepo;
	}
	
	@GetMapping("/")
	public String home() {
		return "home";
	}
}
