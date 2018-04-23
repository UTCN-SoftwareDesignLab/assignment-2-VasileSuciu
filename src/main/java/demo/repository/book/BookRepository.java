package demo.repository.book;

import demo.model.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.yaml.snakeyaml.events.Event;

import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<Book,Long>{

    Book findByTitle(String title);

    List<Book> findAllByStock(int stock);

    @Query("from Book as b where b.title like %?1% or b.author like %?1% or b.genre like %?1%")
    List<Book> findAllByQuery(String q);

}
