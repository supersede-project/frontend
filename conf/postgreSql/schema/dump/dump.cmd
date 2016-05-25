SET PGPASSWORD=testDB
pg_dump -c -s -U testDB -f schema.sql atos
pg_dump -c -U testDB -f install.sql atos
pause