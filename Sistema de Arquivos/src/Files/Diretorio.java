package Files;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Diretorio {

	private String nome;
	private LocalDateTime dataDeCriacao;
	private LocalDateTime dataDeModificacao;
	private int tamanho;
	private ArrayList<Arquivo> arquivos;
	private ArrayList<Diretorio> diretorios;
	private String caminho;
	private Diretorio pai;
	
	public Diretorio(String nome, String caminho, Diretorio pai) {
		this.nome = nome;
		this.dataDeCriacao = LocalDateTime.now();
		this.dataDeModificacao = LocalDateTime.now();
		this.tamanho = 2;
		this.arquivos = new ArrayList<Arquivo>();
		this.diretorios = new ArrayList<Diretorio>();
		this.caminho = caminho;
		this.pai = pai;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public LocalDateTime getDataDeCriacao() {
		return dataDeCriacao;
	}

	public LocalDateTime getDataDeModificacao() {
		return dataDeModificacao;
	}

	public int getTamanho() {
		return tamanho;
	}
	
	public void atualizaTamanho() {
		this.tamanho = 2;
		for (int i = 0; i < arquivos.size(); i++)
			this.tamanho += arquivos.get(i).getTamanho();
		for (int i = 0; i < diretorios.size(); i++)
			this.tamanho += diretorios.get(i).getTamanho();
		if (this.pai != null)
			pai.atualizaTamanho();
	}

	public ArrayList<Arquivo> getArquivos() {
		return arquivos;
	}

	public ArrayList<Diretorio> getDiretorios() {
		return diretorios;
	}

	public String getCaminho() {
		return caminho;
	}

	public void setCaminho(String caminho) {
		this.caminho = caminho;
		this.caminho += this.nome + "\\";
		this.dataDeModificacao = LocalDateTime.now();
	}

	public Diretorio getPai() {
		return pai;
	}

	public void setPai(Diretorio pai) {
		this.pai = pai;
		this.dataDeModificacao = LocalDateTime.now();
	}

	public void adicionaArquivo(Arquivo arq) {
		this.arquivos.add(arq);
		atualizaTamanho();
		this.dataDeModificacao = LocalDateTime.now();
	}
	
	public void removeArquivo(Arquivo arq) {
		this.arquivos.remove(arq);
		atualizaTamanho();
		this.dataDeModificacao = LocalDateTime.now();
	}
	
	public void adicionaDiretorio(Diretorio dir) {
		this.diretorios.add(dir);
		atualizaTamanho();
		this.dataDeModificacao = LocalDateTime.now();
	}
	
	public void removeDiretorio(Diretorio dir) {
		this.diretorios.remove(dir);
		atualizaTamanho();
		this.dataDeModificacao = LocalDateTime.now();
	}
	
	@Override
	public String toString() {
		String retorno = "----------------------------------------------------------------\n";
		retorno += "Diretório " + this.nome + ":\n";
		retorno += "\tTamanho: " + this.tamanho + "\n";
		retorno += "\tData de criação: " + this.dataDeCriacao + "\n";
		retorno += "\tData de modificação: " + this.dataDeModificacao + "\n";
		if (arquivos.size() != 0) {
			retorno += "\tArquivos do diretório:\n\n";
			for (int i = 0; i < arquivos.size(); i++)
				retorno += arquivos.get(i).toString();
		}
		if (diretorios.size() != 0) {
			retorno += "\tDiretórios dentro desse diretório:\n";
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
