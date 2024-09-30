package com.company.spring_boot_crud_app;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProyectoRepository extends CrudRepository<Proyecto, Long> {
    List<Proyecto> findByNombre(String nombre);

    List<Proyecto> findByCategoriaId(Long categoriaId);

    List<Proyecto> findByPrecioGreaterThan(Double precio);

    List<Proyecto> findByPrecioLessThan(Double precio);

}
