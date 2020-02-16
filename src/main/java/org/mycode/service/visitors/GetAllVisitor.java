package org.mycode.service.visitors;

import org.mycode.service.AccountService;
import org.mycode.service.DeveloperService;
import org.mycode.service.SkillService;

import java.util.Optional;

public class GetAllVisitor extends ServiceVisitor {
    public GetAllVisitor(Optional inputData) {
        super(inputData);
    }
    @Override
    public void visitSkillService(SkillService service) {
        resultData = Optional.of(service.getAll());
    }
    @Override
    public void visitAccountService(AccountService service) {
        resultData = Optional.of(service.getAll());
    }
    @Override
    public void visitDeveloperService(DeveloperService service) {
        resultData = Optional.of(service.getAll());
    }
}
