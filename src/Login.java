import pojo.Patient;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import dao.PatientManagementDAO;
//To start build application via the console
//C:\Users\junio>java -jar C:\Users\junio\OneDrive\Bureaublad\javacode\zorgapp2021\out\artifacts\zorgapp2021_jar\zorgapp2021.jar
public class Login {
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
            userID = Integer.parseInt(br.readLine());
            if(String.valueOf(userID).toUpperCase().equals("F")){
                System.out.println("Goodbye!");
                System.exit(0);
            }
            System.out.println("\n");

            if (String.valueOf(userID).equals(adminPassword)) {
                Admin admin = new Admin();
                admin.menu();

            }else{
                Patient patient = dao.getPatientByid(userID);
                //If the userId is in the DB, status should return 1
                int status = dao.updatePatient(patient);
                if(status == 1){
                    patientScreen pS = new patientScreen();
                    pS.menu(patient);

                }else {
                    System.out.println("Invalid credentials!");
                }
            }
        }while(!String.valueOf(userID).toUpperCase().equals("F"));
    }
}

