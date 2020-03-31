package org.mycode.assembler;

import org.mycode.dto.ProjectDto;
import org.mycode.model.Project;

public interface ProjectAssembler {
    Project assemble(ProjectDto projectDto);

    ProjectDto assemble(Project project);
}
