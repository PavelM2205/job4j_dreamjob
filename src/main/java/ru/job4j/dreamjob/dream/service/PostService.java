package ru.job4j.dreamjob.dream.service;

import ru.job4j.dreamjob.dream.model.Post;
import ru.job4j.dreamjob.dream.persistence.PostStore;

import java.util.Collection;

public class PostService {
    private static final PostService POST_SERVICE = new PostService();
    private final PostStore store;

    private PostService() {
        this.store = PostStore.instOf();
    }

    public static PostService instOf() {
        return POST_SERVICE;
    }

    public Collection<Post> findAll() {
        return store.findAll();
    }

    public void addPost(Post post) {
        store.addPost(post);
    }

    public Post findById(int id) {
        return store.findById(id);
    }

    public void update(Post post) {
        store.update(post);
    }
}
