package com.golden.controller;

import com.golden.model.Tutorial;
import com.golden.repository.TutorialRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins ="http://localhost:8081")
public class TutorialController {

    @Autowired
    private TutorialRepository repos;

    @GetMapping("/tutorials")
    public ResponseEntity<List<Tutorial>> getAllTutorial (@RequestParam(required = false) String title){
        try {
            List<Tutorial> output = new ArrayList<>();

            output = repos.findAll();

            return new ResponseEntity<>(output, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/tutorials/{id}")
    public ResponseEntity<Tutorial> getTutorialById( @PathVariable("id") long id){

        try {
            Optional<Tutorial> obj = repos.findById(id);
            if (obj.isPresent()) {
                return new ResponseEntity<>(obj.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(obj.get(), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @PostMapping("/tutorials")
    public ResponseEntity<Tutorial> createTutorial(@RequestBody Tutorial tutorial) {
        try {
            Tutorial _tutorial = repos.save(new Tutorial(tutorial.getTitle(), tutorial.getDescription(), false));
            return new ResponseEntity<>(_tutorial, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
