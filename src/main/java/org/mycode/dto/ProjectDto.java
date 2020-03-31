package org.mycode.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class ProjectDto {
    @NotNull
    private UUID id;
    private String name;
    private Set<String> developersAccounts;
    private UUID customerId;
    @Pattern(regexp = "(DESIGN)|(IMPLEMENTATION)|(TESTING)|(DEPLOYMENT)")
    private String status;

    public ProjectDto() {
        this.id = UUID.randomUUID();
        this.name = "";
        this.developersAccounts = new HashSet<>();
        this.customerId = null;
        this.status = "";
    }

    public ProjectDto(UUID id) {
        this.id = id;
        this.name = "";
        this.developersAccounts = new HashSet<>();
        this.customerId = null;
        this.status = "";
    }

    public ProjectDto(String name, Set<String> developersAccounts, UUID customerId, String status) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.developersAccounts = developersAccounts;
        this.customerId = customerId;
        this.status = status;
    }

    public ProjectDto(UUID id, String name, Set<String> developersAccounts, UUID customerId, String status) {
        this.id = id;
        this.name = name;
        this.developersAccounts = developersAccounts;
        this.customerId = customerId;
        this.status = status;
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

    public Set<String> getDevelopersAccounts() {
        return developersAccounts;
    }

    public void setDevelopersAccounts(Set<String> developersAccounts) {
        this.developersAccounts = developersAccounts;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ProjectDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", developersAccounts=" + developersAccounts +
                ", customerName='" + customerId + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectDto that = (ProjectDto) o;
        return id.equals(that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(developersAccounts, that.developersAccounts) &&
                Objects.equals(customerId, that.customerId) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, developersAccounts, customerId, status);
    }
}
