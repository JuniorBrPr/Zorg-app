package dao;

import dbutil.DBUtil;
import pojo.Medication;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

//Practically the same as the patientDAO, just for the medicines
public class medicationManagementDAO {
    //CRUD Operations
    //This method makes a list of the meds a patient has by patientId and returns that list
    public List<Medication> getAllMeds(int patientId){
        List<Medication> medicationList = new ArrayList<Medication>();
        try{
            Connection conn = DBUtil.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * from medication ");
            while (rs.next()){
                Medication medication = new Medication(rs.getInt("medId"), rs.getString("medName"), rs.getString("dosage"), rs.getString("manufacturer"), rs.getInt("patientId"));
                if(medication.getPatientId() == patientId){
                    medicationList.add(medication);
                }
            }
            DBUtil.closeConnection(conn);  //Close DB connection
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return medicationList;
    }
    //This method adds a medication to the DB, returns status
    public int addMed(Medication medication){
        int status = 0;
        try {
            Connection conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO medication(medName, dosage, manufacturer, patientId) VALUES(?,?,?,?)");
            //set parameters of query here but using the values for the product object
            //ps.setInt(1,medication.getMedId());
            ps.setString(1, medication.getMedName());
            ps.setString(2, medication.getDosage());
            ps.setString(3, medication.getManufacturer());
            ps.setInt(4, medication.getPatientId());
            status = ps.executeUpdate();  //If successful status should return 1
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return status;
    }
    //This method deletes a medication from the DB given the medId, returns status
    public int deleteMedication(int medId) {
        int status = 0;
        try {
            Connection conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM medication where medId = ?");
            ps.setInt(1, medId);
            status = ps.executeUpdate();  //If successful status should return 1
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return status;
    }
    //This medication gets a medication from the DB given the medId, returns that medication
    public Medication getMedicationByMedId(int medId) {
        Medication medication = null;
        try
        {
            Connection conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM medication WHERE medId = ?");
            ps.setInt(1, medId);
            ResultSet rs = ps.executeQuery();
            //iterate through results
            while(rs.next())
            {
                medication = new Medication(rs.getInt("medId"),rs.getString("medName"), rs.getString("dosage"), rs.getString("manufacturer"), rs.getInt("patientId"));
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return medication;
    }
    //This medication updates a medication in the DB, returns status
    public int updateMedication(Medication medication)
    {
        int status = 0;
        try
        {
            Connection conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement("UPDATE medication SET medName=?, dosage=?, manufacturer=? WHERE  medId=? ");
            //set parameters of query here but using the values for the medication object
            ps.setString(1, medication.getMedName());
            ps.setString(2, medication.getDosage());
            ps.setString(3, medication.getManufacturer());
            ps.setInt(4, medication.getMedId());
            status = ps.executeUpdate();  //If successful status should return 1
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return status;
    }
}
