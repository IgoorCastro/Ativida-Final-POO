package cadastro;

public class Administrador extends Usuario{
	
	private int admLvl;//ADM LVL 1: Tem permissão total no sistema - ADM LVL 2: Sem permissão de excluir vendedores e catalogos - ADM LVL 3: nivel 3: Permissão apenas para visualizar os dados dos usuários
	private double salario;
	
	public Administrador() {
		
		admLvl = 3;
		salario = 0.0;
	}

	public int getAdmLvl() {
		return admLvl;
	}

	public void setAdmLvl(int admLvl) {
		this.admLvl = admLvl;
	}

	public double getSalario() {
		return salario;
	}

	public void setSalario(double salario) {
		this.salario = salario;
	}
	
	@Override
	public String toString() {
		
		return 	"Id: " + getId() + 
				"\nAdm lvl: " + admLvl + 
				"\nNome: " + getNome() + 
				"\nUsuario: " + getUsuario() +
				"\nSenha: " + getSenha() + 
				"\nEmail: " + getEmail() + 
				"\nCpf: " + getCpf() +
				"\nCidade : " + getCidade() +
				"\nSalario: " + salario;
	}

}
