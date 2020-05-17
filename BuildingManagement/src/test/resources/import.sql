--bhhjhj
insert into building_table(id,building_name)values (1,'florian'),(2,'yasmine');

insert into building_extras (building_id,name)values ('1','pool'),('2','tennis');

insert into buildinglevel_table (id,building_id,level_number)values ('1','1','1'),('2','1','2'),('3','2','1');
--end building anf level population

--rooms population
insert into room_table (id,building_id,level_id,rent,deposit,occupied)values ('1','1','1','20000',2000,false),('2','1','2','25000',2000,false),('3','1','2','35000',2000,false),('4','2','3','10000',2000,false);

insert into additional_rooms (area,name,room_id)values (20,'ceiling room',1);

insert into bedroom_table (id,is_toilet_internal,area)values (1,true,45),(4,false,60);

insert into studio_table (id,area_of_bedroom,area_of_kitchen,area_of_parlour,area_of_toilet)values (3,10,10,10,10);

insert into appartment_table (id)values (2);

insert into bedroom_areas (apt_id,areas_of_bedrooms)values (2,15),(2,14);

insert into kitchen_areas (apt_id,areas_of_kitchens)values (2,5);

insert into parlour_areas (apt_id,areas_of_parlours)values (2,40);

insert into furniture_table (name,room_id)values ('bed',1),('table',2);
--end room population

--bills population
insert into electricity_bill_table (id,amount,is_paid,room_id)values (1,12000,true,1),(2,15000,true,2);

insert into water_bill_table (id,amount,is_paid,room_id)values (1,15000,false,1),(2,15000,false,2);
--end bills population

--person and contract population
insert into person_table (id,name,room_id,sex)values (1,'florian lowe',1,'M'),(2,'bouloulna yasmine',2,'F'),(3,'alliance lowe',3,'F'),(4,'kouekam nathan',4,'M'),(5,'lowe gabriel',1,'M');

insert into contract_table (id,duration,person_id,room_id)values (1,10,1,1),(2,15,2,2),(3,12,3,3);

































































