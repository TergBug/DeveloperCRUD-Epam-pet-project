package org.mycode.service.visitors;

import org.mycode.model.Account;
import org.mycode.model.Developer;
import org.mycode.model.Skill;
import org.mycode.service.AccountService;
import org.mycode.service.DeveloperService;
import org.mycode.service.SkillService;

import java.util.Optional;

public class CreateVisitor extends ServiceVisitor {
    public CreateVisitor(Optional inputData) {
        super(inputData);
    }
    @Override
    public void visitSkillService(SkillService service) {
        if(inputData.isPresent() && inputData.get() instanceof Skill){
            service.create((Skill)inputData.get());
        }
    }
    @Override
    public void visitAccountService(AccountService service) {
        if(inputData.isPresent() && inputData.get() instanceof Account){
            service.create((Account)inputData.get());
        }
    }
    @Override
    public void visitDeveloperService(DeveloperService service) {
        if(inputData.isPresent() && inputData.get() instanceof Developer){
            service.create((Developer)inputData.get());
        }
    }
}
