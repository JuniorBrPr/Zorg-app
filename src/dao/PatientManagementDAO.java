package dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import dbutil.DBUtil;
import pojo.Patient;

public class PatientManagementDAO {
    DBUtil dU = new DBUtil();
    //CRUD operations
    //This method makes a list of all the patient's in the DB, and returns it for display in the app
    public List<Patient> getAllPatients(){
        List<Patient> patientList = new ArrayList<Patient>();
        try{
            Connection conn = dU.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * from patient");
            while (rs.next()){
                Patient patient = new Patient(rs.getInt("patientId"), rs.getString("surName"), rs.getString("firstName"), rs.getString("nickname"), rs.getObject("DateOfBirth", LocalDate.class), rs.getDouble("length"), rs.getDouble("weight"));
                patientList.add(patient);
            }
            dU.closeConnection(conn);  //Close DB connection
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return patientList;
    }
    //This method gets a patient by patientId from the DB and returns that patient
    public Patient getPatientByid(int patientId) {
        Patient patient = null;
        try
        {
            Connection conn = dU.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM patient WHERE patientId = ?");
            ps.setInt(1, patientId);
            ResultSet rs = ps.executeQuery();
            //iterate through results
            while(rs.next())
            {
                patient = new Patient(rs.getInt("patientId"), rs.getString("surName"), rs.getString("firstName"), rs.getString("nickname"), rs.getObject("DateOfBirth", LocalDate.class), rs.getDouble("length"), rs.getDouble("weight"));
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return patient;
    }
    //This method adds a given patient to the DB, returns status
    public int addPatient(Patient patient) {
        //Status displays 1 if successfully inserted data or else error
        int status = 0;
        try
        {
            Connection conn = dU.getConnection();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO patient(surName, firstName, nickname, DateOfBirth,length, weight) VALUES(?,?,?,?,?,?)");
            //set parameters of query here but using the values for the patient object
            ps.setString(1, patient.getSurName());
            ps.setString(2, patient.getFirstName());
            ps.setString(3, patient.getNickname());
            ps.setDate(4, java.sql.Date.valueOf(patient.getDateOfBirth()));
            ps.setDouble(5, patient.getLength());
            ps.setDouble(6, patient.getWeight());
            status = ps.executeUpdate();  //If successful status should return 1
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return status;
    }

    //This method updates a patient in the DB and returns status
    public int updatePatient(Patient patient)
    {
        //Status displays 1 if successfully inserted data or else error
        int status = 0;
        try
        {
            Connection conn = dU.getConnection();
            PreparedStatement ps = conn.prepareStatement("UPDATE patient SET surName=?, firstName=?, nickname=?, DateOfBirth=?, length=?, weight=? WHERE  patientId=?");
            //set parameters of query here but using the values for the patient object
            ps.setString(1, patient.getSurName());
            ps.setString(2, patient.getFirstName());
            ps.setString(3, patient.getNickname());
            ps.setDate(4, java.sql.Date.valueOf(patient.getDateOfBirth()));
            ps.setDouble(5, patient.getLength());
            ps.setDouble(6, patient.getWeight());
            ps.setInt(7, patient.getPatientId());
            status = ps.executeUpdate();  //If successful status should return 1
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return status;
    }

    public int updateNickname(Patient patient)
    {
        //Status displays 1 if successfully inserted data or else error
        int status = 0;
        try
        {
            Connection conn = dU.getConnection();
            PreparedStatement ps = conn.prepareStatement("UPDATE patient SET nickname=? WHERE  patientId=?");
            //set parameters of query here but using the values for the patient object
            ps.setString(1, patient.getNickname());
            ps.setInt(2, patient.getPatientId());
            status = ps.executeUpdate();  //If successful status should return 1
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return status;
    }

    //This method deletes a patient from the DB by patientId, returns status
    public int deletePatient(int patientId)
    {
        //Status displays 1 if successfully deleted data or else error
        int status = 0;
        try
        {
            Connection conn = dU.getConnection();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM patient where patientId = ?");
            ps.setInt(1, patientId);
            status = ps.executeUpdate();  //If successful status should return 1

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return status;
    }
}
