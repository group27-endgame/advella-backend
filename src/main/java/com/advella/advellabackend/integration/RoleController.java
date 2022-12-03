package com.advella.advellabackend.integration;

import com.advella.advellabackend.model.Role;
import com.advella.advellabackend.unit.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class RoleController {
    private final RoleService roleService;

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }
}
