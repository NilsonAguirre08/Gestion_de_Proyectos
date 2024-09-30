package com.company.spring_boot_crud_app;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriaRepository categoriaRepository;
    private final ProyectoRepository proyectoRepository;

    public CategoriaController(CategoriaRepository categoriaRepository, ProyectoRepository proyectoRepository) {
        this.categoriaRepository = categoriaRepository;
        this.proyectoRepository = proyectoRepository;
    }

    @GetMapping
    public ResponseEntity<List<Categoria>> obtenerTodas() {
        List<Categoria> categorias = (List<Categoria>) categoriaRepository.findAll();
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> obtenerPorId(@PathVariable Long id) {
        return categoriaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Categoria> crearCategoria(@RequestBody Categoria categoria) {
        Categoria nuevaCategoria = categoriaRepository.save(categoria);
        return new ResponseEntity<>(nuevaCategoria, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categoria> actualizarCategoria(@PathVariable Long id, @RequestBody Categoria categoria) {
        if (!categoriaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        categoria.setId(id);
        Categoria actualizada = categoriaRepository.save(categoria);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable Long id) {
        if (!categoriaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        // Verificar si hay proyectos asociados a esta categoría
        List<Proyecto> proyectosAsociados = proyectoRepository.findByCategoriaId(id);
        if (!proyectosAsociados.isEmpty()) {
            return ResponseEntity.badRequest().build(); // No se puede eliminar una categoría con proyectos asociados
        }
        categoriaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/proyectos")
    public ResponseEntity<List<Proyecto>> obtenerProyectosPorCategoria(@PathVariable Long id) {
        if (!categoriaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        List<Proyecto> proyectos = proyectoRepository.findByCategoriaId(id);
        return ResponseEntity.ok(proyectos);
    }
}