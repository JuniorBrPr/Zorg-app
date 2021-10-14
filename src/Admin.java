import calculators.ageCalculator;
import calculators.bmiCalculator;

import dao.weightManagementDAO;
import dbutil.DBUtil;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.jdbc.JDBCCategoryDataset;
import pojo.Medication;
import pojo.Patient;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import dao.PatientManagementDAO;
import dao.medicationManagementDAO;
import pojo.Weight;


import javax.swing.*;
import java.time.LocalDate;

import static java.lang.Integer.parseInt;


public class Admin {
   BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
   PatientManagementDAO dao = new PatientManagementDAO();
   medicationManagementDAO medDao = new medicationManagementDAO();
   weightManagementDAO weightDao = new weightManagementDAO();
   ageCalculator aC = new ageCalculator();
   bmiCalculator bC = new bmiCalculator();
   CheckPatientId checkPatientId = new CheckPatientId();
   DBUtil db = new DBUtil();
   String option;
   //This method shows and gives all the options the Admin has
   void menu() throws Exception {
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
         System.out.println("-----------------");
         System.out.println("K. Add Patient Weight Data");
         System.out.println("L. Update Patient Weight Data");
         System.out.println("M. Delete Patient Weight Data");
         System.out.println("N. View Patients and their Weight Data");
         System.out.println("-----------------");
         System.out.println("O. View Patients and All their Data");
         System.out.println("P. View Patient weight Graph");
         System.out.println("-----------------");
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
            case "K" -> addWeight();
            case "L" -> updateWeight();
            case "M" -> deleteWeight();
            case "N" -> viewPatientsAndWeightData();
            case "O" -> viewPatientsAndAllData();
            case "P" -> viewPatientWeightGraph();
            case "F" -> {
               System.out.println("Goodbye!");
               System.exit(0);
            }
            default -> System.out.println("Invalid Option! Please try again!!");
         }
      }while(!option.equals("F"));
   }
   /*
   try{
         if(checkPatientId.check(patientId)){

         } else {
         System.out.println("\n");
         System.out.println("\n");
         System.out.println("Invalid PatientID");
         System.out.println("\n");
         System.out.println("\n");
      }
      }catch (NumberFormatException ex) {
         System.out.println("---------------------");
         System.out.println("Invalid input");
         System.out.println("---------------------");
      }
    */
   public void viewPatientWeightGraph() throws IOException, SQLException {
      viewPatientIds();
      System.out.println("------------------------------------------------");
      System.out.println("Enter PatientId");
      System.out.println("------------------------------------------------");
      try{
         int patientId = parseInt(br.readLine());
         if(checkPatientId.check(patientId)){
            weightGraph(patientId);
            viewWeightData(dao.getPatientByid(patientId));
         } else {
            System.out.println("\n");
            System.out.println("\n");
            System.out.println("Invalid PatientID");
            System.out.println("\n");
            System.out.println("\n");
         }
      }catch (NumberFormatException ex) {
         System.out.println("---------------------");
         System.out.println("Invalid input");
         System.out.println("---------------------");
      }
   }
   public void weightGraph(int patientId) {
      try{
         //Ask the user to enter patientId
         //Check if ID is in the DB
         String patient = String.valueOf(patientId);
         String query = "SELECT weightDate,weight FROM weight WHERE patientId="+patient+" ORDER BY weight.weightDate ASC ;";
         JDBCCategoryDataset dataset = new JDBCCategoryDataset(db.getConnection(), query);
         JFreeChart chart = ChartFactory.createLineChart("Weight Chart", "Date","Weight", dataset, PlotOrientation.VERTICAL, false, true, true);
         ChartFrame frame = new ChartFrame("Weight Chart", chart);
         frame.setVisible(true);
         frame.setSize(400,600);
      }catch (Exception e) {
         JOptionPane.showMessageDialog(null, e);
      }
   }
   //This method prints a list with all the patients and their credentials, except for their prescribed medication.
   public void viewPatients() {
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
   public void viewPatientIds() {
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
   public void viewPatientsAndMeds() {
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
   public void addPatient() throws Exception {
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
   public void addMedication() throws Exception
   {
      viewPatientIds();
      System.out.println("------------------------------------------------");
      System.out.println("Enter PatientId");
      System.out.println("------------------------------------------------");
      try{
         int patientId = parseInt(br.readLine());
         if(checkPatientId.check(patientId)){
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
         } else {
            System.out.println("\n");
            System.out.println("\n");
            System.out.println("Invalid PatientID");
            System.out.println("\n");
         }
         System.out.println("\n");
      }catch (NumberFormatException ex) {
         System.out.println("---------------------");
         System.out.println("Invalid input");
         System.out.println("---------------------");
      }
   }
   //This method asks the admin if he wants to change a certain attribute value or retain it's original value, while updating patients or meds.
   public String attributeChanger(String originalValue, String attributeName) throws IOException {
      String value = "";
      System.out.println("Enter A to keep current "+attributeName);
      System.out.println("Enter B to change "+attributeName);
      System.out.println("------------------------------------------------");
      option = br.readLine();
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
   public void updatePatient() throws Exception
   {
      //Prints a list with all the patients in the DB and their ID's and names
      viewPatientIds();
      System.out.println("------------------------------------------------");
      System.out.println("Enter the ID of the Patient you would like to update:");
      System.out.println("------------------------------------------------");
      try{
         int patientId = parseInt(br.readLine());
         if(checkPatientId.check(patientId)){
            Patient patientOriginalData = dao.getPatientByid(patientId);

            System.out.println("------------------------------------------------");
            System.out.println("Current Patient Surname: "+ patientOriginalData.getSurName());
            System.out.println("------------------------------------------------");
            String surName = attributeChanger(patientOriginalData.getSurName(), "Surname");

            System.out.println("------------------------------------------------");
            System.out.println("Current Patient First Name: "+patientOriginalData.getFirstName());
            System.out.println("------------------------------------------------");
            String firstName = attributeChanger(patientOriginalData.getFirstName(), "First Name");
            //Call nickname updating method
            String nickname = updateNickname(dao.getPatientByid(patientId), patientOriginalData);

            System.out.println("------------------------------------------------");
            System.out.println("Current Patient DateOfBirth (yyyy-MM-dd): "+patientOriginalData.getDateOfBirth());
            System.out.println("------------------------------------------------");
            String sDateOfBirth = attributeChanger(String.valueOf(patientOriginalData.getDateOfBirth()), "DateOfBirth: (yyyy-MM-dd)");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate DateOfBirth = LocalDate.parse(sDateOfBirth, formatter);

            System.out.println("------------------------------------------------");
            System.out.println("Current Patient Length: "+patientOriginalData.getLength());
            System.out.println("------------------------------------------------");
            double length = Double.parseDouble(attributeChanger(String.valueOf(patientOriginalData.getLength()), "Length"));

            System.out.println("------------------------------------------------");
            System.out.println("Current Patient Weight: "+patientOriginalData.getWeight());
            System.out.println("------------------------------------------------");
            double weight = Double.parseDouble(attributeChanger(String.valueOf(patientOriginalData.getWeight()),"Weight"));

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
         } else {
            System.out.println("\n");
            System.out.println("\n");
            System.out.println("Invalid PatientID");
            System.out.println("\n");
         }
         System.out.println("\n");
      }catch (NumberFormatException ex) {
         System.out.println("---------------------");
         System.out.println("Invalid input");
         System.out.println("---------------------");
      }
   }
   //Basically the same as the updatePatient method, except with this method you can update individual Medicine prescriptions.
   //This method helps the admin update Medication attribute values
   public void updateMedication() throws Exception {
      //First the admin has to select the Patient
      viewPatientIds();
      System.out.println("------------------------------------------------");
      System.out.println("Enter Patient ID:");
      System.out.println("------------------------------------------------");
      try{
         int patientId = parseInt(br.readLine());
         if(checkPatientId.check(patientId)){
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
            String medName = attributeChanger(originalMedicationData.getMedName(),"Medication Name");

            System.out.println("------------------------------------------------");
            System.out.println("Current Medication Dosage: "+ originalMedicationData.getDosage());
            System.out.println("------------------------------------------------");
            String dosage = attributeChanger(originalMedicationData.getDosage(),"Medication Dosage");

            System.out.println("------------------------------------------------");
            System.out.println("Current Medication Manufacturer: "+ originalMedicationData.getManufacturer());
            System.out.println("------------------------------------------------");
            String manufacturer = attributeChanger(originalMedicationData.getManufacturer(),"Medication Manufacturer");

            System.out.println("------------------------------------------------");
            System.out.println("Current Medication Type: "+ originalMedicationData.getMedType());
            System.out.println("------------------------------------------------");
            String medType = attributeChanger(originalMedicationData.getMedType(),"Medication Type");

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
         } else {
            System.out.println("\n");
            System.out.println("\n");
            System.out.println("Invalid PatientID");
            System.out.println("\n");
            System.out.println("\n");
         }
      }catch (NumberFormatException ex) {
         System.out.println("---------------------");
         System.out.println("Invalid input");
         System.out.println("---------------------");
      }
   }
   //This method helps the admin delete a patient from the DB by patientId
   public void deletePatient() throws Exception {
      viewPatientIds();
      System.out.println("------------------------------------------------");
      System.out.println("Enter the ID of the Patient you would like to delete:");
      System.out.println("------------------------------------------------");
      //If the patient gets deleted form the DB status should return 1
      try{
         int patientId = parseInt(br.readLine());
         if(checkPatientId.check(patientId)){
            int status = dao.deletePatient(patientId);
            if(status == 1 ) {
               System.out.println("Patient deleted successfully");
            }
            else {
               System.out.println("ERROR while deleting patient");
            }
         } else {
            System.out.println("\n");
            System.out.println("\n");
            System.out.println("Invalid PatientID");
            System.out.println("\n");
            System.out.println("\n");
         }
      }catch (NumberFormatException ex) {
         System.out.println("---------------------");
         System.out.println("Invalid input");
         System.out.println("---------------------");
      }
   }
   //This method helps the admin delete a medication from the DB by medId
   public void deleteMedication() throws Exception {
      //First select the patient whose medication the Admin would to delete
      viewPatientIds();
      System.out.println("------------------------------------------------");
      System.out.println("Enter Patient ID who's Medication(s) data you would like to delete:");
      System.out.println("------------------------------------------------");
      try{
         int patientId = parseInt(br.readLine());
         if(checkPatientId.check(patientId)){
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
         } else {
            System.out.println("\n");
            System.out.println("\n");
            System.out.println("Invalid PatientID");
            System.out.println("\n");
         }
         System.out.println("\n");
      }catch (NumberFormatException ex) {
         System.out.println("---------------------");
         System.out.println("Invalid input");
         System.out.println("---------------------");
      }
   }
   //This method helps the admin lookup the credentials and medication of a Patient by patientId
   public void searchPatient() throws Exception {
      viewPatientIds();
      System.out.println("------------------------------------------------");
      System.out.println("Enter the ID of the Patient you would like to search:");
      System.out.println("------------------------------------------------");
      try{
         int patientId = parseInt(br.readLine());
         if(checkPatientId.check(patientId)){
            Patient patient = dao.getPatientByid(patientId);
            displayPatient(patient);
            viewMedications(patient);
            viewWeightData(patient);
         } else {
            System.out.println("\n");
            System.out.println("\n");
            System.out.println("Invalid PatientID");
            System.out.println("\n");
         }
         System.out.println("\n");
      }catch (NumberFormatException ex) {
         System.out.println("---------------------");
         System.out.println("Invalid input");
         System.out.println("---------------------");
      }
   }
   //This method is used for the viewPatients methods
   //It just prints out the credentials of a patient
   public void displayPatient(Patient patient) {
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
   public void displayPatientId(Patient patient) {
      System.out.println("Patient ID: "+patient.getPatientId() +", Surname: "+patient.getSurName()+", Firstname: "+patient.getFirstName());
      System.out.println("-----------------------------------------------");
   }
   //This method gets used in the viewMedications
   //This method displays the medication attribute values
   public void displayMedication(Medication medication){
      System.out.println("Medication ID: "+medication.getMedId());
      System.out.println("Medication name: "+medication.getMedName());
      System.out.println("Medication dosage: "+medication.getDosage());
      System.out.println("Medication type: "+medication.getMedType());
      System.out.println("Medication manufacturer: "+medication.getManufacturer());
   }
   //This method gets used in the searchPatient method and the viewPatientsAndMeds method
   //This method prints a list of the medications a given patient has
   public void viewMedications(Patient patient){
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

   public String updateNickname(Patient patient, Patient patientOriginalData) throws IOException {
      System.out.println("------------------------------------------------");
      System.out.println("Current Patient Nickname: "+patientOriginalData.getNickname());
      System.out.println("------------------------------------------------");
      String nickname = attributeChanger(patientOriginalData.getNickname(),"Nickname");
      patient.setNickname(nickname);
      int status = dao.updateNickname(patient);
      if(status ==1 ) {
         System.out.println("Nickname updated successfully");
      }
      else {
         System.out.println("ERROR while updating Nickname");
      }
      System.out.println("\n");
      return nickname;
   }
   //Add, update, search and delete weight functions down here
   public void addWeight() throws Exception
   {
      viewPatientIds();
      System.out.println("------------------------------------------------");
      System.out.println("Enter PatientId");
      System.out.println("------------------------------------------------");
      try{
         int patientId = parseInt(br.readLine());
         if(checkPatientId.check(patientId)){
            System.out.println("------------------------------------------------");
            System.out.println("Enter Patient Weight:");
            System.out.println("------------------------------------------------");
            double weightTemp = Double.parseDouble(br.readLine());
            System.out.println("------------------------------------------------");
            System.out.println("Enter Date of Weighing (YYYY-MM-DD):");
            System.out.println("------------------------------------------------");
            String weightDateTemp = br.readLine();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate weightDate = LocalDate.parse(weightDateTemp, formatter);
            //after user enters values, store them in a Medication variable
            int weightId = 0;
            Weight weight = new Weight(weightId, weightTemp, weightDate, patientId);
            //If the medication gets uploaded to the DB, status should return 1
            int status = weightDao.addWeight(weight);
            if(status ==1 )
            {
               System.out.println("Weight data added successfully");
            }
            else
            {
               System.out.println("ERROR while adding weight Data");
            }
         } else {
            System.out.println("\n");
            System.out.println("\n");
            System.out.println("Invalid PatientID");
            System.out.println("\n");
         }
         System.out.println("\n");
      }catch (NumberFormatException ex) {
         System.out.println("---------------------");
         System.out.println("Invalid input");
         System.out.println("---------------------");
      }
   }
   public void displayWeightData(Weight weight){
      System.out.println("Weight Data ID: "+weight.getWeightId());
      System.out.println("Weight (kg's): "+weight.getWeight());
      System.out.println("Weighing Date: "+weight.getWeightDate());
   }
   public void viewWeightData(Patient patient){
      System.out.println("Patient Weight Data= ");
      List<Weight> weightList = weightDao.getAllWeightData(patient.getPatientId());
      for(Weight weight: weightList)
      {
         //display medication one by one
         displayWeightData(weight);
         System.out.println("\n");
      }
      System.out.println("-----------------------------------------------");
   }
   public void viewPatientsAndWeightData() {
      System.out.println("-----------------------------------------------");
      //Get all the patients from the DAO and store them
      List<Patient> patientList = dao.getAllPatients();
      for(Patient patient: patientList) {
         //display patient and their medication one by one
         displayPatient(patient);
         viewWeightData(patient);
      }
      System.out.println("-----------------------------------------------");
      System.out.println("\n");
   }
   public void viewPatientsAndAllData() {
      System.out.println("-----------------------------------------------");
      //Get all the patients from the DAO and store them
      List<Patient> patientList = dao.getAllPatients();
      for(Patient patient: patientList) {
         //display patient and their medication one by one
         displayPatient(patient);
         viewMedications(patient);
         viewWeightData(patient);
         System.out.println("><><><><><><><><><><><><><><><><><><><><><><><><");
      }
      System.out.println("-----------------------------------------------");
      System.out.println("\n");
   }

   public void deleteWeight() throws Exception {
      //First select the patient whose weight data the Admin would to delete
      viewPatientIds();
      System.out.println("------------------------------------------------");
      System.out.println("Enter Patient ID who's Weight data you would like to delete:");
      System.out.println("------------------------------------------------");
      try{
         int patientId = parseInt(br.readLine());
         if(checkPatientId.check(patientId)){
            //View patient Weight Data list
            viewWeightData(dao.getPatientByid(patientId));
            System.out.println("------------------------------------------------");
            System.out.println("Enter the Weight ID of the Weight Data you would like to delete:");
            System.out.println("------------------------------------------------");
            int weightId = parseInt(br.readLine());
            int status = weightDao.deleteWeight(weightId);
            //If the weight data gets deleted from the DB status should return 1
            if(status == 1 ) {
               System.out.println("Medication deleted successfully");
            }
            else {
               System.out.println("ERROR while deleting Medication");
            }
         } else {
            System.out.println("\n");
            System.out.println("\n");
            System.out.println("Invalid PatientID");
            System.out.println("\n");
         }
         System.out.println("\n");
      }catch (NumberFormatException ex) {
         System.out.println("---------------------");
         System.out.println("Invalid input");
         System.out.println("---------------------");
      }
   }
   @SuppressWarnings("WrapperTypeMayBePrimitive")
   public void updateWeight() throws Exception {
      //First the admin has to select the Patient
      viewPatientIds();
      System.out.println("------------------------------------------------");
      System.out.println("Enter Patient ID:");
      System.out.println("------------------------------------------------");
      try{
         int patientId = parseInt(br.readLine());
         if(checkPatientId.check(patientId)){
            //View patient weight data
            //Then the admin has to select which Weight data he would like to update
            viewWeightData(dao.getPatientByid(patientId));
            System.out.println("------------------------------------------------");
            System.out.println("Enter the Weight ID of the Weight Data you would like to update:");
            System.out.println("------------------------------------------------");
            int weightId = parseInt(br.readLine());
            Weight originalWeightData = weightDao.getWeightByWeightId(weightId);

            //Ask the admin if he wants to retain or change the values
            System.out.println("------------------------------------------------");
            System.out.println("Current Weight: "+ originalWeightData.getWeight());
            System.out.println("------------------------------------------------");
            Double weightTemp = Double.parseDouble(attributeChanger(String.valueOf(originalWeightData.getWeight()),"Weight (kg's)"));

            System.out.println("------------------------------------------------");
            System.out.println("Current Weighing Date: "+ originalWeightData.getWeightDate());
            System.out.println("------------------------------------------------");
            String weightDateTemp = attributeChanger(String.valueOf(originalWeightData.getWeightDate()),"Weighing Date");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate weightDate = LocalDate.parse(weightDateTemp, formatter);

            //Store values and send to DB

            Weight weight = new Weight(weightId,weightTemp,weightDate, patientId);
            //If the Medication gets updated, status should return 1
            int status = weightDao.updateWeightData(weight);
            if(status ==1 ) {
               System.out.println("Weight Data updated successfully");
            }
            else {
               System.out.println("ERROR while updating Weight Data!");
            }
         } else {
            System.out.println("\n");
            System.out.println("\n");
            System.out.println("Invalid PatientID");
            System.out.println("\n");
         }
         System.out.println("\n");
      }catch (NumberFormatException ex) {
         System.out.println("---------------------");
         System.out.println("Invalid input");
         System.out.println("---------------------");
      }
   }

}
