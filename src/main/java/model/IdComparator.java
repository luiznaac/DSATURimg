package model;

import java.util.Comparator;

/**
 * @author Luiz Agner <agner@alunos.utfpr.edu.br>
 * 
 * Compares the vertexes' ID
 */

public class IdComparator implements Comparator<Vertex> {

	@Override
	public int compare(Vertex v1, Vertex v2) {
		return v1.getId() - v2.getId();
	}

}
