package pojo;

import java.time.LocalDate;

public class Patient
{
   private int patientId;
   private String surName;
   private String firstName;
   private String nickname;
   private LocalDate DateOfBirth;
   private double length;
   private double weight;


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
/*
   public ArrayList<Integer> getMedId() {
      return medId;
   }
   public Integer setMedId(Integer medId){
      return medId;
   }

 */
}
