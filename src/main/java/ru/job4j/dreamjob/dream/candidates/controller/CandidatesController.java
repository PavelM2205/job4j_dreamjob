package ru.job4j.dreamjob.dream.candidates.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.dreamjob.dream.candidates.model.Candidate;
import ru.job4j.dreamjob.dream.candidates.store.PostStore;

@Controller
public class CandidatesController {
    private final PostStore store = PostStore.instOf();

    @GetMapping("/candidates")
    public String candidates(Model model) {
        model.addAttribute("candidates", store.findAll());
        return "candidates";
    }

    @GetMapping("/formAddCandidate")
    public String addCandidate(Model model) {
        model.addAttribute("candidate", new Candidate(0, "Заполните поле"));
        return "addCandidate";
    }
}
