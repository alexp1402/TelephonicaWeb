package org.callservice.repository;

import org.callservice.entity.TestEntity;
import org.springframework.data.repository.CrudRepository;

public interface TestEntityRepo extends CrudRepository<TestEntity,Long> {
}
