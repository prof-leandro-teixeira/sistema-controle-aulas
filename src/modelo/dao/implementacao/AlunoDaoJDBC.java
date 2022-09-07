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
import modelo.dao.AlunoDao;
import modelos.entidades.Aluno;

public class AlunoDaoJDBC implements AlunoDao {

	private Connection conn;
	
	public AlunoDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public Aluno findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
				"SELECT * FROM aluno WHERE Id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Aluno obj = new Aluno();
				obj.setId(rs.getInt("Id"));
				obj.setNome(rs.getString("Nome"));
				obj.setResponsavel(rs.getString("Responsavel"));
				obj.setTelefone(rs.getInt("Telefone"));
				obj.setEndereco(rs.getString("Endereco"));
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
	public List<Aluno> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
				"SELECT * FROM aluno ORDER BY Nome");
			rs = st.executeQuery();

			List<Aluno> list = new ArrayList<>();

			while (rs.next()) {
				Aluno obj = new Aluno();
				obj.setId(rs.getInt("Id"));
				obj.setNome(rs.getString("Nome"));
				obj.setResponsavel(rs.getString("Responsavel"));
				obj.setTelefone(rs.getInt("Telefone"));
				obj.setEndereco(rs.getString("Endereco"));
				obj.setEmail(rs.getString("Email"));
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
	public void insert(Aluno obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"INSERT INTO aluno" +
				"(`Nome`, `Responsavel`, `Telefone`, `Endereco`, `Email`)" +
				"VALUES " +
				"(?,?,?,?,?)", 
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
	public void update(Aluno obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE aluno " 
					+ "SET Nome = ?, Responsavel = ?, Telefone = ?, Endereco = ?, Email = ? "
					+ "WHERE Id = ?");
			
			st.setString(1, obj.getNome());
			st.setString(2, obj.getResponsavel());
			st.setInt(3, obj.getTelefone());
			st.setString(4, obj.getEndereco());
			st.setString(5, obj.getEmail());
			st.setInt(6, obj.getId());
					
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
				"DELETE FROM aluno WHERE Id = ?");
			
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