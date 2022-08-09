package recipes.model.businessLayer.recipe;

import recipes.model.businessLayer.recipe.Recipe;

import java.util.List;

/**
 * representation of all available methods that we need to implement
 */
public interface RecipeService {
    long saveRecipe(String username, Recipe recipe);
    void updateRecipe(String username, long id, Recipe recipe);
    void deleteRecipe(String username, long id);
    Recipe getRecipe(long id);
    List<Recipe> getAllRecipes();
    List<Recipe> getRecipesByCategory(String category);
    List<Recipe> getRecipesByName(String name);
}
