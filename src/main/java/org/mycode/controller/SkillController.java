package org.mycode.controller;

import org.mycode.model.Skill;
import org.mycode.service.Serviceable;
import org.mycode.service.visitors.ServiceVisitor;
import org.mycode.service.visitors.VisitorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1")
public class SkillController {
    private Serviceable service;

    @Autowired
    public SkillController(@Qualifier("skillService") Serviceable service) {
        this.service = service;
    }

    @GetMapping("skills/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Skill> getById(@PathVariable Long id) {
        ServiceVisitor visitor = VisitorFactory.getVisitorByOperation(VisitorFactory.GET_BY_ID, id);
        service.doService(visitor);
        if (visitor.getResultData() != null && visitor.getResultData() instanceof Skill) {
            return new ResponseEntity<>((Skill) visitor.getResultData(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("skills")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<?>> getAll() {
        ServiceVisitor visitor = VisitorFactory.getVisitorByOperation(VisitorFactory.GET_ALL, null);
        service.doService(visitor);
        if (visitor.getResultData() != null && visitor.getResultData() instanceof List<?>) {
            return new ResponseEntity<>((List<?>) visitor.getResultData(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("skills")
    @ResponseStatus(code = HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public void create(@RequestBody Skill skill) {
        service.doService(VisitorFactory.getVisitorByOperation(VisitorFactory.CREATE, skill));
    }

    @PutMapping("skills")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void update(@RequestBody Skill skill) {
        service.doService(VisitorFactory.getVisitorByOperation(VisitorFactory.UPDATE, skill));
    }

    @DeleteMapping("skills/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        service.doService(VisitorFactory.getVisitorByOperation(VisitorFactory.DELETE, id));
    }
}
