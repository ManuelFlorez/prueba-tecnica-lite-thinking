package app.manuel.infrastructure.adapter.postgres.repository;

import app.manuel.infrastructure.adapter.postgres.entities.Producto;
import org.springframework.data.repository.CrudRepository;

public interface ProductoRepository extends CrudRepository<Producto, Long> {
}
