package cadastro;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainCadastro {
	
	static List<Usuario> users = new ArrayList<>();
	static Scanner scan;
	
	public static void main(String[] args) {	
		
		scan = new Scanner(System.in);
			
			boolean quitOption = false;
			String preEscolha = "";
			
			do {
				
				try {
					
					//MENU INICIAL
					System.out.println( "\t= FATEFOOD =\n == CADASTRO DE USUARIOS == \n\n");
					
					mainMenu();
					System.out.print("→ ");
					preEscolha = scan.next();
					//FIM DO MENU
					
					switch(Integer.parseInt(preEscolha)) {
						case 0: 
							quitOption = true;
							break;
						case 1:
							menuCadastroUsuario();
							System.out.print("→ ");
							preEscolha = scan.next();
							
							switch(Integer.parseInt(preEscolha)) {
								case 0:
									return;
								case 1:
									cadastroVendedor();
									break;
								case 2:
									cadastroAdministrador();
									break;									
							}
							break;
						case 2:
							editarUsuario();
							break;
					}
					
				}catch(Exception e){System.err.println("\n\ne: " + e);}
				
			}while(quitOption != true);
			
			System.out.print("\nFim!");
			scan.close();	
		
	}
	
	static void writeList(List<Usuario> list) {
		System.out.println("\n----------------------");
		for(int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
			System.out.println("----------------------");
		}
	}
	
	static List<Usuario> buscaUsuarios(char stLetter) {
		
		List<Usuario> searchList = new ArrayList<>();
			
		for (int i = 0; i < users.size(); i++) {
			
			String lowerCase1 = String.valueOf(users.get(i).getNome().charAt(0));
			String stLetterLC = String.valueOf(stLetter);
			
			if(lowerCase1.equalsIgnoreCase(stLetterLC)) 
				searchList.add(users.get(i));			
		}
		
		//LOG
		//writeList(searchList);
		System.out.println("Resultado da busca: " + searchList.size() + " resultado(s).");

		return searchList;		
	}
	
	static void editarUsuario() {
		
		//RECEBE A ENTRADA DO USUARIO PARA PEGAR A PRIMEIRA LETRA DO NOME
		char stLetter = ' ';
		List<Usuario> listResult;
		
		do {
			scan = new Scanner(System.in);
			
			try {
				System.out.print("Primeira letra do nome: ");
				stLetter = scan.next().charAt(0);
				if(Character.isDigit(stLetter)) {
					System.err.println("Não use números!");
				}
			}catch (Exception e) {System.err.println("erro: Digite apenas a primeira letra!");	}
			//BUSCA POR USUÁRIO
			listResult = buscaUsuarios(stLetter);	
			if(listResult.isEmpty())
				System.out.println("\nNenhum nome econtrado! Tente novamente!");
		}while(Character.isDigit(stLetter) || listResult.isEmpty());
		
		//LOG
		//System.out.println("stLetter: " + stLetter);
		
		
		
		//==============
		
		short idSearch = -1;
		boolean kickLoop = false;
		Usuario userSelected = null;
		
		//SELEÇÃO DE ID
		do {
			scan = new Scanner(System.in);
			
			writeList(listResult);
			
			try {
				System.out.print("Digite o ID: ");
				idSearch = scan.nextShort();
				
				if(idSearch > 0) {
					for(int i = 0; i < listResult.size(); i++) {
						
						System.out.println("i: " + i);
						System.out.println("listSize: " + listResult.size());
						
						if(listResult.get(i).getId() == idSearch) {
							System.out.println("------------------------");
							System.out.println("- Usuário selecionado");
							System.out.println(users.get(i));
							
							System.out.println("------------------------");
							System.out.print("Confirmar seleção [S/ N]: ");
							kickLoop = scan.next().equalsIgnoreCase("s");
							
							i = users.size() - 1;//Quebra o laço
							
							if(kickLoop) {
								userSelected = users.get(i);								
							}
						}else
							if(i == listResult.size())
								System.err.println("erro: ID não encontrado! Tente novamente!");
					}
											
				}else
					System.err.println("Número de ID deve ser maior que 0!");
				
			}catch (Exception e) {System.err.println(e);	}
			
		}while(idSearch < 0 || !kickLoop);
		//FIM DA SELEÇÃO DO USUÁRIO
		
		//MENU PARA ALTERAÇÂO
		String option = "";
		System.out.println("----------------------");
		if(userSelected instanceof Administrador) {
			do {
				try {
					System.out.println("  ↓  Escolha uma opção\n" +
									   " [1] - Alterar nome\n" +
									   " [2] - Alterar usuário\n" + 
									   " [3] - Alterar senha\n" +
									   " [4] - Alterar email\n" +
									   " [5] - Alterar cidade\n" + 
									   " [6] - Alterar salário\n" +  
									   " [0] - Sair\n");
					System.out.print("→ ");
				
					option = scan.next();
				}catch(Exception e) {System.out.println("Digite um número valido!\ne: " + e + "\n");};
			
				switch(Integer.parseInt(option)) {
					case 0:
						return;
					case 1:
						//ALTERAR NOME
						System.out.println("- Alterar nome -");
						System.out.println("Nome atual: " + userSelected.getNome());
						System.out.print("- Novo nome: ");
						for(int i = 0; i < users.size(); i++) {
							if(users.get(i).getId() == userSelected.getId()) {
								scan = new Scanner(System.in);
								
								String newName = scan.nextLine();								
								users.get(i).setNome(newName);
								
								if(users.get(i).getNome() == newName) {
									System.out.println("Nome alterado com sucesso!");
									System.out.println("---------------");
									System.out.println(users.get(i));
								}
								
								i = users.size() - 1;
							}							
						}
						System.out.println("\n");
						break;
					case 2:
						//ALTERAR usuário
						System.out.println("- Alterar usuário -");
						System.out.println("Usuário atual: " + userSelected.getUsuario());
						System.out.print("- Novo nome de usuário: ");
						for(int i = 0; i < users.size(); i++) {
							if(users.get(i).getId() == userSelected.getId()) {
								scan = new Scanner(System.in);
								
								String newUsername = scan.nextLine();								
								users.get(i).setUsuario(newUsername);
								
								if(users.get(i).getUsuario() == newUsername) {
									System.out.println("Usuário alterado com sucesso!");
									System.out.println("---------------");
									System.out.println(users.get(i));
								}
								
								i = users.size() - 1;
							}							
						}
						System.out.println("\n");
						break;
					default:
						System.out.println("Opção não encontrada! Tente novamente!");
				}
			}while(Integer.parseInt(option) < 0 || Integer.parseInt(option) > 6 || Integer.parseInt(option) != 0);
			
		}else {//SÓ É POSSIVEL POIS TEMOS APENAS DUAS SUBCLASSES!
			System.out.println("  ↓  Escolha uma opção\n" +
							   " [1] - Alterar nome\n" +
							   " [2] - Alterar usuário\n" + 
							   " [3] - Alterar senha\n" +
							   " [4] - Alterar email\n" +
							   " [5] - Alterar cidade\n" + 
							   " [6] - Alterar condição de aluno\n" +  
							   " [6] - Alterar Ra\n" +  
							   " [0] - Sair\n");
		}
				
	}
	
	
	static boolean usuarioTestCadastroCpf(String cpf) {
		
		if(!users.isEmpty()) {
			for (int i = 0; i < users.size(); i++) {				
				return ( cpf.equals(users.get(i).getUsuario()) ? true : false );
			}
		}
		
		return false;	
	}
	
	static boolean usuarioTestCadastroUsuario(String usuarioNome) {
		
		if(!users.isEmpty()) {
			for (int i = 0; i < users.size(); i++) {				
				return ( usuarioNome.equals(users.get(i).getUsuario()) ? true : false );
			}
		}
		
		return false;	
	}	
	
	// ============== CADASTRO ==============
	static void cadastroAdministrador() {
		
		Usuario adm = new Administrador();
		boolean confirmCadastro = false;
		
		do {			
			scan = new Scanner(System.in);
			
			adm.setId( users.size() + 1);
			
			System.out.println(" -- CADASTRO DE VENDEDOR --\n");
			
			System.out.print("Nome: ");
			adm.setNome( scan.nextLine() );
			
			do {
				try {					
					System.out.print("Sálario: ");
					((Administrador)adm).setSalario( scan.nextDouble() );
				}catch(Exception e) {scan = new Scanner(System.in); System.out.println("Digite um número valido!");}
			}while(((Administrador)adm).getSalario() == 0);
			boolean confirm = true;
			
			do {
				scan = new Scanner(System.in);
				
				System.out.print("Usuário: ");
				adm.setUsuario( scan.nextLine() );
				
				if(usuarioTestCadastroUsuario(adm.getUsuario())) {//TESTA SE O USUARIO JA EXISTE
					System.out.println("-Usuário já cadastrado!\n");	
				
					System.out.print("Voltar [S/ N]: ");
					confirm = scan.next().equalsIgnoreCase("s");
				}
			
			}while(usuarioTestCadastroUsuario(adm.getUsuario()) && !confirm);
			
			if(!usuarioTestCadastroUsuario(adm.getUsuario())) {
				System.out.print("Senha: ");
				adm.setSenha( scan.nextLine() );
				
				System.out.print("Email: ");
				adm.setEmail( scan.nextLine() );
				
				//TESTE CPF
				do {
					System.out.print("Cpf: ");
					adm.setCpf( scan.nextLine() );
					
					if(usuarioTestCadastroCpf(adm.getCpf()))
						System.out.println("Cpf já cadastrado!");						
				}while( usuarioTestCadastroCpf(adm.getCpf()) );
				
				System.out.print("Cidade: ");
				adm.setCidade( scan.nextLine() );
				
				//LOG
				System.out.println("Resumo do cadastro\n-------------------\n");
				System.out.println(adm);
				System.out.println("\n-------------------\n");
				System.out.print(" CONFIRMAR CADASTRO [S/ N]: ");
				//FIM LOG
				
				confirmCadastro = scan.next().equalsIgnoreCase("s");
			}else
				adm = null;
			
		}while(!confirmCadastro);
		
		if(adm != null) {
			users.add(adm);
			
			System.out.println("Usuário adicionado com sucesso!\n\n");
		}else
			System.out.println("\n\n");
	}
	
	static void cadastroVendedor() {
		
		Usuario vendedor = new Vendedor();
		boolean confirmCadastro = false;
		
		do {			
			scan = new Scanner(System.in);
			
			vendedor.setId( users.size() + 1);
			
			System.out.println(" -- CADASTRO DE VENDEDOR --\n");
			
			System.out.print("Nome: ");
			vendedor.setNome( scan.nextLine() );
			
			System.out.print("Aluno [S/ N]: ");
			((Vendedor)vendedor).setAluno( scan.next().equalsIgnoreCase("s") );
			
			scan = new Scanner(System.in);
			
			System.out.print("Ra: ");
			((Vendedor)vendedor).setRa( scan.nextLine() );

			boolean confirm = true;
			
			do {
				scan = new Scanner(System.in);
				
				System.out.print("Usuário: ");
				vendedor.setUsuario( scan.nextLine() );
				
				if(usuarioTestCadastroUsuario(vendedor.getUsuario())) {//TESTA SE O USUARIO JA EXISTE
					System.out.println("-Usuário já cadastrado!\n");	
				
					System.out.print("Voltar [S/ N]: ");
					confirm = scan.next().equalsIgnoreCase("s");
				}
				
			}while(usuarioTestCadastroUsuario(vendedor.getUsuario()) && !confirm);
			
			if(!usuarioTestCadastroUsuario(vendedor.getUsuario())) {
				System.out.print("Senha: ");
				vendedor.setSenha( scan.nextLine() );
				
				System.out.print("Email: ");
				vendedor.setEmail( scan.nextLine() );
				
				
				//TESTE CPF
				do {
					System.out.print("Cpf: ");
					vendedor.setCpf( scan.nextLine() );
					
					if(usuarioTestCadastroCpf(vendedor.getCpf()))
						System.out.println("Cpf já cadastrado!");						
				}while( usuarioTestCadastroCpf(vendedor.getCpf()) );
				
				System.out.print("Cidade: ");
				vendedor.setCidade( scan.nextLine() );
				
				//LOG
				System.out.println("Resumo do cadastro\n-------------------\n");
				System.out.println(vendedor);
				System.out.println("\n-------------------\n");
				System.out.print(" CONFIRMAR CADASTRO [S/ N]: ");
				//FIM LOG
				
				confirmCadastro = scan.next().equalsIgnoreCase("s");
			}else
				vendedor = null;
			
		}while(!confirmCadastro);
		
		if(vendedor != null) {
			users.add(vendedor);
			
			System.out.println("Usuário adicionado com sucesso!\n\n");
		}else
			System.out.println("\n\n");
	}
	
	static void menuCadastroUsuario() {
		
		System.out.println("  ↓  Escolha uma opção\n" +
						   " [1] - Cadastar vendedor\n" +
						   " [2] - Cadastrar administrador\n" + 
						   " [0] - Sair\n");		
	}
	// ============== FIM CADASTRO ==============
	
	static void mainMenu() {
		
		System.out.println("Número de usuários [" + users.size() + "]");
		System.out.println("  ↓  Escolha uma opção\n" +
						   " [1] - Cadastar usuários x\n" +
						   " [2] - Editar usuário\n" + 
						   " [3] - Remover usuario\n" +
						   " [4] - Listar usuários\n" +
						   " [5] - Listar de usuarios por tipo\n" +  
						   " [0] - Sair\n");
	}

}
