package ru.job4j.dreamjob.dream.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.dream.model.Candidate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CandidateDBStore {
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
                    result.add(new Candidate(res.getInt("id"),
                            res.getString("name"), res.getBytes("image")));
                }
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return result;
    }

    public Candidate add(Candidate candidate) {
        try (Connection cn = pool.getConnection(); PreparedStatement statement =
                cn.prepareStatement("INSERT INTO candidates(name, image) VALUES (?, ?)",
                        PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, candidate.getName());
            statement.setBytes(2, candidate.getPhoto());
            statement.execute();
            try (ResultSet res = statement.getGeneratedKeys()) {
                if (res.next()) {
                    candidate.setId(res.getInt("id"));
                }
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return candidate;

    }

    public Candidate findById(int id) {
        try (Connection cn = pool.getConnection(); PreparedStatement statement =
                cn.prepareStatement("SELECT * FROM candidates where id = ?")) {
            statement.setInt(1, id);
            try (ResultSet res = statement.executeQuery()) {
                if (res.next()) {
                    return new Candidate(res.getInt("id"),
                            res.getString("name"), res.getBytes("image"));
                }
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return null;
    }

    public void update(Candidate candidate) {
        try (Connection cn = pool.getConnection(); PreparedStatement statement =
                cn.prepareStatement("UPDATE candidates SET name = ?, image = ?")) {
            statement.setString(1, candidate.getName());
            statement.setBytes(2, candidate.getPhoto());
            statement.execute();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }
}
