package com.domytask.catdog.services;

import com.domytask.catdog.services.exceptions.InvalidEntityToPersistException;
import com.domytask.catdog.services.exceptions.NotFoundEntityException;

import java.util.List;

public interface GenericService<Entity, ID> {

    void save(Entity entity) throws InvalidEntityToPersistException;
    Entity update(Entity entity) throws NotFoundEntityException;
    Entity getById(ID userId) throws  NotFoundEntityException;
    List<Entity> all() throws NotFoundEntityException;
    void delete(ID userId) throws NotFoundEntityException;
}
