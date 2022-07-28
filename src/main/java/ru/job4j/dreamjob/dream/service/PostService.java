package ru.job4j.dreamjob.dream.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.dream.model.Post;
import ru.job4j.dreamjob.dream.store.PostDBStore;

import java.util.List;

@ThreadSafe
@Service
public class PostService {
    private final PostDBStore store;
    private final CityService cityService;

    public PostService(PostDBStore store, CityService cityService) {
        this.store = store;
        this.cityService = cityService;
    }

    public List<Post> findAll() {
        List<Post> result = store.findAll();
        result.forEach(
                post -> post.setCity(cityService.findById(post.getCity().getId()))
        );
        return result;
    }

    public void addPost(Post post) {
        store.add(post);
    }

    public Post findById(int id) {
        return store.findById(id);
    }

    public void update(Post post) {
        store.update(post);
    }
}
