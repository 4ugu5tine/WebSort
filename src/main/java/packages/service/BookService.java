package packages.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import packages.DatabaseConnection;
import packages.model.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@Service
public class BookService {
    private final JdbcTemplate jdbcTemplate;

    public BookService(DatabaseConnection databaseConnection) {
        this.jdbcTemplate = databaseConnection.jdbcTemplate(databaseConnection.dataSource());
    }

    public List<Book> findAll() {
        String sql = "SELECT * FROM books";
        return jdbcTemplate.query(sql, new BookRowMapper());
    }

    public Book findById(Long id) {
        String sql = "SELECT * FROM books WHERE bookid = ?";
        return jdbcTemplate.queryForObject(sql, new BookRowMapper(), id);
    }


    public void save(Book book) {
        String sql = "INSERT INTO books (title, author, copies) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, book.getTitle(), book.getAuthor(), book.getCopies());
    }

    public void updateBook(Long bookId, Book book) {
        String sql = "UPDATE books SET title = ?, author = ?, copies = ? WHERE bookId = ?";
        jdbcTemplate.update(sql, book.getTitle(), book.getAuthor(), book.getCopies(), bookId);
    }


    public String delete(Long id) {
        String sql = "DELETE FROM books WHERE bookid = ?";
        jdbcTemplate.update(sql, id);
        return "Book deleted successfully";
    }


    private static class BookRowMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            Book book = new Book();
            book.setId(rs.getLong("bookId"));
            book.setTitle(rs.getString("title"));
            book.setAuthor(rs.getString("author"));
            book.setCopies(rs.getInt("copies"));
            return book;
        }
    }
}
