package grafos;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;

public class Main {

	private static double MEGABYTE = 1024 * 1024;

	public Main() {
		// TODO Auto-generated constructor stub
	}

	public static double bytesToMegabytes(double bytes) {
		return bytes / MEGABYTE;
	}

	public static void chamaTpGragos(String arq, int i, String caminhoArquivoEntrada) {

		System.out.println(i + "- Programa Executando! Nome " + arq);

		Grafo grafo = new Grafo();

		Arquivo a = new Arquivo((caminhoArquivoEntrada + arq), (caminhoArquivoEntrada + ".OUT"), grafo);

		a.leArquivo();

		for (Vertice origem : grafo.vertices) {
			grafo.caminhosDijkstra(origem);
		}
		grafo.contaVerticesNosCaminhos();
		a.escreveArquivo();
	}

	public static void main(String[] args) throws IOException {
		String vertice;
		String aresta;
		String exemplo;
		String tipoGrafo;
		DecimalFormat df = new DecimalFormat("#.#####");
		FileWriter arq = new FileWriter(args[1]);
		PrintWriter gravarArq = new PrintWriter(arq);
		File arquivos[];
		File diretorio = new File(args[0]);
		arquivos = diretorio.listFiles();
		for (int i = 0; i < arquivos.length; i++) {
			if (arquivos[i].getName().contains("OUT"))
				continue;
			String[] n = arquivos[i].getName().split("_");
			vertice = n[1].substring(1);
			exemplo = n[2];
			tipoGrafo = n[3];
			aresta = n[4].substring(1);

			long startTime = System.currentTimeMillis();

			chamaTpGragos(arquivos[i].getName(), i, args[0]);

			long endTime = System.currentTimeMillis();

			Runtime runtime = Runtime.getRuntime();
			runtime.gc();
			// Calculate the used memory
			long memory = runtime.totalMemory() - runtime.freeMemory();
			double memoryMB = bytesToMegabytes(memory);

			long totalTimeMiliSegundos = endTime - startTime;

			System.out.println("Tempo em segundos: " + (df.format(totalTimeMiliSegundos / 1000d)));
			System.out.println("Memoria em bytes: " + memory + " MB= " + memoryMB);

			gravarArq.write(exemplo + ";" + vertice + ";" + aresta + ";" + tipoGrafo + ";" + totalTimeMiliSegundos + ";"
					+ (df.format(totalTimeMiliSegundos / 1000d)) + ";" + memory + ";" + (df.format(memoryMB)));

			gravarArq.write("\n");

		}
		arq.close();
	}
	// TODO Auto-generated method stub

}
