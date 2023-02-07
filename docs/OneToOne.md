*Hibernate* là một *framework* cung cấp một số lớp trừu tượng, nghĩa là lập trình viên không phải lo lắng về việc triển khai, *Hibernate* tự thực hiện các triển khai bên trong nó như thiết lập một kết nối cơ sở dữ liệu, viết các truy vấn để thực hiện các thao tác *CRUD*, ... Nó là một *java framework* được sử dụng để phát triển *persistence logic*. *Persistence logic* có nghĩa là lưu trữ và xử lí dữ liệu để sử dụng lâu dài. Chính xác hơn *Hibernate * là một *framework ORM* (Object Relational Mapping) mã nguồn mở để phát triển các đối tượng độc lập với các phần mềm cơ sở dữ liệu và tạo ra *persistence logic* độc lập với *Java*, J2EE.

### Ánh xạ *one-to-one* là gì?

Anh xạ *one-to-one* thể hiện rằng một thực thể duy nhất có mối liên kết với một thể hiện duy nhất của một thực thể khác. Một thể hiện của thực thể nguồn có thể được ánh xạ tới nhiều nhất một thể hiện của thực thể đích. Một số ví dụ minh hoạ ánh xạ *one-to-one*:

- Mỗi người chỉ có duy nhất một hộ chiếu, một hộ chiếu chỉ được liên kết với duy nhất một người.
- Mỗi con báo có một mẫu đốm độc nhất, một mẫu đốm chỉ được liên kết với duy nhất một con báo.
- Mỗi chúng ta có một định danh duy nhất ở trường đại học, mỗi định danh dược liên kết với một người duy nhất.

Trong các hệ quản trị cơ sở dữ liệu, ánh xạ *one-to-one* thường có hai kiểu:

- *one-to-one unidirectional*
- *one-to-one bidirectional*

### *one-to-one unidirectional*

Ở kiểu ánh xạ này một thực thể có một thuộc tính hoặc một cột tham chiếu tới một thuộc tính hoặc một cột ở thực thể đích. Chúng ta cùng xem ví dụ sau:

![](https://s3.ap-southeast-1.amazonaws.com/techover.storage/wp-content/uploads/2023/02/06144042/one-to-one.png)

Bảng *customer* tham chiếu tới bảng *account* thông qua khoá ngoại *ACCOUNT_ID*. Thực thể đích (*account*) không có cách nào tham chiếu tới bảng *customer* nhưng bảng *customer* có thể truy cập tới bảng *account* thông qua khoá ngoại. Quan hệ trên được sinh ra bởi kịch bản *SQL* sau:

```SQL
CREATE TABLE IF NOT EXISTS `ACCOUNT` (
  `ID` BIGINT NOT NULL AUTO_INCREMENT
  , `USERNAME` VARCHAR(255) UNIQUE
  , PRIMARY KEY (`ID`)
);

CREATE TABLE IF NOT EXISTS `CUSTOMER` (
  `ID` BIGINT NOT NULL AUTO_INCREMENT
  , `FIRST_NAME` VARCHAR(255) NULL DEFAULT NULL
  , `LAST_NAME` VARCHAR(255) NULL DEFAULT NULL
  , `ACCOUNT_ID` BIGINT NOT NULL UNIQUE
  , PRIMARY KEY (`ID`)
  , FOREIGN KEY (`ACCOUNT_ID`)
    REFERENCES `demo`.`ACCOUNT`(`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
);
```

Khi tạo bảng *customer* chúng ta tham chiếu khoá chính trong bảng *account* (*account_id*). Chúng ta cố tình đặt `ON DELETE NO ACTION` và `ON UPDATE NO ACTION` vì chúng ta sẽ đặt các giá trị này bên trong *Hibernate*. Bây giờ chúng ta sẽ thực hiện migration kịch bản này bằng *Flyway*. Tham khảo [Hướng dẫn migrate cơ sở dữ liệu sử dụng Flyway trong ứng dụng Spring Boot](https://magz.techover.io/2023/01/30/huong-dan-migrate-co-so-du-lieu-su-dung-flyway-trong-ung-dung-spring-boot/).

Trước khi bắt đầu định nghĩa các thực thể, chúng ta cần thêm các thư viện cần thiết. Tham khảo [Hướng dẫn sử dụng Spring Boot với Hibernate](https://magz.techover.io/2023/02/03/huong-dan-su-dung-spring-boot-voi-hibernate/) để thực hiện các thao tác cần thiết.

### Định nghĩa *Hibernate entity*

Bây giờ chúng ta đã có thể định nghĩa các thực thể *Hibernate*.

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
}
```

```java
/**
 * <code>customer</code>.
 *
 * @author Hieu Nguyen
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "customer")
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Customer {

  /** <code>id</code>. */
  @Id
  @ToString.Include
  @EqualsAndHashCode.Include
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /** <code>first_name</code> */
  @ToString.Include private String firstName;

  /** <code>last_name</code>. */
  @ToString.Include private String lastName;

  /** <code>account_id</code>. */
  @OneToOne(optional = false, cascade = CascadeType.ALL)
  @JoinColumn(name = "ACCOUNT_ID", unique = true, nullable = false, updatable = false)
  private Account account;
}
```

Chúng ta sử dụng `@Entity` annotation để định nghĩa *Hibernate entity*. Tên bảng tương ứng với *entity* được định nghĩa thông quan thuộc tính `name` của `@Entity` annotation hoặc có thể sử dụng `@Table(name = "account")` để định nghĩa tên bảng. Chúng ta cùng xem xét một số annotation khác:

- `@Id` annotation định nghĩa trường tưng ứng là khoá chính của *entity*.
- `@GeneratedValue` annotation định nghĩa chiến lược sinh giá trị cho khoá chính, chúng ta sử dụng `strategy = GenerationType.IDENTITY` để xác định khoá chính sẽ được sinh tự động trong cơ sở dữ liệu (cột tương ứng trong cơ sở dữ liệu được đánh dấu là `AUTO_INCREMENT`).
- `@Column` annotation định nghĩa tên cột tương ứng trong cơ sở dữ liệu.

### Triển khai ánh xạ `one-to-one`

Phần chính mà chúng ta cần chú ý tới:
```java
  @OneToOne(optional = false, cascade = CascadeType.ALL)
  @JoinColumn(name = "ACCOUNT_ID", unique = true, nullable = false, updatable = false)
  private Account account;
```

Đối tượng `Account` được thêm vào bên trong *class* `Customer` và được đánh dấu với `@OneToOne` annotation để xác định đây ánh xạ `one-to-one`. Annotation cũng chưa thuộc tính `cascade` xác định chiến lược *cascading*. *Cascading* là một tính năng của *Hibernate* được sử dụng để quản lí trạng thái của thực thể đích mỗi khi trạng thái của thực thể cha thay đổi. *Hibernate* có các kiểu *cascading* sau:

- CascadeType.ALL – lan truyền tất cả các thao tác từ thực thể cha sang thực thể đích.
- CascadeType.PERSIST – lan truyền thao tác persist từ thực thể cha sang thực thể đích.
- CascadeType.MERGE – lan truyền thao tác merge từ thực thể cha sang thực thể đích.
- CascadeType.REMOVE – lan truyền thao tác remove từ thực thể cha sang thực thể đích.
- CascadeType.REFRESH – lan truyền thao tác refresh từ thực thể cha sang thực thể đích.
- CascadeType.DETACH – lan truyền thao tác detach từ thực thể cha sang thực thể đích.

Ví dụ nếu `cascade = CascadeType.REMOVE` thì nếu thực thể cha bị xoá khởi cơ sở dữ liệu thì thực thể đích cũng bị xoá khỏi cơ sở dữ liệu. Trong trường hợp của chúng ta nếu thực thể `Customer` bị xoá khởi cơ sở dữ liệu thì thực thể liên quan `Account` cũng bị xoá khỏi cơ sở dữ liệu.

`@JoinColumn` được xử dụng để xác định tên cột được sử dụng để tìm kiến thực thể đích. Thực thể `Account` sẽ được tìm kiến thông qua cột `ACCOUNT_ID`, nó chính xác là khoá ngoại của bảng `customer` mà chúng ta định nghĩa ở trên.


### Sử dụng *Hibernate entity* để lưu dữ liệu vào cơ sở dữ liệu

Chúng ta cùng tạo một đoạn mã đơn giản để kiểm tra lại toàn bộ định nghĩa đã tạo ở trên:

```java
@Component
public class Main implements CommandLineRunner {
  @Autowired private CustomerRepository customerRepository;

  @Override
  @Transactional
  public void run(String... args) throws Exception {
    var account = Account.builder().username("hieunv").build();
    var customer = Customer.builder().firstName("Hieu").lastName("Nguyen").account(account).build();
    customerRepository.save(customer);
  }
}
```

Sau khi thực thi chương trình chúng ta sẽ thấy output sau:

```
2023-02-06 20:28:55.358 DEBUG 78531 --- [           main] o.h.e.t.internal.TransactionImpl         : On TransactionImpl creation, JpaCompliance#isJpaTransactionComplianceEnabled == false
2023-02-06 20:28:55.358 DEBUG 78531 --- [           main] o.h.e.t.internal.TransactionImpl         : begin
2023-02-06 20:28:55.378 DEBUG 78531 --- [           main] org.hibernate.engine.spi.ActionQueue     : Executing identity-insert immediately
2023-02-06 20:28:55.381 DEBUG 78531 --- [           main] org.hibernate.SQL                        : insert into account (username) values (?)
Hibernate: insert into account (username) values (?)
2023-02-06 20:28:55.383 TRACE 78531 --- [           main] o.h.type.descriptor.sql.BasicBinder      : binding parameter [1] as [VARCHAR] - [hieunv]
2023-02-06 20:28:55.394 DEBUG 78531 --- [           main] o.h.id.IdentifierGeneratorHelper         : Natively generated identity: 1
2023-02-06 20:28:55.394 DEBUG 78531 --- [           main] o.h.r.j.i.ResourceRegistryStandardImpl   : HHH000387: ResultSet's statement was not registered
2023-02-06 20:28:55.395 DEBUG 78531 --- [           main] org.hibernate.engine.spi.ActionQueue     : Executing identity-insert immediately
2023-02-06 20:28:55.395 DEBUG 78531 --- [           main] org.hibernate.SQL                        : insert into customer (account_id, first_name, last_name) values (?, ?, ?)
Hibernate: insert into customer (account_id, first_name, last_name) values (?, ?, ?)
2023-02-06 20:28:55.396 TRACE 78531 --- [           main] o.h.type.descriptor.sql.BasicBinder      : binding parameter [1] as [BIGINT] - [1]
2023-02-06 20:28:55.396 TRACE 78531 --- [           main] o.h.type.descriptor.sql.BasicBinder      : binding parameter [2] as [VARCHAR] - [Hieu]
2023-02-06 20:28:55.396 TRACE 78531 --- [           main] o.h.type.descriptor.sql.BasicBinder      : binding parameter [3] as [VARCHAR] - [Nguyen]
2023-02-06 20:28:55.400 DEBUG 78531 --- [           main] o.h.id.IdentifierGeneratorHelper         : Natively generated identity: 1
2023-02-06 20:28:55.400 DEBUG 78531 --- [           main] o.h.r.j.i.ResourceRegistryStandardImpl   : HHH000387: ResultSet's statement was not registered
2023-02-06 20:28:55.400 DEBUG 78531 --- [           main] o.h.e.t.internal.TransactionImpl         : committing
2023-02-06 20:28:55.401 DEBUG 78531 --- [           main] o.h.e.i.AbstractFlushingEventListener    : Processing flush-time cascades
2023-02-06 20:28:55.401 DEBUG 78531 --- [           main] o.h.e.i.AbstractFlushingEventListener    : Dirty checking collections
2023-02-06 20:28:55.403 DEBUG 78531 --- [           main] o.h.e.i.AbstractFlushingEventListener    : Flushed: 0 insertions, 0 updates, 0 deletions to 2 objects
2023-02-06 20:28:55.403 DEBUG 78531 --- [           main] o.h.e.i.AbstractFlushingEventListener    : Flushed: 0 (re)creations, 0 updates, 0 removals to 0 collections
2023-02-06 20:28:55.403 DEBUG 78531 --- [           main] o.hibernate.internal.util.EntityPrinter  : Listing entities:
2023-02-06 20:28:55.404 DEBUG 78531 --- [           main] o.hibernate.internal.util.EntityPrinter  : app.demo.entity.Account{id=1, username=hieunv}
2023-02-06 20:28:55.404 DEBUG 78531 --- [           main] o.hibernate.internal.util.EntityPrinter  : app.demo.entity.Customer{firstName=Hieu, lastName=Nguyen, id=1, account=app.demo.entity.Account#1}
```

Kiểm tra trong cơ sở dữ liệu chúng ta sẽ thấy các bản ghi sau đã được *insert* vào trong database.

![](https://s3.ap-southeast-1.amazonaws.com/techover.storage/wp-content/uploads/2023/02/06171342/Screenshot-2023-02-06-at-17.12.51.png)

### Xoá dữ liệu cascading

Tiếp theo chúng ta cùng xem một đoạn mã để kiểm chứng cơ chế hoạt động *cascading*. Chúng ta sẽ thử xoá thực thể *Customer* để xem thực thể *Account* tương ứng sẽ được xử lí như thế nào. Chúng ta cùng xem đoạn mã sau:

```java
@Component
public class Main implements CommandLineRunner {
  @Autowired private CustomerRepository customerRepository;

  @Override
  @Transactional
  public void run(String... args) throws Exception {
    var customer = customerRepository.findById(1L).orElseThrow(() -> new EntityNotFoundException());
    customerRepository.delete(customer);
  }
}
```

Sau khi chạy chương trình chúng ta sẽ thấy output như sau:

```
2023-02-07 09:45:07.300 DEBUG 83225 --- [           main] o.h.e.t.internal.TransactionImpl         : On TransactionImpl creation, JpaCompliance#isJpaTransactionComplianceEnabled == false
2023-02-07 09:45:07.300 DEBUG 83225 --- [           main] o.h.e.t.internal.TransactionImpl         : begin
2023-02-07 09:45:07.317 DEBUG 83225 --- [           main] org.hibernate.SQL                        : select customer0_.id as id1_1_0_, customer0_.account_id as account_4_1_0_, customer0_.first_name as first_na2_1_0_, customer0_.last_name as last_nam3_1_0_, account1_.id as id1_0_1_, account1_.username as username2_0_1_ from customer customer0_ inner join account account1_ on customer0_.account_id=account1_.id where customer0_.id=?
Hibernate: select customer0_.id as id1_1_0_, customer0_.account_id as account_4_1_0_, customer0_.first_name as first_na2_1_0_, customer0_.last_name as last_nam3_1_0_, account1_.id as id1_0_1_, account1_.username as username2_0_1_ from customer customer0_ inner join account account1_ on customer0_.account_id=account1_.id where customer0_.id=?
2023-02-07 09:45:07.319 TRACE 83225 --- [           main] o.h.type.descriptor.sql.BasicBinder      : binding parameter [1] as [BIGINT] - [1]
2023-02-07 09:45:07.324 DEBUG 83225 --- [           main] l.p.e.p.i.EntityReferenceInitializerImpl : On call to EntityIdentifierReaderImpl#resolve, EntityKey was already known; should only happen on root returns with an optional identifier specified
2023-02-07 09:45:07.328 DEBUG 83225 --- [           main] o.h.engine.internal.TwoPhaseLoad         : Resolving attributes for [app.demo.entity.Customer#1]
2023-02-07 09:45:07.328 DEBUG 83225 --- [           main] o.h.engine.internal.TwoPhaseLoad         : Processing attribute `account` : value = 1
2023-02-07 09:45:07.328 DEBUG 83225 --- [           main] o.h.engine.internal.TwoPhaseLoad         : Attribute (`account`)  - enhanced for lazy-loading? - false
2023-02-07 09:45:07.328 DEBUG 83225 --- [           main] o.h.engine.internal.TwoPhaseLoad         : Processing attribute `firstName` : value = Hieu
2023-02-07 09:45:07.329 DEBUG 83225 --- [           main] o.h.engine.internal.TwoPhaseLoad         : Attribute (`firstName`)  - enhanced for lazy-loading? - false
2023-02-07 09:45:07.329 DEBUG 83225 --- [           main] o.h.engine.internal.TwoPhaseLoad         : Processing attribute `lastName` : value = Nguyen
2023-02-07 09:45:07.329 DEBUG 83225 --- [           main] o.h.engine.internal.TwoPhaseLoad         : Attribute (`lastName`)  - enhanced for lazy-loading? - false
2023-02-07 09:45:07.329 DEBUG 83225 --- [           main] o.h.engine.internal.TwoPhaseLoad         : Done materializing entity [app.demo.entity.Customer#1]
2023-02-07 09:45:07.329 DEBUG 83225 --- [           main] o.h.engine.internal.TwoPhaseLoad         : Resolving attributes for [app.demo.entity.Account#1]
2023-02-07 09:45:07.329 DEBUG 83225 --- [           main] o.h.engine.internal.TwoPhaseLoad         : Processing attribute `username` : value = hieunv
2023-02-07 09:45:07.329 DEBUG 83225 --- [           main] o.h.engine.internal.TwoPhaseLoad         : Attribute (`username`)  - enhanced for lazy-loading? - false
2023-02-07 09:45:07.329 DEBUG 83225 --- [           main] o.h.engine.internal.TwoPhaseLoad         : Done materializing entity [app.demo.entity.Account#1]
2023-02-07 09:45:07.330 DEBUG 83225 --- [           main] .l.e.p.AbstractLoadPlanBasedEntityLoader : Done entity load : app.demo.entity.Customer#1
2023-02-07 09:45:07.334 DEBUG 83225 --- [           main] o.h.e.t.internal.TransactionImpl         : committing
2023-02-07 09:45:07.334 DEBUG 83225 --- [           main] o.h.e.i.AbstractFlushingEventListener    : Processing flush-time cascades
2023-02-07 09:45:07.334 DEBUG 83225 --- [           main] o.h.e.i.AbstractFlushingEventListener    : Dirty checking collections
2023-02-07 09:45:07.335 DEBUG 83225 --- [           main] o.h.e.i.AbstractFlushingEventListener    : Flushed: 0 insertions, 0 updates, 2 deletions to 2 objects
2023-02-07 09:45:07.335 DEBUG 83225 --- [           main] o.h.e.i.AbstractFlushingEventListener    : Flushed: 0 (re)creations, 0 updates, 0 removals to 0 collections
2023-02-07 09:45:07.335 DEBUG 83225 --- [           main] o.hibernate.internal.util.EntityPrinter  : Listing entities:
2023-02-07 09:45:07.336 DEBUG 83225 --- [           main] o.hibernate.internal.util.EntityPrinter  : app.demo.entity.Customer{firstName=Hieu, lastName=Nguyen, id=1, account=app.demo.entity.Account#1}
2023-02-07 09:45:07.336 DEBUG 83225 --- [           main] o.hibernate.internal.util.EntityPrinter  : app.demo.entity.Account{id=1, username=hieunv}
2023-02-07 09:45:07.341 DEBUG 83225 --- [           main] org.hibernate.SQL                        : delete from customer where id=?
Hibernate: delete from customer where id=?
2023-02-07 09:45:07.341 TRACE 83225 --- [           main] o.h.type.descriptor.sql.BasicBinder      : binding parameter [1] as [BIGINT] - [1]
2023-02-07 09:45:07.346 DEBUG 83225 --- [           main] org.hibernate.SQL                        : delete from account where id=?
Hibernate: delete from account where id=?
2023-02-07 09:45:07.346 TRACE 83225 --- [           main] o.h.type.descriptor.sql.BasicBinder      : binding parameter [1] as [BIGINT] - [1]
```

Chúng ta thấy rằng khi thực thể *Customer* bị xoá thì thực thể *Account* tương ứng được liên kết thông quan anh xạ *one-to-one* cũng bị xoá.

### Tổng kết

Chúng ta đã tiến hành cài đặt ánh xạ `one-to-one unidirectional`. Thực thể `Customer` có thể truy cập vào thực thể `Account` nhưng chúng ta không thể thực hiện ngược lại. Trong thực tế thì chúng ta cần truy cập được thực thể `Customer` từ thực thể `Account`. Do đó chúng ta cần đến anh xạ `one-to-one bidirectional`. Chúng ta cùng xem xét trong bài viết tiếp theo nhé.