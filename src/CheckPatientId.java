import dbutil.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckPatientId{
    DBUtil db = new DBUtil();
    public boolean check(int patientId) throws SQLException {
            Connection conn = db.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM patient WHERE patientId = ?");
            ps.setInt(1, patientId);
            ResultSet rs = ps.executeQuery();
            return rs.next();
    }
}