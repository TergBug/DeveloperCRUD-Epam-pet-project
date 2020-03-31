package org.mycode.dto;

import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.UUID;

public class SkillDto {
    @NotNull
    private UUID id;
    private String name;

    public SkillDto() {
        this.id = UUID.randomUUID();
        this.name = "";
    }

    public SkillDto(UUID id) {
        this.id = id;
        this.name = "";
    }

    public SkillDto(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }

    public SkillDto(UUID id, String name) {
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
        return id + " " + name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SkillDto skill = (SkillDto) o;
        return Objects.equals(id, skill.id) &&
                Objects.equals(name, skill.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
