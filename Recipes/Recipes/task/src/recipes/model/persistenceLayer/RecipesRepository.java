package recipes.model.persistenceLayer;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import recipes.model.businessLayer.recipe.Recipe;

import java.util.List;


@Repository
public interface RecipesRepository extends CrudRepository<Recipe, Long> {
    List<Recipe> findByNameContainingIgnoreCaseOrderByLastUpdatedDesc(String name);
    List<Recipe> findByCategoryIgnoreCaseOrderByLastUpdatedDesc(String category);
}
