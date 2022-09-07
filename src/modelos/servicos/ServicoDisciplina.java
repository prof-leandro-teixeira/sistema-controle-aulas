package modelos.servicos;

import java.util.List;

import modelo.dao.DisciplinaDao;
import modelo.dao.FabricaDao;
import modelos.entidades.Disciplina;

public class ServicoDisciplina {
	private DisciplinaDao dao = FabricaDao.createDisciplinaDao();

	public List<Disciplina> findAll() {
		return dao.findAll();
	}

	public void salvaOuAtualiza(Disciplina obj) {
		if (obj.getId() == null) {
			dao.insert(obj);
		} else {
			dao.update(obj);
		}
	}

	public void deleta(Disciplina obj) {
		dao.deleteById(obj.getId());
	}
}