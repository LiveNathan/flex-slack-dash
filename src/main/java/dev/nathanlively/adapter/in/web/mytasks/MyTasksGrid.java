package dev.nathanlively.adapter.in.web.mytasks;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import dev.nathanlively.application.ReadTask;
import dev.nathanlively.domain.Account;
import dev.nathanlively.security.AuthenticatedUser;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;
import java.util.stream.Stream;

public class MyTasksGrid extends Grid<TaskDto> {
    private final AuthenticatedUser authenticatedUser;
    private final ReadTask readTask;
    private final String TASK_EDIT_ROUTE_TEMPLATE;

    public MyTasksGrid(AuthenticatedUser authenticatedUser, ReadTask readTask, String taskEditRouteTemplate) {
        super(TaskDto.class, false);
        this.authenticatedUser = authenticatedUser;
        this.readTask = readTask;
        TASK_EDIT_ROUTE_TEMPLATE = taskEditRouteTemplate;
        configureGrid();
    }

    private void configureGrid() {
        Optional<Account> maybeUser = authenticatedUser.get();
        if (maybeUser.isPresent()) {
            this.addColumn(TaskDto::getTitle).setAutoWidth(true);
            this.setItems(query -> fetchTasks(query, maybeUser.get().username()));
            this.addThemeVariants(GridVariant.LUMO_NO_BORDER);
            // when a row is selected or deselected, populate form
            this.asSingleSelect().addValueChangeListener(event -> {
                if (event.getValue() != null) {
                    UI.getCurrent().navigate(String.format(TASK_EDIT_ROUTE_TEMPLATE, event.getValue().id()));
                } else {
                    clearForm();
                    UI.getCurrent().navigate(MyTasksView.class);
                }
            });
        }
    }

    private Stream<TaskDto> fetchTasks(Query<TaskDto, ?> query, String username) {
        return readTask.all(
                PageRequest.of(query.getPage(), query.getPageSize(),
                        VaadinSpringDataHelpers.toSpringDataSort(query)),
                username).stream();
    }

    public void clearForm() {
        this.deselectAll();
    }

    public void refreshGrid() {
        this.select(null);
        this.getDataProvider().refreshAll();
    }
}
