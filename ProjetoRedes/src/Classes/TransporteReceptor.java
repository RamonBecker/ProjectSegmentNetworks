package Classes;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class TransporteReceptor {
	private int numSequencia = 1;

	private TreeMap<Integer, Segmento> mapBufferReceptor;
	private TreeMap<Integer, Segmento> mapBufferSegmentoEspera;
	private static TransporteReceptor transporteReceptor;

	private TransporteReceptor() {
		mapBufferReceptor = new TreeMap<Integer, Segmento>();
		mapBufferSegmentoEspera = new TreeMap<Integer, Segmento>();
	}

	public static synchronized TransporteReceptor getInstance() {
		if (transporteReceptor == null) {
			transporteReceptor = new TransporteReceptor();
		}
		return transporteReceptor;
	}

	public void recebeSegmento(int numSequencia, Segmento segmento) {
		trataSegmento(numSequencia, segmento);
	}

	private void EntregaNaoEntregaSegmento() {

		if (mapBufferSegmentoEspera.size() > 0) {
			Iterator<Map.Entry<Integer, Segmento>> iter = mapBufferSegmentoEspera.entrySet().iterator();

			while (iter.hasNext()) {
				Map.Entry<Integer, Segmento> entry = iter.next();
				if (entry.getKey() == this.numSequencia) {
					TransporteEmissor.getInstance().avisaTimeOut(numSequencia);
					iter.remove();
				}
			}
		}
	}

	public void avisaTimeOut(int numSequencia) {
		System.out.println("Pacote: " + numSequencia + " aconteceu timeout!\n");
		System.out.println("Reenviando pacote:"+numSequencia+" com NACK para o emissor\n");
		TransporteEmissor.getInstance().avisaTimeOut(numSequencia);

	}

	private void trataSegmento(int numSequencia, Segmento segmento) {

		System.out.println("Pacote:" + numSequencia + "  recebido pelo receptor\n");

		if (segmento.isVerificador()) {
			if (segmento.getNumSequencia() == this.numSequencia) {

				EntregaNaoEntregaSegmento();
				TransporteEmissor.getInstance().recebeSegmento(numSequencia);
				this.numSequencia++;
			} else {

				mapBufferReceptor.put(numSequencia, segmento);
				mapBufferReceptor.get(numSequencia).setNack(true);
				avisaTimeOut(this.numSequencia);
			}

			if (TransporteEmissor.getInstance().getNumSequencia() > this.numSequencia) {
				mapBufferSegmentoEspera.put(numSequencia, segmento);
			}

		} else {

			EntregaNaoEntregaSegmento();
			System.out.println("Reeviando pacote:"+numSequencia+" corrompido para o Emissor\n");
			TransporteEmissor.getInstance().recebeSegmentoCorrompido(numSequencia);
		}
	}

	public void recebeSegmentoBuffer(int numSequencia, Segmento segmento) {

		System.out.println("Receptor recebendo Pacote " + numSequencia + " com ACK do Emissor\n");
		if (segmento.getNumSequencia() == this.numSequencia) {
			TransporteEmissor.getInstance().recebeSegmento(numSequencia);
			this.numSequencia++;
		}
	}

	public TreeMap<Integer, Segmento> getMapBufferReceptor() {
		return mapBufferReceptor;
	}
}
