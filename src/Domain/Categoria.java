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
@Table(name = "tblcat") 
@NamedQueries({ 
	@NamedQuery(name = "Categoria.listar", query = " SELECT categoria FROM Categoria categoria "),
	@NamedQuery(name = "Categoria.buscarPorCodigo", query = " SELECT categoria FROM Categoria categoria WHERE categoria.codigo = :codigo")
})
public class Categoria {

	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO) 
	@Column(name = "catid") 
	private int codigo;

	@NotNull(message="Favor preencher campo DESCRIÇÃO")
	@Column(name = "catdesc", length = 20, nullable = false)  
	private String descricao;

	//------------------------------------------------------------------------------------------------------------
	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
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
		return descricao;
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
		Categoria other = (Categoria) obj;
		if (codigo != other.codigo)
			return false;
		return true;
	}
	
	//-------------------
	
	
	
	
}