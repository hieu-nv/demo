# demo

## `EditorConfig`

```
root = true

[*]
indent_style = space
indent_size = 2
ij_continuation_indent_size = 4
end_of_line = lf
charset = utf-8
trim_trailing_whitespace = true
insert_final_newline = true
```

## h2

```xml

<dependency>
  <groupId>com.h2database</groupId>
  <artifactId>h2</artifactId>
  <scope>runtime</scope>
</dependency>
```

### datasource

#### h2

```yaml
spring:
  h2:
    console:
      enabled: true
  datasource:
    url: jdbc:h2:mem:demo
    username: sa
    password: demo@123
    driverClassName: org.h2.Driver
```

#### MySQL

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/demo?createDatabaseIfNotExist=true
    username: root
    password: demo@123
    driverClassName: com.mysql.cj.jdbc.Driver
```

### flyway

```yaml

```

#

### docker

```bash
docker run --name demo -e MYSQL_ROOT_PASSWORD=demo@123 -p 3306:3306 -d mysql --lower_case_table_names=1
```


