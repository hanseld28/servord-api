package com.hanseld.servord.domain.service;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanseld.servord.domain.exception.DomainException;
import com.hanseld.servord.domain.exception.EntityNotFoundException;
import com.hanseld.servord.domain.model.Client;
import com.hanseld.servord.domain.model.Comment;
import com.hanseld.servord.domain.model.ServiceOrder;
import com.hanseld.servord.domain.model.ServiceOrderStatus;
import com.hanseld.servord.domain.repository.ClientRepository;
import com.hanseld.servord.domain.repository.CommentRepository;
import com.hanseld.servord.domain.repository.ServiceOrderRepository;

@Service
public class ServiceOrderManagerService {
	
	@Autowired
	private ServiceOrderRepository serviceOrderRepository;
	
	@Autowired
	private ClientRepository clientRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	
	public ServiceOrder create(ServiceOrder serviceOrder) {
		Client client = clientRepository.findById(serviceOrder.getClient().getId())
				.orElseThrow(() -> new DomainException("Client not found"));
		
		serviceOrder.setClient(client);
		serviceOrder.setStatus(ServiceOrderStatus.OPENED);
		serviceOrder.setOpeningDate(OffsetDateTime.now());
		
		return serviceOrderRepository.save(serviceOrder);
	}
	
	public void finish(Long serviceOrderId) {
		ServiceOrder serviceOrder = search(serviceOrderId);
		
		serviceOrder.finish();
		
		serviceOrderRepository.save(serviceOrder);
	}

	public Comment addComment(Long serviceOrderId, String description) {
		ServiceOrder serviceOrder = search(serviceOrderId);
		
		Comment comment = new Comment();
		comment.setDescription(description);
		comment.setSendDate(OffsetDateTime.now());
		comment.setServiceOrder(serviceOrder);
		
		return commentRepository.save(comment);
	}
	
	private ServiceOrder search(Long serviceOrderId) {
		return serviceOrderRepository.findById(serviceOrderId)
				.orElseThrow(() -> new EntityNotFoundException("Service order not found"));
	}
}
