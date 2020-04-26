package com.hanseld.servord.api.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hanseld.servord.api.model.ServiceOrderDTO;
import com.hanseld.servord.api.model.ServiceOrderInput;
import com.hanseld.servord.domain.model.ServiceOrder;
import com.hanseld.servord.domain.repository.ServiceOrderRepository;
import com.hanseld.servord.domain.service.ServiceOrderManagerService;

@RestController
@RequestMapping("/service-orders")
public class ServiceOrderController {

	@Autowired
	private ServiceOrderManagerService serviceOrderManager;
	
	@Autowired
	private ServiceOrderRepository serviceOrderRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ServiceOrderDTO create(@Valid @RequestBody ServiceOrderInput serviceOrderInput) {
		ServiceOrder serviceOrder = toEntity(serviceOrderInput);
		serviceOrder = serviceOrderManager.create(serviceOrder);
		
		return toModel(serviceOrder);
	}
	
	@GetMapping
	public List<ServiceOrderDTO> listAll() {
		List<ServiceOrder> serviceOrders = serviceOrderRepository.findAll();
		
		return toCollectionModel(serviceOrders); 
	}
	
	@GetMapping("/{serviceOrderId}")
	public ResponseEntity<ServiceOrderDTO> find(@PathVariable Long serviceOrderId) {
		Optional<ServiceOrder> serviceOrder = serviceOrderRepository.findById(serviceOrderId);
		
		if(serviceOrder.isPresent()) {
			ServiceOrderDTO serviceOrderModel = toModel(serviceOrder.get());
			
			return ResponseEntity.ok(serviceOrderModel);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{serviceOrderId}/completion")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void finish(@PathVariable Long serviceOrderId) {
		serviceOrderManager.finish(serviceOrderId);
	}
	
	private ServiceOrderDTO toModel(ServiceOrder serviceOrder) {
		return modelMapper.map(serviceOrder, ServiceOrderDTO.class);
	}
	
	private List<ServiceOrderDTO> toCollectionModel(List<ServiceOrder> serviceOrders) {
		return serviceOrders.stream()
				.map(serviceOrder -> toModel(serviceOrder))
				.collect(Collectors.toList());
	}
	
	private ServiceOrder toEntity(ServiceOrderInput serviceOrderInput) {
		return modelMapper.map(serviceOrderInput, ServiceOrder.class);
	}
}
