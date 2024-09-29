package dev.nathanlively.adapter.in.web.mytasks;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import dev.nathanlively.adapter.in.web.MainLayout;
import dev.nathanlively.application.ReadTask;
import dev.nathanlively.domain.Account;
import dev.nathanlively.domain.Task;
import dev.nathanlively.security.AuthenticatedUser;
import jakarta.annotation.security.PermitAll;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

@PageTitle("My Tasks")
@Route(value = "my-tasks/:taskID?/:action?(edit)", layout = MainLayout.class)
@PermitAll
public class MyTasksView extends Div implements BeforeEnterObserver {
    private final AuthenticatedUser authenticatedUser;
    private final String TASK_ID = "taskID";
    private final String TASK_EDIT_ROUTE_TEMPLATE = "my-tasks/%s/edit";

    private final Grid<Task> grid = new Grid<>(Task.class, false);

    private TextField title;
    private TextField description;
    private TextField event;
    private TextField assignedTo;
    private TextField dueDate;
    private TextField status;

    private final Button cancel = new Button("Cancel");
    private final Button save = new Button("Save");

    private final BeanValidationBinder<Task> binder;

    private Task task;

    private final ReadTask readTask;

    public MyTasksView(AuthenticatedUser authenticatedUser, ReadTask readTask) {
        this.authenticatedUser = authenticatedUser;
        this.readTask = readTask;
        addClassNames("my-tasks-view");

        // Create UI
        SplitLayout splitLayout = new SplitLayout();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        Optional<Account> maybeUser = authenticatedUser.get();
        if (maybeUser.isPresent()) {
            grid.addColumn("title").setAutoWidth(true);
//        grid.addColumn("description").setAutoWidth(true);
//        grid.addColumn("event").setAutoWidth(true);
//        grid.addColumn("assignedTo").setAutoWidth(true);
//        grid.addColumn("dueDate").setAutoWidth(true);
//        grid.addColumn("status").setAutoWidth(true);
            grid.setItems(query -> readTask.all(
                    PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)),
                    maybeUser.get().username()).stream());
            grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

            // when a row is selected or deselected, populate form
            grid.asSingleSelect().addValueChangeListener(event -> {
                if (event.getValue() != null) {
                    UI.getCurrent().navigate(String.format(TASK_EDIT_ROUTE_TEMPLATE, event.getValue().id()));
                } else {
                    clearForm();
                    UI.getCurrent().navigate(MyTasksView.class);
                }
            });
        } else {
            Anchor loginLink = new Anchor("login", "Sign in");
            add(loginLink);
        }

        // Configure Form
        binder = new BeanValidationBinder<>(Task.class);

        // Bind fields. This is where you'd define e.g. validation rules

        binder.bindInstanceFields(this);

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.task == null) {
                    this.task = new Task();
                }
                binder.writeBean(this.task);
//                taskService.update(this.task);
                clearForm();
                refreshGrid();
                Notification.show("Data updated");
                UI.getCurrent().navigate(MyTasksView.class);
            } catch (ValidationException validationException) {
                Notification.show("Failed to update the data. Check again that all values are valid");
            }
        });
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Long> taskId = event.getRouteParameters().get(TASK_ID).map(Long::parseLong);
        if (taskId.isPresent()) {
            Optional<Task> taskFromBackend = readTask.get(taskId.get());
            if (taskFromBackend.isPresent()) {
                populateForm(taskFromBackend.get());
            } else {
                Notification.show(String.format("The requested task was not found, ID = %s", taskId.get()), 3000,
                        Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(MyTasksView.class);
            }
        }
    }

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("editor-layout");

        Div editorDiv = new Div();
        editorDiv.setClassName("editor");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        title = new TextField("Title");
//        description = new TextField("Description");
//        event = new TextField("Event");
//        assignedTo = new TextField("Assigned To");
//        dueDate = new TextField("Due Date");
//        status = new TextField("Status");
//        formLayout.add(description, event, assignedTo, dueDate, status);
        formLayout.add(title);

        editorDiv.add(formLayout);
        createButtonLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName("button-layout");
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save, cancel);
        editorLayoutDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setClassName("grid-wrapper");
        splitLayout.addToPrimary(wrapper);
        wrapper.add(grid);
    }

    private void refreshGrid() {
        grid.select(null);
        grid.getDataProvider().refreshAll();
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(Task value) {
        this.task = value;
        binder.readBean(this.task);

    }
}
