package bettinger.david.taskchains.ui;

import bettinger.david.taskchains.ui.views.taskchains.TaskChainsListView;
import bettinger.david.taskchains.ui.views.usermanagement.UserListView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;




@PWA(
        name = "Task-Chains App",
        shortName = "TC App",
        enableInstallPrompt = false
)
@CssImport("./styles/shared-styles.css")
public class MainLayout extends AppLayout {

    public MainLayout() {
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H1 logo = new H1("Task-Chains App");
        logo.addClassName("logo");

        Anchor logout = new Anchor("/logout", "Log out");

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo, logout);
        header.addClassName("header");
        header.setWidth("100%");
        header.expand(logo);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        addToNavbar(header);
    }

    private void createDrawer() {
        RouterLink userManagementLink = new RouterLink("Manage Users", UserListView.class);
        userManagementLink.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink taskChainLink = new RouterLink("Create Task-Chains", TaskChainsListView.class);
        taskChainLink.setHighlightCondition(HighlightConditions.sameLocation());

        addToDrawer(new VerticalLayout(
                userManagementLink,
                taskChainLink
        ));
    }

}
