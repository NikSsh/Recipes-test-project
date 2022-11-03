package recipes.model.businessLayer.recipe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import recipes.model.businessLayer.user.User;
import recipes.model.persistenceLayer.RecipesRepository;
import recipes.model.persistenceLayer.UsersRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the RecipeService interface,
 * It is responsible for processing of actions related to the recipes
 */
@Service
public class RecipeServiceImpl implements RecipeService {
    private final RecipesRepository recipesRepository;

    /**
     * Used to retrieve user data from the username.
     */
    private final UsersRepository usersRepository;

    @Autowired
    public RecipeServiceImpl(UsersRepository usersRepository, RecipesRepository recipesRepository) {
        this.recipesRepository = recipesRepository;
        this.usersRepository = usersRepository;
    }

    /**
     * @param username - string representation of login that is used to check
     * if there are appropriate rights to perform the corresponding action
     * @param recipe - object of the Recipe class on which we perform some actions in DB
     * @return long type, id value of the saved recipe in DB
     * @throws UsernameNotFoundException with parameter String msg
     */
    public long saveRecipe(String username, Recipe recipe) {
        User user = usersRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException("Username " + username));
        recipe.setUser(user);
        recipesRepository.save(recipe);
        return recipesRepository.count();
    }

    /**
     * @param username - string representation of login that is used to check
     * if there are appropriate rights to perform the corresponding action
     * @param id - long type value that represents id of the recipe that we want to update
     * @param recipe - object of the Recipe class on which we perform some actions in DB
     * @throws ResponseStatusException with parameter HttpStatus.NOT_FOUND if there is no recipe in DB with such id
     * or HttpStatus.FORBIDDEN if the user doesn't have appropriate rights
     */
    public void updateRecipe(String username, long id, Recipe recipe) {
        User user = usersRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException("Username " + username));
        Recipe recipeToUpdate = recipesRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!recipeToUpdate.getUser().equals(user)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        recipe.setId(id);
        recipe.setUser(recipeToUpdate.getUser());
        recipesRepository.save(recipe);
    }

    /**
     * @param username - string representation of login that is used to check
     * if there are appropriate rights to perform the corresponding action
     * @param id - long type value that represents id of the recipe that we want to delete
     * @throws ResponseStatusException with parameter HttpStatus.NOT_FOUND if there is no recipe in DB with such id
     * or HttpStatus.FORBIDDEN if the user doesn't have appropriate rights
     */
    public void deleteRecipe(String username, long id) {
        User user = usersRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException("Username " + username));
        Recipe recipeToUpdate = recipesRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    

        if (!recipeToUpdate.getUser().equals(user)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        recipesRepository.deleteById(id);
    }

    /**
     * @param id - long type value that represents id of the recipe that we want to get
     * @return object of the Recipe class
     * @throws ResponseStatusException with parameter HttpStatus.NOT_FOUND if there is no recipe in DB with such id
     */
    public Recipe getRecipe(long id) {
        return recipesRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    /**
     * @return a list of objects of Recipe type containing all recipes from DB
     */
    public List<Recipe> getAllRecipes() {
        List<Recipe> recipeAll = new ArrayList<>();
        recipesRepository.findAll().forEach(recipeAll::add)
        return recipeAll;
    }

    /**
     * @param category String name of the category that we are going to use to retrieve all corresponding recipes
     * @return a list of objects of Recipe type containing all recipes from DB that have corresponding category
     * sorted in descending order by time last updated
     */
    public List<Recipe> getRecipesByCategory(String category) {
        return new ArrayList<>(recipesRepository.findByCategoryIgnoreCaseOrderByLastUpdatedDesc(category));
    }

    /**
     * @param name String that we are going to search for
     * @return a list of objects of Recipe type containing all recipes from DB that contains providen name, ignoring case,
     * sorted in descending order by time last updated
     */
    public List<Recipe> getRecipesByName(String name) {
        return new ArrayList<>(recipesRepository.findByNameContainingIgnoreCaseOrderByLastUpdatedDesc(name));
    }
}
