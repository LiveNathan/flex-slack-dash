package dev.nathanlively.adapter.in.web.projectdetails;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import dev.nathanlively.adapter.in.web.MainLayout;
import jakarta.annotation.security.PermitAll;

@PageTitle("Project Details")
@Route(value = "project-details", layout = MainLayout.class)
@PermitAll
public class ProjectDetailsView extends Composite<VerticalLayout> {

    public ProjectDetailsView() {
        HorizontalLayout layoutRow = new HorizontalLayout();
        H1 h1 = new H1();
        HorizontalLayout layoutRow2 = new HorizontalLayout();
        VerticalLayout layoutColumn2 = new VerticalLayout();
        H2 h2 = new H2();
        Paragraph textMedium = new Paragraph();
        VerticalLayout layoutColumn3 = new VerticalLayout();
        H2 h22 = new H2();
        Paragraph textMedium2 = new Paragraph();
        VerticalLayout layoutColumn4 = new VerticalLayout();
        H2 h23 = new H2();
        Paragraph textMedium3 = new Paragraph();
        HorizontalLayout layoutRow3 = new HorizontalLayout();
        VerticalLayout layoutColumn5 = new VerticalLayout();
        H3 h3 = new H3();
        VerticalLayout layoutColumn6 = new VerticalLayout();
        H3 h32 = new H3();
        VerticalLayout layoutColumn7 = new VerticalLayout();
        H3 h33 = new H3();
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        layoutRow.setWidthFull();
        getContent().setFlexGrow(1.0, layoutRow);
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.setHeight("min-content");
        h1.setText("Event A");
        h1.setWidth("max-content");
        layoutRow2.setWidthFull();
        getContent().setFlexGrow(1.0, layoutRow2);
        layoutRow2.addClassName(Gap.MEDIUM);
        layoutRow2.setWidth("100%");
        layoutRow2.getStyle().set("flex-grow", "1");
        layoutColumn2.setHeightFull();
        layoutRow2.setFlexGrow(1.0, layoutColumn2);
        layoutColumn2.setWidth("100%");
        layoutColumn2.getStyle().set("flex-grow", "1");
        h2.setText("Overview");
        h2.setWidth("max-content");
        textMedium.setText(
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.");
        textMedium.setWidth("100%");
        textMedium.getStyle().set("font-size", "var(--lumo-font-size-m)");
        layoutColumn3.setHeightFull();
        layoutRow2.setFlexGrow(1.0, layoutColumn3);
        layoutColumn3.setWidth("100%");
        layoutColumn3.getStyle().set("flex-grow", "1");
        h22.setText("Venue");
        h22.setWidth("max-content");
        textMedium2.setText(
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.");
        textMedium2.setWidth("100%");
        textMedium2.getStyle().set("font-size", "var(--lumo-font-size-m)");
        layoutColumn4.setHeightFull();
        layoutRow2.setFlexGrow(1.0, layoutColumn4);
        layoutColumn4.setWidth("100%");
        layoutColumn4.getStyle().set("flex-grow", "1");
        h23.setText("Travel");
        h23.setWidth("max-content");
        textMedium3.setText(
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.");
        textMedium3.setWidth("100%");
        textMedium3.getStyle().set("font-size", "var(--lumo-font-size-m)");
        layoutRow3.setWidthFull();
        getContent().setFlexGrow(1.0, layoutRow3);
        layoutRow3.addClassName(Gap.MEDIUM);
        layoutRow3.setWidth("100%");
        layoutRow3.getStyle().set("flex-grow", "1");
        layoutColumn5.setHeightFull();
        layoutRow3.setFlexGrow(1.0, layoutColumn5);
        layoutColumn5.setWidth("100%");
        layoutColumn5.getStyle().set("flex-grow", "1");
        h3.setText("Tasks");
        h3.setWidth("max-content");
        layoutColumn6.setHeightFull();
        layoutRow3.setFlexGrow(1.0, layoutColumn6);
        layoutColumn6.setWidth("100%");
        layoutColumn6.getStyle().set("flex-grow", "1");
        h32.setText("Activity");
        h32.setWidth("max-content");
        layoutColumn7.setHeightFull();
        layoutRow3.setFlexGrow(1.0, layoutColumn7);
        layoutColumn7.setWidth("100%");
        layoutColumn7.getStyle().set("flex-grow", "1");
        h33.setText("Files");
        h33.setWidth("max-content");
        getContent().add(layoutRow);
        layoutRow.add(h1);
        getContent().add(layoutRow2);
        layoutRow2.add(layoutColumn2);
        layoutColumn2.add(h2);
        layoutColumn2.add(textMedium);
        layoutRow2.add(layoutColumn3);
        layoutColumn3.add(h22);
        layoutColumn3.add(textMedium2);
        layoutRow2.add(layoutColumn4);
        layoutColumn4.add(h23);
        layoutColumn4.add(textMedium3);
        getContent().add(layoutRow3);
        layoutRow3.add(layoutColumn5);
        layoutColumn5.add(h3);
        layoutRow3.add(layoutColumn6);
        layoutColumn6.add(h32);
        layoutRow3.add(layoutColumn7);
        layoutColumn7.add(h33);
    }
}
