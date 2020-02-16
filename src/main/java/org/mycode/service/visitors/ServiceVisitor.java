package org.mycode.service.visitors;

import org.mycode.service.AccountService;
import org.mycode.service.DeveloperService;
import org.mycode.service.SkillService;

import java.util.Optional;

public abstract class ServiceVisitor {
    protected Optional inputData;
    protected Optional resultData;
    protected ServiceVisitor(Optional inputData) {
        this.inputData = inputData;
        resultData = Optional.empty();
    }
    public void visitSkillService(SkillService service){}
    public void visitAccountService(AccountService service){}
    public void visitDeveloperService(DeveloperService service){}
    public Optional getResultData() {
        return resultData;
    }
}
