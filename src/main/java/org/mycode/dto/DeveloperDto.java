package org.mycode.dto;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class DeveloperDto {
    @NotNull
    private UUID id;
    private String firstName;
    private String lastName;
    private Set<String> skills;
    private String account;

    public DeveloperDto() {
        this.id = UUID.randomUUID();
        this.firstName = "";
        this.lastName = "";
        this.skills = new HashSet<>();
        this.account = "";
    }

    public DeveloperDto(UUID id) {
        this.id = id;
        this.firstName = "";
        this.lastName = "";
        this.skills = new HashSet<>();
        this.account = "";
    }

    public DeveloperDto(String firstName, String lastName, Set<String> skills, String account) {
        this.id = UUID.randomUUID();
        this.firstName = firstName;
        this.lastName = lastName;
        this.skills = skills;
        this.account = account;
    }

    public DeveloperDto(UUID id, String firstName, String lastName, Set<String> skills, String account) {
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

    public Set<String> getSkills() {
        return skills;
    }

    public void setSkills(Set<String> skills) {
        this.skills = skills;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "DeveloperDto{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", skills=" + skills +
                ", account='" + account + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeveloperDto that = (DeveloperDto) o;
        return id.equals(that.id) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(skills, that.skills) &&
                Objects.equals(account, that.account);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, skills, account);
    }
}
