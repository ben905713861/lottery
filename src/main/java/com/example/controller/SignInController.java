package com.example.controller;

import com.example.dto.SignInDto;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

@RequestMapping("/signIn")
@RestController
public class SignInController {

    private final Map<String, SignInDto> signInMap = new LinkedHashMap<>(100);

    @PostMapping
    public SignInDto signIn(@RequestBody SignInDto signInDto) {
        signInMap.put(signInDto.getName(), signInDto);
        return signInDto;
    }

    @GetMapping
    public Collection<SignInDto> getSignInList() {
        return signInMap.values();
    }

    @DeleteMapping
    public int resetSignIn() {
        int size = signInMap.size();
        signInMap.clear();
        return size;
    }
}
