package modelo.dao.implementacao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import db.DbIntegrityException;
import modelo.dao.AulaDao;
import modelos.entidades.Aula;

public class AulaDaoJDBC2 implements AulaDao {

	private Connection conn;
	
	public AulaDaoJDBC2(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public Aula findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
				"SELECT * FROM disciplina WHERE Id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Aula obj = new Aula();
				obj.setId(rs.getInt("Id"));
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
				"SELECT * FROM disciplina ORDER BY Name");
			rs = st.executeQuery();

			List<Aula> list = new ArrayList<>();

			while (rs.next()) {
				Aula obj = new Aula();
				obj.setId(rs.getInt("Id"));
				list.add(obj);
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
				"INSERT INTO aula " +
				"(Dia) " +
				"(HoraInicio)"  +		
				"(HoraFim)"  +	
				"(Aluno)"  +
				"(Disciplina)"  +
				"(Professor)"  +
				"VALUES " +
				"(?,?,?,?,?,?)", 
				Statement.RETURN_GENERATED_KEYS);

			st.setDate(1, (Date) obj.getDia());
			st.setDate(2, (Date) obj.getHoraInicio());
			st.setDate(3, (Date )obj.getHoraFim());
			st.setString(4, obj.getAluno().toString());
			st.setString(5, obj.getDisciplina().toString());
			st.setString(6, obj.getProfessor().toString());

			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
			}
			else {
				throw new DbException("Unexpected error! No rows affected!");
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
					"INSERT INTO aula " +
					"(Dia) " +
					"(HoraInicio)"  +		
					"(HoraFim)"  +	
					"(Aluno)"  +
					"(Disciplina)"  +
					"(Professor)"  +
					"VALUES " +
					"(?,?,?,?,?,?)", 
					Statement.RETURN_GENERATED_KEYS);

			st.setDate(1, (Date) obj.getDia());
			st.setDate(2, (Date) obj.getHoraInicio());
			st.setDate(3, (Date )obj.getHoraFim());
			st.setString(4, obj.getAluno().toString());
			st.setString(5, obj.getDisciplina().toString());
			st.setString(6, obj.getProfessor().toString());

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
				"DELETE FROM disciplina WHERE Id = ?");

			st.setInt(1, id);

			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbIntegrityException(e.getMessage());
		} 
		finally {
			DB.closeStatement(st);
		}
	}
}
