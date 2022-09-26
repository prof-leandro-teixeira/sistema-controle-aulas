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
import db.DbIntegrityException;
import modelo.dao.MelhoriaDao;
import modelos.entidades.Melhoria;

public class MelhoriaDaoJDBC implements MelhoriaDao {

	private Connection conn;
	
	public MelhoriaDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public Melhoria findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
				"SELECT * FROM melhoria WHERE Id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Melhoria obj = new Melhoria();
				obj.setId(rs.getInt("Id"));
				obj.setTipo(rs.getString("Tipo"));
				obj.setDescricao(rs.getString("Descricao"));
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
	public List<Melhoria> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
				"SELECT * FROM melhoria ORDER BY Tipo");
			rs = st.executeQuery();

			List<Melhoria> list = new ArrayList<>();

			while (rs.next()) {
				Melhoria obj = new Melhoria();
				obj.setId(rs.getInt("Id"));
				obj.setTipo(rs.getString("Tipo"));
				obj.setDescricao(rs.getString("Descricao"));
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
	public void insert(Melhoria obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"INSERT INTO melhoria " +
				"(`Tipo`, `Descricao`) " +
				"VALUES " +
				"(?,?)", 
				Statement.RETURN_GENERATED_KEYS);

			st.setString(1, obj.getTipo());
			st.setString(2, obj.getDescricao());
			
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
	public void update(Melhoria obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE melhoria " 
					+ "SET Tipo = ?, Descricao = ?"
					+ "WHERE Id = ?");

			st.setString(1, obj.getTipo());
			st.setString(2, obj.getDescricao());
			st.setInt(3, obj.getId());

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
				"DELETE FROM melhoria WHERE Id = ?");

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