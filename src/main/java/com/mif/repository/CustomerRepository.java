package com.mif.repository;

import com.mif.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, Long> {

    boolean existsByCustomerId(Long id);

    boolean existsByEmail(String email);

    // $regex : ?0  = accepting input name as the parameter
    // $option: 'i' = to ignore the case
    @Query(value = "{'fullname': {$regex: ?0, $options: 'i'}}")
    Page<Customer> findByFullnameContaining(String fullname, Pageable pageable);

}
