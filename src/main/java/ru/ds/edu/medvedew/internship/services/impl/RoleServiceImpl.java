package ru.ds.edu.medvedew.internship.services.impl;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ds.edu.medvedew.internship.models.Role;
import ru.ds.edu.medvedew.internship.repositories.RoleRepository;
import ru.ds.edu.medvedew.internship.services.RoleService;

import java.util.List;

@Service
@Transactional(readOnly = true)
@Api(tags = "Роли")
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Role getByName(String name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected role"));
    }

    @Override
    public Role getById(int id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected role"));
    }

    @Override
    public List<Role> getAll() {
        return roleRepository.findAll();
    }
}
