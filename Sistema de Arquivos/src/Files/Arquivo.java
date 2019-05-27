package Files;
import java.time.LocalDateTime;

public class Arquivo {

	private String nome;
	private String extensao;
	private LocalDateTime dataDeCriacao;
	private LocalDateTime dataDeModificacao;
	private int tamanho;
	private String dados;
	private String caminho;
	private Diretorio pai;
	
	public Arquivo(String nome, String dados, String caminho, Diretorio pai) {
		this.dados = dados;
		this.tamanho = dados.length() + 1;
		this.dataDeCriacao = LocalDateTime.now();
		this.dataDeModificacao = LocalDateTime.now();
		this.caminho = caminho;
		this.caminho += nome;
		this.setPai(pai);
		geraNome(nome);
		geraExtensao(nome);
	}
	
	public Arquivo(String nome, int tamanho, String caminho, Diretorio pai) {
		this.dados = "";
		this.tamanho = tamanho;
		this.dataDeCriacao = LocalDateTime.now();
		this.dataDeModificacao = LocalDateTime.now();
		this.caminho = caminho;
		this.caminho += nome;
		this.setPai(pai);
		geraNome(nome);
		geraExtensao(nome);
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
		this.dataDeModificacao = LocalDateTime.now();
	}

	public String getExtensao() {
		return extensao;
	}

	public void setExtensao(String extensao) {
		this.extensao = extensao;
		this.dataDeModificacao = LocalDateTime.now();
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

	public String getDados() {
		return dados;
	}

	public void setDados(String dados) {
		this.dados = dados;
		this.tamanho = dados.length() + 1;
		this.dataDeModificacao = LocalDateTime.now();
	}

	public String getCaminho() {
		return caminho;
	}

	public void setCaminho(String caminho) {
		this.caminho = caminho;
		this.caminho += this.nome;
		this.caminho += this.extensao;
		this.dataDeModificacao = LocalDateTime.now();
	}

	public Diretorio getPai() {
		return pai;
	}

	public void setPai(Diretorio pai) {
		this.pai = pai;
	}

	private void geraNome(String nome) {
		String aux = "";
		for (int i = 0; i < nome.length(); i++) {
			if (nome.charAt(i) != '.') {
				aux += nome.charAt(i);
			} else {
				break;
			}
		}
		this.nome = aux;
	}
	
	private void geraExtensao(String nome) {
		String aux = "";
		boolean getExtensao = false;
		for (int i = 0; i < nome.length(); i++) {
			if (nome.charAt(i) == '.') {
				getExtensao = true;
			}
			if (getExtensao) {
				aux += nome.charAt(i);
			}
		}
		this.extensao = aux;
	}
	
	public String getNomeExtensao() {
		return this.nome + this.extensao;
	}
	
	@Override
	public String toString() {
		String retorno = "\t*****************************************\n";
		retorno += "\t\tArquivo " + this.getNome() + "\n";
		retorno += "\t\t\tTamanho: " + this.getTamanho() + "\n";
		retorno += "\t\t\tExtensão: " + this.getExtensao() + "\n";
		retorno += "\t\t\tData de criação: " + this.getDataDeCriacao() + "\n";
		retorno += "\t\t\tData de modificação: " + this.getDataDeModificacao() + "\n";
		retorno += "\t*****************************************\n";
		return retorno;
	}
	
}
