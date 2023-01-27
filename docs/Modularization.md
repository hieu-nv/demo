# Mô đun hoá theo tầng thay mô đun hoá theo tính năng?

Mô đun hoá là quá trình tách một hệ thống phần mềm thành nhiều mô đun. Ngoài việc giảm độ phức tạp, nó làm tăng tính dễ hiểu, khả năng bảo trì và khả năng sử dụng lại của hệ thống. Trong bài viết này sẽ đề cập đến hai phương pháp mô đun hoá (theo tầng và theo tính năng). Chúng ta nên chọn phương pháp nào và tại sao?

### Mô đun hoá theo tầng

Khi áp dụng kiến trúc phân tầng vào các dự án kiểu này, các *class* được đặt trong các *package* dựa theo tầng trong kiến trúc phân tầng mà chúng thuộc về. Phương pháp này làm giảm tính gắn kết (low cohesion) giữa các *class* bên trong các *package* bởi vì trong cùng một *package* có chứa các *class* không liên quan chặt chẽ với nhau. Dưới dây là một ví dụ áp dụng phương pháp mô đun hoá theo tầng.

```bash
├── src
│   ├── main
│   │   ├── java
│   │   │   └── app
│   │   │       └── demo
│   │   │           ├── DemoApplication.java
│   │   │           ├── controller
│   │   │           │   ├── OrderController.java
│   │   │           │   └── ProductController.java
│   │   │           ├── entity
│   │   │           │   ├── Order.java
│   │   │           │   └── Product.java
│   │   │           ├── repository
│   │   │           │   ├── OrderRepository.java
│   │   │           │   └── ProductRepository.java
│   │   │           ├── request
│   │   │           │   ├── OrderCreateRequest.java
│   │   │           │   └── ProductCreateRequest.java
│   │   │           ├── response
│   │   │           │   ├── OrderCreateResponse.java
│   │   │           │   └── ProductCreateResponse.java
│   │   │           └── service
│   │   │               ├── OrderService.java
│   │   │               ├── OrderServiceImpl.java
│   │   │               ├── ProductService.java
│   │   │               └── ProductServiceImpl.java
```

Ngoài ra khi kiểm tra cấu trúc của các dự án như trên chúng ta thấy rằng giữa các *package* có liên kết chặt chẽ với nhau (high coupling). Bởi vì các *class* ở tầng *Repository* được sử dụng trong các *class* ở tầng *Service* và các *class* ở tầng *Service* được sử dụng trong các *class* ở tầng *Controller*. Hơn nữa, mỗi khi có yêu cầu thay đổi chúng ta cần phải thay đổi ở nhiều *package* khác nhau.

> I felt like I had to understand everything in order to help with anything. <cite>Sandi Metz</cite>

Nghĩa là để giúp đỡ một việc nào đó thì chúng ta phải biết mọi thứ.

### *Cohesion* và *Coupling* nghĩa là gì?

- *Cohesion*(tính cố kết): Tính cố kết đề cập đến mức độ quan hệ logic giữa các *class* trong cùng *package* với nhau. Tính cố kết cao giữa các *class* đảm bảo tính độc lập của *package*. Tính cố kết thấp không chỉ giảm tính độc lập mà còn giảm đáng kể khả năng sử dụng lại và tính dễ hiểu.
  
- *Coupling*(Tính liên kết): Tính liên kết để cập đến mức độ phụ thuộc lẫn nhau giữa các *package*/*class*. Tính liên kết thấp làm tăng đáng kể khả năng bảo trì. Bởi vì những thay đổi được thực hiện bên trong *class* do yêu cầu thay đổi không ảnh hưởng đến các *class* khác, không có tác dụng phụ và việc bảo trì dễ dàng hơn.

Tính cố kết cao bên trong các *package* và tính phụ thuộc thấp giữa các *package* là thiết yếu đối với một hệ thống được thiết kế tốt. Một thiết kế tốt làm tăng đáng kể tính bền vững của hệ thống. Vậy thì làm thế để đạt được điều đó?

### Mô đun hoá theo tính năng



