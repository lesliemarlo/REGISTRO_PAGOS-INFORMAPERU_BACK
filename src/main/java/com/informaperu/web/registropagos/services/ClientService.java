package com.informaperu.web.registropagos.services;

import java.util.List;
import java.util.Optional;

import com.informaperu.web.registropagos.model.ClientDTO;

public interface ClientService {


	Optional<ClientDTO> buscarPorCodigoContacto(String codigoContacto);


	
	

}
