CREATE TABLE admin (
	username varchar(32) NOT NULL,
	password varchar(128) NOT NULL,
	name varchar(40) NOT NULL,
	surname varchar(40) NOT NULL,
	PRIMARY KEY(username)
);

CREATE TYPE specialty AS ENUM ( 'pathologos', 'ofthalmiatros', 'orthopedikos');

CREATE TABLE doctor (
	username varchar(32) NOT NULL,
	password varchar(128) NOT NULL,
	spec specialty NOT NULL,
	name varchar(40) NOT NULL,
	surname varchar(40) NOT NULL,
	PRIMARY KEY(username),
    CONSTRAINT doctor_specialty_check CHECK (((spec)::text = ANY ((ARRAY['pathologos'::character varying, 'ofthalmiatros'::character varying, 'orthopedikos'::character varying])::text[])))
);

CREATE TABLE patient (
	username varchar(32) NOT NULL,
	password varchar(128) NOT NULL,
	amka varchar(11) NOT NULL,
	name varchar(40) NOT NULL,
	surname varchar(40) NOT NULL,
	PRIMARY KEY(username),
	UNIQUE(amka)
);

CREATE TABLE availabillity(
	username varchar(32) NOT NULL,
	d_start timestamp NOT NULL,
	d_end timestamp NOT NULL,
	PRIMARY KEY(username, d_start, d_end),
	FOREIGN KEY(username) REFERENCES doctor(username)
);

CREATE TABLE appointments (
	doc_username varchar(32) NOT NULL,
	pat_username varchar(32) NOT NULL,
	d timestamp NOT NULL,
	PRIMARY KEY(doc_username,pat_username, d),
	FOREIGN KEY(doc_username) REFERENCES doctor(username),
	FOREIGN KEY(pat_username) REFERENCES patient(username)
);

INSERT INTO admin (username, password, name, surname)
	VALUES ('admin', 'pass', 'admin', 'admin');
INSERT INTO admin (username, password, name, surname)
	VALUES ('1admin', 'pass', '1admin', '1admin');

INSERT INTO patient (username, password, amka, name, surname)
	VALUES ('asth1', 'pass', '01234567891', 'John', 'Jojn');
INSERT INTO patient (username, password, amka, name, surname)
	VALUES ('eco', 'pass', '01234567892', 'Mak', 'JJojn');
INSERT INTO patient (username, password, amka, name, surname)
	VALUES ('wea', 'pass', '01234567893', 'Hvt', 'JIJojn');
INSERT INTO patient (username, password, amka, name, surname)
	VALUES ('cd', 'pass', '01234567894', 'Kl', 'JoJJjn');

INSERT INTO doctor (username, password, spec, name, surname)
	VALUES ('docc', 'pass', 'ofthalmiatros', 'Mik', 'PPP');
INSERT INTO doctor (username, password, spec, name, surname)
	VALUES('docc2', 'pass', 'ofthalmiatros', 'Gkk', 'Rpro');
INSERT INTO doctor (username, password, spec, name, surname)
	VALUES ('docc3', 'pass', 'orthopedikos', 'eur', 'liii');
INSERT INTO doctor (username, password, spec, name, surname)
	VALUES ('docc5', 'pass', 'pathologos', 'wee', 'PPP');
INSERT INTO doctor (username, password, spec, name, surname)
	VALUES ('docc0', 'pass', 'pathologos', 'rrr', 'PPP');

INSERT INTO availabillity (username, d_start, d_end)
	VALUES ('docc', to_timestamp('18-06-2017 09:30', 'DD-MM-YYYY hh24:mi'),to_timestamp('18-06-2017 15:30', 'DD-MM-YYYY hh24:mi'));
INSERT INTO availabillity (username, d_start, d_end)
	VALUES ('docc2', to_timestamp('18-06-2017 09:30', 'DD-MM-YYYY hh24:mi'),to_timestamp('18-06-2017 19:00', 'DD-MM-YYYY hh24:mi'));
INSERT INTO availabillity (username, d_start, d_end)
	VALUES ('docc2', to_timestamp('28-06-2017 09:30', 'DD-MM-YYYY hh24:mi'),to_timestamp('28-06-2017 20:30', 'DD-MM-YYYY hh24:mi'));
INSERT INTO availabillity (username, d_start, d_end)
	VALUES ('docc2', to_timestamp('19-06-2017 09:30', 'DD-MM-YYYY hh24:mi'),to_timestamp('19-06-2017 19:30', 'DD-MM-YYYY hh24:mi'));
INSERT INTO availabillity (username, d_start, d_end)
	VALUES ('docc0', to_timestamp('19-06-2017 09:30', 'DD-MM-YYYY hh24:mi'),to_timestamp('19-06-2017 19:30', 'DD-MM-YYYY hh24:mi'));
INSERT INTO availabillity (username, d_start, d_end)
	VALUES ('docc0', to_timestamp('20-06-2017 09:30', 'DD-MM-YYYY hh24:mi'),to_timestamp('20-06-2017 22:00', 'DD-MM-YYYY hh24:mi'));
INSERT INTO availabillity (username, d_start, d_end)
	VALUES ('docc0', to_timestamp('21-06-2017 10:30', 'DD-MM-YYYY hh24:mi'),to_timestamp('21-06-2017 19:00', 'DD-MM-YYYY hh24:mi'));
INSERT INTO availabillity (username, d_start, d_end)
	VALUES ('docc3', to_timestamp('18-06-2017 09:30', 'DD-MM-YYYY hh24:mi'),to_timestamp('18-06-2017 19:30', 'DD-MM-YYYY hh24:mi'));
INSERT INTO availabillity (username, d_start, d_end)
	VALUES ('docc5', to_timestamp('19-06-2017 19:30', 'DD-MM-YYYY hh24:mi'),to_timestamp('19-06-2017 21:00', 'DD-MM-YYYY hh24:mi'));

INSERT INTO appointments(doc_username, pat_username, d)
	VALUES ('docc', 'asth1', to_timestamp('18-06-2017 10:00', 'DD-MM-YYYY hh24:mi'));
INSERT INTO appointments(doc_username, pat_username, d)
	VALUES ('docc3', 'asth1', to_timestamp('18-06-2017 12:30', 'DD-MM-YYYY hh24:mi'));
INSERT INTO appointments(doc_username, pat_username, d)
	VALUES ('docc0', 'asth1', to_timestamp('20-06-2017 10:00', 'DD-MM-YYYY hh24:mi'));
INSERT INTO appointments(doc_username, pat_username, d)
	VALUES ('docc0', 'asth1', to_timestamp('21-06-2017 15:00', 'DD-MM-YYYY hh24:mi'));
INSERT INTO appointments(doc_username, pat_username, d)
	VALUES ('docc0', 'wea', to_timestamp('20-06-2017 18:00', 'DD-MM-YYYY hh24:mi'));
INSERT INTO appointments(doc_username, pat_username, d)
	VALUES ('docc0', 'wea', to_timestamp('20-06-2017 18:30', 'DD-MM-YYYY hh24:mi'));
INSERT INTO appointments(doc_username, pat_username, d)
	VALUES ('docc0', 'wea', to_timestamp('20-06-2017 19:30', 'DD-MM-YYYY hh24:mi'));
INSERT INTO appointments(doc_username, pat_username, d)
	VALUES ('docc0', 'wea', to_timestamp('20-06-2017 20:30', 'DD-MM-YYYY hh24:mi'));
INSERT INTO appointments(doc_username, pat_username, d)
	VALUES ('docc0', 'wea', to_timestamp('20-06-2017 21:00', 'DD-MM-YYYY hh24:mi'));
INSERT INTO appointments(doc_username, pat_username, d)
	VALUES ('docc0', 'wea', to_timestamp('20-06-2017 21:30', 'DD-MM-YYYY hh24:mi'));
