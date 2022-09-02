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