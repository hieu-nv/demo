# Mô đun hoá theo tầng thay mô đun hoá theo tính năng?

Mô đun hoá là quá trình tách một hệ thống phần mềm thành nhiều mô đun. Ngoài việc giảm độ phức tạp, nó làm tăng tính dễ hiểu, khả năng bảo trì và khả năng sử dụng lại của hệ thống. Trong bài viết này sẽ đề cập đến hai phương pháp mô đun hoá (theo tầng và theo tính năng). Chúng ta nên chọn phương pháp nào và tại sao?

Trước khi đến với nội dung chính chúng ta cùng xem một số nội dung liên quan:
1. [KIẾN TRÚC PHÂN TẦNG (LAYERED ARCHITECTURE) (PHẦN 1)](https://magz.techover.io/2023/01/04/kien-truc-phan-tang-layered-architecture-phan-1/)
2. [KIẾN TRÚC PHÂN TẦNG (LAYERED ARCHITECTURE) (PHẦN 2)](https://magz.techover.io/2023/01/13/kien-truc-phan-tang-layered-architecture-phan-2/)
3. [ÁP DỤNG KIẾN TRÚC PHÂN TẦNG TRONG ỨNG DỤNG SPRING BOOT](https://magz.techover.io/2023/01/19/ap-dung-kien-truc-phan-tang-trong-ung-dung-spring-boot/)

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

> Để có thể giúp một việc nào đó, chúng ta cần phải biết mọi thứ.

### *Cohesion* và *Coupling* nghĩa là gì?

- *Cohesion*(tính cố kết): Tính cố kết đề cập đến mức độ quan hệ logic giữa các *class* trong cùng *package* với nhau. Tính cố kết cao giữa các *class* đảm bảo tính độc lập của *package*. Tính cố kết thấp không chỉ giảm tính độc lập mà còn giảm đáng kể khả năng sử dụng lại và tính dễ hiểu.
- *Coupling*(Tính liên kết): Tính liên kết để cập đến mức độ phụ thuộc lẫn nhau giữa các *package*/*class*. Tính liên kết thấp làm tăng đáng kể khả năng bảo trì. Bởi vì những thay đổi được thực hiện bên trong *class* do yêu cầu thay đổi không ảnh hưởng đến các *class* khác, không có tác dụng phụ và việc bảo trì dễ dàng hơn.

Tính cố kết cao bên trong các *package* và tính phụ thuộc thấp giữa các *package* là thiết yếu đối với một hệ thống được thiết kế tốt. Một thiết kế tốt làm tăng đáng kể tính bền vững của hệ thống. Vậy thì làm thế để đạt được điều đó?

### Mô đun hoá theo tính năng

Dưới đây là một ví dụ áp dụng phương pháp mô đun hoá theo tính năng.

```bash
├── src
│   ├── main
│   │   ├── java
│   │   │   └── app
│   │   │       └── demo
│   │   │           ├── DemoApplication.java
│   │   │           ├── domain
│   │   │           │   ├── order
│   │   │           │   │   └── create
│   │   │           │   │       ├── OrderCreateController.java
│   │   │           │   │       ├── OrderCreateRequest.java
│   │   │           │   │       ├── OrderCreateResponse.java
│   │   │           │   │       ├── OrderCreateService.java
│   │   │           │   │       └── OrderCreateServiceImpl.java
│   │   │           │   └── product
│   │   │           │       └── create
│   │   │           │           ├── ProductCreateController.java
│   │   │           │           ├── ProductCreateRequest.java
│   │   │           │           ├── ProductCreateResponse.java
│   │   │           │           ├── ProductCreateService.java
│   │   │           │           └── ProductCreateServiceImpl.java
│   │   │           ├── entity
│   │   │           │   ├── Order.java
│   │   │           │   └── Product.java
│   │   │           └── repository
│   │   │               ├── OrderRepository.java
│   │   │               └── ProductRepository.java
```

Trong cấu trúc dự án kiểu này, các *package* chứa tất cả các *class* được yêu cầu bởi một tính năng. Tính độc lập của *package* dượcd đảm bảo bằng cách đặt các *class* có liên quan chặt chẽ trong cùng một package.

Việc sử dụng một *class* bởi một *class* trong gói khác được loại bỏ ở cấu trúc này. Ngoài ra, các *class* trong cùng một *package* có liên quan chặt chẽ với nhau. Vì vậy tính cố kết cao trong cùng một *package* và tính liên kết thấp giữa các *package* được đảm bảo bởi cấu trúc này.

Hơn nữa, cấu trúc này làm tăng tính mô đun hoá. Giả sử rằng chúng ta có thêm 10 domain (ngoài *Product* và *Order*). Với phương pháp mô đun hoá theo tầng, các *class* sẽ được đặt trong các *package* *controller*, *service*, *repository*. Vì vậy toàn bộ ứng dụng sẽ bao gồm 3 *package* (ngoại trừ các *class* tiện ích), các *package* sẽ có số lượng lớn *class*. Tuy nhiên, trong phương pháp mô đun hoá theo tính năng, cùng ứng dụng đó sẽ bảo gồm 12 package tương ứng với 12 domain, tính mô đun hoá đã được tăng lên.

Trong ví dụ trên chúng ta thấy có 2 ngoại lệ, *repository* và *entity* package không được cấu trúc theo tính năng như bình thường. Với các *entity* và các *repository* được sử dụng ở nhiều *service* khác nhau, do chúng không là bắt buộc ở một tính năng cụ thể nào nên chúng ta cấu trúc chúng theo phương pháp mô đun theo tầng như bình thường. Với những *entity* và *repository* chỉ được sử dụng ở một tính năng cụ thể nào đó, chúng ta vẫn cấu trúc chúng theo phương pháp mô đun hoá theo tính năng như bình thường.


> Nếu một tính năng có thể được xoá bởi chỉ một hành động, ứng dụng đó có tính mô đun hoá cao nhất.


### Lợi ích của việc mô đun hoá theo tính năng

- Mô đun hoá theo tính năng tạo ra các *package* có tính cố kết cao, tính liên kết thấp và tính mô đun hoá cao.
- Mô đun hoá theo tính năng cho phép các *class* được khai báo với thuộc tính truy cập là `private` thay vì `public`, đo đó tăng tính đóng gói. Mặt khác mô đun hoá theo tầng buộc chúng ta phải đặt gần như toàn bộ các *class* là `public`.
- Mô đun hoá theo tính năng giúp giảm việc phải điều hướng giữa các *package* bởi vì các *class* cần thiết cho một tính năng được đặt trong cùng một *package*.
- Mô đun hoá theo tính năng giống như kiến trúc *microservice*. Mỗi *package* được giới hạn bởi các *class* liên quan với một tính năng cụ thể. Mặt khác, mô đun hoá theo tầng giống như kiến trúc nguyên khối. Khi một ứng dụng tăng kích thước, số *class* trong mỗi *package* sẽ tăng lên không giới hạn.

### Tổng kết

*Martin Fowler* gợi ý bắt đầu một dự án mới với kiến trúc *microservice* có thể không phải là một ý kiến hay. Nếu ứng dụng của chúng ta đạt mức tăng trưởng lớn và giới hạn của nó là chắc chắn, thì bạn nên chuyển sang kiến trúc *microservice*.

Hãy tưởng tượng tình huống trên, chúng ta đã quyết định tách các *microservice* từ ứng dụng nguyên khối. Giả sử rằng *microservice* sử dụng phương pháp mô đu hoá theo tính năng, vậy cấu trúc nào sẽ dễ dàng chuyển sang kiến trúc *microservice* hơn?

Câu trả lời cho câu hỏi này và các ưu điểm khác giúp chúng ta biết nên sử dụng phương pháp mô đun hoá nào.