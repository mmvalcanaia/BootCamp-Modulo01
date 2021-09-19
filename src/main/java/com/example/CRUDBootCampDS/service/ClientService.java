package com.example.CRUDBootCampDS.service;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.CRUDBootCampDS.dto.ClientDTO;
import com.example.CRUDBootCampDS.entities.Client;
import com.example.CRUDBootCampDS.repository.ClientRepository;
import com.example.CRUDBootCampDS.service.exceptions.ResourceNotFoundException;

@Service
public class ClientService {

	@Autowired
	private ClientRepository repository;

	@Transactional(readOnly = true)
	public Page<ClientDTO> findAllPaged(PageRequest pageRequest) {
		Page<Client> clients = repository.findAll(pageRequest);
		return clients.map(client -> new ClientDTO(client));
	}

	@Transactional(readOnly = true)
	public ClientDTO findById(Long id) {
		Optional<Client> client = repository.findById(id);
		Client entity = client.orElseThrow(() -> new ResourceNotFoundException("Ops! What you are looking for does not exist!"));
		return new ClientDTO(entity);
	}

	@Transactional
	public ClientDTO insert(ClientDTO clientDTO) {
		Client entity = new Client();
		copyDtoDataToEntity(clientDTO, entity);
		entity = repository.save(entity);
		return new ClientDTO(entity);
	}

	@Transactional
	public ClientDTO update(Long id, ClientDTO clientDTO) {
		try {
			Client entity = repository.getOne(id);
			copyDtoDataToEntity(clientDTO, entity);
			entity = repository.save(entity);
			return new ClientDTO(entity);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id " + id + " not found");
		}
	}
	
	
	public void deleteById(Long id) {
		try {
			repository.deleteById(id);	
		}
		catch(EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id " + id + " does not exist.");
		}
	}
	
	private void copyDtoDataToEntity(ClientDTO clientDTO, Client entity) {
		entity.setName(clientDTO.getName());
		entity.setCpf(clientDTO.getCpf());
		entity.setIncome(clientDTO.getIncome());
		entity.setBirthDate(clientDTO.getBirthDate());
		entity.setChildren(clientDTO.getChildren());
	}



}
