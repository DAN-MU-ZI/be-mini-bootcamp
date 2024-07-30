package com.study.bootcamp.question;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class QuestionController {

    @GetMapping("/question/list")
    public String list(Model model) {
        model.addAttribute("abc", "13");

        return "question_list";
    }
}
