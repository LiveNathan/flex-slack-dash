package dev.nathanlively.adapter.in.web.registration;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import dev.nathanlively.adapter.in.web.login.LoginView;
import dev.nathanlively.application.RegisterAccount;
import dev.nathanlively.application.RichResult;
import dev.nathanlively.domain.Account;

public class RegistrationFormBinder {
    private final RegistrationForm registrationForm;
    private final RegisterAccount registerAccount;

    public RegistrationFormBinder(RegistrationForm registrationForm, RegisterAccount registerAccount) {
        this.registrationForm = registrationForm;
        this.registerAccount = registerAccount;
    }

    public void addBindingAndValidation() {
        BeanValidationBinder<UserDto> binder = new BeanValidationBinder<>(UserDto.class);
        binder.bindInstanceFields(registrationForm);  // Method references and lambda expressions are not allowed when using bean validation.
        binder.setStatusLabel(registrationForm.getErrorMessageField());

        registrationForm.getSubmitButton().addClickListener(_ -> {
            UserDto userDto = new UserDto();
            try {
                binder.writeBean(userDto);

                RichResult<Account> result = registerAccount.with(userDto);

                result.handle(
                        _ -> {
                            Notification notification = Notification.show("Registration successful! 🎉 Welcome " + userDto.getName());
                            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                            UI.getCurrent().navigate(LoginView.class);
                        },
                        errorMessage -> {
                            String errorText = "Registration failed: " + errorMessage;
                            registrationForm.getErrorMessageField().setText(errorText);
                            registrationForm.getSubmitButton().setEnabled(true);
                        }
                );
            } catch (ValidationException exception) {
                // validation errors are already visible for each field, and bean-level errors are shown in the status label.
                registrationForm.getSubmitButton().setEnabled(true);
            }
        });
    }

}
