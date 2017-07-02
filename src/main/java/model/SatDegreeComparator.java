package model;

import java.util.Comparator;

/**
 * @author Luiz Agner <agner@alunos.utfpr.edu.br>
 * 
 * Compares the Sat Degree
 */

public class SatDegreeComparator implements Comparator<Vertex> {

	@Override
	public int compare(Vertex v1, Vertex v2) {
		return v2.getSatDegree() - v1.getSatDegree();
	}

}
