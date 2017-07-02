package model;

import java.io.IOException;
import controller.ControllerDsatur;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.ViewDsatur;

/**
 * @author Luiz Agner <agner@alunos.utfpr.edu.br>
 * 
 * An implementation of the DSATUR algorithm, which concerns to solve
 * the problem of coloring a graph. In this implementation, we are able to
 * see the graph and interact with it, making it easier to check which
 * vertexes are the adjacent ones.
 * The implementation was proposed by Prof. Ricardo Lüders <luders@utfpr.edu.br>
 */

@SuppressWarnings("restriction")
public class Main extends Application {
	
	public static void main(String[] args) throws IOException {
		Application.launch(args);

	}

	/*
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		ModelDsatur model = new ModelDsatur();
		ControllerDsatur controller = new ControllerDsatur();
		ViewDsatur view = new ViewDsatur();
		controller.link(model, view);
		Scene scene = new Scene(view);
		primaryStage.setScene(scene);
		primaryStage.setTitle("DSATUR Viewer");
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
}
