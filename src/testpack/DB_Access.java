package testpack;

import java.sql.*;
import java.util.ArrayList;


public class DB_Access {
	private String url = "jdbc:mysql://localhost:3306/test";
	private String driver = "com.mysql.cj.jdbc.Driver";
	private String uname = "root";
	private String upass = "";
	
	private static Connection c;
	private static Statement st;
	private static PreparedStatement pst;
	
	
	public DB_Access() {
		try {
			Class.forName(driver);
			c = DriverManager.getConnection(url, uname, upass);
			st = c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
//	public int validateLogin(String un, String up) {
//		int uid = -1; // -1 is for invalid user login
//		
////		String sql = "select uid, loginname, name, loginpass from t_users where loginname=? and loginpass=? ";
//		String sql = "select uid, loginname, name, loginpass "
//				+ "from t_users where loginname = '"+uname+"' and loginpass= '"+upass+"' ";
//		
//		
//		try {
//			pst = c.prepareStatement("sql");
//			pst.setString(1, un);
//			pst.setString(2, up);
//			ResultSet rs = pst.executeQuery();
//			System.out.println("check ligin>> " + sql);
//			if(rs.next()) {
//				uid = rs.getInt(1);
//			}
//			
//			
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		
//		
//		return uid;
//	}
	
	public int validateLogin(String uname, String upass)
	{
		int uid = -1; //login is not successful
		
		String sql = "select uid, loginname, name, loginpass "
				+ "from t_users where loginname = '"+uname+"' and loginpass= '"+upass+"' ";
				
		try {
			ResultSet rs = st.executeQuery(sql);
			if(rs.next()) {
				uid = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return uid;
	}
	
	public String getUserName(int uid) {
		String sql = "select name from t_users where uid = " + uid;
		String uname = "";
		try {
			ResultSet rs = st.executeQuery(sql);
			if(rs.next()) uname = rs.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return uname;
	}
	public String getUserById(int uid) {
		String sql = "select LoginPass from t_users where uid = " + uid;
//		String uname = "";
		String upass = "";
		try {
			ResultSet rs = st.executeQuery(sql);
			if(rs.next()) { 
//				uname = rs.getString(1);
				upass = rs.getString(1);
					}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return upass;
	}
	
	public ArrayList<Item> getAllUserItems(int uid) {
		ArrayList<Item> all = new ArrayList<Item>();
		
		String sql = "select iid, itemname, qty from t_items where uid = " + uid;
		
		try {
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()) {
				Item i = new Item(rs.getInt(1), rs.getString(2), rs.getInt(3));
				all.add(i);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
			
		return all;
	}
	
	public int createUserAccount(User u) {
		// 0 means everything is OK, user is created
		// 1 means values are too long
		// 2 means unique constraint on the login name has been violated
		// 3 means that an empty form field was submitted
		// 4 means that the passwords are not the same
		int status = 0;
		
		if(u.getLoginName().trim().equals("") || 
				u.getName().trim().equals("") || 
				u.getLoginPass1().trim().equals("") ||
				u.getLoginPass2().trim().equals("")) return 3;
		if(u.getLoginName().trim().length() > 20 || 
				u.getName().trim().length() > 20 || 
				u.getLoginPass1().trim().length()> 20 ||
				u.getLoginPass2().trim().length()>20) return 1;
		
		if(!u.getLoginPass1().trim().equals(u.getLoginPass2().trim())) return 4;
		
		String sql = "insert into t_users (LoginName, Name, LoginPass) values (?, ?, ?)";
		
		try {
			pst = c.prepareStatement(sql);
			pst.setString(1, u.getLoginName());
			pst.setString(2, u.getName());
			pst.setString(3, u.getLoginPass1());
			pst.executeUpdate();
		} catch (SQLException e) {
			status = 2;
			e.printStackTrace();
		}

		return status;
	}
	
	
	public int addItem(String iname, String iqty, Integer uid) {
		int res = 0;
		// 0 - OK - item was inserted
		// 1 - item name was not given
		// 2 - item qty was either not given or not a valid int
		int qty = 0;
		if(iname == null || iname.trim().equals("")) return 1;
		try {
			qty = Integer.parseInt(iqty);
		}catch(Exception e) {return 2;}
		
		String sql = "insert into t_items (ItemName, Qty, uid) values (?, ?, ?)";
		try {
			pst = c.prepareStatement(sql);
			pst.setString(1, iname);
			pst.setInt(2, qty);
			pst.setInt(3, uid);
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	public Item getItemById(int iid){  
        Item i=new Item();  
        
        String sql= "select * from t_items where iid=?";
        
        try{    
        	pst = c.prepareStatement(sql); 
            pst.setInt(1,iid);  
            ResultSet rs=pst.executeQuery();  
            if(rs.next()){  
                i.setId(rs.getInt(1));  
                i.setName(rs.getString(2));  
                i.setQty(rs.getInt(3));  
                
            }  
        }catch(Exception ex){ex.printStackTrace();}  
          
        return i;  
    }
	
	public static int deleteItem(int iid){  
        int status=0;  
        String sql= "";
        try{    
            PreparedStatement ps=c.prepareStatement("delete from t_items where iid=?");  
            ps.setInt(1,iid);  
            status=ps.executeUpdate();  
          
            System.out.println("delete>>" + ps);
        }catch(Exception e){e.printStackTrace();}  
          
        return status;  
    }  
	
	
	public boolean addNewItem(Item i)
	{
		boolean success= true;
		
		String sql = "insert into t_items(itemname, qty, uid) "
				+ "values(?, ?, ?) ";
		
		try {
			pst = c.prepareStatement(sql);
			pst.setString(1, i.getName());
			pst.setInt(2, i.getQty());
			pst.setInt(3, i.getUid());
			int rows = pst.executeUpdate();
			if(rows != 1) {
			success = false;
			}
			System.out.println("Insert: " + sql);
		} catch (SQLException e) {
			success = false;
			e.printStackTrace();
		}
		return success;
	}
	
//	public boolean updateItem(int id, Item i) {
//		boolean success = true;
//		
//		String sql = "select iid, ItemName, Qty, uid " + 
//		"from t_items where iid = " + id;
//		
//		try {
//			ResultSet rs = st.executeQuery(sql);
//			//Move to the first row. our sql has a where clause that is based on a primary key. as a result
//			//our resultSet will have:
//			//1) exactly 1 row - to be modify
//			//2) no row - if id is not correct
//			
//			if(rs.first()) {
//				rs.updateString(2, i.getName());
//				rs.updateInt(3, i.getQty());
//				//commit the change
//				rs.updateRow();
//			}
//			else {
//				success = false;
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			success = false;
//		}
//		return success;
//	}
	
	public boolean updateItem(int id, Item i) {
		boolean success = true;
	
		String sql = "update t_items set ItemName=?, Qty=?, uid=? where iid=" + id ;
		
		PreparedStatement pst;
		try {
			pst = c.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			pst.setString(1, i.getName());
			pst.setInt(2, i.getQty());
			pst.setInt(3, i.getUid());
			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(sql);
		return success;
	}	
	
////	public boolean updateUser(int id, User u) {
//		boolean success = true;
//	
//		String sql = "update t_users set LoginName=?, Name=?, LoginPass=? where uid=" + id ;
//		
//		PreparedStatement pst;
//		try {
//			pst = c.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
//			pst.setString(1, u.getLoginName());
//			pst.setString(2, u.getName());
//			pst.setString(3, u.getLoginPass1());
//			pst.executeUpdate();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println(sql);
//		return success;
//	}	
	
	public boolean updateUser(int uid, User u)
	{
		boolean success = true;
		
		String sql = "SELECT uid, LoginName, Name, LoginPass " +
		"from t_users where uid = " + uid;

		try {
			ResultSet rs = st.executeQuery(sql);
			if(rs.first()) {
				rs.updateString(3, u.getName());
				rs.updateString(4, u.getLoginPass1());
				rs.updateRow();
			}
			else {
				success = false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			success = false;
			e.printStackTrace();
		}	
		return success;
	}

	
}












