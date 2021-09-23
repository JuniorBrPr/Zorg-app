import dao.PatientManagementDAO;
import pojo.Patient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class patientScreen {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static PatientManagementDAO dao = new PatientManagementDAO();
    void menu(Patient patient) throws Exception {
        String option = "";
        do {
            String patientId = patient.getPatientId();
            System.out.println("Welcome " + patient.getFirstName());
            System.out.println("<><><><><><><><><><><><>");
            System.out.println("A. View your credentials");
            System.out.println("B. Change nickname");
            System.out.println("Z. LogOut");
            System.out.println("F. Exit");
            option = br.readLine();
            System.out.println("\n");
            switch (option.toUpperCase()) {
                case "A":
                    //By calling it this way, the nickname automatically updates after a change
                    Admin.displayPatient(dao.getPatientByid(patientId));
                    break;
                case "B":

                    updateNickname(dao.getPatientByid(patientId));
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
        } while (!option.equals("Z"));
        System.out.println("\n");

    }
    public static void updateNickname(Patient patient) throws Exception
    {
        System.out.println("------------------------------------------------");
        System.out.println("Enter New Patient Nickname:");
        System.out.println("------------------------------------------------");
        String nickname = br.readLine();
        String patientId = patient.getPatientId();
        String surName = patient.getSurName();
        String firstName =patient.getFirstName();
        LocalDate DateOfBirth =patient.getDateOfBirth();
        //Drop
        int age =patient.getAge();
        //Add Length, Weight
        Patient patientU = new Patient(patientId, surName,firstName, nickname, DateOfBirth, age);
        int status = dao.updatePatient(patientU);
        if(status ==1 )
        {
            System.out.println("Patient updated successfully");
        }
        else
        {
            System.out.println("ERROR while updating patient");
        }
        System.out.println("\n");

    }
}
