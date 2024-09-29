package dev.nathanlively.adapter.in.web.taskmanager;

import com.vaadin.collaborationengine.CollaborationAvatarGroup;
import com.vaadin.collaborationengine.CollaborationBinder;
import com.vaadin.collaborationengine.UserInfo;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import dev.nathanlively.adapter.in.web.MainLayout;
import dev.nathanlively.application.ReadTask;
import dev.nathanlively.domain.Task;
import jakarta.annotation.security.RolesAllowed;

import java.util.Optional;
import java.util.UUID;

@PageTitle("Task Manager")
@Route(value = "task-manager/:taskID?/:action?(edit)", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class TaskManagerView extends Div implements BeforeEnterObserver {

    private final String TASK_ID = "taskID";
    private final String TASK_EDIT_ROUTE_TEMPLATE = "task-manager/%s/edit";

    private final Grid<Task> grid = new Grid<>(Task.class, false);

    CollaborationAvatarGroup avatarGroup;

    private TextField description;
    private TextField event;
    private TextField assignedTo;
    private TextField dueDate;
    private TextField status;

    private final Button cancel = new Button("Cancel");
    private final Button save = new Button("Save");

    private final CollaborationBinder<Task> binder;

    private Task task;

    private final ReadTask readTask;

    public TaskManagerView(ReadTask readTask) {
        this.readTask = readTask;
        addClassNames("task-manager-view");

        // UserInfo is used by Collaboration Engine and is used to share details
        // of users to each other to able collaboration. Replace this with
        // information about the actual user that is logged, providing a user
        // identifier, and the user's real name. You can also provide the users
        // avatar by passing an url to the image as a third parameter, or by
        // configuring an `ImageProvider` to `avatarGroup`.
        UserInfo userInfo = new UserInfo(UUID.randomUUID().toString(), "Steve Lange");

        // Create UI
        SplitLayout splitLayout = new SplitLayout();

        avatarGroup = new CollaborationAvatarGroup(userInfo, null);
        avatarGroup.getStyle().set("visibility", "hidden");

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        grid.addColumn("description").setAutoWidth(true);
        grid.addColumn("event").setAutoWidth(true);
        grid.addColumn("assignedTo").setAutoWidth(true);
        grid.addColumn("dueDate").setAutoWidth(true);
        grid.addColumn("status").setAutoWidth(true);
//        grid.setItems(query -> taskService.all(
//                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
//                .stream());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(TASK_EDIT_ROUTE_TEMPLATE, event.getValue().id()));
            } else {
                clearForm();
                UI.getCurrent().navigate(TaskManagerView.class);
            }
        });

        // Configure Form
        binder = new CollaborationBinder<>(Task.class, userInfo);

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
                UI.getCurrent().navigate(TaskManagerView.class);
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
                Notification.show(String.format("The requested task was not found, ID = %d", taskId.get()), 3000,
                        Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(TaskManagerView.class);
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
        description = new TextField("Description");
        event = new TextField("Event");
        assignedTo = new TextField("Assigned To");
        dueDate = new TextField("Due Date");
        status = new TextField("Status");
        formLayout.add(description, event, assignedTo, dueDate, status);

        editorDiv.add(avatarGroup, formLayout);
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
        String topic = null;
        if (this.task != null && this.task.id() != null) {
            topic = "task/" + this.task.id();
            avatarGroup.getStyle().set("visibility", "visible");
        } else {
            avatarGroup.getStyle().set("visibility", "hidden");
        }
        binder.setTopic(topic, () -> this.task);
        avatarGroup.setTopic(topic);

    }
}
