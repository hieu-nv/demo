
Với hầu hết các dự án việc quản lý các phiên bản database schema là vô cùng quan trọng. Phương pháp thực hiện *migrate* database tốt sẽ giúp tất cả thành viên dự án dễ dàng đồng bộ môi trường phát triển cũng như là triển khai database schema lên các môi trường khác nhau. Trong bài viết này chúng ta sẽ cùng tìm hiểu phương pháp *migrate* database schema sử dụng *Flyway* trong ứng dụng Spring Boot.

### Khởi tạo ứng dụng Spring Boot

Chúng ta sẽ sử dụng [Spring Initializr](https://start.spring.io/) để khởi tạo ứng dụng Spring Boot. Cấu trúc dự án sẽ giống như sau:

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
│   │       │       └── V20221124103000__Initial.sql
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

Chúng ta sẽ sử dụng *docker* để *container* chạy *MySQL*.

```bash
docker run --name demo -e MYSQL_ROOT_PASSWORD=demo@123 -p 3306:3306 -d mysql --lower_case_table_names=1
```

Chúng ta có thể sử dung *MySQL Workbench* để kiểm tra database MySQL đã chạy hay chưa.

![](https://s3.ap-southeast-1.amazonaws.com/techover.storage/wp-content/uploads/2023/01/30123811/MicrosoftTeams-image-5.png)

![](https://s3.ap-southeast-1.amazonaws.com/techover.storage/wp-content/uploads/2023/01/30124038/MicrosoftTeams-image-6.png)


### Thư viện *Maven*

Để sử dụng *Flyway* chúng ta thêm thư viện phụ thuộc sau:

```xml
    <dependency>
      <groupId>org.flywaydb</groupId>
      <artifactId>flyway-mysql</artifactId>
    </dependency>
```

Trong bài viết này chúng ta sẽ dùng *Maven plugin* để thực hiện migrate database, chúng ta cần cấu hình *plugin* này trong `pom.xml` như sau:

```xml
      <plugin>
        <groupId>org.flywaydb</groupId>
        <artifactId>flyway-maven-plugin</artifactId>
        <configuration>
          <url>jdbc:mysql://localhost:3306/demo?createDatabaseIfNotExist=true</url>
          <user>root</user>
          <password>demo@123</password>
        </configuration>
      </plugin>
```

### Định nghĩa phiên bản migration

Mặc định *Flyway* sẽ đọc các tệp *SQL* trong thư mục `resources/db/migration`. Tên tệp được định nghĩa theo tài liệu [Versioned Migrations](https://flywaydb.org/documentation/concepts/migrations.html#versioned-migrations).

Mỗi phiên bản migration có phiên bản, thông tin mô tả phiên bản và *checksum*. Các phiên bản phải là duy nhất. Thông tin phiên bản là thông tin thuần khiết gợi nhớ cái gì được thực hiện trong phiên bản đó. *Checksum* được sử dụng để phát hiện các thay đổi ngẫu nhiên. Các phiên bản migration được áp dụng theo một thứ tự nhất định. Các định dạng phiên bản có thể sử dụng như sau:

- 1
- 001
- 5.2
- 1.2.3.4.5.6.7.8.9
- 205.68
- 20130115113556
- 2013.1.15.11.35.56
- 2013.01.15.11.35.56


Trong nội dung bài viết này chúng ta định nghĩa một phiên bản `V20221124103000__Initial.sql` với nội dung như sau:

```sql
CREATE TABLE IF NOT EXISTS hibernate_sequence (
  sequence_name VARCHAR(128) NOT NULL,
  next_val INT NOT NULL
);

DROP TABLE IF EXISTS PRODUCT;

CREATE TABLE PRODUCT (
  ID BIGINT PRIMARY KEY AUTO_INCREMENT
  , NAME VARCHAR(255)
  , CREATED_AT DATETIME DEFAULT CURRENT_TIMESTAMP
  , UPDATED_AT DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP
  , DELETED_AT DATETIME DEFAULT NULL
  , CONSTRAINT uk_product_name UNIQUE(name)
);

DROP TABLE IF EXISTS `ORDER`;

CREATE TABLE `ORDER` (
  ID BIGINT PRIMARY KEY AUTO_INCREMENT
  , NAME VARCHAR(255)
  , CREATED_AT DATETIME DEFAULT CURRENT_TIMESTAMP
  , UPDATED_AT DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP
  , DELETED_AT DATETIME DEFAULT NULL
  , CONSTRAINT uk_order_name UNIQUE(NAME)
);
```


### Thực thi migration

Để tiến hành migrate database schema chúng ta thực thi lệnh `./mvnw flyway:migrate`.

```
➜  demo git:(main) ✗ ./mvnw flyway:migrate
[INFO] Scanning for projects...
[INFO] 
[INFO] ------------------------------< app:demo >------------------------------
[INFO] Building demo 0.0.1-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
[WARNING] The artifact mysql:mysql-connector-java:jar:8.0.31 has been relocated to com.mysql:mysql-connector-j:jar:8.0.31: MySQL Connector/J artifacts moved to reverse-DNS compliant Maven 2+ coordinates.
[INFO] 
[INFO] --- flyway-maven-plugin:8.5.13:migrate (default-cli) @ demo ---
[INFO] Flyway Community Edition 8.5.13 by Redgate
[INFO] See what's new here: https://flywaydb.org/documentation/learnmore/releaseNotes#8.5.13
[INFO] 
[INFO] Database: jdbc:mysql://localhost:3306/demo (MySQL 8.0)
[INFO] Successfully validated 1 migration (execution time 00:00.013s)
[INFO] Creating Schema History table `demo`.`flyway_schema_history` ...
[INFO] Current version of schema `demo`: << Empty Schema >>
[INFO] Migrating schema `demo` to version "20221124103000 - Initial"
[WARNING] DB: Unknown table 'demo.product' (SQL State: 42S02 - Error Code: 1051)
[WARNING] DB: Unknown table 'demo.order' (SQL State: 42S02 - Error Code: 1051)
[INFO] Successfully applied 1 migration to schema `demo`, now at version v20221124103000 (execution time 00:00.100s)
[INFO] Flyway Community Edition 8.5.13 by Redgate
[INFO] See what's new here: https://flywaydb.org/documentation/learnmore/releaseNotes#8.5.13
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  1.044 s
[INFO] Finished at: 2023-01-30T14:41:40+07:00
[INFO] ------------------------------------------------------------------------
```

Trong trường hợp thực thi `migrate` có lỗi. Chúng ta có thể thực hiện lệnh `./mvnw flyway:repair` trước khi thực thi lại lệnh `migrate`.

```
➜  demo git:(main) ✗ ./mvnw flyway:repair
[INFO] Scanning for projects...
[INFO] 
[INFO] ------------------------------< app:demo >------------------------------
[INFO] Building demo 0.0.1-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
[WARNING] The artifact mysql:mysql-connector-java:jar:8.0.31 has been relocated to com.mysql:mysql-connector-j:jar:8.0.31: MySQL Connector/J artifacts moved to reverse-DNS compliant Maven 2+ coordinates.
[INFO] 
[INFO] --- flyway-maven-plugin:8.5.13:repair (default-cli) @ demo ---
[INFO] Flyway Community Edition 8.5.13 by Redgate
[INFO] See what's new here: https://flywaydb.org/documentation/learnmore/releaseNotes#8.5.13
[INFO] 
[INFO] Database: jdbc:mysql://localhost:3306/demo (MySQL 8.0)
[INFO] Repair of failed migration in Schema History table `demo`.`flyway_schema_history` not necessary. No failed migration detected.
[INFO] Successfully repaired schema history table `demo`.`flyway_schema_history` (execution time 00:00.030s).
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  0.908 s
[INFO] Finished at: 2023-01-30T14:44:31+07:00
[INFO] ------------------------------------------------------------------------
```


Để tiến thành clean database schema chúng ta thực thi lệnh `./mvnw flyway:clean`.

```
➜  demo git:(main) ✗ ./mvnw flyway:clean
[INFO] Scanning for projects...
[INFO] 
[INFO] ------------------------------< app:demo >------------------------------
[INFO] Building demo 0.0.1-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
[WARNING] The artifact mysql:mysql-connector-java:jar:8.0.31 has been relocated to com.mysql:mysql-connector-j:jar:8.0.31: MySQL Connector/J artifacts moved to reverse-DNS compliant Maven 2+ coordinates.
[INFO] 
[INFO] --- flyway-maven-plugin:8.5.13:clean (default-cli) @ demo ---
[INFO] Flyway Community Edition 8.5.13 by Redgate
[INFO] See what's new here: https://flywaydb.org/documentation/learnmore/releaseNotes#8.5.13
[INFO] 
[INFO] Database: jdbc:mysql://localhost:3306/demo (MySQL 8.0)
[INFO] Successfully dropped pre-schema database level objects (execution time 00:00.002s)
[INFO] Successfully cleaned schema `demo` (execution time 00:00.009s)
[INFO] Successfully cleaned schema `demo` (execution time 00:00.008s)
[INFO] Successfully dropped post-schema database level objects (execution time 00:00.002s)
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  0.861 s
[INFO] Finished at: 2023-01-30T14:39:23+07:00
[INFO] ------------------------------------------------------------------------
```

### Tổng kết

Trong bài viết này chúng ta đã tìm hiểu cách tạo một instance *MySQL* sử dụng *docker* và tạo và quản lý các phiên bản database schema sử dụng *Flyway*.

