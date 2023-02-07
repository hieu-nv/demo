package app.demo;

import app.demo.entity.Account;
import app.demo.entity.Customer;
import app.demo.repository.AccountRepository;
import app.demo.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
public class Main implements CommandLineRunner {
  @Autowired private AccountRepository accountRepository;
  @Autowired private CustomerRepository customerRepository;

  @Override
  @Transactional
  public void run(String... args) throws Exception {
//    var customer = Customer.builder().firstName("Hieu").lastName("Nguyen").build();
//    var account = Account.builder().username("hieunv").customer(customer).build();
//    customer.setAccount(account);
//    accountRepository.save(account);

    //    var customer = customerRepository.findById(1L).orElseThrow(() -> new
    // EntityNotFoundException());
    //    customerRepository.delete(customer);

    log.info("{}", accountRepository.findById(2L).get().getCustomer());
  }
}
