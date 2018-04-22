import java.sql.*;
import javax.swing.*;
public class DatabaseConnection {
	Connection conn = null;
	public static Connection dbconnector()
	{
		try{
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:E:\\New folder (2)\\JFrame\\Darul Ilm\\AdminPanel.sqlite");
			//JOptionPane.showMessageDialog(null, "Connection Successfull");
			
			return conn;
		}catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, e);
			return null;
		}
	}

}
