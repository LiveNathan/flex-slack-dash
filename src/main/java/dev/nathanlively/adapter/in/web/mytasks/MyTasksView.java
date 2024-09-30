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
    private final String TASK_EDIT_ROUTE_TEMPLATE = "my-tasks/%s/edit";
    private MyTasksForm myTasksForm;

    public MyTasksView(ReadTask readTask, UpdateTask updateTask, AuthenticatedUser authenticatedUser) {
        this.readTask = readTask;
        this.updateTask = updateTask;
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
        Optional<String> taskId = event.getRouteParameters().get(TASK_ID);
        if (taskId.isPresent()) {
            Optional<TaskDto> taskFromBackend = readTask.get(taskId.get());
            if (taskFromBackend.isPresent()) {
                populateForm(taskFromBackend.get());
            } else {
                Notification.show(String.format("The requested task was not found, ID = %s", taskId.get()), 3000,
                        Notification.Position.BOTTOM_START);
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

        myTasksForm = new MyTasksForm();

        editorDiv.add(myTasksForm);
        splitLayout.addToSecondary(editorLayoutDiv);

        MyTasksFormBinder formBinder = new MyTasksFormBinder(myTasksForm, updateTask);
        formBinder.addBindingAndValidation();
    }

    private void refreshGrid() {
        myTasksGrid.select(null);
        myTasksGrid.getDataProvider().refreshAll();
    }

    private void populateForm(TaskDto task) {
        MyTasksFormBinder formBinder = new MyTasksFormBinder(myTasksForm, updateTask);
        formBinder.setTask(task);
    }

}
