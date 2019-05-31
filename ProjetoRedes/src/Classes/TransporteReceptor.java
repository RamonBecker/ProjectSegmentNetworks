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
		// NESSE METODO RECEBO O NUMERO DE SEQUENCIA, E O OBJETO SEGMENTO(PACOTE)
		// DEPOIS VOU PARA O METODO TRATASEGMENTO
		trataSegmento(numSequencia, segmento);
	}

	private void EntregaNaoEntregaSegmento() {

		
		// SE A MINHA LISTA NÃO ESTIVER VAZIA, ENTRE NA LISTA, SE NÃO, NÃO EXISTE NENHUM PACOTE A SER ENTREGUE
		if (mapBufferSegmentoEspera.size() > 0) {
		
			//Crio um iterador, para percorrer a minha lista
			// o iterador ira fazer referencia a minha lista do bufferSegmentoEspera
		//	o meotodo entrySet() - Retorna um conjunto de Maps contido no mapa, podendo ser possível acessar suas chaves e valores.
		
			Iterator<Map.Entry<Integer, Segmento>> iter = mapBufferSegmentoEspera.entrySet().iterator();

			
		// neste while a comparação é, se existe um proximo segmento(pacote) a ser percorrido,
		// entao ele ira iterar sobre a lista até não existir nenhum segmento(pacote)
			while (iter.hasNext()) {
				
				Map.Entry<Integer, Segmento> entry = iter.next();
				// a variavel entry irá Retornar o próximo elemento na iteração.
				
				// comparo getKey(que é o numero de sequencia do pacote que esta em espera)
				//com o numero de sequencia atual
				// se ele for igual
				// aviso que deu time out no emissor
				// e envio o pacote para o emissor
				// e depois de enviar para o emissor, com o numero de sequencia correto
				// que é tanto a key do , mapBufferSegmentoEspera como a key da minha lista mapEmissorSegmento
				// removo ele da minha lista de espera
				// e entro no metodo time out..
				
				// EXEMPLO
				//RECEBI 1, E 2 TIME OUT NO 3
				// 4 ESTA NO BUFFER
				// O MEU NUMERO DE SEQUENCIA SERÁ O 3
				// PORQUE ANTES DE ENVIAR O 4, EU JÁ ENVIO 3 QUE ESTA COM NACK 
				// O METODO recebeSegmentoBuffer desta classe incrementa o meu numero de sequencia
				// QUE NA PROXIMA VEZ SERA O 4, E O NUMSEQUENCIA
				// SERA A KEY DA MINHA LISTA EM TRANSPORTE EMISSOR
				
				
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
		// AQUI SERA TRATADO SE O PACOTE, ESTA CORROMPIDO OU NÃO, E TAMBEM SE O NUMERO
		// DE SEQUENCIA É O ESPERADO
		
		//NA PRIMEIRA VERIFICACAO VEJO SE O PACOTE NAO ESTA CORROMPIDO, SE ESTIVER VOU PARA O ULTIMO ELSE
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
			// VERIFICO SE EXISTE (SEGMENTO)PACOTE NO BUFFER A SER ENTREGUE
			// POR EXEMPLO
			// RECEBO O PACOTE 1,2, E 3.
			// O PACOTE 4 DEU TIME OUT
			// RECEBO O 5,6, E 7
			// OS PACOTES 5,6 E 7 ESTARÃO NO BUFFER DE ESPERA
			// ENTAO CHAMO ESTA FUNÇÃO PARA VERIFICAR SE EXISTE PACOTES A SEREM ENTREGUES
			// INDO PARA A FUNÇÃO EntregaNaoEntregaSegmento...
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
