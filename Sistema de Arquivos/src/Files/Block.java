package Files;

public class Block {

	private File file;
	private Diretorio dir;
	private static final int SIZE = 1;
	
	public Block(File file) {
		this.file = file;
		this.dir = null;
	}
	
	public Block(Diretorio dir) {
		this.file = null;
		this.dir = dir;
	}


	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public Diretorio getDir() {
		return dir;
	}

	public void setDir(Diretorio dir) {
		this.dir = dir;
	}

	public static int getSize() {
		return SIZE;
	}

	@Override
	public String toString() {
		return "Bloco [" + (file != null ? "Arquivo = " + file.getExtensionName() + ", Caminho = " + "" + 
						file.getPath() : "") + (dir != null ? "Diretório = "	+ "" + dir.getNome() + 
						", Caminho = " + dir.getCaminho() : "") + "]\n";
	}
	
	public static String blocoNull() {
		return "Bloco []\n";
	}
	
}
