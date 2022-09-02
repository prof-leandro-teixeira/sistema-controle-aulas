package modelo.dao;

import java.util.List;

import modelos.entidades.Disciplina;


public interface DisciplinaDao {

	void insert(Disciplina obj);
	void update(Disciplina obj);
	void deleteById(Integer id);
	Disciplina findById(Integer id);
	List<Disciplina> findAll();
}
