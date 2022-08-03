package ru.job4j.dreamjob.dream.control;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import ru.job4j.dreamjob.dream.model.City;
import ru.job4j.dreamjob.dream.model.Post;
import ru.job4j.dreamjob.dream.model.User;
import ru.job4j.dreamjob.dream.service.CityService;
import ru.job4j.dreamjob.dream.service.PostService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class PostControllerTest {

    @Test
    public void whenPosts() {
        List<Post> posts = Arrays.asList(
                new Post(1, "Post 1"),
                new Post(2, "Post 2")
        );
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        PostController postController = new PostController(postService, cityService);
        Model model = mock(Model.class);
        User user = new User("email", "password");
        HttpSession session = mock(HttpSession.class);
        when(postService.findAll()).thenReturn(posts);
        when(session.getAttribute("user")).thenReturn(user);
        String page = postController.posts(model, session);
        verify(model).addAttribute("posts", posts);
        verify(model).addAttribute("user", user);
        assertThat(page).isEqualTo("posts");
    }

    @Test
    public void whenCreatePost() {
        Post post = new Post(1, "Post 1");
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        PostController postController = new PostController(postService, cityService);
        HttpServletRequest req = mock(HttpServletRequest.class);
        when(req.getParameter("city.id")).thenReturn("1");
        String page = postController.createPost(post, req);
        verify(cityService).findById(1);
        verify(postService).addPost(post);
        assertThat(page).isEqualTo("redirect:/posts");
    }

    @Test
    public void formAddPost() {
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        PostController postController = new PostController(postService, cityService);
        List<City> cities = Arrays.asList(
                new City(1, "Москва"),
                new City(2, "СПб")
        );
        User user = new User("email", "password");
        when(session.getAttribute("user")).thenReturn(user);
        when(cityService.getAllCities()).thenReturn(cities);
        String page = postController.addPost(model, session);
        verify(model).addAttribute("cities", cities);
        verify(model).addAttribute("user", user);
        assertThat(page).isEqualTo("addPost");
    }

    @Test
    public void whenFormUpdatePost() {
        Model model = mock(Model.class);
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        int id = 1;
        List<City> cities = Arrays.asList(
                new City(1, "Москва"),
                new City(2, "СПб")
        );
        Post post = new Post(1, "New Post");
        when(postService.findById(id)).thenReturn(post);
        when(cityService.getAllCities()).thenReturn(cities);
        PostController postController = new PostController(postService, cityService);
        String page = postController.formUpdatePost(model, id);
        verify(model).addAttribute("post", post);
        verify(model).addAttribute("cities", cities);
        assertThat(page).isEqualTo("updatePost");
    }

    @Test
    public void whenUpdatePost() {
        Post post = new Post(1, "New Post");
        HttpServletRequest req = mock(HttpServletRequest.class);
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        PostController postController = new PostController(postService, cityService);
        when(req.getParameter("city.id")).thenReturn("1");
        String page = postController.updatePost(post, req);
        verify(postService).update(post);
        verify(cityService).findById(1);
        assertThat(page).isEqualTo("redirect:/posts");
    }
}