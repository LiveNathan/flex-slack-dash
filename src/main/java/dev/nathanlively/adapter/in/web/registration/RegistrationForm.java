package dev.nathanlively.adapter.in.web.registration;

import com.vaadin.flow.component.HasValueAndElement;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.theme.lumo.LumoUtility;

import java.util.stream.Stream;

public class RegistrationForm extends FormLayout {
    @SuppressWarnings("FieldCanBeLocal")
    private final TextField name;
    @SuppressWarnings("FieldCanBeLocal")  // required for data binding of instance fields
    private final EmailField email;
    @SuppressWarnings("FieldCanBeLocal")
    private final PasswordField password;
    private final Span errorMessageField;
    private final Button submitButton;

    public RegistrationForm() {
        H2 title = new H2("Register");

        name = new TextField("Name");
        name.setAutofocus(true);

        email = new EmailField("Email");
        email.setClearButtonVisible(true);
        email.setId("email-field-register");

        password = new PasswordField("Password");

        setRequiredIndicatorVisible(name, email, password);

        submitButton = new Button("Register");
        submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        submitButton.setDisableOnClick(true);
        submitButton.addClassName(LumoUtility.Margin.Top.XLARGE);
        submitButton.addClickShortcut(Key.ENTER);

        errorMessageField = new Span();
        errorMessageField.addClassNames(LumoUtility.Margin.Top.SMALL, LumoUtility.TextColor.ERROR);

        add(title, name, email, password, errorMessageField, submitButton);

//        setMaxWidth("360px");
        setResponsiveSteps(new ResponsiveStep("0", 1, ResponsiveStep.LabelsPosition.TOP));
    }

    public Button getSubmitButton() {
        return submitButton;
    }

    public Span getErrorMessageField() {
        return errorMessageField;
    }

    private void setRequiredIndicatorVisible(HasValueAndElement<?, ?>... components) {
        Stream.of(components).forEach(comp -> comp.setRequiredIndicatorVisible(true));
    }
}
