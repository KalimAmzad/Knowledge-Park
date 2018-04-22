import java.awt.BorderLayout;
import java.sql.*;
import javax.swing.*;
import java.awt.EventQueue;

import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BooksInfo extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	public static BooksInfo frame = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new BooksInfo();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	Connection connection = null;
	private JTextField textFieldISBN;
	private JTextField textFieldBookName;
	private JTextField textFieldAuthor;
	private JTextField textFieldPage;
	private JTextField textFieldPrice;
	
	
	public void refreshtable()
	{
		try{
			String query = "select * from Books";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
			
			pst.close();
			rs.close();
			
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	/**
	 * Create the frame.
	 */
	public BooksInfo() {
		connection = DatabaseConnection.dbconnector();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 695, 456);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(173, 216, 230));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnLoadTable = new JButton("Book Catalog");
		btnLoadTable.setBackground(new Color(255, 222, 173));
		btnLoadTable.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnLoadTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					String query = "select * from Books";
					PreparedStatement pst = connection.prepareStatement(query);
					ResultSet rs = pst.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs));
					
					pst.close();
					rs.close();
					
				}catch(Exception ex)
				{
					ex.printStackTrace();
				}
				
			}
		});
		btnLoadTable.setBounds(367, 88, 217, 34);
		contentPane.add(btnLoadTable);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(247, 155, 422, 245);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try{
					int row = table.getSelectedRow();
					String isbn_ = (table.getModel().getValueAt(row, 0).toString());
					String query = "select * from books where ISBN = '"+isbn_+"'";
					PreparedStatement pst = connection.prepareStatement(query);
					
					ResultSet rs = pst.executeQuery();
					
					while(rs.next())
					{
						textFieldISBN.setText(rs.getString("ISBN"));
						textFieldBookName.setText(rs.getString("Bookname"));
						textFieldAuthor.setText(rs.getString("Author"));
						textFieldPage.setText(rs.getString("Page"));
						textFieldPrice.setText(rs.getString("Price"));
						
					}
					
					pst.close();
					
				}catch(Exception ex4)
				{
					ex4.printStackTrace();
				}
			}
		});
		scrollPane.setViewportView(table);
		
		JLabel lblIsbnNo = new JLabel("ISBN No.");
		lblIsbnNo.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblIsbnNo.setBounds(10, 158, 76, 23);
		contentPane.add(lblIsbnNo);
		
		textFieldISBN = new JTextField();
		textFieldISBN.setBounds(112, 155, 125, 23);
		contentPane.add(textFieldISBN);
		textFieldISBN.setColumns(10);
		
		JLabel lblBookName = new JLabel("Book Name");
		lblBookName.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblBookName.setBounds(10, 205, 76, 23);
		contentPane.add(lblBookName);
		
		textFieldBookName = new JTextField();
		textFieldBookName.setBounds(111, 202, 126, 23);
		contentPane.add(textFieldBookName);
		textFieldBookName.setColumns(10);
		
		JLabel lblAuthor = new JLabel("Author");
		lblAuthor.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblAuthor.setBounds(10, 245, 76, 23);
		contentPane.add(lblAuthor);
		
		textFieldAuthor = new JTextField();
		textFieldAuthor.setBounds(111, 242, 126, 23);
		contentPane.add(textFieldAuthor);
		textFieldAuthor.setColumns(10);
		
		JLabel lblPage = new JLabel("Page");
		lblPage.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblPage.setBounds(10, 283, 65, 23);
		contentPane.add(lblPage);
		
		textFieldPage = new JTextField();
		textFieldPage.setBounds(111, 283, 125, 23);
		contentPane.add(textFieldPage);
		textFieldPage.setColumns(10);
		
		JLabel lblPrice = new JLabel("Price");
		lblPrice.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblPrice.setBounds(10, 320, 65, 23);
		contentPane.add(lblPrice);
		
		textFieldPrice = new JTextField();
		textFieldPrice.setBounds(112, 322, 125, 23);
		contentPane.add(textFieldPrice);
		textFieldPrice.setColumns(10);
		
		JButton btnSave = new JButton("Insert");
		btnSave.setBackground(new Color(250, 240, 230));
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					String query = "Insert into Books values(?,?,?,?,?)";
					PreparedStatement pst = connection.prepareStatement(query);
					pst.setString(1, textFieldISBN.getText() );
					pst.setString(2, textFieldBookName.getText() );
					pst.setString(3, textFieldAuthor.getText() );
					pst.setString(4, textFieldPage.getText() );
					pst.setString(5, textFieldPrice.getText() );
					
					pst.execute();
					
					JOptionPane.showMessageDialog(null, "Data Inserted");
					
					pst.close();
					
				}catch(Exception ex2)
				{
					ex2.printStackTrace();
				}
				refreshtable();
			}
		});
		btnSave.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnSave.setBounds(10, 94, 89, 23);
		contentPane.add(btnSave);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.setBackground(new Color(250, 250, 210));
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					String query = "Update Books set  ISBN='"+textFieldISBN.getText()+"',BookName='"+textFieldBookName.getText()+"',Author='"+textFieldAuthor.getText()+"',Page='"+textFieldPage.getText()+"',Price='"+textFieldPrice.getText()+"' where ISBN='"+textFieldISBN.getText()+"' ";
					PreparedStatement pst = connection.prepareStatement(query);
					
					pst.execute();
					
					JOptionPane.showMessageDialog(null, "Data Updated");
					
					pst.close();
					
				}catch(Exception ex3)
				{
					ex3.printStackTrace();
				}
				refreshtable();
			}
			
		});
		btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnUpdate.setBounds(114, 94, 89, 23);
		contentPane.add(btnUpdate);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setBackground(new Color(250, 250, 210));
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					String query = "Delete from books where ISBN='"+textFieldISBN.getText()+ "' ";
					PreparedStatement pst = connection.prepareStatement(query);
					
					pst.execute();
					
					JOptionPane.showMessageDialog(null, "Data Deleted");
					
					pst.close();
					
				}catch(Exception ex4)
				{
					ex4.printStackTrace();
				}
				refreshtable();
			}
		});
		btnDelete.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnDelete.setBounds(229, 94, 89, 23);
		contentPane.add(btnDelete);
		
		JLabel lblBookListManagement = new JLabel("Book List Management");
		lblBookListManagement.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD | Font.ITALIC, 20));
		lblBookListManagement.setBounds(217, 11, 226, 44);
		contentPane.add(lblBookListManagement);
		refreshtable();
	}
}
