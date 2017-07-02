package model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Random;
import javafx.collections.FXCollections;
import javafx.collections.ModifiableObservableListBase;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Luiz Agner <agner@alunos.utfpr.edu.br>
 * 
 * MVC Design - Model class which handles the processing and data storage
 */

@SuppressWarnings("restriction")
public class ModelDsatur extends ModifiableObservableListBase<Vertex> {

	private ObservableList<Vertex> graph;
	private SimpleStringProperty warning, finalValues; //"send" messages to the GUI 
	private String pathIn;
	
	public ModelDsatur() {
		graph = FXCollections.observableArrayList(new ArrayList<Vertex>());
		warning = new SimpleStringProperty("Aguardando input.");
		finalValues = new SimpleStringProperty();
	}
	
	@Override
	protected void doAdd(int index, Vertex e) {
		graph.add(index, e);
	}
	
	@Override
	protected Vertex doRemove(int index) {
		return graph.remove(index);
	}
	
	@Override
	protected Vertex doSet(int index, Vertex e) {
		return null;
	}
	
	@Override
	public Vertex get(int index) {
		return graph.get(index);
	}
	
	@Override
	public int size() {
		return graph.size();
	}
	
	/*
	 * Loads and creates the graph from the given file.
	 * 
	 * @param pathIn the path to the file with the information about the graph
	 */
	public void load(String pathIn) {
		this.pathIn = pathIn;
		clear();
		ArrayList<Vertex> readGraph;
		try {
			long start = System.currentTimeMillis();
			readGraph = Dsatur.compute(GraphIO.build(pathIn));
			long end = System.currentTimeMillis();
			readGraph = computePositions(readGraph);
			for(Vertex v : readGraph)
				this.add(v);
			warning.set("Grafo carregado com sucesso.");
			computeFinalValues();
			finalValues.set(finalValues.get() + "\nTempo de execução: "  + (end-start) + " milisegundos");
		} catch(Exception e) {
			warning.set("Verifique o caminho.");
		}
	}
	
	/*
	 * Computes the coordinate x, y of each vertex to be displayed on the canvas
	 * 
	 *  @param graph the Array with the vertexes
	 */
	private ArrayList<Vertex> computePositions(ArrayList<Vertex> graph) {
		int SIZE = 1000;
		int BORDER = 100;
		SIZE = SIZE - 2*BORDER;
	    int div = (int)Math.sqrt(graph.size())+1;
	    Vertex tempVertex;
	    int k = 0;
	    Random rand = new Random();
	    
	    for(int i = 0 ; k < graph.size() ; i++) {
	    	for(int j = 0 ; j < div && k < graph.size() ; j++, k++) {
	    		tempVertex = graph.get(k);	
	    		//sets the coordinates to the vertexes
	    		//tries to avoid intersecting edges by giving +65 and -65 pixels of freedom for each vertex
	    		tempVertex.setX((int)(Math.pow(-1, (i%2)+1))*(15 + rand.nextInt(50)) + BORDER + (int)j*SIZE/div);
	    		tempVertex.setY((int)(Math.pow(-1, (j%2)+1))*(15 + rand.nextInt(50)) + BORDER + (int)i*SIZE/div);
	    	}
	    }
		return graph;
	}
	
	public void saveGraph() {
		try {
			int i = pathIn.length()-1;
			while((pathIn.charAt(i) != '/') && (pathIn.charAt(i) != '\\'))
				i--;
			String pathOut = pathIn.substring(0, i) + "/out.csv";
			FileWriter fw = new FileWriter(pathOut);
			BufferedWriter textWriter = new BufferedWriter(fw);
			String out = "";
			for(Vertex v : graph) {
				out += v + "\n";
			}
			textWriter.write(out);
			textWriter.close();
			fw.close();
			warning.set("Grafo salvo com sucesso.");
		} catch(Exception e) {
			warning.set("Verifique o caminho.");
		}
	}
	
	public void computeFinalValues() {
		int nVertexes, nEdges = 0, maxSatDegree = 0, minSatDegree = 10, nColors = 0;
		float averageSatDegree = 0, standardDeviationSatDegree = 0;
		nVertexes = graph.size();
		for(Vertex v1 : graph) {
			if(nColors < v1.getColor())
				nColors = v1.getColor();
			averageSatDegree += v1.getSatDegree();
			if(v1.getSatDegree() > maxSatDegree)
				maxSatDegree = v1.getSatDegree();
			if(v1.getSatDegree() < minSatDegree)
				minSatDegree = v1.getSatDegree();
			for(Vertex v2 : v1.getAdjacent()) {
				if(v2.getId() > v1.getId())
					nEdges++;
			}
		}
		averageSatDegree = averageSatDegree/(float)nVertexes;
		for(Vertex v1 : graph) 
			standardDeviationSatDegree += Math.pow(v1.getSatDegree() - averageSatDegree, 2);
		standardDeviationSatDegree = (float)Math.sqrt(standardDeviationSatDegree);
		
		finalValues.set("N. Vértices: " + nVertexes + 
						"\nN. Arestas: " + nEdges + 
						"\nMin Sat Degree: " + minSatDegree + 
						"\nMax Sat Degree: " + maxSatDegree +
						"\nMédia Sat Degree: " + averageSatDegree +
						"\nDesvio-Padrão Sat Degree: " + standardDeviationSatDegree +
						"\nN. Cores: " + nColors);	
	}
	
	public void saveImage() {
		if(pathIn != null) {
			int i = pathIn.length()-1;
			while((pathIn.charAt(i) != '/') && (pathIn.charAt(i) != '\\'))
				i--;
			String pathOut = pathIn.substring(0, i) + "/graph.png";
			ArrayList<Vertex> graphToPrint = new ArrayList<>();
			for(Vertex v : graph)
				graphToPrint.add(v);
			GraphPrinter gp = new GraphPrinter(100, 1000, pathOut, graphToPrint);
			gp.print();
			warning.set("Imagem salva com sucesso.");
			gp.display();
		} else
			warning.set("Verifique o caminho.");
	}
	
	public SimpleStringProperty getWarning() {
		return warning;
	}
	
	public SimpleStringProperty getFinalValues() {
		return finalValues;
	}
	
}
