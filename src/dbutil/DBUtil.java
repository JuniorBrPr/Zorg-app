package dbutil;
import java.sql.*;
//This whole class just connects and disconnects the program from the DB
public class DBUtil {
    //DB connector
    public Connection getConnection(){
        Connection conn = null;
        PreparedStatement pst;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/zorgapp", "root","");
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
        return conn;
    }
    //DB disconnect
    public void closeConnection(Connection conn)
    {
        try{
            conn.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
