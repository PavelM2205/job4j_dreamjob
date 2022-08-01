package ru.job4j.dreamjob.dream.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.dream.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDBStore {
    private static final Logger LOG = LoggerFactory.getLogger(UserDBStore.class);
    private final BasicDataSource pool;

    public UserDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public Optional<User> add(User user) {
        Optional<User> result = Optional.empty();
        try (PreparedStatement st = pool.getConnection().prepareStatement(
                "INSERT INTO users(email, password) VALUES (?, ?)",
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            st.setString(1, user.getEmail());
            st.setString(2, user.getPassword());
            st.execute();
            try (ResultSet res = st.getGeneratedKeys()) {
                if (res.next()) {
                    user.setId(res.getInt("id"));
                    result = Optional.of(user);
                }
            }
        } catch (Exception exc) {
            LOG.error("Exception: ", exc);
        }
        return result;
    }

    public User findById(int id) {
        try (PreparedStatement st = pool.getConnection().prepareStatement(
                "SELECT * FROM users WHERE id = ?")) {
            st.setInt(1, id);
            try (ResultSet res = st.executeQuery()) {
                if (res.next()) {
                    return new User(
                            res.getInt("id"), res.getString("email"),
                            res.getString("password"));
                }
            }
        } catch (Exception exc) {
            LOG.error("Exception: ", exc);
        }
        return null;
    }

    public List<User> findAll() {
        List<User> result = new ArrayList<>();
        try (PreparedStatement st = pool.getConnection().prepareStatement(
                "SELECT * FROM users")) {
            try (ResultSet res = st.executeQuery()) {
                while (res.next()) {
                    result.add(new User(
                            res.getInt("id"), res.getString("email"),
                            res.getString("password")));
                }
            }
        } catch (Exception exc) {
            LOG.error("Exception: ", exc);
        }
        return result;
    }

    public void update(User user) {
        try (PreparedStatement st = pool.getConnection().prepareStatement(
                "UPDATE users SET email = ?, password = ? WHERE id = ?")) {
            st.setString(1, user.getEmail());
            st.setString(2, user.getPassword());
            st.setInt(3, user.getId());
            st.execute();

        } catch (Exception exc) {
            LOG.error("Exception: ", exc);
        }
    }

    public Optional<User> findUserByEmailAndPwd(String email, String password) {
        Optional<User> result = Optional.empty();
        try (PreparedStatement st = pool.getConnection().prepareStatement(
                "SELECT * FROM users WHERE email = ? AND password = ?")) {
            st.setString(1, email);
            st.setString(2, password);
            try (ResultSet res = st.executeQuery()) {
                if (res.next()) {
                    result = Optional.of(new User(res.getInt("id"), res.getString("email"),
                            res.getString("password")));
                }
            }

        } catch (Exception exc) {
            LOG.error("Exception: ", exc);
        }
        return result;
    }
}
