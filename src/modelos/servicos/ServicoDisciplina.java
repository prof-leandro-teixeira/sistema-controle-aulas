package modelos.servicos;

import java.util.ArrayList;
import java.util.List;
import modelos.entidades.Disciplina;

public class ServicoDisciplina {
	
	public List<Disciplina> findAll(){
		List<Disciplina> list = new ArrayList<>();
		list.add(new Disciplina(1,"Física","Ciências Exatas e da Natureza"));
		list.add(new Disciplina(2,"Geografia","Ciências Humanas"));
		list.add(new Disciplina(3,"História","Ciências Humanas"));
		list.add(new Disciplina(4, "Matemática","Ciências Exatas e da Natureza"));
		list.add(new Disciplina(5,"Português","Linguagens"));
		list.add(new Disciplina(6,"Química","Ciências Exatas e da Natureza"));
		return list;
	}
}