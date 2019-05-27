package Files;

public class Block {

	private File file;
	private Directory dir;
	private static final int SIZE = 1;
	
	public Block(File file) {
		this.file = file;
		this.dir = null;
	}
	
	public Block(Directory dir) {
		this.file = null;
		this.dir = dir;
	}


	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public Directory getDir() {
		return dir;
	}

	public void setDir(Directory dir) {
		this.dir = dir;
	}

	public static int getSize() {
		return SIZE;
	}

	@Override
	public String toString() {
		return "Bloco [" + (file != null ? "Arquivo = " + file.getExtensionName() + ", Caminho = " + "" + 
						file.getPath() : "") + (dir != null ? "Diretório = "	+ "" + dir.getName() + 
						", Caminho = " + dir.getpath() : "") + "]\n";
	}
	
	public static String blocoNull() {
		return "Bloco []\n";
	}
	
}
