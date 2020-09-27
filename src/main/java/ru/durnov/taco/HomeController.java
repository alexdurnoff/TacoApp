package ru.durnov.taco;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.client.Traverson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import ru.durnov.taco.data.IngredientRepository;
import ru.durnov.taco.data.TacoRepository;

import java.lang.reflect.Type;
import java.net.URI;
import java.util.ArrayList;

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

	//Метод создан для проверки Traverse. Работает.
	//Как я понял, параметризированная ссылка преобразуется в CollectionModel
	//Эта ссылка в качестве параметра имеет тип объекта, в который нужно преобразовать ресурс
	//Для того, чтобы изобразить RESTController, влепил анноацию ResponseBody
	//Все эксперименты буду сувать теперь сюда через responsebody
	@GetMapping("/getingredients")
	@ResponseBody
	public CollectionModel<Ingredient> getIngredientsCollection() {
		Traverson traverson = new Traverson(URI.create("http://localhost:8080/api"), MediaTypes.HAL_JSON);
		ParameterizedTypeReference<CollectionModel<Ingredient>> ingredientType =
				new ParameterizedTypeReference<CollectionModel<Ingredient>>() {
					@Override
					public Type getType() {
						return super.getType();
					}
				};
		CollectionModel<Ingredient> ingredients = traverson.follow("ingredients").toObject(ingredientType);
		return ingredients;
	}

	//Метод ручного добавления тако
	//Еще выяснил, что если стоит аннотация ResponseBody, то надо что-то возвращать. Void не подходит. Ругается на ошибку thymeleaef, или типа того...
	@GetMapping("/addtaco")
	@ResponseBody
	public Taco addTaco(){
		Taco taco = new Taco();
		taco.setName("Durnovs_Forever!!!");
		ArrayList<Ingredient> ingredients = new ArrayList<>();
		for (Ingredient ingredient:ingredientRepo.findAll()){
			ingredients.add(ingredient);
		}
		taco.setIngredients(ingredients);
		tacoRepo.save(taco);
		return taco;
	}

	@GetMapping(path = "/getrecents")
	@ResponseBody
	public ArrayList<Taco> getRecentTacos(){
		Traverson traverson = new Traverson(URI.create("http://localhost:8080/api"), MediaTypes.HAL_JSON);
		ParameterizedTypeReference<CollectionModel<Taco>> tacoType = new ParameterizedTypeReference<CollectionModel<Taco>>() {};
		CollectionModel<Taco> tacos = traverson.follow("tacos").follow("recents").toObject(tacoType);
		return (ArrayList<Taco>) tacos.getContent();
	}

	@GetMapping("/design/postnewtaco")
	@ResponseBody
	public Taco postNewTaco(){
		RestTemplate restTemplate = new RestTemplate();
		Traverson traverson = new Traverson(URI.create("http://localhost:8080/api"), MediaTypes.HAL_JSON);
		String tacoUrl = traverson.follow("tacos").asLink().getHref();
		Taco taco = new Taco();
		taco.setName("Durnovs_Forever!!!");
		ArrayList<Ingredient> ingredients = new ArrayList<>();
		for (Ingredient ingredient:ingredientRepo.findAll()){
			ingredients.add(ingredient);
		}
		taco.setIngredients(ingredients);
		return restTemplate.postForObject(tacoUrl, taco, Taco.class);
	}

}
