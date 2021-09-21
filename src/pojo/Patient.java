package pojo;

import java.sql.Date;
import java.time.LocalDate;

public class Patient
{
   String patientId;
   String surName;
   String firstName;
   String nickname;
   //start implementing age and ageCalculator

   LocalDate DateOfBirth;
   int age;

   // Constructor


   public Patient(String patientId, String surName, String firstName, String nickname, LocalDate DateOfBirth, int age){
      this.patientId = patientId;
      this.surName = surName;
      this.firstName = firstName;
      this.nickname = nickname;
      this.DateOfBirth = DateOfBirth;
      this.age = age;

   }
   //pojo getters and setters
   public String getPatientId(){
      return patientId;
   }
   public String setPatientId(String patientId){
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
      return firstName;
   }
   public String setNickname(String nickname){
      return firstName;
   }

   public LocalDate getDateOfBirth(){
      return DateOfBirth;
   }
   public LocalDate setDateOfBirth(LocalDate DateOfBirth){
      return DateOfBirth;
   }
   public int getAge(){
      return age;
   }
   public int setAge(int age){
      return age;
   }



   // Write data to screen.

}
