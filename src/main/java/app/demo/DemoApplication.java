package app.demo;

import app.demo.repository.AuthorRepository;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * DemoApplication.
 *
 * @author Hieu Nguyen
 */
@SpringBootApplication
@MapperScan(basePackages = {"app.demo.mybatis.repository"})
public class DemoApplication {

  @Autowired AuthorRepository authorRepository;

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }
}
