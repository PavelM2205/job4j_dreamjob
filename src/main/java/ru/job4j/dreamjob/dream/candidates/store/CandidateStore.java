package ru.job4j.dreamjob.dream.candidates.store;

import ru.job4j.dreamjob.dream.candidates.model.Candidate;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CandidateStore {
    private static final CandidateStore INST = new CandidateStore();
    private final Map<Integer, Candidate> store = new ConcurrentHashMap<>();

    private CandidateStore() {
        store.put(1, new Candidate(1, "Pavel", "Java Middle",
                LocalDateTime.now()));
        store.put(2, new Candidate(2, "Ivan", "Python junior",
                LocalDateTime.now()));
        store.put(3, new Candidate(3, "Maxim", "Java senior",
                LocalDateTime.now()));
    }

    public static CandidateStore instOf() {
        return INST;
    }

    public Collection<Candidate> findAll() {
        return store.values();
    }

    public void addCandidate(Candidate candidate) {
        store.put(candidate.getId(), candidate);
    }

    public Candidate findById(int id) {
        return store.get(id);
    }

    public void update(Candidate candidate) {
        store.replace(candidate.getId(), candidate);
    }
}