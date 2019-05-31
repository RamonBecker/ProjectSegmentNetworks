package Classes;

import java.security.SecureRandom;
import java.util.HashMap;

public class TransporteEmissor {
	private int janelaEnvio = 5;
	private int numSequencia = 1;
	private String dados;
	private static TransporteEmissor transporteEmissor;
	private HashMap<Integer, Segmento> mapEmissorSegmento;

	private TransporteEmissor() {
		mapEmissorSegmento = new HashMap<Integer, Segmento>();
	}

	public int getJanelaEnvio() {
		return janelaEnvio;
	}

	public static synchronized TransporteEmissor getInstance() {
		if (transporteEmissor == null) {
			transporteEmissor = new TransporteEmissor();
		}
		return transporteEmissor;

	}

	public int getNumSequencia() {
		return numSequencia;
	}

	public void enviaTransporte(int portaOrigem, int portaReceptor, String dados) {

		int cont = 0;

		while (cont < janelaEnvio) {
			mapEmissorSegmento.put(this.numSequencia, new Segmento(portaOrigem, portaReceptor, this.numSequencia));
			alteraNumSequencia(numSequencia);

			System.out.println("O pacote: " + this.numSequencia + " está sendo enviado para o Receptor \n");
			TransporteReceptor.getInstance().recebeSegmento(numSequencia, mapEmissorSegmento.get(this.numSequencia));
			this.numSequencia++;
			cont++;

		}
	}

	public void avisaTimeOut(int numSequencia) {
		reenviarPacoteNumSequencia(numSequencia);
	}

	public void reenviarPacoteNumSequencia(int numSequencia) {

		TransporteReceptor receptor = TransporteReceptor.getInstance();
		System.out.println("Emissor recebendo pacote:" + numSequencia + " com NACK\n");
		System.out.println("Reenviado pacote:" + numSequencia + " com ACK para o Receptor \n");
		Segmento segmento = mapEmissorSegmento.get(numSequencia);
		segmento.setNumSequencia(numSequencia);
		segmento.setNack(false);
		receptor.recebeSegmentoBuffer(numSequencia, segmento);
	}

	public void alteraNumSequencia(int key) {

		SecureRandom numRandom = new SecureRandom();

		int num = numRandom.nextInt(10) + 1;

		if (num > 1) {
			num = num + 1;

			if (mapEmissorSegmento.containsKey(key)) {
				mapEmissorSegmento.get(key).setNumSequencia(num);
				System.out.println("Numero de sequencia alterado com sucesso ! \n");
			} else {
				System.err.println("Chave nao encontrada !");
			}
		}

	}

	public void reenviaSegmentoCorrompido(int numSequencia) {

		TransporteReceptor receptor = TransporteReceptor.getInstance();
		System.out.println("Reenviado pacote:" + numSequencia + " corrompido corrigido para o Receptor \n");
		Segmento segmento = mapEmissorSegmento.get(numSequencia);
		segmento.setNewVerificador();
		segmento.setNumSequencia(numSequencia);
		segmento.setNack(false);
		receptor.recebeSegmento(numSequencia, segmento);
	}

	public void recebeSegmentoCorrompido(int numSequencia) {
		System.out.println("Pacote:" + numSequencia + " corrompido  recebido pelo Emissor \n");
		reenviaSegmentoCorrompido(numSequencia);
	}

	public HashMap<Integer, Segmento> getMapEmissorSegmento() {
		return mapEmissorSegmento;
	}

	public void recebeSegmento(int numSequencia) {
		System.out.println("O pacote: " + numSequencia + " foi entregue com sucesso ao emissor \n");
		System.out.println("O pacote: " + numSequencia + " recebido pela aplicacao \n");
		
		mapEmissorSegmento.remove(numSequencia);
	}

	public String getDados() {
		return dados;
	}
}
