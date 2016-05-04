package fatec.com.digital_library.utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CountryLoader {
	
	private List<String> countryList = new ArrayList<String>();
	
	public List<String> loadCity() {
		DatabaseConnection dbCon;
		PreparedStatement ps;
		Connection con;
		ResultSet rs;
		dbCon = new DatabaseConnection();
		StringBuilder builder = new StringBuilder();
		String query;
		con = dbCon.getConnection();

		builder.append("SELECT portuguese_name  FROM library.country ");
		query = builder.toString();

		try {
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();

			while (rs.next()) {
				countryList.add(rs.getString(1));
			}

			ps.close();
			rs.close();
			con.close();
			return countryList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
