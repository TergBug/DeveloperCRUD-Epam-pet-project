package org.mycode.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "skills")
public class Skill {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;
    @Column(unique = true)
    private String name;

    public Skill() {
        this.id = UUID.randomUUID();
        this.name = "";
    }

    public Skill(UUID id) {
        this.id = id;
        this.name = "";
    }

    public Skill(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }

    public Skill(UUID id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public String toString() {
        return "Skill{" +
                "id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Skill skill = (Skill) o;
        return Objects.equals(id, skill.id) &&
                Objects.equals(name, skill.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
