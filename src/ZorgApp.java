import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

public class ZorgApp
{
    public static void main(String[] args)   throws Exception
    {
        //Start new LogIn screen
        Login login = new Login();
        login.menu();

    }
}
