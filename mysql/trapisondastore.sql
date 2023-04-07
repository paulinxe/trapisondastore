CREATE database IF NOT EXISTS trapisondastore;

CREATE USER 'trapisondastore'@'%' IDENTIFIED BY 'secret';

GRANT ALL PRIVILEGES ON trapisondastore.* TO 'trapisondastore'@'%' WITH GRANT OPTION;
