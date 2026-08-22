package authservice.services.interfaces;

import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Extended by every ServiceInterface
 * E - entity in database
 * RequestDto - entity's request dto, used for post and put routes
 * ResponseDto - entity's request dto, used for get routes
 */
public interface BaseServiceInterface<E, RequestDto, ResponseDto> {
    /**
     * Get entity by its id
     * @param id of Entity
     * @return entity as an object
     */
    E getEntity(Long id);

    /**
     * Get entity as response dto, by its id
     * @param id of entity
     * @return response dto
     */
    ResponseDto getById(Long id);

    /**
     * Get all entities as response dto
     * @param pageable pagination
     * @return list of entities
     */
    List<ResponseDto> getAll(Pageable pageable);

    /**
     * Crete entity
     * @param dto represents a new entity
     * @return created entity or null
     */
    ResponseDto create(RequestDto dto);

    /**
     * Update an existing entity
     * @param dto represents an updated entity
     * @return updated entity or null
     */
    ResponseDto update(Long id, RequestDto dto);

    /**
     * Delete entity by its id
     * @param id of entity
     * @return operation success
     */
    boolean deleteById(Long id);

    /**
     * Delete all entities in the database table
     * Must have @Transactional
     * ADMIN only
     */
    void deleteAll();
}
