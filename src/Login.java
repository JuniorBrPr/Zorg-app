import dbutil.DBUtil;
import pojo.Patient;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import dao.PatientManagementDAO;

import static java.lang.Integer.parseInt;

//To start build application via the console
//java -jar C:\Users\junio\OneDrive\Bureaublad\javacode\zorgapp2021\out\artifacts\zorgapp2021_jar\zorgapp2021.jar
public class Login {
    DBUtil dB = new DBUtil();
    CheckPatientId checkPatientId = new CheckPatientId();
    //This method shows the login screen
    void menu() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PatientManagementDAO dao = new PatientManagementDAO();
        //Admin screen password
        String adminPassword = "2021";
        int userID = 0;
        //login
        do
        {

            System.out.println("--------------------------------------");
            System.out.println("|                                    |");
            System.out.println("| Welcome to the uZorg application!  |");
            System.out.println("|                                    |");
            System.out.println("|                                    |");
            System.out.println("|           Enter UserID             |");
            System.out.println("|                                    |");
            System.out.println("|                                    |");
            System.out.println("|        !MUST BE A VALID ID!        |");
            System.out.println("|                                    |");
            System.out.println("|        or enter F to Exit          |");
            System.out.println("--------------------------------------");

            String option = br.readLine();
            //userID = Integer.parseInt(br.readLine());
            if(option.equalsIgnoreCase("F")){
                System.out.println("Goodbye!");
                System.exit(0);
            }
            try{
                userID = parseInt(option);
                if (String.valueOf(userID).equals(adminPassword)) {
                    Admin admin = new Admin();
                    admin.menu();
                }else{
                    if(checkPatientId.check(userID)){
                        Connection conn = dB.getConnection();
                        PreparedStatement ps = conn.prepareStatement("SELECT * FROM weight WHERE patientId = ?");
                        ps.setInt(1, userID);
                        ResultSet rs = ps.executeQuery();
                        if (rs.next()) {
                            Patient patient = dao.getPatientByid(userID);
                            patientScreen pS = new patientScreen();
                            pS.menu(patient);
                        } else {
                            System.out.println("\n");
                            System.out.println("\n");
                            System.out.println("---------------------");
                            System.out.println("Invalid credentials!");
                            System.out.println("---------------------");
                            System.out.println("\n");
                        }
                    } else {
                        System.out.println("\n");
                        System.out.println("\n");
                        System.out.println("Invalid PatientID");
                        System.out.println("\n");
                        System.out.println("\n");
                    }
                }

            }catch (NumberFormatException ex) {
                System.out.println("---------------------");
                System.out.println("Invalid credentials!");
                System.out.println("---------------------");
            }



        }while(!String.valueOf(userID).equalsIgnoreCase("F"));
    }
}

