package com.company.spring_boot_crud_app;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/proyectos")
public class ProyectoController {

    private final ProyectoRepository proyectoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProyectoController(ProyectoRepository proyectoRepository, CategoriaRepository categoriaRepository) {
        this.proyectoRepository = proyectoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @GetMapping
    public ResponseEntity<List<Proyecto>> obtenerTodos() {
        List<Proyecto> proyectos = (List<Proyecto>) proyectoRepository.findAll();
        return ResponseEntity.ok(proyectos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Proyecto> obtenerPorId(@PathVariable Long id) {
        return proyectoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Proyecto> crearProyecto(@RequestBody Proyecto proyecto) {
        if (proyecto.getCategoria() != null && proyecto.getCategoria().getId() != null) {
            Optional<Categoria> categoria = categoriaRepository.findById(proyecto.getCategoria().getId());
            if (categoria.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            proyecto.setCategoria(categoria.get());
        }
        Proyecto nuevoProyecto = proyectoRepository.save(proyecto);
        return new ResponseEntity<>(nuevoProyecto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Proyecto> actualizarProyecto(@PathVariable Long id, @RequestBody Proyecto proyecto) {
        if (!proyectoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        proyecto.setId(id);
        if (proyecto.getCategoria() != null && proyecto.getCategoria().getId() != null) {
            Optional<Categoria> categoria = categoriaRepository.findById(proyecto.getCategoria().getId());
            if (categoria.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            proyecto.setCategoria(categoria.get());
        }
        Proyecto actualizado = proyectoRepository.save(proyecto);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProyecto(@PathVariable Long id) {
        if (!proyectoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        proyectoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<Proyecto>> obtenerPorCategoria(@PathVariable Long categoriaId) {
        if (!categoriaRepository.existsById(categoriaId)) {
            return ResponseEntity.notFound().build();
        }
        List<Proyecto> proyectos = proyectoRepository.findByCategoriaId(categoriaId);
        return ResponseEntity.ok(proyectos);
    }
}