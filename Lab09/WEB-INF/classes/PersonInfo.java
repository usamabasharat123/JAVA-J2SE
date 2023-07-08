import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class PersonInfo extends HttpServlet {

	Connection con;
	Statement st;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {

			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://127.0.0.1/studentinfo";
			con = DriverManager.getConnection(url, "root", "root123");
			st = con.createStatement();

			String query = "Select * from student";
			ResultSet rs = st.executeQuery(query);

			PrintWriter out = response.getWriter();

			out.println("<html><head><title>Information System</title></head>");
			out.println("<body><style>body {\r\n" + //
					"        display: flex;\r\n" + //
					"        justify-content: center;\r\n" + //
					"      }\r\n" + //
					"   \r\n" + //
					"      th {\r\n" + //
					"        font-size: 20px;\r\n" + //
					"        padding: 0 40px;\r\n" + //
					"        border-bottom: 2px solid #45a049;\r\n" + //
					"      }\r\n" + //
					"      td {\r\n" + //
					"        font-size: 18px;\r\n" + //
					"        padding: 10px 40px;\r\n" + //
					"      }\r\n" + //
					"      div {\r\n" + //
					"        width: 40%;\r\n" + //

					"        border-radius: 5px;\r\n" + //
					"        background-color: #f2f2f2;\r\n" + //
					"        padding: 20px;\r\n" + //
					"        text-align: center;\r\n" + //
					"      }\r\n" + //
					"    </style>");
			out.println(
					"<div><table><tr><h1>Student Information Record Room</h1></tr><tr><th>Name</th><th>Address</th><th>Phone No</th></tr>");
			while (rs.next()) {
				String name = rs.getString(1);
				String address = rs.getString(2);
				String phone = rs.getString(3);
				out.println("<tr><td>" + name + "</td><td>" + address + "</td><td>"
						+ phone + "</td></tr>");
			}
			out.print("</table></div>");
			out.println("</body>");
			out.println("</html>");

			out.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("SQL Exception Occured!");
		} catch (ClassNotFoundException e) {
			System.out.println("Class Not Found Exception");
		}

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://127.0.0.1/studentinfo";
			con = DriverManager.getConnection(url, "root", "root123");
			st = con.createStatement();

			PrintWriter pr = response.getWriter();
			String operation = request.getParameter("command");

			switch (operation) {
				case "EnterRecord":
					String name = request.getParameter("name");
					String address = request.getParameter("address");
					String phone = request.getParameter("phone");

					String query = "insert into student(name,address,phone_no)values('" + name + "','" + address + "','"
							+ phone + "')";

					System.out.println(query);

					int rs = st.executeUpdate(query);

					if (rs > 0) {
						pr.println("<html><body><h3>Record Inserted Successfully!</h3></body></html>");
					} else {
						pr.println("<html><body><h3>Record Not Inserted!</h3></body></html>");
					}
					break;

				case "DeleteRecord":
					String nameToDelete = request.getParameter("name");
					String q = "delete from student where name='" + nameToDelete + "'";
					int i = st.executeUpdate(q);
					if (i > 0) {
						pr.println("<html><body><h3>Record is Removed</h3></body></html>");
					} else {
						pr.println("<html><body><h3>Record Not Found</h3></body></html>");

					}
					break;

				case "UpdateRecord":
					String nameToUpdate = request.getParameter("name");
					String addressToUpdate = request.getParameter("address");
					String phonebnToUpdate = request.getParameter("phone");

					String q1 = "update student set address='" + addressToUpdate + "',phone_no='" + phonebnToUpdate
							+ "'where name='" + nameToUpdate + "'";

					System.out.println(q1);

					int r = st.executeUpdate(q1);

					if (r > 0) {
						pr.println("<html><body><h3>Record Updated Successfully!</h3></body></html>");
					} else {
						pr.println("<html><body><h3>Record Not Updated!</h3></body></html>");
					}
					break;

			}
		} catch (SQLException e) {
			System.out.println("SQL Exception Occured!");
		} catch (ClassNotFoundException e) {
			System.out.println("Class Not Found Exception");
		}
	}

}
