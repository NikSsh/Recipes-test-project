package recipes.model.businessLayer.recipe;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.UpdateTimestamp;
import recipes.model.businessLayer.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Recipe class represents the model in database.
 * It has ManyToOne bidirectional relationship with the User table
 */
@Entity
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotBlank
    private String name, description, category;

    @UpdateTimestamp
    @JsonProperty("date")
    private LocalDateTime lastUpdated;

    @NotEmpty
    @ElementCollection
    @CollectionTable(name = "INGREDIENTS", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "ingredients")
    @JsonProperty("ingredients")
    private List<String> ingredientsList;

    @NotEmpty
    @ElementCollection
    @CollectionTable(name = "DIRECTIONS", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "ingredients")
    @JsonProperty("directions")
    private List<String> directionsList;

    public Recipe() {

    }

    public Recipe(String name, String description, List<String> ingredientsList, List<String> directionsList) {
        this.name = name;
        this.description = description;
        this.ingredientsList = ingredientsList;
        this.directionsList = directionsList;
    }

    @JsonIgnore
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<String> getIngredientsList() {
        return ingredientsList;
    }

    public void setIngredientsList(List<String> ingredientsList) {
        this.ingredientsList = ingredientsList;
    }

    public List<String> getDirectionsList() {
        return directionsList;
    }

    public void setDirectionsList(List<String> directionsList) {
        this.directionsList = directionsList;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    @JsonIgnore
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Recipe recipe = (Recipe) o;

        if (hashCode() != recipe.hashCode()) return false;

        if (id != recipe.id) return false;
        if (!user.equals(recipe.user)) return false;
        if (!name.equals(recipe.name)) return false;
        if (!description.equals(recipe.description)) return false;
        if (!category.equals(recipe.category)) return false;
        if (!lastUpdated.equals(recipe.lastUpdated)) return false;
        if (!ingredientsList.equals(recipe.ingredientsList)) return false;
        return directionsList.equals(recipe.directionsList);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + user.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + category.hashCode();
        result = 31 * result + lastUpdated.hashCode();
        result = 31 * result + ingredientsList.hashCode();
        result = 31 * result + directionsList.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", lastUpdated=" + lastUpdated +
                ", ingredientsList=" + ingredientsList +
                ", directionsList=" + directionsList +
                '}';
    }
}
