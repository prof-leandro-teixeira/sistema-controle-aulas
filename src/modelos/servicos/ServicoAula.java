package modelos.servicos;

import java.util.List;

import modelo.dao.AulaDao;
import modelo.dao.FabricaDao;
import modelos.entidades.Aula;

public class ServicoAula {
	private AulaDao dao = FabricaDao.createAulaDao();
	
		public List<Aula> findAll(){
			return dao.findAll();
		}
		
		public void salvaOuAtualiza(Aula obj) {
			if (obj.getId() == null) {
				dao.insert(obj);
			}
			else {
				dao.update(obj);
			}
		}
}