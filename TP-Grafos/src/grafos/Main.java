package grafos;

public class Main {

	public static void main(String[] args) {
		Grafo grafo = new Grafo();

		// Parametros:
		// [0] -> caminho do arquivo + nome do arquivo (entrada)
		// [1] -> caminho do arquivo + nome do arquivo (saida)

		Arquivo a = new Arquivo(args[0], args[1], grafo);

		a.leArquivo();

		// Executar o Algorimo Dijkstra para todos os Vertices
		for (Vertice origem : grafo.vertices) {
			grafo.caminhosDijkstra(origem);
		}

		// Metodo para contar a quantas vezes um vertice aparece no caminho, o qual e a
		// inflencia dos deputados
		grafo.contaVerticesNosCaminhos();

		// Escreve no arquivo de saida
		a.escreveArquivo();

		System.out.println("Programa Executado!");

	}

}
