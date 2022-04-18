package mak.controller;

import mak.model.Employee;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import mak.service.TestService;

@RestController
public class Test {

    TestService service;
    Test(TestService service){
        this.service = service;
    }

    @PostMapping
    public Employee employee(@RequestBody Employee employee){return service.asdf(employee);}

}
