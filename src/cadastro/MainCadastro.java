package cadastro;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainCadastro {

	static List<Usuario> users = new ArrayList<>();
	static Scanner scan;

	public static void main(String[] args) {

		users = upDataUsers();//SUBINDO OS DADOS
		scan = new Scanner(System.in);

		boolean quitOption = false;
		String preEscolha = "";

		do {
			// MENU INICIAL
			System.out.println("\t= FATEFOOD =\n == CADASTRO DE USUARIOS == \n\n");

			mainMenu();
			System.out.print("→ ");
				try {
					preEscolha = scan.next();
				// FIM DO MENU

				switch (Integer.parseInt(preEscolha)) {
				case 0:
					quitOption = true;
					break;
				case 1:
					do {
						menuCadastroUsuario();
						try {
							System.out.print("→ ");
							preEscolha = scan.next();

							switch (Integer.parseInt(preEscolha)) {
							case 0:
								break;
							case 1:
								cadastroVendedor();
								break;
							case 2:
								cadastroAdministrador();
								break;
							default:
								System.err.println("erro: Digite uma opção valida!\n");
								break;
							}
						}catch(Exception e) {preEscolha = "-1";/*MANTER O LAÇO*/ System.err.println("erro: Digite apenas valores validos!\n");}
					}while(Integer.parseInt(preEscolha) < 0 || Integer.parseInt(preEscolha) > 2);
					break;
					
				case 2:
					
					do {
						scan = new Scanner(System.in);
						menuEditUser();
						try {
							System.out.print("→ ");
							preEscolha = scan.next();

							switch (Integer.parseInt(preEscolha)) {
							case 0:
								break;
							case 1:
								editUserCharSearch();
								break;
							case 2:
								System.out.println("\nLista completa de usuários");
								editUserFromList();
								break;
							default:
								System.err.println("erro: Digite uma opção valida!\n");
								break;
							}
						}catch(Exception e) {preEscolha = "-1";/*MANTER O LAÇO*/ System.err.println("erro: Digite apenas valores validos!\n");}
					}while(Integer.parseInt(preEscolha) < 0 || Integer.parseInt(preEscolha) > 2);
					break;
				case 3:
					removeUser();
					break;
				case 4:
					writeList();
					break;
				case 5:
					listType();
					break;
				default:
					System.err.println("erro: Digite uma opção valida!\n");
					break;
				}

			} catch (Exception e) { System.err.println("erro: Digite apenas valores validos!\n");}

		} while (quitOption != true);

		System.out.print("\nFim!");
		scan.close();

	}
	
	//LISTA POR TIPO
	static void listType() {
		
		String preEscolha;

		do {
			System.out.println("  ↓  Escolha uma opção\n" + " [1] - Lista vendedores\n" + " [2] - Lista administrador\n"
					+ " [0] - Sair\n");

			scan = new Scanner(System.in);
			try {
				System.out.print("→ ");
				preEscolha = scan.next();
			
				System.out.println("\n--------------------");
				switch (Integer.parseInt(preEscolha)) {
				case 0:
					break;
				case 1:
					System.out.println("Lista de vendedores\n");
					for (int i = 0; i < users.size(); i++) {
						if (users.get(i) instanceof Vendedor) {
							System.out.println(users.get(i));
							System.out.println("--------------------");
						}
					}
					break;
				case 2:
					System.out.println("Lista de administradores\n");
					for (int i = 0; i < users.size(); i++) {
						if (users.get(i) instanceof Administrador) {
							System.out.println(users.get(i));
							System.out.println("--------------------");
						}
					}
					break;
				default:
					System.err.println("erro: Digite uma opção valida!\n");
					break;
				}
			} catch (Exception e) {
				preEscolha = "-1";
				/* MANTER O LAÇO */ System.err.println("erro: Digite apenas valores validos!\n");
			}
			System.out.println("");
		} while (Integer.parseInt(preEscolha) != 0);
	}

	//REMOVER USUARIO
	static void removeUser() {
		
		Usuario userSelected = null;
		boolean confirRemove = false;
		
		do {
			scan = new Scanner(System.in);
			System.out.println("");
			userSelected = idUserSelection(users);
			
			System.out.println("Usuario selecionado");
			System.out.println("----------------------");
			System.out.println(userSelected);
			
			System.out.print("Confirmar remoção [S/ N]: ");
			confirRemove = scan.next().equalsIgnoreCase("s");			
		}while(!confirRemove);
		
		for(int i = 0; i < users.size(); i++){
			if(users.get(i).getId() == userSelected.getId()) 
				users.remove(i);
		}
		
		saveDataUsers(users);
	}
	
	//ESCREVE LISTA
	static void writeList() {
		// LISTAR USUÁRIOS 
		
		System.out.println("-Lista de úsuarios-");
		if(!users.isEmpty()) {
			System.out.println("--------------------");
			for(int i = 0; i < users.size(); i++) {
				System.out.println(users.get(i));
				System.out.println("--------------------");
			}
		}else
			System.out.println("Lista vazia!\n");
		System.out.println();
	}

	static void writeList(List<Usuario> list) {
		
		System.out.println("\n----------------------");
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
			System.out.println("----------------------");
		}
	}
	
	//UP DATES
	static List<Usuario> upDataUsers() {

		// Carregando o objeto do arquivo
		List<Usuario> dadosCarregados = null;

		try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("dataUsers.ser"))) {
			dadosCarregados = (List) inputStream.readObject();
			//System.out.println("Dados carregados\n " + dadosCarregados);
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		return dadosCarregados;
	}
	
	//SAVE DATES
	static void saveDataUsers(List<Usuario> element) {

		if (!element.isEmpty()) {

			// Salvando o objeto em um arquivo
			try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("dataUsers.ser"))) {
				outputStream.writeObject(element);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	//PROCURA POR USUARIOS
	static List<Usuario> findUsers(char stLetter) {
		//METODO QUE RETORNA UMA LISTA CONTENDO OS NOMES COM INCIAIS DE stLetter
		List<Usuario> searchList = new ArrayList<>();

		for (int i = 0; i < users.size(); i++) {

			String lowerCase1 = String.valueOf(users.get(i).getNome().charAt(0));
			String stLetterLC = String.valueOf(stLetter);

			if (lowerCase1.equalsIgnoreCase(stLetterLC))
				searchList.add(users.get(i));
		}

		// LOG
		// writeList(searchList);
		System.out.println("Resultado da busca: " + searchList.size() + " resultado(s).");

		return searchList;
	}
	
	// ============= EDITAR USUÁRIO ===================
	static void editUser(Usuario userSelected) {
		// MENU PARA ALTERAÇÂO
		
		String option = "";
		System.out.println("----------------------");
		System.out.println(userSelected);
		System.out.println("----------------------");
		if (userSelected instanceof Administrador) {
			do {
				
				scan = new Scanner(System.in);
				
				System.out.println("\n  ↓  Escolha uma opção\n" + " [1] - Alterar nome\n" + " [2] - Alterar usuário\n"
						+ " [3] - Alterar senha\n" + " [4] - Alterar email\n" + " [5] - Alterar cidade\n"
						+ " [6] - Alterar salário\n" + " [7] - Alterar nivel adm\n" + " [0] - Sair\n");
				System.out.print("→ ");
					try {
						option = scan.next();	
						
						boolean confirmAlter;

						switch (Integer.parseInt(option)) {
						
						case 0:
							break;
						case 1:
							// ALTERAR NOME
							
							confirmAlter = false;
							String newName;
							
							System.out.println("\n- Alterar nome -");
							do {								
								System.out.println("\nNome atual: " + userSelected.getNome());
								System.out.print("- Novo nome: ");
								scan = new Scanner(System.in);
								newName = scan.nextLine();
								
								System.out.print("Confirmar alteração [S/ N]: ");
								confirmAlter = scan.next().equalsIgnoreCase("s");
							}while(!confirmAlter);
							
							userSelected.setNome(newName);


							System.out.println("Nome alterado com sucesso!\n");
							System.out.println("- Resumo\n---------------");
							System.out.println(userSelected);
							break;
						case 2:
							// ALTERAR USUÁRIO
							
							confirmAlter = false;
							String newUsername;
							
							System.out.println("- Alterar usuário -");
							do {								
								System.out.println("\nUsuário atual: " + userSelected.getUsuario());
								System.out.print("- Novo nome de usuário: ");
								scan = new Scanner(System.in);
								newUsername = scan.nextLine();

								System.out.print("Confirmar alteração [S/ N]: ");
								confirmAlter = scan.next().equalsIgnoreCase("s");
							}while(!confirmAlter);
							
							userSelected.setUsuario(newUsername);
							
							System.out.println("Usuário alterado com sucesso!");
							System.out.println("---------------");
							System.out.println(userSelected);
							break;
						case 3:
							// ALTERAR SENHA
							
							confirmAlter = false;
							String newSenha;
							boolean pswConfirm;
							
							System.out.println("- Alterar senha -");
							do {								
								System.out.println("\nUsuário atual: " + userSelected.getUsuario());
								System.out.print("- Nova senha: ");
								scan = new Scanner(System.in);
								newSenha = scan.nextLine();
								
								System.out.print("- Repita a senha: ");
								pswConfirm = scan.nextLine().equals(newSenha);
								
								if(!pswConfirm)
									System.err.println("Senhas diferente! Tente novamente!");
								else {
									System.out.print("Confirmar alteração [S/ N]: ");
									confirmAlter = scan.next().equalsIgnoreCase("s");
								}
							}while(!confirmAlter || !pswConfirm);
							
							userSelected.setSenha(newSenha);
							
							System.out.println("Senha alterada com sucesso!");
							System.out.println("---------------");
							System.out.println(userSelected);
							break;
						case 4:
							// ALTERAR EMAIl
							
							confirmAlter = false;
							String newEmail;
							
							System.out.println("- Alterar email -");
							do {								
								System.out.println("\nUsuário atual: " + userSelected.getUsuario() + "\nEmail atual: " + userSelected.getEmail());
								System.out.print("- Novo email: ");
								scan = new Scanner(System.in);
								newEmail = scan.nextLine();

								System.out.print("Confirmar alteração [S/ N]: ");
								confirmAlter = scan.next().equalsIgnoreCase("s");
							}while(!confirmAlter);
							
							userSelected.setEmail(newEmail);
							
							System.out.println("Email alterado com sucesso!");
							System.out.println("---------------");
							System.out.println(userSelected);
							break;
						case 5:
							// ALTERAR CIDADE
							
							confirmAlter = false;
							String newCidade;
							
							System.out.println("- Alterar email -");
							do {								
								System.out.println("\nUsuário atual: " + userSelected.getUsuario() + "\nCidade atual: " + userSelected.getCidade());
								System.out.print("- Nova cidade: ");
								scan = new Scanner(System.in);
								newCidade = scan.nextLine();

								System.out.print("Confirmar alteração [S/ N]: ");
								confirmAlter = scan.next().equalsIgnoreCase("s");
							}while(!confirmAlter);
							
							userSelected.setCidade(newCidade);
							
							System.out.println("Cidade alterada com sucesso!");
							System.out.println("---------------");
							System.out.println(userSelected);
							break;
						case 6:
							// ALTERAR SALÁRIO
							
							confirmAlter = false;
							double newSalario = 0;
							
							System.out.println("- Alterar email -");
							do {								
								System.out.println("\nUsuário atual: " + userSelected.getUsuario() + "\nSalário atual: " + ((Administrador)userSelected).getSalario());
								System.out.print("- Nova salário: ");
								try {
									scan = new Scanner(System.in);
									newSalario = scan.nextDouble();

									System.out.print("Confirmar alteração [S/ N]: ");
									confirmAlter = scan.next().equalsIgnoreCase("s");
								}catch(Exception e) {System.err.println("erro: Digite um número valido!");}
							}while(!confirmAlter);
							
							((Administrador) userSelected).setSalario(newSalario);
							
							System.out.println("Salário alterado com sucesso!");
							System.out.println("---------------");
							System.out.println(userSelected);
							break;
						case 7:
							// ALTERAR NIVEL ADM
							
							confirmAlter = false;
							short newNivel = 3;
							
							System.out.println("- Alterar nivel de administração -");
							do {								
								System.out.println("\nUsuário atual: " + userSelected.getUsuario() + "\nNivel atual: " + ((Administrador)userSelected).getAdmLvl());
								System.out.print("- Novo nivel [1, 2 e 3]: ");
								try {
									scan = new Scanner(System.in);
									newNivel = scan.nextShort();

									System.out.print("Confirmar alteração [S/ N]: ");
									confirmAlter = scan.next().equalsIgnoreCase("s");
								}catch(Exception e) {System.err.println("erro: Digite um número valido!");}
							}while(!confirmAlter || newNivel < 1 || newNivel > 3);
							
							((Administrador) userSelected).setAdmLvl(newNivel);
							
							System.out.println("Nivel alterado com sucesso!");
							System.out.println("---------------");
							System.out.println(userSelected);
							break;
						default:
							System.out.println("Opção não encontrada! Tente novamente!");
							break;
						}
					} catch (Exception e) {
						System.out.println("Digite um número valido!\ne: " + e + "\n");
					}					
			} while (Integer.parseInt(option) != 0);

		} else {// SÓ É POSSIVEL POIS TEMOS APENAS DUAS SUBCLASSES!
			System.out.println("\n  ↓  Escolha uma opção\n" + " [1] - Alterar nome\n" + " [2] - Alterar usuário\n"
					+ " [3] - Alterar senha\n" + " [4] - Alterar email\n" + " [5] - Alterar cidade\n"
					+ " [6] - Alterar condição de aluno\n" + " [7] - Alterar Ra\n" + " [0] - Sair\n");			
			System.out.print("→ ");
			try {
				option = scan.next();

				boolean confirmAlter;

				switch (Integer.parseInt(option)) {

				case 0:
					break;
				case 1:
					// ALTERAR NOME

					confirmAlter = false;
					String newName;

					System.out.println("\n- Alterar nome -");
					do {
						System.out.println("\nNome atual: " + userSelected.getNome());
						System.out.print("- Novo nome: ");
						scan = new Scanner(System.in);
						newName = scan.nextLine();

						System.out.print("Confirmar alteração [S/ N]: ");
						confirmAlter = scan.next().equalsIgnoreCase("s");
					} while (!confirmAlter);

					userSelected.setNome(newName);

					System.out.println("Nome alterado com sucesso!\n");
					System.out.println("- Resumo\n---------------");
					System.out.println(userSelected);
					break;
				case 2:
					// ALTERAR USUÁRIO

					confirmAlter = false;
					String newUsername;

					System.out.println("- Alterar usuário -");
					do {
						System.out.println("\nUsuário atual: " + userSelected.getUsuario());
						System.out.print("- Novo nome de usuário: ");
						scan = new Scanner(System.in);
						newUsername = scan.nextLine();

						System.out.print("Confirmar alteração [S/ N]: ");
						confirmAlter = scan.next().equalsIgnoreCase("s");
					} while (!confirmAlter);

					userSelected.setUsuario(newUsername);

					System.out.println("Usuário alterado com sucesso!");
					System.out.println("---------------");
					System.out.println(userSelected);
					break;
				case 3:
					// ALTERAR SENHA

					confirmAlter = false;
					String newSenha;
					boolean pswConfirm;

					System.out.println("- Alterar senha -");
					do {
						System.out.println("\nUsuário atual: " + userSelected.getUsuario());
						System.out.print("- Nova senha: ");
						scan = new Scanner(System.in);
						newSenha = scan.nextLine();

						System.out.print("- Repita a senha: ");
						pswConfirm = scan.nextLine().equals(newSenha);

						if (!pswConfirm)
							System.err.println("Senhas diferente! Tente novamente!");
						else {
							System.out.print("Confirmar alteração [S/ N]: ");
							confirmAlter = scan.next().equalsIgnoreCase("s");
						}
					} while (!confirmAlter || !pswConfirm);

					userSelected.setSenha(newSenha);

					System.out.println("Senha alterada com sucesso!");
					System.out.println("---------------");
					System.out.println(userSelected);
					break;
				case 4:
					// ALTERAR EMAIl

					confirmAlter = false;
					String newEmail;

					System.out.println("- Alterar email -");
					do {
						System.out.println("\nUsuário atual: " + userSelected.getUsuario() + "\nEmail atual: "
								+ userSelected.getEmail());
						System.out.print("- Novo email: ");
						scan = new Scanner(System.in);
						newEmail = scan.nextLine();

						System.out.print("Confirmar alteração [S/ N]: ");
						confirmAlter = scan.next().equalsIgnoreCase("s");
					} while (!confirmAlter);

					userSelected.setEmail(newEmail);

					System.out.println("Email alterado com sucesso!");
					System.out.println("---------------");
					System.out.println(userSelected);
					break;
				case 5:
					// ALTERAR CIDADE

					confirmAlter = false;
					String newCidade;

					System.out.println("- Alterar email -");
					do {
						System.out.println("\nUsuário atual: " + userSelected.getUsuario() + "\nCidade atual: "
								+ userSelected.getCidade());
						System.out.print("- Nova cidade: ");
						scan = new Scanner(System.in);
						newCidade = scan.nextLine();

						System.out.print("Confirmar alteração [S/ N]: ");
						confirmAlter = scan.next().equalsIgnoreCase("s");
					} while (!confirmAlter);

					userSelected.setCidade(newCidade);

					System.out.println("Cidade alterada com sucesso!");
					System.out.println("---------------");
					System.out.println(userSelected);
					break;
				case 6:
					// ALTERAR CONDIÇÃO DE ALUNO

					confirmAlter = false;
					boolean newCondicao = false;

					System.out.println("- Alterar condição de aluno -");
					do {
						System.out.println("\nUsuário atual: " + userSelected.getUsuario() + "\nÉ aluno: "
								+ (((Vendedor) userSelected).isAluno() ? "Sim" : "Não"));
						System.out.print("- Aluno [S/ N]: ");
						try {
							scan = new Scanner(System.in);
							newCondicao = scan.next().equalsIgnoreCase("s");

							System.out.print("Confirmar alteração [S/ N]: ");
							confirmAlter = scan.next().equalsIgnoreCase("s");
						} catch (Exception e) {
							System.err.println("erro: Digite um número valido!");
						}
					} while (!confirmAlter);

					((Vendedor) userSelected).setAluno(newCondicao);

					System.out.println("Condição alterada com sucesso!");
					System.out.println("---------------");
					System.out.println(userSelected);
					break;
				case 7:
					// ALTERAR RA

					confirmAlter = false;
					String newRa = "";

					System.out.println("- Alterar RA -");
					do {
						System.out.println("\nUsuário atual: " + userSelected.getUsuario() + "\nRa atual: "
								+ ((Vendedor) userSelected).getRa());
						System.out.print("- Novo nivel [1, 2 e 3]: ");
						try {
							scan = new Scanner(System.in);
							newRa = scan.next();

							System.out.print("Confirmar alteração [S/ N]: ");
							confirmAlter = scan.next().equalsIgnoreCase("s");
						} catch (Exception e) {
							System.err.println("erro: Digite um número valido!");
						}
					} while (!confirmAlter);

					((Vendedor) userSelected).setRa(newRa);

					System.out.println("RA alterado com sucesso!");
					System.out.println("---------------");
					System.out.println(userSelected);
					break;
				default:
					System.err.println("erro: Opção não encontrada! Tente novamente!");
					break;
				}
			} catch (Exception e) {
				System.err.println("erro: Digite um número valido!\ne: " + e + "\n");
			}
		}
		while (Integer.parseInt(option) != 0)
			;

		System.out.println("");
	}
	
	static void editUserFromList() {		
		
		editUser(idUserSelection(users));	
		saveDataUsers(users);//SAVE DATES
	}
	
	static void editUserCharSearch() {
		
		//PROCURA UM USUÁRIO DE ACORDO COM A PRIMEIRA LETRA DO NOME E PERMITE A ALTERAÇÃO DOS SEUS DADOS
			//ATRAVEZ DA SELEÇÃO DE UMA ID
	
		char stLetter = ' ';
		List<Usuario> listResult = null;

		do {//RECEBE A ENTRADA DO USUARIO PARA PEGAR A PRIMEIRA LETRA DO NOME
			scan = new Scanner(System.in);
			
			System.out.print("Primeira letra do nome: ");
			stLetter = scan.next().charAt(0);
			if (!Character.isLetter(stLetter)) {
				System.err.println("erro: Não use números!\n");
			}else{
				// BUSCA POR USUÁRIO - RETORNA UMA LISTA COM OS USUARIOS ENCONTRADOS
				listResult = findUsers(stLetter);
				if (listResult.isEmpty())
					System.err.println("erro: Nenhum nome econtrado! Tente novamente!\n");
			}
						
		} while (!Character.isLetter(stLetter) || listResult.isEmpty());
		
		// LOG
		// System.out.println("stLetter: " + stLetter);

		
		// ==============
		
		
		editUser(idUserSelection(listResult));//MOSTRAR LISTA RESULTANTE 
		saveDataUsers(users);//SAVE DATES
	}
	
	static Usuario idUserSelection(List<Usuario> listResult) {		
		//SELECIONA UMA ID E RETORNA O USUARIO
		
		short idSearch = 0;
		boolean kickLoop = false;
		Usuario userSelected = null;
		
		// SELEÇÃO DE ID
		do {
			scan = new Scanner(System.in);

			writeList(listResult);

			try {
				System.out.print("Digite o ID: ");
				idSearch = scan.nextShort();

				if (idSearch > 0) {
					for (int i = 0; i < listResult.size(); i++) {
						if (listResult.get(i).getId() == idSearch) {
							System.out.println("\n------------------------");
							System.out.println("- Usuário selecionado");
							System.out.println(listResult.get(i));

							System.out.println("------------------------");
							System.out.print("Confirmar seleção [S/ N]: ");
							kickLoop = scan.next().equalsIgnoreCase("s");
							System.out.println();
							if (kickLoop) {
								//userSelected = users.get(i);
								userSelected = listResult.get(i);
							}
							
							i = users.size() - 1;// Quebra o laço
							
						} else if (i == listResult.size() - 1)
							System.err.println("erro: ID não encontrado! Tente novamente!");
					}

				} else
					System.err.println("Número de ID deve ser maior que 0!");

			} catch (Exception e) {
				System.err.println("erro: Digite uma ID valida!");
			}

		} while (idSearch < 0 || !kickLoop);
		
		return userSelected;
		
	}
	// ============== FIM EDITAR USUÁRIO =================
	
	// ============== TESTES DE CADASTRO =================
	static boolean usuarioTestCadastroCpf(String cpf) {
		//TESTE DE VERIFICAÇÃO DE CPF REPETIDO
		
		if (!users.isEmpty()) {
			for (int i = 0; i < users.size(); i++) {
				return (cpf.equals(users.get(i).getUsuario()) ? true : false);
			}
		}

		return false;
	}

	static boolean usuarioTestCadastroUsuario(String usuarioNome) {
		//TESTE DE VERIFICAÇÃO DE USUÁRIO REPETIDO
		
		if (!users.isEmpty()) {
			for (int i = 0; i < users.size(); i++) {
				return (usuarioNome.equals(users.get(i).getUsuario()) ? true : false);
			}
		}

		return false;
	}
	// ============== FIM TESTES DE CADASTRO ==================
	
	// ============== 		CADASTRO 		 ==================
	static void cadastroAdministrador() {
		//CADASTRO ADMINISTRADOR
		
		Usuario adm = new Administrador();
		boolean confirmCadastro = false;

		do {
			scan = new Scanner(System.in);

			adm.setId(users.size() + 1);

			System.out.println(" -- CADASTRO DE VENDEDOR --\n");

			System.out.print("Nome: ");
			adm.setNome(scan.nextLine());

			do {
				try {
					System.out.print("Sálario: ");
					((Administrador) adm).setSalario(scan.nextDouble());
				} catch (Exception e) {
					scan = new Scanner(System.in);
					System.out.println("Digite um número valido!");
				}
			} while (((Administrador) adm).getSalario() == 0);
			boolean confirm = true;

			do {
				scan = new Scanner(System.in);

				System.out.print("Usuário: ");
				adm.setUsuario(scan.nextLine());

				if (usuarioTestCadastroUsuario(adm.getUsuario())) {// TESTA SE O USUARIO JA EXISTE
					System.out.println("-Usuário já cadastrado!\n");

					System.out.print("Voltar [S/ N]: ");
					confirm = scan.next().equalsIgnoreCase("s");
				}

			} while (usuarioTestCadastroUsuario(adm.getUsuario()) && !confirm);

			if (!usuarioTestCadastroUsuario(adm.getUsuario())) {
				System.out.print("Senha: ");
				adm.setSenha(scan.nextLine());

				System.out.print("Email: ");
				adm.setEmail(scan.nextLine());

				// TESTE CPF
				do {
					System.out.print("Cpf: ");
					adm.setCpf(scan.nextLine());

					if (usuarioTestCadastroCpf(adm.getCpf()))
						System.out.println("Cpf já cadastrado!");
				} while (usuarioTestCadastroCpf(adm.getCpf()));

				System.out.print("Cidade: ");
				adm.setCidade(scan.nextLine());

				// LOG
				System.out.println("Resumo do cadastro\n-------------------\n");
				System.out.println(adm);
				System.out.println("\n-------------------\n");
				System.out.print(" CONFIRMAR CADASTRO [S/ N]: ");
				// FIM LOG

				confirmCadastro = scan.next().equalsIgnoreCase("s");
			} else
				adm = null;

		} while (!confirmCadastro);

		if (adm != null) {
			users.add(adm);
			System.out.println("Usuário adicionado com sucesso!\n\n");
		} else
			System.out.println("\n\n");
		saveDataUsers(users);
	}

	static void cadastroVendedor() {
		//CADASTRO VENDENDOR
		
		Usuario vendedor = new Vendedor();
		boolean confirmCadastro = false;

		do {
			scan = new Scanner(System.in);

			vendedor.setId(users.size() + 1);

			System.out.println(" -- CADASTRO DE VENDEDOR --\n");

			System.out.print("Nome: ");
			vendedor.setNome(scan.nextLine());

			System.out.print("Aluno [S/ N]: ");
			((Vendedor) vendedor).setAluno(scan.next().equalsIgnoreCase("s"));

			scan = new Scanner(System.in);

			System.out.print("Ra: ");
			((Vendedor) vendedor).setRa(scan.nextLine());

			boolean confirm = true;

			do {
				scan = new Scanner(System.in);

				System.out.print("Usuário: ");
				vendedor.setUsuario(scan.nextLine());

				if (usuarioTestCadastroUsuario(vendedor.getUsuario())) {// TESTA SE O USUARIO JA EXISTE
					System.out.println("-Usuário já cadastrado!\n");

					System.out.print("Voltar [S/ N]: ");
					confirm = scan.next().equalsIgnoreCase("s");
				}

			} while (usuarioTestCadastroUsuario(vendedor.getUsuario()) && !confirm);

			if (!usuarioTestCadastroUsuario(vendedor.getUsuario())) {
				System.out.print("Senha: ");
				vendedor.setSenha(scan.nextLine());

				System.out.print("Email: ");
				vendedor.setEmail(scan.nextLine());

				// TESTE CPF
				do {
					System.out.print("Cpf: ");
					vendedor.setCpf(scan.nextLine());

					if (usuarioTestCadastroCpf(vendedor.getCpf()))
						System.out.println("Cpf já cadastrado!");
				} while (usuarioTestCadastroCpf(vendedor.getCpf()));

				System.out.print("Cidade: ");
				vendedor.setCidade(scan.nextLine());

				// LOG
				System.out.println("Resumo do cadastro\n-------------------\n");
				System.out.println(vendedor);
				System.out.println("\n-------------------\n");
				System.out.print(" CONFIRMAR CADASTRO [S/ N]: ");
				// FIM LOG

				confirmCadastro = scan.next().equalsIgnoreCase("s");
			} else
				vendedor = null;

		} while (!confirmCadastro);

		if (vendedor != null) {
			users.add(vendedor);
			
			System.out.println("Usuário adicionado com sucesso!\n\n");
		} else
			System.out.println("\n\n");
		saveDataUsers(users);
	}
	
	static void menuEditUser() {
		//MENU EDIÇÃO DE USUÁRIO
		
		System.out.println("  ↓  Escolha uma opção\n" + " [1] - Seleção pela primeira letra\n"
				+ " [2] - Listar usuários\n" + " [0] - Voltar\n");
	}

	static void menuCadastroUsuario() {
		//MENU CADASTRO DE USUÁRIO
		
		System.out.println("  ↓  Escolha uma opção\n" + " [1] - Cadastar vendedor\n"
				+ " [2] - Cadastrar administrador\n" + " [0] - Voltar\n");
	}
	// ============== FIM CADASTRO ==============

	static void mainMenu() {
		//MENU PRINCIPAL
		System.out.println("Número de usuários [" + users.size() + "]");
		System.out.println("  ↓  Escolha uma opção\n" + " [1] - Cadastar usuários x\n" + " [2] - Editar usuário\n"
				+ " [3] - Remover usuario\n" + " [4] - Listar usuários\n" + " [5] - Listar de usuarios por tipo\n"
				+ " [0] - Sair\n");
	}

}