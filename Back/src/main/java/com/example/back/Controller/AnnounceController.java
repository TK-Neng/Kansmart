package com.example.back.Controller;


import com.example.back.Entity.Announces;
import com.example.back.Services.AnnounceServices;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/announcements")
@CrossOrigin(origins = "*")
public class AnnounceController {
    @Autowired
    private AnnounceServices services;

    @GetMapping("")
    public List<Announces> getAnnounces(){
        return services.findAll();
    }
}
