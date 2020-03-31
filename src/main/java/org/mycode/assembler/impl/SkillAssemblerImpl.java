package org.mycode.assembler.impl;

import org.mycode.assembler.SkillAssembler;
import org.mycode.dto.SkillDto;
import org.mycode.model.Skill;
import org.springframework.stereotype.Component;

@Component
public class SkillAssemblerImpl implements SkillAssembler {

    @Override
    public Skill assemble(SkillDto skill) {
        return new Skill(skill.getId(), skill.getName());
    }

    @Override
    public SkillDto assemble(Skill skill) {
        return new SkillDto(skill.getId(), skill.getName());
    }
}
