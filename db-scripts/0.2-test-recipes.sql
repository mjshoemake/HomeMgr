-- Feature:  Test recipes
INSERT INTO `mshoemake`.`meals` (`name`) VALUES ('Test');


ALTER TABLE `mshoemake`.`recipes` ADD COLUMN `favorite` VARCHAR(3) NOT NULL  AFTER `serving_size` ;

-- Update existing recipes
update mshoemake.recipes set favorite='Yes' where recipes_pk > 0;

