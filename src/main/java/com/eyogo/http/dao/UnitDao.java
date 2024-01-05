package com.eyogo.http.dao;

import com.eyogo.http.entity.Activity;
import com.eyogo.http.entity.Unit;
import com.eyogo.http.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UnitDao implements Dao<Integer, Unit> {

    private static final UnitDao INSTANCE = new UnitDao();

    private static final String FIND_ALL = """
            SELECT *
            FROM units
            """;

    private static final String FIND_BY_ID = """
            SELECT *
            FROM units
            WHERE id = ?
            """;

    private static final String FIND_SUB_UNITS = """
            SELECT *
            FROM units
            WHERE parent_id = ?
            """;

    private UnitDao() {
    }

    @Override
    public List<Unit> findAll() {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Unit> units = new ArrayList<>();
            while (resultSet.next()) {
                Unit unit = buildUnit(resultSet);
                units.add(unit);
            }
            return units;
        } catch (SQLException e) {
            //TODO
            throw new RuntimeException(e);
        }
    }

    public List<Unit> findSubUnits(Integer unitId) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_SUB_UNITS)) {
            preparedStatement.setInt(1, unitId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Unit> units = new ArrayList<>();
            while (resultSet.next()) {
                Unit unit = buildUnit(resultSet);
                units.add(unit);
            }
            return units;
        } catch (SQLException e) {
            //TODO
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Unit> findById(Integer id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(buildUnit(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            //TODO
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public void update(Unit entity) {

    }

    @Override
    public Unit save(Unit entity) {
        return null;
    }

    private Unit buildUnit(ResultSet resultSet) throws SQLException {
        return new Unit(
                resultSet.getObject("id", Integer.class),
                resultSet.getObject("unit_name", String.class),
                resultSet.getObject("parent_id", Integer.class),
                resultSet.getObject("managed_by_admin", Boolean.class)
        );
    }

    public static UnitDao getInstance() {
        return INSTANCE;
    }

}
