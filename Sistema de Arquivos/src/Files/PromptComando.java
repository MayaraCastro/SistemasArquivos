package Files;

import java.util.Scanner;

public class PromptComando {
	
	private static String help() {
		String help = "\nComandos disponíveis:\n";
		
		
		
		help += "\trmkdir - remove um diretório dentro do diretório atual\n";
		help += "\trm - remove um arquivo dentro do diretório atual\n";
		help += "\ttouch - cria um arquivo do tamanho que o usuário deseja\n";
		help += "\ttxt - cria um arquivo de texto com os dados de entrada do usuário\n";
		help += "\tmkdir - cria um diretório dentro do caminho atual ou dentro do caminho informado\n";
		help += "\tcd - navega para um caminho informado\n";
		help += "\tls - faz a listagem dos arquivos/direórios de um diretório\n";
		help += "\tpwd - faz a listagem dos arquivos/direórios de um diretório\n";
		
		help += "\tinfoArq - informações de um arquivo\n";
		help += "\tinfoDir - informações do diretório atual\n";
		help += "\tinfoPart - informações da partição\n";
		help += "\tinfoBlocoArq - informações a respeito dos blocos do disco referente aos "
				+ "arquivos do diretório atual\n";
		help += "\tinfoBlocoDir - informações a respeito dos blocos do disco referente ao "
				+ "diretório atual\n";
		help += "\tinfoBloco - informações a respeito dos blocos do disco referente a todos "
				+ "os blocos existentes\n";
		help += "\tcompacta - compacta a memória\n";
		help += "\thelp - comandos suportados pelo programa\n";
		help += "\texit - fecha o programa\n";
		return help;
	}


	public static void main(String[] args) {
		 
			
		FileSystem manager = FileSystem.getInstance();
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		String nome;
		String text;
		String caminho;
		int tamanho;
		System.out.println("\t\t\tBem-vindo!");
		System.out.print("Criar partição: ");
		nome = sc.nextLine();
		System.out.print("Tamanho da partição: ");
		tamanho = sc.nextInt();
		sc.nextLine();
		Partition principal = new Partition(nome, tamanho);
		manager.setPrincipal(principal);
		System.out.println("\n");
		while(true) {
			
			System.out.print(manager.getCaminho() + ">");
			String line;
			String comando = null;
			nome=null;
			line = sc.nextLine();
			
			//Separa o comando e o nome
			for(int i =0; i<line.length();i++) {
				if(line.charAt(i)==' ' || i== line.length()-1 ) {
					
					if(i== line.length()-1) {
						comando = line.substring(0, line.length());
					}
					else {
						comando = line.substring(0, i);
						nome = line.substring(i+1, line.length());
					
					}

					break;
				}
			}
			if(comando!=null) {
				switch (comando) {
			case "exit":
				return;
	
			case "rmkdir":
				if(nome == null) {
					System.out.println("Nome invalido!");
				}else {
				manager.removeDirectory(nome);
				}
				break;
				
			case "rm":
				if(nome == null) {
					System.out.println("Nome invalido!");
				}else {
				System.out.println(nome);
				manager.removeFile(nome);
				}
				break;
				
			case "touch":
				if(nome == null) {
					System.out.println("Nome invalido!");
				}else {
					System.out.print("Tamanho do arquivo: ");
				tamanho = sc.nextInt();
				sc.nextLine();
				manager.touch(nome, tamanho);
				}
				
				break;
				
			case "txt": //cria arquivo txt
				if(nome == null) {
					System.out.println("Nome invalido!");
				}else {
				System.out.println("Texto do arquivo: ");
				text = sc.nextLine();
				manager.txt(nome, text);
				}
				break;
				
			case "mkdir":
				if(nome == null) {
					System.out.println("Nome invalido!");
				}else {
					if(nome.charAt(0)=='\\') {// com path absoluto
						//separa nome e caminho
						caminho = nome;
						for(int i = caminho.length()-1; i>0; i--) {
							if(caminho.charAt(i)=='\\') {
								nome = caminho.substring(i+1, caminho.length());
								caminho = caminho.substring(0, i+1);
								break;
							}
						}
						manager.mkdir(caminho, nome);
					}else {//relativo
						manager.mkdir(nome);
					}
					
				}
				
				break;
				
			case "cd":
				if(nome == null) {
					System.out.println("Caminho invalido!");
				}else {
				caminho = nome;
				if(caminho.charAt(0)=='\\') {
					manager.navega(caminho);
				}else if(caminho .length()>= 3 && caminho.substring(0, 3).equals("..\\")) {
					manager.out();
				}else {
					manager.enter(caminho);
				}
				}
				
				break;
				
			case "pwd":
				System.out.println(manager.getCaminho());
				break;
				
			case "ls":
				manager.ls();
				break;	
				
			case "help":
				System.out.println(help());
				break;
				
			case "infoBlocoArq":
				manager.infoBlocofile();
				break;
			case "infoBlocoDir":
				manager.infoBlocoDir(manager.getusedDir());
				break;
			case "infoBloco":
				manager.infoBloco();
				break;
			case "infoArq":
				manager.infofile(nome);
				break;
				
			case "infoDir":
				manager.infoDir();
				break;
				
			case "infoPart":
				manager.infoPart();
				break;
			case "compacta":
				manager.compacta();
				break;
			
			default:
				System.err.println("\nComando não reconhecido pelo sistema.");
				break;
			}
			}
			
		}
		
	}
	
}
