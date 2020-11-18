package UserManagement.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import UserManagement.model.User;

// This DAO class provides CRUD database operations for the table users in database
public class UserDao {

	private String jdbcURL = "jdbc:mysql://localhost:3306/jsp-servlet-mysql-demo?useSSL=false";
	private String jdbcUsername = "root";
	private String jdbcPassword = "";
	
	private static final String INSERT_USERS_SQL = "INSERT INTO users" + "(name,email,country) VALUES"
	+ "(?,?,?)";
	private static final String SELECT_USER_BY_ID = "select id,name,email,country from users where id=?";
	private static final String SELECT_ALL_USERS = "select * from users";
	private static final String DELETE_USER_BY_ID = "delete from users where id = ?";
	private static final String UPDATE_USER_BY_ID = "update users set name = ?, email = ?, country = ? where id = ?";
	
	
	protected Connection getConnection(){
		Connection connection=null;
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(jdbcURL,jdbcUsername,jdbcPassword);
		}
		catch (SQLException e) {
			System.out.println(e);
		}
		catch (ClassNotFoundException e) {
			System.out.println(e);
		}
		return connection;
	}
	
	// Create user or insert user
	public void insertUser(User user)
	{
		try
		{
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL);
			preparedStatement.setString(1,user.getName());
			preparedStatement.setString(2, user.getEmail());
			preparedStatement.setString(3, user.getCountry());
			preparedStatement.execute();
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
	}
	
	// select user by id
	public User selectUser(int id)
	{
		User user=null;
		try
		{
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);
			preparedStatement.setInt(1, id);
			ResultSet rs=preparedStatement.executeQuery();
			while(rs.next())
			{
				String name = rs.getString("name");
				String email = rs.getString("email");
				String country = rs.getString("country");
				user = new User(id,name,email,country);
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return user;
	}
	
	// select users
	public List<User> selectAllUsers()
	{
		List<User> users=new ArrayList<User>();
		try
		{
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS);
			System.out.println(preparedStatement);
			ResultSet rs=preparedStatement.executeQuery();
			while(rs.next())
			{
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String email = rs.getString("email");
				String country = rs.getString("country");
				users.add(new User(id,name,email,country));
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return users;
	}
	
	// delete user by id
	public boolean deleteUser(int id)
	{
		boolean rowDeleted = false;
		try
		{
			Connection connection= getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_BY_ID);
			preparedStatement.setInt(1, id);
			rowDeleted = preparedStatement.executeUpdate() > 0;
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return rowDeleted;
	}
	
	// update user by id
	public boolean updateUser(User user)
	{
		boolean rowUpdated = false;
		try
		{
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_BY_ID);
			preparedStatement.setString(1,user.getName());
			preparedStatement.setString(2, user.getEmail());
			preparedStatement.setString(3, user.getCountry());
			preparedStatement.setInt(4, user.getId());
			preparedStatement.execute();
			
			rowUpdated = preparedStatement.executeUpdate() > 0;
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		return rowUpdated;
	}
	
}
