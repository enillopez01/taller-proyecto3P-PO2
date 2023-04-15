package com.tallerautomotizoptimus.application.views.marcarepuestos;


import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.orm.ObjectOptimisticLockingFailureException;


import com.tallerautomotizoptimus.application.controller.MarcaRepuestosInteractor;
import com.tallerautomotizoptimus.application.controller.MarcaRepuestosInteractorImpl;
import com.tallerautomotizoptimus.application.data.entity.MarcaRepuesto;
import com.tallerautomotizoptimus.application.data.service.ReportGenerator;
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
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Marca Repuestos")
@Route(value = "marcaRepuestos/:marcarepuestoID?/:action?(edit)", layout = MainLayout.class)
@Uses(Icon.class)
public class MarcaRepuestosView extends Div implements BeforeEnterObserver, MarcaRepuestosViewModel {
	

    private final String MARCAREPUESTO_ID = "marcarepuestoID";
    private final String MARCAREPUESTO_EDIT_ROUTE_TEMPLATE = "marcaRepuestos/%s/edit";

    private final Grid<MarcaRepuesto> grid = new Grid<>(MarcaRepuesto.class, false);

    //private NumberField idmarca;
    private TextField nombremarca;
    
    private final Button cancel = new Button("Cancelar");
    private final Button save = new Button("Guardar");

    
    private MarcaRepuesto marcarepuesto;
    private MarcaRepuestosInteractor controlador;  
    private List<MarcaRepuesto> marcas;
        
    
    public MarcaRepuestosView() {
        addClassNames("marca-repuestos-view");
        controlador = new MarcaRepuestosInteractorImpl(this);
        // Create UI
        SplitLayout splitLayout = new SplitLayout();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        grid.setColumnReorderingAllowed(true);
        grid.addColumn("idmarca").setAutoWidth(true).setHeader("Codigo Marca");
        grid.addColumn("nombremarca").setAutoWidth(true).setHeader("Nombre Marca");
                grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(MARCAREPUESTO_EDIT_ROUTE_TEMPLATE, event.getValue().getIdmarca()));
            } else {
                clearForm();
                UI.getCurrent().navigate(MarcaRepuestosView.class);
            }
        });

        //GRID CONTEXT MENU
        GridContextMenu<MarcaRepuesto> menu = grid.addContextMenu();
        
        GridMenuItem<MarcaRepuesto> editar = menu.addItem("Editar", event -> {
        	if (event != null && event.getItem() != null ) {
        		MarcaRepuesto editarMarca = event.getItem().get();
        		populateForm(editarMarca);
        	
        	
        	}
        });
           GridMenuItem<MarcaRepuesto> eliminar = menu.addItem("Eliminar", event -> {
        	if (event != null && event.getItem() != null) {
        		MarcaRepuesto marcaEliminar = event.getItem().get();
        		
        		ConfirmDialog dialog = new ConfirmDialog();
                dialog.setHeader("¿Eliminar \'"+marcaEliminar.getNombremarca()+"\'?");
                dialog.setText("¿Estás seguro de que deseas eliminar de forma permanente esta Marca de repuesto?");

                dialog.setCancelable(true);

                dialog.setConfirmText("Eliminar");
                dialog.setCancelText("Cancelar");
                dialog.setConfirmButtonTheme("error primary");

                dialog.addConfirmListener(eventDialog -> {
                	controlador.eliminarMarca(marcaEliminar);
                	actualizarPantalla();
                });
        		
                dialog.open();
        	}
        });
        menu.add(new Hr());   
        GridMenuItem<MarcaRepuesto> reporte = menu.addItem("Reporte", event -> {
        	Notification.show("Generando Reporte");
        	generarReporte();
        });
        reporte.addComponentAsFirst(createIcon(VaadinIcon.PRINT));
        editar.addComponentAsFirst(createIcon(VaadinIcon.EDIT));
        eliminar.addComponentAsFirst(createIcon(VaadinIcon.TRASH));
        
        
        // Configure Form
        
        consultarMarcas();
        
        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.marcarepuesto == null) {
                    //CREACION
                    this.marcarepuesto = new MarcaRepuesto();
                    this.marcarepuesto.setNombremarca(nombremarca.getValue());
                    
                    if(this.marcarepuesto.getNombremarca() == null) {
                    	Notification.show("El nombre de la Marca es requerido, favor ingresar la información");
                    }else {
                    	controlador.crearMarca(marcarepuesto);
                    }
                }else {
                	//ACTUALIZACION
                	this.marcarepuesto.setNombremarca(nombremarca.getValue());
                	if(this.marcarepuesto.getNombremarca() == null) {
                    	Notification.show("El nombre de la Marca es requerido, favor ingresar la información");
                    }else {
                    	controlador.actualizarMarca(marcarepuesto);
                    }
                }

                actualizarPantalla();
                
            } catch (ObjectOptimisticLockingFailureException exception) {
                Notification n = Notification.show(
                        "Error actualizando la data. Alguien mas actualizo el registro mientras tu actualizabas.");
                n.setPosition(Position.MIDDLE);
                n.addThemeVariants(NotificationVariant.LUMO_ERROR);
            } 
        });
    }
    
    
    private void generarReporte() {
    	ReportGenerator generador = new ReportGenerator();
    	
    }
    

    private void consultarMarcas() {
		controlador.consultarMarcas();
		
	}
    
	private Component createIcon(VaadinIcon vaadinIcon) {
        Icon icon = vaadinIcon.create();
        icon.getStyle().set("color", "var(--lumo-secondary-text-color)")
                .set("margin-inline-end", "var(--lumo-space-s")
                .set("padding", "var(--lumo-space-xs");
        return icon;
    }

	
	@Override
    public void beforeEnter(BeforeEnterEvent event) {
		Optional<Long> marcarepuestoId = event.getRouteParameters().get(MARCAREPUESTO_ID).map(Long::parseLong);
        if (marcarepuestoId.isPresent()) {
        	for (MarcaRepuesto marcarepuesto : marcas) {
        		if(marcarepuesto.getIdmarca() == marcarepuestoId.get()) {
        			populateForm(marcarepuesto);
        			break;
        			
        		}
        	}
        } 
    }
	
	
    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("editor-layout");

        Div editorDiv = new Div();
        editorDiv.setClassName("editor");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        //idmarca = new NumberField();
        nombremarca = new TextField("Nombre Marca");
        formLayout.add(nombremarca);

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

    private void populateForm(MarcaRepuesto value) {
        this.marcarepuesto = value;
        if(value == null) {
        	nombremarca.setValue(" ");	
        	
        	
        }else {
        	nombremarca.setValue(value.getNombremarca());
        }
    }

    
    private void actualizarPantalla() {
    	clearForm();
		refreshGrid();
		consultarMarcas();
		UI.getCurrent().navigate(MarcaRepuestosView.class);
;    	
    }
    
    
	@Override
	public void refrescarGridMarca(List<MarcaRepuesto> marca) {
		Collection<MarcaRepuesto> collectionItems = marca;
		grid.setItems(collectionItems);
		this.marcas = marca;
		
	}

	@Override
	public void msjCreacionMarca(MarcaRepuesto nuevo, String mensaje) {
		Notification.show(String.format(mensaje, nuevo.getNombremarca()));
		actualizarPantalla();
		
	}

	@Override
	public void msjActualizarMarca(MarcaRepuesto nuevo, String mensaje) {
		Notification.show(String.format(mensaje, nuevo.getNombremarca()));
		actualizarPantalla();
	}

	@Override
	public void msjEliminarMarca(MarcaRepuesto nuevo, String mensaje) {
		Notification.show(String.format(mensaje, nuevo.getNombremarca()));
		actualizarPantalla();
	}

}
