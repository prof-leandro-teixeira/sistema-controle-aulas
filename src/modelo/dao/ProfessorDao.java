package modelo.dao;

import java.util.List;

import modelos.entidades.Professor;

public interface ProfessorDao {

	void insert(Professor obj);
	void update(Professor obj);
	void deleteById(Integer id);
	Professor findById(Integer id);
	List<Professor> findAll();
}