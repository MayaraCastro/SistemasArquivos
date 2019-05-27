package Files;
import java.time.LocalDateTime;

public class File {

	private String name;
	private String extension;
	private LocalDateTime creationDate;
//	private LocalDateTime dataDeModificacao; (manter apenas a data de criação?)
	private int size;
	private String data;
	private String path;
	private Directory parent;
	
	public File(String name, String data, String path, Directory parent) {
		this.data = data;
		this.size = data.length() + 1;
		this.creationDate = LocalDateTime.now();
//		this.dataDeModificacao = LocalDateTime.now();
		this.path = path;
		this.path += name;
		this.setParent(parent);
		makeName(name);
		defineExtension(name);
	}
	
	public File(String name, int size, String path, Directory parent) {
		this.data = "";
		this.size = size;
		this.creationDate = LocalDateTime.now();
//		this.dataDeModificacao = LocalDateTime.now();
		this.path = path;
		this.path += name;
		this.setParent(parent);
		makeName(name);
		defineExtension(name);
	}
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Directory getParent() {
		return parent;
	}

	public void setParent(Directory parent) {
		this.parent = parent;
	}

	public void setCaminho(String caminho) {
		this.path = caminho;
		this.path += this.name;
		this.path += this.extension;
//		this.dataDeModificacao = LocalDateTime.now();
	}

	private void makeName(String nome) {
		String aux = "";
		for (int i = 0; i < nome.length(); i++) {
			if (nome.charAt(i) != '.') {
				aux += nome.charAt(i);
			} else {
				break;
			}
		}
		this.name = aux;
	}
	
	private void defineExtension(String nome) {
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
		this.extension = aux;
	}

	public String getExtensionName() {
		return this.name + this.extension;
	}
	
	@Override
	public String toString() {
		String details = "\t\tFile " + this.getName() + "\n" +
						"\t\t\tSize: " + this.getSize() + "\n" +
						"\t\t\tExtension: " + this.getExtension() + "\n" +
						"\t\t\tCreation Date: " + this.getCreationDate() + "\n";
		return details;
	}
	
}
