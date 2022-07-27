package ru.job4j.dreamjob.dream.control;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.dreamjob.dream.model.City;
import ru.job4j.dreamjob.dream.model.Post;
import ru.job4j.dreamjob.dream.service.CityService;
import ru.job4j.dreamjob.dream.service.PostService;

import javax.servlet.http.HttpServletRequest;

@ThreadSafe
@Controller
public class PostController {
    private final PostService postService;
    private final CityService cityService;

    public PostController(PostService postService, CityService cityService) {
        this.postService = postService;
        this.cityService = cityService;
    }

    @GetMapping("/posts")
    public String posts(Model model) {
        model.addAttribute("posts", postService.findAll());
        return "posts";
    }

    @GetMapping("/formAddPost")
    public String addPost(Model model) {
        model.addAttribute("cities", cityService.getAllCities());
        return "addPost";
    }

    @PostMapping("/createPost")
    public String createPost(@ModelAttribute Post post, HttpServletRequest req) {
        City selectedCity = cityService.findById(Integer.parseInt(req.getParameter("city.id")));
        post.setCity(selectedCity);
        postService.addPost(post);
        return "redirect:posts";
    }

    @GetMapping("/formUpdatePost/{postId}")
    public String formUpdatePost(Model model, @PathVariable("postId") int id) {
        model.addAttribute("post", postService.findById(id));
        model.addAttribute("cities", cityService.getAllCities());
        return "updatePost";
    }

    @PostMapping("/updatePost")
    public String updatePost(@ModelAttribute Post post, HttpServletRequest req) {
        City selectedCity = cityService.findById(Integer.parseInt(req.getParameter("city.id")));
        post.setCity(selectedCity);
        postService.update(post);
        return "redirect:/posts";
    }
}
