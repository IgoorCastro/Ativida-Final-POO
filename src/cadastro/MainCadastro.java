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
					}
					
				}catch(Exception e){/*System.err.println("\n\ne: " + e);*/}
				
			}while(quitOption != true);
			
			System.out.print("\nFim!");
			scan.close();	
		
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
			
			System.out.print("Sálario: ");
			((Administrador)adm).setSalario( scan.nextDouble() );
			
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
				System.out.println(" CONFIRMAR CADASTRO [S/ N]: ");
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
				System.out.println(" CONFIRMAR CADASTRO [S/ N]: ");
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
