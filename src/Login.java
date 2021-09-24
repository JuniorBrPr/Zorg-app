import pojo.Patient;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import dao.PatientManagementDAO;


public class Login {


    void menu() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PatientManagementDAO dao = new PatientManagementDAO();
        String adminPassword = "BOSS";
        String adminId = "1";
        int UserID = 0;
        String surName = "";
        String option = "";
        //login
        do
        {
        System.out.println("<><><><><><><><><><><><><><><><><><><>");
        System.out.println("<><><><><><><><><><><><><><><><><><><>");
        System.out.println("<>Welcome to the uZorg application!<><");
        System.out.println("<><><><><><><><><><><><><><><><><><><>");
        System.out.println("<><><><><><><><><><><><><><><><><><><>");
        System.out.println("<>Enter UserID and  Password(surname)<");
        System.out.println("<><><><><><><><><><><><><><><><><><><>");
        System.out.println("<><><><><>||Enter UserID||<><><><><><>");
        System.out.println("<><><><>!MUST BE A VALID ID!<><><><><>");
        System.out.println("<><><><><><><><><><><><><><><><><><><>");
        System.out.println("<><><><>or enter F to Exit<><><><><><>");
        System.out.println("<><><><><><><><><><><><><><><><><><><>");
        UserID = Integer.parseInt(br.readLine());
            if(String.valueOf(UserID).toUpperCase().equals("F")){
                System.out.println("Goodbye!");
                System.exit(0);
            }

        System.out.println("\n");
        System.out.println("<><><><><><><><><><><><>");
        System.out.println("Enter surName");
        System.out.println("<><><><><><><><><><><><>");
        // System.out.println("or enter F to Exit");
        System.out.println("<><><><><><><><><><><><>");
        surName = br.readLine();
            if(surName.toUpperCase().equals("F")){
                System.out.println("Goodbye!");
                System.exit(0);
            }

        Patient patient = dao.getPatientByid(UserID);
        System.out.println("\n");

            if (String.valueOf(UserID).equals(adminId) && (surName.equals(adminPassword))) {
                Admin admin = new Admin();
                admin.menu();
            }else if(surName.equals(patient.getSurName())){
                patientScreen pS = new patientScreen();
                pS.menu(patient);
            } else {
            System.out.println("Invalid credentials!");
            }

        }while(!String.valueOf(UserID).toUpperCase().equals("F") || !surName.toUpperCase().equals("F"));




}
}

