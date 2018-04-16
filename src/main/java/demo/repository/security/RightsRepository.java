package demo.repository.security;

import demo.model.Right;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RightsRepository extends CrudRepository<Right,Long>{

    Right findByRight(String right);

}
