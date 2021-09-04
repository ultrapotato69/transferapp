package com.example.transferjdbc.repo.springDataJdbc;

import com.example.transferjdbc.domain.Transfer;
import com.example.transferjdbc.repo.TransferRepo;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface SpringDataJdbcTransferRepo extends CrudRepository<Transfer, Long>, TransferRepo {

    @Query("")
    Transfer transferMoney(Transfer transfer);

    @Query("select transfer_id, from_account_id, to_account_id, amount, comment, transfer_date " +
            "from transfer where from_account_id = :id or to_account_id = :id order by transfer_id")
    Iterable<Transfer> findAllTransfersById(@Param("id")Long from_account_id);
}
