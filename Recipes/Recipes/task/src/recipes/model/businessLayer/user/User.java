package recipes.model.businessLayer.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import recipes.model.businessLayer.recipe.Recipe;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * User class represents the model in database.
 * It has oneToMany bidirectional relationship with the Recipe table
 */
@Entity
public class User {
    @Id
    @Pattern(regexp = ".+@.+\\..+")
    private String email;
    @NotBlank
    @Size(min = 8)
    private String password;
    private String role;

    @OneToMany(mappedBy = "user")
    private List<Recipe> recipes = new ArrayList<>();

    public User() {
        role = "USER";
    }

    public User(String email, String password, String role) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @JsonIgnore
    public List<Recipe> getRecipes() {
        return recipes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (hashCode() != user.hashCode()) return false;

        if (!email.equals(user.email)) return false;
        if (!password.equals(user.password)) return false;
        if (!role.equals(user.role)) return false;
        return recipes.equals(user.recipes);
    }

    @Override
    public int hashCode() {
        int result = email.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + role.hashCode();
        result = 31 * result + recipes.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", recipes=" + recipes +
                '}';
    }
}
