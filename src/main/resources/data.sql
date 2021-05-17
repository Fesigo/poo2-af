INSERT INTO tb_base_user(name, email) VALUES ('User1', 'user1@email.com');
INSERT INTO tb_base_user(name, email) VALUES ('User2', 'user2@email.com');
INSERT INTO tb_base_user(name, email) VALUES ('User3', 'user3@email.com');

INSERT INTO tb_base_user(name, email) VALUES ('User4', 'user4@email.com');
INSERT INTO tb_base_user(name, email) VALUES ('User5', 'user5@email.com');
INSERT INTO tb_base_user(name, email) VALUES ('User6', 'user6@email.com');

INSERT INTO tb_admin(user_id, phone_number) VALUES ('1', '(15) 123456789');
INSERT INTO tb_admin(user_id, phone_number) VALUES ('2', '(15) 123456789');
INSERT INTO tb_admin(user_id, phone_number) VALUES ('3', '(15) 123456789');

INSERT INTO tb_attend(user_id, balance) VALUES ('4', '100.0');
INSERT INTO tb_attend(user_id, balance) VALUES ('5', '200.0');
INSERT INTO tb_attend(user_id, balance) VALUES ('6', '300.0');

INSERT INTO tb_place (name, address) VALUES ('Lugar1', 'Rua X, 11');
INSERT INTO tb_place (name, address) VALUES ('Lugar2', 'Rua Y, 22');
INSERT INTO tb_place (name, address) VALUES ('Lugar3', 'Rua Z, 33');

INSERT INTO tb_event (name, description, start_date, end_date, start_time, end_time, email_contact, amount_free_tickets, amount_payed_tickets, price_ticket, user_id) VALUES ('Evento1', 'Desc1', '2021-03-01', '2021-03-05', '16:30:00', '18:00:00', 'email1@gmail.com', '3', '5', '50.0', '1');
INSERT INTO tb_event (name, description, start_date, end_date, start_time, end_time, email_contact, amount_free_tickets, amount_payed_tickets, price_ticket, user_id) VALUES ('Evento2', 'Desc2', '2021-03-15', '2021-03-20', '16:30:00', '18:00:00', 'email2@gmail.com', '9', '20', '100.0', '2');
INSERT INTO tb_event (name, description, start_date, end_date, start_time, end_time, email_contact, amount_free_tickets, amount_payed_tickets, price_ticket, user_id) VALUES ('Evento3', 'Desc3', '2021-02-28', '2021-03-07', '16:30:00', '18:00:00', 'email3@gmail.com', '30', '40', '200.0', '3');
