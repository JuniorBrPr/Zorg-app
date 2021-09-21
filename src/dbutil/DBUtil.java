package dbutil;
import java.sql.*;

public class DBUtil {
    //DB connector
    public static Connection getConnection(){
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
    public static void closeConnection(Connection conn)
    {
        try{
            conn.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
