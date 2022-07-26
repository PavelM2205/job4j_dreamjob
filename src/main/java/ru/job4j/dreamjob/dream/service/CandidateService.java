package ru.job4j.dreamjob.dream.service;

import ru.job4j.dreamjob.dream.model.Candidate;
import ru.job4j.dreamjob.dream.persistence.CandidateStore;

import java.util.Collection;

public class CandidateService {
    private static final CandidateService CANDIDATES_SERVICE = new CandidateService();
    private final CandidateStore store;

    private CandidateService() {
        this.store = CandidateStore.instOf();
    }

    public static CandidateService instOf() {
        return CANDIDATES_SERVICE;
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
