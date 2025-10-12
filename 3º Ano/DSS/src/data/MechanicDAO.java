package data;

import business.Facades.ServicesFacade;
import business.Station.Mechanic;
import business.Station.Service;
import business.Station.Shift;

import java.sql.*;
import java.time.LocalTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MechanicDAO implements Map<Integer, Mechanic> {
    private static MechanicDAO singleton = null;

    private MechanicDAO() {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS mechanics (" +
                    "Name VARCHAR(75)," +
                    "Skills VARCHAR(255)," +
                    "Shifts VARCHAR(255)," +
                    "Id INT NOT NULL PRIMARY KEY)";
            stm.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    public static MechanicDAO getInstance() {
        if (MechanicDAO.singleton == null) {
            MechanicDAO.singleton = new MechanicDAO();
        }
        return MechanicDAO.singleton;
    }

    @Override
    public int size() {
        int count = 0;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT count(*) FROM mechanics")) {
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return count;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        boolean exists;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT Id FROM mechanics WHERE Id=" + key)) {
            exists = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return exists;
    }

    @Override
    public boolean containsValue(Object value) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public Mechanic get(Object key) {
        Mechanic mechanic = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT * FROM mechanics WHERE Id='" + key + "'")) {

            if (rs.next()) {
                mechanic = new Mechanic(
                        rs.getString("Name"),
                        new Service(ServicesFacade.valueOf(rs.getString("Skills"))),
                        extractShiftFromString(rs.getString("Shifts")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return mechanic;
    }

    @Override
    public Mechanic put(Integer key, Mechanic mechanic) {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             PreparedStatement stm = conn.prepareStatement(
                     "INSERT INTO mechanics (Name, Skills, Shifts, Id) VALUES (?, ?, ?, ?) " +
                             "ON DUPLICATE KEY UPDATE Name=VALUES(Name), Skills=VALUES(Skills), Shifts=VALUES(Shifts)")) {

            stm.setString(1, mechanic.getName());
            stm.setString(2, mechanic.getGenericSkill().toString());
            stm.setString(3, shiftToString(mechanic.getShift()));
            stm.setInt(4, key);

            stm.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error updating database", e);
        }

        return mechanic;
    }

    @Override
    public Mechanic remove(Object key) {
        Mechanic mechanic = this.get(key);
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("DELETE FROM mechanics WHERE Id='" + key + "'");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return mechanic;
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends Mechanic> mechanics) {
        for (Map.Entry<? extends Integer, ? extends Mechanic> entry : mechanics.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void clear() {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("DELETE FROM mechanics");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    @Override
    public Set<Integer> keySet() {
        Set<Integer> keys = new HashSet<>();

        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT Id FROM mechanics")) {
            while (rs.next()) {
                keys.add(rs.getInt("Id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }

        return keys;
    }

    @Override
    public Collection<Mechanic> values() {
        Collection<Mechanic> mechanics = new HashSet<>();

        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT * FROM mechanics")) {
            while (rs.next()) {
                mechanics.add(extractMechanicFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }

        return mechanics;
    }

    @Override
    public Set<Entry<Integer, Mechanic>> entrySet() {
        Set<Entry<Integer, Mechanic>> entries = new HashSet<>();

        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT * FROM mechanics")) {
            while (rs.next()) {
                int id = rs.getInt("Id");
                Mechanic mechanic = extractMechanicFromResultSet(rs);
                entries.add(Map.entry(id, mechanic));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }

        return entries;
    }

    private Mechanic extractMechanicFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("Id");
        String name = rs.getString("Name");
        Service skill = ServicesFacade.valueOf(rs.getString("Skills"));

        Shift shift = extractShiftFromString(rs.getString("Shifts"));

        Mechanic mechanic = new Mechanic(name, skill, shift);
        mechanic.setId(id);

        return mechanic;
    }

    private String shiftToString(Shift shift) {
        return shift.getStartTime() + "-" + shift.getEndTime();
    }

    private Shift extractShiftFromString(String shiftString) {
        String[] timeValues = shiftString.split("-");
        LocalTime startTime = LocalTime.parse(timeValues[0]);
        LocalTime endTime = LocalTime.parse(timeValues[1]);
        return new Shift(startTime, endTime);
    }

}
