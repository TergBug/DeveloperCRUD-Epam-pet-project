package org.mycode.controller;

import org.mycode.model.Account;
import org.mycode.service.Serviceable;
import org.mycode.service.visitors.ServiceVisitor;
import org.mycode.service.visitors.VisitorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1")
public class AccountController {
    private Serviceable service;

    @Autowired
    public AccountController(@Qualifier("accountService") Serviceable service) {
        this.service = service;
    }

    @GetMapping("accounts/{id}")
    public ResponseEntity<Account> getById(@PathVariable Long id) {
        ServiceVisitor visitor = VisitorFactory.getVisitorByOperation(VisitorFactory.GET_BY_ID, id);
        service.doService(visitor);
        if (visitor.getResultData() != null && visitor.getResultData() instanceof Account) {
            return new ResponseEntity<>((Account) visitor.getResultData(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("accounts")
    public ResponseEntity<List<?>> getAll() {
        ServiceVisitor visitor = VisitorFactory.getVisitorByOperation(VisitorFactory.GET_ALL, null);
        service.doService(visitor);
        if (visitor.getResultData() != null && visitor.getResultData() instanceof List<?>) {
            return new ResponseEntity<>((List<?>) visitor.getResultData(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("accounts")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void create(@RequestBody Account account) {
        service.doService(VisitorFactory.getVisitorByOperation(VisitorFactory.CREATE, account));
    }

    @PutMapping("accounts")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void update(@RequestBody Account account) {
        service.doService(VisitorFactory.getVisitorByOperation(VisitorFactory.UPDATE, account));
    }

    @DeleteMapping("accounts/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.doService(VisitorFactory.getVisitorByOperation(VisitorFactory.DELETE, id));
    }
}
