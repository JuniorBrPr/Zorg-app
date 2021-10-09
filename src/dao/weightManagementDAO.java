package dao;

import dbutil.DBUtil;
import pojo.Weight;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class weightManagementDAO {
    DBUtil dU = new DBUtil();
    //CRUD Operations
    //This method makes a list of the weightdata of a patient by patientId and returns that list
    public List<Weight> getAllWeightData(int patientId){
        List<Weight> weightDataList = new ArrayList<Weight>();
        try{
            Connection conn = dU.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM patient ORDER BY patient.DateOfBirth ASC ");
            while (rs.next()){
                Weight weight = new Weight(rs.getInt("weightId"), rs.getDouble("weight"),rs.getObject("weightDate" , LocalDate.class), rs.getInt("patientId"));
                if(weight.getPatientId() == patientId){
                    weightDataList.add(weight);
                }
            }
            dU.closeConnection(conn);  //Close DB connection
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return weightDataList;
    }
    public int addWeight(Weight weight){
        int status = 0;
        try {
            Connection conn = dU.getConnection();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO weight(weight, weightDate, patientId) VALUES(?,?,?)");
            //set parameters of query here but using the values for the product object
            //ps.setInt(1,medication.getMedId());
            ps.setDouble(1, weight.getWeight());
            ps.setObject(2, weight.getWeightDate());
            ps.setInt(3, weight.getPatientId());
            status = ps.executeUpdate();  //If successful status should return 1
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return status;
    }
    public int deleteWeight(int weightId) {
        int status = 0;
        try {
            Connection conn = dU.getConnection();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM weight where weightId = ?");
            ps.setInt(1, weightId);
            status = ps.executeUpdate();  //If successful status should return 1
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return status;
    }
    public Weight getWeightByWeightId(int weightId) {
        Weight weight = null;
        try
        {
            Connection conn = dU.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM weight WHERE weightId = ?");
            ps.setInt(1, weightId);
            ResultSet rs = ps.executeQuery();
            //iterate through results
            while(rs.next())
            {
                weight = new Weight(rs.getInt("weightId"), rs.getDouble("weight"),rs.getObject("weightDate" , LocalDate.class), rs.getInt("patientId"));
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return weight;
    }
    public int updateWeightData(Weight weight)
    {
        int status = 0;
        try
        {
            Connection conn = dU.getConnection();
            PreparedStatement ps = conn.prepareStatement("UPDATE weight SET weight=?, weightDate=? WHERE  weightId ");
            //set parameters of query here but using the values for the medication object
            ps.setDouble(1, weight.getWeight());
            ps.setObject(2, weight.getWeightDate());
            ps.setInt(3, weight.getWeightId());
            status = ps.executeUpdate();  //If successful status should return 1
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return status;
    }
}
