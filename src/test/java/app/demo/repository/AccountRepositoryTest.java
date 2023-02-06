package app.demo.repository;

import app.demo.entity.Account;
import app.demo.entity.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class AccountRepositoryTest {

  @Autowired private AccountRepository accountRepository;

  @Autowired private CustomerRepository customerRepository;

  @Test
  void test() {
    var customer = Customer.builder().firstName("Hieu").lastName("Nguyen").build();
    var account = Account.builder().username("hieunv").build();
    customer.setAccount(account);

//    accountRepository.save(account);
    customerRepository.save(customer);
  }
}
