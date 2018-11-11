package sample;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JsonTestServlet extends HttpServlet {
	private final String REQUEST_STRING = "requestJs";

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String parameter = req.getParameter(REQUEST_STRING);
		// サーブレットに送信されたメッセージが表示される。
		System.out.println(parameter);

		String responseJson = "{\"responseMessage\" : \"from doGet\"}";
		try {
			saveDB();
		} catch (Exception ex) {
			System.out.println("in doGet");
			System.out.print(ex);
			responseJson += ex;
		}
		res.setContentType("application/json;charset=UTF-8");
		PrintWriter out = res.getWriter();
		out.print(responseJson);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String parameter = req.getParameter(REQUEST_STRING);
//		MessageBean bean = JSON.decode(parameter, MessageBean.class);
		// サーブレットに送信されたメッセージが表示される。
		System.out.println(parameter);

		String responseJson = "{\"responseMessage\" : \"from doPost\"}";
		try {
			saveDB();
		} catch (Exception ex) {
			System.out.println("in doPost");
			System.out.print(ex);
			responseJson += ex;
		}

		res.setContentType("application/json;charset=UTF-8");
		PrintWriter out = res.getWriter();
		out.print(responseJson);
	}

	private void saveDB() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager
				.getConnection("jdbc:mysql://mysql:3306/mysql?user=root&password=my-secret-pw&useSSL=false");

		try {
			String tmp = "INSERT INTO my_first_table (name,num) VALUES (?,?)";
			PreparedStatement stat = conn.prepareStatement(tmp);
			stat.setString(1, "grape");
			stat.setInt(2, 42);
			stat.execute();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}
}
