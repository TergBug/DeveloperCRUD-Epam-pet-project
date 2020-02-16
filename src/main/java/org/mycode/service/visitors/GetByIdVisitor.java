package org.mycode.service.visitors;

import org.mycode.service.AccountService;
import org.mycode.service.DeveloperService;
import org.mycode.service.SkillService;

import java.util.Optional;

public class GetByIdVisitor extends ServiceVisitor {
    public GetByIdVisitor(Optional inputData) {
        super(inputData);
    }
    @Override
    public void visitSkillService(SkillService service) {
        if(inputData.isPresent() && inputData.get() instanceof Long){
            resultData = Optional.of(service.getById((Long) inputData.get()));
        }
    }
    @Override
    public void visitAccountService(AccountService service) {
        if(inputData.isPresent() && inputData.get() instanceof Long){
            resultData = Optional.of(service.getById((Long) inputData.get()));
        }
    }
    @Override
    public void visitDeveloperService(DeveloperService service) {
        if(inputData.isPresent() && inputData.get() instanceof Long){
            resultData = Optional.of(service.getById((Long) inputData.get()));
        }
    }
}
