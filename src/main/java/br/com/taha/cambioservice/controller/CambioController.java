package br.com.taha.cambioservice.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.websocket.server.PathParam;

import lombok.AllArgsConstructor;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.taha.cambioservice.model.Cambio;
import br.com.taha.cambioservice.repository.CambioRepository;

@RestController
@AllArgsConstructor
@RequestMapping("/cambio-service")
public class CambioController {

	private final Environment environment;

	private final CambioRepository cambioRepository;

	@GetMapping("{amount}/{from}/{to}")
	public Cambio show(@PathVariable(value = "amount") BigDecimal amount, @PathVariable("from") String from, @PathVariable("to") String to) {
		var cambio = this.cambioRepository.findByFromAndTo(from, to);
		if(cambio == null) {
			throw new RuntimeException("Cambio not found");
		}

		var port = environment.getProperty("local.server.port");
		cambio.setEnvironment(port);
		cambio.setConvertedValue(cambio.getConversionFactor().multiply(amount).setScale(2, RoundingMode.CEILING));
		return cambio;
	}

}
