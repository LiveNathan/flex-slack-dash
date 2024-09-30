package dev.nathanlively.adapter.in.web.mytasks;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import dev.nathanlively.adapter.in.web.MainLayout;
import dev.nathanlively.application.ReadTask;
import dev.nathanlively.application.UpdateTask;
import dev.nathanlively.domain.Task;
import dev.nathanlively.security.AuthenticatedUser;
import jakarta.annotation.security.PermitAll;

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
    private final Button save = new Button("Save");

    private Task task;

    private final ReadTask readTask;
    private final UpdateTask updateTask;

    public MyTasksView(AuthenticatedUser authenticatedUser, BeanValidationBinder<Task> binder,
                       ReadTask readTask, UpdateTask updateTask) {
        this.authenticatedUser = authenticatedUser;
        this.readTask = readTask;
        this.updateTask = updateTask;
        addClassNames("my-tasks-view");

        // Create UI
        SplitLayout splitLayout = new SplitLayout();
        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);
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

        MyTasksForm myTasksForm = new MyTasksForm();

        editorDiv.add(myTasksForm);
        splitLayout.addToSecondary(editorLayoutDiv);

        MyTasksFormBinder formBinder = new MyTasksFormBinder(myTasksForm, updateTask);
        formBinder.addBindingAndValidation();
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
    }
}
