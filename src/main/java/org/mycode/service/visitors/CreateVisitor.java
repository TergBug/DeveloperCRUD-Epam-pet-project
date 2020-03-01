package org.mycode.service.visitors;

import org.mycode.model.Account;
import org.mycode.model.Developer;
import org.mycode.model.Skill;
import org.mycode.service.AccountService;
import org.mycode.service.DeveloperService;
import org.mycode.service.SkillService;

public class CreateVisitor extends ServiceVisitor {
    public CreateVisitor(Object inputData) {
        super(inputData);
    }

    @Override
    public void visitSkillService(SkillService service) {
        if (inputData != null && inputData instanceof Skill) {
            service.create((Skill) inputData);
        }
    }

    @Override
    public void visitAccountService(AccountService service) {
        if (inputData != null && inputData instanceof Account) {
            service.create((Account) inputData);
        }
    }

    @Override
    public void visitDeveloperService(DeveloperService service) {
        if (inputData != null && inputData instanceof Developer) {
            service.create((Developer) inputData);
        }
    }
}
