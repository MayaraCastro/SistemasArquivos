package Files;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Partition {

	private String name;
	private int totalSize;
	private int usedSize;
	private Block [] blocks;
	private ArrayList<Directory> directories;
	private LocalDateTime dateofCreation;
	
	public Partition(String name, int totalSize) {
		this.name = name;
		this.totalSize = totalSize;
		this.usedSize = 0;
		this.blocks = new Block[totalSize/Block.getSize()];
		this.directories = new ArrayList<Directory>();
		this.dateofCreation = LocalDateTime.now();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int gettotalSize() {
		return totalSize;
	}

	public void settotalSize(int totalSize) {
		this.totalSize = totalSize;
	}

	public int getusedSize() {
		return usedSize;
	}

	public Block [] getblocks() {
		return blocks;
	}

	public ArrayList<Directory> getdirectories() {
		return directories;
	}

	public LocalDateTime getdateofCreation() {
		return dateofCreation;
	}
	
	public boolean addDirectory(Directory dir) {
		int k = verifySpace(dir.getsize()/Block.getSize());
		boolean result = false;
		if (k == -1)
			return result;
		this.directories.add(dir);
		this.usedSize += dir.getsize();
		result = true;
		for (int i = 0; i < dir.getsize()/Block.getSize(); i++)
			blocks[k++] = new Block(dir);
		return result;
	}
	
	public boolean addBlock(Directory dir) {
		int k = verifySpace(dir.getsize()/Block.getSize());
		boolean result = false;
		if (k == -1)
			return result;
		result = true;
		for (int i = 0; i < dir.getsize()/Block.getSize(); i++)
			blocks[k++] = new Block(dir);
		return result;
	}
	
	public boolean addBlock(File file) {
		int k = verifySpace(file.getSize()/Block.getSize());
		boolean result = false;
		if (k == -1)
			return result;
		result = true;
		for (int i = 0; i < file.getSize()/Block.getSize(); i++)
			blocks[k++] = new Block(file);
		return result;
	}
	
	private int verifySpace(int size) {
		int idx = -1;
		int counter = 0;
		boolean found  = false;
		for (int i = 0; i < this.totalSize/Block.getSize(); i++) {
			if (blocks[i] == null) {
				if (!found) {
					found = true;
					idx = i;
					counter++;
				} else {
					counter++;
				}
				if (counter == size)
					break;
			} else {
				found = false;
				counter = 0;
			}
		}
		if (counter < size)
			idx = -1;
		return idx;
	}
	
	public void updatesize() {
		this.usedSize = 0;
		for (int i = 0; i < directories.size(); i++)
			this.usedSize += directories.get(i).getsize();
	}
	
	public void compacta() {
		ArrayList<Block> aux = new ArrayList<Block>();
		for (int i = 0; i < blocks.length; i++) {
			if (blocks[i] != null)
				aux.add(blocks[i]);
		}
		for (int i = 0; i < aux.size(); i++)
			blocks[i] = aux.get(i);
		for (int i = aux.size(); i < blocks.length; i++)
			blocks[i] = null;
	}
	
	@Override
	public String toString() {
		String retorno = "----------------------------------------------------------------\n";
		retorno += "Partição " + this.getName() + "\n";
		retorno += "\tsize total: " + this.totalSize + "\n";
		retorno += "\tsize utilizado: " + this.usedSize + "\n";
		retorno += "\tData de criação: " + this.dateofCreation.toLocalDate().toString() + "\n";
		if (directories.size() != 0) {
			retorno += "\tDiretórios dentro dessa partição:\n";
			Directory dir;
			for (int i = 0; i < directories.size(); i++) {
				dir = directories.get(i);
				retorno += "\t*****************************************\n";
				retorno += "\t\tDiretório " + dir.getName() + "\n";
				retorno += "\t\t\tsize: " + dir.getsize() + "\n";
				retorno += "\t\t\tData de criação: " + dir.getcreationDate().toLocalDate().toString() + "\n";
				retorno += "\t\t\tData de modificação: " + dir.getmodificationDate() + "\n";
				retorno += "\t*****************************************\n";
			}
		}
		retorno += "----------------------------------------------------------------\n";
		return retorno;
	}
	public String ls() {
		String retorno = "----------------------------------------------------------------\n";
		retorno+= "Pasta de "+ this.getName() +":\\"+" \n";
		
		if (directories.size() != 0) {
			retorno += "\n";
			Directory dir;
			for (int i = 0; i < directories.size(); i++) {
				dir = directories.get(i);
				retorno += dir.getmodificationDate().toLocalDate().toString() +"\t"+ "<DIR>" + "\t";
				retorno +=  dir.getsize()+"  " +  dir.getName()+"\n";
			}
		}
		retorno += "----------------------------------------------------------------\n";
		
		return retorno;
	}
		
	
}
