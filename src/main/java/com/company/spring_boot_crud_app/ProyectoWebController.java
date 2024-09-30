package com.company.spring_boot_crud_app;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/proyectos")
public class ProyectoWebController {
    private static final String REDIRECT_ProyectoS = "redirect:/proyectos";
    private final ProyectoRepository proyectoRepository;
    private final CategoriaRepository categoriaRepository; 

    public ProyectoWebController(ProyectoRepository proyectoRepository, CategoriaRepository categoriaRepository) {
        this.proyectoRepository = proyectoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @GetMapping
    public String listarProyectos(Model model) {
        model.addAttribute("proyectos", proyectoRepository.findAll());
        return "proyectos/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("proyecto", new Proyecto());
        model.addAttribute("categorias", categoriaRepository.findAll()); 
        return "proyectos/formulario";
    }

    @PostMapping("/nuevo")
    public String crearProyecto(@ModelAttribute Proyecto proyecto) {
        proyectoRepository.save(proyecto);
        return REDIRECT_ProyectoS;
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Proyecto proyecto = proyectoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de proyecto inválido: " + id));
        model.addAttribute("proyecto", proyecto);
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "proyectos/formulario";
    }

    @PostMapping("/editar/{id}")
    public String actualizarProyecto(@PathVariable Long id, @ModelAttribute Proyecto proyecto) {
        proyecto.setId(id);
        proyectoRepository.save(proyecto);
        return REDIRECT_ProyectoS;
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarProyecto(@PathVariable Long id) {
        proyectoRepository.deleteById(id);
        return REDIRECT_ProyectoS;
    }
}