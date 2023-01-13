package app.demo.domain;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/** Book. */
@Data
@Entity
@Builder
@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BOOKS")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
// @SequenceGenerator(
//    name = "bookSequenceGenerator",
//    initialValue = 1,
//    allocationSize = 1,
//    sequenceName = "bookSequence")
@TableGenerator(
    name = "bookSequenceGenerator",
    table = "hibernate_sequences",
    pkColumnName = "sequence_name",
    valueColumnName = "next_val",
    pkColumnValue = "bookSequence",
    allocationSize = 1)
public class Book {

  @Id
  @ToString.Include
  @EqualsAndHashCode.Include
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "bookSequenceGenerator")
  private Long id;

  @ToString.Include private String name;

  @Getter(AccessLevel.NONE)
  @JoinColumn(name = "PUBLISHER_ID")
  @ManyToOne
  private Publisher publisher;

  @ManyToMany
  @JoinTable(
      name = "book_authors",
      joinColumns = @JoinColumn(name = "BOOK_ID"),
      inverseJoinColumns = @JoinColumn(name = "AUTHOR_ID"))
  private Set<Author> authors;
}
