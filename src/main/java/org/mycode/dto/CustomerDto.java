package org.mycode.dto;

import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.UUID;

public class CustomerDto {
    @NotNull
    private UUID id;
    private String name;

    public CustomerDto() {
        this.id = UUID.randomUUID();
        this.name = "";
    }

    public CustomerDto(UUID id) {
        this.id = id;
        this.name = "";
    }

    public CustomerDto(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }

    public CustomerDto(UUID id, String name) {
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
        return "CustomerDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerDto that = (CustomerDto) o;
        return id.equals(that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
