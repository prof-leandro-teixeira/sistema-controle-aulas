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
				obj.setAno(rs.getString("Ano"));
				obj.setTelefone(rs.getString("Telefone"));
				obj.setEmail(rs.getString("Email"));
				obj.setResponsavel(rs.getString("Responsavel"));
				obj.setEndereco(rs.getString("Endereco"));
				obj.setEscola(rs.getString("Escola"));
				
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
				obj.setAno(rs.getString("Ano"));
				obj.setTelefone(rs.getString("Telefone"));
				obj.setEmail(rs.getString("Email"));
				obj.setResponsavel(rs.getString("Responsavel"));
				obj.setEndereco(rs.getString("Endereco"));
				obj.setEscola(rs.getString("Escola"));
				
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
				"(`Nome`, `Ano`, `Telefone`, `Email`, `Responsavel`,`Endereco`, `Escola`) " +
				"VALUES " +
				"(?,?,?,?,?,?,?)",
				Statement.RETURN_GENERATED_KEYS);
							
			st.setString(1, obj.getNome());
			st.setString(2, obj.getAno());
			st.setString(3, obj.getTelefone());
			st.setString(4, obj.getEmail());
			st.setString(5, obj.getResponsavel());
			st.setString(6, obj.getEndereco());
			st.setString(7, obj.getEscola());
									
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
	public void update(Aluno obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE aluno "
					+ " SET Nome = ?, Ano = ?, Telefone = ?, Email = ?, Responsavel = ?, Endereco = ?, Escola = ?"
					+ "WHERE Id = ?");
					
			st.setString(1, obj.getNome());
			st.setString(2, obj.getAno());
			st.setString(3, obj.getTelefone());
			st.setString(4, obj.getEmail());
			st.setString(5, obj.getResponsavel());
			st.setString(6, obj.getEndereco());
			st.setString(7, obj.getEscola());
			st.setInt(8,obj.getId());
			
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