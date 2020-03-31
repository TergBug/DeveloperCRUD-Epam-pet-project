package org.mycode.model;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "developers")
public class Developer {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @ManyToMany
    @JoinTable(
            name = "developer_skill",
            joinColumns = @JoinColumn(name = "developer_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private Set<Skill> skills;
    @OneToOne()
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @JoinColumn(name = "account_id")
    private Account account;

    public Developer() {
        this.id = UUID.randomUUID();
        this.firstName = "";
        this.lastName = "";
        this.skills = new HashSet<>();
        this.account = null;
    }

    public Developer(UUID id) {
        this.id = id;
        this.firstName = "";
        this.lastName = "";
        this.skills = new HashSet<>();
        this.account = null;
    }

    public Developer(String firstName, String lastName, Set<Skill> skills, Account account) {
        this.id = UUID.randomUUID();
        this.firstName = firstName;
        this.lastName = lastName;
        this.skills = skills;
        this.account = account;
    }

    public Developer(UUID id, String firstName, String lastName, Set<Skill> skills, Account account) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.skills = skills;
        this.account = account;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<Skill> getSkills() {
        return skills;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "Developer{" +
                "id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Developer developer = (Developer) o;
        return Objects.equals(id, developer.id) &&
                Objects.equals(firstName, developer.firstName) &&
                Objects.equals(lastName, developer.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName);
    }
}
