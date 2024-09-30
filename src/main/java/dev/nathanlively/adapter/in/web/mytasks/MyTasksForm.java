package dev.nathanlively.adapter.in.web.mytasks;

import com.vaadin.flow.component.HasValueAndElement;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.theme.lumo.LumoUtility;

import java.util.stream.Stream;

public class MyTasksForm extends FormLayout {
    private final TextField title = new TextField("Title");
    private final Button cancel = new Button("Cancel");
    private final Button save = new Button("Save");
    private final Span errorMessageField = new Span();
    private SaveListener saveListener;

    public void setSaveListener(SaveListener saveListener) {
        this.saveListener = saveListener;
    }
    public MyTasksForm() {
        addClassName("my-tasks-form");
        title.setRequired(true);
        setRequiredIndicatorVisible(title);

        cancel.addClickListener(e -> clearForm());
        save.addClickListener(e -> saveForm());

        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.setDisableOnClick(true);
        save.addClickShortcut(Key.ENTER);
        errorMessageField.addClassNames(LumoUtility.Margin.Top.SMALL, LumoUtility.TextColor.ERROR);

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.add(save, cancel);

        add(title, errorMessageField, buttonLayout);
    }

    private void saveForm() {
        if (saveListener != null) {
            saveListener.onSave();
        }
    }

    public interface SaveListener {

        void onSave();
    }

    public TextField getTitle() {
        return title;
    }

    public Button getCancel() {
        return cancel;
    }

    public Button getSave() {
        return save;
    }

    private void clearForm() {
        title.clear();
    }

    public Span getErrorMessageField() {
        return errorMessageField;
    }

    private void setRequiredIndicatorVisible(HasValueAndElement<?, ?>... components) {
        Stream.of(components).forEach(comp -> comp.setRequiredIndicatorVisible(true));
    }

}
