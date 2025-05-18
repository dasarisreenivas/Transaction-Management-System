import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Scanner;
public class MoneyTransaction {
	private static final String URL = "jdbc:mysql://localhost:3306/jdbc_conn";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "Srinivas@192004";
	static Connection con = null;
	
	
	private static PreparedStatement pstm_s= null;
	private static PreparedStatement pstm_r= null;
	

	private static int pin = 12121212;
	static Scanner sc = new Scanner(System.in);
	private static String sendQuery = "UPDATE BANK SET BALANCE = BALANCE - ? WHERE UID = ?";
	private static String receiveQuery = "UPDATE BANK SET BALANCE = BALANCE + ? WHERE UID = ?";
	
	public static void main(String[]args) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(URL,USERNAME,PASSWORD);
			con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			con.setAutoCommit(false);
			
			
			//preparing the sending the query
			pstm_s = con.prepareStatement(sendQuery);
			System.out.println("Enter the sender uid : ");
			int suid = sc.nextInt();
			System.out.println("Enter the amount : ");
			int amount = sc.nextInt();
			System.out.println("Current amount is "+exAmount(suid));
			if(amount<=exAmount(suid)) {
			pstm_s.setInt(1, amount);
			pstm_s.setInt(2, suid);
			int x = pstm_s.executeUpdate();
			System.out.println("Sending the money "+x);
			//preparing the receiver sending Query
			
			pstm_r = con.prepareStatement(receiveQuery);
			System.out.println("Enter the receiver uid : ");
			int ruid = sc.nextInt();
			
			 
			pstm_r.setInt(1,amount);
			pstm_r.setInt(2,ruid);
			pstm_r.addBatch();
			
			
			int[] y = pstm_r.executeBatch();
			System.out.println("Receivong the money "+y.length);
			
			
			//validation 
			System.out.println("Enter the pin : ");
			if(pin == sc.nextInt() && x==1 && y.length==1) {
				con.commit();
				System.out.println("successful");
			}
			else {
				con.rollback();
				System.out.println("unSuccessfull");
				
			}
			}else {
				System.out.println("insufficient balance ");
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		finally {
			try {
				if(pstm_r!=null) {
				pstm_r.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(pstm_s!=null)
					pstm_s.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(con!=null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
public static int exAmount(int suid){
		ResultSet res = null;
	    int cuammount = 0;
	    PreparedStatement pstm = null;
		try {
			
			String cAmount = "SELECT BALANCE FROM BANK WHERE UID = ?;";
			pstm = con.prepareStatement(cAmount);
			pstm.setInt(1, suid);
			res = pstm.executeQuery();
			if(res.next()) {
				cuammount = res.getInt("BALANCE");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
	            if (res != null) res.close();
	            if (pstm != null) pstm.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
		}
		return cuammount;
	}

}
