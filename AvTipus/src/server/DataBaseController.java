package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import application.Request;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataBaseController {
	private static Connection c;
	private static String url = "jdbc:mysql://remotemysql.com:3306/jsyC4yp1qF?useLegacyDatetimeCode=false&serverTimezone=UTC";
	private static String username = "jsyC4yp1qF";
	private static String password = "50MjwBICSL";
	

	
	
	
	public static boolean Connect() {

		
		System.out.println("Connecting to database...");

		try {
			c = DriverManager.getConnection(url, username, password);
			System.out.println("Database connected!");
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static void setUrl(String url) {
		DataBaseController.url = url;
	}

	public static void setUsername(String username) {
		DataBaseController.username = username;
	}

	public static void setPassword(String password) {
		DataBaseController.password = password;
	}

	public static ObservableList<Request> getTable() {
		ObservableList<Request> o = FXCollections.observableArrayList();
		String query = "select * from requests";
		ResultSet rs = null;
		PreparedStatement statement;
		try {
			statement = c.prepareStatement(query);
			rs = statement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			while (rs.next()) {
				try {
					o.add(new Request(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5),
							rs.getString(6), rs.getString(7)));
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return o;
	}

	public static ObservableList<Request> getTableWithID(int id) {
		ObservableList<Request> o = FXCollections.observableArrayList();
		String query = "select * from requests where id=" + Integer.toString(id);
		ResultSet rs = null;
		PreparedStatement statement;
		try {
			statement = c.prepareStatement(query);
			rs = statement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			while (rs.next()) {
				try {
					o.add(new Request(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5),
							rs.getString(6), rs.getString(7)));
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return o;
	}

	public static void addToDB(String txt) throws Exception {
		ArrayList<String> arr;
		PreparedStatement st;
		String query = "insert into requests (requests.name,requests.system,requests.desc,requests.change,requests.status,requests.handler)"
				+ " values (?,?,?,?,?,?);";
		arr = new ArrayList<>(Arrays.asList(txt.split(",")));
		for (int i = 0;i<arr.size();i++) //CUT SPACES INFRONT OF WORD (REMOVES PARSE INT EXCEPTION FOR 2 WORDS IN ADDLINE
		{
			if (arr.get(i).charAt(0)==' ')
			{
				String temp = arr.get(i).substring(1);
				arr.set(i, temp);
			}
		}
		if (arr.size() != 6 || Integer.parseInt(arr.get(1)) > 5 || Integer.parseInt(arr.get(1)) < 0
				|| arr.get(0).length() > 100 || arr.get(2).length() > 1000 || arr.get(3).length() > 1000
				|| arr.get(4).length() > 100 || arr.get(5).length() > 100) {
			throw new Exception("Wrong parameters");
		}
		try {
			st = c.prepareStatement(query);
			st.setString(1, arr.get(0));
			st.setInt(2, Integer.parseInt(arr.get(1)));
			st.setString(3, arr.get(2));
			st.setString(4, arr.get(3));
			st.setString(5, arr.get(4));
			st.setString(6, arr.get(5));
			st.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static boolean changeStatus(int id, String status) {
		String query = "update requests set status='" + status + "' where id=" + id;
		try {
			PreparedStatement st = c.prepareStatement(query);
			st.execute();
		} catch (SQLException e) {
			return false;
		}
		return true;
	}

	public static boolean changeDescription(int id, String desc) {
		String query = "update requests set requests.desc='" + desc + "' where id=" + id;
		try {
			PreparedStatement st = c.prepareStatement(query);
			st.execute();
		} catch (SQLException e) {
			return false;
		}
		return true;
	}

	public static boolean changeChanges(int id, String change) {
		String query = "update requests set requests.change='" + change + "' where id=" + id;
		try {
			PreparedStatement st = c.prepareStatement(query);
			st.execute();
		} catch (SQLException e) {
			return false;
		}
		return true;
	}

}
