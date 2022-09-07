package modelos.servicos;

import java.util.List;

import modelo.dao.MelhoriaDao;
import modelo.dao.FabricaDao;
import modelos.entidades.Melhoria;

public class ServicoMelhoria {
	private MelhoriaDao dao = FabricaDao.createMelhoriaDao();

	public List<Melhoria> findAll() {
		return dao.findAll();
	}

	public void salvaOuAtualiza(Melhoria obj) {
		if (obj.getId() == null) {
			dao.insert(obj);
		} else {
			dao.update(obj);
		}
	}

	public void deleta(Melhoria obj) {
		dao.deleteById(obj.getId());
	}
}