package modelos.servicos;

import java.util.ArrayList;
import java.util.List;

import modelos.entidades.Disciplina;

public class ServicoDisciplina {
	
	public List<Disciplina> findAll(){
		List<Disciplina> list = new ArrayList<>();
		list.add(new Disciplina(1, "Matemática","Exatas"));
		list.add(new Disciplina(2,"Português","Humanas"));
		list.add(new Disciplina(3,"História","Humanas"));
		list.add(new Disciplina(4,"Geografia","Humanas"));
		eturn list;
	
		
	}

}
