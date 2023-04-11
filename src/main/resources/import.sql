INSERT INTO db_pizzeria.pizzas(name, description,  price, img_path) VALUES('carrettiera', 'provola, salsiccia e friarielli', 12.00, 'https://th.bing.com/th/id/OIP.jZdD_NDGOkaoga8eZlpO7gHaFk?pid=ImgDet&rs=1');
INSERT INTO db_pizzeria.pizzas(name, description,  price, img_path) VALUES('marinara', 'pomodoro e origano', 6.50, 'https://www.pizzarecipe.org/wp-content/uploads/2019/01/Pizza-Marinara.jpg');
INSERT INTO db_pizzeria.pizzas(name, description,  price, img_path) VALUES('margherita', 'pomodoro e mozzarella', 8.00, 'https://bing.com/th?id=OSK.a0b6165df951cda8c34d1c7020112703');
INSERT INTO db_pizzeria.pizzas(name, description,  price, img_path) VALUES('atomica', "burrata e 'nduja", 13.00, 'https://th.bing.com/th/id/OIP.3XreRBTz-1szWo-Gv_0x_gHaE8?pid=ImgDet&rs=1');

INSERT INTO db_pizzeria.offers(end_date, start_date, title, pizza_id) VALUES('2023-05-01', '2023-06-01', '15', 1);
INSERT INTO db_pizzeria.offers(end_date, start_date, title, pizza_id) VALUES('2023-06-01', '2023-07-01', '25', 1);
INSERT INTO db_pizzeria.offers(end_date, start_date, title, pizza_id) VALUES('2023-07-01', '2023-08-01', '35', 1);

INSERT INTO db_pizzeria.offers(end_date, start_date, title, pizza_id) VALUES('2023-05-01', '2023-06-01', '10', 2);
INSERT INTO db_pizzeria.offers(end_date, start_date, title, pizza_id) VALUES('2023-06-01', '2023-07-01', '20', 2);
INSERT INTO db_pizzeria.offers(end_date, start_date, title, pizza_id) VALUES('2023-07-01', '2023-08-01', '30', 2);

INSERT INTO db_pizzeria.offers(end_date, start_date, title, pizza_id) VALUES('2023-05-01', '2023-06-01', '13', 3);
INSERT INTO db_pizzeria.offers(end_date, start_date, title, pizza_id) VALUES('2023-06-01', '2023-07-01', '23', 3);
INSERT INTO db_pizzeria.offers(end_date, start_date, title, pizza_id) VALUES('2023-07-01', '2023-08-01', '33', 3);

INSERT INTO db_pizzeria.ingredients(name) VALUES('pomodoro');
INSERT INTO db_pizzeria.ingredients(name) VALUES('mozzarella');
INSERT INTO db_pizzeria.ingredients(name) VALUES('origano');
INSERT INTO db_pizzeria.ingredients(name) VALUES('provola');
INSERT INTO db_pizzeria.ingredients(name) VALUES('salsiccia');
INSERT INTO db_pizzeria.ingredients(name) VALUES('friarielli');
INSERT INTO db_pizzeria.ingredients(name) VALUES('burrata');
INSERT INTO db_pizzeria.ingredients(name) VALUES("'nduja");

INSERT INTO users (email, first_name, last_name, password) VALUES('john@email.it', 'John', 'Doe', '{noop}john');
INSERT INTO users (email, first_name, last_name, password) VALUES('jane@email.it', 'Jane', 'Smith','{noop}jane');

INSERT INTO roles (id, name) VALUES(1, 'ADMIN');
INSERT INTO roles (id, name) VALUES(2, 'USER');

INSERT into users_roles(user_id, roles_id) VALUES(1, 1);
INSERT into users_roles(user_id, roles_id) VALUES(2, 2);