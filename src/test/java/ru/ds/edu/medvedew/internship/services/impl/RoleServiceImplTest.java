package ru.ds.edu.medvedew.internship.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ds.edu.medvedew.internship.models.Role;
import ru.ds.edu.medvedew.internship.models.User;
import ru.ds.edu.medvedew.internship.repositories.RoleRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class RoleServiceImplTest {
    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleServiceImpl;

    @Test
    public void getByName() {
        String roleName = "ADMIN";
        Role role = new Role();
        role.setName(roleName);
        doReturn(Optional.of(role)).when(roleRepository).findByName("ADMIN");

        Role roleFromService = roleServiceImpl.getByName("ADMIN");

        assertEquals(roleName, roleFromService.getName());
    }

    @Test
    public void getByName_WithUnexpectedRole_ThrowsExceptions() {
        String roleName = "DOESNT EXIST";
        doReturn(Optional.empty()).when(roleRepository).findByName(roleName);

        assertThrows(IllegalArgumentException.class, () -> roleServiceImpl.getByName(roleName));
    }

    @Test
    void getAll() {
        doReturn(List.of(new User())).when(roleRepository).findAll();

        List<Role> roles = roleServiceImpl.getAll();

        assertEquals(1, roles.size());
    }

    @Test
    void getById() {
        Role role = new Role();
        role.setId(1);
        doReturn(Optional.of(role)).when(roleRepository).findById(1);

        Role roleReturned = roleServiceImpl.getById(1);

        assertEquals(1, roleReturned.getId());
    }

    @Test
    void getById_WithNotExistingValue_ThrowsException() {
        doReturn(Optional.empty()).when(roleRepository).findById(1);

        assertThrows(IllegalArgumentException.class, () -> roleServiceImpl.getById(1));
    }
}