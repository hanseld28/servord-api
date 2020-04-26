create table service_order (
	id bigint not null auto_increment,
    client_id bigint not null,
    description text not null,
    price decimal(10,2) not null,
    status varchar(20) not null,
    opening_date datetime not null,
    completion_date datetime,
    
    primary key (id)
) ENGINE=InnoDB;

alter table service_order 
	add	constraint 
	foreign key fk_service_order_client (client_id) 
    references client (id);