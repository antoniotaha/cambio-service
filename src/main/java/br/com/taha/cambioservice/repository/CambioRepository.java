package br.com.taha.cambioservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.taha.cambioservice.model.Cambio;

public interface CambioRepository extends JpaRepository<Cambio, Long> {

	Cambio findByFromAndTo(String from, String to);

}
