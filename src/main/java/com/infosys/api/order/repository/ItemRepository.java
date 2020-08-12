
package com.infosys.api.order.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface ItemRepository extends CrudRepository<ItemEntity, String> {
	List<ItemEntity> findByCartIdentifier(UUID libraryId);
}
