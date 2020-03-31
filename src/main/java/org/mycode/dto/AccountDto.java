package org.mycode.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Objects;
import java.util.UUID;

public class AccountDto {
    @NotNull
    private UUID id;
    private String name;
    @Pattern(regexp = "(ACTIVE)|(BANNED)|(DELETED)")
    private String status;

    public AccountDto() {
        this.id = UUID.randomUUID();
        this.name = "";
        this.status = "";
    }

    public AccountDto(UUID id) {
        this.id = id;
        this.name = "";
        this.status = "";
    }

    public AccountDto(String name, String status) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.status = status;
    }

    public AccountDto(UUID id, String name, String status) {
        this.id = id;
        this.name = name;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "AccountDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountDto that = (AccountDto) o;
        return id.equals(that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, status);
    }
}
