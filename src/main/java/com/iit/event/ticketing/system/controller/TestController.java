package com.iit.event.ticketing.system.controller;

import com.iit.event.ticketing.system.entity.Customer;
import com.iit.event.ticketing.system.entity.Vendor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController {

  @GetMapping("/hello")
  public String sayHello() {
    Vendor vendor = new Vendor("V001", 10, 5);
    Thread thread = new Thread(vendor);
    thread.start();
    return "hello";
  }

  @GetMapping("/bye")
  public String sayBye() {
    Customer customer = new Customer("C001", 10);
    Thread thread = new Thread(customer);
    thread.start();
    return "bye";
  }
}
