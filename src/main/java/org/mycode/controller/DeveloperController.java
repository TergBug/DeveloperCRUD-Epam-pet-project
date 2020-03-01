package org.mycode.controller;

import org.mycode.model.Developer;
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
public class DeveloperController {
    private Serviceable service;

    @Autowired
    public DeveloperController(@Qualifier("developerService") Serviceable service) {
        this.service = service;
    }

    @GetMapping("developers/{id}")
    public ResponseEntity<Developer> getById(@PathVariable Long id) {
        ServiceVisitor visitor = VisitorFactory.getVisitorByOperation(VisitorFactory.GET_BY_ID, id);
        service.doService(visitor);
        if (visitor.getResultData() != null && visitor.getResultData() instanceof Developer) {
            return new ResponseEntity<>((Developer) visitor.getResultData(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("developers")
    public ResponseEntity<List<?>> getAll() {
        ServiceVisitor visitor = VisitorFactory.getVisitorByOperation(VisitorFactory.GET_ALL, null);
        service.doService(visitor);
        if (visitor.getResultData() != null && visitor.getResultData() instanceof List<?>) {
            return new ResponseEntity<>((List<?>) visitor.getResultData(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("developers")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void create(@RequestBody Developer developer) {
        service.doService(VisitorFactory.getVisitorByOperation(VisitorFactory.CREATE, developer));
    }

    @PutMapping("developers")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void update(@RequestBody Developer developer) {
        service.doService(VisitorFactory.getVisitorByOperation(VisitorFactory.UPDATE, developer));
    }

    @DeleteMapping("developers/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.doService(VisitorFactory.getVisitorByOperation(VisitorFactory.DELETE, id));
    }
}
