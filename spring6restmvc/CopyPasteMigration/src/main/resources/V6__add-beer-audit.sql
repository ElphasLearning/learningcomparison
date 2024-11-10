drop table if exists beer_audit;

    create table beer_audit (
       audit_id varchar(36) not null,
       id varchar(36) not null,
        beer_name varchar(50) not null,
        beer_style smallint not null,
        created_date datetime(6),
        price decimal(38,2) not null,
        quantity_on_hand integer,
        upc varchar(255) not null,
        update_date datetime(6),
        created_date datetime(6),
        created_date_udit datetime(6),
        version integer,
        principal_name varchar(36),
        audit_event_type varchar(36),
        primary key (audit_id)
    ) engine=InnoDB;