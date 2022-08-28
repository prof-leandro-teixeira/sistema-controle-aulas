package modelos.servicos;

import java.util.ArrayList;
import java.util.List;
import modelos.entidades.Aluno;

public class ServicoAluno {
	
	public List<Aluno> findAll(){
		List<Aluno> list = new ArrayList<>();
		list.add(new Aluno(1,"João","mãe",999999999,"Rua tal 222, bairro centro","joao@yahoo.com"));
		list.add(new Aluno(1,"Maria","mãe",999999999,"Rua tal 222, bairro centro","maria@yahoo.com"));
		list.add(new Aluno(1,"Chapeuzinho","vovó",888889999,"Rua tal 2222, bairro centro","littlehat@yahoo.com"));
		list.add(new Aluno(1,"Hunter","o próprio",989898989,"Rua tal 2202, bairro centro","hunter@yahoo.com"));
		return list;
	}
}