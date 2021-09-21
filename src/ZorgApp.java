import java.sql.Connection;
import java.sql.PreparedStatement;

public class ZorgApp
{
    Connection con;
    PreparedStatement pst;



    public static void main(String[] args) throws Exception
    {
        Login login = new Login();
        login.menu();
    }
}
