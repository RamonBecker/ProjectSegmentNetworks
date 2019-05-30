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

		if (portaDestino < 0) {
			throw new IllegalArgumentException("A porta de destino n�o pode ser negativa !");
		}

		if (portaOrigem < 0) {
			throw new IllegalArgumentException("A porta de destino n�o pode ser negativa !");
		}

		if(numSequencia < 0) {
			throw new IllegalArgumentException("O número de sequencia n�o pode ser negativa !");
		}
		
		this.portaOrigem = portaOrigem;
		this.portaDestino = portaDestino;
		this.numSequencia = numSequencia;
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

	public void setVerificador() {
		SecureRandom numRandom = new SecureRandom();

		int numero = numRandom.nextInt(10) + 1;

		if (numero > 1) {
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

}
