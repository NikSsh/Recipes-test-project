package recipes.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import recipes.model.businessLayer.recipe.Recipe;
import recipes.model.businessLayer.recipe.RecipeServiceImpl;
import recipes.model.businessLayer.recipe.RecipeService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Validated
@RestController
@RequestMapping("/api")
public class ApiController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiController.class);
    private final RecipeService recipeService;

    @Autowired
    public ApiController(RecipeServiceImpl recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping("/recipe/new")
    @ResponseBody
    public ResponseEntity<Map<String, ?>> addRecipe(HttpServletRequest request, @Valid @RequestBody Recipe newRecipe) {
        Principal principal = request.getUserPrincipal();
        LOGGER.debug( "DATE_TIME = {}; METHOD = {}; URI = {}; USER_ID = {}",
                LocalDateTime.now(), request.getMethod(), request.getRequestURI(), principal.getName());
        long id = recipeService.saveRecipe(principal.getName(), newRecipe);

        LOGGER.debug( "DATE_TIME = {}; USER_ID = {} has successfully saved recipe {}",
                LocalDateTime.now(), principal.getName(), id);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "application/json");

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(Map.of("id", id));
    }

    @GetMapping("/recipe/{id}")
    public ResponseEntity<Recipe> getRecipe(HttpServletRequest request, @PathVariable long id) {
        Recipe recipe = recipeService.getRecipe(id);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json");

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(recipe);
    }

    @DeleteMapping("/recipe/{id}")
    public ResponseEntity<?> deleteRecipe(HttpServletRequest request, @PathVariable long id) {
        Principal principal = request.getUserPrincipal();

        LOGGER.debug( "DATE_TIME = {}; METHOD = {}; URI = {}; USER_ID = {}",
                LocalDateTime.now(), request.getMethod(), request.getRequestURI(), principal.getName());

        recipeService.deleteRecipe(principal.getName(), id);

        LOGGER.debug( "DATE_TIME = {}; USER_ID = {} has successfully deleted recipe {}",
                LocalDateTime.now(), principal.getName(), id);

        return ResponseEntity
                .noContent()
                .build();
    }

    @PutMapping("/recipe/{id}")
    public ResponseEntity<?> updateRecipe(HttpServletRequest request, @PathVariable long id, @Valid @RequestBody Recipe recipe) {
        Principal principal = request.getUserPrincipal();

        LOGGER.debug( "DATE_TIME = {}; METHOD = {}; URI = {}; USER_ID = {}", LocalDateTime.now(), request.getMethod(), request.getRequestURI(), principal.getName());

        recipeService.updateRecipe(principal.getName(), id, recipe);

        LOGGER.debug( "DATE_TIME = {}; USER_ID = {} has successfully updated recipe {}",
                LocalDateTime.now(), principal.getName(), id);

        return ResponseEntity
                .noContent()
                .build();
    }

    @GetMapping("/recipe/search")
    public ResponseEntity<List<Recipe>> searchRecipes(@RequestParam(required = false) Optional<String> name, @RequestParam(required = false) Optional<String> category) {
        List<Recipe> recipesList;
        if (name.isPresent() && category.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } else if (name.isPresent()) {
            recipesList = recipeService.getRecipesByName(name.get());
        } else if (category.isPresent()) {
            recipesList = recipeService.getRecipesByCategory(category.get());
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json");

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(recipesList);
    }
}
