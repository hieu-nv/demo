package app.demo;

import app.demo.entity.Account;
import app.demo.entity.Customer;
import app.demo.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
