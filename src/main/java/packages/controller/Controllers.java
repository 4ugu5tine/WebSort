package packages.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import packages.model.Book;
import packages.service.BookService;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequiredArgsConstructor
@RestController
public class Controllers {

    private final BookService bookServices;

    @GetMapping("/home")
    public ModelAndView welcomePage(){
        ModelAndView model = new ModelAndView();

        model.setViewName("homepage");
        return model;
    }

    @GetMapping("/all")
    public CollectionModel<EntityModel<Book>> getAllBooks() {
        List<EntityModel<Book>> books = bookServices.findAll().stream()
                .map(book -> EntityModel.of(book,
                        linkTo(methodOn(Controllers.class).getBookById(book.getId())).withSelfRel()))
                .collect(Collectors.toList());
        return CollectionModel.of(books, linkTo(methodOn(Controllers.class).getAllBooks()).withSelfRel());

    }


    @GetMapping("/{id}")
    public EntityModel<Book> getBookById(@PathVariable("id") Long id)  {
        Book book = bookServices.findById(id);
        if (book == null) {
            throw new RuntimeException("Book not found");
        }
        return EntityModel.of(book,
                linkTo(methodOn(Controllers.class).getAllBooks()).withRel("books"));
    }

    @PostMapping("/add")
    public EntityModel<Book> createBook(@RequestBody Book book) {
        bookServices.save(book);
        return EntityModel.of(book,
                linkTo(methodOn(Controllers.class).getAllBooks()).withRel("books"));    }


    @PatchMapping("/{id}")
    public EntityModel<Book> updateBook(@PathVariable("id") Long id, @RequestBody Book bookDetails) {

        bookServices.updateBook(id,bookDetails);

        return EntityModel.of( bookServices.findById(id),
                linkTo(methodOn(Controllers.class).getBookById(id)).withSelfRel(),
                linkTo(methodOn(Controllers.class).getAllBooks()).withRel("books"));    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable("id") Long id) throws SQLException {
        Book book = bookServices.findById(id);
        if (book == null) {
            throw new RuntimeException("Book not found");
        }
        bookServices.delete(id);
        return new ResponseEntity<>("Book deleted successfully",HttpStatus.OK);
    }

}
