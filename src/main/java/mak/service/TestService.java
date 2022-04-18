package mak.service;

import mak.annotation.LogAround;
import mak.model.Employee;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    @LogAround(before = "beforeLog",after = "afterLog",argFields = {"address.city"})
    public Employee asdf(Employee employee) {
        return employee;
    }
}
