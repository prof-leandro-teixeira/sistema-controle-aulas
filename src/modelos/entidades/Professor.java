package modelos.entidades;

import java.io.Serializable;
import java.util.Date;

public class Professor implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String nome;
	private String telefone;
	private String email;
	private Double horaAula;
	private Date dataNascimento;
		
	private Disciplina disciplina;
		
	public Professor() {
	}

	public Professor(Integer id, String nome, String telefone,String email, Double horaAula, Date dataNascimento,
			Disciplina disciplina) {
		this.id = id;
		this.nome = nome;
		this.telefone = telefone;
		this.email = email;
		this.horaAula = horaAula;
		this.dataNascimento = dataNascimento;
		this.disciplina = disciplina;
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

	public Double getHoraAula() {
		return horaAula;
	}

	public void setHoraAula(Double horaAula) {
		this.horaAula = horaAula;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Disciplina getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Professor other = (Professor) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Professor [id=" + id + ", nome=" + nome + ", telefone=" + telefone + ", email=" + email + ", horaAula="
				+ horaAula + ", dataNascimento=" + dataNascimento + ", disciplina=" + disciplina + "]";
	}	
}