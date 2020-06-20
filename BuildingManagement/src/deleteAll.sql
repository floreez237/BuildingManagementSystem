truncate additional_rooms cascade;
truncate appartment_table cascade;
truncate bedroom_areas cascade;
truncate bedroom_table cascade;
truncate building_extras cascade;
truncate building_table cascade;
truncate buildinglevel_table cascade;
truncate contract_table cascade;
truncate electricity_bill_table cascade;
truncate furniture_table cascade;
truncate kitchen_areas cascade;
truncate parlour_areas cascade;
truncate person_table cascade;
truncate room_table cascade;
truncate studio_table cascade;
truncate toilet_areas cascade;
truncate water_bill_table cascade;

drop sequence building_level_sequence;
drop sequence building_sequence;
drop sequence contract_sequence;
drop sequence elec_bill_sequence;
drop sequence person_sequence;
drop sequence room_sequence;
drop sequence water_bill_sequence;


create sequence building_level_sequence;
create sequence building_sequence;
create sequence contract_sequence;
create sequence elec_bill_sequence;
create sequence person_sequence;
create sequence room_sequence;
create sequence water_bill_sequence;