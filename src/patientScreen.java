import dao.PatientManagementDAO;
import pojo.Patient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;

public class patientScreen {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    PatientManagementDAO dao = new PatientManagementDAO();
    Patient user;
    Admin adminFunc = new Admin();
    void menu(Patient patient) throws Exception {
        int patientId = patient.getPatientId();
        //Calling the patient like this, updates the welcome greeting if the nickname gets changed by the user
        user = dao.getPatientByid(patientId);
        String option;
        do {
            //Patient options screen
            System.out.println("Welcome " + dao.getPatientByid(patientId).getNickname());
            System.out.println("<><><><><><><><><><><><>");
            System.out.println("A. View your credentials");
            System.out.println("B. Change nickname");
            System.out.println("C. View Medication Data");
            System.out.println("Z. LogOut");
            System.out.println("F. Exit");
            option = br.readLine();
            System.out.println("\n");
            switch (option.toUpperCase()) {
                case "A" ->
                        //By calling it this way, the nickname automatically updates after a change
                        adminFunc.displayPatient(dao.getPatientByid(patientId));
                case "B" -> //updateNickname(dao.getPatientByid(patientId));
                        adminFunc.updateNickname(dao.getPatientByid(patientId), user);
                case "C" -> adminFunc.viewMedications(dao.getPatientByid(patientId));
                case "Z" -> option = "Z";
                case "F" -> {
                    System.out.println("Goodbye!");
                    System.exit(0);
                }
                default -> System.out.println("Invalid Option! Please try again!!");
            }
        } while (!option.equals("Z"));
        System.out.println("\n");
    }
}
