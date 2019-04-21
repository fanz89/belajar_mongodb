package com.mif.controller;

import com.mif.exception.ResourceNotFoundException;
import com.mif.model.Customer;
import com.mif.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/customers")
public class UserController {

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("")
    public Page<Customer> findAll(Pageable pageable){
        return customerRepository.findAll(pageable);
    }

    @GetMapping("/name/{name}")
    public Page<Customer> findByName(@PathVariable(value = "name") String name, Pageable pageable){
        return customerRepository.findByFullnameContaining(name, pageable);
    }

    @GetMapping("{id}")
    public Customer findById(@PathVariable(value = "id") Long id){
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer tidak ditemukan dengan id : "+ id));
    }

    @PostMapping("")
    public ResponseEntity<?> insert(@Valid @RequestBody Customer customer){

        if(customerRepository.existsByCustomerId(customer.getCustomerId())){
            return new ResponseEntity<>("Customer Id sudah terdaftar!!", HttpStatus.CONFLICT);
        }

        if(customerRepository.existsByEmail(customer.getEmail())){
            return new ResponseEntity<>("Email sudah terdaftar!!", HttpStatus.CONFLICT);
        }

        return ResponseEntity.ok().body(customerRepository.save(customer));

    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") Long id, @Valid @RequestBody Customer customer){
        return customerRepository.findById(id)
                .map(obj -> {
                    obj.setFullname(customer.getFullname());
                    obj.setEmail(customer.getEmail());
                    obj.setPhoneNumber(customer.getPhoneNumber());
                    return ResponseEntity.ok().body(customerRepository.save(obj));
                }).map(updateCustomer -> new ResponseEntity<>(updateCustomer, HttpStatus.OK))
                .orElseThrow(() -> new ResourceNotFoundException("Customer tidak ditemukan dengan id : " + id + " dan nama : " + customer.getFullname()));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id){
        return customerRepository.findById(id)
                .map(customer -> {
                    customerRepository.delete(customer);
                    return ResponseEntity.ok().body("Customer berhasil dihapus dengan id : "+ id);
                }).orElseThrow(() -> new ResourceNotFoundException("Customer tidak ditemukan dengan id : " + id));
    }

}
