CREATE TABLE IF NOT EXISTS companies (
    id INT NOT NULL,
    name varchar(250) NOT NULL,
    bin BIGINT NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS product (
    id INT NOT NULL,
    name varchar(250) NOT NULL,
    size INT NOT NULL,
    price BIGINT NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS users (
    id INT NOT NULL,
    second_name varchar(250) NOT NULL,
    name varchar(250) NOT NULL,
    role INT NOT NULL,
    id_company INT NOT NULL,
    password varchar(15) NOT NULL,
    login varchar(250) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT "fk_id_company" FOREIGN KEY(id_company) REFERENCES companies(id)
);

CREATE TABLE IF NOT EXISTS token (
    id INT NOT NULL,
    id_user INT NOT NULL,
    token varchar(250) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT "fk_id_user" FOREIGN KEY(id_user) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS warehouse (
    id INT NOT NULL,
    name varchar(250) NOT NULL,
    address varchar(250) NOT NULL,
    size BIGINT NOT NULL,
    id_owner INT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT "fk_id_owner" FOREIGN KEY(id_owner) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS warehouse_product (
    id INT NOT NULL,
    id_product INT NOT NULL,
    product_quantity BIGINT NOT NULL,
    id_warehouse INT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT "fk_id_product" FOREIGN KEY(id_product) REFERENCES product(id),
    CONSTRAINT "fk_id_warehouse" FOREIGN KEY(id_warehouse) REFERENCES warehouse(id)
);