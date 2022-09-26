package modelo.dao.implementacao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import modelo.dao.ProfessorDao;
import modelos.entidades.Disciplina;
import modelos.entidades.Professor;

public class ProfessorDaoJDBC implements ProfessorDao {

	private Connection conn;
	
	public ProfessorDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Professor obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"INSERT INTO professor" +
				"(`Nome`,`Telefone`,`Email`,`HoraAula`,`DataNascimento`,`DisciplinaId`) " 
				+ "VALUES "
				+"(?,?,?,?,?,?)",
				Statement.RETURN_GENERATED_KEYS);
							
			st.setString(1, obj.getNome());
			st.setString(2, obj.getTelefone());
			st.setString(3, obj.getEmail());
			st.setDouble(4, obj.getHoraAula());
			st.setDate(5, new java.sql.Date(obj.getDataNascimento().getTime()));
			st.setInt(6, obj.getDisciplina().getId());
			
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
					"UPDATE professor "
					+ " SET Nome = ?, Telefone = ?, Email = ?, HoraAula = ?, DataNascimento = ?, DisciplinaId = ? "
					+ "WHERE Id = ?");
					
			st.setString(1, obj.getNome());
			st.setString(2, obj.getTelefone());
			st.setString(3, obj.getEmail());
			st.setDouble(4, obj.getHoraAula());
			st.setDate(5, new java.sql.Date(obj.getDataNascimento().getTime()));
			st.setString(6, obj.getDisciplina().getNome());
			st.setInt(7, obj.getId());
			
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
				"DELETE FROM professor WHERE Id = ?");
			
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
	public Professor findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT professor.*,disciplina.Nome as DiscNome "
					+ "FROM professor INNER JOIN disciplina "
					+ "ON professor.DisciplinaId = disciplina.Id "
					+ "WHERE disciplina.Id = ? ");
						
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Disciplina disc = instanciaDisciplina(rs);
				Professor obj = instanciaProfessor(rs, disc);
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
	
	private Professor instanciaProfessor(ResultSet rs, Disciplina disc) throws SQLException {
		Professor obj = new Professor();
		obj.setId(rs.getInt("Id"));
		obj.setNome(rs.getString("Nome"));
		obj.setTelefone(rs.getString("Telefone"));
		obj.setEmail(rs.getString("Email"));
		obj.setHoraAula(rs.getDouble("HoraAula"));
		obj.setDataNascimento(new java.util.Date(rs.getTimestamp("DataNascimento").getTime()));
		obj.setDisciplina(disc);
		return obj;	
	}

	private Disciplina instanciaDisciplina(ResultSet rs) throws SQLException {
		Disciplina disc = new Disciplina();
			disc.setId(rs.getInt("DisciplinaId"));
		disc.setNome(rs.getString("DiscNome"));
		//disc.setArea(rs.getString("DiscArea"));
		return disc;
	}

	@Override
	public List<Professor> findAll() {
			PreparedStatement st = null;
			ResultSet rs = null;
			try {
				st = conn.prepareStatement(
						"SELECT professor.*,disciplina.Nome as DiscNome "
						+ "FROM professor INNER JOIN disciplina "
						+ "ON professor.DisciplinaId = disciplina.Id "
						+ "ORDER BY Nome ");
				
						rs = st.executeQuery();
						List<Professor> lista = new ArrayList<>();
						Map<Integer, Disciplina> map = new HashMap<>();
						
						while (rs.next()) {
							
							Disciplina disc = map.get(rs.getInt("DisciplinaId"));
							
							if (disc == null) {
								disc = instanciaDisciplina(rs);
								map.put(rs.getInt("DisciplinaId"),disc);
							}
							
							Professor obj = instanciaProfessor(rs, disc);
							lista.add(obj);
						}
						return lista;
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
	public List<Professor> findByDisciplina(Disciplina disciplina) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT professor.*,disciplina.Nome as DiscNome "
					+ "FROM professor INNER JOIN disciplina "
					+ "ON professor.DisciplinaId = disciplina.Id "
					+ "WHERE DisciplinaId = ? "
					+ "ORDER BY Nome ");
			
					st.setInt(1, disciplina.getId());
					rs = st.executeQuery();
					
					List<Professor> lista = new ArrayList<>();
					Map<Integer, Disciplina> map = new HashMap<>();
					
					while (rs.next()) {
						
						Disciplina disc = map.get(rs.getInt("DisciplinaId"));
						
						if (disc == null) {
							disc = instanciaDisciplina(rs);
							map.put(rs.getInt("DisciplinaId"),disc);
						}
						
						Professor obj = instanciaProfessor(rs, disc);
						lista.add(obj);
					}
					return lista;
				}
				catch (SQLException e) {
					throw new DbException(e.getMessage());
				}
				finally {
					DB.closeStatement(st);
					DB.closeResultSet(rs);
				}
			}
}