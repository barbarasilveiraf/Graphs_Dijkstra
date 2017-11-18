package grafos;

import java.util.ArrayList;
import java.util.List;

// Classe que representa o vertice de um grafo

public class Vertice {

	String nome;
	Integer distancia; // Atributo que informa distancia de um vertice com relacao a origem do caminho
	Boolean visitado; // Atributo que informa se o vertice ja foi visitado ou nao.
	List<Vertice> pais; // Vertice que antece o vertice corrente
	Integer frequenciaCaminho; // Quantas vezes um vertice aparece no caminho

	public Vertice() {
		pais = new ArrayList<Vertice>();
		nome = "";
		visitado = false;
	}

	public Vertice(String n) {
		this.nome = n;
	}

	public List<Vertice> getPai() {
		return pais;
	}

	public void setPai(List<Vertice> pai) {
		this.pais = pai;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Boolean getVisitado() {
		return visitado;
	}

	public void setVisitado(Boolean visitado) {
		this.visitado = visitado;
	}

	public Integer getDistancia() {
		return distancia;
	}

	public void setDistancia(Integer distancia) {
		this.distancia = distancia;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	// Para conseguir realizar a comparacao entre dois vertices
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vertice other = (Vertice) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

}
