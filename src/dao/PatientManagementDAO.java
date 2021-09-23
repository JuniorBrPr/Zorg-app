package dao;
//DAO

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import dbutil.DBUtil;
import pojo.Patient;

public class PatientManagementDAO {
    //CRUD operations
    //DB list maker
    public List<Patient> getAllPatients(){
        List<Patient> patientList = new ArrayList<Patient>();
        try{
            Connection conn = DBUtil.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * from patient");
            while (rs.next()){
                Patient patient = new Patient(rs.getString("patientId"), rs.getString("surName"), rs.getString("firstName"), rs.getString("nickname"), rs.getObject("DateOfBirth", LocalDate.class), rs.getInt("age"));
                patientList.add(patient);
            }
            DBUtil.closeConnection(conn);  //Close DB connection
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return patientList;
    }

    //Methods
    public Patient getPatientByid(String patientId) {
        Patient patient = null;
        try
        {
            Connection conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM patient WHERE patientId = ?");
            ps.setString(1, patientId);
            ResultSet rs = ps.executeQuery();
            //iterate through results
            while(rs.next())
            {
                patient = new Patient(rs.getString("patientId"), rs.getString("surName"), rs.getString("firstName"), rs.getString("nickname"), rs.getObject("DateOfBirth", LocalDate.class), rs.getInt("age"));
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return patient;
    }

    public int addPatient(Patient patient) {
        //Status displays 1 if successfully inserted data or error
        int status = 0;
        try
        {
            Connection conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO patient VALUES(?,?,?,?,?,?)");
            //set parameters of query here but using the values for the product object
            ps.setString(1, patient.getPatientId());
            ps.setString(2, patient.getSurName());
            ps.setString(3, patient.getFirstName());
            ps.setString(4, patient.getNickname());
            ps.setDate(5, java.sql.Date.valueOf(patient.getDateOfBirth()));
            //Maybe drop the age column
            ps.setInt(6, patient.getAge());
            //ps.setDouble(7, patient.getLength());
            //ps.setDouble(8, patient.getWeight());
            status = ps.executeUpdate();  //If successful status should return 1
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return status;
    }

    //Updates patient in DB
    public int updatePatient(Patient patient)
    {
        int status = 0;
        try
        {
            Connection conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement("UPDATE patient SET surName=?, firstName=?, nickname=?, DateOfBirth=?, age=? WHERE  patientId=?");
            //set parameters of query here but using the values for the product object
            ps.setString(1, patient.getSurName());
            ps.setString(2, patient.getFirstName());
            ps.setString(3, patient.getNickname());
            ps.setDate(4, java.sql.Date.valueOf(patient.getDateOfBirth()));
            //Drop age
            ps.setInt(5, patient.getAge());
            //ps.setDouble(6, patient.getLength());
            //ps.setDouble(7, patient.getWeight());
            //ID should be index 8
            ps.setString(6, patient.getPatientId());
            status = ps.executeUpdate();  //If successful status should return 1
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return status;
    }

    //Delete patient in DB
    public int deletePatient(String patientId)
    {
        int status = 0;
        try
        {
            Connection conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM patient where patientId = ?");
            ps.setString(1, patientId);
            status = ps.executeUpdate();  //If successful status should return 1

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return status;
    }
}
