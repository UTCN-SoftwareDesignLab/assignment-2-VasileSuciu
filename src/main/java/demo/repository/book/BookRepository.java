package demo.repository.book;

import demo.model.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<Book,Long>{

    Book findByTitle(String title);

    List<Book> findAllByStock(int stock);

}
