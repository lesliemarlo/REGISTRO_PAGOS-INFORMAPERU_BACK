package com.informaperu.web.registropagos.services;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.informaperu.web.registropagos.model.APIResponseDTO;
import com.informaperu.web.registropagos.model.ClientDTO;

@Service
public class ClientServiceImpl implements ClientService {

	// Se instancia RestTemplate para hacer peticiones HTTP (REST)
	private final RestTemplate restTemplate = new RestTemplate();
	// Autorizacion
	private final String TOKEN = "6864f2b0f565b14bb911acc6ccaf0560935e3e97";
	private final String PREFIX = "token";

	// Este método busca un cliente por su DNI y devuelve un Optional (puede traer
	// algo o nada)
	@Override
	public Optional<ClientDTO> buscarPorCodigoContacto(String codigoContacto) {
		
		// url original de la API
		String url = "https://api.beexcontact.com/api/v1/reports/products/?limit=9999&offset=1&portfolio=4";
		
		// Se crean los headers de la petición, para la autorizacion en este caso
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", PREFIX + " " + TOKEN);
		// Se arma la entidad HTTP con solo los headers
		HttpEntity<String> entity = new HttpEntity<>(headers);
        //`exchange` permite enviar headers
		ResponseEntity<APIResponseDTO> response = restTemplate.exchange(url, HttpMethod.GET, entity,
				APIResponseDTO.class);

		if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
			for (ClientDTO cliente : response.getBody().getResults()) {
				if (cliente.getCodigo_contacto().equals(codigoContacto)) {
					return Optional.of(cliente);
				}
			}
		}

		return Optional.empty();
	}
}
