package Files;

import java.util.Scanner;

public class PromptComando {
	
	private static String help() {
		String help = "\nComandos disponíveis:\n";
		help += "\texit - fecha o programa\n";
		help += "\tinfoArq - informações de um arquivo\n";
		help += "\tinfoDir - informações do diretório atual\n";
		help += "\tinfoPart - informações da partição\n";
		help += "\tremoveDir - remove um diretório dentro do diretório atual\n";
		help += "\tremoveArq - remove um arquivo dentro do diretório atual\n";
		help += "\tcriaArq - cria um arquivo do tamanho que o usuário deseja\n";
		help += "\tcriaArqTexto - cria um arquivo de texto com os dados de entrada do usuário\n";
		help += "\tcriaDir - cria um diretório dentro do caminho atual\n";
		help += "\tcriaDirPath - cria um diretório dentro do caminho informado\n";
		help += "\tnavega - navega para um caminho informado\n";
		help += "\tenter - entra em um diretório dentro do diretório/partição atual\n";
		help += "\tout - sai do diretório atual para o anterior\n";
		help += "\tinfoBlocoArq - informações a respeito dos blocos do disco referente aos "
				+ "arquivos do diretório atual\n";
		help += "\tinfoBlocoDir - informações a respeito dos blocos do disco referente ao "
				+ "diretório atual\n";
		help += "\tinfoBloco - informações a respeito dos blocos do disco referente a todos "
				+ "os blocos existentes\n";
		help += "\tcompacta - compacta a memória\n";
		help += "\thelp - comandos suportados pelo programa\n";
		return help;
	}


	public static void main(String[] args) {
		 
			
		GerenciaArquivos manager = GerenciaArquivos.getInstance();
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
		Particao principal = new Particao(nome, tamanho);
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
			
			switch (comando) {
			case "exit":
				return;
	
			case "rmkdir":
				manager.removeDiretorio(nome);
				break;
				
			case "rm":
				manager.removeArquivo(nome);
				break;
				
			case "touch":
				System.out.print("Tamanho do arquivo: ");
				tamanho = sc.nextInt();
				sc.nextLine();
				manager.criaArquivo(nome, tamanho);
				break;
				
			case "txt": //cria arquivo txt
				System.out.println("Texto do arquivo: ");
				text = sc.nextLine();
				manager.criaArquivoDados(nome, text);
				break;
				
			case "mkdir":
				if(nome.charAt(0)=='\\') {// com path absoluto
					//separa nome e caminho
					caminho = nome;
					for(int i = caminho.length()-1; i>0; i--) {
						if(caminho.charAt(i)=='\\') {
							nome = caminho.substring(i+1, caminho.length());
							caminho = caminho.substring(0, i+1);
							System.out.println(caminho);
							break;
						}
					}
					manager.mkdir(caminho, nome);
				}else {//relativo
					manager.mkdir(nome);
				}
				
				break;
				
			case "cd":
				caminho = nome;
				if(caminho.charAt(0)=='\\') {
					manager.navega(caminho);
				}else if(caminho .length()>= 3 && caminho.substring(0, 3).equals("..\\")) {
					manager.out();
				}else {
					manager.enter(caminho);
				}
				
				break;
				
			case "help":
				System.out.println(help());
				break;
				
			case "infoBlocoArq":
				manager.infoBlocoArq();
				break;
			case "infoBlocoDir":
				manager.infoBlocoDir(manager.getDirAtual());
				break;
			case "infoBloco":
				manager.infoBloco();
				break;
			case "infoArq":
				manager.infoArq(nome);
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
