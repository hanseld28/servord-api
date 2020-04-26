package com.hanseld.servord.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanseld.servord.domain.exception.DomainException;
import com.hanseld.servord.domain.model.Client;
import com.hanseld.servord.domain.repository.ClientRepository;

@Service
public class ClientManagerService {
	
	@Autowired
	private ClientRepository clientRepository;
	
	public Client save(Client client) {
		Optional<Client> existingClient = clientRepository.findByEmail(client.getEmail());
		
		if(existingClient.isPresent() && !existingClient.get().equals(client)) {
			throw new DomainException("There is already registered client with this email.");
		}
			
		return clientRepository.save(client);
	}
	
	public void deleteById(Long clientId) {
		clientRepository.deleteById(clientId);
	}
}
