package modelos.entidades;

import java.io.Serializable;
import java.util.Objects;

public class Professor implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String nome;
	private String disciplina;
	private Integer telefone;
	private String endereco;
	private String email;
	
	public Professor() {
	}
	
	public Professor(Integer id, String nome, String disciplina, Integer telefone, String endereco, String email) {
		this.id = id;
		this.nome = nome;
		this.disciplina = disciplina;
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

	public String getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(String disciplina) {
		this.disciplina = disciplina;
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
		return Objects.hash(disciplina, email, endereco, id, nome, telefone);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Professor other = (Professor) obj;
		return Objects.equals(disciplina, other.disciplina) && Objects.equals(email, other.email)
				&& Objects.equals(endereco, other.endereco) && Objects.equals(id, other.id)
				&& Objects.equals(nome, other.nome) && Objects.equals(telefone, other.telefone);
	}

	@Override
	public String toString() {
		return "Professor [id=" + id + ", nome=" + nome + ", disciplina=" + disciplina + ", telefone=" + telefone
				+ ", endereco=" + endereco + ", email=" + email + "]";
	}
	

}
