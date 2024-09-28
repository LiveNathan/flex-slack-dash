package dev.nathanlively.adapter.out.eclipse;

import dev.nathanlively.domain.Account;
import org.eclipse.store.storage.embedded.types.EmbeddedStorage;
import org.eclipse.store.storage.embedded.types.EmbeddedStorageManager;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("database")
class EclipseAccountAdapterTest {
    private static final Logger log = LoggerFactory.getLogger(EclipseAccountAdapterTest.class);
    @TempDir Path storageDir;
    private EclipseAccountAdapter adapter;

    @Test
    void canWriteReadRestartAndReadAgain() {
        writeData();
        log.info("Storage manager shut down. Restarting...");
        readData();
    }

    private void writeData() {
        try (EmbeddedStorageManager storageManager = startStorageManager()) {
            adapter = new EclipseAccountAdapter(storageManager);
            List<Account> accounts = adapter.findAll();
            assertThat(accounts).isEmpty();

            Account account = Account.create("Account A", "password");
            adapter.save(account);

            List<Account> allAccounts = adapter.findAll();
            assertThat(allAccounts).hasSize(1);
            assertThat(allAccounts.getFirst().username()).isEqualTo("Account A");
        }
    }

    private void readData() {
        try (EmbeddedStorageManager storageManager = startStorageManager()) {
            adapter = new EclipseAccountAdapter(storageManager);

            List<Account> allAccounts = adapter.findAll();
            assertThat(allAccounts).hasSize(1);
            assertThat(allAccounts.getFirst().username()).isEqualTo("Account A");
        }
    }

    private EmbeddedStorageManager startStorageManager() {
        return EmbeddedStorage.start(storageDir);
    }

}