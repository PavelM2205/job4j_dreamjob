package ru.job4j.dreamjob.dream.vacancies.store;

import ru.job4j.dreamjob.dream.vacancies.model.Post;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PostStore {
    private static final PostStore INST = new PostStore();
    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();

    private PostStore() {
        posts.put(1, new Post(1, "Junior Java Job", "Very good job",
                LocalDateTime.now()));
        posts.put(2, new Post(2, "Middle Java Job", "You don't be stupid",
                LocalDateTime.now()));
        posts.put(3, new Post(3, "Senior Java Job", "We are searching a master",
                LocalDateTime.now()));
    }

    public static PostStore instOf() {
        return INST;
    }

    public Collection<Post> findAll() {
        return posts.values();
    }
}