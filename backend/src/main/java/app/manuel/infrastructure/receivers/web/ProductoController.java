package app.manuel.infrastructure.receivers.web;

import app.manuel.infrastructure.adapter.postgres.entities.Empresa;
import app.manuel.infrastructure.adapter.postgres.entities.Producto;
import app.manuel.infrastructure.adapter.postgres.repository.EmpresaRepository;
import app.manuel.infrastructure.adapter.postgres.repository.ProductoRepository;
import app.manuel.infrastructure.receivers.web.dto.ProductoDto;
import app.manuel.infrastructure.receivers.web.exception.ResourceNotFoundException;
import app.manuel.infrastructure.receivers.web.payload.ProductoPayload;
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
public class ProductoController {

    private final ProductoRepository productoRepository;
    private final EmpresaRepository empresaRepository;

    private static final String PRODUCTO_NOT_FOUNT = "Producto not found for this id :: ";
    private static final String EMPRESA_NOT_FOUNT = "Empresa not found for this id :: ";

    @Autowired
    public ProductoController(ProductoRepository productoRepository, EmpresaRepository empresaRepository) {
        this.productoRepository = productoRepository;
        this.empresaRepository = empresaRepository;
    }

    @CrossOrigin
    @GetMapping("/productos")
    public List<ProductoDto> getProductos() {
        List<ProductoDto> products = new ArrayList<>();
        productoRepository.findAll().forEach(producto -> products.add(new ProductoDto(
                producto.getId(),
                producto.getCodigo(),
                producto.getNombre(),
                producto.getCaracteristicas(),
                producto.getPrecio(),
                producto.getEmpresa().getNombre())
        ));
        return products;
    }

    @GetMapping("/productos/{id}")
    public ResponseEntity<ProductoDto> getProductoById(@PathVariable(value = "id") long productoId)
            throws ResourceNotFoundException {
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new ResourceNotFoundException(PRODUCTO_NOT_FOUNT + productoId));
        return ResponseEntity.ok().body(new ProductoDto(producto.getId(),
                producto.getCodigo(),
                producto.getNombre(),
                producto.getCaracteristicas(),
                producto.getPrecio(),
                producto.getEmpresa().getNombre()));
    }

    @CrossOrigin
    @PostMapping("/productos")
    public ProductoDto createProducto(@RequestBody ProductoPayload productoPayload)
            throws ResourceNotFoundException {
        Empresa empresa = empresaRepository.findById(productoPayload.nitEmpresa())
                .orElseThrow(() -> new ResourceNotFoundException(EMPRESA_NOT_FOUNT + productoPayload.nitEmpresa()));

        Producto producto = new Producto();
        producto.setCodigo(productoPayload.codigo());
        producto.setNombre(productoPayload.nombre());
        producto.setCaracteristicas(productoPayload.caracteristicas());
        producto.setPrecio(productoPayload.precio());
        producto.setEmpresa(empresa);

        productoRepository.save(producto);

        return new ProductoDto(producto.getId(),
                producto.getCodigo(),
                producto.getNombre(),
                producto.getCaracteristicas(),
                producto.getPrecio(),
                producto.getEmpresa().getNombre());
    }

    @PutMapping("/productos/{id}")
    public ResponseEntity<ProductoDto> updateProducto(@PathVariable(value = "id") long productoId,
            @Valid @RequestBody ProductoPayload productoPayload) throws  ResourceNotFoundException{
        Empresa empresa = empresaRepository.findById(productoPayload.nitEmpresa())
                .orElseThrow(() -> new ResourceNotFoundException(EMPRESA_NOT_FOUNT + productoPayload.nitEmpresa()));

        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new ResourceNotFoundException(PRODUCTO_NOT_FOUNT + productoId));

        producto.setCodigo(productoPayload.codigo());
        producto.setNombre(productoPayload.nombre());
        producto.setCaracteristicas(productoPayload.caracteristicas());
        producto.setPrecio(productoPayload.precio());
        producto.setEmpresa(empresa);

        productoRepository.save(producto);

        return ResponseEntity.ok(new ProductoDto(producto.getId(),
                producto.getCodigo(),
                producto.getNombre(),
                producto.getCaracteristicas(),
                producto.getPrecio(),
                producto.getEmpresa().getNombre())
        );
    }

    @CrossOrigin
    @DeleteMapping("/productos/{id}")
    public Map<String, Boolean> deleteProducto(@PathVariable(value = "id") long productoId)
            throws ResourceNotFoundException {
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new ResourceNotFoundException(PRODUCTO_NOT_FOUNT + productoId));

        productoRepository.delete(producto);
        Map<String, Boolean> response = new HashMap<>();
        response.put("delete", Boolean.TRUE);
        return response;
    }
}
