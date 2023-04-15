package com.tallerautomotizoptimus.application.views.productos;



import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.tallerautomotizoptimus.application.controller.MarcaRepuestosInteractor;
import com.tallerautomotizoptimus.application.controller.RepuestoInteractor;
import com.tallerautomotizoptimus.application.controller.RepuestoInteractorImpl;
import com.tallerautomotizoptimus.application.data.entity.MarcaRepuesto;
import com.tallerautomotizoptimus.application.data.entity.Repuesto;
import com.tallerautomotizoptimus.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.contextmenu.GridContextMenu;
import com.vaadin.flow.component.grid.contextmenu.GridMenuItem;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@PageTitle("Productos")
@Route(value = "productos/:repuestoID?/:action?(edit)", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@Uses(Icon.class)
public class RepuestosView extends Div implements BeforeEnterObserver, RepuestosViewModel {

    private static final long serialVersionUID = 1L;
	private final String REPUESTO_ID = "repuestoID";
    private final String REPUESTO_EDIT_ROUTE_TEMPLATE = "productos/%s/edit";

    private final Grid<Repuesto> grid = new Grid<>(Repuesto.class, false);
    
    //private NumberField idrepuesto;
    private TextField nombre;
    private TextField numeroserie;
    private NumberField cantidad;
	private NumberField preciocosto;
	private NumberField precio;
	private TextField aniovehiculo;
	private TextArea compvehiculo;
	private TextField marca;
	private ComboBox<MarcaRepuesto> cmarca;
	private TextField clasificacion;
	private TextField fabricante;
    

	private final Button cancelar = new Button("Cancelar");
    private final Button guardar = new Button("Guardar");
    

    private Repuesto repuesto;
    private RepuestoInteractor controlador;
    private List<Repuesto> repuestos;
    
    private MarcaRepuesto marcarepuesto;
    
    private List<MarcaRepuesto> marcaa;
   

    public RepuestosView() {
		addClassNames("repuestos-view");
          
	controlador = new RepuestoInteractorImpl(this);
	
        // Create UI
        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setOrientation(SplitLayout.Orientation.VERTICAL);
        
        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
       // LitRenderer<Repuesto> imagenRenderer = LitRenderer.<Repuesto>of(
       // "<span style='border-radius: 50%; overflow: hidden; display: flex; align-items: left; justify-content: center; width: 64px; height: 64px'></span>");
       
        grid.setColumnReorderingAllowed(true);
        grid.addColumn("idrepuesto").setAutoWidth(true).setHeader("No. Repuesto");
        grid.addColumn("nombre").setAutoWidth(true).setHeader("Nombre");
        grid.addColumn("numeroserie").setAutoWidth(true).setHeader("Numero de Serie");
        grid.addColumn("cantidad").setAutoWidth(true).setHeader("Cantidad").setTextAlign(ColumnTextAlign.CENTER);
        grid.addColumn("preciocosto").setAutoWidth(true).setHeader("Precio Costo").setTextAlign(ColumnTextAlign.END);
        grid.addColumn("precio").setAutoWidth(true).setHeader("Precio").setTextAlign(ColumnTextAlign.END);
        grid.addColumn("aniovehiculo").setAutoWidth(true).setHeader("Año Vehiculo");
        grid.addColumn("compvehiculo").setAutoWidth(true).setHeader("Compatiilidad");
        grid.addColumn("marca").setAutoWidth(true).setHeader("Marca Repuesto");
        grid.addColumn("clasificacion").setAutoWidth(true).setHeader("Clasificación");
        grid.addColumn("fabricante").setAutoWidth(true).setHeader("Fabricante");
        
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
      
        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(REPUESTO_EDIT_ROUTE_TEMPLATE, event.getValue().getIdrepuesto()));
            } else {
                clearForm();
                UI.getCurrent().navigate(RepuestosView.class);
            }
        });
        
        
         GridContextMenu<Repuesto> menu = grid.addContextMenu();
		    
        GridMenuItem<Repuesto> delete = menu.addItem("Eliminar", event -> {	
        	if (event != null && event.getItem() != null) {
        		Repuesto repuestoEliminar = event.getItem().get();
        		
        		ConfirmDialog dialog = new ConfirmDialog();
                dialog.setHeader("¿Eliminar \'"+repuestoEliminar.getNombre() + "("+repuestoEliminar.getNumeroserie()+")\'?");
                dialog.setText("¿Estás seguro de que deseas eliminar de forma permanente este Repuesto?");

                dialog.setCancelable(true);

                dialog.setConfirmText("Eliminar");
                dialog.setCancelText("Cancelar");
                dialog.setConfirmButtonTheme("error primary");

                dialog.addConfirmListener(eventDialog -> {
                
                });
        		
                dialog.open();
        	}
        });
        
        delete.addComponentAsFirst(createIcon(VaadinIcon.TRASH));
        
        
        consultarRepuestos();
        consultarMarcas();
        
        cancelar.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        guardar.addClickListener(e -> {
            /*try {
                
                clearForm();
                refreshGrid();
                Notification.show("Data updated");
                UI.getCurrent().navigate(RepuestosView.class);
            } catch (ObjectOptimisticLockingFailureException exception) {
                Notification n = Notification.show(
                        "Error updating the data. Somebody else has updated the record while you were making changes.");
                n.setPosition(Position.MIDDLE);
                n.addThemeVariants(NotificationVariant.LUMO_ERROR);
                
            } catch (ValidationException validationException) {
                Notification.show("Failed to update the data. Check again that all values are valid");
            }*/
        });
    }

    
    private void consultarRepuestos() {
    	controlador.consultarRepuestos();		
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
        Optional<Long> repuestoId = event.getRouteParameters().get(REPUESTO_ID).map(Long::parseLong);
        if (repuestoId.isPresent()) {
        	for (Repuesto repuesto : repuestos) {
        		if(repuesto.getIdrepuesto() == repuestoId.get()) {
        			populateForm(repuesto);
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
        
       // idrepuesto = new NumberField();
       //idrepuesto.setLabel("ID");
        
        nombre = new TextField("Nombre");
        numeroserie = new TextField();
        numeroserie.setLabel("Numero Serie");
        
        cantidad = new NumberField();
        cantidad.setLabel("Cantidad");
        
        Div dollarPrefix1 = new Div();
        preciocosto = new NumberField();
        preciocosto.setLabel("Precio Costo");
        preciocosto.setValue(0.0);
        dollarPrefix1.setText("L");
        preciocosto.setPrefixComponent(dollarPrefix1);
        
        Div dollarPrefix = new Div();
        precio = new NumberField();
        precio.setLabel("Precio");
        precio.setValue(0.0);
        dollarPrefix.setText("L");
        precio.setPrefixComponent(dollarPrefix);
        
        aniovehiculo = new TextField();
        aniovehiculo.setLabel("Año Vehiculo");
        
        int charLimit = 250;
        compvehiculo = new TextArea();
        compvehiculo.setWidthFull();
        compvehiculo.setLabel("Compatibilidad Vehiculo");
        compvehiculo.setMaxLength(charLimit);
        compvehiculo.setValueChangeMode(ValueChangeMode.EAGER);
        compvehiculo.addValueChangeListener(e -> {
            e.getSource().setHelperText(e.getValue().length() + "/" + charLimit);
        });
         
      
        marca = new TextField();
        marca.setLabel("Marca");
        
        
        cmarca = new ComboBox<>();
        cmarca.setLabel("Marcas");
        cmarca.setItemLabelGenerator(MarcaRepuesto::getNombremarca);
        
        
        clasificacion = new TextField();
        clasificacion.setLabel("Clasificacion");
        
        fabricante = new TextField();
        fabricante.setLabel("Fabricante");
        
        
        
        formLayout.add(nombre, numeroserie, cantidad, preciocosto, precio,
        		aniovehiculo, compvehiculo, cmarca, clasificacion, fabricante);

        editorDiv.add(formLayout);
        createButtonLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName("button-layout");
        cancelar.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        guardar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(guardar, cancelar);
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
        cmarca.setValue(null);
    }

    private void populateForm(Repuesto value, MarcaRepuesto value2) {
        this.repuesto = value;
        if (value == null && value2 == null) {
        	//idrepuesto.setValue("");
        	nombre.setValue("");
        	numeroserie.setValue("");
        	cantidad.setValue((double)0);
        	preciocosto.setValue(0d);
        	precio.setValue(0d);
        	aniovehiculo.setValue("");
        	compvehiculo.setValue("");
        	marca.setValue("");
        	cmarca.setValue(null);
        	clasificacion.setValue("");
        	fabricante.setValue("");
        	
        	
        }else {
        	//idrepuesto.setValue((double) value.getIdrepuesto());
        	nombre.setValue(value.getNombre());
        	numeroserie.setValue(value.getNumeroserie());
        	cantidad.setValue((double) value.getCantidad());
        	preciocosto.setValue(value.getPreciocosto());
        	precio.setValue(value.getPrecio());
        	aniovehiculo.setValue(value.getAniovehiculo());
        	compvehiculo.setValue(value.getCompvehiculo());
        	marca.setValue(value.getMarca());
        	cmarca.setValue(value2.getNombremarca());
        	clasificacion.setValue(value.getClasificacion());
        	fabricante.setValue(value.getFabricante());
        }
     }
    
    private void populateForm2(MarcaRepuesto value) {
    	this.marcarepuesto = value;
    	
    }

	@Override
	public void refrescarGridRepuestos(List<Repuesto> repuesto) {
		Collection<Repuesto> collectionItems = repuesto;
		grid.setItems(collectionItems);
		this.repuestos = repuesto;
	}

	@Override
	public void refrescarMarca(List<MarcaRepuesto> marcas) {
		Collection<MarcaRepuesto> collectionItems = marcas;
		cmarca.setItems(collectionItems);
		//this.marcaa = marcas;
		
	}
	
 
	
	@Override
	public void msjCrearRepuesto(Repuesto nuevo, String mensaje) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void msjActualizarRepuesto(Repuesto nuevo, String mensaje) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void msjEliminarRepuesto(Repuesto nuevo, String mensaje) {
		// TODO Auto-generated method stub
		
	}




	
}
