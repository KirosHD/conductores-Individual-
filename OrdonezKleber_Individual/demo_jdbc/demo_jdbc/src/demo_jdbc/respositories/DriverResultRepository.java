package demo_jdbc.respositories;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import demo_jdbc.models.DriverResult;

public class DriverResultRepository  {
	
	
	

	
	public List<DriverResult> getResultByYear(int year){
		
		List<DriverResult> results = new ArrayList<>();
			String jdbcUrl = "jdbc:mysql://localhost:3306/formula_01";
			String user = "root";
			String password = "0505";
	
			
			// Ejecutar la consulta
			String query = "SELECT   r.year,   d.forename,\n"
					+ "    d.surname,\n"
					+ "    COUNT(CASE WHEN res.position = 1 THEN 1 END) AS wins,\n"
					+ "    SUM(res.points) AS total_points,\n"
					+ "    RANK() OVER (PARTITION BY r.year ORDER BY SUM(res.points) DESC) AS season_rank\n"
					+ "FROM results  res\n"
					+ "JOIN races r ON res.raceid = r.raceid\n"
					+ "JOIN drivers d ON res.driverid = d.driverid\n"
					+ "\n"
					+ "WHERE r.year = ? \n"
					+ "GROUP BY   r.year, d.driverid, d.forename, d.surname\n"
					+ "ORDER BY   r.year, season_rank;";
			
			try (Connection con = DriverManager.getConnection(jdbcUrl, user, password);
		             PreparedStatement pst = con.prepareStatement(query)) {
				
				pst.setInt(1, year);
				
			
		
				try (ResultSet rs = pst.executeQuery()) {
	                while (rs.next()) {
	            String driverName = rs.getString("forename") + " " + rs.getString("surname");
				int wins = rs.getInt("wins");
				int totalPoints = rs.getInt("total_points");
				int rank = rs.getInt("season_rank");
				
				results.add(new DriverResult(driverName, wins, totalPoints, rank));
				
			}
		}
			
		}catch(SQLException ex) {
			ex.printStackTrace();
			System.out.println(ex.getCause());
		}
		
		return results;
		}
	
	
	public List<Integer> getAvailableYears() {
        List<Integer> years = new ArrayList<>();

        String url = "jdbc:mysql://localhost:3306/formula_01";
        String user = "root";
        String password = "0505";
        String query = "SELECT DISTINCT year FROM races ORDER BY year";

        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement pst = con.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                years.add(rs.getInt("year"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return years;
    }
}
