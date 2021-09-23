import calculators.ageCalculator;
import pojo.Patient;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import dao.PatientManagementDAO;
import java.time.LocalDate;


public class Admin {
   static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
   static PatientManagementDAO dao = new PatientManagementDAO();
   static ageCalculator aC = new ageCalculator();
   void menu() throws Exception {
      String option = "";

      do
      {
         System.out.println("Welcome Admin");
         System.out.println("<><><><><><><><><><><><>");
         System.out.println("A. View Patients");
         System.out.println("B. Add Patient");
         System.out.println("C. Update Patient");
         System.out.println("D. Delete Patient");
         System.out.println("E. Search Patient");
         System.out.println("F. Exit");
         System.out.println("<><><><><><><><><><><><>");
         System.out.println("Enter an option");
         System.out.println("<><><><><><><><><><><><>");
         option = br.readLine();
         System.out.println("\n");

         switch(option.toUpperCase())
         {
            case "A":
               viewPatients();
               break;

            case "B":
               addPatient();
               break;

            case "C":
               updatePatient();
               break;

            case "D":
               deletePatient();
               break;

            case "E":
               searchPatient();
               break;

            case "F":
               System.out.println("Goodbye!");
               System.exit(0);
               break;

            default:
               System.out.println("Invalid Option! Please try again!!");
               break;
         }
      }while(!option.equals("F"));
   }
   public static void viewPatients()
   {
      System.out.println("-----------------------------------------------");

      //Get all the patients from the DAO and store them
      List<Patient> patientList = dao.getAllPatients();
      for(Patient patient: patientList)
      {
         //display product one by one
         displayPatient(patient);
      }
      System.out.println("-----------------------------------------------");
      System.out.println("\n");

   }

   public static void addPatient() throws Exception
   {
      //Move the calculator to the displayPatient class
      ageCalculator ac = new ageCalculator();
      System.out.println("------------------------------------------------");
      System.out.println("Enter Patient ID:");
      System.out.println("------------------------------------------------");
      String patientId = br.readLine();
      System.out.println("------------------------------------------------");
      System.out.println("Enter Patient Surname:");
      System.out.println("------------------------------------------------");
      String surName = br.readLine();
      System.out.println("------------------------------------------------");
      System.out.println("Enter Patient First Name:");
      System.out.println("------------------------------------------------");
      String firstName = br.readLine();
      System.out.println("------------------------------------------------");
      System.out.println("Enter Patient Nickname:");
      System.out.println("------------------------------------------------");
      String nickname = br.readLine();
      System.out.println("------------------------------------------------");
      System.out.println("Enter Patient DateOfBirth: (yyyy/MM/dd)");
      System.out.println("------------------------------------------------");
      String sDateOfBirth = br.readLine();
      //Ask length
      /*
      System.out.println("------------------------------------------------");
      System.out.println("Enter Patient Length:");
      System.out.println("------------------------------------------------");
      double length = Double.parseDouble(br.readLine());
      System.out.println("------------------------------------------------");
      System.out.println("Enter Patient Weight:");
      System.out.println("------------------------------------------------");
      double weight = Double.parseDouble(br.readLine());
       */
      //Ask Weight
      //Put the values in the Patient object



      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
      LocalDate DateOfBirth = LocalDate.parse(sDateOfBirth, formatter);
      //Drop this
      int age = aC.calculateAge(DateOfBirth);
      //after user enters values, store them in a Product variable
      Patient patient = new Patient(patientId, surName,firstName, nickname, DateOfBirth, age);
      int status = dao.addPatient(patient);
      if(status ==1 )
      {
         System.out.println("Patient added successfully");
      }
      else
      {
         System.out.println("ERROR while adding patient");
      }
      System.out.println("\n");
   }

   //this method asks practitioner to enter the ID and change attributes
   public static void updatePatient() throws Exception
   {

      //Add a function that let certain values stay the same
      System.out.println("------------------------------------------------");
      System.out.println("Enter Patient ID:");
      System.out.println("------------------------------------------------");
      String patientId = br.readLine();
      System.out.println("------------------------------------------------");
      System.out.println("Enter New Patient Surname:");
      System.out.println("------------------------------------------------");
      String surName = br.readLine();
      System.out.println("------------------------------------------------");
      System.out.println("Enter New Patient First Name:");
      System.out.println("------------------------------------------------");
      String firstName = br.readLine();
      System.out.println("------------------------------------------------");
      System.out.println("Enter New Patient Nickname:");
      System.out.println("------------------------------------------------");
      String nickname = br.readLine();
      System.out.println("------------------------------------------------");
      System.out.println("Enter New Patient DateOfBirth: (yyyy/MM/dd)");
      System.out.println("------------------------------------------------");
      String sDateOfBirth = br.readLine();
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
      LocalDate DateOfBirth = LocalDate.parse(sDateOfBirth, formatter);
      //Length and Weight updater
      /*
      System.out.println("------------------------------------------------");
      System.out.println("Enter new Patient Length:");
      System.out.println("------------------------------------------------");
      double length = Double.parseDouble(br.readLine());
      System.out.println("------------------------------------------------");
      System.out.println("Enter new Patient Weight:");
      System.out.println("------------------------------------------------");
      double weight = Double.parseDouble(br.readLine());
       */
      //Drop this
      int age = aC.calculateAge(DateOfBirth);
      //after practitioner enters values, store them in a Patient variable
      //Still have to put length and weight in here
      /*length, weight*/
      Patient patient = new Patient(patientId, surName,firstName, nickname, DateOfBirth, age);
      int status = dao.updatePatient(patient);
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

   public static void deletePatient() throws Exception
   {
      System.out.println("------------------------------------------------");
      System.out.println("Enter Patient ID:");
      System.out.println("------------------------------------------------");
      String patientId = br.readLine();
      int status = dao.deletePatient(patientId);
      if(status == 1 )
      {
         System.out.println("Patient deleted successfully");
      }
      else
      {
         System.out.println("ERROR while deleting patient");
      }
      System.out.println("\n");

   }

   //ask user for productId and use dao method to display product
   public static void searchPatient() throws Exception
   {
      System.out.println("------------------------------------------------");
      System.out.println("Enter Patient ID:");
      System.out.println("------------------------------------------------");
      String patientId = br.readLine();
      Patient patient = dao.getPatientByid(patientId);
      displayPatient(patient);
      System.out.println("\n");
   }

   public static void displayPatient(Patient patient)
   {
      System.out.println("Patient ID: "+patient.getPatientId());
      System.out.println("Patient Surname: "+patient.getSurName());
      System.out.println("Patient Firstname: "+patient.getFirstName());
      System.out.println("Patient Nickname: "+patient.getNickname());
      System.out.println("Patient DateOfBirth: "+patient.getDateOfBirth());
      //Maybe let the age be calculated here
      System.out.println("Patient Age: "+patient.getAge());
      //Add length, Weight and BMI
      /*
      System.out.println("Patient Length: "+patient.getLength());
      System.out.println("Patient Length: "+patient.getWeight());
      Calculate BMI here and print it
      Still have to make BMI calculator
       */
      System.out.println("\n");
   }



}
