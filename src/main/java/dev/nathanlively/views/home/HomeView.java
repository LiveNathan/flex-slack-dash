package dev.nathanlively.views.home;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import dev.nathanlively.data.SamplePerson;
import dev.nathanlively.views.MainLayout;
import jakarta.annotation.security.PermitAll;

@PageTitle("Home")
@Route(value = "", layout = MainLayout.class)
@PermitAll
@Uses(Icon.class)
public class HomeView extends Composite<VerticalLayout> {

    public HomeView() {
        HorizontalLayout layoutRow = new HorizontalLayout();
        H2 h2 = new H2();
        HorizontalLayout layoutRow2 = new HorizontalLayout();
        VerticalLayout layoutColumn2 = new VerticalLayout();
        H2 h22 = new H2();
        Grid stripedGrid = new Grid(SamplePerson.class);
        VerticalLayout layoutColumn3 = new VerticalLayout();
        H2 h23 = new H2();
        Grid stripedGrid2 = new Grid(SamplePerson.class);
        VerticalLayout layoutColumn4 = new VerticalLayout();
        H2 h24 = new H2();
        Grid stripedGrid3 = new Grid(SamplePerson.class);
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        layoutRow.setWidthFull();
        getContent().setFlexGrow(1.0, layoutRow);
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.getStyle().set("flex-grow", "1");
        h2.setText("Schedule");
        h2.setWidth("max-content");
        layoutRow2.setWidthFull();
        getContent().setFlexGrow(1.0, layoutRow2);
        layoutRow2.addClassName(Gap.MEDIUM);
        layoutRow2.setWidth("100%");
        layoutRow2.getStyle().set("flex-grow", "1");
        layoutColumn2.setHeightFull();
        layoutRow2.setFlexGrow(1.0, layoutColumn2);
        layoutColumn2.setWidth("100%");
        layoutColumn2.getStyle().set("flex-grow", "1");
        h22.setText("Upcoming Events");
        h22.setWidth("max-content");
        stripedGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        stripedGrid.setWidth("100%");
        stripedGrid.getStyle().set("flex-grow", "0");
//        setGridSampleData(stripedGrid);
        layoutColumn3.setHeightFull();
        layoutRow2.setFlexGrow(1.0, layoutColumn3);
        layoutColumn3.setWidth("100%");
        layoutColumn3.getStyle().set("flex-grow", "1");
        h23.setText("Recent Activity");
        h23.setWidth("max-content");
        stripedGrid2.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        stripedGrid2.setWidth("100%");
        stripedGrid2.getStyle().set("flex-grow", "0");
//        setGridSampleData(stripedGrid2);
        layoutColumn4.setHeightFull();
        layoutRow2.setFlexGrow(1.0, layoutColumn4);
        layoutColumn4.setWidth("100%");
        layoutColumn4.getStyle().set("flex-grow", "1");
        h24.setText("Your Tasks");
        h24.setWidth("max-content");
        stripedGrid3.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        stripedGrid3.setWidth("100%");
        stripedGrid3.getStyle().set("flex-grow", "0");
//        setGridSampleData(stripedGrid3);
        getContent().add(layoutRow);
        layoutRow.add(h2);
        getContent().add(layoutRow2);
        layoutRow2.add(layoutColumn2);
        layoutColumn2.add(h22);
        layoutColumn2.add(stripedGrid);
        layoutRow2.add(layoutColumn3);
        layoutColumn3.add(h23);
        layoutColumn3.add(stripedGrid2);
        layoutRow2.add(layoutColumn4);
        layoutColumn4.add(h24);
        layoutColumn4.add(stripedGrid3);
    }

//    private void setGridSampleData(Grid grid) {
//        grid.setItems(query -> samplePersonService.list(
//                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
//                .stream());
//    }

}
