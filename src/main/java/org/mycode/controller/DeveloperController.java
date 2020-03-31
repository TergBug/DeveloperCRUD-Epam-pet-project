package org.mycode.controller;

import org.mycode.dto.DeveloperDto;
import org.mycode.service.DeveloperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "api/v1/developers")
public class DeveloperController {
    private DeveloperService service;

    @Autowired
    public DeveloperController(DeveloperService service) {
        this.service = service;
    }

    @GetMapping("{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<DeveloperDto> getById(@PathVariable String id) {
        return new ResponseEntity<>(service.getById(UUID.fromString(id.toLowerCase())), HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<DeveloperDto>> getAll() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public void create(@RequestBody DeveloperDto developer) {
        service.create(developer);
    }

    @PutMapping
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void update(@RequestBody DeveloperDto developer) {
        service.update(developer);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable String id) {
        service.delete(UUID.fromString(id.toLowerCase()));
    }
}
