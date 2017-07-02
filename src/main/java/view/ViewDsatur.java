package view;

import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.layout.Pane;
import javafx.scene.Node;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.Vertex;
import javafx.scene.shape.Circle;
import javafx.scene.input.MouseEvent;
import javafx.scene.Group;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.event.Event;
import javafx.scene.control.Label;
import javafx.scene.control.CheckBox;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;

/**
 * @author Luiz Agner <agner@alunos.utfpr.edu.br>
 * 
 * MVC Design - View class which renders the GUI
 */

@SuppressWarnings("restriction")
public class ViewDsatur extends BorderPane {
	
	private ObservableList<Vertex> graph;
	private Pane canvas;
	private Group lines, vertexes, permanentLines;
	private TextField fieldPathIn;
	private Button buttonOpen, buttonSave, buttonSaveImage;
	private EventHandler<Event> eventHandler;
	private Label labelWarning, labelFinalValues;
	private CheckBox checkBoxShowLines;
	
    //some colors
    int[][] rgb = {	{255, 0, 0}, 
    				{0, 255, 0},
				 	{0, 0, 255},
				 	{255, 255, 0},
				 	{255, 0, 255},
				 	{0, 255, 255},
				 	{255, 255, 255},
				 	{0, 0, 0}};

	public ViewDsatur() {
		//where the graph is showed
		canvas = new Pane();
		lines = new Group();
		vertexes = new Group();
		permanentLines = new Group();
		canvas.setId("canvas");
		canvas.setPrefSize(1000, 800);
		canvas.getChildren().addAll(permanentLines, vertexes, lines);
		setCenter(canvas);
		//graph settings and path
		HBox hBoxIO = new HBox();
		VBox vBoxIO = new VBox();
		fieldPathIn = new TextField();
		buttonOpen = new Button("Abrir");
		buttonOpen.setId("open");
		buttonSave = new Button("Salvar");
		buttonSave.setId("save");
		buttonSaveImage = new Button("Salvar Imagem");
		buttonSaveImage.setId("saveImage");
		hBoxIO.getChildren().addAll(buttonOpen, buttonSave, buttonSaveImage);
		Text reminder = new Text("O arquivo de saída será salvo no\nmesmo caminho do arquivo de entrada.");
		Text textPathTitle = new Text("Insira o caminho:");
		checkBoxShowLines = new CheckBox("Mostrar linhas");
		//check box listener
		checkBoxShowLines.selectedProperty().addListener(new ChangeListener<Boolean>() {
	        public void changed(ObservableValue<? extends Boolean> ov,
	            Boolean old_val, Boolean new_val) {
	                renderPermanentLines(new_val);
	        }
	    });
		labelWarning = new Label();
		labelFinalValues = new Label();
		vBoxIO.getChildren().addAll(textPathTitle, fieldPathIn, hBoxIO, reminder, labelWarning, checkBoxShowLines, labelFinalValues);
		vBoxIO.setSpacing(5);
		vBoxIO.setPadding(new Insets(5, 5, 5, 5));
		setLeft(vBoxIO);
	}
	
	public void setList(ObservableList<Vertex> graph) {
		this.graph = graph;
	}
	
	public ObservableList<Vertex> getList() {
		return graph;
	}
	
	public void addEventHandler(EventHandler<Event> eventHandler) {
		this.eventHandler = eventHandler;
		buttonOpen.addEventHandler(ActionEvent.ACTION, eventHandler);
		buttonSave.addEventHandler(ActionEvent.ACTION, eventHandler);
		buttonSaveImage.addEventFilter(ActionEvent.ACTION, eventHandler);
	}
	
	/*
	 * @param n the vertex to render the lines
	 */
	public void renderVertex(Node n) {
		Circle c = (Circle)n;
		Vertex v1 = graph.get(Integer.parseInt(c.getId())-1);
		for(Vertex v2 : v1.getAdjacent()) {
			Line line = new Line();
			line.setStroke(Color.BLACK);
			line.setStrokeWidth(2);
			line.setStartX(v1.getX());
			line.setStartY(v1.getY());
			line.setEndX(v2.getX());
			line.setEndY(v2.getY());
			lines.getChildren().add(line);
		}
		vertexes.toFront();
	}
	
	/*
	 * @param newValue the value of the checkbox. true -> show gray liens, false -> do not show
	 */
	public void renderPermanentLines(Boolean newValue) {
		if(newValue) {
			for(Vertex v1 : graph) {
				for(Vertex v2 : v1.getAdjacent()) {
					if(v1.getId() > v2.getId()) {
						Line line = new Line();
						line.setStroke(Color.GRAY);
						line.setStartX(v1.getX());
						line.setStartY(v1.getY());
						line.setEndX(v2.getX());
						line.setEndY(v2.getY());
						permanentLines.getChildren().add(line);
					}
				}
			}
		} else 
			permanentLines.getChildren().clear();
	}
	
	public void buildGraph() {
		vertexes.getChildren().clear();
		permanentLines.getChildren().clear();
		for(Vertex v : graph) {
			Circle circle = new Circle(v.getX(), v.getY(), 10, Color.rgb(rgb[v.getColor()][0], rgb[v.getColor()][1], rgb[v.getColor()][2]));
			Text label = new Text(v.getX()-6, v.getY()-12, "v" + v.getId());
			label.setId("" + v.getId());
			circle.setId(Integer.toString(v.getId()));
			vertexes.getChildren().addAll(circle, label);
		}
		for(Node n : vertexes.getChildren()) {
			if(n instanceof Circle)
				n.addEventHandler(MouseEvent.ANY, eventHandler);
		}
		renderPermanentLines(checkBoxShowLines.isSelected());
	}
	
	public void clear() {
		lines.getChildren().clear();
	}
	
	public String getFieldPathIn() {
		return fieldPathIn.getText();
	}
	
	public Label getLabelWarning() {
		return labelWarning;
	}
	
	public Label getLabelFinalValues() {
		return labelFinalValues;
	}
	
}

