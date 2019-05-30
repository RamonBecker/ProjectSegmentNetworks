package Classes;

import java.security.SecureRandom;

public class Aplicacao implements Runnable {
	private int portaOrigem;
	private  int portaDestino;
	private  String nomeAplicacao;
	private SecureRandom numRandom;

	
	public Aplicacao(int portaDestino, String nomeAplicacao) {

		if (portaDestino < 0) {
			throw new IllegalArgumentException("A porta de destino nao pode ser um negativa !");
		}
		if (nomeAplicacao.isEmpty()) {
			throw new IllegalArgumentException("O nome da aplicacao nao pode ser vazio !");

		}
		numRandom = new SecureRandom();
		this.portaDestino = portaDestino;
		this.nomeAplicacao = nomeAplicacao;
		setPortaOrigem();

	}

	public int getPortaOrigem() {
		return portaOrigem;
	}

	private void setPortaOrigem() {
		this.portaOrigem = numRandom.nextInt((65535 - 49152) + 1) + 49152;
	}

	public int getPortaDestino() {
		return portaDestino;
	}

	public void setPortaDestino(int portaDestino) {

		if (portaOrigem < 0) {
			throw new IllegalArgumentException("A porta de origem nao pode ser um negativa !");
		}

		this.portaDestino = portaDestino;
	}

	public String getNomeAplicacao() {
		return nomeAplicacao;
	}

	public void setNomeAplicacao(String nomeAplicacao) {

		if (nomeAplicacao.isEmpty() || nomeAplicacao == null) {
			throw new IllegalArgumentException("O nome da aplicacao nao pode ser vazio !");
		}

		this.nomeAplicacao = nomeAplicacao;
	}

	public String getDados() {
		return "Dados da Aplicacao: " + nomeAplicacao;
	}

	@Override
	public void run() {
		while (true) {
			int tempo = numRandom.nextInt(3000) + 100;
			TransporteEmissor.getInstance().enviaTransporte(this.portaOrigem, this.portaDestino, this.nomeAplicacao);
			try {
				Thread.sleep(tempo);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
