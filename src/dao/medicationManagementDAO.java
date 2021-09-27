package dao;

import dbutil.DBUtil;
import pojo.Patient;
import pojo.medication;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class medicationManagementDAO {
    //CRUD Operations
    //DB list maker
    public List<medication> getAllMeds(){
        List<medication> medicationList = new ArrayList<medication>();
        try{
            Connection conn = DBUtil.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * from medication");
            while (rs.next()){
                medication medication = new medication(rs.getInt("medId"), rs.getString("medName"), rs.getString("dosage"), rs.getString("manufacturer"), rs.getInt("patientId"));
                medicationList.add(medication);
            }
            DBUtil.closeConnection(conn);  //Close DB connection
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return medicationList;
    }
    //Methods
    //Add medication to DB
    public int addMed(medication medication){
        int status = 0;
        try
        {
            Connection conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO medication VALUES(?,?,?,?,?)");
            //set parameters of query here but using the values for the product object
            ps.setInt(1,medication.getMedId());
            ps.setString(2, medication.getMedName());
            ps.setString(3, medication.getDosage());
            ps.setString(4, medication.getManufacturer());
            ps.setInt(5, medication.getPatientId());
            status = ps.executeUpdate();  //If successful status should return 1
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return status;
    }
    //Delete medication from DB
    public int deleteMedication(int medId)
    {
        int status = 0;
        try
        {
            Connection conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM medication where medId = ?");
            ps.setInt(1, medId);
            status = ps.executeUpdate();  //If successful status should return 1
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return status;
    }
    //Get medication by ID
    public medication getMedicationById(int medId) {
        medication medication = null;
        try
        {
            Connection conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM medication WHERE medId = ?");
            ps.setInt(1, medId);
            ResultSet rs = ps.executeQuery();
            //iterate through results
            while(rs.next())
            {
                medication = new medication(rs.getInt("medId"), rs.getString("medName"), rs.getString("dosage"), rs.getString("manufacturer"), rs.getInt("patientId"));
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return medication;
    }
    //Update medication
    public int updateMedication(medication medication)
    {
        int status = 0;
        try
        {
            Connection conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement("UPDATE medication SET medName=?, dosage=?, manufacturer=?, patientId=? WHERE  medId=? ");
            //set parameters of query here but using the values for the medication object
            ps.setString(1, medication.getMedName());
            ps.setString(2, medication.getDosage());
            ps.setString(3, medication.getManufacturer());
            ps.setInt(4, medication.getPatientId());
            ps.setInt(5, medication.getMedId());
            status = ps.executeUpdate();  //If successful status should return 1
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return status;
    }
}
