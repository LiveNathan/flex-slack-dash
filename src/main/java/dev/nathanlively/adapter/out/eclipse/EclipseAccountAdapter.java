package dev.nathanlively.adapter.out.eclipse;

import dev.nathanlively.application.port.AccountRepository;
import dev.nathanlively.domain.Account;
import dev.nathanlively.domain.DataRoot;
import org.eclipse.serializer.persistence.types.Storer;
import org.eclipse.store.integrations.spring.boot.types.concurrent.Read;
import org.eclipse.store.integrations.spring.boot.types.concurrent.Write;
import org.eclipse.store.storage.embedded.types.EmbeddedStorageManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class EclipseAccountAdapter implements AccountRepository {
    private final EmbeddedStorageManager storageManager;
    private static final Logger log = LoggerFactory.getLogger(EclipseAccountAdapter.class);

    public EclipseAccountAdapter(EmbeddedStorageManager storageManager) {
        this.storageManager = storageManager;
        initializeRoot();
    }

    private void initializeRoot() {
        if (storageManager.root() == null) {
            log.info("Initializing root.");
            storageManager.setRoot(new DataRoot());
            storageManager.storeRoot();
        }
    }

    @Write
    @Override
    public void save(Account account) {
        DataRoot root = (DataRoot) storageManager.root();
        root.accounts().add(account);
//        storageManager.store(root.accounts());
        saveWithEagerStoring(root);
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

    private void saveWithEagerStoring(DataRoot root) {
        Storer eagerStorer = storageManager.createEagerStorer();
        eagerStorer.store(root);
        eagerStorer.commit();
    }
}
