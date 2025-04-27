package com.example.controller;

import com.example.form.TopicForm;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * topic
 *
 * @author Ben
 * @since 2025/4/27
 */
@Validated
@RequestMapping("/topic")
@RestController
public class TopicController {
    private final List<String> topicList = new ArrayList<>();

    @GetMapping
    public List<String> getTopicList() {
        return topicList;
    }

    @PostMapping
    public boolean addTopic(@Validated @RequestBody TopicForm topicForm) {
        topicList.clear();
        topicList.addAll(topicForm.getTopicList());
        return true;
    }
}
