package ru.job4j.dreamjob.dream.service;

import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.dream.model.Candidate;
import ru.job4j.dreamjob.dream.persistence.CandidateStore;

import java.util.Collection;

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
