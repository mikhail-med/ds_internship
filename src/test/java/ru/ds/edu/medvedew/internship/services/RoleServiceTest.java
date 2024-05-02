package ru.ds.edu.medvedew.internship.services;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.ds.edu.medvedew.internship.models.Role;
import ru.ds.edu.medvedew.internship.repositories.RoleRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
public class RoleServiceTest {
    @Autowired
    private RoleService roleService;

    @MockBean
    private RoleRepository roleRepository;


    @Test
    public void getByNameWithExistingRole() {
        String roleName = "ADMIN";
        Role role = new Role();
        role.setName(roleName);
        doReturn(Optional.of(role)).when(roleRepository).findByName("ADMIN");

        Role roleFromService = roleService.getByName("ADMIN");
        assertEquals(roleName, roleFromService.getName());
        Mockito.verify(roleRepository, Mockito.times(1)).findByName("ADMIN");
    }

    @Test
    public void getByNameWithUnexpectedRole() {
        String roleName = "DOESNT EXIST";
        doReturn(Optional.empty()).when(roleRepository).findByName("DOESNT EXIST");

        assertThrows(IllegalArgumentException.class, () -> roleService.getByName("DOESNT EXIST"));
        Mockito.verify(roleRepository, Mockito.times(1)).findByName("DOESNT EXIST");
    }
}