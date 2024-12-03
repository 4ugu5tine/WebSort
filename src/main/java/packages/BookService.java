package packages;


import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class BookService {

    private final Map<Long, Book> books = new HashMap<>();

    private Long idCounter = 3L;
    public List<Book> findAll() {
        return new ArrayList<>(books.values());
    }

    public Book findById(Long id) {
        return books.get(id);
    }

    public Book save(Book book) {
        if (book.getId() == null) {
            book.setId(idCounter++);
        }
        books.put(book.getId(), book);
        return book;
    }

    public void delete(Long id) {
        books.remove(id);
    }

}
