package data;

import business.Facades.ServicesFacade;
import business.Station.Service;
import business.Station.Workstation;

import java.sql.*;
import java.util.*;

public class WorkstationDAO implements Map<Integer, Workstation> {
    private static WorkstationDAO singleton = null;

    private WorkstationDAO() {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS workstations (" +
                    "Id INT NOT NULL PRIMARY KEY," +
                    "IsAvailable BOOLEAN DEFAULT NULL," +
                    "Service VARCHAR(125) DEFAULT NULL," +
                    "MechanicID INT DEFAULT NULL," +
                    "CurrentService VARCHAR(255) DEFAULT NULL," +
                    "CurrentClient INT DEFAULT NULL," +
                    "CurrentVehicle INT DEFAULT NULL)";
            stm.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    public static WorkstationDAO getInstance() {
        if (WorkstationDAO.singleton == null) {
            WorkstationDAO.singleton = new WorkstationDAO();
        }
        return WorkstationDAO.singleton;
    }

    @Override
    public int size() {
        int i = 0;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT count(*) FROM workstations")) {
            if (rs.next()) {
                i = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return i;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        boolean r;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT Id FROM workstations WHERE Id='" + key.toString() + "'")) {
            r = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }

    @Override
    public boolean containsValue(Object value) {
        Workstation workstation = (Workstation) value;
        return this.containsKey(workstation.getID());
    }

    @Override
    public Workstation get(Object key) {
        Workstation workstation = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT * FROM workstations WHERE Id='" + key + "'")) {

            if (rs.next()) {
                workstation = new Workstation(
                        new Service(ServicesFacade.valueOf(rs.getString("Service"))),
                        rs.getInt("MechanicID"),
                        rs.getInt("Id"),
                        rs.getBoolean("IsAvailable"),
                        rs.getString("CurrentService"),
                        rs.getInt("CurrentClient"),
                        rs.getInt("CurrentVehicle"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return workstation;
    }

    @Override
    public Workstation put(Integer key, Workstation workstation) {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             PreparedStatement pstmtUpdate = conn.prepareStatement(
                     "UPDATE workstations SET IsAvailable=?, Service=?, MechanicID=?, CurrentService=?, CurrentClient=?, CurrentVehicle=? WHERE Id=?");
             PreparedStatement pstmtInsert = conn.prepareStatement(
                     "INSERT INTO workstations (Id, IsAvailable, Service, MechanicID, CurrentService, CurrentClient, CurrentVehicle) VALUES (?, ?, ?, ?, ?, ?, ?)")) {

            if (this.containsKey(key)) {
                System.out.println("Updating existing record for key: " + key);
                pstmtUpdate.setBoolean(1, workstation.isAvailable());
                pstmtUpdate.setString(2, workstation.getService().toString());
                pstmtUpdate.setInt(3, workstation.getMechanic());
                pstmtUpdate.setString(4, workstation.getCurrentService());
                pstmtUpdate.setInt(5, workstation.getCurrentClientID());
                pstmtUpdate.setInt(6, workstation.getCurrentVehicleID());
                pstmtUpdate.setInt(7, key);
                pstmtUpdate.executeUpdate();
            } else {
                System.out.println("Inserting new record for key: " + key);
                pstmtInsert.setInt(1, key);
                pstmtInsert.setBoolean(2, workstation.isAvailable());
                pstmtInsert.setString(3, workstation.getService().toString());
                pstmtInsert.setInt(4, workstation.getMechanic());
                pstmtInsert.setString(5, workstation.getCurrentService());
                pstmtInsert.setInt(6, workstation.getCurrentClientID());
                pstmtInsert.setInt(7, workstation.getCurrentVehicleID());
                pstmtInsert.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return workstation;
    }

    @Override
    public Workstation remove(Object key) {
        Workstation workstation = this.get(key);
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("DELETE FROM workstations WHERE Id='" + key + "'");
        } catch (Exception e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return workstation;
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends Workstation> workstations) {
        for (Workstation w : workstations.values()) {
            this.put(w.getID(), w);
        }
    }

    @Override
    public void clear() {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("TRUNCATE workstations");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    @Override
    public Set<Integer> keySet() {
        Set<Integer> keySet = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT Id FROM workstations")) {
            while (rs.next()) {
                keySet.add(rs.getInt("Id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return keySet;
    }

    @Override
    public Collection<Workstation> values() {
        Collection<Workstation> res = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT Id FROM workstations")) {
            while (rs.next()) {
                int id = rs.getInt("Id");
                Workstation w = this.get(id);
                res.add(w);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }

    @Override
    public Set<Entry<Integer, Workstation>> entrySet() {
        Set<Entry<Integer, Workstation>> entrySet = new HashSet<>();

        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT * FROM workstations")) {

            while (rs.next()) {
                Integer id = rs.getInt("Id");
                Workstation workstation = new Workstation(
                        new Service(ServicesFacade.valueOf(rs.getString("Service"))),
                        rs.getInt("MechanicID"),
                        rs.getInt("Id"),
                        rs.getBoolean("IsAvailable"),
                        rs.getString("CurrentService"),
                        rs.getInt("CurrentClient"),
                        rs.getInt("CurrentVehicle"));

                entrySet.add(new AbstractMap.SimpleEntry<>(id, workstation));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }

        return entrySet;
    }
}
