# Recipes-test-project

## Description
It is a restful spring boot project, that depends on such spring-boot starters: jpa, security, validation, actuator.
A service supports registration via Basic Auth, storing, retrieving, updating, and deleting recipes in json format.

## Instruction
Path to main class - ".\Recipes\task\src\recipes\RecipesApplication.class"

Endpoints: 
*POST /api/register* - allows to register with following json document format: 

{ 
   "email": "youremail@gmail.com",
   "password": "yourpassword"
}

*POST /api/recipe/new* - request with basic authentication to add new recipe with following json format: 

{
   "name": "Mint Tea",
   "category": "beverage",
   "description": "Light, aromatic and refreshing beverage, ...",
   "ingredients": ["boiled water", "honey", "fresh mint leaves"],
   "directions": ["Boil water", "Pour boiling hot water into a mug", "Add fresh mint leaves", "Mix and let the mint leaves seep for 3-5 minutes", "Add honey and mix         again"]
}

response includes id of the recipe in format : {"id" : 1}

*PUT /api/recipe/{id}* - request with basic authentication to update recipe with corresponding id,
it has the same json format as "/api/recipe/new" endpoint

*GET /api/recipe/1* - request with basic authentication to get recipe with corresponding id,
response includes the same json document as you have sent when you were adding it

*DELETE /api/recipe/1* -  request with basic authentication to delete recipe with corresponding id

*GET /recipe/search?name=value* or *GET /recipe/search?category=value* - allows you to get recipes that contains providen name or category
