import pojo.Patient;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import dao.PatientManagementDAO;


public class Login {
    String adminPassword = "BOSS";
    String adminId = "1";

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static PatientManagementDAO dao = new PatientManagementDAO();
    void menu() throws Exception {
        String UserID = "";
        String surName = "";
        //login
        do
        {
            System.out.println("<><><><><><><><><><><><>");
            System.out.println("<><><><><><><><><><><><>");
            System.out.println("Welcome to the uZorg application!");
            System.out.println("<><><><><><><><><><><><>");
            System.out.println("<><><><><><><><><><><><>");
            System.out.println("Enter UserID and  Password(surname)");
            System.out.println("<><><><><><><><><><><><>");
            System.out.println("Enter UserID");
            System.out.println("!MUST BE A VALID ID!");
            System.out.println("<><><><><><><><><><><><>");
            System.out.println("or enter F to Exit");
            System.out.println("<><><><><><><><><><><><>");
            UserID = br.readLine();
            System.out.println("\n");
            System.out.println("<><><><><><><><><><><><>");
            System.out.println("Enter surName");
            System.out.println("<><><><><><><><><><><><>");
            System.out.println("or enter F to Exit");
            System.out.println("<><><><><><><><><><><><>");
            surName = br.readLine();

            Patient patient = dao.getPatientByid(UserID);
            System.out.println("\n");

            if (UserID.equals(adminId) && (surName.equals(adminPassword))){
            Admin admin = new Admin();
            admin.menu();
            } else if (surName.equals(patient.getSurName())){

                String option = "";
                do {
                    System.out.println("Welcome " +patient.getFirstName() );
                    System.out.println("<><><><><><><><><><><><>");
                    System.out.println("A. View your credentials");
                    System.out.println("B. Change nickname");
                    System.out.println("Z. LogOut");
                    System.out.println("F. Exit");
                    option = br.readLine();
                    System.out.println("\n");
                    switch(option.toUpperCase()) {
                        case "A":
                            Admin.displayPatient(patient);
                            break;
                        case "B":
                            //Need to make disssss
                            System.out.println("This Feature is yet to be implemented!");
                            //Need to make dis
                            break;
                        case "Z":
                            option = "Z";
                            break;
                        case "F":
                            System.out.println("Goodbye!");
                            System.exit(0);
                            break;
                        default:
                            System.out.println("Invalid Option! Please try again!!");
                            break;
                    }
                }while (!option.equals("Z"));
                System.out.println("\n");
            }
            else {
                System.out.println("Invalid credentials!");
            }
        }while(!UserID.equals("F") || !surName.equals("F"));
    }
}
