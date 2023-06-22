import java.util.*;
import java.sql.*;

class AddressBook {
	Connection con;
	Statement st;

	public AddressBook() throws Exception {
		try {

			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://127.0.0.1/studentinfo";
			con = DriverManager.getConnection(url, "root", "root123");
			st = con.createStatement();

		} catch (SQLException e) {
			System.out.println("SQL Exception Occured!");
		}
	}

	public void Addperson(String username, String address, String city, int Phone) throws Exception {

		String query = "insert into info(username,address,city1,phone)values('" + username + "','" + address + "','"
				+ city + "'," + Phone + ")";

		int rs = st.executeUpdate(query);

		System.out.println(rs);

		if (rs > 0) {
			System.out.println("Record inserted successfully.");
		}

		else {
			System.out.println("Record could not inserted.");
		}

	}

	public void SearchPerson(String Name) throws Exception {
		String query = "Select * from info where username='" + Name + "';";
		ResultSet rs = st.executeQuery(query);

		if (rs.next()) {
			String username = rs.getString(1);
			String address = rs.getString(2);
			String city = rs.getString(3);
			int phone = rs.getInt("phone");
			System.out.println("\n--------------------\n");
			System.out.println("Name:" + username + "\nAddress:" + address + "\ncity: " + city + "\nphone:" + phone);
			System.out.println("\n--------------------\n");
		} else {
			System.out.println("\n--------------------\n");
			System.out.println("Sorry! No record found");
			System.out.println("\n--------------------\n");
		}

	}

	public void Deleteperson(String Name) throws Exception {
		String q = "delete from info where username='" + Name + "'";
		int i = st.executeUpdate(q);
		if (i > 0) {
			System.out.println("record deleted");
		} else {
			System.out.println("no record deleted");

		}
	}

}

class Driver {
	public static void main(String args[]) throws Exception {
		AddressBook obj = new AddressBook();
		boolean exit = false;
		int choice;

		while (!exit) {
			System.out.println("\n\n");
			System.out.println("Address Book Menu");
			System.out.println("1. Add Person");
			System.out.println("2. Delete Person");
			System.out.println("3. Search Person");
			System.out.println("4. Exit");
			System.out.print("Enter your choice: ");

			Scanner scanner = new Scanner(System.in);
			choice = scanner.nextInt();

			switch (choice) {
				case 1:
					scanner.nextLine();
					System.out.print("Enter username: ");
					String username = scanner.nextLine();
					System.out.print("Enter address: ");
					String address = scanner.nextLine();
					System.out.print("Enter city: ");
					String city = scanner.nextLine();
					System.out.print("Enter phone number: ");
					int phone = scanner.nextInt();

					obj.Addperson(username, address, city, phone);
					break;

				case 2:
					scanner.nextLine();
					System.out.print("Enter username to delete: ");
					String deleteUsername = scanner.nextLine();
					obj.Deleteperson(deleteUsername);
					break;
				case 3:
					scanner.nextLine();
					System.out.print("Enter username to search: ");
					String search = scanner.nextLine();
					obj.SearchPerson(search);
					break;
				case 4:
					System.out.print("Connection is Closed");
					// con.close();
					 System.exit(0);
			}

		}
	}
}