package Files;

public class FileSystem {

	private Partition principal;
	private String caminho;
	private Directory usedDir;
	private static FileSystem instance;
	
	private FileSystem() {
		this.caminho = "";
		this.usedDir = null;
	}
	
	public static FileSystem getInstance() {
		if (instance == null)
			instance = new FileSystem();
		return instance;
	}

	public Partition getPrincipal() {
		return principal;
	}

	public void setPrincipal(Partition principal) {
		this.principal = principal;
		this.caminho = principal.getName() + ":\\";
	}
	
	public String getCaminho() {
		return caminho;
	}

	public Directory getusedDir() {
		return usedDir;
	}

	private int buscaDiretorio(String caminho) {
		Directory auxiliar = usedDir;
		usedDir = null;
		int profundidade = 0;
		boolean valido = false;
		for (int i = 0; i < caminho.length(); i++) {
			if (caminho.charAt(i) == '\\') {
				profundidade++;
				valido = true;
			} else {
				valido = false;
			}
		}
		if (!valido) {
			usedDir = auxiliar;
			return -1;
		}
		if (profundidade == 1)
			return profundidade;
		String [] busca = new String[profundidade];
		String aux = "";
		int j = 0;
		for (int i = 0; i < caminho.length(); i++) {
			if (caminho.charAt(i) != '\\') {
				aux += caminho.charAt(i);
			} else {
				busca[j++] = aux;
				aux = "";
			}
		}
		for (j = 0; j < principal.getdirectories().size(); j++) {
			Directory dir = principal.getdirectories().get(j);
			if (dir.getName().equals(busca[1])) {
				usedDir = dir;
				break;
			}
		}
		if (usedDir == null) {
			usedDir = auxiliar;
			return -1;
		}
		if (profundidade == 2) {
			this.caminho = usedDir.getpath();
			return profundidade;
		}
		boolean found = false;
		for (int i = 2; i < busca.length; i++) {
			for (j = 0; j < usedDir.getdirectories().size(); j++) {
				if (usedDir.getdirectories().get(j).getName().equals(busca[i])) {
					usedDir = usedDir.getdirectories().get(j);
					found = true;
					break;
				}
			}
			if (!found) {
				usedDir = auxiliar;
				return -1;
			} else {
				found = false;
			}
		}
		this.caminho = usedDir.getpath();
		return profundidade;
	}

	public void navega(String caminho) {
		int navega = buscaDiretorio(caminho);
		if (navega != -1) {
			if (navega == 1) {
				this.caminho = caminho;
				this.usedDir = null;
			}
		} else {
			System.err.println("O sistema não pode encontrar o caminho especificado.");
		}
	}
	
	public void enter(String nome) {
		if (usedDir == null) {
			if (principal.getdirectories().size() == 0) {
				System.err.println("Essa partição não possui diretórios.");
				return;
			}
			Directory dir = null;
			for (int i = 0; i < principal.getdirectories().size(); i++) {
				if (principal.getdirectories().get(i).getName().equals(nome)) {
					dir = principal.getdirectories().get(i);
					break;
				}
			}
			if (dir != null) {
				this.usedDir = dir;
				this.caminho = dir.getpath();
			} else {
				System.err.println("Não existe o diretório informado.");
			}
		} else {
			if (usedDir.getdirectories().size() == 0) {
				System.err.println("Esse diretório não possui outros diretórios.");
				return;
			}
			Directory dir = null;
			for (int i = 0; i < usedDir.getdirectories().size(); i++) {
				if (usedDir.getdirectories().get(i).getName().equals(nome)) {
					dir = usedDir.getdirectories().get(i);
					break;
				}
			}
			if (dir != null) {
				this.usedDir = dir;
				this.caminho = dir.getpath();
			} else {
				System.err.println("Não existe o diretório informado.");
			}
		}
	}
	
	public void out() {
		if (usedDir == null) {
			System.err.println("Não é possível sair de uma partição.");
		} else {
			if (usedDir.getparent() == null) {
				this.usedDir = null;
				this.caminho = this.principal.getName() + ":\\";
			} else {
				this.usedDir = this.usedDir.getparent();
				this.caminho = this.usedDir.getpath();
			}
		}
	}
	
	public void mkdir(String caminho, String nome) {
		if (principal.getusedSize() + 2 > principal.gettotalSize()) {
			System.err.println("Não há espaço suficiente para criar um diretório.");
			return;
		}
		if (buscaDiretorio(caminho) != -1) {
			String path = caminho;
			path += nome + "\\";
			Directory dir = new Directory(nome, path, usedDir);
			if (usedDir == null) {
				for (int i = 0; i < principal.getdirectories().size(); i++) {
					if (principal.getdirectories().get(i).getpath().equals(dir.getpath())) {
						System.err.println("Não é possível criar diretórios com mesmo nome.");
						return;
					}
				}
				if(!principal.addDirectory(dir)) {
					System.err.println("Necessita compactação para a criação do diretório.");
					return;
				}
			} else {
				for (int i = 0; i < usedDir.getdirectories().size(); i++) {
					if (usedDir.getdirectories().get(i).getpath().equals(dir.getpath())) {
						System.err.println("Não é possível criar diretórios com mesmo nome.");
						return;
					}
				}
				if (!principal.addBlock(dir)) {
					System.err.println("Necessita compactação para a criação do diretório.");
					return;
				}
				usedDir.addDirectory(dir);
				principal.updatesize();
			}
			this.caminho = path;
			this.usedDir = dir;
			System.out.println("Diretório adicionado com sucesso.");
		} else {
			System.err.println("O sistema não pode encontrar o caminho especificado.");
		}
	}

	public void mkdir(String nome) {
		if (principal.getusedSize() + 2 > principal.gettotalSize()) {
			System.err.println("Não há espaço suficiente para criar um diretório.");
			return;
		}
		String path = this.caminho;
		path += nome + "\\";
		Directory dir = new Directory(nome, path, usedDir);
		if (usedDir == null) {
			for (int i = 0; i < principal.getdirectories().size(); i++) {
				if (principal.getdirectories().get(i).getpath().equals(dir.getpath())) {
					System.err.println("Não é possível criar diretórios com mesmo nome.");
					return;
				}
			}
			if(!principal.addDirectory(dir)) {
				System.err.println("Necessita compactação para a criação do diretório.");
				return;
			}
		} else {
			for (int i = 0; i < usedDir.getdirectories().size(); i++) {
				if (usedDir.getdirectories().get(i).getpath().equals(dir.getpath())) {
					System.err.println("Não é possível criar diretórios com mesmo nome.");
					return;
				}
			}
			if (!principal.addBlock(dir)) {
				System.err.println("Necessita compactação para a criação do diretório.");
				return;
			}
			usedDir.addDirectory(dir);
			principal.updatesize();
		}
		this.caminho = path;
		this.usedDir = dir;
		System.out.println("Diretório adicionado com sucesso.");
	}

	public void txt(String nome, String dados) {
		if (principal.getusedSize() + dados.length() + 1 > principal.gettotalSize()) {
			System.err.println("Não há espaço suficiente para criar este File.");
			return;
		}
		if (usedDir == null) {
			System.err.println("Não é possível criar Files em uma partição.");
			return;
		}
		nome += ".txt";
		for (int i = 0; i < usedDir.getfiles().size(); i++) {
			if (usedDir.getfiles().get(i).getExtension().equals(nome)) {
				System.err.println("Não é possível criar Files com mesmo nome.");
				return;
			}
		}
		File file = new File(nome, dados, this.caminho, usedDir);
		
		usedDir.addFile(file);
		principal.updatesize();
		System.out.println("File adicionado com sucesso.");
	}
	
	public void touch(String nome, int tamanho) {
		if (principal.getusedSize() + tamanho > principal.gettotalSize()) {
			System.err.println("Não há espaço suficiente para criar um File.");
			return;
		}
		if (usedDir == null) {
			System.err.println("Não é possível criar Files em uma partição.");
			return;
		}
		for (int i = 0; i < usedDir.getfiles().size(); i++) {
			if (usedDir.getfiles().get(i).getExtension().equals(nome)) {
				System.err.println("Não é possível criar Files com mesmo nome.");
				return;
			}
		}
		File file = new File(nome, tamanho, this.caminho, usedDir);
		if(!principal.addBlock(file)) {
			System.err.println("Necessita compactação para a criação do File.");
			return;
		}
		usedDir.addFile(file);
		principal.updatesize();
		System.out.println("File adicionado com sucesso.");
	}
	
	public void removeFile(String nome) {
		if (usedDir == null) {
			System.err.println("O sistema não encontra-se em um diretório.");			
		} else {
			File file = null;
			for (int i = 0; i < usedDir.getfiles().size(); i++) {
				if (usedDir.getfiles().get(i).getName().equals(nome)) {
					file = usedDir.getfiles().get(i);
					break;
				}
			}
			if (file == null) {
				System.err.println("O sistema não pode encontrar o File especificado.");
			} else {
				
			removeBlockfile(file);
			usedDir.removeFile(file);
			principal.updatesize();
			System.out.println("File removido com sucesso.");
		}
	}
	}
	
	private void removeBlockfile(File file) {
		for (int i = 0; i < principal.getblocks().length; i++) {
			if (principal.getblocks()[i] != null) {
				if (principal.getblocks()[i].getFile() != null) {
					if (principal.getblocks()[i].getFile().equals(file))
						principal.getblocks()[i] = null;

				}
			}
		}
	}
	
	public void removeDirectory(String nome) {
		Directory dir = null;
		if (usedDir == null) {
			for (int i = 0; i < principal.getdirectories().size(); i++) {
				if (principal.getdirectories().get(i).getName().equals(nome)) {
					dir = principal.getdirectories().get(i);
					break;
				}
			}
			if (dir == null) {
				System.err.println("O sistema não pode encontrar o diretório especificado.");
				return;
			} else {
				removeBlocoDiretorio(dir);
				principal.getdirectories().remove(dir);
				principal.updatesize();
				System.out.println("Diretório removido com sucesso.");
			}
		} else {
			for (int i = 0; i < usedDir.getdirectories().size(); i++) {
				if (usedDir.getdirectories().get(i).getName().equals(nome)) {
					dir = usedDir.getdirectories().get(i);
					break;
				}
			}
			if (dir == null) {
				System.err.println("O sistema não pode encontrar o diretório especificado.");
				return;
			} else {
				removeBlocoDiretorio(dir);
				usedDir.removeDirectory(dir);
				principal.updatesize();
				System.out.println("Diretório removido com sucesso.");
			}
		}
	}
	
	private void removeBlocoDir(Directory dir) {
		for (int i = 0; i < principal.getblocks().length; i++) {
			if (principal.getblocks()[i] != null) {
				if (principal.getblocks()[i].getDir() != null) {
					if (principal.getblocks()[i].getDir().equals(dir))
						principal.getblocks()[i] = null;

				}
			}
		}
	}
	
	private void removeBlocoDiretorio(Directory dir) {
			for (int i = 0; i < dir.getfiles().size(); i++)
				removeBlockfile(dir.getfiles().get(i));
			for (int i = 0; i < dir.getdirectories().size(); i++)
				removeBlocoDir(dir.getdirectories().get(i));
			removeBlocoDir(dir);
	}
	
	public void infoDir() {
		if (usedDir == null) {
			System.err.println("O sistema não encontra-se em um diretório.");
		} else {
			System.out.println(usedDir.toString());
		}
	}
	
	public void ls() {
		if (usedDir == null) {
			System.err.println("O sistema não encontra-se em um diretório.");
		} else {
			System.out.println(usedDir.ls());
		}
		
	}
	public void infoPart() {
		System.out.print(principal.toString());
	}
	
	public void infofile(String nome) {
		if (usedDir == null) {
			System.err.println("O sistema não encontra-se em um diretório.");			
		} else {
			File file = null;
			for (int i = 0; i < usedDir.getfiles().size(); i++) {
				if (usedDir.getfiles().get(i).getName().equals(nome)) {
					file = usedDir.getfiles().get(i);
					break;
				}
			}
			if (file == null) {
				System.err.println("O sistema não pode encontrar o File especificado.");
			} else {
				System.out.println(file.toString());
			}
		}
	}
	
	public void infoBlocoDir(Directory dir) {
		if (usedDir == null) {
			System.err.println("Não existe informações a respeito de blocos "
					+ "referente a partição.");
			return;
		}
		boolean found = false;
		for (int i = 0; i < principal.getblocks().length; i++) {
			if (principal.getblocks()[i] != null) {
				if (principal.getblocks()[i].getDir().equals(dir))

					found = true;
				if (found)
					break;
			}
		}
		if (!found) {
			System.err.println("Diretório não encontrado na lista de blocos.");
			return;
		}
		boolean pertence = false;
		int k = 0;
		for (int i = 0; i < dir.getsize()/Block.getSize(); i++) {
			if (principal.getblocks()[k] == null) {

				i--;
				k++;
				continue;
			}
			if (principal.getblocks()[k].getFile() != null) {
				if (dir.getfiles().contains(principal.getblocks()[k].getFile())) {

					k++;
					continue;
				}
			}
			if (principal.getblocks()[k].getDir().equals(dir)) {
				System.out.print("\t" + k + ".\t" + principal.getblocks()[k++].toString());
				continue;
			}
			for (int j = 0; j < dir.getdirectories().size(); j++) {
				if (principal.getblocks()[k].getDir().equals(dir.getdirectories().get(j))) {

					pertence = true;
					break;
				}
			}
			if (pertence) {
				System.out.print("\t" + k + ".\t" + principal.getblocks()[k++].toString());

				pertence = false;
			} else {
				i--;
				k++;
			}
		}
	}
	
	public void infoBlocofile() {
		if (usedDir == null) {
			System.err.println("Não existe Files em caminho de partição.");
			return;
		}
		if (usedDir.getfiles().size() == 0) {
			System.err.println("Não há Files no diretório.");
			return;
		} else {
			int k;
			for (int i = 0; i < usedDir.getfiles().size(); i++) {
				for (k = 0; k < principal.getblocks().length; k++) {
					if (principal.getblocks()[k] != null) {
						if (principal.getblocks()[k].getFile() != null) {
							if (principal.getblocks()[k].getFile().equals(usedDir.getfiles().get(i)))

								break;
						}
					}
				}
				for (int j = 0; j < usedDir.getfiles().get(i).getSize()/Block.getSize(); j++)
					System.out.print("\t" + k + ".\t" + principal.getblocks()[k++].toString());

			}
		}
	}
	
	public void compacta() {
		System.out.println("Compactando memória.");
		principal.compacta();
		System.out.println("Memória compactada.");
	}
	
	public void infoBloco() {

		for (int i = 0; i < principal.getblocks().length; i++) {
			if (principal.getblocks()[i] == null) {
				System.out.print("\t" + i + ".\t" + Block.blocoNull());
				continue;
			}
			System.out.print("\t" + i + ".\t" + principal.getblocks()[i].toString());
		}
	}
	
}
