
CREATE TABLE address (
  ID      INTEGER NOT NULL,
  TOWN    VARCHAR(100),
  STREET  VARCHAR(100),
  HOUSE   VARCHAR(10),
  FLAT    VARCHAR(10),
  COUNTRY VARCHAR(100),
  PRIMARY KEY (ID)
);

CREATE TABLE company (
  ID      INTEGER NOT NULL,
  NAME    VARCHAR(20),
  WEBSITE VARCHAR(100),
  PRIMARY KEY (ID)
);

CREATE TABLE contact (
  ID         INTEGER     NOT NULL,
  NAME       VARCHAR(20) NOT NULL,
  SURNAME    VARCHAR(20) NOT NULL,
  FATHERNAME VARCHAR(20),
  BIRTHDAY   DATE,
  EMAIL      VARCHAR(50),
  ADDRESS_ID INTEGER     NOT NULL,
  COMPANY_ID INTEGER     NOT NULL,
  PRIMARY KEY (ID)
);

CREATE TABLE flower (
  ID   INTEGER     NOT NULL,
  NAME VARCHAR(20) NOT NULL,
  PRIMARY KEY (ID)
);

CREATE TABLE goods (
  ID         INTEGER NOT NULL,
  COST       FLOAT,
  FLOWER_ID  INTEGER,
  COMPANY_ID INTEGER,
  REMAIN     INTEGER,
  IMAGE_NAME VARCHAR(255),
  PRIMARY KEY (ID)
);

CREATE TABLE item (
  ID       INTEGER NOT NULL,
  GOODS_ID INTEGER,
  ORDER_ID INTEGER,
  QUANTITY INTEGER,
  PRIMARY KEY (ID)
);

CREATE TABLE order_altering (
  ID       INTEGER NOT NULL,
  "DATE"   TIMESTAMP,
  STATE_ID INTEGER,
  USER_ID  INTEGER,
  COMMENT  VARCHAR(100),
  ORDER_ID INTEGER NOT NULL,
  PRIMARY KEY (ID)
);

CREATE TABLE phone (
  ID         INTEGER NOT NULL,
  COUNTRY    VARCHAR(5),
  OPERATOR   VARCHAR(5),
  NUMBER     VARCHAR(10),
  TYPE       VARCHAR(250),
  COMMENT    VARCHAR(50),
  CONTACT_ID INTEGER,
  PRIMARY KEY (ID)
);

CREATE TABLE purchase (
  ID                  INTEGER NOT NULL,
  CUSTOMER_ID         INTEGER,
  DESCRIPTION         VARCHAR(300),
  MANAGER_ID          INTEGER,
  COST                FLOAT,
  STAFF_ID            INTEGER,
  DELIVERY_MANAGER_ID INTEGER,
  RECEIVER_ID         INTEGER,
  STATE_ID            INTEGER,
  DELIVERY_DATE       DATE,
  ADDRESS_ID          INTEGER,
  PRIMARY KEY (ID)
);

CREATE TABLE role (
  ID   INTEGER      NOT NULL,
  NAME VARCHAR(250) NOT NULL,
  PRIMARY KEY (ID)
);

CREATE TABLE state (
  ID          INTEGER NOT NULL,
  DESCRIPTION VARCHAR(250),
  PRIMARY KEY (ID)
);

CREATE TABLE "USER" (
  ID         INTEGER     NOT NULL,
  LOGIN      VARCHAR(20) NOT NULL,
  PASSWORD   VARCHAR(100) NOT NULL,
  CONTACT_ID INTEGER     NOT NULL,
  ROLE_ID    INTEGER,
  PRIMARY KEY (ID)
);

ALTER TABLE contact
ADD FOREIGN KEY (COMPANY_ID)
REFERENCES company (ID);

ALTER TABLE contact
ADD FOREIGN KEY (ADDRESS_ID)
REFERENCES address (ID);

ALTER TABLE goods
ADD FOREIGN KEY (FLOWER_ID)
REFERENCES flower (ID);

ALTER TABLE goods
ADD FOREIGN KEY (COMPANY_ID)
REFERENCES company (ID);

ALTER TABLE item
ADD FOREIGN KEY (ORDER_ID)
REFERENCES purchase (ID);

ALTER TABLE item
ADD FOREIGN KEY (GOODS_ID)
REFERENCES goods (ID)

ALTER TABLE order_altering
ADD FOREIGN KEY (USER_ID)
REFERENCES "USER" (ID)

ALTER TABLE order_altering
ADD FOREIGN KEY (STATE_ID)
REFERENCES state (ID)

ALTER TABLE order_altering
ADD FOREIGN KEY (ORDER_ID)
REFERENCES purchase (ID);

ALTER TABLE phone
ADD FOREIGN KEY (CONTACT_ID)
REFERENCES contact (ID);

ALTER TABLE purchase
ADD FOREIGN KEY (MANAGER_ID)
REFERENCES "USER" (ID);

ALTER TABLE purchase
ADD FOREIGN KEY (DELIVERY_MANAGER_ID)
REFERENCES "USER" (ID)

ALTER TABLE purchase
ADD FOREIGN KEY (ADDRESS_ID)
REFERENCES address (ID);

ALTER TABLE purchase
ADD FOREIGN KEY (CUSTOMER_ID)
REFERENCES contact (ID);

ALTER TABLE purchase
ADD FOREIGN KEY (STATE_ID)
REFERENCES state (ID);

ALTER TABLE purchase
ADD FOREIGN KEY (STAFF_ID)
REFERENCES "USER" (ID)

ALTER TABLE purchase
ADD FOREIGN KEY (RECEIVER_ID)
REFERENCES contact (ID);

ALTER TABLE "USER"
ADD FOREIGN KEY (ROLE_ID)
REFERENCES role (ID);

ALTER TABLE "USER"
ADD FOREIGN KEY (CONTACT_ID)
REFERENCES contact (ID);

INSERT INTO role (ID, NAME) VALUES (1, 'ADMIN');
INSERT INTO role (ID, NAME) VALUES (2, 'DELIVERY_MANAGER');
INSERT INTO role (ID, NAME) VALUES (3, 'ORDERS_MANAGER');
INSERT INTO role (ID, NAME) VALUES (4, 'ORDERS_PROCESSOR');
INSERT INTO role (ID, NAME) VALUES (5, 'SUPERVISOR');
INSERT INTO state (ID, DESCRIPTION) VALUES (1, 'ACCEPTED');
INSERT INTO state (ID, DESCRIPTION) VALUES (2, 'CANCELED');
INSERT INTO state (ID, DESCRIPTION) VALUES (3, 'CLOSED');
INSERT INTO state (ID, DESCRIPTION) VALUES (4, 'DELIVERY');
INSERT INTO state (ID, DESCRIPTION) VALUES (5, 'IMPOSSIBLE');
INSERT INTO state (ID, DESCRIPTION) VALUES (6, 'NEW');
INSERT INTO state (ID, DESCRIPTION) VALUES (7, 'PROCESSING');
INSERT INTO state (ID, DESCRIPTION) VALUES (8, 'READY');
INSERT INTO address (ID, TOWN, STREET, HOUSE, FLAT, COUNTRY) VALUES (1, 'Minsk', 'Kirova', '10', '11', 'Belarus');
INSERT INTO address (ID, TOWN, STREET, HOUSE, FLAT, COUNTRY) VALUES (2, 'Moscow', 'Lermontova', '1', '2', 'Russia');
INSERT INTO address (ID, TOWN, STREET, HOUSE, FLAT, COUNTRY) VALUES (3, 'London', 'Orange', '12', '34', 'GB');
INSERT INTO address (ID, TOWN, STREET, HOUSE, FLAT, COUNTRY) VALUES (4, 'Minsk', 'Pulikhova', '90', '19', 'Belarus');
INSERT INTO address (ID, TOWN, STREET, HOUSE, FLAT, COUNTRY) VALUES (5, 'Moscow', 'Pushkina', '10', '20', 'Russia');
INSERT INTO address (ID, TOWN, STREET, HOUSE, FLAT, COUNTRY) VALUES (6, 'London', 'Green', '11', '33', 'GB');
INSERT INTO address (ID, TOWN, STREET, HOUSE, FLAT, COUNTRY)
VALUES (7, 'Minsk', 'Independence', '11', '111', 'Belarus');
INSERT INTO address (ID, TOWN, STREET, HOUSE, FLAT, COUNTRY) VALUES (8, 'Moscow', 'Lermontova', '6', '2', 'Russia');
INSERT INTO address (ID, TOWN, STREET, HOUSE, FLAT, COUNTRY) VALUES (9, 'London', 'Queen', '12', '34', 'GB');
INSERT INTO address (ID, TOWN, STREET, HOUSE, FLAT, COUNTRY) VALUES (10, 'Minsk', 'Kirova', '80', '119', 'Belarus');
INSERT INTO address (ID, TOWN, STREET, HOUSE, FLAT, COUNTRY) VALUES (11, 'Moscow', 'Lermontova', '10', '20', 'Russia');
INSERT INTO address (ID, TOWN, STREET, HOUSE, FLAT, COUNTRY) VALUES (12, 'London', 'Charles', '12', '34', 'GB');
INSERT INTO address (ID, TOWN, STREET, HOUSE, FLAT, COUNTRY) VALUES (13, 'London', 'Orange', '112', '314', 'GB');
INSERT INTO company (ID, NAME, WEBSITE) VALUES (1, 'FandJ', 'www.FandJ.com');
INSERT INTO company (ID, NAME, WEBSITE) VALUES (2, 'FlowersForYou', 'www.FlowersForYou.com');
INSERT INTO company (ID, NAME, WEBSITE) VALUES (3, 'GoodPresents', 'www.GoodPresents.com');
INSERT INTO flower (ID, NAME) VALUES (1, 'Red Rose');
INSERT INTO flower (ID, NAME) VALUES (2, 'White Rose');
INSERT INTO flower (ID, NAME) VALUES (3, 'Yellow Tulip');
INSERT INTO flower (ID, NAME) VALUES (4, 'Camomile');
INSERT INTO flower (ID, NAME) VALUES (5, 'Iris');
INSERT INTO flower (ID, NAME) VALUES (6, 'Violet');
INSERT INTO goods (ID, COST, FLOWER_ID, COMPANY_ID, REMAIN, IMAGE_NAME) VALUES (1, 12.12, 1, 1, 11, NULL);
INSERT INTO goods (ID, COST, FLOWER_ID, COMPANY_ID, REMAIN, IMAGE_NAME) VALUES (2, 132.12, 2, 1, 110, NULL);
INSERT INTO goods (ID, COST, FLOWER_ID, COMPANY_ID, REMAIN, IMAGE_NAME) VALUES (3, 12.12, 3, 2, 150, NULL);
INSERT INTO goods (ID, COST, FLOWER_ID, COMPANY_ID, REMAIN, IMAGE_NAME) VALUES (4, 12.2, 1, 2, 15, 'bush-rose.jpg');
INSERT INTO goods (ID, COST, FLOWER_ID, COMPANY_ID, REMAIN, IMAGE_NAME) VALUES (5, 19, 4, 1, 15, 'flower-iris.jpg');
INSERT INTO goods (ID, COST, FLOWER_ID, COMPANY_ID, REMAIN, IMAGE_NAME) VALUES (6, 30, 5, 2, 150, 'orchid_rose.jpg');
INSERT INTO goods (ID, COST, FLOWER_ID, COMPANY_ID, REMAIN, IMAGE_NAME) VALUES (7, 3, 6, 1, 130, 'violet_pion.jpg');
INSERT INTO contact (ID, NAME, SURNAME, FATHERNAME, BIRTHDAY, EMAIL, ADDRESS_ID, COMPANY_ID)
VALUES (1, 'Anton', 'Antonov', 'Antonovich', '1990-12-03', 'anton@mail.com', 1, 1);
INSERT INTO contact (ID, NAME, SURNAME, FATHERNAME, BIRTHDAY, EMAIL, ADDRESS_ID, COMPANY_ID)
VALUES (2, 'Ivan', 'Ivanov', 'Ivanovich', '1990-10-04', 'ivanov@mail.com', 2, 1);
INSERT INTO contact (ID, NAME, SURNAME, FATHERNAME, BIRTHDAY, EMAIL, ADDRESS_ID, COMPANY_ID)
VALUES (3, 'Sergey', 'Sergeew', 'Sergeewitch', NULL, NULL, 3, 1);
INSERT INTO contact (ID, NAME, SURNAME, FATHERNAME, BIRTHDAY, EMAIL, ADDRESS_ID, COMPANY_ID)
VALUES (4, 'Helen', 'Ivanova', 'Ivanovna', '1990-12-03', NULL, 4, 2);
INSERT INTO contact (ID, NAME, SURNAME, FATHERNAME, BIRTHDAY, EMAIL, ADDRESS_ID, COMPANY_ID)
VALUES (5, 'John', 'Brown', NULL, '1990-12-03', 'john@mail.com', 5, 3);
INSERT INTO contact (ID, NAME, SURNAME, FATHERNAME, BIRTHDAY, EMAIL, ADDRESS_ID, COMPANY_ID)
VALUES (6, 'Chuck', 'Brown', NULL, '1990-12-03', 'chuck@mail.com', 6, 1);
INSERT INTO contact (ID, NAME, SURNAME, FATHERNAME, BIRTHDAY, EMAIL, ADDRESS_ID, COMPANY_ID)
VALUES (7, 'Alex', 'Sidorov', 'Alexandrovich', '1990-12-03', 'alex@mail.com', 7, 2);
INSERT INTO contact (ID, NAME, SURNAME, FATHERNAME, BIRTHDAY, EMAIL, ADDRESS_ID, COMPANY_ID)
VALUES (8, 'Boris', 'Borisov', 'Borisovich', NULL, NULL, 8, 3);
INSERT INTO contact (ID, NAME, SURNAME, FATHERNAME, BIRTHDAY, EMAIL, ADDRESS_ID, COMPANY_ID)
VALUES (9, 'Jack', 'Black', NULL, '1989-01-03', 'black@mail.com', 9, 1);
INSERT INTO contact (ID, NAME, SURNAME, FATHERNAME, BIRTHDAY, EMAIL, ADDRESS_ID, COMPANY_ID)
VALUES (10, 'Sarah', 'Brown', NULL, '1990-12-03', 'sarahn@mail.com', 10, 2);
INSERT INTO contact (ID, NAME, SURNAME, FATHERNAME, BIRTHDAY, EMAIL, ADDRESS_ID, COMPANY_ID)
VALUES (11, 'Olga', 'Sergeeva', 'Sergeevna', '1980-10-06', 'sergeeva80@mail.com', 11, 3);
INSERT INTO contact (ID, NAME, SURNAME, FATHERNAME, BIRTHDAY, EMAIL, ADDRESS_ID, COMPANY_ID)
VALUES (12, 'Olga', 'Antonova', 'Antonovna', '1992-11-04', 'olga@mail.com', 1, 1);
INSERT INTO "USER" (ID, LOGIN, PASSWORD, CONTACT_ID, ROLE_ID) VALUES (1, 'test', '$2a$10$ZWwh6S.iW5Sjeo2mklifkegHdSDOpmxpAw5oHDRTEMWgHLS.bILny', 1, 1);
INSERT INTO "USER" (ID, LOGIN, PASSWORD, CONTACT_ID, ROLE_ID) VALUES (2, 'sergeM', '$2a$10$iiehz6SIz1WJtVXmFSE.2ex4iMCJgBNd30nls2iElwJy142Rn4hG.', 2, 3);
INSERT INTO "USER" (ID, LOGIN, PASSWORD, CONTACT_ID, ROLE_ID) VALUES (3, 'testAdmin', '$2a$10$ZBk/ChKIjGnTSm9/OCJmCOwDmLokMHJU4Ac3x1A9obzNr2jusDJOi', 4, 1);
INSERT INTO "USER" (ID, LOGIN, PASSWORD, CONTACT_ID, ROLE_ID) VALUES (4, 'delivery_manager', '$2a$10$Okl4EXDsJLi8grBfGVr0aeHz8jsO3VnQM.MJVr/2PMROPZOaTBnQG', 5, 2);
INSERT INTO "USER" (ID, LOGIN, PASSWORD, CONTACT_ID, ROLE_ID) VALUES (5, 'orders_manager', '$2a$10$eQV7Z0TJFtNWR0NKjH7BIO5vIZll.UXZYeEMrkZHkUcVQzZaA7qJu', 6, 3);
INSERT INTO "USER" (ID, LOGIN, PASSWORD, CONTACT_ID, ROLE_ID) VALUES (6, 'orders_processor', '$2a$10$oD5eWiJRPeLAIaWxY6Caj.PK6tiaadUlCnuJdTxnqJl77VgHkgI6i', 7, 4);
INSERT INTO "USER" (ID, LOGIN, PASSWORD, CONTACT_ID, ROLE_ID) VALUES (7, 'supervisor', '$2a$10$Ykn5DOMP6bnAxuOEUk3cp.lU5hog0jQWGmHjEKnzu4G6cuhPmhuMy', 8, 5);
INSERT INTO purchase (ID, CUSTOMER_ID, DESCRIPTION, MANAGER_ID, COST, STAFF_ID, DELIVERY_MANAGER_ID, RECEIVER_ID, STATE_ID, DELIVERY_DATE, ADDRESS_ID)
VALUES (1, 1, '15 Red roses bouquet', 5, 12, 6, 4, 1, 1, '2015-05-01', NULL);
INSERT INTO purchase (ID, CUSTOMER_ID, DESCRIPTION, MANAGER_ID, COST, STAFF_ID, DELIVERY_MANAGER_ID, RECEIVER_ID, STATE_ID, DELIVERY_DATE, ADDRESS_ID)
VALUES (2, 3, '5 yellow tulips', 5, 19.1, 6, 4, 4, 2, '2015-05-02', NULL);
INSERT INTO purchase (ID, CUSTOMER_ID, DESCRIPTION, MANAGER_ID, COST, STAFF_ID, DELIVERY_MANAGER_ID, RECEIVER_ID, STATE_ID, DELIVERY_DATE, ADDRESS_ID)
VALUES (3, 5, 'orchids bouquet', 5, 50, 6, 4, 6, 3, '2015-08-08', NULL);
INSERT INTO purchase (ID, CUSTOMER_ID, DESCRIPTION, MANAGER_ID, COST, STAFF_ID, DELIVERY_MANAGER_ID, RECEIVER_ID, STATE_ID, DELIVERY_DATE, ADDRESS_ID)
VALUES (4, 7, '1 red rose', 5, 5, 6, 4, 8, 4, '2015-05-01', NULL);
INSERT INTO purchase (ID, CUSTOMER_ID, DESCRIPTION, MANAGER_ID, COST, STAFF_ID, DELIVERY_MANAGER_ID, RECEIVER_ID, STATE_ID, DELIVERY_DATE, ADDRESS_ID)
VALUES (5, 9, 'big pink bouquet', 5, 50, 6, 4, 10, 5, '2015-10-10', NULL);
INSERT INTO purchase (ID, CUSTOMER_ID, DESCRIPTION, MANAGER_ID, COST, STAFF_ID, DELIVERY_MANAGER_ID, RECEIVER_ID, STATE_ID, DELIVERY_DATE, ADDRESS_ID)
VALUES (6, 11, 'multicolor tulips bouquet', 5, 30, 6, 4, 12, 6, '2015-03-02', NULL);
INSERT INTO purchase (ID, CUSTOMER_ID, DESCRIPTION, MANAGER_ID, COST, STAFF_ID, DELIVERY_MANAGER_ID, RECEIVER_ID, STATE_ID, DELIVERY_DATE, ADDRESS_ID)
VALUES (7, 1, '9 tea roses', 5, 32, 6, 4, 2, 7, '2015-05-01', NULL);
INSERT INTO purchase (ID, CUSTOMER_ID, DESCRIPTION, MANAGER_ID, COST, STAFF_ID, DELIVERY_MANAGER_ID, RECEIVER_ID, STATE_ID, DELIVERY_DATE, ADDRESS_ID)
VALUES (8, 3, '1 white and 6 red roses', 5, 7, 6, 4, 4, 8, '2015-05-02', NULL);
INSERT INTO item (ID, GOODS_ID, ORDER_ID, QUANTITY) VALUES (1, 1, 1, 1);
INSERT INTO item (ID, GOODS_ID, ORDER_ID, QUANTITY) VALUES (2, 6, 8, 6);
INSERT INTO item (ID, GOODS_ID, ORDER_ID, QUANTITY) VALUES (3, 3, 7, 9);
INSERT INTO item (ID, GOODS_ID, ORDER_ID, QUANTITY) VALUES (4, 5, 6, 2);
INSERT INTO item (ID, GOODS_ID, ORDER_ID, QUANTITY) VALUES (5, 2, 6, 3);
INSERT INTO item (ID, GOODS_ID, ORDER_ID, QUANTITY) VALUES (6, 6, 5, 1);
INSERT INTO item (ID, GOODS_ID, ORDER_ID, QUANTITY) VALUES (7, 1, 4, 1);
INSERT INTO item (ID, GOODS_ID, ORDER_ID, QUANTITY) VALUES (8, 4, 3, 7);
INSERT INTO item (ID, GOODS_ID, ORDER_ID, QUANTITY) VALUES (9, 3, 2, 5);
INSERT INTO item (ID, GOODS_ID, ORDER_ID, QUANTITY) VALUES (10, 4, 1, 15);
INSERT INTO phone (ID, COUNTRY, OPERATOR, NUMBER, TYPE, COMMENT, CONTACT_ID)
VALUES (1, '12', '34', '56789', 'CELL', 'Comment1', 1);
INSERT INTO phone (ID, COUNTRY, OPERATOR, NUMBER, TYPE, COMMENT, CONTACT_ID)
VALUES (2, '67', '89', '01234', 'HOME', 'Comment2', 1);
INSERT INTO phone (ID, COUNTRY, OPERATOR, NUMBER, TYPE, COMMENT, CONTACT_ID)
VALUES (3, '56', '78', '90123', 'CELL', 'Comment3', 2);
INSERT INTO phone (ID, COUNTRY, OPERATOR, NUMBER, TYPE, COMMENT, CONTACT_ID)
VALUES (4, '34', '56', '78901', 'HOME', 'Comment4', 2);
INSERT INTO phone (ID, COUNTRY, OPERATOR, NUMBER, TYPE, COMMENT, CONTACT_ID)
VALUES (5, '56', '78', '90123', 'CELL', 'Comment5', 1);
INSERT INTO phone (ID, COUNTRY, OPERATOR, NUMBER, TYPE, COMMENT, CONTACT_ID)
VALUES (6, '34', '56', '78901', 'HOME', 'Comment6', 2);
