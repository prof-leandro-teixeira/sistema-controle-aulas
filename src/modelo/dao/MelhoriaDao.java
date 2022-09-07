package modelo.dao;

import java.util.List;

import modelos.entidades.Melhoria;


public interface MelhoriaDao {

	void insert(Melhoria obj);
	void update(Melhoria obj);
	void deleteById(Integer id);
	Melhoria findById(Integer id);
	List<Melhoria> findAll();
}
