INSERT INTO account(client_name, balance)
VALUES ('Sergey Lebedev', 500);

INSERT INTO account(client_name, balance)
VALUES ('Alexandr Smoleev', 300000);

INSERT INTO account(client_name, balance)
VALUES ('Natalia Korneeva', 12000);

INSERT INTO account(client_name, balance)
VALUES ('Leonid Terekhov', 6000);


UPDATE account SET balance = balance - 5000 WHERE id = 2;
UPDATE account SET balance = balance + 5000 WHERE id = 1;
INSERT INTO transfer(from_account_id, to_account_id, amount, comment, transfer_date)
VALUES (2 , 1, 5000, 'for botinocki', current_timestamp);

UPDATE account SET balance = balance - 200 WHERE id = 2;
UPDATE account SET balance = balance + 200 WHERE id = 1;
INSERT INTO transfer(from_account_id, to_account_id, amount, comment, transfer_date)
VALUES (2 , 1, 200, 'for taxi', current_timestamp);

UPDATE account SET balance = balance - 150 WHERE id = 2;
UPDATE account SET balance = balance + 150 WHERE id = 1;
INSERT INTO transfer(from_account_id, to_account_id, amount, comment, transfer_date)
VALUES (2 , 1, 150, 'for segoreti', current_timestamp);

UPDATE account SET balance = balance - 2500 WHERE id = 3;
UPDATE account SET balance = balance + 2500 WHERE id = 4;
INSERT INTO transfer(from_account_id, to_account_id, amount, comment, transfer_date)
VALUES (3 , 4, 2500, 'za kvartiry', current_timestamp);

UPDATE account SET balance = balance - 400 WHERE id = 4;
UPDATE account SET balance = balance + 400 WHERE id = 2;
INSERT INTO transfer(from_account_id, to_account_id, amount, comment, transfer_date)
VALUES (4 , 2, 400, 'za lekarstva', current_timestamp);
