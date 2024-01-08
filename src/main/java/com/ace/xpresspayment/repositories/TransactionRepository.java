package com.ace.xpresspayment.repositories;

import com.ace.xpresspayment.models.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {
    @Query(name = "select * from transactions where transactionReference =?1")
    Transaction findByTransactionReference(String transactionReference);

    Page<Transaction> findAllByUserId(long userId, Pageable pageable);
}
