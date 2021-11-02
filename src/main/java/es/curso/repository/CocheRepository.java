package es.curso.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.curso.entity.Coche;

@Repository
public interface CocheRepository extends JpaRepository<Coche, Integer> {

}
