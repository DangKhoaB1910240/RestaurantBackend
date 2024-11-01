package com.example.event.ghe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.event.registration.RegistrationService;

@RestController
@CrossOrigin(origins = {"http://localhost:4200","http://localhost:4401"})
@RequestMapping("api/v1/ghe")
public class GheResource {
    @Autowired
    private GheService gheService;   

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateById2(@PathVariable Integer id,@RequestBody GheDto gheDto) {
        this.gheService.update(id, gheDto.getGiaGhe());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ghe> getById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(gheService.getById(id));
    }

}
