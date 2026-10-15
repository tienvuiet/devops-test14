package com.ptit.devops.lab.controller;

import com.ptit.devops.lab.entity.Lab;
import com.ptit.devops.lab.repository.LabRepository;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class LabController {

    private final LabRepository repository;

    public LabController(LabRepository repository) {
        this.repository = repository;
    }

    /** GET /api/labs - ds phòng lab */
    @GetMapping("/labs")
    public List<Lab> findAll() {
        return repository.findAll(Sort.by("id"));
    }
    @GetMapping("/submission")
    public Map<String, Object> submission() {
        Map<String, String> env = System.getenv();
        return Map.of(
                "student", Map.of(
                        "name", env.getOrDefault("STUDENT_NAME", ""),
                        "id", env.getOrDefault("STUDENT_ID", ""),
                        "class", env.getOrDefault("STUDENT_CLASS", ""),
                        "email", env.getOrDefault("STUDENT_EMAIL", ""),
                        "github", env.getOrDefault("GITHUB_USERNAME", "")
                ),
                "version", env.getOrDefault("APP_VERSION", "unknown"),
                "labs", findAll()
        );
    }

}
