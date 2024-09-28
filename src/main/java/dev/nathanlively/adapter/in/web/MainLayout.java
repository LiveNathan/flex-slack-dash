package dev.nathanlively.adapter.in.web;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.auth.AccessAnnotationChecker;
import com.vaadin.flow.theme.lumo.LumoUtility;
import dev.nathanlively.adapter.in.web.home.HomeView;
import dev.nathanlively.adapter.in.web.mytasks.MyTasksView;
import dev.nathanlively.adapter.in.web.projectdetails.ProjectDetailsView;
import dev.nathanlively.adapter.in.web.search.SearchView;
import dev.nathanlively.adapter.in.web.taskmanager.TaskManagerView;
import dev.nathanlively.domain.Account;
import dev.nathanlively.security.AuthenticatedUser;
import org.vaadin.lineawesome.LineAwesomeIcon;

import java.io.ByteArrayInputStream;
import java.util.Optional;

public class MainLayout extends AppLayout {

    private H1 viewTitle;

    private final AuthenticatedUser authenticatedUser;
    private final AccessAnnotationChecker accessChecker;

    public MainLayout(AuthenticatedUser authenticatedUser, AccessAnnotationChecker accessChecker) {
        this.authenticatedUser = authenticatedUser;
        this.accessChecker = accessChecker;

        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent();
    }

    private void addHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.setAriaLabel("Menu toggle");

        viewTitle = new H1();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

        addToNavbar(true, toggle, viewTitle);
    }

    private void addDrawerContent() {
        Span appName = new Span("Flex Slack Dash");
        appName.addClassNames(LumoUtility.FontWeight.SEMIBOLD, LumoUtility.FontSize.LARGE);
        Header header = new Header(appName);

        Scroller scroller = new Scroller(createNavigation());

        addToDrawer(header, scroller, createFooter());
    }

    private SideNav createNavigation() {
        SideNav nav = new SideNav();

        if (accessChecker.hasAccess(HomeView.class)) {
            nav.addItem(new SideNavItem("Home", HomeView.class, LineAwesomeIcon.HOME_SOLID.create()));

        }
        if (accessChecker.hasAccess(ProjectDetailsView.class)) {
            nav.addItem(new SideNavItem("Project Details", ProjectDetailsView.class,
                    LineAwesomeIcon.PENCIL_RULER_SOLID.create()));

        }
        if (accessChecker.hasAccess(MyTasksView.class)) {
            nav.addItem(new SideNavItem("My Tasks", MyTasksView.class, LineAwesomeIcon.TASKS_SOLID.create()));

        }
        if (accessChecker.hasAccess(TaskManagerView.class)) {
            nav.addItem(new SideNavItem("Task Manager", TaskManagerView.class, LineAwesomeIcon.EDIT.create()));

        }
        if (accessChecker.hasAccess(SearchView.class)) {
            nav.addItem(new SideNavItem("Search", SearchView.class, LineAwesomeIcon.SEARCH_SOLID.create()));

        }

        return nav;
    }

    private Footer createFooter() {
        Footer layout = new Footer();

        Optional<Account> maybeUser = authenticatedUser.get();
        if (maybeUser.isPresent()) {
            Account account = maybeUser.get();

            String username;
            if (account.person() != null && account.person().name() != null) {
                username = account.person().name();
            } else {
                username = account.username();
            }
            Avatar avatar = new Avatar(username);
            StreamResource resource = new StreamResource("profile-pic",
                    () -> new ByteArrayInputStream(account.profilePicture()));
            avatar.setImageResource(resource);
            avatar.setThemeName("xsmall");
            avatar.getElement().setAttribute("tabindex", "-1");

            MenuBar userMenu = new MenuBar();
            userMenu.setThemeName("tertiary-inline contrast");

            MenuItem userName = userMenu.addItem("");
            Div div = new Div();
            div.add(avatar);
            div.add(account.username());
            div.add(new Icon("lumo", "dropdown"));
            div.getElement().getStyle().set("display", "flex");
            div.getElement().getStyle().set("align-items", "center");
            div.getElement().getStyle().set("gap", "var(--lumo-space-s)");
            userName.add(div);
            userName.getSubMenu().addItem("Sign out", _ -> authenticatedUser.logout());

            layout.add(userMenu);
        } else {
            Anchor loginLink = new Anchor("login", "Sign in");
            layout.add(loginLink);
        }

        return layout;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
}
