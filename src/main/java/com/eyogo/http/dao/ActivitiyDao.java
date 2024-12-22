package com.eyogo.http.dao;

import com.eyogo.http.entity.Activity;
import com.eyogo.http.entity.User;
import com.eyogo.http.util.ConnectionManager;
import lombok.SneakyThrows;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ActivitiyDao implements Dao<Integer, Activity> {

    private static final ActivitiyDao INSTANCE = new ActivitiyDao();

    private static final String FIND_ALL = """
            SELECT *
            FROM activities
            """;

    private static final String FIND_BY_ID = """
            SELECT *
            FROM activities
            WHERE id = ?
            """;

    private static final String FIND_BY_USER_ID = """
            SELECT *
            FROM activities
            WHERE user_id = ?
            """;

    //TODO: can be enhanced to order by predefined column with creation time
    private static final String FIND_BY_USER_AND_UNIT_ID = """
            SELECT *
            FROM activities
            WHERE user_id = ? AND unit_id = ?
            ORDER BY id
            """;

    public static final String SAVE_SQL = "INSERT INTO activities (user_id, unit_id, activity_name, description, author_id) VALUES (?, ?, ?, ?, ?);";

    public static final String UPDATE_SQL = """
            UPDATE activities
            SET
              user_id = COALESCE(?, user_id),
              unit_id = COALESCE(?, unit_id),
              activity_name = COALESCE(?, activity_name),
              description = COALESCE(?, description),
              author_id = COALESCE(?, author_id)
            WHERE
              id = ?;
            """;

    public static final String DELETE_SQL = "DELETE FROM activities WHERE id = ?;";

    private ActivitiyDao() {
    }

    @Override
    public List<Activity> findAll() {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Activity> activities = new ArrayList<>();
            while (resultSet.next()) {
                Activity activity = buildActivity(resultSet);
                activities.add(activity);
            }
            return activities;
        } catch (SQLException e) {
            //TODO
            throw new RuntimeException(e);
        }
    }

    public List<Activity> findByUser(Integer userId) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_USER_ID)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Activity> activities = new ArrayList<>();
            while (resultSet.next()) {
                Activity activity = buildActivity(resultSet);
                activities.add(activity);
            }
            return activities;
        } catch (SQLException e) {
            //TODO
            throw new RuntimeException(e);
        }
    }

    public List<Activity> findByUserAndUnit(Integer userId, Integer unitId) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_USER_AND_UNIT_ID)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, unitId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Activity> activities = new ArrayList<>();
            while (resultSet.next()) {
                Activity activity = buildActivity(resultSet);
                activities.add(activity);
            }
            return activities;
        } catch (SQLException e) {
            //TODO
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Activity> findById(Integer id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(buildActivity(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            //TODO
            throw new RuntimeException(e);
        }
    }

    @Override
    @SneakyThrows//TODO
    public boolean delete(Integer id) {//TODO consider returned value
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setObject(1, id);
            int i = preparedStatement.executeUpdate();
            if (i == 1) {
                return true;
            }
        }
        return false;
    }

    @Override
    @SneakyThrows//TODO
    public void update(Activity entity) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setObject(1, entity.getUserId());
            preparedStatement.setObject(2, entity.getUnit().getId());
            preparedStatement.setObject(3, entity.getActivityName());
            preparedStatement.setObject(4, entity.getDescription());
            preparedStatement.setObject(5, entity.getAuthorId());
            preparedStatement.setObject(6, entity.getId());

            preparedStatement.executeUpdate();
        }
    }

    @Override
    @SneakyThrows//TODO
    public Activity save(Activity entity) { //TODO consider returned value
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setObject(1, entity.getUserId());
            preparedStatement.setObject(2, entity.getUnit().getId());
            preparedStatement.setObject(3, entity.getActivityName());
            preparedStatement.setObject(4, entity.getDescription());
            preparedStatement.setObject(5, entity.getAuthorId());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            entity.setId(generatedKeys.getObject("id", Integer.class));
        }
        return null;
    }

    private Activity buildActivity(ResultSet resultSet) throws SQLException {
        return new Activity(
                resultSet.getObject("id", Integer.class),
                resultSet.getObject("user_id", Integer.class),
                /*resultSet.getObject("unit_id", Integer.class)*/ null,
                resultSet.getObject("activity_name", String.class),
                resultSet.getObject("description", String.class),
                resultSet.getObject("author_id", Integer.class)
        );
    }

    public static ActivitiyDao getInstance() {
        return INSTANCE;
    }
}
