package org.mycode.assembler;

import org.mycode.dto.DeveloperDto;
import org.mycode.model.Developer;

public interface DeveloperAssembler {
    DeveloperDto assemble(Developer developerEntity);

    Developer assemble(DeveloperDto developer);
}
