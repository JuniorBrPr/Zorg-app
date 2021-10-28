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
import java.time.format.DateTimeParseException;
import java.util.List;
import dao.PatientManagementDAO;
import dao.medicationManagementDAO;
import pojo.Weight;


import javax.swing.*;
import java.time.LocalDate;
import java.util.Locale;
import java.util.ResourceBundle;

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
   LocalDate now = LocalDate.now();

   //This method shows and gives all the options the Admin has
   void menu(Locale locale) throws Exception {
      ResourceBundle resourceBundle = ResourceBundle.getBundle("res.bundle", locale);
      do {
         System.out.println("      "+resourceBundle.getString("welcom")+" Admin    ");
         System.out.println("<><><><><><><><><><><><>");
         System.out.println("A. "+resourceBundle.getString("viewPatients"));
         System.out.println("B. "+resourceBundle.getString("addPatient"));
         System.out.println("C. "+resourceBundle.getString("updatePatient"));
         System.out.println("D. "+resourceBundle.getString("deletePatient"));
         System.out.println("E. "+resourceBundle.getString("searchPatient"));
         System.out.println("-----------------");
         System.out.println("G. "+resourceBundle.getString("prescrbMed"));
         System.out.println("H. "+resourceBundle.getString("viewAllPandM"));
         System.out.println("I. "+resourceBundle.getString("delMed"));
         System.out.println("J. "+resourceBundle.getString("updtMed"));
         System.out.println("-----------------");
         System.out.println("K. "+resourceBundle.getString("addWeight"));
         System.out.println("L. "+resourceBundle.getString("updtWeight"));
         System.out.println("M. "+resourceBundle.getString("delWeight"));
         System.out.println("N. "+resourceBundle.getString("viewAllWeightData"));
         System.out.println("-----------------");
         System.out.println("O. "+resourceBundle.getString("viewAllData"));
         System.out.println("P. "+resourceBundle.getString("viewGraph"));
         System.out.println("-----------------");
         System.out.println("F. "+resourceBundle.getString("exit"));
         System.out.println("<><><><><><><><><><><><>");
         System.out.println(resourceBundle.getString("enterOption"));
         System.out.println("<><><><><><><><><><><><>");
         option = br.readLine();
         System.out.println("\n");
         //Admin options
         switch (option.toUpperCase()) {
            case "A" -> viewPatients(locale);
            case "B" -> addPatient(locale);
            case "C" -> updatePatient(locale);
            case "D" -> deletePatient(locale);
            case "E" -> searchPatient(locale);
            case "G" -> addMedication(locale);
            case "H" -> viewPatientsAndMeds(locale);
            case "I" -> deleteMedication(locale);
            case "J" -> updateMedication(locale);
            case "K" -> addWeight(locale);
            case "L" -> updateWeight(locale);
            case "M" -> deleteWeight(locale);
            case "N" -> viewPatientsAndWeightData(locale);
            case "O" -> viewPatientsAndAllData(locale);
            case "P" -> viewPatientWeightGraph(locale);
            case "F" -> {
               System.out.println(resourceBundle.getString("gbye"));
               System.exit(0);
            }
            default -> System.out.println(resourceBundle.getString("invalidOpt"));
         }
      }while(!option.equals("F"));
   }
   //i18n'd
   public void viewPatientWeightGraph(Locale locale) throws IOException, SQLException {
      ResourceBundle resourceBundle = ResourceBundle.getBundle("res.bundle", locale);
      viewPatientIds(locale);
      System.out.println("------------------------------------------------");
      System.out.println(resourceBundle.getString("enterId"));
      System.out.println("------------------------------------------------");
      try{
         int patientId = parseInt(br.readLine());
         if(checkPatientId.check(patientId)){
            weightGraph(patientId, locale);
            viewWeightData(dao.getPatientByid(patientId), locale);
         } else {
            System.out.println("\n");
            System.out.println("\n");
            System.out.println(resourceBundle.getString("invalidId"));
            System.out.println("\n");
            System.out.println("\n");
         }
      }catch (NumberFormatException ex) {
         System.out.println("---------------------");
         System.out.println(resourceBundle.getString("invalidInput"));
         System.out.println("---------------------");
      }
   }
   //i18n'd
   public void weightGraph(int patientId,Locale locale) {
      ResourceBundle resourceBundle = ResourceBundle.getBundle("res.bundle", locale);
      try{
         //Ask the user to enter patientId
         //Check if ID is in the DB
         String patient = String.valueOf(patientId);
         String query = "SELECT weightDate,weight FROM weight WHERE patientId="+patient+" ORDER BY weight.weightDate ASC ;";
         JDBCCategoryDataset dataset = new JDBCCategoryDataset(db.getConnection(), query);
         JFreeChart chart = ChartFactory.createLineChart(resourceBundle.getString("weightChart"), resourceBundle.getString("date"),resourceBundle.getString("weight"), dataset, PlotOrientation.VERTICAL, false, true, true);
         ChartFrame frame = new ChartFrame(resourceBundle.getString("weightChart"), chart);
         frame.setVisible(true);
         frame.setSize(400,600);
      }catch (Exception e) {
         JOptionPane.showMessageDialog(null, e);
      }
   }
   //i18n'd
   //This method prints a list with all the patients and their credentials, except for their prescribed medication.
   public void viewPatients(Locale locale) {
      System.out.println("-----------------------------------------------");
      //Get all the patients from the DAO and store them
      List<Patient> patientList = dao.getAllPatients();
      for(Patient patient: patientList)
      {
         //display patient one by one
         displayPatient(patient, locale);
      }
      System.out.println("-----------------------------------------------");
      System.out.println("\n");
   }

   //This method prints all the ID's and names of patients in the DB
   //Mainly made to make it easier for the Admin to find a patient while updating, deleting etc. a patient
   //i18n'd
   public void viewPatientIds(Locale locale) {
      System.out.println("-----------------------------------------------");
      //Get all the patients from the DAO and store them
      List<Patient> patientIdList = dao.getAllPatients();
      for(Patient patient: patientIdList) {
         displayPatientId(patient, locale);
      }
      System.out.println("-----------------------------------------------");
      System.out.println("\n");
   }
   //i18n'd
   //Same as viewPatients method, but this method also prints the medication Data for every patient
   public void viewPatientsAndMeds(Locale locale) {
      System.out.println("-----------------------------------------------");
      //Get all the patients from the DAO and store them
      List<Patient> patientList = dao.getAllPatients();
      for(Patient patient: patientList) {
         //display patient and their medication one by one
         displayPatient(patient, locale);
         viewMedications(patient, locale);
      }
      System.out.println("-----------------------------------------------");
      System.out.println("\n");
   }
   //This method adds a new Patient to the database. The admin has to insert the credentials.
   //i18n'd
   public void addPatient(Locale locale) throws Exception {
      //patientId gets set on the server side, this value is a placeholder
      int patientId = 0;
      ResourceBundle resourceBundle = ResourceBundle.getBundle("res.bundle", locale);
      try {
         System.out.println("------------------------------------------------");
         System.out.println(resourceBundle.getString("enterSur")+" :");
         System.out.println("------------------------------------------------");
         String surName = br.readLine();
         System.out.println("------------------------------------------------");
         System.out.println(resourceBundle.getString("eFirst")+" :");
         System.out.println("------------------------------------------------");
         String firstName = br.readLine();
         System.out.println("------------------------------------------------");
         System.out.println(resourceBundle.getString("eNick")+" :");
         System.out.println("------------------------------------------------");
         String nickname = br.readLine();
         System.out.println("------------------------------------------------");
         System.out.println(resourceBundle.getString("eDOB")+" : (yyyy-MM-dd)");
         System.out.println("------------------------------------------------");
         String sDateOfBirth = br.readLine();
         //Here the DOB gets formatted the correct way in order to store it in the DB and to later be able to calculate the age.
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate DateOfBirth = LocalDate.parse(sDateOfBirth, formatter);
         System.out.println("------------------------------------------------");
         System.out.println(resourceBundle.getString("eLen")+" :");
         System.out.println("------------------------------------------------");
         double length = Double.parseDouble(br.readLine());
         System.out.println("------------------------------------------------");
         System.out.println(resourceBundle.getString("eWeight")+" :");
         System.out.println("------------------------------------------------");
         double weight = Double.parseDouble(br.readLine());
         //After admin enters values, store them in a Patient variable
         Patient patient = new Patient(patientId, surName,firstName, nickname, DateOfBirth, length, weight);
         //If the patient gets uploaded to the DB, status should return 1
         int status = dao.addPatient(patient);
         if(status ==1 ){
            System.out.println(resourceBundle.getString("pAddSucces"));
            //Add a Weight object to the Patient in order to populate the weight Graph
            int weightId = 0;
            patientId = dao.getLatestPatient();
            Weight weightN = new Weight(weightId, weight, now, patientId);
            //If the weight gets uploaded to the DB, status should return 1
            int statusWeight = weightDao.addWeight(weightN);
            if(statusWeight ==1 ) {
               System.out.println(resourceBundle.getString("wAddedSucces"));
            }
            else {
               System.out.println(resourceBundle.getString("wAddingError"));
            }
         }
         else
         {
            System.out.println(resourceBundle.getString("pAddingError"));
         }
         System.out.println("\n");
      }catch (DateTimeParseException ex){
         System.out.println(resourceBundle.getString("wrongDateFormat"));
      }
   }
   //This method lets the admin 'prescribe' a medication to a patient by ID
   //i18n'd
   public void addMedication(Locale locale) throws Exception
   {
      ResourceBundle resourceBundle = ResourceBundle.getBundle("res.bundle", locale);
      viewPatientIds(locale);
      System.out.println("------------------------------------------------");
      System.out.println(resourceBundle.getString("enterId"));
      System.out.println("------------------------------------------------");
      try{
         int patientId = parseInt(br.readLine());
         if(checkPatientId.check(patientId)){
            System.out.println("------------------------------------------------");
            System.out.println(resourceBundle.getString("eMedName")+" :");
            System.out.println("------------------------------------------------");
            String medName = br.readLine();
            System.out.println("------------------------------------------------");
            System.out.println(resourceBundle.getString("eMedDosage")+" :");
            System.out.println("------------------------------------------------");
            String dosage = br.readLine();
            System.out.println("------------------------------------------------");
            System.out.println(resourceBundle.getString("eMedManufacturer")+" :");
            System.out.println("------------------------------------------------");
            String manufacturer = br.readLine();
            System.out.println("------------------------------------------------");
            System.out.println(resourceBundle.getString("eMedType")+" :");
            System.out.println("------------------------------------------------");
            String medType = br.readLine();
            //after user enters values, store them in a Medication variable
            int medId = 0;
            Medication medication = new Medication(medId, medName, dosage,manufacturer, patientId, medType);
            //If the medication gets uploaded to the DB, status should return 1
            int status = medDao.addMed(medication);
            if(status ==1 )
            {
               System.out.println(resourceBundle.getString("medAddedSucces"));
            }
            else
            {
               System.out.println(resourceBundle.getString("medAddingError"));
            }
         } else {
            System.out.println("\n");
            System.out.println("\n");
            System.out.println(resourceBundle.getString("invalidId"));
            System.out.println("\n");
         }
         System.out.println("\n");
      }catch (NumberFormatException ex) {
         System.out.println("---------------------");
         System.out.println(resourceBundle.getString("invalidInput"));
         System.out.println("---------------------");
      }
   }
   //This method asks the admin if he wants to change a certain attribute value or retain it's original value, while updating patients or meds.
   //i18n'd
   public String attributeChanger(String originalValue, String attributeName, Locale locale) throws IOException {
      ResourceBundle resourceBundle = ResourceBundle.getBundle("res.bundle", locale);
      String value = "";
      System.out.println(resourceBundle.getString("eAToKeep")+" "+attributeName);
      System.out.println(resourceBundle.getString("eBToChange")+" "+attributeName);
      System.out.println("------------------------------------------------");
      option = br.readLine();
      switch (option.toUpperCase()) {
         case "A" -> value = originalValue;
         case "B" -> {
            System.out.println(resourceBundle.getString("eNew")+" "+ attributeName + ":");
            value = br.readLine();
         }
         default -> System.out.println(resourceBundle.getString("invalidOpt"));
      }
      return value;
   }
   //This method helps the admin change attribute values.
   //i18n'd
   public void updatePatient(Locale locale) throws Exception
   {
      //Prints a list with all the patients in the DB and their ID's and names
      ResourceBundle resourceBundle = ResourceBundle.getBundle("res.bundle", locale);
      viewPatientIds(locale);
      System.out.println("------------------------------------------------");
      System.out.println(resourceBundle.getString("eIdOfPatient")+" :");
      System.out.println("------------------------------------------------");
      try{
         int patientId = parseInt(br.readLine());
         if(checkPatientId.check(patientId)){
            Patient patientOriginalData = dao.getPatientByid(patientId);

            System.out.println("------------------------------------------------");
            System.out.println(resourceBundle.getString("cSur")+" : "+ patientOriginalData.getSurName());
            System.out.println("------------------------------------------------");
            String surName = attributeChanger(patientOriginalData.getSurName(), resourceBundle.getString("sur"), locale);

            System.out.println("------------------------------------------------");
            System.out.println(resourceBundle.getString("cFirst")+" : "+patientOriginalData.getFirstName());
            System.out.println("------------------------------------------------");
            String firstName = attributeChanger(patientOriginalData.getFirstName(), resourceBundle.getString("firstN"), locale);
            //Call nickname updating method
            String nickname = updateNickname(dao.getPatientByid(patientId), patientOriginalData, locale);

            System.out.println("------------------------------------------------");
            System.out.println(resourceBundle.getString("cDOB")+" : "+patientOriginalData.getDateOfBirth());
            System.out.println("------------------------------------------------");
            String sDateOfBirth = attributeChanger(String.valueOf(patientOriginalData.getDateOfBirth()), resourceBundle.getString("DOB"), locale);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate DateOfBirth = LocalDate.parse(sDateOfBirth, formatter);

            System.out.println("------------------------------------------------");
            System.out.println(resourceBundle.getString("cLen")+" : "+patientOriginalData.getLength());
            System.out.println("------------------------------------------------");
            double length = Double.parseDouble(attributeChanger(String.valueOf(patientOriginalData.getLength()), resourceBundle.getString("len"), locale));

            System.out.println("------------------------------------------------");
            System.out.println(resourceBundle.getString("cWeight")+" : "+patientOriginalData.getWeight());
            System.out.println("------------------------------------------------");
            double weight = Double.parseDouble(attributeChanger(String.valueOf(patientOriginalData.getWeight()),resourceBundle.getString("weightWord"), locale));

            //after practitioner enters values, store them in a Patient variable
            Patient patient = new Patient(patientId, surName,firstName, nickname, DateOfBirth, length, weight);
            //If the patient gets updated in the DB, status should return 1
            int status = dao.updatePatient(patient);
            if(status ==1 ) {
               System.out.println(resourceBundle.getString("pUpdateSucces"));
            }
            else {
               System.out.println(resourceBundle.getString("pUpdateError"));
            }
         } else {
            System.out.println("\n");
            System.out.println("\n");
            System.out.println(resourceBundle.getString("invalidId"));
            System.out.println("\n");
         }
         System.out.println("\n");
      }catch (NumberFormatException ex) {
         System.out.println("---------------------");
         System.out.println(resourceBundle.getString("invalidInput"));
         System.out.println("---------------------");
      }
      catch (DateTimeParseException ex){
         System.out.println(resourceBundle.getString("wrongDateFormat"));
      }
   }
   //Basically the same as the updatePatient method, except with this method you can update individual Medicine prescriptions.
   //This method helps the admin update Medication attribute values
   //i18n'd
   public void updateMedication(Locale locale) throws Exception {
      ResourceBundle resourceBundle = ResourceBundle.getBundle("res.bundle", locale);
      //First the admin has to select the Patient
      viewPatientIds(locale);
      System.out.println("------------------------------------------------");
      System.out.println(resourceBundle.getString("enterId"));
      System.out.println("------------------------------------------------");
      try{
         int patientId = parseInt(br.readLine());
         if(checkPatientId.check(patientId)){
            //View patient medList
            //Then the admin has to select which Medicine he would like to update
            viewMedications(dao.getPatientByid(patientId), locale);
            System.out.println("------------------------------------------------");
            System.out.println(resourceBundle.getString("eMedId")+" :");
            System.out.println("------------------------------------------------");
            int medId = parseInt(br.readLine());
            Medication originalMedicationData = medDao.getMedicationByMedId(medId);

            //Ask the admin if he wants to retain or change the values
            System.out.println("------------------------------------------------");
            System.out.println(resourceBundle.getString("cMName")+" : "+ originalMedicationData.getMedName());
            System.out.println("------------------------------------------------");
            String medName = attributeChanger(originalMedicationData.getMedName(),resourceBundle.getString("cMName"), locale);

            System.out.println("------------------------------------------------");
            System.out.println(resourceBundle.getString("cMDosage")+" : "+ originalMedicationData.getDosage());
            System.out.println("------------------------------------------------");
            String dosage = attributeChanger(originalMedicationData.getDosage(),resourceBundle.getString("medDosage"), locale);

            System.out.println("------------------------------------------------");
            System.out.println(resourceBundle.getString("cMedManufacturer")+" : "+ originalMedicationData.getManufacturer());
            System.out.println("------------------------------------------------");
            String manufacturer = attributeChanger(originalMedicationData.getManufacturer(),resourceBundle.getString("medManufacturer"), locale);

            System.out.println("------------------------------------------------");
            System.out.println(resourceBundle.getString("cMedType")+" : "+ originalMedicationData.getMedType());
            System.out.println("------------------------------------------------");
            String medType = attributeChanger(originalMedicationData.getMedType(),resourceBundle.getString("medType"), locale);

            //Store values and send to DB
            Medication medication = new Medication(medId, medName, dosage, manufacturer, patientId, medType);
            //If the Medication gets updated, status should return 1
            int status = medDao.updateMedication(medication);
            if(status ==1 ) {
               System.out.println(resourceBundle.getString("mAddSucces"));
            }
            else {
               System.out.println(resourceBundle.getString("mAddError"));
            }
         } else {
            System.out.println("\n");
            System.out.println("\n");
            System.out.println(resourceBundle.getString("invalidId"));
            System.out.println("\n");
            System.out.println("\n");
         }
      }catch (NumberFormatException ex) {
         System.out.println("---------------------");
         System.out.println(resourceBundle.getString("invalidInput"));
         System.out.println("---------------------");
      }
   }
   //This method helps the admin delete a patient from the DB by patientId
   //i18n'd
   public void deletePatient(Locale locale) throws Exception {
      ResourceBundle resourceBundle = ResourceBundle.getBundle("res.bundle", locale);
      viewPatientIds(locale);
      System.out.println("------------------------------------------------");
      System.out.println(resourceBundle.getString("eIdToDelete")+":");
      System.out.println("------------------------------------------------");
      //If the patient gets deleted form the DB status should return 1
      try{
         int patientId = parseInt(br.readLine());
         if(checkPatientId.check(patientId)){
            int status = dao.deletePatient(patientId);
            if(status == 1 ) {
               System.out.println(resourceBundle.getString("pDeleteSucces"));
            }
            else {
               System.out.println(resourceBundle.getString("pDeleteError"));
            }
         } else {
            System.out.println("\n");
            System.out.println("\n");
            System.out.println(resourceBundle.getString("invalidId"));
            System.out.println("\n");
            System.out.println("\n");
         }
      }catch (NumberFormatException ex) {
         System.out.println("---------------------");
         System.out.println(resourceBundle.getString("invalidInput"));
         System.out.println("---------------------");
      }
   }
   //This method helps the admin delete a medication from the DB by medId
   //i18n'd
   public void deleteMedication(Locale locale) throws Exception {
      ResourceBundle resourceBundle = ResourceBundle.getBundle("res.bundle", locale);
      //First select the patient whose medication the Admin would to delete
      viewPatientIds(locale);
      System.out.println("------------------------------------------------");
      System.out.println(resourceBundle.getString("eIdToDeleteMed")+" :");
      System.out.println("------------------------------------------------");
      try{
         int patientId = parseInt(br.readLine());
         if(checkPatientId.check(patientId)){
            //View patient medList
            viewMedications(dao.getPatientByid(patientId), locale);
            System.out.println("------------------------------------------------");
            System.out.println(resourceBundle.getString("eMedIdToDelete")+" :");
            System.out.println("------------------------------------------------");
            int medId = parseInt(br.readLine());
            int status = medDao.deleteMedication(medId);
            //If the medication gets deleted from the DB status should return 1
            if(status == 1 ) {
               System.out.println(resourceBundle.getString("medDeleteSucces"));
            }
            else {
               System.out.println(resourceBundle.getString("medDeleteError"));
            }
         } else {
            System.out.println("\n");
            System.out.println("\n");
            System.out.println(resourceBundle.getString("invalidId"));
            System.out.println("\n");
         }
         System.out.println("\n");
      }catch (NumberFormatException ex) {
         System.out.println("---------------------");
         System.out.println(resourceBundle.getString("invalidInput"));
         System.out.println("---------------------");
      }
   }
   //This method helps the admin lookup the credentials and medication of a Patient by patientId
   //i18n'd
   public void searchPatient(Locale locale) throws Exception {
      ResourceBundle resourceBundle = ResourceBundle.getBundle("res.bundle", locale);
      viewPatientIds(locale);
      System.out.println("------------------------------------------------");
      System.out.println(resourceBundle.getString("eIdToSearch")+" :");
      System.out.println("------------------------------------------------");
      try{
         int patientId = parseInt(br.readLine());
         if(checkPatientId.check(patientId)){
            Patient patient = dao.getPatientByid(patientId);
            displayPatient(patient, locale);
            viewMedications(patient, locale);
            viewWeightData(patient, locale);
         } else {
            System.out.println("\n");
            System.out.println("\n");
            System.out.println(resourceBundle.getString("invalidId"));
            System.out.println("\n");
         }
         System.out.println("\n");
      }catch (NumberFormatException ex) {
         System.out.println("---------------------");
         System.out.println(resourceBundle.getString("invalidInput"));
         System.out.println("---------------------");
      }
   }
   //This method is used for the viewPatients methods
   //It just prints out the credentials of a patient
   //i18n'd
   public void displayPatient(Patient patient, Locale locale) {
      ResourceBundle resourceBundle = ResourceBundle.getBundle("res.bundle", locale);
      int age = aC.calculateAge(patient.getDateOfBirth());
      double bmi = bC.calculateBMI(patient.getLength(), patient.getWeight());
      String bmiCategory = bC.bmiCategory(bmi, locale);
      System.out.println("-----------------------------------------------");
      System.out.println(resourceBundle.getString("pCreds")+" = ");
      System.out.println(resourceBundle.getString("pId")+": "+patient.getPatientId());
      System.out.println(resourceBundle.getString("pSur")+": "+patient.getSurName());
      System.out.println(resourceBundle.getString("pFirst")+": "+patient.getFirstName());
      System.out.println(resourceBundle.getString("pNick")+": "+patient.getNickname());
      System.out.println(resourceBundle.getString("pDOB")+": "+patient.getDateOfBirth());
      System.out.println(resourceBundle.getString("pAge")+": "+age);
      System.out.println(resourceBundle.getString("pLen")+": "+patient.getLength());
      System.out.println(resourceBundle.getString("pWeight")+": "+patient.getWeight());
      System.out.println(resourceBundle.getString("pBMI")+": "+bmi);
      System.out.println(resourceBundle.getString("pBMICat")+": "+bmiCategory);
      System.out.println("\n");
   }
   //This method is used for the viewPatientIds
   //Prints out the patientId, surname and firstname
   //i18n'd
   public void displayPatientId(Patient patient, Locale locale) {
      ResourceBundle resourceBundle = ResourceBundle.getBundle("res.bundle", locale);
      System.out.println(resourceBundle.getString("pId")+": "+patient.getPatientId() +", "+ resourceBundle.getString("pSur") +": "+patient.getSurName()+","+ resourceBundle.getString("pFirst") +": "+patient.getFirstName());
      System.out.println("-----------------------------------------------");
   }
   //This method gets used in the viewMedications
   //This method displays the medication attribute values
   //i18n'd
   public void displayMedication(Medication medication, Locale locale){
      ResourceBundle resourceBundle = ResourceBundle.getBundle("res.bundle", locale);
      System.out.println(resourceBundle.getString("medId")+": "+medication.getMedId());
      System.out.println(resourceBundle.getString("medName")+": "+medication.getMedName());
      System.out.println(resourceBundle.getString("medDosage")+": "+medication.getDosage());
      System.out.println(resourceBundle.getString("medType")+": "+medication.getMedType());
      System.out.println(resourceBundle.getString("medManufacturer")+": "+medication.getManufacturer());
   }
   //This method gets used in the searchPatient method and the viewPatientsAndMeds method
   //This method prints a list of the medications a given patient has
   //i18n'd
   public void viewMedications(Patient patient, Locale locale){
      ResourceBundle resourceBundle = ResourceBundle.getBundle("res.bundle", locale);
      System.out.println(resourceBundle.getString("pPrescriptions")+" :");
      List<Medication> medicationList = medDao.getAllMeds(patient.getPatientId());
      for(Medication medication: medicationList)
      {
         //display medication one by one
         displayMedication(medication, locale);
         System.out.println("\n");
      }
      System.out.println("-----------------------------------------------");
   }
   //i18n'd
   public String updateNickname(Patient patient, Patient patientOriginalData, Locale locale) throws IOException {
      ResourceBundle resourceBundle = ResourceBundle.getBundle("res.bundle", locale);
      System.out.println("------------------------------------------------");
      System.out.println(resourceBundle.getString("cNick")+" : "+patientOriginalData.getNickname());
      System.out.println("------------------------------------------------");
      String nickname = attributeChanger(patientOriginalData.getNickname(),resourceBundle.getString("nick"), locale);
      patient.setNickname(nickname);
      int status = dao.updateNickname(patient);
      if(status ==1 ) {
         System.out.println(resourceBundle.getString("nickUpdateSucces"));
      }
      else {
         System.out.println(resourceBundle.getString("nickUpdateError"));
      }
      System.out.println("\n");
      return nickname;
   }
   //Add, update, search and delete weight functions down here
   //i18n'd
   public void addWeight(Locale locale) throws Exception
   {
      ResourceBundle resourceBundle = ResourceBundle.getBundle("res.bundle", locale);
      viewPatientIds(locale);
      System.out.println("------------------------------------------------");
      System.out.println(resourceBundle.getString("enterId"));
      System.out.println("------------------------------------------------");
      try{
         int patientId = parseInt(br.readLine());
         if(checkPatientId.check(patientId)){
            System.out.println("------------------------------------------------");
            System.out.println(resourceBundle.getString("ePWeight")+" :");
            System.out.println("------------------------------------------------");
            double weightTemp = Double.parseDouble(br.readLine());
            System.out.println("------------------------------------------------");
            System.out.println(resourceBundle.getString("eDateWeighing")+" :");
            System.out.println("------------------------------------------------");
            String weightDateTemp = br.readLine();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate weightDate = LocalDate.parse(weightDateTemp, formatter);
            //after user enters values, store them in a Weight variable
            //Werkt nog niet
               if (weightDate.isEqual(now)) {
                  int status = dao.updateWeight(patientId, weightTemp);
                  if(status ==1 ) {
                     System.out.println(resourceBundle.getString("wUpdateSucces"));
                  }
                  else {
                     System.out.println(resourceBundle.getString("wUpdateError"));
                  }
               }
            int weightId = 0;
            Weight weight = new Weight(weightId, weightTemp, weightDate, patientId);
            //If the weight gets uploaded to the DB, status should return 1
            int status = weightDao.addWeight(weight);
               if(status ==1 ) {
                  System.out.println(resourceBundle.getString("wDataAddSucces"));
               }
               else {
                  System.out.println(resourceBundle.getString("wDataAddError"));
               }
         } else {
            System.out.println("\n");
            System.out.println("\n");
            System.out.println(resourceBundle.getString("invalidId"));
            System.out.println("\n");
         }
         System.out.println("\n");
      }catch (NumberFormatException ex) {
         System.out.println("---------------------");
         System.out.println(resourceBundle.getString("invalidInput"));
         System.out.println("---------------------");
      }
      catch (DateTimeParseException ex){
         System.out.println(resourceBundle.getString("wrongDateFormat"));
      }
   }
   //i18n'd
   public void displayWeightData(Weight weight, Locale locale){
      ResourceBundle resourceBundle = ResourceBundle.getBundle("res.bundle", locale);
      System.out.println(resourceBundle.getString("wID")+" : "+weight.getWeightId());
      System.out.println(resourceBundle.getString("weightWord")+" (KG's): "+weight.getWeight());
      System.out.println(resourceBundle.getString("wDate")+" : "+weight.getWeightDate());
   }
   //i18n'd
   public void viewWeightData(Patient patient, Locale locale){
      ResourceBundle resourceBundle = ResourceBundle.getBundle("res.bundle", locale);
      System.out.println(resourceBundle.getString("pWData")+" : ");
      List<Weight> weightList = weightDao.getAllWeightData(patient.getPatientId());
      for(Weight weight: weightList)
      {
         //display medication one by one
         displayWeightData(weight, locale);
         System.out.println("\n");
      }
      System.out.println("-----------------------------------------------");
   }
   //i18n'd
   public void viewPatientsAndWeightData(Locale locale) {
      System.out.println("-----------------------------------------------");
      //Get all the patients from the DAO and store them
      List<Patient> patientList = dao.getAllPatients();
      for(Patient patient: patientList) {
         //display patient and their medication one by one
         displayPatient(patient, locale);
         viewWeightData(patient, locale);
      }
      System.out.println("-----------------------------------------------");
      System.out.println("\n");
   }
   //i18n'd
   public void viewPatientsAndAllData(Locale locale) {
      System.out.println("-----------------------------------------------");
      //Get all the patients from the DAO and store them
      List<Patient> patientList = dao.getAllPatients();
      for(Patient patient: patientList) {
         //display patient and their medication one by one
         displayPatient(patient, locale);
         viewMedications(patient, locale);
         viewWeightData(patient, locale);
         System.out.println("><><><><><><><><><><><><><><><><><><><><><><><><");
      }
      System.out.println("-----------------------------------------------");
      System.out.println("\n");
   }
   //i18n'd
   public void deleteWeight(Locale locale) throws Exception {
      ResourceBundle resourceBundle = ResourceBundle.getBundle("res.bundle", locale);
      //First select the patient whose weight data the Admin would to delete
      viewPatientIds(locale);
      System.out.println("------------------------------------------------");
      System.out.println(resourceBundle.getString("ePIDToDeleteWeightData")+" :");
      System.out.println("------------------------------------------------");
      try{
         int patientId = parseInt(br.readLine());
         if(checkPatientId.check(patientId)){
            //View patient Weight Data list
            viewWeightData(dao.getPatientByid(patientId), locale);
            System.out.println("------------------------------------------------");
            System.out.println(resourceBundle.getString("eWIDToDelete")+" :");
            System.out.println("------------------------------------------------");
            int weightId = parseInt(br.readLine());
            int status = weightDao.deleteWeight(weightId);
            //If the weight data gets deleted from the DB status should return 1
            if(status == 1 ) {
               System.out.println(resourceBundle.getString("wDeleteSucces"));
            }
            else {
               System.out.println(resourceBundle.getString("wDeleteError"));
            }
         } else {
            System.out.println("\n");
            System.out.println("\n");
            System.out.println(resourceBundle.getString("invalidId"));
            System.out.println("\n");
         }
         System.out.println("\n");
      }catch (NumberFormatException ex) {
         System.out.println("---------------------");
         System.out.println(resourceBundle.getString("invalidInput"));
         System.out.println("---------------------");
      }
   }
   @SuppressWarnings("WrapperTypeMayBePrimitive")
   public void updateWeight(Locale locale) throws Exception {
      ResourceBundle resourceBundle = ResourceBundle.getBundle("res.bundle", locale);
      //First the admin has to select the Patient
      viewPatientIds(locale);
      System.out.println("------------------------------------------------");
      System.out.println(resourceBundle.getString("enterId"));
      System.out.println("------------------------------------------------");
      try{
         int patientId = parseInt(br.readLine());
         if(checkPatientId.check(patientId)){
            //View patient weight data
            //Then the admin has to select which Weight data he would like to update
            viewWeightData(dao.getPatientByid(patientId), locale);
            System.out.println("------------------------------------------------");
            System.out.println(resourceBundle.getString("eWIDToUpdate")+" :");
            System.out.println("------------------------------------------------");
            int weightId = parseInt(br.readLine());
            Weight originalWeightData = weightDao.getWeightByWeightId(weightId);

            //Ask the admin if he wants to retain or change the values
            System.out.println("------------------------------------------------");
            System.out.println(resourceBundle.getString("cW")+" :"+ originalWeightData.getWeight());
            System.out.println("------------------------------------------------");
            Double weightTemp = Double.parseDouble(attributeChanger(String.valueOf(originalWeightData.getWeight()),resourceBundle.getString("weightWord")+" (kg's)", locale));

            System.out.println("------------------------------------------------");
            System.out.println(resourceBundle.getString("cWeighingDate")+" :"+ originalWeightData.getWeightDate());
            System.out.println("------------------------------------------------");
            String weightDateTemp = attributeChanger(String.valueOf(originalWeightData.getWeightDate()),resourceBundle.getString("wDate"), locale);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate weightDate = LocalDate.parse(weightDateTemp, formatter);

            //Store values and send to DB

            Weight weight = new Weight(weightId,weightTemp,weightDate, patientId);
            //If the Medication gets updated, status should return 1
            int status = weightDao.updateWeightData(weight);
            if(status ==1 ) {
               System.out.println(resourceBundle.getString("wUpdateSucces"));
            }
            else {
               System.out.println(resourceBundle.getString("wDataAddSucces"));
            }
         } else {
            System.out.println("\n");
            System.out.println("\n");
            System.out.println(resourceBundle.getString("invalidId"));
            System.out.println("\n");
         }
         System.out.println("\n");
      }catch (NumberFormatException ex) {
         System.out.println("---------------------");
         System.out.println(resourceBundle.getString("invalidInput"));
         System.out.println("---------------------");
      }
   }

}
