package modelos.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Aula implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Date dia;
	private Date horaInicio;
	private Date horaFim;
	
	private Aluno aluno;
	private Disciplina disciplina;
	private Professor professor;
	
	public Aula() {
	}

	public Aula(Integer id, Date dia, Date horaInicio, Date horaFim, Aluno aluno, Disciplina disciplina, Professor professor) {
		this.id = id;
		this.dia = dia;
		this.horaInicio = horaInicio;
		this.horaFim = horaFim;
		this.aluno = aluno;
		this.disciplina = disciplina;
		this.professor = professor;
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

	public Date getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(Date horaInicio) {
		this.horaInicio = horaInicio;
	}

	public Date getHoraFim() {
		return horaFim;
	}

	public void setHoraFim(Date horaFim) {
		this.horaFim = horaFim;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public Disciplina getDisciplina() {
		return disciplina;
	}

	public void setDisciplina (Disciplina disciplina) {
		this.disciplina = disciplina;
	}
	
	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
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
		Aula other = (Aula) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Aula [id=" + id + ", dia=" + dia + ", horaInicio=" + horaInicio + ", horaFim=" + horaFim + ", aluno="
				+ aluno + ", disciplina=" + disciplina + ", professor=" + professor + "]";
	}	
}
