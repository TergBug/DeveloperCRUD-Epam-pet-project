package org.mycode.model;

import org.hibernate.annotations.GenericGenerator;
import org.mycode.model.enums.AccountStatus;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;
    private String name;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    public Account() {
        this.id = UUID.randomUUID();
        this.name = "";
        this.status = null;
    }

    public Account(UUID id) {
        this.id = id;
        this.name = "";
        this.status = null;
    }

    public Account(String name, AccountStatus status) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.status = status;
    }

    public Account(UUID id, String name, AccountStatus status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id) &&
                Objects.equals(name, account.name) &&
                status == account.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, status);
    }
}
