package com.tallerautomotizoptimus.application.views.reportes;

import java.util.Collection;
import java.util.List;

import com.tallerautomotizoptimus.application.controller.AlmacenInteractor;
import com.tallerautomotizoptimus.application.data.entity.Almacen;
import com.tallerautomotizoptimus.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.contextmenu.GridContextMenu;
import com.vaadin.flow.component.grid.contextmenu.GridMenuItem;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


@PageTitle("Almacen")
@Route(value = "almacen/:almacenID?/:action?(edit)", layout = MainLayout.class)
@Uses(Icon.class)
public class AlmacenView extends Div implements AlmacenViewModel {

    private static final long serialVersionUID = 1L;
    private final String ALMACEN_ID = "almacenID"; 
    private final String ALMACEN_EDIT_ROUTE_TEMPLATE = "almacen/%s/edit";
    
    private final Grid<Almacen> grid = new Grid<>(Almacen.class, false);  
    
    
    private IntegerField idalmacen;
	private TextField nmrepuesto;
	private TextField nombre;
	private IntegerField cantstock;
	private IntegerField minstock;
	private IntegerField maxstock;
	
    private final Button cancel = new Button("Cancelar");
    private final Button save = new Button("Guardar");

    
    private Almacen almacen;
    private AlmacenInteractor controlador;
    private List<Almacen> almacenes;
    
	public AlmacenView() {
    	addClassNames("almacen-view");
    	
    	
        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSplitterPosition(75);
        
        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        grid.setColumnReorderingAllowed(true);
        grid.addColumn("nombre").setHeader("Almacen").setAutoWidth(true);
        grid.addColumn("nmrepuesto").setHeader("Repuesto").setAutoWidth(true);
        grid.addColumn("cantstock").setHeader("Cantidad Stock").setAutoWidth(true);
        grid.addColumn("minstock").setHeader("Cant Minimo").setAutoWidth(true);
        grid.addColumn("maxstock").setHeader("Cant Maxima").setAutoWidth(true);
        
        
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(ALMACEN_EDIT_ROUTE_TEMPLATE, event.getValue().getIdalmacen()));
            } else {
                clearForm();
                UI.getCurrent().navigate(AlmacenView.class);
            }
        });
         
      //GRID CONTEXT MENU
        GridContextMenu<Almacen> menu = grid.addContextMenu();
        
        GridMenuItem<Almacen> editar = menu.addItem("Editar", event -> {
        	if (event != null && event.getItem() != null ) {
        		Almacen editarMarca = event.getItem().get();
        		populateForm(editarMarca);
        	
        	
        	}
        });
           GridMenuItem<Almacen> eliminar = menu.addItem("Eliminar", event -> {
        	if (event != null && event.getItem() != null) {
        		Almacen almacenEliminar = event.getItem().get();
        		
        		ConfirmDialog dialog = new ConfirmDialog();
                dialog.setHeader("¿Eliminar \'"+almacenEliminar.getNombre()+"\'?");
                dialog.setText("¿Estás seguro de que deseas eliminar de forma permanente este Almacen?");

                dialog.setCancelable(true);

                dialog.setConfirmText("Eliminar");
                dialog.setCancelText("Cancelar");
                dialog.setConfirmButtonTheme("error primary");

                dialog.addConfirmListener(eventDialog -> {
                	//controlador.eliminarMarca(almacenEliminar);
                	actualizarPantalla();
                });
        		
                dialog.open();
        	}
        });
        menu.add(new Hr());   
        GridMenuItem<Almacen> reporte = menu.addItem("Reporte", event -> {
        	Notification.show("Generando Reporte");
        	generarReporte();
        });
        reporte.addComponentAsFirst(createIcon(VaadinIcon.PRINT));
        editar.addComponentAsFirst(createIcon(VaadinIcon.EDIT));
        eliminar.addComponentAsFirst(createIcon(VaadinIcon.TRASH));
        
        
        //consultarAlmacen();
        
        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });
        
        save.addClickListener(e -> {
        	
        	
        });
        
        
    }
	
	
	private void consultarAlmacen() {
		controlador.consultarAlmacen();
	}

	private Component createIcon(VaadinIcon vaadinIcon) {
        Icon icon = vaadinIcon.create();
        icon.getStyle().set("color", "var(--lumo-secondary-text-color)")
                .set("margin-inline-end", "var(--lumo-space-s")
                .set("padding", "var(--lumo-space-xs");
        return icon;
    }

	private void generarReporte() {
		// TODO Auto-generated method stub
		
	}

	private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("editor-layout");

        Div editorDiv = new Div();
        editorDiv.setClassName("editor");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        
        nombre = new TextField();
        nombre.setLabel("Almacen");
        
        nmrepuesto = new TextField();
        nmrepuesto.setLabel("Repuesto");
        
        cantstock = new IntegerField();
        cantstock.setLabel("Cantidad Stock");
        
        minstock = new IntegerField();
        minstock.setLabel("Stock Minimo");
        
        maxstock = new IntegerField();
        maxstock.setLabel("Stock Maximo");
        
        formLayout.add(nombre, nmrepuesto, cantstock, minstock, maxstock);
        editorDiv.add(formLayout);
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

	private void populateForm(Almacen value) {
		this.almacen = value;
		if(value == null) {
			nombre.setValue("");
			nmrepuesto.setValue("");
			cantstock.setValue(0);
			minstock.setValue(0);
			maxstock.setValue(0);
		}else {
			nombre.setValue(value.getNombre());
			nmrepuesto.setValue(value.getNmrepuesto());
			cantstock.setValue(value.getCantstock());
			minstock.setValue(value.getMinstock());
			maxstock.setValue(value.getMaxstock());
			
		}
	}

    private void actualizarPantalla() {
    	clearForm();
		refreshGrid();
		//consultarAlmacen();
		UI.getCurrent().navigate(AlmacenView.class);
    }

	@Override
	public void refrescarAlmacen(List<Almacen> almacen) {
		Collection<Almacen> collectionItems = almacen;
		grid.setItems(collectionItems);
		this.almacenes = almacen;
	}

	@Override
	public void msjCreacionAlmacen(Almacen nuevo, String mensaje) {
		Notification.show(String.format(mensaje, nuevo.getNombre()));
		actualizarPantalla();
	}

	@Override
	public void msjActualizarAlmacen(Almacen actualizar, String mensaje) {
		Notification.show(String.format(mensaje, actualizar.getNombre()));
		actualizarPantalla();
		
	}

	@Override
	public void msjEliminarAlmacen(Almacen eliminar, String mensaje) {
		Notification.show(String.format(mensaje, eliminar.getNombre()));
		actualizarPantalla();
		
	}

    
}
