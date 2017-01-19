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


INSERT INTO `netadvert`.`realestate` (`realestate_name`,`area`,`heating`,`category_id`,`type_id`,`location_id`) VALUES ('Stan na grbavici', 45, false, 1, 2, 1);
INSERT INTO `netadvert`.`realestate` (`realestate_name`,`area`,`heating`,`category_id`,`type_id`,`location_id`) VALUES ('Kuca na novom naselju', 60, true, 1, 1, 2);


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


INSERT INTO `netadvert`.`user` (`first_name`,`last_name`,`password`,`email`,`role_id`,`user_rate`) VALUES ('Milos', 'Andric','pass', 'milosa942@gmail.com', '1', 0);
INSERT INTO `netadvert`.`user` (`first_name`,`last_name`,`password`,`email`,`role_id`,`user_rate`) VALUES ('Milos', 'Obradovic','pass', 'milossm94@hotmail.com', '2', 0);
INSERT INTO `netadvert`.`user` (`first_name`,`last_name`,`password`,`email`,`role_id`,`user_rate`) VALUES ('Mladen', 'Doslic','pass', 'doslicmm@live.com', '3', 0);
INSERT INTO `netadvert`.`user` (`first_name`,`last_name`,`password`,`email`,`role_id`,`user_rate`) VALUES ('Milan', 'Milanovic','pass', 'milan@gmail.com', '2', 0);

INSERT INTO `netadvert`.`advert` (`advert_name`,`user_id`,`created_at`,`updated_at`,`expire_date`,`cost`,`advert_rate`,`contact`,`description`,`is_sold`,`is_deleted`,`rent_sale`,`realestate_id`) VALUES ('Izdaje se stan','2', '2016-11-28 12:45:00', '2016-11-29 14:05:12', '2017-12-11', 25000, 4, '060/123-456', 'description', false, false, true, '1');
INSERT INTO `netadvert`.`advert` (`advert_name`,`user_id`,`created_at`,`updated_at`,`expire_date`,`cost`,`advert_rate`,`contact`,`description`,`is_sold`,`is_deleted`,`rent_sale`,`realestate_id`) VALUES ('HITNO se prodaje kuca!!!','2', '2017-11-14 18:19:00', '2016-11-14 18:19:00', '2017-12-11', 40000, 5, '060/123-456', 'description', false, false, true, '2');

INSERT INTO `netadvert`.`picture` (`picture_name`,`user_id`,`advert_id`,`is_profile`) VALUES ('Spoljasnji izgled', '2', '1', true);
INSERT INTO `netadvert`.`picture` (`picture_name`,`user_id`,`advert_id`,`is_profile`) VALUES ('Dnevna soba', '2', '1', false);
INSERT INTO `netadvert`.`picture` (`picture_name`,`user_id`,`advert_id`,`is_profile`) VALUES ('Kuhinja', '2', '1', false);
INSERT INTO `netadvert`.`picture` (`picture_name`,`user_id`,`advert_id`,`is_profile`) VALUES ('Kupatilo', '2', '1', false);
INSERT INTO `netadvert`.`picture` (`picture_name`,`user_id`,`advert_id`,`is_profile`) VALUES ('Dnevna soba', '2', '2', true);
INSERT INTO `netadvert`.`picture` (`picture_name`,`user_id`,`advert_id`,`is_profile`) VALUES ('Spavaca soba', '2', '2', false);
INSERT INTO `netadvert`.`picture` (`picture_name`,`user_id`,`advert_id`,`is_profile`) VALUES ('Kuhinja', '2', '2', false);

INSERT INTO `netadvert`.`comment` (`date`, `text`, `advert_id`, `user_id`) VALUES ('2016-11-28 12:45:00', 'ovo je moj komentar na oglas', '1', '2');
INSERT INTO `netadvert`.`comment` (`date`, `text`, `advert_id`, `user_id`) VALUES ('2016-11-28 12:45:00', 'open moj komm', '1', '2');

INSERT INTO `netadvert`.`advert_rating` (`rating`, `advert_id`, `user_id`) VALUES ('4', '1', '2');
INSERT INTO `netadvert`.`advert_rating` (`rating`, `advert_id`, `user_id`) VALUES ('5', '2', '2');

INSERT INTO `netadvert`.`user` (`first_name`,`last_name`,`password`,`email`,`role_id`,`user_rate`) VALUES ('Miki', 'Mikic','pass', 'mikim@gmail.com', '2', 0);
INSERT INTO `netadvert`.`user` (`first_name`,`last_name`,`password`,`email`,`role_id`,`user_rate`) VALUES ('Jova', 'Jovic','pass', 'jovaj@gmail.com', '2', 5);

INSERT INTO `netadvert`.`advert` (`advert_name`,`user_id`,`created_at`,`updated_at`,`expire_date`,`cost`,`advert_rate`,`contact`,`description`,`is_sold`,`is_deleted`,`rent_sale`,`realestate_id`) VALUES ('Na kraj sela zuta kuca','5', '2017-11-14 18:19:00', '2016-11-14 18:19:00', '2017-12-11', 45000, 0, '060/123-456', 'description', false, false, true, '1');
INSERT INTO `netadvert`.`advert` (`advert_name`,`user_id`,`created_at`,`updated_at`,`expire_date`,`cost`,`advert_rate`,`contact`,`description`,`is_sold`,`is_deleted`,`rent_sale`,`realestate_id`) VALUES ('Dvosoban stan centar grada','5', '2017-11-14 18:19:00', '2016-11-14 18:19:00', '2017-12-11', 45000, 0, '060/123-456', 'description', false, false, true, '2');
INSERT INTO `netadvert`.`advert` (`advert_name`,`user_id`,`created_at`,`updated_at`,`expire_date`,`cost`,`advert_rate`,`contact`,`description`,`is_sold`,`is_deleted`,`rent_sale`,`realestate_id`) VALUES ('Duplex liman','6', '2017-11-14 18:19:00', '2016-11-14 18:19:00', '2017-12-11', 55000, 0, '060/123-456', 'description', false, false, true, '1');
INSERT INTO `netadvert`.`advert` (`advert_name`,`user_id`,`created_at`,`updated_at`,`expire_date`,`cost`,`advert_rate`,`contact`,`description`,`is_sold`,`is_deleted`,`rent_sale`,`realestate_id`) VALUES ('Duplex novo naselje','6', '2017-11-14 18:19:00', '2016-11-14 18:19:00', '2017-12-11', 30000, 0, '060/123-456', 'description', false, false, true, '2');
INSERT INTO `netadvert`.`advert` (`advert_name`,`user_id`,`created_at`,`updated_at`,`expire_date`,`cost`,`advert_rate`,`contact`,`description`,`is_sold`,`is_deleted`,`rent_sale`,`realestate_id`) VALUES ('Garsonjera','4', '2017-11-14 18:19:00', '2016-11-14 18:19:00', '2017-12-11', 1000, 0, '060/123-456', 'description', false, false, true, '1');
INSERT INTO `netadvert`.`advert` (`advert_name`,`user_id`,`created_at`,`updated_at`,`expire_date`,`cost`,`advert_rate`,`contact`,`description`,`is_sold`,`is_deleted`,`rent_sale`,`realestate_id`) VALUES ('Duplex detelinara','2', '2017-11-14 18:19:00', '2016-11-14 18:19:00','2017-12-11', 55000, 0, '060/123-456', 'description', false, false, true, '2');

INSERT INTO `netadvert`.`advert` (`advert_name`,`user_id`,`created_at`,`updated_at`,`expire_date`,`cost`,`advert_rate`,`contact`,`description`,`is_sold`,`is_deleted`,`rent_sale`,`realestate_id`) VALUES ('Strazilovska stan','6', '2017-11-14 18:19:00', '2016-11-14 18:19:00', '2017-12-11', 2000, 0, '060/123-456', 'description', false, false, true, '1');
INSERT INTO `netadvert`.`advert` (`advert_name`,`user_id`,`created_at`,`updated_at`,`expire_date`,`cost`,`advert_rate`,`contact`,`description`,`is_sold`,`is_deleted`,`rent_sale`,`realestate_id`) VALUES ('Dvosoban stan na prodaju!','5', '2017-11-14 18:19:00', '2016-11-14 18:19:00', '2017-12-11', 45000, 0, '060/123-456', 'description', false, false, true, '1');
INSERT INTO `netadvert`.`advert` (`advert_name`,`user_id`,`created_at`,`updated_at`,`expire_date`,`cost`,`advert_rate`,`contact`,`description`,`is_sold`,`is_deleted`,`rent_sale`,`realestate_id`) VALUES ('Garsonjera detelinara','5', '2017-11-14 18:19:00', '2016-11-14 18:19:00', '2017-12-11', 9000, 0, '060/123-456', 'description', false, false, true, '1');
INSERT INTO `netadvert`.`advert` (`advert_name`,`user_id`,`created_at`,`updated_at`,`expire_date`,`cost`,`advert_rate`,`contact`,`description`,`is_sold`,`is_deleted`,`rent_sale`,`realestate_id`) VALUES ('Duplex detelinara','4', '2017-11-14 18:19:00', '2016-11-14 18:19:00', '2017-12-11', 70000, 0, '060/123-456', 'description', false, false, true, '2');
INSERT INTO `netadvert`.`advert` (`advert_name`,`user_id`,`created_at`,`updated_at`,`expire_date`,`cost`,`advert_rate`,`contact`,`description`,`is_sold`,`is_deleted`,`rent_sale`,`realestate_id`) VALUES ('Garsonjera klisa','4', '2017-11-14 18:19:00', '2016-11-14 18:19:00', '2017-12-11', 300, 0, '060/123-456', 'description', false, false, true, '2');
INSERT INTO `netadvert`.`advert` (`advert_name`,`user_id`,`created_at`,`updated_at`,`expire_date`,`cost`,`advert_rate`,`contact`,`description`,`is_sold`,`is_deleted`,`rent_sale`,`realestate_id`) VALUES ('Duplex klisa','2', '2017-11-14 18:19:00', '2016-11-14 18:19:00', '2017-12-11', 2200, 0, '060/123-456', 'description', false, false, true, '2');

INSERT INTO `netadvert`.`comment` (`date`, `text`, `advert_id`, `user_id`) VALUES ('2016-11-28 12:45:00', 'Nije lose', '4', '2');
INSERT INTO `netadvert`.`comment` (`date`, `text`, `advert_id`, `user_id`) VALUES ('2016-11-28 12:45:00', 'Bezveze', '5', '2');
INSERT INTO `netadvert`.`comment` (`date`, `text`, `advert_id`, `user_id`) VALUES ('2016-11-28 12:45:00', 'ovo je moj komentar na oglas', '3', '5');
INSERT INTO `netadvert`.`comment` (`date`, `text`, `advert_id`, `user_id`) VALUES ('2016-11-28 12:45:00', 'open moj komm', '3', '6');
INSERT INTO `netadvert`.`comment` (`date`, `text`, `advert_id`, `user_id`) VALUES ('2016-11-28 12:45:00', 'Nije lose', '3', '2');
INSERT INTO `netadvert`.`comment` (`date`, `text`, `advert_id`, `user_id`) VALUES ('2016-11-28 12:45:00', 'Bezveze', '4', '5');

INSERT INTO `netadvert`.`report` (`report_description`, `verified`, `advert_id`, `user_id`, `visited`) VALUES ('Los oglas! Komsijina kuca!', '0', '1', '4', '0');
INSERT INTO `netadvert`.`report` (`report_description`, `verified`, `advert_id`, `user_id`, `visited`) VALUES ('Ne valja nista!', '0', '1', '5', '0');

INSERT INTO `netadvert`.`report` (`report_description`, `verified`, `advert_id`, `user_id`, `visited`) VALUES ('Los oglas! Stan se ne nalazi na toj lokaciji!', '0', '3', '5', '0');
INSERT INTO `netadvert`.`report` (`report_description`, `verified`, `advert_id`, `user_id`, `visited`) VALUES ('Ne valja nista!', '0', '4', '2', '0');
INSERT INTO `netadvert`.`user_rating` (`rating`, `user_id`, `user_rated_id`) VALUES ('5', '4', '5');
