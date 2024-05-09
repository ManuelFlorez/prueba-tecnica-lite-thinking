package app.manuel.infrastructure.receivers.web;

import app.manuel.infrastructure.adapter.postgres.entities.Empresa;
import app.manuel.infrastructure.adapter.postgres.repository.EmpresaRepository;
import app.manuel.infrastructure.receivers.web.exception.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class EmpresaController {

    private final EmpresaRepository empresaRepository;

    private static final String EMPRESA_NOT_FOUNT = "Empresa not found for this id :: ";

    @Autowired
    public EmpresaController(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    @CrossOrigin
    @GetMapping("/empresas")
    public List<Empresa> getEmpresas() {
        List<Empresa> empresas = new ArrayList<>();
        empresaRepository.findAll().forEach(empresas::add);
        return empresas;
    }

    @GetMapping("/empresas/{id}")
    public ResponseEntity<Empresa> getEmpresaById(@PathVariable(value = "id") String empresaId)
            throws ResourceNotFoundException {
        Empresa empresa =  empresaRepository.findById(empresaId)
                .orElseThrow(() -> new ResourceNotFoundException(EMPRESA_NOT_FOUNT + empresaId));
        return ResponseEntity.ok().body(empresa);
    }

    @CrossOrigin
    @PostMapping("/empresas")
    public Empresa createEmpresa(@Valid @RequestBody Empresa empresa) {
        return empresaRepository.save(empresa);
    }

    @CrossOrigin
    @PutMapping("/empresas/{id}")
    public ResponseEntity<Empresa> updateEmpresa(@PathVariable(value = "id") String empresaId,
        @RequestBody Empresa empresaDetail) throws  ResourceNotFoundException {
        Empresa empresa = empresaRepository.findById(empresaId)
                .orElseThrow(() -> new ResourceNotFoundException(EMPRESA_NOT_FOUNT + empresaId));

        empresa.setNombre(empresaDetail.getNombre());
        empresa.setDireccion(empresaDetail.getDireccion());
        empresa.setTelefono(empresaDetail.getTelefono());

        final Empresa updateEmpresa = empresaRepository.save(empresa);
        return ResponseEntity.ok(updateEmpresa);
    }

    @CrossOrigin
    @DeleteMapping("/empresas/{id}")
    public Map<String, Boolean> deleteUser(@PathVariable(value = "id") String empresaId) throws ResourceNotFoundException {
        Empresa empresa = empresaRepository.findById(empresaId)
                .orElseThrow(() -> new ResourceNotFoundException(EMPRESA_NOT_FOUNT + empresaId));

        empresaRepository.delete(empresa);
        Map<String, Boolean> response = new HashMap<>();
        response.put("delete", Boolean.TRUE);
        return response;
    }

}
