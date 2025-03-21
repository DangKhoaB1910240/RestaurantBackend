package com.example.event.category;

import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/category")
@CrossOrigin(origins = {"http://localhost:4200"})
public class CategoryResource {
    @Autowired
    private CategoryService organizerService;

    @GetMapping()
    public ResponseEntity<List<Category>> getAllOrganizers(@RequestParam(required = false) String name) {
        return ResponseEntity.status(HttpStatus.OK).body(organizerService.getAllOrganizers(name));
    }
    @GetMapping("{id}")
    public ResponseEntity<Category> getInfoById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(organizerService.getInfoById(id));
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<Void> addOrganizer(@Valid @RequestBody Category organizer,@PathVariable Integer userId) {
        organizerService.addOrganizer(organizer,userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}/user/{userId}")
    public ResponseEntity<Void> deleteOrganizerById(@PathVariable Integer id,@PathVariable Integer userId) {
        organizerService.deleteOrganizerById(id,userId);
        return ResponseEntity.noContent().build();
    }
    //Cập nhật có 2 loại PUT & PATCH
    @PatchMapping("/{id}/user/{userId}")
    public ResponseEntity<Void> updateOrganizerById(@PathVariable Integer id,@PathVariable Integer userId,@Valid @RequestBody Category organizer) {
        organizerService.updateOrganizerById(id,userId,organizer);
        return ResponseEntity.noContent().build();
    }
}
