package org.mycode.service.visitors;

import org.mycode.service.AccountService;
import org.mycode.service.DeveloperService;
import org.mycode.service.SkillService;

import java.util.Optional;

public class DeleteVisitor extends ServiceVisitor {
    public DeleteVisitor(Optional inputData) {
        super(inputData);
    }
    @Override
    public void visitSkillService(SkillService service) {
        if(inputData.isPresent() && inputData.get() instanceof Long){
            service.delete((Long) inputData.get());
        }
    }
    @Override
    public void visitAccountService(AccountService service) {
        if(inputData.isPresent() && inputData.get() instanceof Long){
            service.delete((Long) inputData.get());
        }
    }
    @Override
    public void visitDeveloperService(DeveloperService service) {
        if(inputData.isPresent() && inputData.get() instanceof Long){
            service.delete((Long) inputData.get());
        }
    }
}
