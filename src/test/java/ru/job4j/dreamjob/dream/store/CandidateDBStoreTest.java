package ru.job4j.dreamjob.dream.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.dream.model.Candidate;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class CandidateDBStoreTest {
    public static BasicDataSource pool;

    @BeforeAll
    public static void loadPool() {
        pool = new Main().loadPool();
    }

    @AfterEach
    public void cleanTable() throws SQLException {
        try (PreparedStatement st = pool.getConnection().prepareStatement(
                "DELETE FROM candidates")) {
            st.execute();
        }
    }

    @Test
    void whenAddCandidate() {
        CandidateDBStore store = new CandidateDBStore(pool);
        Candidate candidate = new Candidate(
                0, "Pavel", "Java developer", LocalDateTime.now(),
                new byte[3]
        );
        store.add(candidate);
        Candidate candidateInDB = store.findById(candidate.getId());
        assertThat(candidateInDB.getName()).isEqualTo(candidateInDB.getName());
        assertThat(candidateInDB.getDescription()).isEqualTo(candidate.getDescription());
        assertThat(candidateInDB.getCreated()).isEqualTo(candidate.getCreated());
        assertThat(candidateInDB.getPhoto()).isEqualTo(candidate.getPhoto());
    }

    @Test
    public void whenAddPostThenMustBeIdInstallIntoPost() {
        CandidateDBStore store = new CandidateDBStore(pool);
        Candidate candidate = new Candidate(
                0, "Pavel", "Java developer", LocalDateTime.now(),
                new byte[3]
        );
        store.add(candidate);
        assertThat(candidate.getId()).isNotEqualTo(0);
    }

    @Test
    public void whenAddTwoCandidatesThenFindAllReturnsListWithBoth() {
        CandidateDBStore store = new CandidateDBStore(pool);
        Candidate candidate1 = new Candidate(0, "Pavel");
        Candidate candidate2 = new Candidate(0, "Egor");
        store.add(candidate1);
        store.add(candidate2);
        List<Candidate> expected = List.of(candidate1, candidate2);
        assertThat(store.findAll()).isEqualTo(expected);
    }

    @Test
    public void whenUpdateThenMustBeChangedCandidateWithSameId() {
        CandidateDBStore store = new CandidateDBStore(pool);
        Candidate candidate = new Candidate(
                0, "Pavel", "Java developer", LocalDateTime.now(),
                new byte[3]
        );
        store.add(candidate);
        Candidate changed = new Candidate(
                candidate.getId(), "Ivan", "Python developer",
                LocalDateTime.of(2014, 6, 23, 12, 00),
                new byte[10]
        );
        store.update(changed);
        Candidate candidateInDB = store.findById(candidate.getId());
        assertThat(candidateInDB.getName()).isEqualTo(changed.getName());
        assertThat(candidateInDB.getDescription()).isEqualTo(changed.getDescription());
        assertThat(candidateInDB.getCreated()).isEqualTo(changed.getCreated());
        assertThat(candidateInDB.getPhoto()).isEqualTo(changed.getPhoto());
    }
}