package org.mycode.repository.hibernate;

import org.hibernate.SessionFactory;
import org.mycode.exceptions.NoSuchEntryException;
import org.mycode.exceptions.NotUniqueEntryException;
import org.mycode.model.Account;
import org.mycode.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
@EnableTransactionManagement
public class AccountRepositoryImpl implements AccountRepository {
    private SessionFactory sessionFactory;

    @Autowired
    public AccountRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public void create(Account model) {
        if (!checkForUniquenessOfName(model.getName())) {
            throw new NotUniqueEntryException("Not unique name");
        }
        sessionFactory.getCurrentSession().save(model);
    }

    @Override
    @Transactional(readOnly = true)
    public Account getById(UUID readID) {
        Account readAccount = sessionFactory.getCurrentSession().get(Account.class, readID);
        if (readAccount == null) {
            throw new NoSuchEntryException("Entity for read not found");
        }
        return readAccount;
    }

    @Override
    @Transactional
    public void update(Account updatedModel) {
        if (!checkForUniquenessOfName(updatedModel.getName())) {
            throw new NotUniqueEntryException("Not unique name");
        }
        Account updatedAccount = sessionFactory.getCurrentSession().get(Account.class, updatedModel.getId());
        if (updatedAccount == null) {
            throw new NoSuchEntryException("Entity for update not found");
        }
        sessionFactory.getCurrentSession().detach(updatedAccount);
        sessionFactory.getCurrentSession().update(updatedModel);
    }

    @Override
    @Transactional
    public void delete(UUID deletedEntryId) {
        Account deletedAccount = sessionFactory.getCurrentSession().get(Account.class, deletedEntryId);
        if (deletedAccount == null) {
            throw new NoSuchEntryException("Entity for delete not found");
        }
        sessionFactory.getCurrentSession().delete(deletedAccount);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Account> getAll() {
        return sessionFactory.getCurrentSession().createQuery("from Account", Account.class).list();
    }

    private boolean checkForUniquenessOfName(String name) {
        List<Account> existedEntities = sessionFactory.getCurrentSession()
                .createQuery("from Account where name='" + name + "'", Account.class).getResultList();
        return existedEntities.size() == 0;
    }
}
