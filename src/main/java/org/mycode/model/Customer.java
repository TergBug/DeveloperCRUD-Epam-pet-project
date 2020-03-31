package org.mycode.model;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;
    private String name;
    @OneToMany(mappedBy = "customer")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private Set<Project> projects;

    public Customer() {
        this.id = UUID.randomUUID();
        this.name = "";
        this.projects = new HashSet<>();
    }

    public Customer(UUID id) {
        this.id = id;
        this.name = "";
        this.projects = new HashSet<>();
    }

    public Customer(String name, Set<Project> projects) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.projects = projects;
    }

    public Customer(UUID id, String name, Set<Project> projects) {
        this.id = id;
        this.name = name;
        this.projects = projects;
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

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                '}';
    }
}
