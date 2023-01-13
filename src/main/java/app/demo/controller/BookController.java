package app.demo.controller;

import static org.springframework.http.ResponseEntity.ok;

import app.demo.domain.Book;
import app.demo.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** {@link BookController}. */
@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {
  private final BookRepository bookRepository;

  /**
   * create.
   *
   * @return ResponseEntity&lt;Book&gt;
   */
  @PostMapping
  public ResponseEntity<Book> create() {
    Book book =
        bookRepository.save(Book.builder().name(RandomStringUtils.randomAlphabetic(16)).build());
    return ok().body(book);
  }
}
