package org.mycode.assembler;

import org.mycode.dto.SkillDto;
import org.mycode.model.Skill;

public interface SkillAssembler {
    Skill assemble(SkillDto skill);

    SkillDto assemble(Skill skill);
}
