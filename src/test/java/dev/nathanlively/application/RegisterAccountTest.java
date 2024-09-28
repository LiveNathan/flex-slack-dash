package dev.nathanlively.application;

import dev.nathanlively.adapter.in.web.registration.UserDto;
import dev.nathanlively.application.port.AccountRepository;
import dev.nathanlively.domain.Account;
import dev.nathanlively.domain.Person;
import org.junit.jupiter.api.Test;

import static dev.nathanlively.application.ResultAssertions.assertThat;

class RegisterAccountTest {

    @Test
    void register() {
        AccountRepository accountRepository = InMemoryAccountRepository.createEmpty();
        assertThat(accountRepository.findAll()).hasSize(0);
        RegisterAccount service = new RegisterAccount(accountRepository);
        String travis = "Travis";
        String email = "travsi@micework.ch";
        String password = "<PASSWORD>";
        Account expected = Account.create(email, password);
        Person person = Person.create(travis, email);
        expected.setPerson(person);
        UserDto userDto = new UserDto(travis, email, password);

        RichResult<Account> actual = service.with(userDto);

        assertThat(actual).isSuccess();
        assertThat(actual).failureMessages().isEmpty();
        assertThat(actual).successValues().contains(expected);

        assertThat(accountRepository.findAll()).hasSize(1);
    }
}