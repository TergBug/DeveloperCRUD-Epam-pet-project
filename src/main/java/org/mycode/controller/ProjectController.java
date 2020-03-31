package org.mycode.controller;

import org.mycode.dto.ProjectDto;
import org.mycode.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "api/v1/projects")
public class ProjectController {
    private ProjectService service;

    @Autowired
    public ProjectController(ProjectService service) {
        this.service = service;
    }

    @GetMapping("{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ProjectDto> getById(@PathVariable String id) {
        return new ResponseEntity<>(service.getById(UUID.fromString(id.toLowerCase())), HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<ProjectDto>> getAll() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public void create(@RequestBody ProjectDto project) {
        service.create(project);
    }

    @PutMapping
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void update(@RequestBody ProjectDto project) {
        service.update(project);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable String id) {
        service.delete(UUID.fromString(id.toLowerCase()));
    }
}
