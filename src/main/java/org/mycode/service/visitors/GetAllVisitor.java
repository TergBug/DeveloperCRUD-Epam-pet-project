package org.mycode.service.visitors;

import org.mycode.service.AccountService;
import org.mycode.service.DeveloperService;
import org.mycode.service.SkillService;

public class GetAllVisitor extends ServiceVisitor {
    public GetAllVisitor() {
        super(null);
    }

    @Override
    public void visitSkillService(SkillService service) {
        resultData = service.getAll();
    }

    @Override
    public void visitAccountService(AccountService service) {
        resultData = service.getAll();
    }

    @Override
    public void visitDeveloperService(DeveloperService service) {
        resultData = service.getAll();
    }
}
