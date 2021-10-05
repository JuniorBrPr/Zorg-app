import calculators.ageCalculator;
import calculators.bmiCalculator;
import org.jetbrains.annotations.NotNull;
import pojo.Medication;
import pojo.Patient;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.format.DateTimeFormatter;
import java.util.List;
import dao.PatientManagementDAO;
import dao.medicationManagementDAO;
import java.time.LocalDate;

import static java.lang.Integer.parseInt;


public class Admin {
   static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
   static PatientManagementDAO dao = new PatientManagementDAO();
   static medicationManagementDAO medDao = new medicationManagementDAO();
   static ageCalculator aC = new ageCalculator();
   static bmiCalculator bC = new bmiCalculator();
   //This method shows and gives all the options the Admin has
   void menu() throws Exception {
      String option;
      do {
         System.out.println("      Welcome Admin    ");
         System.out.println("<><><><><><><><><><><><>");
         System.out.println("A. View Patients");
         System.out.println("B. Add Patient");
         System.out.println("C. Update Patient");
         System.out.println("D. Delete Patient");
         System.out.println("E. Search Patient");
         System.out.println("-----------------");
         System.out.println("G. Prescribe Medication to Patient");
         System.out.println("H. View Patients and their Medication(s)");
         System.out.println("I. Delete Medication");
         System.out.println("J. Update Medication");
         System.out.println("F. Exit");
         System.out.println("<><><><><><><><><><><><>");
         System.out.println("Enter an option");
         System.out.println("<><><><><><><><><><><><>");
         option = br.readLine();
         System.out.println("\n");
         //Admin options
         switch (option.toUpperCase()) {
            case "A" -> viewPatients();
            case "B" -> addPatient();
            case "C" -> updatePatient();
            case "D" -> deletePatient();
            case "E" -> searchPatient();
            case "G" -> addMedication();
            case "H" -> viewPatientsAndMeds();
            case "I" -> deleteMedication();
            case "J" -> updateMedication();
            case "F" -> {
               System.out.println("Goodbye!");
               System.exit(0);
            }
            default -> System.out.println("Invalid Option! Please try again!!");
         }
      }while(!option.equals("F"));
   }
   //This method prints a list with all the patients and their credentials, except for their prescribed medication.
   public static void viewPatients() {
      System.out.println("-----------------------------------------------");
      //Get all the patients from the DAO and store them
      List<Patient> patientList = dao.getAllPatients();
      for(Patient patient: patientList)
      {
         //display patient one by one
         displayPatient(patient);
      }
      System.out.println("-----------------------------------------------");
      System.out.println("\n");
   }
   //This method prints all the ID's and names of patients in the DB
   //Mainly made to make it easier for the Admin to find a patient while updating, deleting etc. a patient
   public static void viewPatientIds() {
      System.out.println("-----------------------------------------------");
      //Get all the patients from the DAO and store them
      List<Patient> patientIdList = dao.getAllPatients();
      for(Patient patient: patientIdList) {
         displayPatientId(patient);
      }
      System.out.println("-----------------------------------------------");
      System.out.println("\n");
   }
   //Same as viewPatients method, but this method also prints the medication Data for every patient
   public static void viewPatientsAndMeds() {
      System.out.println("-----------------------------------------------");
      //Get all the patients from the DAO and store them
      List<Patient> patientList = dao.getAllPatients();
      for(Patient patient: patientList) {
         //display patient and their medication one by one
         displayPatient(patient);
         viewMedications(patient);
      }
      System.out.println("-----------------------------------------------");
      System.out.println("\n");
   }
   //This method adds a new Patient to the database. The admin has to insert the credentials.
   //I could maybe a function that lets certain values be null, if the admin doesn't know them.
   public static void addPatient() throws Exception {
      //patientId gets set on the server side, this value is a placeholder
      int patientId = 0;
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
      System.out.println("Enter Patient DateOfBirth: (yyyy-MM-dd)");
      System.out.println("------------------------------------------------");
      String sDateOfBirth = br.readLine();
      System.out.println("------------------------------------------------");
      System.out.println("Enter Patient Length (In CM):");
      System.out.println("------------------------------------------------");
      double length = Double.parseDouble(br.readLine());
      System.out.println("------------------------------------------------");
      System.out.println("Enter Patient Weight (In KG's):");
      System.out.println("------------------------------------------------");
      double weight = Double.parseDouble(br.readLine());
      //Here the DOB gets formatted the correct way in order to store it in the DB and to later be able to calculate the age.
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      LocalDate DateOfBirth = LocalDate.parse(sDateOfBirth, formatter);
      //After admin enters values, store them in a Patient variable
      Patient patient = new Patient(patientId, surName,firstName, nickname, DateOfBirth, length, weight);
      //If the patient gets uploaded to the DB, status should return 1
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
   //This method lets the admin 'prescribe' a medication to a patient by ID
   public static void addMedication() throws Exception
   {
      viewPatientIds();
      System.out.println("------------------------------------------------");
      System.out.println("Enter PatientId");
      System.out.println("------------------------------------------------");
      int patientId = parseInt(br.readLine());
      System.out.println("------------------------------------------------");
      System.out.println("Enter Medication name:");
      System.out.println("------------------------------------------------");
      String medName = br.readLine();
      System.out.println("------------------------------------------------");
      System.out.println("Enter medication dosage:");
      System.out.println("------------------------------------------------");
      String dosage = br.readLine();
      System.out.println("------------------------------------------------");
      System.out.println("Enter medication manufacturer:");
      System.out.println("------------------------------------------------");
      String manufacturer = br.readLine();
      System.out.println("------------------------------------------------");
      System.out.println("Enter medication type:");
      System.out.println("------------------------------------------------");
      String medType = br.readLine();
      //after user enters values, store them in a Medication variable
      int medId = 0;
      Medication medication = new Medication(medId, medName, dosage,manufacturer, patientId, medType);
      //If the medication gets uploaded to the DB, status should return 1
      int status = medDao.addMed(medication);
      if(status ==1 )
      {
         System.out.println("Medication added successfully");
      }
      else
      {
         System.out.println("ERROR while adding medication");
      }
      System.out.println("\n");
   }
   //This method asks the admin if he wants to change a certain attribute value or retain it's original value, while updating patients or meds.
   public static String attributeChanger(String originalValue, @NotNull String option, String attributeName) throws IOException {
      String value = "";
      System.out.println("Enter A to keep current "+attributeName);
      System.out.println("Enter B to change "+attributeName);
      System.out.println("------------------------------------------------");
      switch (option.toUpperCase()) {
         case "A" -> value = originalValue;
         case "B" -> {
            System.out.println("Enter new " + attributeName + ":");
            value = br.readLine();
         }
         default -> System.out.println("Invalid Option! Please try again!!");
      }
      return value;
   }
   //This method helps the admin change attribute values.
   public static void updatePatient() throws Exception
   {
      //Prints a list with all the patients in the DB and their ID's and names
      viewPatientIds();
      System.out.println("------------------------------------------------");
      System.out.println("Enter the ID of the Patient you would like to update:");
      System.out.println("------------------------------------------------");
      int patientId = parseInt(br.readLine());
      //Store original patient attributes
      Patient patientOriginalData = dao.getPatientByid(patientId);

      System.out.println("------------------------------------------------");
      System.out.println("Current Patient Surname: "+ patientOriginalData.getSurName());
      System.out.println("------------------------------------------------");
      String option = br.readLine();
      String surName = attributeChanger(patientOriginalData.getSurName(), option, "Surname");

      System.out.println("------------------------------------------------");
      System.out.println("Current Patient First Name: "+patientOriginalData.getFirstName());
      System.out.println("------------------------------------------------");
      option = br.readLine();
      String firstName = attributeChanger(patientOriginalData.getFirstName(), option, "First Name");

      System.out.println("------------------------------------------------");
      System.out.println("Current Patient Nickname: "+patientOriginalData.getNickname());
      System.out.println("------------------------------------------------");
      option = br.readLine();
      String nickname = attributeChanger(patientOriginalData.getNickname(), option, "Nickname");

      System.out.println("------------------------------------------------");
      System.out.println("Current Patient DateOfBirth (yyyy-MM-dd): "+patientOriginalData.getDateOfBirth());
      System.out.println("------------------------------------------------");
      option = br.readLine();
      String sDateOfBirth = attributeChanger(String.valueOf(patientOriginalData.getDateOfBirth()), option, "DateOfBirth: (yyyy-MM-dd)");
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      LocalDate DateOfBirth = LocalDate.parse(sDateOfBirth, formatter);

      System.out.println("------------------------------------------------");
      System.out.println("Current Patient Length: "+patientOriginalData.getLength());
      System.out.println("------------------------------------------------");
      option = br.readLine();
      double length = Double.parseDouble(attributeChanger(String.valueOf(patientOriginalData.getLength()), option, "Length"));

      System.out.println("------------------------------------------------");
      System.out.println("Current Patient Weight: "+patientOriginalData.getWeight());
      System.out.println("------------------------------------------------");
      option = br.readLine();
      double weight = Double.parseDouble(attributeChanger(String.valueOf(patientOriginalData.getWeight()), option, "Weight"));

      //after practitioner enters values, store them in a Patient variable
      Patient patient = new Patient(patientId, surName,firstName, nickname, DateOfBirth, length, weight);
      //If the patient gets updated in the DB, status should return 1
      int status = dao.updatePatient(patient);
      if(status ==1 ) {
         System.out.println("Patient updated successfully");
      }
      else {
         System.out.println("ERROR while updating patient");
      }
      System.out.println("\n");
   }
   //Basically the same as the updatePatient method, except with this method you can update individual Medicine prescriptions.
   //This method helps the admin update Medication attribute values
   public static void updateMedication() throws Exception {
      //First the admin has to select the Patient
      viewPatientIds();
      System.out.println("------------------------------------------------");
      System.out.println("Enter Patient ID:");
      System.out.println("------------------------------------------------");
      int patientId = parseInt(br.readLine());

      //View patient medList
      //Then the admin has to select which Medicine he would like to update
      viewMedications(dao.getPatientByid(patientId));
      System.out.println("------------------------------------------------");
      System.out.println("Enter the Medication ID of the Medication you would like to update:");
      System.out.println("------------------------------------------------");
      int medId = parseInt(br.readLine());
      Medication originalMedicationData = medDao.getMedicationByMedId(medId);

      //Ask the admin if he wants to retain or change the values
      System.out.println("------------------------------------------------");
      System.out.println("Current Medication Name: "+ originalMedicationData.getMedName());
      System.out.println("------------------------------------------------");
      String option = br.readLine();
      String medName = attributeChanger(originalMedicationData.getMedName(), option, "Medication Name");

      System.out.println("------------------------------------------------");
      System.out.println("Current Medication Dosage: "+ originalMedicationData.getDosage());
      System.out.println("------------------------------------------------");
      option = br.readLine();
      String dosage = attributeChanger(originalMedicationData.getDosage(), option, "Medication Dosage");

      System.out.println("------------------------------------------------");
      System.out.println("Current Medication Manufacturer: "+ originalMedicationData.getManufacturer());
      System.out.println("------------------------------------------------");
      option = br.readLine();
      String manufacturer = attributeChanger(originalMedicationData.getManufacturer(), option, "Medication Manufacturer");

      System.out.println("------------------------------------------------");
      System.out.println("Current Medication Type: "+ originalMedicationData.getMedType());
      System.out.println("------------------------------------------------");
      option = br.readLine();
      String medType = attributeChanger(originalMedicationData.getMedType(), option, "Medication Type");

      //Store values and send to DB
      Medication medication = new Medication(medId, medName, dosage, manufacturer, patientId, medType);
      //If the Medication gets updated, status should return 1
      int status = medDao.updateMedication(medication);
      if(status ==1 ) {
         System.out.println("Medication updated successfully");
      }
      else {
         System.out.println("ERROR while updating medication");
      }
      System.out.println("\n");
   }
   //This method helps the admin delete a patient from the DB by patientId
   public static void deletePatient() throws Exception {
      viewPatientIds();
      System.out.println("------------------------------------------------");
      System.out.println("Enter the ID of the Patient you would like to delete:");
      System.out.println("------------------------------------------------");
      //If the patient gets deleted form the DB status should return 1
      int patientId = parseInt(br.readLine());
      int status = dao.deletePatient(patientId);
      if(status == 1 ) {
         System.out.println("Patient deleted successfully");
      }
      else {
         System.out.println("ERROR while deleting patient");
      }
      System.out.println("\n");
   }
   //This method helps the admin delete a medication from the DB by medId
   public static void deleteMedication() throws Exception {
      //First select the patient whose medication the Admin would to delete
      viewPatientIds();
      System.out.println("------------------------------------------------");
      System.out.println("Enter Patient ID who's Medication(s) data you would like to delete:");
      System.out.println("------------------------------------------------");
      int patientId = parseInt(br.readLine());
      //View patient medList
      viewMedications(dao.getPatientByid(patientId));
      System.out.println("------------------------------------------------");
      System.out.println("Enter the Medication ID of the Medication you would like to delete:");
      System.out.println("------------------------------------------------");
      int medId = parseInt(br.readLine());
      int status = medDao.deleteMedication(medId);
      //If the medication gets deleted from the DB status should return 1
      if(status == 1 ) {
         System.out.println("Medication deleted successfully");
      }
      else {
         System.out.println("ERROR while deleting Medication");
      }
      System.out.println("\n");
   }
   //This method helps the admin lookup the credentials and medication of a Patient by patientId
   public static void searchPatient() throws Exception {
      viewPatientIds();
      System.out.println("------------------------------------------------");
      System.out.println("Enter the ID of the Patient you would like to search:");
      System.out.println("------------------------------------------------");
      int patientId = parseInt(br.readLine());
      Patient patient = dao.getPatientByid(patientId);
      displayPatient(patient);
      viewMedications(patient);
      System.out.println("\n");
   }
   //This method is used for the viewPatients methods
   //It just prints out the credentials of a patient
   public static void displayPatient(Patient patient) {
      int age = aC.calculateAge(patient.getDateOfBirth());
      double bmi = bC.calculateBMI(patient.getLength(), patient.getWeight());
      String bmiCategory = bC.bmiCategory(bmi);
      System.out.println("-----------------------------------------------");
      System.out.println("Patient credentials= ");
      System.out.println("Patient ID: "+patient.getPatientId());
      System.out.println("Patient Surname: "+patient.getSurName());
      System.out.println("Patient Firstname: "+patient.getFirstName());
      System.out.println("Patient Nickname: "+patient.getNickname());
      System.out.println("Patient DateOfBirth: "+patient.getDateOfBirth());
      System.out.println("Patient Age: "+age);
      System.out.println("Patient Length: "+patient.getLength());
      System.out.println("Patient Weight: "+patient.getWeight());
      System.out.println("Patient BMI: "+bmi);
      System.out.println("Patient BMI Category: "+bmiCategory);
      System.out.println("\n");
   }
   //This method is used for the viewPatientIds
   //Prints out the patientId, surname and firstname
   public static void displayPatientId(Patient patient) {
      System.out.println("Patient ID: "+patient.getPatientId() +", Surname: "+patient.getSurName()+", Firstname: "+patient.getFirstName());
      System.out.println("-----------------------------------------------");
   }
   //This method gets used in the viewMedications
   //This method displays the medication attribute values
   public static void displayMedication(Medication medication){
      System.out.println("Medication ID: "+medication.getMedId());
      System.out.println("Medication name: "+medication.getMedName());
      System.out.println("Medication dosage: "+medication.getDosage());
      System.out.println("Medication type: "+medication.getMedType());
      System.out.println("Medication manufacturer: "+medication.getManufacturer());
   }
   //This method gets used in the searchPatient method and the viewPatientsAndMeds method
   //This method prints a list of the medications a given patient has
   public static void viewMedications(Patient patient){
      System.out.println("Patient prescription(s)=");
      List<Medication> medicationList = medDao.getAllMeds(patient.getPatientId());
      for(Medication medication: medicationList)
      {
         //display medication one by one
         displayMedication(medication);
         System.out.println("\n");
      }
      System.out.println("-----------------------------------------------");
   }

}
