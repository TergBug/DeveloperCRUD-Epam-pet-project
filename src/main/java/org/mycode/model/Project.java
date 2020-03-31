package org.mycode.model;

import org.hibernate.annotations.GenericGenerator;
import org.mycode.model.enums.ProjectStatus;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;
    private String name;
    @OneToMany
    @JoinColumn(name = "project_id")
    private Set<Developer> developers;
    @Enumerated(EnumType.STRING)
    private ProjectStatus status;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public Project() {
        this.id = UUID.randomUUID();
        this.name = "";
        this.developers = null;
        this.status = null;
        this.customer = null;
    }

    public Project(UUID id) {
        this.id = id;
        this.name = "";
        this.developers = null;
        this.status = null;
        this.customer = null;
    }

    public Project(String name, Set<Developer> developers, ProjectStatus status, Customer customer) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.developers = developers;
        this.status = status;
        this.customer = customer;
    }

    public Project(UUID id, String name, Set<Developer> developers, ProjectStatus status, Customer customer) {
        this.id = id;
        this.name = name;
        this.developers = developers;
        this.status = status;
        this.customer = customer;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Developer> getDevelopers() {
        return developers;
    }

    public void setDevelopers(Set<Developer> developers) {
        this.developers = developers;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                '}';
    }
}
