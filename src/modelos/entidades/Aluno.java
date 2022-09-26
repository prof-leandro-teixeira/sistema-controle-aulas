package modelos.entidades;

import java.io.Serializable;
import java.util.Objects;

public class Aluno implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String nome;
	private String ano;
	private String telefone;
	private String email;
	private String responsavel;
	private String endereco;
	private String escola;

	public Aluno() {
	}

	public Aluno(Integer id, String nome, String ano, String telefone, String email, String responsavel,
			String endereco, String escola) {
		super();
		this.id = id;
		this.nome = nome;
		this.ano = ano;
		this.telefone = telefone;
		this.email = email;
		this.responsavel = responsavel;
		this.endereco = endereco;
		this.escola = escola;
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

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
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

	public String getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getEscola() {
		return escola;
	}

	public void setEscola(String escola) {
		this.escola = escola;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
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
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Aluno [id=" + id + ", nome=" + nome + ", ano=" + ano + ", telefone=" + telefone + ", email=" + email
				+ ", responsavel=" + responsavel + ", endereco=" + endereco + ", escola=" + escola + "]";
	}

	}
