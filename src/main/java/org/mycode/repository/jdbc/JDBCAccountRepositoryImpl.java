package org.mycode.repository.jdbc;

import org.apache.log4j.Logger;
import org.mycode.exceptions.NoSuchEntryException;
import org.mycode.exceptions.NotUniqueEntryException;
import org.mycode.exceptions.RepoStorageException;
import org.mycode.mapping.JDBCAccountMapper;
import org.mycode.model.Account;
import org.mycode.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository("accountRepository")
public class JDBCAccountRepositoryImpl implements AccountRepository {
    private static final Logger log = Logger.getLogger(JDBCAccountRepositoryImpl.class);
    private final String INSERT_QUERY = "insert into accounts(name, status) values (?, ?);";
    private final String SELECT_QUERY = "select * from accounts where id=?;";
    private final String UPDATE_QUERY = "update accounts set name=?, status=? where id=?;";
    private final String DELETE_QUERY = "delete from accounts where id=?;";
    private final String SELECT_ALL_QUERY = "select * from accounts;";
    private Connection connection;

    @Autowired
    public JDBCAccountRepositoryImpl(DataSource dataSource) {
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            log.error("Cannot connect to SQL DB", e);
            throw new RepoStorageException("Cannot connect to SQL DB");
        }
    }

    @Override
    public void create(Account model) {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            statement.setString(1, model.getName());
            statement.setString(2, model.getStatus().toString());
            statement.execute();
            log.debug("Create entry(DB): " + model);
        } catch (SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                log.warn("Not unique entry with id: " + model.getId());
                throw new NotUniqueEntryException("Creating in DB is failed");
            } else {
                log.error("Wrong SQL query to DB in reading", e);
                throw new RepoStorageException("Wrong SQL query to DB in reading");
            }
        }
    }

    @Override
    public Account getById(Long readID) {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_QUERY, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            statement.setLong(1, readID);
            ResultSet resultSet = statement.executeQuery();
            Account account = new JDBCAccountMapper().map(resultSet, readID);
            log.debug("Read entry(DB) with ID: " + readID);
            return account;
        } catch (SQLException e) {
            log.error("Wrong SQL query to DB in reading", e);
            throw new RepoStorageException("Wrong SQL query to DB in reading");
        }
    }

    @Override
    public void update(Account updatedModel) {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            statement.setString(1, updatedModel.getName());
            statement.setString(2, updatedModel.getStatus().toString());
            statement.setLong(3, updatedModel.getId());
            if (statement.executeUpdate() < 1) {
                log.warn("No such entry: " + updatedModel);
                throw new NoSuchEntryException("Updating in DB is failed");
            }
            log.debug("Update entry(DB): " + updatedModel);
        } catch (SQLException e) {
            log.error("Wrong SQL query to DB in updating", e);
            throw new RepoStorageException("Wrong SQL query to DB in updating");
        }
    }

    @Override
    public void delete(Long deletedEntry) {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            statement.setLong(1, deletedEntry);
            if (statement.executeUpdate() < 1) {
                log.warn("No such entry with ID: " + deletedEntry);
                throw new NoSuchEntryException("Deleting in DB is failed");
            }
            log.debug("Delete entry(DB) with ID: " + deletedEntry);
        } catch (SQLException e) {
            log.error("Wrong SQL query to DB in deleting", e);
            throw new RepoStorageException("Wrong SQL query to DB in deleting");
        }
    }

    @Override
    public List<Account> getAll() {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_QUERY, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            ResultSet resultSet = statement.executeQuery();
            ArrayList<Account> accounts = new ArrayList<>();
            JDBCAccountMapper mapper = new JDBCAccountMapper();
            while (resultSet.next()) {
                accounts.add(mapper.map(resultSet, resultSet.getLong(1)));
            }
            log.debug("Read all entries(DB)");
            return accounts;
        } catch (SQLException e) {
            log.error("Wrong SQL query to DB in reading", e);
            throw new RepoStorageException("Wrong SQL query to DB in reading");
        }
    }
}
