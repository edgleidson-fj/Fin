package Domain;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "tbluser")
@NamedQueries({ 
	@NamedQuery(name = "Usuario.listar", query = "SELECT usuario FROM Usuario usuario"),
	@NamedQuery(name = "Usuario.buscarPorCodigo", query = "SELECT usuario FROM Usuario usuario WHERE usuario.codigo = :codigo"),
	@NamedQuery(name = "Usuario.autenticar", query = "SELECT usuario FROM Usuario usuario WHERE usuario.cpf = :cpf AND usuario.senha = :senha")
	})
public class Usuario {

	@Id
	@Column(name = "usuid")
	private int codigo;

	@NotNull(message="Favor preencher campo NOME")
	@Column(name = "usunome", length = 50, nullable = false)
	private String nome;

	@Column(name = "ususenha", length = 32)
	private String senha;

	@NotNull(message="Favor preencher campo CPF")
	@Column(name = "usucpf", length = 45, nullable = false, unique = true)
	private	String cpf;
	// ----------------------------------------------------------------------------------------------------------

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	// -----------------------------------------------------------------------------------------------------------
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + codigo;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (codigo != other.codigo)
			return false;
		return true;
	}	
	//-------------------
}
