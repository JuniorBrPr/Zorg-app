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
        int patientId = patient.getPatientId();
        String option = "";
        do {

            System.out.println("Welcome " + patient.getNickname());
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
        int patientId = patient.getPatientId();
        String surName = patient.getSurName();
        String firstName =patient.getFirstName();
        LocalDate DateOfBirth =patient.getDateOfBirth();
        double length = patient.getLength();
        double weight = patient.getWeight();
        Patient patientU = new Patient(patientId, surName,firstName, nickname, DateOfBirth, length, weight);
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
