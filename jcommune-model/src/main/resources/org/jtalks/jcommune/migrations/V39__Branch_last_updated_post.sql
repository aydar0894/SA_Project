
ALTER TABLE BRANCHES ADD LAST_POST BIGINT(20) DEFAULT NULL;
ALTER TABLE BRANCHES ADD 
	CONSTRAINT BRANCH_LAST_POST_FK
	FOREIGN KEY(LAST_POST)
	REFERENCES POST(POST_ID);
		

