CREATE database IF NOT EXISTS trapisondastore;

CREATE USER 'trapisondastore'@'%' IDENTIFIED BY 'secret';

GRANT ALL PRIVILEGES ON trapisondastore.* TO 'trapisondastore'@'%' WITH GRANT OPTION;

CREATE database IF NOT EXISTS test_trapisondastore;

GRANT ALL PRIVILEGES ON test_trapisondastore.* TO 'trapisondastore'@'%' WITH GRANT OPTION;

