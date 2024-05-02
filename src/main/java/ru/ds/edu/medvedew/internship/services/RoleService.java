package ru.ds.edu.medvedew.internship.services;

import ru.ds.edu.medvedew.internship.models.Role;

/**
 * Сервис для ролей
 */
public interface RoleService {
    Role getByName(String name);
}
