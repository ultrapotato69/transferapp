package com.example.transferjdbc.repo.springDataJdbc;

import com.example.transferjdbc.domain.Account;
import com.example.transferjdbc.repo.AccountRepo;
import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
//@Primary
public interface SpringDataJdbcAccountRepo extends CrudRepository<Account, Long>, AccountRepo {

}
