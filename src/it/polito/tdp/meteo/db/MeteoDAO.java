package it.polito.tdp.meteo.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.meteo.bean.Citta;
import it.polito.tdp.meteo.bean.Rilevamento;

public class MeteoDAO {

	public List<Rilevamento> getAllRilevamenti() {

		final String sql = "SELECT Localita, Data, Umidita FROM situazione ORDER BY data ASC";

		List<Rilevamento> rilevamenti = new ArrayList<Rilevamento>();

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Rilevamento r = new Rilevamento(rs.getString("Localita"), rs.getDate("Data"), rs.getInt("Umidita"));
				rilevamenti.add(r);
			}

			conn.close();
			return rilevamenti;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public List<Rilevamento> getAllRilevamentiLocalitaMese(int mese, String localita) {
		List<Rilevamento> rilevamenti = new ArrayList<Rilevamento>();
	final String sql = "SELECT Localita, Data, Umidita  from situazione where EXTRACT( MONTH FROM DATA) = ? AND localita= ?";
	try {
		Connection conn = DBConnect.getInstance().getConnection();
		PreparedStatement st = conn.prepareStatement(sql);
		st.setInt(1, mese);
		st.setString(2, localita);
		ResultSet rs = st.executeQuery();

		while (rs.next()) {

			Rilevamento r = new Rilevamento(rs.getString("Localita"), rs.getDate("Data"), rs.getInt("Umidita"));
			rilevamenti.add(r);
	
		}

		conn.close();
		return rilevamenti;

	} catch (SQLException e) {

		e.printStackTrace();
		throw new RuntimeException(e);
	}
		
		
		
		
	}

	public Double getAvgRilevamentiLocalitaMese(int mese, String localita) {
	final String sql ="SELECT avg(umidita) from situazione where EXTRACT( MONTH FROM DATA) = ? AND localita= ?";
	double media=0.0;
		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, mese);
			st.setString(2, localita);
			ResultSet rs = st.executeQuery();

			if (rs.next()) {

				media=rs.getInt(1);
			}

			conn.close();
			return media;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		
		
		
		
		
		
		
		
		
		
		//
	}

	public String getAllMedia(int mese) {
		final String sql ="SELECT localita,AVG(umidita) FROM situazione WHERE EXTRACT(MONTH FROM DATA) = ? GROUP BY localita";
		String stampa="";
		
			try {
				Connection conn = DBConnect.getInstance().getConnection();
				PreparedStatement st = conn.prepareStatement(sql);
				
				st.setInt(1, mese);
				ResultSet rs = st.executeQuery();

				while (rs.next()) {
				stampa+=rs.getString("localita")+" "+rs.getDouble(2)+"\n";
				}
					
		
				conn.close();
				return stampa;

			} catch (SQLException e) {

				e.printStackTrace();
				throw new RuntimeException(e);
			}
	
	}



	public List<Citta> getTutteCitta() {
		List<Citta> tutteCitta=new ArrayList<Citta>();
		final String sql ="SELECT localita from situazione group by localita";

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				tutteCitta.add(new Citta(rs.getString("localita")));
			}
			
			
			conn.close();
			return tutteCitta;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}

			
	}

}
