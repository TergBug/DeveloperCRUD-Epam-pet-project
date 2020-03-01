package org.mycode.service.visitors;

import org.mycode.service.AccountService;
import org.mycode.service.DeveloperService;
import org.mycode.service.SkillService;

public abstract class ServiceVisitor {
    protected Object inputData;
    protected Object resultData;

    protected ServiceVisitor(Object inputData) {
        this.inputData = inputData;
        resultData = new Object();
    }

    public abstract void visitSkillService(SkillService service);

    public abstract void visitAccountService(AccountService service);

    public abstract void visitDeveloperService(DeveloperService service);

    public Object getResultData() {
        return resultData;
    }

    public void setInputData(Object inputData) {
        this.inputData = inputData;
    }
}
