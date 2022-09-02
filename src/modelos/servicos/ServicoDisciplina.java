package modelos.servicos;

import java.util.List;

import modelo.dao.DisciplinaDao;
import modelo.dao.FabricaDao;
import modelos.entidades.Disciplina;

	
public class ServicoDisciplina {

	private DisciplinaDao dao = FabricaDao.createDisciplinaDao();
	
		public List<Disciplina> findAll(){
			return dao.findAll();
		}
		
		public void salvaOuAtualiza(Disciplina obj) {
			if (obj.getId() == null) {
				dao.insert(obj);
			}
			else {
				dao.update(obj);
			}
		}
}



/*List<Disciplina> list = new ArrayList<>();
list.add(new Disciplina(1,"Física","Ciências Exatas e da Natureza"));
list.add(new Disciplina(2,"Geografia","Ciências Humanas"));
list.add(new Disciplina(3,"História","Ciências Humanas"));
list.add(new Disciplina(4, "Matemática","Ciências Exatas e da Natureza"));
list.add(new Disciplina(5,"Português","Linguagens"));
list.add(new Disciplina(6,"Química","Ciências Exatas e da Natureza"));
return list;
*/