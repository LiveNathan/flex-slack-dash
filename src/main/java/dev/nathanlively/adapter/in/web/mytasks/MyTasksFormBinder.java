package dev.nathanlively.adapter.in.web.mytasks;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import dev.nathanlively.application.RichResult;
import dev.nathanlively.application.UpdateTask;
import dev.nathanlively.domain.Task;

public class MyTasksFormBinder {
    private final MyTasksForm form;
    private final UpdateTask updateTask;
    private TaskDto taskDto;

    public MyTasksFormBinder(MyTasksForm form, UpdateTask updateTask) {
        this.form = form;
        this.updateTask = updateTask;
    }

    public void addBindingAndValidation() {
        BeanValidationBinder<TaskDto> binder = new BeanValidationBinder<>(TaskDto.class);
        binder.bindInstanceFields(form);
        binder.setStatusLabel(form.getErrorMessageField());

        form.getSave().addClickListener(e -> {
            try {
                if (this.taskDto == null) {
                    this.taskDto = new TaskDto();
                }
                binder.writeBean(this.taskDto);
                RichResult<Task> result = updateTask.with(this.taskDto);
                result.handle(
                        task -> {
                            // clear form
                            this.taskDto = null;
                            binder.readBean(null);
                            // refresh grid
//                            refreshGrid();
                            Notification notification = Notification.show("Task updated");
                            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                            UI.getCurrent().navigate(MyTasksView.class);
                        },
                        errorMessage -> {
                            String errorText = "Failed to update the data. Check again that all values are valid";
                            form.getErrorMessageField().setText(errorText);
                            form.getSave().setEnabled(true);
                        }
                );
            } catch (ValidationException validationException) {
                Notification.show("Failed to update the data. Check again that all values are valid");
            }
        });
    }

}
