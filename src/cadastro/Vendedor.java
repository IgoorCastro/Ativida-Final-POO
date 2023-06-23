package cadastro;

import java.io.Serializable;

public class Vendedor extends Usuario implements Serializable{
	
	private boolean aluno;
	private String ra;
	
	public Vendedor() {
		
		aluno = true;
		ra = "Ra não cadastrado!";
	}

	public boolean isAluno() {
		return aluno;
	}

	public void setAluno(boolean aluno) {
		this.aluno = aluno;
	}

	public String getRa() {
		return ra;
	}

	public void setRa(String ra) {
		this.ra = ra;
	}

	@Override
	public String toString() {
		
		return 	"Id: " + getId() + 
				"\nNome: " + getNome() +
				"\nRa: " + ra + 
				"\nAluno: " + ((isAluno()) ? "Sim" : "Não") + 
				"\nUsuario: " + getUsuario() +
				"\nSenha: " + getSenha() + 
				"\nEmail: " + getEmail() + 
				"\nCpf: " + getCpf() +
				"\nCidade : " + getCidade();
	}
}
