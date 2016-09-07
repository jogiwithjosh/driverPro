

create table roles(role_id bigint primary key,
					role_name varchar(255) not null,
					created_ts timestamp not null default current_timestamp);
					
insert into roles(1, 'ROLE_DRIVER');
insert into roles(2, 'ROLE_CUSTOMER');
insert into roles(3, 'ROLE_DRIVER_AGENCY');
insert into roles(4, 'ROLE_ADMIN');
insert into roles(5, 'ROLE_MANAGER');

create table users(user_id bigint primary key not null auto_increment,
				  email varchar(255) unique not null,
				  username varchar(255) unique not null,
				  password varchar(255) not null,
				  phone_number varchar(12) unique not null,
				  is_email_verified boolean not null default false,
				  is_phone_number_verified boolean not null default false,
				  email_verified_ts timestamp not null default 0,
				  phone_number_verified_ts timestamp not null default 0,
				  registered_ts timestamp not null default current_timestamp,
				  updated_ts timestamp not null default 0
				  );
				  
create table user_profile(user_profile_id bigint primary key not null auto_increment,
							user_id bigint not null,
							first_name varchar(255) not null,
							last_name varchar(255) not null,
							alternate_phone_number varchar(12) not null,
							gender varchar(5) not null,
							foreign key user_profile_user_id_fk(user_id)
							references users(user_id)
							);
							
create table driver_identity_profile(driver_identity_profile_id bigint not null 
										primary key auto_increment,
									user_id bigint not null,
									pan_card_number varchar(20) not null,
									driving_licence_number varchar(50) not null,
									adhaar_card_number varchar(50) not null,
									driver_having_vehicle boolean not null default false,
									vehicle_details varchar(500) null,
									is_pan_verified boolean not null default false,
									pan_verified_ts timestamp not null default 0,
									pan_verified_by varchar(255) null,
									is_driving_licence_verified boolean not null default false,
									driving_licence_verified_ts timestamp not null default 0,
									driving_licence_verified_by varchar(255) null,
									is_adhaar_verified boolean not null default false,
									adhaar_verified_ts timestamp not null default 0,
									adhaar_verified_by varchar(255) null,
									foreign key driver_identity_profile_user_id_fk(user_id)
									references users(user_id)
									);
									
create table driver_agency_identity_profile(driver_agency_identity_profile_id bigint not null
											primary key auto_increment,
											user_id bigint not null,
											agency_name varchar(500) not null,
											agency_address varchar(500) not null,
											agency_registartion_number varchar(100) not null,
											agency_contact_number varchar(20) not null,
											is_agency_verified boolean not null default false,
											agency_verified_ts timestamp not null default 0,
											agency_verified_by varchar(255) null,
											);

create table address(address_id bigint not null primary key auto_increment,
						user_id bigint not null,
						address_line1 varchar(255) not null,
						address_line2 varchar(255) not null,
						address_line3 varchar(255) not null,
						city varchar (255) not null,
						state varchar(255) not null, 
						country varchar(255) not null ,
						pincode int not null,
						foreign key users_address_user_id_fk(user_id) 
						references users(user_id));

create table user_to_role(user_to_role_id bigint primary key auto_increment ,
							user_id bigint not null,
							role_id bigint not null,
							foreign key user_to_role_user_id_fk(user_id) 
							references users(user_id),
							foreign key user_to_role_role_id_fk(role_id) 
							references roles(role_id));
							
create table user_login_feed(user_login_feed_id int unsigned not null primary key auto_increment,
							 user_id int unsigned not null,
							 ip_address varchar(50) not null,
							 loggedin_ts timestamp not null default current_timestamp,
							 loggedout_ts timestamp not null default 0 on update current_timestamp,
							 foreign key user_id_fk(user_id)
						  	 references users(user_id)
						  	 on delete cascade);
						  	 
create table user_login_attempt_count(user_login_attempt_count_id int unsigned not null primary key auto_increment,
								 user_id int unsigned not null,
								 attempt_count int unsigned not null default 1,
								 last_attempt_ts timestamp not null default current_timestamp,
								 foreign key user_id_fk(user_id)
						  	 	 references users(user_id)
						  	 	 on delete cascade);
						  	 	 
create table user_codes(user_codes_id int unsigned not null primary key auto_increment,
								user_id int unsigned not null,
								password_reset_code varchar(500) null,
								signup_verification_code varchar(500) not null,
								password_reset_code_used boolean not null default false,
								signup_verification_code_used boolean not null default false,
								initial_created_ts timestamp not null default current_timestamp,
								password_reset_code_created_ts timestamp not null default 0,
								signup_verification_code_created_ts timestamp not null default 0,
								password_reset_code_updated_ts timestamp not null default 0,
								signup_verification_code_updated_ts timestamp not null default 0,
								foreign key user_id_fk(user_id)
						  	 	references users(user_id)
						  	 	on delete cascade);


