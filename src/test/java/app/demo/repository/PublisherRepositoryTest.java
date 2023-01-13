package app.demo.repository;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.UUID;
import javax.persistence.EntityNotFoundException;
import app.demo.domain.Book;
import app.demo.domain.Publisher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * PublisherRepositoryTest.
 *
 * @author hieunv
 */
@SpringBootTest
class PublisherRepositoryTest {

  @Autowired PublisherRepository publisherRepository;

  @Autowired BookRepository bookRepository;

  @Test
  @Transactional(readOnly = true)
  void test() {
    Publisher publisher =
        publisherRepository
            .findById(UUID.fromString("34033d4b-8d12-4e8c-a440-5baff4cb2a91"))
            .orElseThrow(EntityNotFoundException::new);

    publisher.getBooks().stream().map(Book::getName).forEach(System.out::println);

    //
    //    assertThat(opt.isEmpty(), is(false));
    //    assertThat(opt.get().getName(), is("Simon & Schuster"));
    //

    //    var publisher = Publisher.builder().name("4").build();
    //
    //    var book =
    //        Book.builder()
    //            .name(
    //                "Microsoft Information Protection Administrator SC-400 Certification Guide:
    // Advance your Microsoft Security & Compliance services knowledge and pass the SC-400 exam with
    // confidence")
    //            .build();
    //    Set<Book> books = new HashSet<>();
    //    books.add(book);
    //    publisher.setBooks(books);
    //    publisherRepository.save(publisher);

    //    var publisher = Publisher.builder().name("5").build();
    //
    //    var book = Book.builder().name("5-1").publisher(publisher).build();
    //
    //    bookRepository.save(book);

    assertThat(true, is(true));
  }

  @Test
  @Transactional(readOnly = true)
  void testN1() {
    publisherRepository.findAll().stream()
        .flatMap((x) -> x.getBooks().stream())
        .forEach(System.out::println);
  }
}
