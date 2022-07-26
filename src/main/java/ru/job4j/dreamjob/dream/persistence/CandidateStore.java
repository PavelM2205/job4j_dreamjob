package ru.job4j.dreamjob.dream.persistence;

import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.dream.model.Candidate;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class CandidateStore {
    private final Map<Integer, Candidate> store = new ConcurrentHashMap<>();
    private final AtomicInteger number = new AtomicInteger();

    public CandidateStore() {
        store.put(number.incrementAndGet(), new Candidate(1, "Pavel",
                "Java Middle", LocalDateTime.now()));
        store.put(number.incrementAndGet(), new Candidate(2, "Ivan",
                "Python junior", LocalDateTime.now()));
        store.put(number.incrementAndGet(), new Candidate(3, "Maxim",
                "Java senior", LocalDateTime.now()));
    }

    public Collection<Candidate> findAll() {
        return store.values();
    }

    public void addCandidate(Candidate candidate) {
        candidate.setId(number.incrementAndGet());
        store.put(candidate.getId(), candidate);
    }

    public Candidate findById(int id) {
        return store.get(id);
    }

    public void update(Candidate candidate) {
        store.replace(candidate.getId(), candidate);
    }
}
