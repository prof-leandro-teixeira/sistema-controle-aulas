package modelos.entidades;

import java.io.Serializable;
import java.util.Objects;

public class Aluno implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String nome;
	private String responsavel;
	private Integer telefone;
	private String endereco;
	private String email;
	
	public Aluno() {
	}
	
	public Aluno(Integer id, String nome, String responsavel, Integer telefone, String endereco, String email) {
		this.id = id;
		this.nome = nome;
		this.responsavel = responsavel;
		this.telefone = telefone;
		this.endereco = endereco;
		this.email = email;
	}

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

	public String getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}

	public Integer getTelefone() {
		return telefone;
	}

	public void setTelefone(Integer telefone) {
		this.telefone = telefone;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, endereco, id, nome, responsavel, telefone);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Aluno other = (Aluno) obj;
		return Objects.equals(email, other.email) && Objects.equals(endereco, other.endereco)
				&& Objects.equals(id, other.id) && Objects.equals(nome, other.nome)
				&& Objects.equals(responsavel, other.responsavel) && Objects.equals(telefone, other.telefone);
	}

	@Override
	public String toString() {
		return "Aluno [id=" + id + ", nome=" + nome + ", responsavel=" + responsavel + ", telefone=" + telefone
				+ ", endereco=" + endereco + ", email=" + email + "]";
	}


		
	}


