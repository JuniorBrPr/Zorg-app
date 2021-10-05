import dao.PatientManagementDAO;
import pojo.Patient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;

public class patientScreen {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static PatientManagementDAO dao = new PatientManagementDAO();
    static Patient user;
    void menu(Patient patient) throws Exception {
        System.out.print("\u000C");
        int patientId = patient.getPatientId();
        //Calling the patient like this, updates the welcome greeting if the nickname gets changed by the user
        user = dao.getPatientByid(patientId);
        String option = "";
        do {
            //Patient options screen
            System.out.println("Welcome " + user.getNickname());
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
                        Admin.displayPatient(dao.getPatientByid(patientId));
                case "B" -> updateNickname(dao.getPatientByid(patientId));
                case "C" -> Admin.viewMedications(dao.getPatientByid(patientId));
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
    //Nickname changing function
    public static void updateNickname(Patient patient) throws Exception
    {
        System.out.println("------------------------------------------------");
        System.out.println("Enter New Nickname:");
        System.out.println("------------------------------------------------");
        String nickname = br.readLine();
        //Copy all the original attributes from the patient, only the nickname gets a new value.
        int patientId = patient.getPatientId();
        String surName = patient.getSurName();
        String firstName =patient.getFirstName();
        LocalDate DateOfBirth =patient.getDateOfBirth();
        double length = patient.getLength();
        double weight = patient.getWeight();
        //Store them in a patient object and send it to the DB
        Patient patientU = new Patient(patientId, surName,firstName, nickname, DateOfBirth, length, weight);
        user = patientU;
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
