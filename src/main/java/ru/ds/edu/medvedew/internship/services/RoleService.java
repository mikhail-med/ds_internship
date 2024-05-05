package ru.ds.edu.medvedew.internship.services;

import ru.ds.edu.medvedew.internship.models.Role;

import java.util.List;

/**
 * Сервис для ролей
 */
public interface RoleService {
    Role getByName(String name);

    Role getById(int id);

    List<Role> getAll();
}
