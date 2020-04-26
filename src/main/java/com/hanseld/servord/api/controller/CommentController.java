package com.hanseld.servord.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hanseld.servord.api.model.CommentDTO;
import com.hanseld.servord.api.model.CommentInput;
import com.hanseld.servord.domain.exception.EntityNotFoundException;
import com.hanseld.servord.domain.model.Comment;
import com.hanseld.servord.domain.model.ServiceOrder;
import com.hanseld.servord.domain.repository.ServiceOrderRepository;
import com.hanseld.servord.domain.service.ServiceOrderManagerService;

@RestController
@RequestMapping("/service-orders/{serviceOrderId}/comments")
public class CommentController {
	
	@Autowired
	private ServiceOrderManagerService serviceOrderManager;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private ServiceOrderRepository serviceOrderRepository;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CommentDTO add(@PathVariable Long serviceOrderId, 
			@Valid @RequestBody CommentInput commentInput) {
		
		Comment comment = serviceOrderManager.addComment(serviceOrderId, commentInput.getDescription());
				
		return toModel(comment);	
	}
	
	@GetMapping
	public List<CommentDTO> listAll(@PathVariable Long serviceOrderId) {
		ServiceOrder serviceOrder = serviceOrderRepository.findById(serviceOrderId)
				.orElseThrow(() -> new EntityNotFoundException("Service order not found"));
		
		return toCollectionModel(serviceOrder.getComments());
	}
	
	private CommentDTO toModel(Comment comment) {
		return modelMapper.map(comment, CommentDTO.class);
	}
	
	private List<CommentDTO> toCollectionModel(List<Comment> comments) {
		return comments.stream()
				.map(comment -> toModel(comment))
				.collect(Collectors.toList());
	}
}
