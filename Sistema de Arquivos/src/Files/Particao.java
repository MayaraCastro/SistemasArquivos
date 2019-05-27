package Files;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Particao {

	private String nome;
	private int tamanhoTotal;
	private int tamanhoUtilizado;
	private Bloco [] blocos;
	private ArrayList<Diretorio> diretorios;
	private LocalDateTime dataDeCriacao;
	
	public Particao(String nome, int tamanhoTotal) {
		this.nome = nome;
		this.tamanhoTotal = tamanhoTotal;
		this.tamanhoUtilizado = 0;
		this.blocos = new Bloco[tamanhoTotal/Bloco.getTamanho()];
		this.diretorios = new ArrayList<Diretorio>();
		this.dataDeCriacao = LocalDateTime.now();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getTamanhoTotal() {
		return tamanhoTotal;
	}

	public void setTamanhoTotal(int tamanhoTotal) {
		this.tamanhoTotal = tamanhoTotal;
	}

	public int getTamanhoUtilizado() {
		return tamanhoUtilizado;
	}

	public Bloco [] getBlocos() {
		return blocos;
	}

	public ArrayList<Diretorio> getDiretorios() {
		return diretorios;
	}

	public LocalDateTime getDataDeCriacao() {
		return dataDeCriacao;
	}
	
	public boolean adicionaDiretorio(Diretorio dir) {
		int k = verificaEspaco(dir.getTamanho()/Bloco.getTamanho());
		boolean resultado = false;
		if (k == -1)
			return resultado;
		this.diretorios.add(dir);
		this.tamanhoUtilizado += dir.getTamanho();
		resultado = true;
		for (int i = k; i < dir.getTamanho()/Bloco.getTamanho(); i++)
			blocos[i] = new Bloco(dir);
		return resultado;
	}
	
	public boolean adicionaBloco(Diretorio dir) {
		int k = verificaEspaco(dir.getTamanho()/Bloco.getTamanho());
		boolean resultado = false;
		if (k == -1)
			return resultado;
		resultado = true;
		for (int i = 0; i < dir.getTamanho()/Bloco.getTamanho(); i++)
			blocos[k++] = new Bloco(dir);
		return resultado;
	}
	
	public boolean adicionaBloco(Arquivo arq) {
		int k = verificaEspaco(arq.getTamanho()/Bloco.getTamanho());
		boolean resultado = false;
		if (k == -1)
			return resultado;
		resultado = true;
		for (int i = 0; i < arq.getTamanho()/Bloco.getTamanho(); i++)
			blocos[k++] = new Bloco(arq);
		return resultado;
	}
	
	private int verificaEspaco(int tamanho) {
		int idx = -1;
		int counter = 0;
		boolean found  = false;
		for (int i = 0; i < this.tamanhoTotal/Bloco.getTamanho(); i++) {
			if (blocos[i] == null) {
				if (!found) {
					found = true;
					idx = i;
					counter++;
				} else {
					counter++;
				}
				if (counter == tamanho)
					break;
			} else {
				found = false;
				counter = 0;
			}
		}
		if (counter < tamanho)
			idx = -1;
		return idx;
	}
	
	public void atualizaTamanho() {
		this.tamanhoUtilizado = 0;
		for (int i = 0; i < diretorios.size(); i++)
			this.tamanhoUtilizado += diretorios.get(i).getTamanho();
	}
	
	public void compacta() {
		ArrayList<Bloco> aux = new ArrayList<Bloco>();
		for (int i = 0; i < blocos.length; i++) {
			if (blocos[i] != null)
				aux.add(blocos[i]);
		}
		for (int i = 0; i < aux.size(); i++)
			blocos[i] = aux.get(i);
		for (int i = aux.size(); i < blocos.length; i++)
			blocos[i] = null;
	}
	
	@Override
	public String toString() {
		String retorno = "----------------------------------------------------------------\n";
		retorno += "Partição " + this.getNome() + "\n";
		retorno += "\tTamanho total: " + this.tamanhoTotal + "\n";
		retorno += "\tTamanho utilizado: " + this.tamanhoUtilizado + "\n";
		retorno += "\tData de criação: " + this.dataDeCriacao + "\n";
		if (diretorios.size() != 0) {
			retorno += "\tDiretórios dentro dessa partição:\n";
			Diretorio dir;
			for (int i = 0; i < diretorios.size(); i++) {
				dir = diretorios.get(i);
				retorno += "\t*****************************************\n";
				retorno += "\t\tDiretório " + dir.getNome() + "\n";
				retorno += "\t\t\tTamanho: " + dir.getTamanho() + "\n";
				retorno += "\t\t\tData de criação: " + dir.getDataDeCriacao() + "\n";
				retorno += "\t\t\tData de modificação: " + dir.getDataDeModificacao() + "\n";
				retorno += "\t*****************************************\n";
			}
		}
		retorno += "----------------------------------------------------------------\n";
		return retorno;
	}
	
}
