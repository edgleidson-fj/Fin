package Domain;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tbldesp")
@NamedQueries({
	@NamedQuery(name = "Despesa.listar",query = "SELECT despesa FROM Despesa despesa"),
	@NamedQuery(name = "Despesa.buscarPorCodigo",query = "SELECT despesa FROM Despesa despesa WHERE despesa.codigo = :codigo")
})
public class Despesa {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "despid")
	private Long codigo;
	
	@NotNull(message="Favor preencher campo PRODUTO/SERVIÇO")
	@Column(name = "despdesc" , nullable = false )
	private String descricao;
	
	@NotNull(message="Favor preencher campo VALOR")
	@Column(name = "despval" , precision = 7, scale = 2 , nullable = false)
	private Double valor;
	
	@NotNull(message="Favor preencher campo PAGO")
	@Column(name = "despag" , nullable = false)
	private String pago;
	
	@Column(name = "despformpag")
	private String formaPagamento;
	
	@Column(name = "despobs")
	private String obs;
		
	@Temporal(TemporalType.TIMESTAMP)//Tempo (H:MM:SS--DIA/MES/ANO )
	@Column(name = "despdatavenc",nullable = true)
	private Date dataVencimento;       // NÃO ESTOU USANDO 
	
	@Temporal(TemporalType.TIMESTAMP)//Tempo (H:MM:SS--DIA/MES/ANO )
	@Column(name = "despdatapag",nullable = true)
	private Date dataPagamento = new Date(); //Pegar data do dia
		
	@Column(name = "despcanc")
	private String cancelamento;
	
	@Column(name = "desparcela")
	private Long parcela = 1L;
	
	//verificar possibilidade Nullable=true
	@Column(name = "desptipo")
	private String tipo; // Normal, FIXO,  Parcelado, Débito automático 
			
	//CHAVE ESTRANGEIRA
		@NotNull(message="Favor preencher o campo CATEGORIA")
		@ManyToOne(fetch = FetchType.EAGER)//( * PROD x 1 FAB )--(Fetch = EAGER) Inicializar os FAB junto com o PROD. 
		@JoinColumn(name="tblcat_catid",referencedColumnName="catid",nullable = false)//@JoinColumn= Relacionamento de 2 Tabelas.
		private Categoria categoria; //Name = Chave Estrangeira --- ReferenceColumnName = Chave Primaria.		
		
		//@NotNull(message="Favor preencher o campo CARTÃO")
				@ManyToOne(fetch = FetchType.EAGER)//( * PROD x 1 FAB )--(Fetch = EAGER) Inicializar os FAB junto com o PROD. 
				@JoinColumn(name="tblcartao_cartaoid",referencedColumnName="cartaoid",nullable = true)//@JoinColumn= Relacionamento de 2 Tabelas.
				private Cartao cartao; //Name = Chave Estrangeira --- ReferenceColumnName = Chave Primaria.
		//----------------------------------------------------------------------------------------------------------
			
		public Long getCodigo() {
			return codigo;
		}
		public void setCodigo(Long codigo) {
			this.codigo = codigo;
		}
		public String getDescricao() {
			return descricao;
		}
		public void setDescricao(String descricao) {
			this.descricao = descricao;
		}
		public Double getValor() {
			return valor;
		}
		public void setValor(Double valor) {
			this.valor = valor;
		}
		public String getPago() {
			return pago;
		}
		public void setPago(String pago) {
			this.pago = pago;
		}
		public String getFormaPagamento() {
			return formaPagamento;
		}
		public void setFormaPagamento(String formaPagamento) {
			this.formaPagamento = formaPagamento;
		}
		public String getObs() {
			return obs;
		}
		public void setObs(String obs) {
			this.obs = obs;
		}
		public Date getDataVencimento() {
			return dataVencimento;
		}
		public void setDataVencimento(Date dataVencimento) {
			this.dataVencimento = dataVencimento;
		}
		public Date getDataPagamento() {
			return dataPagamento;
		}
		public void setDataPagamento(Date dataPagamento) {
			this.dataPagamento = dataPagamento;
		}
		public String getCancelamento() {
			return cancelamento;
		}
		public void setCancelamento(String cancelamento) {
			this.cancelamento = cancelamento;
		}
		public Long getParcela() {
			return parcela;
		}
		public void setParcela(Long parcela) {
			this.parcela = parcela;
		}
		public Categoria getCategoria() {
			return categoria;
		}
		public void setCategoria(Categoria categoria) {
			this.categoria = categoria;
		}
		
		public String getTipo() {
			return tipo;
		}
		public void setTipo(String tipo) {
			this.tipo = tipo;
		}
		

		public Cartao getCartao() {
			return cartao;
		}
		public void setCartao(Cartao cartao) {
			this.cartao = cartao;
		}
		
		
		//-----------------------------------------------------------------------------------------------------------
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
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
		Despesa other = (Despesa) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

		//---------------------------
}
