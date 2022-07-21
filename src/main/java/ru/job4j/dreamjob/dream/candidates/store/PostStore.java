package ru.job4j.dreamjob.dream.candidates.store;

import ru.job4j.dreamjob.dream.candidates.model.Candidate;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PostStore {
    private static final PostStore INST = new PostStore();
    private final Map<Integer, Candidate> store = new ConcurrentHashMap<>();

    private PostStore() {
        store.put(1, new Candidate(1, "Pavel", "Java Middle",
                LocalDateTime.now()));
        store.put(2, new Candidate(2, "Ivan", "Python junior",
                LocalDateTime.now()));
        store.put(3, new Candidate(3, "Maxim", "Java senior",
                LocalDateTime.now()));
    }

    public static PostStore instOf() {
        return INST;
    }

    public Collection<Candidate> findAll() {
        return store.values();
    }
}
