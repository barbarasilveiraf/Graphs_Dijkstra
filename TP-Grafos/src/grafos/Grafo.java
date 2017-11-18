package grafos;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Grafo {

	Map<Vertice, ArrayList<Vertice>> grafosLA; // Lista de Adjacencia do Grafo
	Map<Vertice, ArrayList<Vertice>> grafosLAFIXA; // Lista de Adjacencia do Grafo
	List<Vertice> vertices; // Todos os Vertices do Grafo
	List<Aresta> arestas; // Todas as arestas do Grafo
	List<Vertice> todosCaminhos; // Todos os caminhos realizados pelo Dijkstra
	List<Vertice> qtdeVerticesNosCaminhos; // Quantidade de vzes que um determinado vertice aparece

	public Grafo() {
		grafosLA = new HashMap<Vertice, ArrayList<Vertice>>();
		grafosLAFIXA = new HashMap<Vertice, ArrayList<Vertice>>();
		qtdeVerticesNosCaminhos = new ArrayList<Vertice>();
		todosCaminhos = new ArrayList<Vertice>();
		arestas = new ArrayList<>();
		vertices = new ArrayList<>();
	}

	public Map<Vertice, ArrayList<Vertice>> getGrafosLA() {
		return grafosLA;
	}

	public void setGrafosLA(Map<Vertice, ArrayList<Vertice>> grafosLA) {
		this.grafosLA = grafosLA;
	}

	public List<Vertice> getVertices() {
		return vertices;
	}

	public void setVertices(ArrayList<Vertice> vertices) {
		this.vertices = vertices;
	}

	public List<Aresta> getArestas() {
		return arestas;
	}

	public void setArestas(ArrayList<Aresta> arestas) {
		this.arestas = arestas;
	}

	public List<Vertice> getQtdeVerticesNosCaminhos() {
		return qtdeVerticesNosCaminhos;
	}

	public void setQtdeVerticesNosCaminhos(List<Vertice> qtdeVerticesNosCaminhos) {
		this.qtdeVerticesNosCaminhos = qtdeVerticesNosCaminhos;
	}

	// Padrao de projeto SINGLE SIGN ON.
	// Utilizado para manter a mesma referencia da instancia de Vertice
	private static HashMap<String, Vertice> InstanciaVertices = new HashMap<String, Vertice>();

	public static synchronized Vertice getInstance(String nomeVertice) {
		if (InstanciaVertices.get(nomeVertice) == null)
			InstanciaVertices.put(nomeVertice, new Vertice(nomeVertice));
		return InstanciaVertices.get(nomeVertice);
	}

	// Metodo para criar o grafo, seus vertices e suas conexoes
	public void criaGrafo(String v1, String v2, int peso) {

		if (peso == 0) { // Condicao para nao pegar arestas de peso 0, porque elas nao sao representadas
			return;
		}

		Vertice vertice1 = getInstance(v1);
		Vertice vertice2 = getInstance(v2);

		Aresta aresta = new Aresta(peso, vertice1, vertice2);

		// Quando o vertice1 nao existe no grafo e tenho que adicionar
		if (!grafosLA.containsKey(vertice1))
			grafosLA.put(vertice1, new ArrayList<Vertice>());

		// Quando o vertice2 nao existe no grafo e tenho que adicionar
		if (!grafosLA.containsKey(vertice2))
			grafosLA.put(vertice2, new ArrayList<Vertice>());

		// Verifico se o vertice2 esta na LA do vertice1, caso nao esteja adiciono
		if (!grafosLA.get(vertice1).contains(vertice2))
			grafosLA.get(vertice1).add(vertice2);

		// Verifico se o vertice1 esta na LA do vertice 2, caso nao esteja adiciono
		if (!grafosLA.get(vertice2).contains(vertice1))
			grafosLA.get(vertice2).add(vertice1);

		// Informo que os vertices nao foram visitados
		vertice1.visitado = false;
		vertice2.visitado = false;

		// Adiciono os vertices na lista de vertices
		if (!vertices.contains(vertice1))
			vertices.add(vertice1);
		if (!vertices.contains(vertice2))
			vertices.add(vertice2);

		// Adiciono as arestas na lista de arestas
		arestas.add(aresta);
	}

	// Metodo que implementa o algortimo Dijkstra.
	// Que e o caminho minimo de um grafo a partir de uma origem

	public void caminhosDijkstra(Vertice origem) {
		inicializa(origem); // Inializacao dos atributos dos vertices

		ArrayList<Vertice> verticesNaoVisitados = new ArrayList<>();
		ArrayList<Vertice> caminhos = new ArrayList<>();
		Vertice verticeCorrente;

		// Inicialmente nenhum vertice foi visitado.
		// Entao adiciono todos na lista de vertices nao visitados.
		verticesNaoVisitados.addAll(vertices);

		while (!verticesNaoVisitados.isEmpty()) {

			verticeCorrente = extraiVerticeMinimo(verticesNaoVisitados);
			verticesNaoVisitados.remove(verticeCorrente); // Removo o vertice que sera visitado
			caminhos.add(verticeCorrente); // Adiciono o vertice no caminho

			// Realiza o relaxmento para o vertices adjacentes ao vertice corrente
			for (Vertice vertice : grafosLA.get(verticeCorrente)) {
				// if (!vertice.visitado)
				relaxamento(verticeCorrente, vertice, getPesoAresta(verticeCorrente, vertice));
			}
		}

		// Metodo para gerar todos os caminhos
		montaTodosCaminhos(caminhos, origem);

	}

	// Metodo para gerar todos caminhos de uma fonte ate os outros vertices
	public void montaTodosCaminhos(ArrayList<Vertice> caminhos, Vertice s) {
		for (Vertice vertice : caminhos) {
			montaCaminho(caminhos, s, vertice, vertice);
			// System.out.println("\n-------------------------------");
		}
	}

	// Caminho unico de uma fonte a um vertice em especifico
	public void montaCaminho(ArrayList<Vertice> caminhos, Vertice s, Vertice v, Vertice t) {
		if (s == v) {
			// System.out.print(" " + s.nome);
			return;
		} else if (v.pais == null) {
			return;// System.out.println("sem path");
		} else {
			for (Vertice vPai : v.pais) {
				montaCaminho(caminhos, s, vPai, t);
				// System.out.print(" " + v.nome);
				if (v != t)
					todosCaminhos.add(v);
			}

		}

	}

	// Peso de uma Aresta entre dois Vertices
	public int getPesoAresta(Vertice v1, Vertice v2) {
		int pesoAresta = -1;

		for (Aresta a : arestas) {
			if (a.getVertice1().equals(v1) && a.getVertice2().equals(v2)) {
				pesoAresta = a.getPeso();
				break;
			} else if (a.getVertice1().equals(v2) && a.getVertice2().equals(v1)) {
				pesoAresta = a.getPeso();
				break;
			}
		}
		return pesoAresta;
	}

	// Metodo utilizado pelo Dijkstra
	// Inicializa a fonte com 0 e os demais verices com um numero grande que
	// representa o infinito
	public void inicializa(Vertice origem) {
		grafosLA = copiaGrafoLA(grafosLAFIXA);
		for (Vertice v : vertices) {
			v.distancia = Integer.MAX_VALUE - 1;
			v.pais = new ArrayList<Vertice>();
			v.visitado = false;
		}
		origem.distancia = 0;
	}

	// Metodo utilizado pelo Dijkstra
	// Extrai o menor vertice que nao foi visitado
	public Vertice extraiVerticeMinimo(ArrayList<Vertice> v) {
		Vertice verticeMin = new Vertice();
		verticeMin.distancia = Integer.MAX_VALUE;

		for (Vertice vertice : v) {
			if (!vertice.getVisitado()) {
				if (vertice.distancia < verticeMin.distancia) {
					verticeMin = vertice;
				}
			}
		}

		verticeMin.visitado = true;
		if (!verticeMin.pais.isEmpty())
			grafosLA.get(verticeMin).removeAll(verticeMin.pais);
		return verticeMin;
	}

	// Metodo utiliazdo pelo Dijkstra
	// Realiza o relaxamentro dos vertices
	public void relaxamento(Vertice v1, Vertice v2, int peso) {
		if (peso == -1)
			System.out.println("nao achou aresta");
		if (v2.distancia > v1.distancia + peso) {
			v2.distancia = (v1.distancia + peso);
			v2.pais = new ArrayList<Vertice>();
			v2.pais.add(v1);
		} else if (v2.distancia == (v1.distancia + peso)) {
			v2.pais.add(v1);
		}
	}

	// Metodo para transformar as arestas
	// As maiores arestas serao as menores
	// As menores arestas serao as maiores
	// Desta maneira posso aplicar um algoritmo de caminho minimo
	public void transformaArestas() {
		for (Aresta a : arestas) {
			a.setPeso(100 - (a.getPeso()));
		}
	}

	// Metodo para contar quantas vezes cada vertice apareceu nos caminhos
	public void contaVerticesNosCaminhos() {
		int cont;
		for (Vertice vertice : vertices) {
			cont = 0;
			for (Vertice v : todosCaminhos) {
				if (vertice.nome == v.nome)
					cont++;
			}
			vertice.frequenciaCaminho = cont;
			qtdeVerticesNosCaminhos.add(vertice);

			// System.out.println("Vertice " + vertice.nome + " Quantidade " + cont);
		}

		qtdeVerticesNosCaminhos.sort(getCompVertice()); // Ordeno os vertices de acordo com sua frequencia do caminho

	}

	// Comparator, o qual informa como os vertices deverao ser ordenados pelo metodo
	// sort()
	private static Comparator<Vertice> getCompVertice() {
		Comparator<Vertice> comp = new Comparator<Vertice>() {
			@Override
			public int compare(Vertice v1, Vertice v2) {
				if (v1.frequenciaCaminho > v2.frequenciaCaminho)
					return -1;
				else if (v1.frequenciaCaminho.equals(v2.frequenciaCaminho))
					return v1.nome.compareTo(v2.nome);
				else
					return 1;
			}
		};
		return comp;
	}

	// faz copia da lista de adjacencia
	public Map<Vertice, ArrayList<Vertice>> copiaGrafoLA(Map<Vertice, ArrayList<Vertice>> origemCopia) {
		Map<Vertice, ArrayList<Vertice>> copia = new HashMap<Vertice, ArrayList<Vertice>>();
		for (Map.Entry<Vertice, ArrayList<Vertice>> v : origemCopia.entrySet()) {
			if (!copia.containsKey(v.getKey())) {
				ArrayList<Vertice> al = new ArrayList<Vertice>(v.getValue());
				copia.put(v.getKey(), al);
			}
		}
		return copia;

	}
}
