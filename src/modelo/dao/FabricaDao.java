package modelo.dao;

import db.DB;
import modelo.dao.implementacao.AlunoDaoJDBC;
import modelo.dao.implementacao.AulaDaoJDBC;
import modelo.dao.implementacao.DisciplinaDaoJDBC;
import modelo.dao.implementacao.ProfessorDaoJDBC;

public class FabricaDao {
	
	public static AlunoDao createAlunoDao() {
		return new AlunoDaoJDBC(DB.getConnection());
	}
	
	public static AulaDao createAulaDao() {
		return new AulaDaoJDBC(DB.getConnection());
	}
		
	public static DisciplinaDao createDisciplinaDao() {
		return new DisciplinaDaoJDBC(DB.getConnection());
	}
	
	public static ProfessorDao createProfessorDao() {
		return new ProfessorDaoJDBC(DB.getConnection());
	}

	
}