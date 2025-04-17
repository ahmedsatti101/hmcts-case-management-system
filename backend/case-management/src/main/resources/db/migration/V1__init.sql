CREATE TABLE tasks (
  id BIGSERIAL PRIMARY KEY,
  title varchar(255) NOT NULL,
  description varchar(255) DEFAULT NULL,
  status varchar(255) NOT NULL,
  due TIMESTAMP NOT NULL
);
