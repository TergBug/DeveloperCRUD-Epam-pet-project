package org.mycode.service.visitors;

import org.mycode.service.AccountService;
import org.mycode.service.DeveloperService;
import org.mycode.service.SkillService;

public class DeleteVisitor extends ServiceVisitor {
    public DeleteVisitor(Long inputData) {
        super(inputData);
    }

    @Override
    public void visitSkillService(SkillService service) {
        if (inputData != null && inputData instanceof Long) {
            service.delete((Long) inputData);
        }
    }

    @Override
    public void visitAccountService(AccountService service) {
        if (inputData != null && inputData instanceof Long) {
            service.delete((Long) inputData);
        }
    }

    @Override
    public void visitDeveloperService(DeveloperService service) {
        if (inputData != null && inputData instanceof Long) {
            service.delete((Long) inputData);
        }
    }
}
