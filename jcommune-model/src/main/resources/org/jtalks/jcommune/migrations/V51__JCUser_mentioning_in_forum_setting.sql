ALTER TABLE JC_USER_DETAILS ADD(MENTIONING_NOTIFICATIONS_ENABLED TINYINT(1) DEFAULT 1);
UPDATE JC_USER_DETAILS SET MENTIONING_NOTIFICATIONS_ENABLED=1;