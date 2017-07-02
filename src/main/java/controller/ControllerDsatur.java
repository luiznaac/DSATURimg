package controller;

import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import model.ModelDsatur;
import view.ViewDsatur;
import javafx.scene.Node;
import javafx.event.Event;
import javafx.scene.control.Button;

/**
 * @author Luiz Agner <agner@alunos.utfpr.edu.br>
 * 
 * MVC Design - Controller class which binds the data and describes 
 * the actions performed by the buttons
 */

@SuppressWarnings("restriction")
public class ControllerDsatur {

	public void link(ModelDsatur model, ViewDsatur view) {
		view.setList(model);
		view.getLabelWarning().textProperty().bind(model.getWarning());
		view.getLabelFinalValues().textProperty().bind(model.getFinalValues());
		view.addEventHandler(new EventHandler<Event>(){
			@Override
			public void handle(Event event) {
				//determine the action based on the event
				if(event.getEventType() == MouseEvent.MOUSE_ENTERED) {
					view.renderVertex((Node)event.getSource());
					event.consume();
				} else if(event.getEventType() == MouseEvent.MOUSE_EXITED) {
					view.clear();
					event.consume();
				} else if(event.getSource() instanceof Button) {
					Button button = (Button)event.getSource();
					if(button.getId() == "open") {
						model.load(view.getFieldPathIn());
						view.buildGraph();
						event.consume();
					} else if(button.getId() == "save") {
						model.saveGraph();
						event.consume();
					} else if(button.getId() == "saveImage") {
						model.saveImage();
						event.consume();
					}
				}
			}
		});
	}
	
}
