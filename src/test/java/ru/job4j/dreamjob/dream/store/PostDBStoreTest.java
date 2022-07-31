package ru.job4j.dreamjob.dream.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.dream.model.City;
import ru.job4j.dreamjob.dream.model.Post;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class PostDBStoreTest {
    private static BasicDataSource pool;

    @BeforeAll
    public static void loadPool() {
        pool = new Main().loadPool();
    }

    @AfterEach
    public void cleanTable() throws SQLException {
        try (PreparedStatement st = pool.getConnection().prepareStatement(
                "DELETE FROM post")) {
            st.execute();
        }
    }

    @Test
    public void whenCreatePost() {
        PostDBStore store = new PostDBStore(pool);
        Post post = new Post(
                0, "Java Job", "The funny work", LocalDateTime.now(),
                false);
        City city = new City(1);
        post.setCity(city);
        store.add(post);
        Post postInDB = store.findById(post.getId());
        assertThat(postInDB.getName()).isEqualTo(post.getName());
        assertThat(postInDB.getDescription()).isEqualTo(post.getDescription());
        assertThat(postInDB.getCreated()).isEqualTo(post.getCreated());
        assertThat(postInDB.isVisible()).isEqualTo(post.isVisible());
        assertThat(postInDB.getCity().getId()).isEqualTo(post.getCity().getId());
    }

    @Test
    public void whenAddPostThenMustBeIdInstallIntoPost() {
        PostDBStore store = new PostDBStore(pool);
        Post post = new Post(0, "Java Job");
        store.add(post);
        assertThat(post.getId()).isNotEqualTo(0);
    }

    @Test
    public void whenAddTwoPostsThenFindAllReturnListWithBoth() {
        PostDBStore store = new PostDBStore(pool);
        Post post1 = new Post(0, "Java Job");
        Post post2 = new Post(0, "Python Job");
        store.add(post1);
        store.add(post2);
        List<Post> expected = List.of(post1, post2);
        assertThat(store.findAll()).isEqualTo(expected);
    }

    @Test
    public void whenUpdateThenMustBeChangedPostWithSameId() {
        PostDBStore store = new PostDBStore(pool);
        Post post = new Post(
                0, "Java Job", "The funny work", LocalDateTime.now(),
                true);
        City city = new City(1);
        post.setCity(city);
        store.add(post);
        Post changed = new Post(post.getId(), "Kotlin Job", "Heavy Job",
                LocalDateTime.of(2005, 8, 24, 12, 0),
                false);
        City changedCity = new City(2);
        changed.setCity(changedCity);
        store.update(changed);
        Post postInDb = store.findById(post.getId());
        assertThat(postInDb.getName()).isEqualTo(changed.getName());
        assertThat(postInDb.getDescription()).isEqualTo(changed.getDescription());
        assertThat(postInDb.getCreated()).isEqualTo(changed.getCreated());
        assertThat(postInDb.isVisible()).isEqualTo(changed.isVisible());
        assertThat(postInDb.getCity().getId()).isEqualTo(changed.getCity().getId());
    }
}