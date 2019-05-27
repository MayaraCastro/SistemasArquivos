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
		String key;
		String text;
		String caminho;
		int tamanho;
		System.out.println("\t\t\tBem-vindo!");
		System.out.print("Dê um nome a sua partição: ");
		nome = sc.nextLine();
		System.out.print("Informe o tamanho da sua partição: ");
		tamanho = sc.nextInt();
		sc.nextLine();
		Particao principal = new Particao(nome, tamanho);
		manager.setPrincipal(principal);
		System.out.println("Partição criada com sucesso!");
		while(true) {
			System.out.print(manager.getCaminho() + ">");
			key = sc.nextLine();
			switch (key) {
			case "exit":
				return;
			case "infoArq":
				System.out.print("Nome do arquivo: ");
				nome = sc.nextLine();
				manager.infoArq(nome);
				break;
			case "infoDir":
				manager.infoDir();
				break;
			case "infoPart":
				manager.infoPart();
				break;
			case "removeDir":
				System.out.print("Nome do diretório: ");
				nome = sc.nextLine();
				manager.removeDiretorio(nome);
				break;
			case "removeArq":
				System.out.print("Nome do arquivo: ");
				nome = sc.nextLine();
				manager.removeArquivo(nome);
				break;
			case "criaArq":
				System.out.print("Nome do arquivo: ");
				nome = sc.nextLine();
				System.out.print("Tamanho do arquivo: ");
				tamanho = sc.nextInt();
				sc.nextLine();
				manager.criaArquivo(nome, tamanho);
				break;
			case "criaArqTexto":
				System.out.print("Nome do arquivo: ");
				nome = sc.nextLine();
				System.out.println("Texto do arquivo: ");
				text = sc.nextLine();
				manager.criaArquivoDados(nome, text);
				break;
			case "criaDir":
				System.out.print("Nome do diretório: ");
				nome = sc.nextLine();
				manager.criaDiretorio(nome);
				break;
			case "criaDirPath":
				System.out.print("Nome do diretório: ");
				nome = sc.nextLine();
				System.out.print("Caminho do diretório: ");
				caminho = sc.nextLine();
				manager.criaDiretorio(caminho, nome);
				break;
			case "navega":
				System.out.print("Caminho: ");
				caminho = sc.nextLine();
				manager.navega(caminho);
				break;
			case "help":
				System.out.println(help());
				break;
			case "enter":
				System.out.print("Diretório: ");
				nome = sc.nextLine();
				manager.enter(nome);
				break;
			case "out":
				manager.out();
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
