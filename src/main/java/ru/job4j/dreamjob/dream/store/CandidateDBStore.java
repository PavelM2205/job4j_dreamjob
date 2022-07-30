package ru.job4j.dreamjob.dream.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.dream.model.Candidate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CandidateDBStore {
    private static final Logger LOG = LoggerFactory.getLogger(CandidateDBStore.class);
    private final BasicDataSource pool;

    public CandidateDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public List<Candidate> findAll() {
        List<Candidate> result = new ArrayList<>();
        try (Connection cn = pool.getConnection(); PreparedStatement statement =
                cn.prepareStatement("SELECT * FROM candidates")) {
            try (ResultSet res = statement.executeQuery()) {
                while (res.next()) {
                    result.add(new Candidate(
                            res.getInt("id"),
                            res.getString("name"),
                            res.getString("description"),
                            res.getTimestamp("created").toLocalDateTime(),
                            res.getBytes("image")));
                }
            }
        } catch (Exception exc) {
            LOG.error("Exception: ", exc);
        }
        return result;
    }

    public Candidate add(Candidate candidate) {
        try (Connection cn = pool.getConnection(); PreparedStatement statement =
                cn.prepareStatement(
                        "INSERT INTO candidates(name, description, created, image) "
                                + "VALUES (?, ?, ?, ?)",
                        PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, candidate.getName());
            statement.setString(2, candidate.getDescription());
            statement.setTimestamp(3, Timestamp.valueOf(candidate.getCreated()));
            statement.setBytes(4, candidate.getPhoto());
            statement.execute();
            try (ResultSet res = statement.getGeneratedKeys()) {
                if (res.next()) {
                    candidate.setId(res.getInt("id"));
                }
            }
        } catch (Exception exc) {
            LOG.error("Exception: ", exc);
        }
        return candidate;

    }

    public Candidate findById(int id) {
        try (Connection cn = pool.getConnection(); PreparedStatement statement =
                cn.prepareStatement("SELECT * FROM candidates WHERE id = ?")) {
            statement.setInt(1, id);
            try (ResultSet res = statement.executeQuery()) {
                if (res.next()) {
                    return new Candidate(
                            res.getInt("id"),
                            res.getString("name"),
                            res.getString("description"),
                            res.getTimestamp("created").toLocalDateTime(),
                            res.getBytes("image"));
                }
            }
        } catch (Exception exc) {
            LOG.error("Exception: ", exc);
        }
        return null;
    }

    public void update(Candidate candidate) {
        try (Connection cn = pool.getConnection(); PreparedStatement statement =
                cn.prepareStatement(
                        "UPDATE candidates SET name = ?, description = ?, "
                                + "created = ?, image = ? WHERE id = ?")) {
            statement.setString(1, candidate.getName());
            statement.setString(2, candidate.getDescription());
            statement.setTimestamp(3, Timestamp.valueOf(candidate.getCreated()));
            statement.setBytes(4, candidate.getPhoto());
            statement.setInt(5, candidate.getId());
            statement.execute();
        } catch (Exception exc) {
            LOG.error("Exception: ", exc);
        }
    }
}
