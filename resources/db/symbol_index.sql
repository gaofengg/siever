PRAGMA foreign_keys=OFF;
BEGIN TRANSACTION;
CREATE TABLE symbols_list_tbl (id INTEGER UNIQUE NOT NULL, conid STRING PRIMARY KEY UNIQUE NOT NULL);
CREATE UNIQUE INDEX conid_index ON symbols_list_tbl (conid);
COMMIT;