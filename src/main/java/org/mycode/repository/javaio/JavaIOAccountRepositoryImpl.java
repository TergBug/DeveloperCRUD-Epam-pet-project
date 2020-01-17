package org.mycode.repository.javaio;

import org.mycode.exceptions.RepoStorageException;
import org.mycode.exceptions.NoSuchEntryException;
import org.mycode.exceptions.NotUniquePrimaryKeyException;
import org.mycode.model.Account;
import org.mycode.model.AccountStatus;
import org.mycode.repository.AccountRepository;
import org.mycode.util.JavaIOUtils;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class JavaIOAccountRepositoryImpl implements AccountRepository {
    private final String PATTERN_OF_ENTRY = "<{*-1-*}{-2-}{-3-}>";
    private final String VALIDATION_PATTERN = "<\\{\\*\\d+?\\*}\\{.*?}\\{((ACTIVE)|(BANNED)|(DELETED))}>";
    private File repo;
    public JavaIOAccountRepositoryImpl(){
        repo = JavaIOUtils.getAccountRepo();
    }
    private List<String[]> getContentFromFile(File file, String validPattern) throws RepoStorageException {
        if(!file.exists()){
            throw  new RepoStorageException("Extracting of content from file is failed");
        }
        StringBuilder content = new StringBuilder();
        try (FileReader fr = new FileReader(file)){
            int c;
            while ((c=fr.read()) != -1) content.append((char) c);
        } catch (IOException e) { e.printStackTrace(); }
        List<String[]> contentTable = new ArrayList<>();
        Matcher outerMatcher = Pattern.compile(validPattern).matcher(content);
        Matcher innerMatcher;
        while (outerMatcher.find()){
            innerMatcher = Pattern.compile("\\{.*?}").matcher(outerMatcher.group());
            contentTable.add(new String[3]);
            contentTable.get(contentTable.size()-1)[0] = findInMatcherByIndex(innerMatcher, 1).group().replaceAll("[{*}]", "");
            contentTable.get(contentTable.size()-1)[1] = findInMatcherByIndex(innerMatcher, 2).group().replaceAll("[{}]", "");
            contentTable.get(contentTable.size()-1)[2] = findInMatcherByIndex(innerMatcher, 3).group().replaceAll("[{}]", "");
        }
        if(contentTable.size()==0 && content.length()>0){
            throw  new RepoStorageException("Extracting of content from file is failed");
        }
        return contentTable;
    }
    private Account strMasToAccount(String[] mas){
        return new Account(Long.parseLong(mas[0]), mas[1], AccountStatus.valueOf(mas[2]));
    }
    private String[] accountToStrMas(Account account){
        return new String[]{account.getId().toString(), account.getName(), account.getStatus().toString()};
    }
    @Override
    public void create(Account model) throws RepoStorageException, NotUniquePrimaryKeyException {
        if(!repo.exists()) {
            try {
                repo.createNewFile();
            } catch (IOException e) { e.printStackTrace(); }
        }
        if(model.getId()==null || model.getId()<1) {
            model.setId(generateAutoIncrId());
        }
        else if(getContentFromFile(repo, VALIDATION_PATTERN).stream().anyMatch(el -> el[0].equals(model.getId().toString()))){
            throw new NotUniquePrimaryKeyException("Creating of entry is failed");
        }
        String entry = PATTERN_OF_ENTRY.replace("-1-", String.valueOf(model.getId())).
                replace("-2-", model.getName()).
                replace("-3-", model.getStatus().toString());
        try (FileWriter fw = new FileWriter(repo, true)){
            fw.append(entry);
            fw.flush();
        } catch (IOException e) { e.printStackTrace(); }
    }
    private Long generateAutoIncrId() throws RepoStorageException {
        List<String[]> content = getContentFromFile(repo, VALIDATION_PATTERN);
        long id = 1L;
        if (content.size()!=0){
            content.sort(Comparator.comparing(el -> el[0]));
            id = Long.parseLong(content.get(content.size()-1)[0])+1;
        }
        return id;
    }
    @Override
    public Account getById(Long readID) throws RepoStorageException, NoSuchEntryException, NotUniquePrimaryKeyException {
        List<String[]> content = getContentFromFile(repo, VALIDATION_PATTERN).stream().
                filter(el -> el[0].equals(readID.toString())).
                collect(Collectors.toList());
        if(content.size()==0){
            throw new NoSuchEntryException("Reading of entry is failed");
        }
        else if(content.size()>1){
            throw new NotUniquePrimaryKeyException("Reading of entry is failed");
        }
        return strMasToAccount(content.get(0));
    }
    @Override
    public void update(Account updatedModel) throws RepoStorageException, NoSuchEntryException {
        List<String[]> content = getContentFromFile(repo, VALIDATION_PATTERN);
        boolean isExist = false;
        for (int i = 0; i < content.size(); i++) {
            if(content.get(i)[0].equals(updatedModel.getId().toString())){
                isExist = true;
                content.set(i, accountToStrMas(updatedModel));
            }
        }
        if(!isExist){
            throw new NoSuchEntryException("Updating of entry is failed");
        }
        setAll(content);
    }
    @Override
    public void delete(Long deletedID) throws NoSuchEntryException, RepoStorageException {
        List<String[]> content = getContentFromFile(repo, VALIDATION_PATTERN);
        if(!content.removeIf(el -> el[0].equals(deletedID.toString()))){
            throw new NoSuchEntryException("Deleting of entry is failed");
        }
        setAll(content);
    }
    @Override
    public List<Account> getAll() throws RepoStorageException {
        List<String[]> content = getContentFromFile(repo, VALIDATION_PATTERN);
        return content.stream().map(this::strMasToAccount).collect(Collectors.toList());
    }
    private Matcher findInMatcherByIndex(Matcher matcher, int index){
        matcher.reset();
        for (int i = 0; i < index && matcher.find(); i++);
        return matcher;
    }
    private void setAll(List<String[]> listOfAccountsInStrMas){
        StringBuilder content = new StringBuilder();
        for (String[] accountStrMas : listOfAccountsInStrMas) {
            content.append(PATTERN_OF_ENTRY.replace("-1-", accountStrMas[0]).
                    replace("-2-", accountStrMas[1]).
                    replace("-3-", accountStrMas[2]));
        }
        try (FileWriter fw = new FileWriter(repo, false)){
            fw.append(content.toString());
            fw.flush();
        } catch (IOException e) { e.printStackTrace(); }
    }
}
