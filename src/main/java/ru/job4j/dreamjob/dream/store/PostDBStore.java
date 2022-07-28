package ru.job4j.dreamjob.dream.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.dream.model.City;
import ru.job4j.dreamjob.dream.model.Post;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PostDBStore {
    private final BasicDataSource pool;

    public PostDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public List<Post> findAll() {
        List<Post> posts = new ArrayList<>();
        try (Connection cn = pool.getConnection(); PreparedStatement statement =
                cn.prepareStatement("SELECT * FROM post")) {
            try (ResultSet it = statement.executeQuery()) {
                while (it.next()) {
                    Post current = new Post(it.getInt("id"), it.getString("name"));
                    current.setCity(new City(it.getInt("city_id")));
                    posts.add(current);
                }
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return posts;
    }

    public Post add(Post post) {
        try (Connection cn = pool.getConnection(); PreparedStatement statement =
                cn.prepareStatement("INSERT INTO post(name, city_id) VALUES (?, ?)",
                        PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, post.getName());
            statement.setInt(2, post.getCity().getId());
            statement.execute();
            try (ResultSet id = statement.getGeneratedKeys()) {
                if (id.next()) {
                    post.setId(id.getInt("id"));
                }
            }

        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return post;
    }

    public void update(Post post) {
        try (Connection cn = pool.getConnection(); PreparedStatement statement =
                cn.prepareStatement("UPDATE post SET name = ?, city_id = ? where id = ?")) {
            statement.setString(1, post.getName());
            statement.setInt(2, post.getCity().getId());
            statement.setInt(3, post.getId());
            statement.execute();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    public Post findById(int id) {
        try (Connection cn = pool.getConnection(); PreparedStatement statement =
                cn.prepareStatement("SELECT * FROM post where id = ?")) {
            statement.setInt(1, id);
            try (ResultSet it = statement.executeQuery()) {
                if (it.next()) {
                    Post post = new Post(it.getInt("id"), it.getString("name"));
                    post.setCity(new City(it.getInt("city_id")));
                    return post;
                }
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return null;
    }
}
