package ru.job4j.dreamjob.dream.store;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.dream.model.Post;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
@Repository
public class PostStore {
    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();
    private final AtomicInteger number = new AtomicInteger();

    public PostStore() {
        posts.put(number.incrementAndGet(), new Post(1, "Junior Java Job",
                "Very good job", LocalDateTime.now()));
        posts.put(number.incrementAndGet(), new Post(2, "Middle Java Job",
                "You don't be stupid", LocalDateTime.now()));
        posts.put(number.incrementAndGet(), new Post(3, "Senior Java Job",
                "We are searching a master", LocalDateTime.now()));
    }

    public Collection<Post> findAll() {
        return posts.values();
    }

    public void addPost(Post post) {
        post.setId(number.incrementAndGet());
        posts.put(post.getId(), post);
    }

    public Post findById(int id) {
        return posts.get(id);
    }

    public void update(Post post) {
        posts.replace(post.getId(), post);
    }
}
