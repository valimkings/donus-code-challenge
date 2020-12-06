create table account
(
    id         uuid not null
               constraint account_pkey
               primary key,
    balance    numeric(19, 2),
    created_at timestamp,
    cpf        varchar(11),
    name       varchar(60),
    updated_at timestamp
);

create table transaction
(
    id            uuid not null
                  constraint transaction_pkey
                  primary key,
    account_id    uuid,
    destination_account_id uuid,
    amount        numeric(19, 2),
    old_balance  numeric(19, 2),
    balance    numeric(19, 2),
    created_at    timestamp,
    type          varchar(15)
);

create unique index account_document_uindex
    on account (cpf);