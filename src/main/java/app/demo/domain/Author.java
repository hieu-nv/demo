package app.demo.domain;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Author.
 *
 * @author Hieu Nguyen
 */
@Data
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "AUTHORS")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
// @SequenceGenerator(
//    name = "authorSequenceGenerator",
//    initialValue = 1,
//    allocationSize = 1,
//    sequenceName = "authorSequence")
@TableGenerator(
    name = "authorSequenceGenerator",
    table = "hibernate_sequences",
    pkColumnName = "sequence_name",
    valueColumnName = "next_val",
    pkColumnValue = "authorSequence",
    allocationSize = 1)
public class Author {
  @Id
  @EqualsAndHashCode.Include
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "authorSequenceGenerator")
  private Long id;

  private String firstName;
  private String lastName;

  @ManyToMany(cascade = {CascadeType.ALL})
  @JoinTable(
      joinColumns = @JoinColumn(name = "AUTHOR_ID"),
      inverseJoinColumns = @JoinColumn(name = "BOOK_ID"))
  private Set<Book> books;
}
