package grafos;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Arquivo {

	private String arquivoEntrada;
	private String arquivoSaida;
	Grafo grafo;

	public Arquivo(String arqEntrada, String arqSaida, Grafo g) {
		this.arquivoEntrada = arqEntrada;
		this.arquivoSaida = arqSaida;
		grafo = g;
	}

	// Metodo para realizar a leitura do arquivo que foi passado por parametro
	public void leArquivo() {

		try {
			FileReader arq = new FileReader(arquivoEntrada);

			BufferedReader lerArq = new BufferedReader(arq);

			String linha = lerArq.readLine();

			while (linha != null && !linha.equals("") && !linha.contains("0,0,0")) {

				String[] g = linha.split(",");

				grafo.criaGrafo(g[0], g[1], Integer.parseInt(g[2])); // Criacao do grafo de acordo com os dados lidos do
																		// arquivo

				linha = lerArq.readLine();
			}
			grafo.transformaArestas(); // Metodo que inverte os valores das arestas para conseguir aplicar o algorimo
										// de caminho minimo

			grafo.grafosLAFIXA = grafo.copiaGrafoLA(grafo.grafosLA);

			arq.close();

		} catch (FileNotFoundException e) {
			System.out.println("Arquivo nao encontrado!! Insira o caminho absoluto + o nome do arquivo. Nao deve existir espaco no caminho.");
			System.out.println("Exemplo: Caminho/Caminho/arquivo.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Metodo para escrever no arquivo os deputados mais influentes
	public void escreveArquivo() {

		try {

			// FileWriter arq = new FileWriter(arquivoSaida.replace(".txt", ".out"));

			FileWriter arq = new FileWriter(arquivoSaida);

			PrintWriter gravarArq = new PrintWriter(arq);

			int aux = 0;
			for (Vertice deputado : grafo.getQtdeVerticesNosCaminhos()) {
				gravarArq.print(deputado.nome);

				if ((aux + 1) != grafo.getQtdeVerticesNosCaminhos().toArray().length)
					gravarArq.write(",");
				aux++;
			}

			arq.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
