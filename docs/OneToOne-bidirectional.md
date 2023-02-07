Chúng ta đã cùng tìm hiểu về [ánh xạ one-to-one unidirectional](https://magz.techover.io/2023/02/06/anh-xa-one-to-one-trong-hibernate/). Chúng ta cũng đã đề cập tới *one-to-one bidirectional*. Với *one-tone-unidirectional* chúng ta đã có thể truy cập thực thể đích từ thực thể nguồn nhưng không thể truy cập ngược lại. Trong bài viết này chúng ta sẽ cùng tìm hiểu những hạn chế của quan hệ *unidirectional* và tại sao chúng ta cần quan hệ *bidirectional*.

### Những hạn chế của *one-to-one unidirectional*

Trong bài viết trước chúng ta đã định nghĩa hai thực thể *Customer* và *Account* có quan hệ thông qua khoá ngoại `ACCOUNT_ID`. Nếu bằng cách nào đó, chúng ta chỉ xoá thực thể *Account* và để nguyên thực thể *Customer*, khi đó khoá ngoại trong bảng *Customer* sẽ tham chiếu tới một đối tượng không tồn tại, vấn đề này còn được gọi là *dangling foreign key*. Tuỳ chọn xoá thực thể *Customer* khi thực thị *Account* bị xoá phụ thuộc vào thiết kế cơ dữ liệu, đôi khi chúng ta muốn giữ lại thực thể *Customer* dưới dạng thông tin lưu trữ để theo dõi lịch sử. Chúng ta có thể làm điều này mà không cần thay đổi cơ sở dữ liệu bằng cách thay đổi thực thể *Account*

```java
/**
 * <code>account</code>.
 *
 * @author Hieu Nguyen
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "account")
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Account {

  /** <code>id</code>. */
  @Id
  @ToString.Include
  @EqualsAndHashCode.Include
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /** <code>username</code>. */
  @ToString.Include private String username;

  @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
  private Customer customer;
}
```

Ở thực thể *Account* chúng ta không có cột nào có thể sử dụng để tham chiếu tới thực thể *Customer*. Do đó chúng ta cần đến sự hỗ trợ từ *Hibernate*.

### Cài đặt *one-to-one bidirectional*

Chúng ta chỉ cần thêm đoạn mã sau vào thực thể *Account*:

```java
  ...
  @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
  private Customer customer;
  ...
```
Định nghĩa `mappedBy = "account"` sẽ thông báo cho *Hibernate* biết rằng nó cần tìm thuộc tính `account` trong thực thể *Customer* và liên kết thực thể cụ thể đó với đối tượng *Account*. Bây giờ chúng ta cùng thêm một thực thể vào *database* nhưng lúc này chúng ta sẽ *save* thực thể *Account* và thực thể *Customer* cũng sẽ được thêm vào *database* vì chúng ta đã sử dụng `cascade = CascadeType.ALL`.

```java
@Component
public class Main implements CommandLineRunner {
  @Autowired private AccountRepository accountRepository;
  @Autowired private CustomerRepository customerRepository;

  @Override
  @Transactional
  public void run(String... args) throws Exception {
    var customer = Customer.builder().firstName("Hieu").lastName("Nguyen").build();
    var account = Account.builder().username("hieunv").customer(customer).build();
    customer.setAccount(account);
    accountRepository.save(account);
  }
}
```
Sau khi chạy đoạn mã trên, chúng ta thấy rằng có 2 bản ghi đã được *insert* vào *database*. Chúng ta cũng sẽ thấy ouput như sau:

```
2023-02-07 13:05:42.746 DEBUG 90289 --- [           main] o.h.e.t.internal.TransactionImpl         : On TransactionImpl creation, JpaCompliance#isJpaTransactionComplianceEnabled == false
2023-02-07 13:05:42.746 DEBUG 90289 --- [           main] o.h.e.t.internal.TransactionImpl         : begin
2023-02-07 13:05:42.764 DEBUG 90289 --- [           main] org.hibernate.engine.spi.ActionQueue     : Executing identity-insert immediately
2023-02-07 13:05:42.767 DEBUG 90289 --- [           main] org.hibernate.SQL                        : insert into account (username) values (?)
Hibernate: insert into account (username) values (?)
2023-02-07 13:05:42.769 TRACE 90289 --- [           main] o.h.type.descriptor.sql.BasicBinder      : binding parameter [1] as [VARCHAR] - [hieunv]
2023-02-07 13:05:42.778 DEBUG 90289 --- [           main] o.h.id.IdentifierGeneratorHelper         : Natively generated identity: 2
2023-02-07 13:05:42.778 DEBUG 90289 --- [           main] o.h.r.j.i.ResourceRegistryStandardImpl   : HHH000387: ResultSet's statement was not registered
2023-02-07 13:05:42.779 DEBUG 90289 --- [           main] org.hibernate.engine.spi.ActionQueue     : Executing identity-insert immediately
2023-02-07 13:05:42.779 DEBUG 90289 --- [           main] org.hibernate.SQL                        : insert into customer (account_id, first_name, last_name) values (?, ?, ?)
Hibernate: insert into customer (account_id, first_name, last_name) values (?, ?, ?)
2023-02-07 13:05:42.779 TRACE 90289 --- [           main] o.h.type.descriptor.sql.BasicBinder      : binding parameter [1] as [BIGINT] - [2]
2023-02-07 13:05:42.779 TRACE 90289 --- [           main] o.h.type.descriptor.sql.BasicBinder      : binding parameter [2] as [VARCHAR] - [Hieu]
2023-02-07 13:05:42.779 TRACE 90289 --- [           main] o.h.type.descriptor.sql.BasicBinder      : binding parameter [3] as [VARCHAR] - [Nguyen]
2023-02-07 13:05:42.792 DEBUG 90289 --- [           main] o.h.id.IdentifierGeneratorHelper         : Natively generated identity: 2
2023-02-07 13:05:42.792 DEBUG 90289 --- [           main] o.h.r.j.i.ResourceRegistryStandardImpl   : HHH000387: ResultSet's statement was not registered
2023-02-07 13:05:42.792 DEBUG 90289 --- [           main] o.h.e.t.internal.TransactionImpl         : committing
2023-02-07 13:05:42.793 DEBUG 90289 --- [           main] o.h.e.i.AbstractFlushingEventListener    : Processing flush-time cascades
2023-02-07 13:05:42.793 DEBUG 90289 --- [           main] o.h.e.i.AbstractFlushingEventListener    : Dirty checking collections
2023-02-07 13:05:42.794 DEBUG 90289 --- [           main] o.h.e.i.AbstractFlushingEventListener    : Flushed: 0 insertions, 0 updates, 0 deletions to 2 objects
2023-02-07 13:05:42.794 DEBUG 90289 --- [           main] o.h.e.i.AbstractFlushingEventListener    : Flushed: 0 (re)creations, 0 updates, 0 removals to 0 collections
2023-02-07 13:05:42.795 DEBUG 90289 --- [           main] o.hibernate.internal.util.EntityPrinter  : Listing entities:
2023-02-07 13:05:42.795 DEBUG 90289 --- [           main] o.hibernate.internal.util.EntityPrinter  : app.demo.entity.Account{id=2, username=hieunv, customer=app.demo.entity.Customer#2}
2023-02-07 13:05:42.795 DEBUG 90289 --- [           main] o.hibernate.internal.util.EntityPrinter  : app.demo.entity.Customer{firstName=Hieu, lastName=Nguyen, id=2, account=app.demo.entity.Account#2}
```
Nhìn vào ouput trên chúng ta thấy rằng có 2 bản ghi đã được *insert* vào *database* với `id=2`.

### Truy xuất thông tin *Customer* từ thực thể *Account*

Bây giờ chúng ta cùng tìm hiểu xem bằng cách nào *Hibernate* có thể lấy thông tin *Customer* thông qua thực thể *Account*. Chúng ta cùng cài đặt thử đoạn mã sau:

```java
@Component
@Slf4j
public class Main implements CommandLineRunner {
  @Autowired private AccountRepository accountRepository;
  @Autowired private CustomerRepository customerRepository;

  @Override
  @Transactional
  public void run(String... args) throws Exception {
    log.info("{}", accountRepository.findById(2L).get().getCustomer());
  }
}
```

Sau khi chạy chương trình các bạn sẽ thấy output:

```
2023-02-07 13:17:51.141 DEBUG 90956 --- [           main] o.h.e.t.internal.TransactionImpl         : On TransactionImpl creation, JpaCompliance#isJpaTransactionComplianceEnabled == false
2023-02-07 13:17:51.141 DEBUG 90956 --- [           main] o.h.e.t.internal.TransactionImpl         : begin
2023-02-07 13:17:51.157 DEBUG 90956 --- [           main] org.hibernate.SQL                        : select account0_.id as id1_0_0_, account0_.username as username2_0_0_, customer1_.id as id1_1_1_, customer1_.account_id as account_4_1_1_, customer1_.first_name as first_na2_1_1_, customer1_.last_name as last_nam3_1_1_ from account account0_ left outer join customer customer1_ on account0_.id=customer1_.account_id where account0_.id=?
Hibernate: select account0_.id as id1_0_0_, account0_.username as username2_0_0_, customer1_.id as id1_1_1_, customer1_.account_id as account_4_1_1_, customer1_.first_name as first_na2_1_1_, customer1_.last_name as last_nam3_1_1_ from account account0_ left outer join customer customer1_ on account0_.id=customer1_.account_id where account0_.id=?
2023-02-07 13:17:51.159 TRACE 90956 --- [           main] o.h.type.descriptor.sql.BasicBinder      : binding parameter [1] as [BIGINT] - [2]
2023-02-07 13:17:51.170 DEBUG 90956 --- [           main] l.p.e.p.i.EntityReferenceInitializerImpl : On call to EntityIdentifierReaderImpl#resolve, EntityKey was already known; should only happen on root returns with an optional identifier specified
2023-02-07 13:17:51.173 DEBUG 90956 --- [           main] o.h.engine.internal.TwoPhaseLoad         : Resolving attributes for [app.demo.entity.Account#2]
2023-02-07 13:17:51.173 DEBUG 90956 --- [           main] o.h.engine.internal.TwoPhaseLoad         : Processing attribute `username` : value = hieunv
2023-02-07 13:17:51.173 DEBUG 90956 --- [           main] o.h.engine.internal.TwoPhaseLoad         : Attribute (`username`)  - enhanced for lazy-loading? - false
2023-02-07 13:17:51.173 DEBUG 90956 --- [           main] o.h.engine.internal.TwoPhaseLoad         : Processing attribute `customer` : value = 2
2023-02-07 13:17:51.173 DEBUG 90956 --- [           main] o.h.engine.internal.TwoPhaseLoad         : Attribute (`customer`)  - enhanced for lazy-loading? - false
2023-02-07 13:17:51.174 DEBUG 90956 --- [           main] o.h.engine.internal.TwoPhaseLoad         : Done materializing entity [app.demo.entity.Account#2]
2023-02-07 13:17:51.174 DEBUG 90956 --- [           main] o.h.engine.internal.TwoPhaseLoad         : Resolving attributes for [app.demo.entity.Customer#2]
2023-02-07 13:17:51.174 DEBUG 90956 --- [           main] o.h.engine.internal.TwoPhaseLoad         : Processing attribute `account` : value = 2
2023-02-07 13:17:51.174 DEBUG 90956 --- [           main] o.h.engine.internal.TwoPhaseLoad         : Attribute (`account`)  - enhanced for lazy-loading? - false
2023-02-07 13:17:51.174 DEBUG 90956 --- [           main] o.h.engine.internal.TwoPhaseLoad         : Processing attribute `firstName` : value = Hieu
2023-02-07 13:17:51.174 DEBUG 90956 --- [           main] o.h.engine.internal.TwoPhaseLoad         : Attribute (`firstName`)  - enhanced for lazy-loading? - false
2023-02-07 13:17:51.174 DEBUG 90956 --- [           main] o.h.engine.internal.TwoPhaseLoad         : Processing attribute `lastName` : value = Nguyen
2023-02-07 13:17:51.174 DEBUG 90956 --- [           main] o.h.engine.internal.TwoPhaseLoad         : Attribute (`lastName`)  - enhanced for lazy-loading? - false
2023-02-07 13:17:51.174 DEBUG 90956 --- [           main] o.h.engine.internal.TwoPhaseLoad         : Done materializing entity [app.demo.entity.Customer#2]
2023-02-07 13:17:51.174 DEBUG 90956 --- [           main] .l.e.p.AbstractLoadPlanBasedEntityLoader : Done entity load : app.demo.entity.Account#2
2023-02-07 13:17:51.175  INFO 90956 --- [           main] app.demo.Main                            : Customer(id=2, firstName=Hieu, lastName=Nguyen)
2023-02-07 13:17:51.175 DEBUG 90956 --- [           main] o.h.e.t.internal.TransactionImpl         : committing
2023-02-07 13:17:51.175 DEBUG 90956 --- [           main] o.h.e.i.AbstractFlushingEventListener    : Processing flush-time cascades
2023-02-07 13:17:51.178 DEBUG 90956 --- [           main] o.h.e.i.AbstractFlushingEventListener    : Dirty checking collections
2023-02-07 13:17:51.179 DEBUG 90956 --- [           main] o.h.e.i.AbstractFlushingEventListener    : Flushed: 0 insertions, 0 updates, 0 deletions to 2 objects
2023-02-07 13:17:51.179 DEBUG 90956 --- [           main] o.h.e.i.AbstractFlushingEventListener    : Flushed: 0 (re)creations, 0 updates, 0 removals to 0 collections
2023-02-07 13:17:51.179 DEBUG 90956 --- [           main] o.hibernate.internal.util.EntityPrinter  : Listing entities:
2023-02-07 13:17:51.179 DEBUG 90956 --- [           main] o.hibernate.internal.util.EntityPrinter  : app.demo.entity.Account{id=2, username=hieunv, customer=app.demo.entity.Customer#2}
2023-02-07 13:17:51.179 DEBUG 90956 --- [           main] o.hibernate.internal.util.EntityPrinter  : app.demo.entity.Customer{firstName=Hieu, lastName=Nguyen, id=2, account=app.demo.entity.Account#2}

```

Các bạn chú ý tới dòng sau:

```
2023-02-07 13:17:51.157 DEBUG 90956 --- [           main] org.hibernate.SQL                        : select account0_.id as id1_0_0_, account0_.username as username2_0_0_, customer1_.id as id1_1_1_, customer1_.account_id as account_4_1_1_, customer1_.first_name as first_na2_1_1_, customer1_.last_name as last_nam3_1_1_ from account account0_ left outer join customer customer1_ on account0_.id=customer1_.account_id where account0_.id=?
Hibernate: select account0_.id as id1_0_0_, account0_.username as username2_0_0_, customer1_.id as id1_1_1_, customer1_.account_id as account_4_1_1_, customer1_.first_name as first_na2_1_1_, customer1_.last_name as last_nam3_1_1_ from account account0_ left outer join customer customer1_ on account0_.id=customer1_.account_id where account0_.id=?
```

Các bạn có thể thấy rằng thực thể *Customer* có thể đường truy xuất thông qua câu lệnh `LEFT OUTER JOIN`. Chúng ta cũng có thể thực hiện các thao tác *update* và *delete* theo cùng cách như đã thực hiện với *one-to-one unidirectional*.

### Tổng kết

Trong bài viết này chúng ta đã chỉ ra những vấn đề đối với quan hệ *one-to-one unidirectional* và cách triển khai quan hệ *one-to-one bidirectional* trong *Hibernate* để giải quyết các vấn đề với *one-to-one unidirectional*.