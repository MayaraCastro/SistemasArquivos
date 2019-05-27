package Files;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Directory {

	private String name;
	private LocalDateTime creationDate;
	private LocalDateTime modificationDate;
	private int size;
	private ArrayList<File> files;
	private ArrayList<Directory> directory;
	private String path;
	private Directory parent;
	
	public Directory(String name, String path, Directory parent) {
		this.name = name;
		this.creationDate = LocalDateTime.now();
		this.modificationDate = LocalDateTime.now();
		this.size = 2;
		this.files = new ArrayList<File>();
		this.directory = new ArrayList<Directory>();
		this.path = path;
		this.parent = parent;
	}
	
	public String getname() {
		return name;
	}

	public void setname(String name) {
		this.name = name;
	}

	public LocalDateTime getcreationDate() {
		return creationDate;
	}

	public LocalDateTime getmodificationDate() {
		return modificationDate;
	}

	public int getsize() {
		return size;
	}
	
	public void updateSize() {
		this.size = 2;
		for (int i = 0; i < files.size(); i++)
			this.size += files.get(i).getSize();
		for (int i = 0; i < directory.size(); i++)
			this.size += directory.get(i).getsize();
		if (this.parent != null)
			parent.updateSize();
	}

	public ArrayList<File> getfiles() {
		return files;
	}

	public ArrayList<Directory> getdirectory() {
		return directory;
	}

	public String getpath() {
		return path;
	}

	public void setpath(String path) {
		this.path = path;
		this.path += this.name + "\\";
		this.modificationDate = LocalDateTime.now();
	}

	public Directory getparent() {
		return parent;
	}

	public void setparent(Directory parent) {
		this.parent = parent;
		this.modificationDate = LocalDateTime.now();
	}

	public void adicionaArquivo(File file) {
		this.files.add(file);
		updateSize();
		this.modificationDate = LocalDateTime.now();
	}
	
	public void removeArquivo(File file) {
		this.files.remove(file);
		updateSize();
		this.modificationDate = LocalDateTime.now();
	}
	
	public void adicionaDiretorio(Directory dir) {
		this.directory.add(dir);
		updateSize();
		this.modificationDate = LocalDateTime.now();
	}
	
	public void removeDiretorio(Directory dir) {
		this.directory.remove(dir);
		updateSize();
		this.modificationDate = LocalDateTime.now();
	}
	
	@Override
	public String toString() {
		String retorno = "----------------------------------------------------------------\n";
		retorno += "Diretório " + this.name + ":\n";
		retorno += "\tsize: " + this.size + "\n";
		retorno += "\tData de criação: " + this.creationDate + "\n";
		retorno += "\tData de modificação: " + this.modificationDate + "\n";
		retorno +="path"+this.path;
		if (files.size() != 0) {
			retorno += "\tfiles do diretório:\n\n";
			for (int i = 0; i < files.size(); i++)
				retorno += files.get(i).toString();
		}
		if (directory.size() != 0) {
			retorno += "\tDiretórios dentro desse diretório:\n";
			Directory dir;
			for (int i = 0; i < directory.size(); i++) {
				dir = directory.get(i);
				retorno += "\t*****************************************\n";
				retorno += "\t\tDiretório " + dir.getname() + "\n";
				retorno += "\t\t\tsize: " + dir.getsize() + "\n";
				retorno += "\t\t\tData de criação: " + dir.getcreationDate() + "\n";
				retorno += "\t\t\tData de modificação: " + dir.getmodificationDate() + "\n";
				retorno += "\t*****************************************\n";
				retorno +="path"+dir.path;
			}
		}
		retorno += "----------------------------------------------------------------\n";
		
		return retorno;
	}
	
}
