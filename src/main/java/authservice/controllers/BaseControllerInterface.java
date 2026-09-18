package authservice.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface BaseControllerInterface<RequestDto, ResponseDto> {

    // TODO: @Operational when swagger is added
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ResponseDto> get(@PathVariable Long id);

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Page<ResponseDto>> getAll(Pageable pageable);

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ResponseDto> create(@RequestBody RequestDto dto);

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ResponseDto> update(@PathVariable Long id, @RequestBody RequestDto dto);

    @DeleteMapping(path = "/{id}")
    ResponseEntity<?> delete(@PathVariable Long id);

    @DeleteMapping
    ResponseEntity<?> deleteAll();
}
