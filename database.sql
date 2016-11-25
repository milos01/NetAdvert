INSERT INTO `netadvert`.`technical_equipment` (`equipment_name`) VALUES ('Air-conditioner');
INSERT INTO `netadvert`.`technical_equipment` (`equipment_name`) VALUES ('Fridge');
INSERT INTO `netadvert`.`technical_equipment` (`equipment_name`) VALUES ('Internet');
INSERT INTO `netadvert`.`technical_equipment` (`equipment_name`) VALUES ('Cable TV');
INSERT INTO `netadvert`.`technical_equipment` (`equipment_name`) VALUES ('Phone');

INSERT INTO `netadvert`.`realestate_category` (`category_name`) VALUES ('Residential buildings');
INSERT INTO `netadvert`.`realestate_category` (`category_name`) VALUES ('Business facilities');
INSERT INTO `netadvert`.`realestate_category` (`category_name`) VALUES ('Lot');


INSERT INTO `netadvert`.`realestate_type` (`type_name`) VALUES ('House');
INSERT INTO `netadvert`.`realestate_type` (`type_name`) VALUES ('Apartment');


INSERT INTO `netadvert`.`location` (`city`,`postal_code`,`region`,`street`,`street_number`) VALUES ('Novi Sad', 21000, 'Grbavica', 'Puskinova', 24);
INSERT INTO `netadvert`.`location` (`city`,`postal_code`,`region`,`street`,`street_number`) VALUES ('Novi Sad', 21000, 'Novo naselje', 'Momcila Tapavice', 4);


INSERT INTO `netadvert`.`realestate_category_technical_equipment` (`realestate_category_id`, `technical_equipment_id`) VALUES ('1', '1');
INSERT INTO `netadvert`.`realestate_category_technical_equipment` (`realestate_category_id`, `technical_equipment_id`) VALUES ('1', '2');
INSERT INTO `netadvert`.`realestate_category_technical_equipment` (`realestate_category_id`, `technical_equipment_id`) VALUES ('1', '3');
INSERT INTO `netadvert`.`realestate_category_technical_equipment` (`realestate_category_id`, `technical_equipment_id`) VALUES ('1', '4');
INSERT INTO `netadvert`.`realestate_category_technical_equipment` (`realestate_category_id`, `technical_equipment_id`) VALUES ('1', '5');
INSERT INTO `netadvert`.`realestate_category_technical_equipment` (`realestate_category_id`, `technical_equipment_id`) VALUES ('2', '1');
INSERT INTO `netadvert`.`realestate_category_technical_equipment` (`realestate_category_id`, `technical_equipment_id`) VALUES ('2', '3');
INSERT INTO `netadvert`.`realestate_category_technical_equipment` (`realestate_category_id`, `technical_equipment_id`) VALUES ('2', '5');


INSERT INTO `netadvert`.`realestate_category_realestate_type` (`realestate_category_id`, `realestate_type_id`) VALUES ('1', '1');
INSERT INTO `netadvert`.`realestate_category_realestate_type` (`realestate_category_id`, `realestate_type_id`) VALUES ('1', '2');
INSERT INTO `netadvert`.`realestate_category_realestate_type` (`realestate_category_id`, `realestate_type_id`) VALUES ('2', '1');
INSERT INTO `netadvert`.`realestate_category_realestate_type` (`realestate_category_id`, `realestate_type_id`) VALUES ('2', '2');


INSERT INTO `netadvert`.`realestate` (`realestate_name`,`area`,`cost`,`heating`,`category_id`,`type_id`,`location_id`) VALUES ('Stan na grbavici', 45, 25000, false, 1, 2, 1);
INSERT INTO `netadvert`.`realestate` (`realestate_name`,`area`,`cost`,`heating`,`category_id`,`type_id`,`location_id`) VALUES ('Kuca na novom naselju', 60, 40000, true, 1, 1, 2);


INSERT INTO `netadvert`.`realestate_technical_equipment` (`realestate_id`,`technical_equipment_id`) VALUES ('1', '2');
INSERT INTO `netadvert`.`realestate_technical_equipment` (`realestate_id`,`technical_equipment_id`) VALUES ('1', '3');
INSERT INTO `netadvert`.`realestate_technical_equipment` (`realestate_id`,`technical_equipment_id`) VALUES ('1', '5');
INSERT INTO `netadvert`.`realestate_technical_equipment` (`realestate_id`,`technical_equipment_id`) VALUES ('2', '1');
INSERT INTO `netadvert`.`realestate_technical_equipment` (`realestate_id`,`technical_equipment_id`) VALUES ('2', '2');
INSERT INTO `netadvert`.`realestate_technical_equipment` (`realestate_id`,`technical_equipment_id`) VALUES ('2', '3');
INSERT INTO `netadvert`.`realestate_technical_equipment` (`realestate_id`,`technical_equipment_id`) VALUES ('2', '4');
INSERT INTO `netadvert`.`realestate_technical_equipment` (`realestate_id`,`technical_equipment_id`) VALUES ('2', '5');


INSERT INTO `netadvert`.`role` (`name`) VALUES ('Admin');
INSERT INTO `netadvert`.`role` (`name`) VALUES ('Regular user');
INSERT INTO `netadvert`.`role` (`name`) VALUES ('Verifier');


INSERT INTO `netadvert`.`user` (`first_name`,`last_name`,`email`,`role_id`,`user_rate`) VALUES ('Milos', 'Andric', 'milosa942@gmail.com', '1', 0);
INSERT INTO `netadvert`.`user` (`first_name`,`last_name`,`email`,`role_id`,`user_rate`) VALUES ('Milos', 'Obradovic', 'milossm94@hotmail.com', '2', 4);
INSERT INTO `netadvert`.`user` (`first_name`,`last_name`,`email`,`role_id`,`user_rate`) VALUES ('Mladen', 'Doslic', 'doslicmm@live.com', '3', 0);


INSERT INTO `netadvert`.`picture` (`picture_name`,`user_id`,`realestate_id`,`is_profile`) VALUES ('Spoljasnji izgled', '2', '1', true);
INSERT INTO `netadvert`.`picture` (`picture_name`,`user_id`,`realestate_id`,`is_profile`) VALUES ('Dnevna soba', '2', '1', false);
INSERT INTO `netadvert`.`picture` (`picture_name`,`user_id`,`realestate_id`,`is_profile`) VALUES ('Kuhinja', '2', '1', false);
INSERT INTO `netadvert`.`picture` (`picture_name`,`user_id`,`realestate_id`,`is_profile`) VALUES ('Kupatilo', '2', '1', false);
INSERT INTO `netadvert`.`picture` (`picture_name`,`user_id`,`realestate_id`,`is_profile`) VALUES ('Dnevna soba', '2', '2', true);
INSERT INTO `netadvert`.`picture` (`picture_name`,`user_id`,`realestate_id`,`is_profile`) VALUES ('Spavaca soba', '2', '2', false);
INSERT INTO `netadvert`.`picture` (`picture_name`,`user_id`,`realestate_id`,`is_profile`) VALUES ('Kuhinja', '2', '2', false);


INSERT INTO `netadvert`.`advert` (`user_id`,`created_at`,`updated_at`,`expire_date`,`advert_rate`,`contact`,`description`,`is_sold`,`is_deleted`,`rent_sale`,`realestate_id`) VALUES ('2', '2016-11-28 12:45:00', '2016-11-29 14:05:12', '2016-12-25', 3, '060/123-456', 'description', false, false, true, '1');
INSERT INTO `netadvert`.`advert` (`user_id`,`created_at`,`updated_at`,`expire_date`,`advert_rate`,`contact`,`description`,`is_sold`,`is_deleted`,`rent_sale`,`realestate_id`) VALUES ('2', '2016-11-14 18:19:00', '2016-11-14 18:19:00', '2016-12-11', 5, '060/123-456', 'description', false, false, true, '2');
