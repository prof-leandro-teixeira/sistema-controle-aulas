package modelos.entidades;

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

public class Aula implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Date dia;
	private Date inicio;
	private Date fim;
	private String aluno;
	private String disciplina;
	private String professor;
	private Integer duracao;
		
	public Aula() {
	}

	public Aula(Integer id, Date dia, Date inicio, Date fim, String aluno, String disciplina, String professor,
			Integer duracao) {
		super();
		this.id = id;
		this.dia = dia;
		this.inicio = inicio;
		this.fim = fim;
		this.aluno = aluno;
		this.disciplina = disciplina;
		this.professor = professor;
		this.duracao = duracao;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDia() {
		return dia;
	}

	public void setDia(Date dia) {
		this.dia = dia;
	}

	public Date getInicio() {
		return inicio;
	}

	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}

	public Date getFim() {
		return fim;
	}

	public void setFim(Date fim) {
		this.fim = fim;
	}

	public String getAluno() {
		return aluno;
	}

	public void setAluno(String aluno) {
		this.aluno = aluno;
	}

	public String getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(String disciplina) {
		this.disciplina = disciplina;
	}

	public String getProfessor() {
		return professor;
	}

	public void setProfessor(String professor) {
		this.professor = professor;
	}

	public Integer getDuracao() {
		return duracao;
	}

	public void setDuracao(Integer duracao) {
		this.duracao = duracao;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		return Objects.hash(aluno, dia, disciplina, duracao, fim, id, inicio, professor);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Aula other = (Aula) obj;
		return Objects.equals(aluno, other.aluno) && Objects.equals(dia, other.dia)
				&& Objects.equals(disciplina, other.disciplina) && Objects.equals(duracao, other.duracao)
				&& Objects.equals(fim, other.fim) && Objects.equals(id, other.id)
				&& Objects.equals(inicio, other.inicio) && Objects.equals(professor, other.professor);
	}

	@Override
	public String toString() {
		return "Aula [id=" + id + ", dia=" + dia + ", inicio=" + inicio + ", fim=" + fim + ", aluno=" + aluno
				+ ", disciplina=" + disciplina + ", professor=" + professor + ", duracao=" + duracao + "]";
	}
	
	
}