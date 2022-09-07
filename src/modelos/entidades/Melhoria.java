package modelos.entidades;

import java.io.Serializable;
import java.util.Objects;

public class Melhoria implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String tipo;
	private String descricao;
	
	public Melhoria() {
	}

	public Melhoria(Integer id, String tipo, String descricao) {
		this.id = id;
		this.tipo = tipo;
		this.descricao = descricao;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	
	@Override
	public int hashCode() {
		return Objects.hash(descricao, id, tipo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Melhoria other = (Melhoria) obj;
		return Objects.equals(descricao, other.descricao) && Objects.equals(id, other.id) && Objects.equals(tipo, other.tipo);
	}

	@Override
	public String toString() {
		return "Melhoria [id=" + id + ", tipo=" + tipo + ", descricao=" + descricao + "]";
	}
}