# Sử dụng kiến trúc phân tầng trong ứng dụng Spring Boot

Trong bài viết này chúng ta sẽ cùng tìm hiểu [kiến trúc phân tầng](https://magz.techover.io/2023/01/04/kien-truc-phan-tang-layered-architecture-phan-1/) được ứng dụng như thế nào trong ứng dụng Spring Boot.

### Chúng ta nên sử dụng bao nhiêu tầng?

Trong kiến trúc phân tầng chúng ta không bị hạn chế về số tầng. Tuy nhiên, các dự án trong thực tế triển khai thường sử dụng 4 tầng. Các ứng dụng Spring Boot cũng có thể triển khai kiến trúc phân tầng với 4 tầng như sau:

- Tầng *Controller* là triển khai của tầng *Presentation*.
- Tầng *Service* là triển khai của tầng *Business*.
- Tầng *Repository* là triển khai của tầng *Persistence*.
- Tầng *Database* không được phản ánh trong mã nguồn của ứng dụng.

Do một ứng dụng có hoặc không cần sử dụng tới database. Khi đó có thể tầng *Repository* cũng không tồn tại. Trong thực tế chúng ta thường thấy các *Entity* được định nghĩa trong mã nguồn. Về mặt lí thuyết thì các *Entity* thuộc về tầng *Persistence*. Tuy nhiên các *Entity* chính là phản ánh của các bảng trong database. Do đó chúng ta có thể xem các *Entity* như là thể hiện của tầng *Database*.


### Kiến trúc phân tầng trong ứng dụng Spring Boot

Dưới đây là một ví dụ triển khai của kiến trúc phân tầng với ứng dụng Spring Boot:

```
├── src
│   ├── main
│   │   ├── java
│   │   │   └── app
│   │   │       └── demo
│   │   │           ├── DemoApplication.java
│   │   │           ├── controller
│   │   │           │   └── ProductController.java
│   │   │           ├── entity
│   │   │           │   └── Product.java
│   │   │           ├── repository
│   │   │           │   └── ProductRepository.java
│   │   │           ├── request
│   │   │           │   └── ProductCreateRequest.java
│   │   │           ├── response
│   │   │           │   └── ProductCreateResponse.java
│   │   │           └── service
│   │   │               ├── ProductService.java
│   │   │               └── ProductServiceImpl.java
```

Mỗi một request từ client sẽ lần lượt đi qua các tầng *Controller*, *Service*, *Repository* và kết thúc ở tầng *Database*. Trong ví dụ trên, bảng *Product* trong database sẽ được ánh xạ tương ứng với *Product* entity. Các thao tác tương tác với bảng *Product* sẽ được triển khai ở *ProductRepository*. Logic nghiệp vụ liên quan tới *Product* sẽ được cài đặt trong *ProductService*. *ProductController* sẽ là nơi tiếp nhận yêu cầu từ phía client.

Các yêu cầu từ phía client sẽ được tiếp nhận ở *ProductController*. Sau đó các thông tin nhận được sẽ được truyền tới *ProductService*. Tại đây các logic nghiệp vụ liên qua sẽ được xử lí trước khi được truyền tới *ProductRepository*. Cuối cùng dữ liệu sẽ được lưu trữ trong bảng *Product* của có sở dữ liệu.

### Tầng *Controller*
```java
/**
 * ProductController.
 *
 * @author Hieu Nguyen
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
  private final ProductService productService;

  @PostMapping
  public ResponseEntity<ProductCreateResponse> create(@RequestBody ProductCreateRequest request) {
    return ResponseEntity.ok().body(ProductCreateResponse.of(productService.create(request)));
  }
}
```

*ProductController* nhận dữ liệu từ client trong request body thông qua *ProductCreateRequest*. Sau đó nó truyền dữ liệu *ProductCreateRequest* xuống cho *ProductService*. Kết quả trả lại từ *ProductService* được chuyển thành *ProductCreateResponse* để trả lại client trong response body. *ProductCreateRequest* và *ProductCreateResponse* được cài đặt như sau:


```java
/**
 * ProductCreateRequest.
 *
 * @author Hieu Nguyen
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreateRequest {
  private String name;

}
```

```java
/**
 * ProductCreateResponse.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreateResponse {
  private Integer id;

  private String name;

  public static ProductCreateResponse of(Product product) {
    return ProductCreateResponse.builder().id(product.getId()).name(product.getName()).build();
  }
}
```

### Tầng *Service*

```java
/**
 * ProductRepository.
 *
 * @author Hieu Nguyen
 */
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
  private final ProductRepository productRepository;

  @Override
  @Transactional
  public Product create(ProductCreateRequest request) {
    return productRepository.save(Product.of(request));
  }
}
```

Tại tầng *Service*, *ProductService* nhận dữ liệu thông qua *ProductCreateRequest* được truyền xuống từ tầng *Controller*. Nó chuyển dữ liệu *ProductCreateRequest* vào *Product* entity, sau đó truyền dữ liệu *Product* entity xuống *ProductRepository* và cuối cùng dữ liệu được insert vào database.

### Tầng *Repository*

Trong ví dụ này chúng ta sử dụng *Spring Data JPA* và *Hibernate* để cài đặt tầng *Repository*. 

```java
/**
 * ProductRepository.
 *
 * @author Hieu Nguyen
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {}
```

Chúng ta ánh xạ bảng *Product* vào *Product* entity như sau:

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
  private Integer id;

  private String name;

  public static Product of(ProductCreateRequest request) {
    return Product.builder().name(request.getName()).build();
  }
}
```


### Sử dụng tầng đóng hay mở

Tất cả các tầng nên là đóng, nghĩa là với bất kì một tính năng nào chúng ta cần triển khai đủ 4 tầng: *Controller*, *Service*, *Repository*, *Database*. Tuy nhiên, trong qua trình sử dùng kiến trúc phân tầng sẽ có nhiều bạn đặt câu hỏi liệu có thực sự cần đến tầng *Service* không? Trên thực tế có nhiếu tính năng chúng ta sẽ không cần cài đặt mã nguồn ở tầng *Service*. Khi đó tẩng *Service* chỉ làm nhiệm vụ chuyển tiếp dữ liệu từ tầng *Controller* xuống tầng *Repository*. Tuy nhiên để đảm bảo không có những sai phạm không đáng có như việc cài đặt các logic nghiệp vụ ở tầng *Controller* sau đó gọi trực tiếp tới tầng *Repository* thì chúng ta nên triển khai tất cả các tầng đóng. Khi đó thì dù có hay không có logic nghiệp vụ ở tầng *Service* chúng ta vẫn nên triển khai tầng này.


### Tổng kết

Trong bài viết này chúng ta đã cùng tìm hiểu cách triển khai kiến trúc phân tầng trong một ứng dụng Spring Boot. Việc triển khai thực sự không khó, tuy nhiên để đảm bảo việc triển khai được thống nhất thì thực sự rất khó. Bài viết này hi vọng rằng có thể đem lại cái nhìn thống nhất giữa tất các các thành viên trong một dự án. Khi đó việc áp dụng kiến trúc phân tầng sẽ có hiệu quả hơn. 