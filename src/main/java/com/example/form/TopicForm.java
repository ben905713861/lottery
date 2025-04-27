package com.example.form;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

/**
 * TopicForm
 *
 * @author Ben
 * @since 2025/4/27
 */
@Data
public class TopicForm {
    @NotEmpty
    private List<String> topicList;
}
