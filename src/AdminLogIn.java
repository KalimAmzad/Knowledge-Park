import java.awt.EventQueue;
import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class AdminLogIn {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminLogIn window = new AdminLogIn();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	Connection connection = null;
	private JTextField textFieldUn;
	private JPasswordField passwordField;
	/**
	 * Create the application.
	 */
	public AdminLogIn() {
		initialize();
		connection=DatabaseConnection.dbconnector();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(224, 255, 255));
		frame.setBounds(100, 100, 610, 386);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("User Name");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setBounds(251, 89, 102, 32);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Password");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1.setBounds(251, 145, 102, 38);
		frame.getContentPane().add(lblNewLabel_1);
		
		textFieldUn = new JTextField();
		textFieldUn.setBounds(401, 88, 128, 32);
		frame.getContentPane().add(textFieldUn);
		textFieldUn.setColumns(10);
		
		JButton btnLogIn = new JButton("Log In");
		btnLogIn.setBackground(new Color(255, 245, 238));
		Image img1 = new ImageIcon(this.getClass().getResource("/ok.png")).getImage();
		btnLogIn.setIcon(new ImageIcon (img1));
		btnLogIn.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnLogIn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				try{
					String query = "select * from Admin where username = ? and password =?";
					PreparedStatement pst =connection.prepareStatement(query); 
					pst.setString(1, textFieldUn.getText());
					pst.setString(2, passwordField.getText());
					
					ResultSet rs = pst.executeQuery();
					int count = 0;
					while(rs.next())
					{
						count++;
					}
					if(count == 1)
					{
						//JOptionPane.showMessageDialog(null, "Username and password is correct");
						frame.dispose();
						BooksInfo bookinfo = new BooksInfo();
						bookinfo.setVisible(true);
					}
					else if(count>1)
					{
						JOptionPane.showMessageDialog(null, "Duplicate Username and password");
					}
					else
						
					{
						JOptionPane.showMessageDialog(null, "Username and password is not correct.Try again...");
					}
					
					rs.close();
					pst.close();
				}catch(Exception exc)
				{
					JOptionPane.showMessageDialog(null, exc);
					
				}
		
			}
		}
		);
		btnLogIn.setBounds(316, 248, 180, 49);
		frame.getContentPane().add(btnLogIn);
		
		passwordField = new JPasswordField();
		passwordField.setEchoChar('*');
		passwordField.setBounds(401, 150, 128, 32);
		frame.getContentPane().add(passwordField);
		
		JLabel label = new JLabel("");
		Image img = new ImageIcon(this.getClass().getResource("/library.PNG")).getImage();
		label.setIcon(new ImageIcon (img));
		label.setBounds(22, 73, 194, 197);
		frame.getContentPane().add(label);
		
		JLabel label_1 = new JLabel("");
		label_1.setBounds(10, 80, 194, 190);
		frame.getContentPane().add(label_1);
		
		JLabel lblDarulIlm = new JLabel("DARUL ILM");
		lblDarulIlm.setForeground(new Color(186, 85, 211));
		lblDarulIlm.setFont(new Font("Stencil", Font.ITALIC, 30));
		lblDarulIlm.setBounds(194, 11, 200, 26);
		frame.getContentPane().add(lblDarulIlm);
		
		JLabel lblGainKnoeledgeBefore = new JLabel("Gain Knowledge, Before Acton");
		lblGainKnoeledgeBefore.setFont(new Font("Times New Roman", Font.ITALIC, 18));
		lblGainKnoeledgeBefore.setBounds(160, 36, 263, 26);
		frame.getContentPane().add(lblGainKnoeledgeBefore);
	}
}
