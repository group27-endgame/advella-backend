package com.advella.advellabackend.unit;

import com.advella.advellabackend.model.Role;
import com.advella.advellabackend.repositories.IRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleService {
    private final IRoleRepository roleRepository;

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public void saveAllRoles(List<Role> rolesToSave) {
        roleRepository.saveAll(rolesToSave);
    }

    public Role findByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }
}
