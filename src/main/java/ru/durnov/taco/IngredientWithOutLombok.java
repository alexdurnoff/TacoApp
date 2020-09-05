package ru.durnov.taco;

public class IngredientWithOutLombok {
	private final String name;
	private final String id;
	private final Type type;
	
	public static enum Type {
		WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
	}
	
	public IngredientWithOutLombok(String id, String name, String typeName) {
		this.name = name;
		this.id = id;
		this.type = Type.valueOf(typeName);

	}
	public String getName() {
		return name;
	}
	public String getId() {
		return id;
	}
	public Type getType() {
		return type;
	}

}
