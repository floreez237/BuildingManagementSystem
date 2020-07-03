insert into building_table(id,building_name,location)values (nextval('building_sequence'),'florian','emana'),(nextval('building_sequence'),'yasmine','bastos');

insert into building_extras (building_entity_id,name)values ('1','pool'),('2','tennis');

insert into buildinglevel_table (id,building_id,level_number)values (nextval('building_level_sequence'),'1','1'),(nextval('building_level_sequence'),'1','2'),(nextval('building_level_sequence'),'2','1');
--end building anf level population

--rooms population
insert into room_table (id,building_id,level_id,rent,deposit,occupied)values (nextval('room_sequence'),'1','1','20000',2000,true),(nextval('room_sequence'),'1','2','25000',2000,true),(nextval('room_sequence'),'1','2','35000',2000,true),(nextval('room_sequence'),'2','3','10000',2000,false);

insert into additional_rooms (area,name,room_entity_id)values (20,'ceiling room',1);

insert into bedroom_table (id,is_toilet_internal,area)values (1,true,45),(4,false,60);

insert into studio_table (id,area_of_bedroom,area_of_kitchen,area_of_parlour,area_of_toilet)values (3,10,10,10,10);

insert into appartment_table (id)values (2);

insert into bedroom_areas (apt_id,areas_of_bedrooms)values (2,15),(2,14);

insert into kitchen_areas (apt_id,areas_of_kitchens)values (2,5);

insert into parlour_areas (apt_id,areas_of_parlours)values (2,40);

insert into furniture_table (name,room_entity_id)values ('bed',1),('table',2);
--end room population

--bills population
insert into electricity_bill_table (id,amount,is_paid,room_id)values (nextval('elec_bill_sequence'),12000,true,1),(nextval('elec_bill_sequence'),15000,true,2);

insert into water_bill_table (id,amount,is_paid,room_id)values (nextval('water_bill_sequence'),15000,false,1),(nextval('water_bill_sequence'),15000,false,2);
--end bills population

--person and contract population
insert into person_table (id,name,room_id,sex)values (nextval('person_sequence'),'florian lowe',1,'M'),(nextval('person_sequence'),'bouloulna yasmine',2,'F'),(nextval('person_sequence'),'alliance lowe',3,'F'),(nextval('person_sequence'),'kouekam nathan',4,'M'),(nextval('person_sequence'),'lowe gabriel',1,'M');

insert into contract_table (id,duration,person_id,room_id,is_obsolete,date_of_payment,date_of_creation)values (nextval('contract_sequence'),10,1,1,false,to_date('12/12/2017','dd/MM/YYYY'),to_date('10/12/2017','dd/MM/YYYY')),(nextval('contract_sequence'),15,2,2,false,to_date('12/12/2019','dd/MM/YYYY'),to_date('11/12/2019','dd/MM/YYYY')),(nextval('contract_sequence'),12,3,3,false,to_date('12/02/2020','dd/MM/YYYY'),to_date('10/02/2020','dd/MM/YYYY'));

































































