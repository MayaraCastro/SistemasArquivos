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
	
	public String getName() {
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

	public ArrayList<Directory> getdirectories() {
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

	public void addFile(File file) {
		this.files.add(file);
		updateSize();
		this.modificationDate = LocalDateTime.now();
	}
	
	public void removeFile(File file) {
		this.files.remove(file);
		updateSize();
		this.modificationDate = LocalDateTime.now();
	}
	
	public void addDirectory(Directory dir) {
		this.directory.add(dir);
		updateSize();
		this.modificationDate = LocalDateTime.now();
	}
	
	public void removeDirectory(Directory dir) {
		this.directory.remove(dir);
		updateSize();
		this.modificationDate = LocalDateTime.now();
	}
	
	@Override
	public String toString() {
		String retorno = "----------------------------------------------------------------\n";
		retorno += "Diretório " + this.name + ":\n";
		retorno += "\tsize: " + this.size + "\n";
		retorno += "\tData de criação: " + this.creationDate.toLocalDate().toString() + "\n";
		retorno += "\tData de modificação: " + this.modificationDate.toLocalDate().toString() + "\n";
		retorno +="path"+this.path;

		retorno += "----------------------------------------------------------------\n";
		
		return retorno;
	}
	public String ls() {
		String retorno = "----------------------------------------------------------------\n";
		retorno+= "Pasta de "+ this.path +"\n";
		if (files.size() != 0) {
			File file;
			retorno += "\n";
			for (int i = 0; i < files.size(); i++)
			{
				file = files.get(i);
			retorno += file.getCreationDate().toLocalDate().toString() +"\t"+ "    " + "\t";
			retorno +=  file.getSize()+"  " + file.getExtensionName()+"\n";
			}
		}
		if (directory.size() != 0) {
			Directory dir;
			for (int i = 0; i < directory.size(); i++) {
				dir = directory.get(i);
				retorno += dir.getmodificationDate().toLocalDate().toString() +"\t"+ "<DIR>" + "\t";
				retorno +=  dir.getsize()+"  " +  dir.getName()+"\n";
		
			}
		}
		retorno += "----------------------------------------------------------------\n";
		
		return retorno;
	}
	
}
