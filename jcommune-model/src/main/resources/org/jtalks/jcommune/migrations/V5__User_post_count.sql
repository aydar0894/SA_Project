ALTER TABLE `JC_USER_DETAILS` ADD `POST_COUNT` int NULL;
UPDATE `JC_USER_DETAILS` UD  SET `POST_COUNT` = (SELECT COUNT(1) from `POST` WHERE `USER_CREATED` = UD.`USER_ID`);