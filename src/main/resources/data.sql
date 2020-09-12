delete from Taco_Order_Tacos;
delete from Taco_Ingredients;
delete from Taco;
delete from Taco_Order;
delete from Ingredient;

INSERT INTO Ingredient (id, name, type) values ('FLTO', 'Flour Tortilla', 'WRAP' );
INSERT INTO Ingredient (id, name, type) values ('COTO', 'Corn Tortilla', 'WRAP');
INSERT INTO Ingredient (id, name, type) values ('GRBF', 'Ground Beef', 'PROTEIN');
INSERT INTO Ingredient (id, name, type) values ('CARN', 'Carnitas', 'PROTEIN');
INSERT INTO Ingredient (id, name, type) values ('TMTO', 'Diced Tomatoes', 'VEGGIES');
INSERT INTO Ingredient (id, name, type) values ('LETC', 'Lettuce', 'VEGGIES');
INSERT INTO Ingredient (id, name, type) values ('CHED', 'Cheddar', 'CHEESE');
INSERT INTO Ingredient (id, name, type) values ('JACK', 'Monterrey Jack', 'CHEESE');
INSERT INTO Ingredient (id, name, type) values ('SLSA', 'Salsa', 'SAUCE');
INSERT INTO Ingredient (id, name, type) values ('SRCR', 'Sour Cream', 'SAUCE');
