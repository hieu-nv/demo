Trong bài viết này chúng ta sẽ tìm về cách sử dụng Spring Boot cùng với Hibernate. Chúng ta sẽ tạo một ứng dụng Spring Boot đơn giản để minh hoạ cách tích hợp Spring Boot với Hibernate.

### Khởi tạo ứng dụng Spring Boot

Chúng ta sẽ sử dụng [Spring Initializr](https://start.spring.io/) để khởi tạo ứng dụng Spring Boot. Chúng ta cần thêm một số thư viện và cấu hình để tích hợp *Hibernate*, thêm các thư viện *Web*, *JPA*, *MySQL*. Bây giờ chúng ta cùng kiểm tra lại cấu trúc dự án đã được tạo ra và xác định các tệp cấu hình mà chúng ta sẽ cần.

Cấu trúc dự án sẽ giống như sau:

```bash
├── HELP.md
├── README.md
├── mvnw
├── mvnw.cmd
├── pom.xml
├── src
│   ├── main
│   │   ├── java
│   │   │   └── app
│   │   │       └── demo
│   │   │           └── DemoApplication.java
│   │   └── resources
│   │       ├── application.properties
│   │       ├── db
│   │       │   └── migration
│   │       ├── static
│   │       └── templates
│   └── test
│       ├── java
│       │   └── app
│       │       └── demo
│       │           └── DemoApplicationTests.java
│       └── resources
```

### MySQL

Các bạn tham khảo bài viết [HƯỚNG DẪN MIGRATE CƠ SỞ DỮ LIỆU SỬ DỤNG FLYWAY TRONG ỨNG DỤNG SPRING BOOT](https://magz.techover.io/2023/01/30/huong-dan-migrate-co-so-du-lieu-su-dung-flyway-trong-ung-dung-spring-boot/) để có thể tạo một cơ sở dữ liệu và cách tạo database schema cho ứng dụng của bạn.

### Thư viện *Maven*

Khi chúng ta mở tệp `pom.xml`, chúng ta sẽ thấy các thư viện maven *spring-boot-starter-web* và *spring-boot-starter-test*.

```xml
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
```

```xml
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-test</artifactId>
      <scope>test</scope>
    </dependency>
```

Đây là hai thư viện cần thiết khi chúng ta bắt đầu một dự án với *Spring Boot*.

Chúng ta cũng sẽ nhìn thấy thư viên *JPA* trong phần các thư viện phụ thuộc.

```xml
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
```
Thư viện này bao gồm các thư viện phụ thuộc *JPA API*, *JPA implementation*, *JDBC* và các thư viện cần thiết khác. Mặc định triển khai *JPA implementation* được sử dụng là *Hibernate*.

Tiếp theo chúng ta cũng sẽ thấy thư viện để tích hợp với *MySQL*.

```xml
    <dependency>
      <groupId>mysql</groupId>
      <artifactId>mysql-connector-java</artifactId>
      <scope>runtime</scope>
    </dependency>
```

### Cấu hình datasource

Mặc định *Spring Boot* sử dụng cấu hình datasource được cấu hình trong `application.properties` hoặc nếu bạn đang sử dụng `application.yml` datasource được cấu hình như sau:

```yml
  datasource:
    url: jdbc:mysql://localhost:3306/demo?createDatabaseIfNotExist=true
    schemas: "demo"
    username: root
    password: demo@123
    driverClassName: com.mysql.cj.jdbc.Driver
```

### Tạo và sử dụng *Entity*

Chúng ta tạo package `entity` và định nghĩa *JPA* entity:

```java
/**
 * Product.
 *
 * @author Hieu Nguyen
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

  @Id
  @GeneratedValue(generator = "Product")
  @TableGenerator(name = "Product", table = "hibernate_sequence")
  private Long id;

  private String name;

  public static Product of(ProductCreateRequest request) {
    return Product.builder().name(request.getName()).build();
  }
}
```

Sau đó chúng ta tạo *ProductRepository* trong *package* `repository`:

```java
/**
 * ProductRepository.
 *
 * @author Hieu Nguyen
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
```
Ở đây chúng ta sử dụng `JpaRepository` đã được cài đặt trong *Spring Data JPA*. Các bạn có thể tham khảo các phương thức của [JpaRepository](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html) trong tài liệu [này](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html). Trong ví dụ dưới đây chúng ta sẽ thử với phương thức `findById`.

Chúng ta cũng tạo *ProductRepositoryTest* để test *ProductRepository*:

```java
@SpringBootTest
class ProductRepositoryTest {

  @Autowired private ProductRepository productRepository;

  @Test
  void test() {
    Product product =
        productRepository.findById(1L).orElseThrow(EntityNotFoundException::new);
    assertThat(product.getId(), is(1L));
  }
}
```
Sau khi chạy thử test này chúng ta nhận được *output log* như sau:
```
Hibernate: select product0_.id as id1_1_0_, product0_.name as name2_1_0_ from product product0_ where product0_.id=?
```
Mặc định *Hibernate* sẽ query sử dụng tên bảng là tên *entity* ở dạng chữ viết thường.

### Xác định tên bảng

Trong ví dụ của chúng ta đang sử dụng *MySQL* với `--lower_case_table_names=1` nên sẽ không có vấn đề xảy ra. Trong trường hợp bạn sử dụng database cần phân biệt tên bảng viết thường và viết hoa hoặc trong trường hợp tên bảng và tên *entity* không giống nhau. Khi đó chúng ta cần xác định tên bảng tương ứng với *entity* như sau:

```java
/**
 * Product.
 *
 * @author Hieu Nguyen
 */
@Data
@Entity(name = "PRODUCT")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
  ...
}
```

### Tổng kết

Trong bài viết này chúng ta đã cũng tìm hiểu cách tìm hợp *Hibernate* vào ứng dụng *Spring Boot*. Chúng ta cũng đã test thử mã nguồn với database *MySQL*.
