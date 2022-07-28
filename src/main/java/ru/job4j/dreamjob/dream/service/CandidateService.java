package ru.job4j.dreamjob.dream.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.dream.model.Candidate;
import ru.job4j.dreamjob.dream.store.CandidateStore;

import java.util.Collection;

@ThreadSafe
@Service
public class CandidateService {
    private final CandidateStore store;

    public CandidateService(CandidateStore store) {
        this.store = store;
    }

    public Collection<Candidate> findAll() {
        return store.findAll();
    }

    public void addCandidate(Candidate candidate) {
        store.addCandidate(candidate);
    }

    public Candidate findById(int id) {
        return store.findById(id);
    }

    public void update(Candidate candidate) {
        store.update(candidate);
    }
}
