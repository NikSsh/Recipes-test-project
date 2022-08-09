package recipes.model.persistenceLayer;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import recipes.model.businessLayer.user.User;

@Repository
public interface UsersRepository extends CrudRepository<User, String> {
}
