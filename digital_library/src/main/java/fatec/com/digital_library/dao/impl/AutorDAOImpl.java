package fatec.com.digital_library.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fatec.com.digital_library.dao.AutorDAO;
import fatec.com.digital_library.entity.Autor;
import fatec.com.digital_library.entity.Category;
import fatec.com.digital_library.utility.DatabaseConnection;

public class AutorDAOImpl implements AutorDAO {
	
	private String query;
	private String dml;
	private StringBuilder builder;
	
	@Override
	public List<Autor> fetchAutors() {
		List<Autor> autorList = new ArrayList<Autor>();
		DatabaseConnection dbCon;
		PreparedStatement ps;
		Connection con;
		dbCon = new DatabaseConnection();
		builder = new StringBuilder();
		con = dbCon.getConnection();
		ResultSet rs;
		
		builder.append("SELECT name, date_of_death, date_of_birth, country_of_birth, state_of_birth, city_of_birth, country_of_death, state_of_death, city_of_death, biography FROM library.autor ");
		query = builder.toString();
			
		try {
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			java.util.Date javaDate = null;
			while (rs.next()) {
				Autor autor = new Autor();
				autor.setName(rs.getString(1));
				
				if (rs.getDate(2) != null) {
					javaDate = new java.sql.Date(rs.getDate(2).getTime());
				}
				
				autor.setDeathDate(javaDate);
				
				if (rs.getDate(3) != null) {
					javaDate = new java.sql.Date(rs.getDate(3).getTime());
				}
				autor.setBirthDate(javaDate);
				autor.setCountryOfBirth(rs.getString(4));
				autor.setStateOfBirth(rs.getString(5));
				autor.setCityOfBirth(rs.getString(6));
				autor.setCountryOfDeath(rs.getString(7));
				autor.setStateOfDeath(rs.getString(8));
				autor.setCityOfDeath(rs.getString(9));
				autor.setBiography(rs.getString(10));
				autorList.add(autor);
			}	
			
			ps.close();
			rs.close();
			con.close();
			
			return autorList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return autorList;
	}
	@Override
	public boolean createAutor(Autor autor) {
		DatabaseConnection dbCon;
		PreparedStatement ps;
		Connection con;
		dbCon = new DatabaseConnection();
		builder = new StringBuilder();
		con = dbCon.getConnection();
		builder.append("INSERT INTO library.autor(name, date_of_death, date_of_birth, country_of_birth, state_of_birth, city_of_birth, country_of_death, state_of_death, city_of_death, biography) ");
		builder.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		dml = builder.toString();
		java.util.Date javaDate = null;
		java.sql.Date sqlDate = null;
		try {
			con.setAutoCommit(false);
			ps = con.prepareStatement(dml);
			ps.setString(1, autor.getName());
			
			if(autor.getDeathDate() != null) {
				javaDate = autor.getDeathDate();
				sqlDate = new java.sql.Date(javaDate.getTime());
			}
			
			ps.setDate(2, sqlDate);
			
			if(autor.getDeathDate() != null) {
				javaDate = autor.getBirthDate();
				sqlDate = new java.sql.Date(javaDate.getTime());
			}
			
			ps.setDate(3, sqlDate);
			ps.setString(4, autor.getCountryOfBirth());
			ps.setString(5, autor.getStateOfBirth());
			ps.setString(6, autor.getCityOfBirth());
			ps.setString(7, autor.getCountryOfDeath());
			ps.setString(8, autor.getStateOfDeath());
			ps.setString(9, autor.getCityOfDeath());
			ps.setString(10, autor.getBiography());
			
			if (ps.executeUpdate() > 0) {
				ps.close();
				con.commit();
				con.close();
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean updateAutor(Autor autor, Autor oldAutor) {
		DatabaseConnection dbCon;
		PreparedStatement ps;
		Connection con;
		dbCon = new DatabaseConnection();
		builder = new StringBuilder();
		con = dbCon.getConnection();
		builder.append("UPDATE library.autor SET name = ?, date_of_death = ?, date_of_birth = ?, country_of_birth = ? , state_of_birth = ?, city_of_birth = ?, country_of_death = ?, state_of_death = ?, city_of_death = ?, biography = ? ");
		builder.append("WHERE name = ? AND date_of_birth = ? AND country_of_birth = ? AND state_of_birth = ? AND city_of_birth = ?");
		dml = builder.toString();
		
		try {
			con.setAutoCommit(false);
			ps = con.prepareStatement(dml);
			ps.setString(1, autor.getName());
			java.util.Date javaDate = autor.getDeathDate();
			java.sql.Date sqlDate = new java.sql.Date(javaDate.getTime());
			ps.setDate(2, sqlDate);
			javaDate = autor.getBirthDate();
			sqlDate = new java.sql.Date(javaDate.getTime());
			ps.setDate(3, sqlDate);
			ps.setString(4, autor.getCountryOfBirth());
			ps.setString(5, autor.getStateOfBirth());
			ps.setString(6, autor.getCityOfBirth());
			ps.setString(7, autor.getCountryOfDeath());
			ps.setString(8, autor.getStateOfDeath());
			ps.setString(9, autor.getCityOfDeath());
			ps.setString(10, autor.getBiography());
			ps.setString(11, oldAutor.getName());
			javaDate = oldAutor.getBirthDate();
			sqlDate = new java.sql.Date(javaDate.getTime());
			ps.setDate(12, sqlDate);
			ps.setString(13, oldAutor.getCountryOfBirth());
			ps.setString(14, oldAutor.getStateOfBirth());
			ps.setString(15, oldAutor.getCityOfBirth());
			
			if (ps.executeUpdate() > 0) {
				ps.close();
				con.commit();
				con.close();
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean removeAutor(Autor autor) {
		DatabaseConnection dbCon;
		PreparedStatement ps;
		Connection con;
		dbCon = new DatabaseConnection();
		builder = new StringBuilder();
		con = dbCon.getConnection();
		
		builder.append("DELETE FROM library.autor ");
		builder.append(" WHERE name = ? AND date_of_birth = ? AND country_of_birth = ? AND state_of_birth = ? AND city_of_birth = ? ");
		dml = builder.toString();
		
		try {
			con.setAutoCommit(false);
			ps = con.prepareStatement(dml);
			
			ps.setString(1, autor.getName());
			java.util.Date javaDate = autor.getBirthDate();
			java.sql.Date sqlDate = new java.sql.Date(javaDate.getTime());
			ps.setDate(2, sqlDate);
			ps.setString(3, autor.getCountryOfBirth());
			ps.setString(4, autor.getStateOfBirth());
			ps.setString(5, autor.getCityOfBirth());
			
			if (ps.executeUpdate() > 0) {
				ps.close();
				con.commit();
				con.close();
				return true;
			} else {
				ps.close();
				con.close();
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Autor fetchAutor(Autor autor) {
		DatabaseConnection dbCon;
		PreparedStatement ps;
		Connection con;
		ResultSet rs;
		dbCon = new DatabaseConnection();
		builder = new StringBuilder();
		con = dbCon.getConnection();
		Autor fetchedAutor = null;
		builder.append("SELECT name, date_of_death, date_of_birth, country_of_birth, state_of_birth, city_of_birth, country_of_death, state_of_death, city_of_death, biography FROM library.autor ");
		builder.append("WHERE name = ? AND date_of_birth = ? AND country_of_birth = ? AND state_of_birth = ? AND city_of_birth = ? ");
		query = builder.toString();
		
		try {
			ps = con.prepareStatement(query);
			ps.setString(1, autor.getName());
			java.util.Date javaDate = autor.getBirthDate();
			java.sql.Date sqlDate = new java.sql.Date(javaDate.getTime());
			ps.setDate(2, sqlDate);
			ps.setString(3, autor.getCountryOfBirth());
			ps.setString(4, autor.getStateOfBirth());
			ps.setString(5, autor.getCityOfBirth());
			rs = ps.executeQuery();
			
			while (rs.next()) {
				fetchedAutor = new Autor();
				fetchedAutor.setName(rs.getString(1));
				sqlDate = rs.getDate(2);
				javaDate = new java.util.Date(sqlDate.getTime());
				fetchedAutor.setDeathDate(javaDate);
				sqlDate = rs.getDate(3);
				javaDate = new java.util.Date(sqlDate.getTime());
				fetchedAutor.setBirthDate(javaDate);
				fetchedAutor.setCountryOfBirth(rs.getString(4));
				fetchedAutor.setStateOfBirth(rs.getString(5));
				fetchedAutor.setCityOfBirth(rs.getString(6));
				fetchedAutor.setCountryOfDeath(rs.getString(7));
				fetchedAutor.setStateOfDeath(rs.getString(8));
				fetchedAutor.setCityOfDeath(rs.getString(9));
				fetchedAutor.setBiography(rs.getString(10));
			}
			
			ps.close();
			rs.close();
			con.close();
			return fetchedAutor;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (RuntimeException e) {
			e.printStackTrace();
		}		
		return null;
	}
}
