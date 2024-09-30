package dev.nathanlively.adapter.in.web.mytasks;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.splitlayout.SplitLayout;
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
    private final String TASK_ID = "taskID";
    private final MyTasksGrid myTasksGrid;
    private final ReadTask readTask;
    private final UpdateTask updateTask;
    private final AuthenticatedUser authenticatedUser;
    private final String TASK_EDIT_ROUTE_TEMPLATE = "my-tasks/%s/edit";

    public MyTasksView(ReadTask readTask, UpdateTask updateTask, AuthenticatedUser authenticatedUser) {
        this.readTask = readTask;
        this.updateTask = updateTask;
        this.authenticatedUser = authenticatedUser;
        addClassNames("my-tasks-view");

        SplitLayout splitLayout = new SplitLayout();
        myTasksGrid = new MyTasksGrid(authenticatedUser, readTask, TASK_EDIT_ROUTE_TEMPLATE);
        Div wrapper = new Div();
        wrapper.setClassName("grid-wrapper");
        wrapper.add(myTasksGrid);

        createEditorLayout(splitLayout);

        splitLayout.addToPrimary(wrapper);
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

    private void refreshGrid() {
        myTasksGrid.select(null);
        myTasksGrid.getDataProvider().refreshAll();
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(Task value) {
        this.task = value;
    }
}
