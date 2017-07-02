package model;

import java.util.Comparator;

/**
 * @author Luiz Agner <agner@alunos.utfpr.edu.br>
 * 
 * A class that compares the Degree of the Vertex
 */

public class DegreeComparator implements Comparator<Vertex> {

	@Override
	public int compare(Vertex v1, Vertex v2) {
		return v2.getDegree() - v1.getDegree();
	}

}
