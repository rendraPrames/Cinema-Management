
CREATE OR REPLACE FUNCTION trigger_set_timestamp()
RETURNS TRIGGER AS $$
BEGIN
  NEW.updated_at = NOW();
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TABLE users (
	user_id SERIAL primary key NOT NULL,
	username varchar(50) NULL,
	email_id varchar(255) NULL,
	"password" varchar(255) NULL,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TRIGGER set_timestamp
BEFORE UPDATE ON users
FOR EACH ROW
EXECUTE PROCEDURE trigger_set_timestamp();

CREATE TABLE films (
	film_id SERIAL NOT null primary KEY,
	"name" varchar(100) NULL,
	is_playing int NOT NULL,
	 created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TRIGGER set_timestamp
BEFORE UPDATE ON films
FOR EACH ROW
EXECUTE PROCEDURE trigger_set_timestamp();

create table seats(
    seat_id SERIAL primary key not null,
    seat_number int not null,
    studio_name varchar(50) not null,
    is_available int not null default 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TRIGGER set_timestamp
BEFORE UPDATE ON seats
FOR EACH ROW
EXECUTE PROCEDURE trigger_set_timestamp();

create table schedules (
    schedule_id SERIAL primary key not null,
    film_id int not null,
    seat_id int not null,
    price int not null,
    date_show date not null,
    show_start time not null,
    show_end time not null,  
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    foreign key (film_id) REFERENCES Films(film_id),
    foreign key (seat_id) REFERENCES Seats(seat_id)
);

CREATE TRIGGER set_timestamp
BEFORE UPDATE ON schedules
FOR EACH ROW
EXECUTE PROCEDURE trigger_set_timestamp();

create table booking(
    booking_id SERIAL primary key not null,
    user_id int,
    schedule_id int,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    foreign key (user_id) references Users(user_id),
    foreign key (schedule_id) references Schedules(schedule_id)
);

CREATE TRIGGER set_timestamp
BEFORE UPDATE ON booking
FOR EACH ROW
EXECUTE PROCEDURE trigger_set_timestamp();