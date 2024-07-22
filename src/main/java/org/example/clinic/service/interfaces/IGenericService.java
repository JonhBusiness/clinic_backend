package org.example.clinic.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IGenericService<T> {
    Page<T> getAll(Pageable pageable);


}
