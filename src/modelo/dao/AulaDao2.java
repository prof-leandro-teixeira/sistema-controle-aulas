package modelo.dao;

import java.util.List;

import modelos.entidades.Aula;

public interface AulaDao2 {

	void insert(Aula obj);
	void update(Aula obj);
	void deleteById(Integer id);
	Aula findById(Integer id);
	List<Aula> findAll();
}