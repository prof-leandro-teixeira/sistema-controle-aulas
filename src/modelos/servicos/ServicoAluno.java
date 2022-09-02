package modelos.servicos;

import java.util.List;

import modelo.dao.AlunoDao;
import modelo.dao.FabricaDao;
import modelos.entidades.Aluno;

public class ServicoAluno {
	private AlunoDao dao = FabricaDao.createAlunoDao();
	
		public List<Aluno> findAll(){
			return dao.findAll();
		}
		
		public void salvaOuAtualiza(Aluno obj) {
			if (obj.getId() == null) {
				dao.insert(obj);
			}
			else {
				dao.update(obj);
			}
		}
}