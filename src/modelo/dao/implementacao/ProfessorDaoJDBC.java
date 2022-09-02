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
import modelo.dao.ProfessorDao;
import modelos.entidades.Professor;

public class ProfessorDaoJDBC implements ProfessorDao {

	private Connection conn;
	
	public ProfessorDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public Professor findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
				"SELECT * FROM disciplina WHERE Id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Professor obj = new Professor();
				obj.setId(rs.getInt("Id"));
				obj.setNome(rs.getString("Nome"));
				obj.setDisciplina(rs.getString("Responsável"));
				obj.setTelefone(rs.getInt("Telefone"));
				obj.setEndereco(rs.getString("Endereço"));
				obj.setEmail(rs.getString("Email"));
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
	public List<Professor> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
				"SELECT * FROM disciplina ORDER BY Nome");
			rs = st.executeQuery();

			List<Professor> list = new ArrayList<>();

			while (rs.next()) {
				Professor obj = new Professor();
				obj.setId(rs.getInt("Id"));
				obj.setNome(rs.getString("Nome"));
				obj.setDisciplina(rs.getString("Responsável"));
				obj.setTelefone(rs.getInt("Telefone"));
				obj.setEndereco(rs.getString("Endereço"));
				obj.setEmail(rs.getString("Email"));
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
	public void insert(Professor obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"INSERT INTO aluno "
				+ "(`Nome`, `Responsável`, `Telefone`, `Endereço`, `Email`) "
				+ "VALUES "
				+ "(?, ?, ?, ?, ?, ?)",
				Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, obj.getNome());
			st.setString(2, obj.getDisciplina());
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
	public void update(Professor obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO aluno "
					+ "(Nome, Responsável, Telefone, Endereço, Email) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, obj.getNome());
			st.setString(2, obj.getDisciplina());
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
}