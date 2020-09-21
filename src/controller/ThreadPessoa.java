package controller;

import java.util.concurrent.Semaphore;

public class ThreadPessoa extends Thread {

	private int idPessoa;
	private static int posChegada;
	private static int posSaida;
	private Semaphore semaforo;

	public ThreadPessoa(int idPessoa, Semaphore semaforo) {

		this.idPessoa = idPessoa;
		this.semaforo = semaforo;
	}

	public void run() {

		pessoaAndando();
		// ----seção critica ----
		try {
			semaforo.acquire();
			pessoaPassando();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			// --- Fim da seção critica ----
			semaforo.release();
			pessoaSaiu();

		}

	}

	private void pessoaAndando() {

		int corredor = 200;
		int espacoPercorrido = 0;

		while (espacoPercorrido < corredor) {

			espacoPercorrido += (int) ((Math.random() * 3) + 4);

			System.out.println("A pessoa #" + idPessoa + " ja percorreu " + espacoPercorrido + ".m");
		}
		posChegada++;
		System.out.println("A pessoa #" + idPessoa + " chegou em " + posChegada + ".o ");

	}

	private void pessoaPassando() {
		System.out.println("A pessoa #" + idPessoa + " esta passando pela porta");

		int tempo = (int) ((Math.random() * 1001) + 1000);
		try {
			sleep(tempo);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	private void pessoaSaiu() {
		posSaida++;
		System.out.println("A pessoa #" + idPessoa + " saiu ");

	}
}
