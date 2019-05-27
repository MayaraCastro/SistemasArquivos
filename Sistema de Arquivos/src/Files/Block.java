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
		return "Block [" + (file != null ? "File : " + file.getExtensionName() + ", Path : " +  
						file.getPath() : "") + (dir != null ? "Directory = " + "" + dir.getName() + 
						", Path = " + dir.getpath() : "") + "]\n";
	}
	
	public static String nullBlock() {
		return "Block []\n";
	}
	
}
