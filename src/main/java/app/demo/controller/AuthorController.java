package app.demo.controller;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.springframework.http.ResponseEntity.ok;

import app.demo.domain.Author;
import app.demo.repository.AuthorRepository;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** AuthorController. */
@Validated
@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController {
  private final AuthorRepository authorRepository;

  @GetMapping
  public ResponseEntity<Iterable<Author>> index(@RequestParam String name) {

    return ok().body(authorRepository.findAll());
  }

  /**
   * create.
   *
   * @return ResponseEntity&lt;String&gt;
   */
  @PostMapping
  @Transactional
  @SuppressWarnings("checkstyle:ParameterName")
  public ResponseEntity<String> create(
      @RequestHeader(name = "X-Request-Id") @Valid @Pattern(regexp = "test|dev")
          String xRequestId) {
    authorRepository.save(
        Author.builder().firstName(randomAlphabetic(16)).lastName(randomAlphabetic(16)).build());
    return ok().body("OK");
  }

  /**
   * delete.
   *
   * @param id Long
   * @return ResponseEntity&lt;String&gt;
   */
  @Transactional
  @DeleteMapping("/{id}")
  public ResponseEntity<String> delete(@Valid @Min(1) @PathVariable Long id) {
    authorRepository.deleteById(id);
    return ok().body("OK");
  }
}
