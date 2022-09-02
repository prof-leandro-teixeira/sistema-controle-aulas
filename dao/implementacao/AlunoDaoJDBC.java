package modelo.dao.implementacao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import db.DB;
import db.DbException;
import modelo.dao.AlunoDao;
import modelos.entidades.Aluno;

public class AlunoDaoJDBC implements AlunoDao {

	private Connection conn;
	
	public AlunoDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Aluno obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO aluno "
					+ "(Nome, Responsável, Telefone, Endereço, Email) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, obj.getNome());
			st.setString(2, obj.getResponsavel());
			st.setInt(3, obj.getTelefone());
			st.setString(4, obj.getEndereco());
			st.setString(5, obj.getEmail());
			
			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
				DB.closeResultSet(rs);
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
	public void update(Aluno obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO aluno "
					+ "(Nome, Responsável, Telefone, Endereço, Email) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, obj.getNome());
			st.setString(2, obj.getResponsavel());
			st.setInt(3, obj.getTelefone());
			st.setString(4, obj.getEndereco());
			st.setString(5, obj.getEmail());
					
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
			st = conn.prepareStatement("DELETE FROM aluno WHERE Id = ?");
			
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



	@Override
	public Aluno findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Aluno> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}