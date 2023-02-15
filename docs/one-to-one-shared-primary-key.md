Các chương trình máy tính thể hiện các nhu cầu thực tế của con người, chúng ánh xạ các đối tượng trong thế giới thực thành các thực thể. Khi thực hiện quá trình ánh xạ đó, chúng ta thực hiện ánh xạ cả mối quan hệ giữa chúng. Trong bài viết này chúng ta đặt mối quan tâm tới các đối tượng có mối quan hệ 1-1 với nhau. Chúng ta sẽ cùng tìm hiểu cách chúng được thể hiện trong chương trình máy tính như thế nào.

### Các đối tượng trong thế giới thực được phản ánh trong chương trình máy tính như thế nào?

Trước tiên chúng ta thấy các đối tượng sẽ được ánh xạ tương ứng thành các *class* trong các ngôn ngữ lập trình. Khi chúng được lưu trữ vào *database*, chúng sẽ được ánh xạ thành các bản ghi của một bảng. Vậy thì mối quan hệ giữa chúng được định nghĩa như thế nào? Đối với các bảng trong *database*, các khoá trong bảng sẽ thể hiện mối quan hệ giữa các bảng. Đối với quan hệ 1-1 chúng ta có thể định nghĩa theo 2 cách:

- Sử dụng khoá ngoại duy nhất (một cột được đánh dấu là khoá ngoại và nó cũng là duy nhất trong bảng đó).
- Hai bảng cùng chia sẻ khoá chính.

### Các đối tượng được phản ánh thành các bảng trong *database*

Chúng ta cùng xem xét các thực thể được phản ánh thành các bảng trong *database* thông qua một vài ví dụ các bảng được thiết kế trong *database* như thế nào. Đối với cách sử dụng khoá ngoại duy nhất, các bảng có thể được định nghĩa như sau:

![](https://i0.wp.com/s3.ap-southeast-1.amazonaws.com/techover.storage/wp-content/uploads/2023/02/06144042/one-to-one.png?w=1170&ssl=1)

Trong trường hợp bạn sử dụng cách chia sẻ khoá chính giữa hai bảng, các bảng trong *database* có thể được định nghĩa như sau:

![](https://s3.ap-southeast-1.amazonaws.com/techover.storage/wp-content/uploads/2023/02/15182541/shared-primary-key.png)

### Các đối tượng được ánh xạ thành các *class* như thế nào?

Đối với cách sử dụng khoá ngoại duy nhất, chúng ta có thể tham khảo cách định nghĩa mối quan hệ của chúng trong Spring Boot qua các bài viết sau:

- [Ánh xạ one-to-one unidirectional trong Hibernate sử dụng khoá ngoại](https://magz.techover.io/2023/02/06/anh-xa-one-to-one-unidirectional-trong-hibernate-su-dung-khoa-ngoai/)
- [Ánh xạ one-to-one bidirectional trong Hibernate sử dụng khoá ngoại](https://magz.techover.io/2023/02/10/anh-xa-one-to-one-bidirectional-trong-hibernate-su-dung-khoa-ngoai/)


Trường hợp bạn sử dụng cách chia sẻ khoá chính giữa hai bảng chúng ta tìm hiểu qua từng bước dưới đây.

### Định nghĩa các bảng trong *database*

Với ví dụ ở trên các bạn có thể sử dụng đoạn mã sau để tạo ra các bảng:

```SQL
CREATE TABLE IF NOT EXISTS `user` (
  `id` BIGINT NOT NULL AUTO_INCREMENT
  , `username` VARCHAR(255) UNIQUE
  , `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP
  , `created_by` BIGINT DEFAULT NULL
  , `updated_at` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP
  , `updated_by` BIGINT DEFAULT NULL
  , `deleted_at` DATETIME DEFAULT NULL
  , `deleted_by` BIGINT DEFAULT NULL
  , PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `user_info` (
  `user_id` BIGINT NOT NULL
  , `first_name` VARCHAR(255)
  , `last_name` VARCHAR(255)
  , PRIMARY KEY (`user_id`)
  , FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
);
```

Chúng ta có thể tham khảo cách *migrate* các bảng này bằng cách sử dụng *Flyway* trong bài viết [Hướng dẫn migrate cơ sở dữ liệu sử dụng Flyway trong ứng dụng Spring Boot
](https://magz.techover.io/2023/01/30/huong-dan-migrate-co-so-du-lieu-su-dung-flyway-trong-ung-dung-spring-boot/).


### Định nghĩa các *entity* để ánh xạ các bảng với các *class*

Tiếp theo chúng ta cần định nghĩa các *entity* thành các *class* tương ứng. Từ đó, chúng ta có thể thực hiện các thao tác *CRUD* hoặc các thao tác truy vấn trên các bảng tương ứng.


```java
/**
 * <code>user_info</code>.
 *
 * @author Hieu Nguyen
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "user_info")
public class UserInfo {

  /** <code>user_id</code>. */
  @Id
  private Long userId;

  /** <code>first_name</code>. */
  private String firstName;

  /** <code>last_name</code>. */
  private String lastName;

  @MapsId
  @ToString.Exclude
  @PrimaryKeyJoinColumn
  @Fetch(FetchMode.JOIN)
  @OneToOne(cascade = CascadeType.PERSIST, optional = false, fetch = FetchType.EAGER)
  private User user;
}
```


```java
/**
 * <code>user</code>.
 *
 *
 * @author Hieu Nguyen
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "user")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {

  /** <code>id</code>. */
  @Id
  @EqualsAndHashCode.Include
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /** <code>username</code>. */
  private String username;

  /** <code>created_at</code>. */
  private LocalDateTime createdAt;

  /** <code>created_by</code>. */
  private Long createdBy;

  /** <code>updated_at</code>. */
  private LocalDateTime updatedAt;

  /** <code>updated_by</code>. */
  private Long updatedBy;

  /** <code>deleted_at</code>. */
  private LocalDateTime deletedAt;

  /** <code>deleted_by</code>. */
  private Long deletedBy;

  /** <code>user_info.user_id</code> */
  @ToString.Exclude
  @OneToOne(mappedBy = "user", fetch = FetchType.EAGER)
  private UserInfo userInfo;
}
```

Chúng ta sử `@Id` để đánh dấu thuộc tính được ánh xạ tương ứng với trường khoá chính của bảng. Trong ví dụ này, chúng ta sử dụng trường tự tăng để sinh ra khoá chính cho bảng, do đó chúng ta sử dụng `@GeneratedValue(strategy = GenerationType.IDENTITY)` để thông báo với *Hibernate* rằng trường này sẽ được tự sinh trong *database*.

Tiếp theo là phần quan trọng nhất, chúng ta sử dụng `@MapsId` để đánh dấu thuốc tính định nghĩa mối quan hệ 1-1 cùng với `@OneToOne` để xác định thực thể trong bảng có quan hệ 1-1 tương ứng. `@MapsId` sẽ thông báo cho *Hibernate* biết rằng chúng ta đang sử dụng khoá chính làm trường để thực hiện phép *JOIN*.

Tiếp đến để thực hiện ánh xạ quan hệ 1-1 hai chiều, chúng ta sử dụng `@OneToOne(mappedBy = "user", fetch = FetchType.EAGER)` để đánh dấu thuộc tính ánh xạ sang thực thể nguồn đã được định nghĩa ở trên. Thuộc tính `mappedBy` chính là tên thuộc tính được khai báo với `@MapsId` ở trên.

### Xác nhận việc định nghĩa quan hệ 1-1

Tiếp theo chúng ta cùng viết một đoạn chương trình nhỏ để kiểm tra lại các bước đã thực hiện ở trên.

```java
/**
 * Main.
 *
 * @author Hieu Nguyen
 */
@Component
@RequiredArgsConstructor
public class Main implements CommandLineRunner {

  private final UserRepository userRepository;

  @Override
  @Transactional
  public void run(String... args) throws Exception {
    var uuid = UUID.randomUUID();
    var userInfo = UserInfo.builder().firstName("Hieu-" + uuid).lastName("Nguyen-" + uuid).build();
    var user = User.builder().username("hieunv-" + UUID.randomUUID()).userInfo(userInfo).build();
    userInfo.setUser(user);
    userRepository.save(user);
  }
}
```

Chạy thử đoạn chương trình này chúng ta sẽ nhận được output như sau:

```
2023-02-15 18:56:38.262 DEBUG 25263 --- [           main] org.hibernate.SQL                        : 
    insert 
    into
        user
        (created_at, created_by, deleted_at, deleted_by, passport_id, updated_at, updated_by, username) 
    values
        (?, ?, ?, ?, ?, ?, ?, ?)
Hibernate: 
    insert 
    into
        user
        (created_at, created_by, deleted_at, deleted_by, passport_id, updated_at, updated_by, username) 
    values
        (?, ?, ?, ?, ?, ?, ?, ?)
2023-02-15 18:56:38.264 TRACE 25263 --- [           main] o.h.type.descriptor.sql.BasicBinder      : binding parameter [1] as [TIMESTAMP] - [null]
2023-02-15 18:56:38.264 TRACE 25263 --- [           main] o.h.type.descriptor.sql.BasicBinder      : binding parameter [2] as [BIGINT] - [null]
2023-02-15 18:56:38.264 TRACE 25263 --- [           main] o.h.type.descriptor.sql.BasicBinder      : binding parameter [3] as [TIMESTAMP] - [null]
2023-02-15 18:56:38.264 TRACE 25263 --- [           main] o.h.type.descriptor.sql.BasicBinder      : binding parameter [4] as [BIGINT] - [null]
2023-02-15 18:56:38.264 TRACE 25263 --- [           main] o.h.type.descriptor.sql.BasicBinder      : binding parameter [5] as [BIGINT] - [null]
2023-02-15 18:56:38.264 TRACE 25263 --- [           main] o.h.type.descriptor.sql.BasicBinder      : binding parameter [6] as [TIMESTAMP] - [null]
2023-02-15 18:56:38.264 TRACE 25263 --- [           main] o.h.type.descriptor.sql.BasicBinder      : binding parameter [7] as [BIGINT] - [null]
2023-02-15 18:56:38.264 TRACE 25263 --- [           main] o.h.type.descriptor.sql.BasicBinder      : binding parameter [8] as [VARCHAR] - [hieunv-48779f8f-4efe-4d15-b658-92ebd3f7d9a3]
2023-02-15 18:56:38.278 DEBUG 25263 --- [           main] org.hibernate.SQL                        : 
    insert 
    into
        user_info
        (first_name, last_name, user_id) 
    values
        (?, ?, ?)
Hibernate: 
    insert 
    into
        user_info
        (first_name, last_name, user_id) 
    values
        (?, ?, ?)
2023-02-15 18:56:38.278 TRACE 25263 --- [           main] o.h.type.descriptor.sql.BasicBinder      : binding parameter [1] as [VARCHAR] - [Hieu-3c8726d4-0d3f-49f6-ba05-819bbb428863]
2023-02-15 18:56:38.278 TRACE 25263 --- [           main] o.h.type.descriptor.sql.BasicBinder      : binding parameter [2] as [VARCHAR] - [Nguyen-3c8726d4-0d3f-49f6-ba05-819bbb428863]
2023-02-15 18:56:38.278 TRACE 25263 --- [           main] o.h.type.descriptor.sql.BasicBinder      : binding parameter [3] as [BIGINT] - [9]
```

Chúng ta thấy rằng có 2 bản ghi đã được *insert* vào 2 bảng chúng ta đã định nghĩa ở trên.

### Tổng kết

Trong bài viết này chúng ta đã cùng đi từ các khái niệm cơ bản liên quan đến quan hệ 1-1 cũng như cách triển khai quan hệ này với *MySQL* database. Sau đó chúng ta cũng viết một ứng dụng đơn giản bằng *Spring Boot* để minh hoạ cơ chế hoạt động của quan hệ này.