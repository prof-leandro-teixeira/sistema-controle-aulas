package modelos.servicos;

import java.util.List;

import modelo.dao.FabricaDao;
import modelo.dao.ProfessorDao;
import modelos.entidades.Professor;

public class ServicoProfessor {
private ProfessorDao dao = FabricaDao.createProfessorDao();
	
	public List<Professor> findAll(){
		return dao.findAll();
	}
	
	public void salvaOuAtualiza(Professor obj) {
		if (obj.getId() == null) {
			dao.insert(obj);
		}
		else {
			dao.update(obj);
		}
	}
}
/*public List<Professor> findAll(){
		List<Professor> list = new ArrayList<>();
		list.add(new Professor(1,"Zezinho","Matemática",999999999,"Rua de tal 111, bairro centro","zez@yahoo.com"));
		list.add(new Professor(1,"Huguinho","Português",999999999,"Rua  atal 1, bairro centro","hug@yahoo.com"));
		list.add(new Professor(1,"Luizinho","Química",888889999,"Rua com tal 92, bairro centro","luz@yahoo.com"));
		return list;*/