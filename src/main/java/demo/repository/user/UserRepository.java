package demo.repository.user;

import demo.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User,Long>{

    User findByUsername(String username);

    User findByUsernameAndPassword(String username, String password);


}
