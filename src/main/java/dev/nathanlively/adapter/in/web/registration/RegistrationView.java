package dev.nathanlively.adapter.in.web.registration;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility;
import dev.nathanlively.application.RegisterAccount;
import dev.nathanlively.security.AuthenticatedUser;

@AnonymousAllowed
@PageTitle("Register")
@Route(value = "register")
public class RegistrationView extends VerticalLayout implements BeforeEnterObserver {

    private final AuthenticatedUser authenticatedUser;

    public RegistrationView(AuthenticatedUser authenticatedUser, RegisterAccount registerAccount) {
        this.authenticatedUser = authenticatedUser;
        RegistrationForm registrationForm = new RegistrationForm();

        Div cardWrapper = new Div();
        cardWrapper.addClassNames(LumoUtility.Border.ALL, LumoUtility.BoxShadow.SMALL,
                LumoUtility.BorderRadius.LARGE, LumoUtility.Padding.LARGE, LumoUtility.Background.BASE);
        cardWrapper.setMaxWidth("340px");
        cardWrapper.add(registrationForm);

        addClassNames(LumoUtility.Background.CONTRAST_5, LumoUtility.AlignItems.CENTER,
                LumoUtility.JustifyContent.CENTER);
        setSizeFull();

        add(cardWrapper);

        RegistrationFormBinder registrationFormBinder = new RegistrationFormBinder(registrationForm, registerAccount);
        registrationFormBinder.addBindingAndValidation();
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (authenticatedUser.get().isPresent()) {
            // Already logged in
            event.forwardTo("");
        }
    }
}
