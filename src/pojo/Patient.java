package pojo;

import java.time.LocalDate;
import java.util.ArrayList;

public class Patient
{
   int patientId;
   String surName;
   String firstName;
   String nickname;
   LocalDate DateOfBirth;
   double length;
   double weight;
   //int[] meds;
   //Dit zou kunnen werken. Dan bewaar ik de ID's van de medicatie van een patient

   //Medicijnen implementen. (Mischien een extra table maken in de DB en daar medicijnen in opslaan en dan met een key oproepen als een patient die medicatie heeft.
   //Zou dus ook een medicijn class moeten maken.
   //Attributes zouden dingen zoals werking, dosis, fabrikant, naam etc.
   //Succes buddy
   //Zou een aangepaste versie van de SearchPatient by ID kunnen gebruiken om medicatie op te zoeken in een andere table



   public Patient(int patientId, String surName, String firstName, String nickname, LocalDate DateOfBirth, double length, double weight){
      this.patientId = patientId;
      this.surName = surName;
      this.firstName = firstName;
      this.nickname = nickname;
      this.DateOfBirth = DateOfBirth;
      this.length = length;
      this.weight = weight;
   }


   //pojo getters and setters
   public int getPatientId(){
      return patientId;
   }
   public int setPatientId(int patientId){
      return patientId;
   }
   public String getSurName(){
      return surName;
   }
   public String setSurName(String surName){
      return surName;
   }
   public String getFirstName(){
      return firstName;
   }
   public String setFirstName(String firstName){
      return firstName;
   }
   public String getNickname(){
      return nickname;
   }
   public String setNickname(String nickname){
      return nickname;
   }
   public LocalDate getDateOfBirth(){
      return DateOfBirth;
   }
   public LocalDate setDateOfBirth(LocalDate DateOfBirth){
      return DateOfBirth;
   }
   public double getLength(){
      return length;
   }
    public double setLength(double length){
      return length;
   }
   public double getWeight(){
      return weight;
   }
    public double setWeight(double weight){
      return weight;
   }
}
