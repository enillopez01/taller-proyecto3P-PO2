package com.tallerautomotizoptimus.application.views;


import com.tallerautomotizoptimus.application.components.appnav.AppNav;
import com.tallerautomotizoptimus.application.components.appnav.AppNavItem;
import com.tallerautomotizoptimus.application.views.clasificacionproductos.ClasificacionProductosView;
import com.tallerautomotizoptimus.application.views.fabricantevehiculo.FabricanteVehiculoView;
import com.tallerautomotizoptimus.application.views.marcarepuestos.MarcaRepuestosView;
import com.tallerautomotizoptimus.application.views.productos.ProductosView;
import com.tallerautomotizoptimus.application.views.reportes.ReportesView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.vaadin.lineawesome.LineAwesomeIcon;

/**
 * The main view is a top-level placeholder for other views.
 */
public class MainLayout extends AppLayout {

    private H2 viewTitle;

    public MainLayout() {
        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent();
    }

    private void addHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.getElement().setAttribute("aria-label", "Menu toggle");

        viewTitle = new H2();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

        addToNavbar(true, toggle, viewTitle);
    }

    private void addDrawerContent() {
        H1 appName = new H1("Taller Automotriz Optimus");
        appName.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);
        Header header = new Header(appName);

        Scroller scroller = new Scroller(createNavigation());

        addToDrawer(header, scroller, createFooter());
    }

    private AppNav createNavigation() {
        // AppNav is not yet an official component.
        // For documentation, visit https://github.com/vaadin/vcf-nav#readme
        AppNav nav = new AppNav();

        nav.addItem(new AppNavItem("Productos", ProductosView.class, LineAwesomeIcon.CART_PLUS_SOLID.create()));
        nav.addItem(new AppNavItem("Marca Repuestos", MarcaRepuestosView.class,
                LineAwesomeIcon.CHEVRON_CIRCLE_DOWN_SOLID.create()));
        nav.addItem(new AppNavItem("Fabricante Vehiculo", FabricanteVehiculoView.class,
                LineAwesomeIcon.CAR_SOLID.create()));
        nav.addItem(new AppNavItem("Clasificacion Productos", ClasificacionProductosView.class,
                LineAwesomeIcon.TH_LIST_SOLID.create()));
        nav.addItem(new AppNavItem("Reportes", ReportesView.class, LineAwesomeIcon.NEWSPAPER.create()));

        return nav;
    }

    private Footer createFooter() {
        Footer layout = new Footer();

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
