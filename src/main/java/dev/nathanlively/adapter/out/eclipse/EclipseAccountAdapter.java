package dev.nathanlively.adapter.out.eclipse;

import dev.nathanlively.application.port.AccountRepository;
import dev.nathanlively.domain.Account;
import dev.nathanlively.domain.DataRoot;
import org.eclipse.store.integrations.spring.boot.types.concurrent.Read;
import org.eclipse.store.integrations.spring.boot.types.concurrent.Write;
import org.eclipse.store.storage.embedded.types.EmbeddedStorageManager;

import java.util.ArrayList;
import java.util.List;

public class EclipseAccountAdapter implements AccountRepository {
    private final EmbeddedStorageManager storageManager;

    public EclipseAccountAdapter(EmbeddedStorageManager storageManager) {
        this.storageManager = storageManager;
        initializeRoot();
    }

    private void initializeRoot() {
        if (storageManager.root() == null) {
            storageManager.setRoot(new DataRoot());
            storageManager.storeRoot();
        }
    }

    @Write
    @Override
    public void save(Account account) {
        DataRoot root = (DataRoot) storageManager.root();
        root.accounts().add(account);
        storageManager.store(root.accounts());
    }

    @Read
    @Override
    public Account findByUsername(String username) {
        DataRoot root = (DataRoot) storageManager.root();
        return root.accounts().byUsername(username);
    }

    @Read
    @Override
    public List<Account> findAll() {
        DataRoot root = (DataRoot) storageManager.root();
        return new ArrayList<>(root.accounts().all());
    }
}
