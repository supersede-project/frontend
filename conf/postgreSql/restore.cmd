SET PGPASSWORD=testDB
psql -U testDB atos < install.sql
psql -U testDB siemens < install.sql
psql -U testDB senercon < install.sql
pause