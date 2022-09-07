package modelo.dao.implementacao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import modelo.dao.AulaDao;
import modelos.entidades.Aula;

public class AulaDaoJDBC implements AulaDao {

	private Connection conn;
	
	public AulaDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public Aula findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
				"SELECT * FROM aula WHERE Id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Aula obj = new Aula();
				obj.setId(rs.getInt("Id"));
				obj.setDia(rs.getDate("Dia"));
				obj.setInicio(rs.getDate("Inicio"));
				obj.setFim(rs.getDate("Fim"));
				obj.setAluno(rs.getString("Aluno"));
				obj.setDisciplina(rs.getString("Disciplina"));
				obj.setProfessor(rs.getString("Professor"));
				return obj;
			}
			return null;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	
	@Override
	public List<Aula> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
				"SELECT * FROM aula ORDER BY Aula");
			rs = st.executeQuery();

			List<Aula> list = new ArrayList<>();

			while (rs.next()) {
				Aula obj = new Aula();
				obj.setId(rs.getInt("Id"));
				obj.setDia(rs.getDate("Dia"));
				obj.setInicio(rs.getDate("Inicio"));
				obj.setFim(rs.getDate("Fim"));
				obj.setAluno(rs.getString("Aluno"));
				obj.setDisciplina(rs.getString("Disciplina"));
				obj.setProfessor(rs.getString("Professor"));
				}
			return list;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	
	@Override
	public void insert(Aula obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"INSERT INTO Aula " +
				"(`Dia`, `Inicio`, `Fim`, `Aluno`, `Disciplina`,`Professor`,`Duracao`) " +
				"VALUES " +
				"(?, ?, ?, ?, ?, ?, ?)", 
				Statement.RETURN_GENERATED_KEYS);
			
			st.setDate(1, obj.getDia());
			st.setDate(2, obj.getInicio());
			st.setDate(3, obj.getFim());
			st.setString(4, obj.getAluno());
			st.setString(5, obj.getDisciplina());
			st.setString(6, obj.getProfessor());
			st.setInt(7, obj.getDuracao());
			
			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
			}
			else {
				throw new DbException("Unexpected error! Nenhuma linha alterada!");
			}
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(Aula obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					
				"UPDATE aluno " 
				+ "SET Dia = ?,	Inicio = ?, Fim = ?, Aluno = ?, Disciplina = ?, Professor = ?, Duracao = ? "
				+ "WHERE Id = ?");
			
			st.setDate(1, obj.getDia());
			st.setDate(2, obj.getInicio());
			st.setDate(3, obj.getFim());
			st.setString(4, obj.getAluno());
			st.setString(5, obj.getDisciplina());
			st.setString(6, obj.getProfessor());
			st.setInt(7, obj.getDuracao());
					
			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"DELETE FROM aula WHERE Id = ?");
			
			st.setInt(1, id);
			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}
}