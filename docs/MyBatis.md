### [MyBatis](https://mybatis.org/mybatis-3/index.html) là gì?

*MyBatis* là một framework nổi tiếng trong cộng đồng Java. Nó là triển khai của tầng lưu trữ trong kiến trúc phân tầng trên nền tảng Java tương tự như Hibernate hoặc ngay cả là JDBC thuần. Nó giúp việc triển khai tầng lưu trữ trở nên đơn giản hơn với nhà phát triển. Thông tin về [MyBatis](https://mybatis.org/mybatis-3/index.html) bạn có thể tham khảo tại [đây](https://mybatis.org/mybatis-3/index.html). Nội dung bài viết này tập trung vào cách sử dụng *MyBatis* cùng với *Spring Boot*.

### [MyBatis-Spring-Boot-Starter](https://mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/index.html)

Để sử dụng MyBatis với Spring Boot chúng ta sử dụng thư viện [MyBatis-Spring-Boot-Starter](https://mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/index.html). Đây là thư viện hỗ trợ cấu hình nhanh chóng một ứng dụng Spring Boot có sử dụng MyBatis. Thư viện này được hỗ trợ chính thức từ nhóm phát triển MyBatis. Các bước cấu hình tại [đây](https://mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/index.html#installation).

### `Repository` hay `Mapper`

`Mapper` là một Java interface mà các phương thức được ánh xạ tới các truy vấn SQL tương ứng. Mặc định MyBatis sẽ ánh xạ các method được định nghĩa trong các interface được đánh dấu với annotation `@Mapper` tới các truy vấn SQL tương ứng.

### `ProductRepository`

Dưới đây là một ví dụ về `Mapper`. `ProductRepository` được định nghĩa trong package `app.demo.mybatis.repository`

```java

@Mapper
public interface ProductRepository {

  void create(Product product);
}
```

Để định nghĩa truy vấn SQL tương ứng chúng ta tạo `ProductRepository.xml` trong thư mục `app.demo.mybatis.repository` bên trong `resources`.

```xml
<?xml version = "1.0" encoding = "UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
    "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="app.demo.mybatis.repository.ProductRepository">
  <insert id="create" parameterType="app.demo.mybatis.entity.Product">
    <![CDATA[
      INSERT INTO product (
        ID
        , NAME
      ) VALUES (
        #{id}
        , #{name}
      );
    ]]>
  </insert>
</mapper>
```

Trong ví dụ trên chúng ta đang ánh xạ phương thức `create` với câu truy vấn `INSERT`. Khi phương thức `create` được gọi thì câu truy vấn sẽ được thực thi. Kết quả câu truy vấn sẽ được trả về phương thức `create`.

### DataSource

Trong bài viết này chúng ta sẽ sử dụng MySQL để thực hành với MyBatis. Chúng ta có thể sử dụng docker để tạo container chạy MySQL với câu lệnh sau:

```bash
docker run --name demo -e MYSQL_ROOT_PASSWORD=demo@123 -p 3306:3306 -d mysql --lower_case_table_names=1
```

Với câu lệnh trên chúng ta đã tạo xong MySQL với schema `demo` cũng như mật khẩu cho tài khoản `root` là `demo@123`.

Để cấu hình data source với Spring Boot chúng ta thêm các cấu hình sau trong `application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/demo?createDatabaseIfNotExist=true
    username: root
    password: demo@123
    driverClassName: com.mysql.cj.jdbc.Driver
```

### `BindingException`

```
org.apache.ibatis.binding.BindingException: Invalid bound statement (not found)
```

Lỗi này xuất hiện khi chưa cấu hình thuộc tính `mybatis.mapper-locations`. Cấu hình thuộc tính này trong `application.yml` hoặc `application.properties` như sau:

```yaml
mybatis:
  mapper-locations: "classpath:app.demo.mybatis.repository/*.xml"
```

```properties
mybatis.mapper-locations=classpath:app.demo.mybatis.repository/*.xml
```

### Tổng kết

Trong bài viết này tôi đã hướng dẫn các bạn các bước cơ bản để tạo một ứng dụng Spring Boot có sử dụng MyBatis. Hi vọng bài viết sẽ giúp ích cho các bạn mới bắt đầu tiếp cận với MyBatis cũng như là cung cấp một hướng dẫn khi bắt đầu xây dựng một dự án.

