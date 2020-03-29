package FINAL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDAOlmpl implements PatientDAO {

    public static String DriverName = "org.sqlite.JDBC";
    public static String url = "jdbc:sqlite:D:/FINAL_OOP_359411110025/Hospital.sqlite";
    public static Connection conn = null;

    public static final String GET_ALL_PAT = "Select * from patient";
    public static final String ADD_NEW_PAT = "Insert Into patient(p_id,p_name,p_gender,p_age,p_address,p_blood_result)values(?,?,?,?,?,?)";
    public static final String FIND_PAT_BY_ID = "Select * from patient Where p_id=?";
    public static final String UPDATE_PAT = "update patient set p_name=?,p_gender=?,p_age=?,p_address=?,p_blood_result=? Where p_id";
    public static final String DELETE_PAT = "delete from patient where p_id=?" ;


    private static PatientDAOlmpl instance = new PatientDAOlmpl();

    public static PatientDAOlmpl getInstance() {
        return instance;

    }//instance

    private PatientDAOlmpl () {
        try {
            Class.forName(DriverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Patient> getAllPAT() {
        List<Patient> PAT = new ArrayList<>();
        try {
            conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(GET_ALL_PAT);

            while(rs.next()){
                int id = rs.getInt(1);
                String p_name = rs.getString(2);
                String p_gender = rs.getString(3);
                int p_age = rs.getInt(4);
                String p_address=rs.getString(5);
                String p_blood_result = rs.getString(6);

                PAT.add(new Patient(id,p_name,p_gender,p_age,p_address,p_blood_result));

            }
            rs.close();
            stmt.close();
            conn.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return PAT;
    }

    @Override
    public void addPAT(Patient PAT) {
        try {
            conn = DriverManager.getConnection(url);
            PreparedStatement  ps = conn.prepareStatement(ADD_NEW_PAT);

            ps.setInt(1,PAT.getP_id());
            ps.setString(2,PAT.getP_name());
            ps.setString(3,PAT.getP_gender());
            ps.setInt(4,PAT.getP_age());
            ps.setString(5,PAT.getP_address());
            ps.setString(6,PAT.getP_address());

            if (ps.execute() == false) {
                System.out.println("Already add new Patient.");
            } else {
                System.out.println("Could not add new Patient");
                System.exit(1);
            }
            ps.close();
            conn.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @Override
    public Patient findPAT(int id) {
        Patient PAT = null;
        try {
            conn = DriverManager.getConnection(url);
            PreparedStatement ps = conn.prepareStatement(FIND_PAT_BY_ID);

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int ID = rs.getInt(1);
                String p_name = rs.getString(2);
                String p_gender = rs.getString(3);
                int p_age = rs.getInt(4);
                String p_address = rs.getString(5);
                String p_p_blood_result = rs.getString(6);

                PAT = new Patient(ID, p_name, p_gender, p_age, p_address, p_p_blood_result);
                System.out.print("Could not found Patient with ID: " + id);

            }
            rs.close();
            ps.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return PAT;
    }

    @Override
    public void updatePAT(Patient PAT) {

        try {
            conn = DriverManager.getConnection(url);
            PreparedStatement ps = conn.prepareStatement(UPDATE_PAT);

            ps.setString(1,PAT.getP_name());
            ps.setString(2,PAT.getP_gender());
            ps.setInt(3,PAT.getP_age());
            ps.setString(4,PAT.getP_address());
            ps.setString(5,PAT.getP_blood_result());
            ps.setInt(6,PAT.getP_id());



            int ra = ps.executeUpdate();
            if (ra != 0) {
                System.out.print("Patiebt with ID"+PAT.getP_id());
            } else {
                System.out.print("Could not Update with ID"+PAT.getP_id());
            }

            ps.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void deletePAT(int id) {
        try {
            conn = DriverManager.getConnection(url);
            PreparedStatement ps = conn.prepareStatement(DELETE_PAT);

            ps.setInt(1, id);
            int rs = ps.executeUpdate();
            if (rs != 0) {
                System.out.println("Patiebt with ID" + "was id " + id);
            } else {
                System.out.println("Could not delete Patient "+"with id "+id);
            }
            ps.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}//class
