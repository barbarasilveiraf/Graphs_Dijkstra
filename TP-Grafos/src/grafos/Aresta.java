package grafos;

// Classe que representa a aresta de um grafo entre 2 vertices

public class Aresta {

	private int peso; // Valor da aresta entre dois vertices
	private Vertice vertice1;
	private Vertice vertice2;

	public Aresta(int p, Vertice v1, Vertice v2) {
		this.peso = p;
		this.vertice1 = v1;
		this.vertice2 = v2;
	}

	public Aresta() {
		this.peso = 0;
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}

	public Vertice getVertice1() {
		return vertice1;
	}

	public void setVertice1(Vertice vertice1) {
		this.vertice1 = vertice1;
	}

	public Vertice getVertice2() {
		return vertice2;
	}

	public void setVertice2(Vertice vertice2) {
		this.vertice2 = vertice2;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + peso;
		result = prime * result + ((vertice1 == null) ? 0 : vertice1.hashCode());
		result = prime * result + ((vertice2 == null) ? 0 : vertice2.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Aresta other = (Aresta) obj;
		if (peso != other.peso)
			return false;
		if (vertice1 == null) {
			if (other.vertice1 != null)
				return false;
		} else if (!vertice1.equals(other.vertice1))
			return false;
		if (vertice2 == null) {
			if (other.vertice2 != null)
				return false;
		} else if (!vertice2.equals(other.vertice2))
			return false;
		return true;
	}

}
