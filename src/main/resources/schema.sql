create TABLE IF NOT EXISTS ACCOUNT (
    id serial,
    client_name varchar(255),
    balance numeric NOT NULL DEFAULT 0,
    primary key (id)
 );


CREATE TABLE IF NOT EXISTS transfer (
    transfer_id serial,
    from_account_id integer REFERENCES account (id) on delete set null,
    to_account_id integer REFERENCES account (id) on delete set null,
    amount numeric NOT NULL,
    transfer_date timestamp,
    comment varchar(255),
    primary key (transfer_id)
);
