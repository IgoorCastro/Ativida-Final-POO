package cadastro;

public class Usuario {
	
	private int id;
	private String nome;
	private String usuario;
	private String senha;
	private String email;
	private String cpf;
	private String cidade;	

	public Usuario() {
		
		id = 0;
		nome = "Sem nome cadastrado!";
		usuario = "";
		senha = "";
		email = "Sem email cadastrado!";
		cpf = "000-000-000-00";
		cidade = "Cidade não cadastrada!";
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		if(id > 0)
			this.id = id;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		if(!nome.isEmpty())
			this.nome = nome;
	}


	public String getUsuario() {
		return usuario;
	}


	public void setUsuario(String usuario) {
		if(!usuario.isEmpty())
			this.usuario = usuario;
	}


	public String getSenha() {
		return senha;
	}


	public void setSenha(String senha) {
		if(!senha.isEmpty() && senha.length() > 4)//SENHA DEVE TER MAIS QUE 4 CARACTERES
			this.senha = senha;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		if(!email.isEmpty())
			this.email = email;
	}


	public String getCpf() {
		return cpf;
	}


	public void setCpf(String cpf) {
		if(!cpf.isEmpty())
			this.cpf = cpf;
	}


	public String getCidade() {
		return cidade;
	}


	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	
	@Override
	public String toString() {
		
		return 	"Id: " + id + 
				"Nome: " + nome + 
				"Usuario: " + usuario +
				"Senha: " + senha + 
				"Email: " + email + 
				"Cpf: " + cpf +
				"Cidade : " + cidade;
	}
	
}
