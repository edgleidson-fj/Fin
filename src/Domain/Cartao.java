package Domain;

//import java.util.Arrays;
//import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

//import antlr.collections.List;


@Entity 
@Table(name = "tblcartao") 
@NamedQueries({ 
	@NamedQuery(name = "Cartao.listar", query = " SELECT cartao FROM Cartao cartao "),
	@NamedQuery(name = "Cartao.buscarPorCodigo", query = " SELECT cartao FROM Cartao cartao WHERE cartao.codigo = :codigo")
})
public class Cartao {

	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO) 
	@Column(name = "cartaoid") 
	private int codigo;

	@NotNull(message="Favor preencher campo Bandeira")
	@Column(name = "cartaoband", length = 20, nullable = false)  
	private String bandeira;

	//------------------------------------------------------------------------------------------------------------
	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getBandeira() {
		return bandeira;
	}

	public void setBandeira(String bandeira) {
		this.bandeira = bandeira;
	}
	//------------------------------------------------------------------------------------------------------------

	/*/Mostra como o SQL deve exibir o resultado da pesquisa no console.
	@Override
	public String toString() {
		return "Categoria [codigo=" + codigo + ", descricao=" + descricao
				+ "]";
	}*/
	@Override
	public String toString() {
		return bandeira;
	}
	//------------------------------------------------------------------------------------------------------------

	//HashCode Equal
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
		Cartao other = (Cartao) obj;
		if (codigo != other.codigo)
			return false;
		return true;
	}
	
	//-------------------
	
	
	
	
}