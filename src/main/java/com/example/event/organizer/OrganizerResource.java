package com.example.event.organizer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/organizer")
public class OrganizerResource {
    @Autowired
    private OrganizerService organizerService;

    @GetMapping()
    public List<Organizer> getAllOrganizers() {
        return organizerService.getAllOrganizers();
    }
}
