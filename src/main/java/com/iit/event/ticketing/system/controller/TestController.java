package com.iit.event.ticketing.system.controller;

import com.iit.event.ticketing.system.configuration.TicketingConfiguration;
import com.iit.event.ticketing.system.entity.Customer;
import com.iit.event.ticketing.system.entity.Vendor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
@Slf4j
public class TestController {

  private final TicketingConfiguration ticketingConfiguration;

  @GetMapping("/hello")
  public String sayHello() {
    log.info("say hello called");
    Vendor vendorobj1 = new Vendor("V001", 10, 5);
    Vendor vendorobj2 = new Vendor("V001", 10, 5);
    Thread thread1 = new Thread(vendorobj1);
    Thread thread2 = new Thread(vendorobj2);
    thread2.start();
    thread1.start();
    log.info("Thredding in progress");
    return "hello";

  }

  @GetMapping("/bye")
  public String sayBye() {
    log.info("say bye called");
    Customer customerobj1 = new Customer("C001", 10);
    Customer customerobj2 = new Customer("V001", 10);
    Thread thread3 = new Thread(customerobj1);
    Thread thread4 = new Thread(customerobj2);
    thread4.start();
    thread3.start();
    log.info("Thredding in progress");
    return "Bye";

  }


}
