CREATE TABLE IF NOT EXISTS roles (
	id SERIAL not null primary key,
	role_name VARCHAR(30) not null
);

CREATE TABLE IF NOT EXISTS users (
    id SERIAL not null primary key,
    userName VARCHAR(30) not null,
    email VARCHAR(30) not null unique,
    pass VARCHAR(30) not null,
    role_id INT not null,
    foreign key (role_id) references roles (id)
);

CREATE TABLE IF NOT EXISTS states (
	id SERIAL not null primary key,
	state_name VARCHAR(30) not null
);

CREATE TABLE IF NOT EXISTS orders (
	id SERIAL not null primary key,
    title VARCHAR(30) not null,
    message VARCHAR not null,
    user_id INT not null,
    price DOUBLE PRECISION,
    worker_id INT,
    state_id INT not null, 
    foreign key (user_id) references users (id) on delete cascade,
    foreign key (worker_id) references users (id),
    foreign key (state_id) references states (id)
);

INSERT into roles (role_name) VALUES('manager');
INSERT into roles (role_name) VALUES('worker');
INSERT into roles (role_name) VALUES('user');

INSERT into states (state_name) VALUES('processing');
INSERT into states (state_name) VALUES('working');
INSERT into states (state_name) VALUES('done');

INSERT into users (username, email, pass, role_id) VALUES('Bob', 'bob@', '111', 1);
INSERT into users (username, email, pass, role_id) VALUES('Carl', 'carl@', '222', 2);
INSERT into users (username, email, pass, role_id) VALUES('Tom', 'tom@', '333', 3);
INSERT into users (username, email, pass, role_id) VALUES('Anna', 'ann@', '444', 1);
INSERT into users (username, email, pass, role_id) VALUES('Anna2', 'ann@2', '444', 2);
INSERT into users (username, email, pass, role_id) VALUES('Anna3', 'ann@3', '444', 3);
INSERT into users (username, email, pass, role_id) VALUES('Anna4', 'ann@4', '444', 3);
INSERT into users (username, email, pass, role_id) VALUES('Anna5', 'ann@5', '444', 3);
INSERT into users (username, email, pass, role_id) VALUES('Anna6', 'ann@6', '444', 1);
INSERT into users (username, email, pass, role_id) VALUES('Anna7', 'ann@7', '444', 1);
INSERT into users (username, email, pass, role_id) VALUES('Anna8', 'ann@8', '444', 2);
INSERT into users (username, email, pass, role_id) VALUES('Anna9', 'ann@9', '444', 1);
INSERT into users (username, email, pass, role_id) VALUES('Anna10', 'ann@10', '444', 3);

INSERT into orders (title, message, user_id, price, worker_id, state_id) VALUES('Bathroom', 'Need to repair bathroom', 3, 150.5, 2, 3);
INSERT into orders (title, message, user_id, price, worker_id, state_id) VALUES('Kitchen', 'Fire!!! HEEELP', 3, 357.5, 2, 1);
INSERT into orders (title, message, user_id, price, worker_id, state_id) VALUES('Bed', 'My bed is broken', 1, 999.0, 2, 3);
INSERT into orders (title, message, user_id, price, worker_id, state_id) VALUES('Door', 'Test test test test test test', 3, 100000.2, 2, 1);


select id, title, message, price, (SELECT username FROM users INNER JOIN orders ON orders.worker_id=users.id where orders.id=1), (SELECT state_name FROM states INNER JOIN orders ON orders.state_id=states.id where orders.id=1) FROM orders WHERE user_id=3 order by id;


select username FROM users INNER JOIN orders ON orders.user_id=users.id where orders.id=3;


select state_name FROM states INNER JOIN orders ON orders.state_id=states.id where orders.id=3;


SELECT username, email, pass, (SELECT role_name FROM roles INNER JOIN users ON users.role_id=roles.id WHERE users.username='Tom') FROM users WHERE username='Tom'


UPDATE orders SET worker_id=(select id from users where username='Test' order by id) WHERE id=4;


SELECT id, username, email, role_id FROM users;


select * from users inner join roles on roles.id=users.role_id where roles.role_name='worker';


delete from orders using users where orders.user_id=users.id and users.id=6;


delete from users where id=5;


SELECT id, username, email, pass, (SELECT role_name FROM roles INNER JOIN users ON users.role_id=roles.id WHERE users.username='Tom') FROM users WHERE username='Tom';


SELECT role_name FROM roles INNER JOIN users ON users.role_id=roles.id WHERE users.username='Tom';


SELECT * FROM orders where user_id=3 ORDER BY price desc;
