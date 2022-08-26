package modelos.entidades;

import java.io.Serializable;
import java.util.Objects;

public class Disciplina implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String nome;
	private String area;
	
	public Disciplina() {
	}

	public Disciplina(Integer id, String nome, String area) {
		this.id = id;
		this.nome = nome;
		this.area = area;
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
	
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	
	@Override
	public int hashCode() {
		return Objects.hash(area, id, nome);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Disciplina other = (Disciplina) obj;
		return Objects.equals(area, other.area) && Objects.equals(id, other.id) && Objects.equals(nome, other.nome);
	}

	@Override
	public String toString() {
		return "Disciplina [id=" + id + ", nome=" + nome + ", area=" + area + "]";
	}

	
}
