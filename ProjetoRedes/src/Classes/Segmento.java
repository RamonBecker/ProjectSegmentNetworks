package Classes;

import java.security.SecureRandom;

public class Segmento {
	private int portaOrigem;
	private int portaDestino;
	private int numSequencia;
	private boolean verificador;
	private boolean ack;
	private boolean nack;
	
	public Segmento(int portaOrigem, int portaDestino, int numSequencia) {
		// SE A PORTA DE DESTINO FOR NEGATIVA, LANÇO UMA EXCEÇÃO, E NÃO CRIO O OBJETO DO TIPO SEGMENTO
		// ASSIM ACONTECE COM OS DEMAIS
		if (portaDestino < 0) {
			throw new IllegalArgumentException("A porta de destino nï¿½o pode ser negativa !");
		}

		if (portaOrigem < 0) {
			throw new IllegalArgumentException("A porta de destino nï¿½o pode ser negativa !");
		}

		if(numSequencia < 0) {
			throw new IllegalArgumentException("O nÃºmero de sequencia nï¿½o pode ser negativa !");
		}
		
		this.portaOrigem = portaOrigem;
		this.portaDestino = portaDestino;
		this.numSequencia = numSequencia;
		// INDO PARA FUNÇÃO setVerificador
		setVerificador();
		
	}


	public int getPortaOrigem() {
		return portaOrigem;
	}

	public int getPortaDestino() {
		return portaDestino;
	}


	public int getNumSequencia() {
		return numSequencia;
	}

	public void setNumSequencia(int numSequencia) {
		
		if(numSequencia < 0) {
			throw new IllegalArgumentException("Numero de sequencia nao pode ser negativo");
		}
		
		this.numSequencia = numSequencia;
	}

	public boolean isVerificador() {
		return verificador;
	}
	
	// NO SET VERIFICADOR, EU VOU DIZER SE O PACOTE ESTA CORROMPIDO OU NAO
	// SE O NUMERO SORTIDO FOR MAIOR NA COMPARACAO DO IF, ENTAO O PACOTE NAO ESTA CORROMPIDO
	// SETO O VERIFICADOR COMO TRUE
	// SE ESTA CORROMPIDO
	// SETO O VERIFICADOR COMO FALSE
	public void setVerificador() {
		SecureRandom numRandom = new SecureRandom();

		int numero = numRandom.nextInt(10) + 1;

		if (numero > 9) {
			this.verificador = true;
		} else {
			this.verificador = false;
		}
	}

	public boolean isAck() {
		return ack;
	}

	public void setAck(boolean ack) {
		this.ack = ack;
	}

	public boolean isNack() {
		return nack;
	}

	public void setNack(boolean nack) {
		this.nack = nack;
	}

	public void setNewVerificador() {
		this.verificador = true;
	}
}
