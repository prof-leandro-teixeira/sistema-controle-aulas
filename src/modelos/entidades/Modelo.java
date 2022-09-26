package modelos.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

//implements Serializable torna o acesso aos dados do banco de dados acessíveis para leitura
public class Modelo implements Serializable {

	private static final long serialVersionUID = 1L;

//declaração das variáveis	
	private Integer id;
	private String nome;
	private String telefone;
	private String email;
	private Double salario;
	private Date dataNascimento;
	
//construtor vazio
	public Modelo() {
	}

//construtor com campos	
	public Modelo(Integer id, String nome, String telefone, String email, Double salario, Date dataNascimento) {
		super();
		this.id = id;
		this.nome = nome;
		this.telefone = telefone;
		this.email = email;
		this.salario = salario;
		this.dataNascimento = dataNascimento;
	}

//getters and setters
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Double getSalario() {
		return salario;
	}

	public void setSalario(Double salario) {
		this.salario = salario;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	
//hashcode
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

//equals
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Modelo other = (Modelo) obj;
		return Objects.equals(id, other.id);
	}
//tostring
	@Override
	public String toString() {
		return "Modelo [id=" + id + ", nome=" + nome + ", telefone=" + telefone + ", email=" + email + ", salario="
				+ salario + ", dataNascimento=" + dataNascimento + "]";
	}
}