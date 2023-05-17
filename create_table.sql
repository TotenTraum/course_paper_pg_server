create table "Logs"
(
    "Id"          bigserial primary key,
    "Source"      varchar(64) not null,
    "Created"     timestamp   not null,
    "RoleCreated" name        not null
);

create or replace procedure "AddLog"(source varchar(64), created date, role_creator name)
    language plpgsql
as
$$
begin
    insert into "Logs"("Source", "Created", "RoleCreated") values ($1, $2, $3);
end
$$;

-- Таблица столов кафе
create table "Tables"
(
    "Id"          bigserial primary key,
--     Номер стола
    "TableNumber" int not null
);
-- Таблица бронирований столов
create table "Bookings"
(
    "Id"          bigserial primary key,
    "TableId"     bigint references "Tables" ("Id"),
--     Контактные данные
    "ContactData" jsonb     not null,
--     Со скольки стол забронирован
    "Start"       timestamp not null,
--     До скольки стол забронирован
    "End"         timestamp not null
);

-- Сотрудники
create table "Employees"
(
    "Id"          bigserial primary key,
--     ФИО Сотрудника
    "Name"        varchar(128) not null,
--     Номер телефона
    "PhoneNumber" varchar(32)  not null
);

-- Заказы
create table "Orders"
(
    "Id"         bigserial primary key,
    "TableId"    bigint    not null references "Tables" ("Id"),
    "EmployeeId" bigint    not null references "Employees" ("Id"),
--     Дата совершения заказа
    "Date"       timestamp not null,
--     Общая сумма всех товаров
    "Sum"        decimal   not null default 0
);

-- Виды измерения
create table "Measurements"
(
    "Id"   bigserial primary key,
--     Название измерения
    "Name" varchar(64) not null
);

-- Категории товаров
create table "Categories"
(
    "Id"   bigserial primary key,
    "Name" varchar(64) not null
);

-- Товары
create table "Items"
(
    "Id"           bigserial primary key,
    "categoryId"   bigint       not null references "Categories" ("Id"),
--     Название товара
    "Name"         varchar(64)  not null,
--     Описание товара
    "Description"  varchar(256) not null,
--     Флаг, который указывает на то, что поступил в продажу товар или нет
    "IsNotForSale" boolean      not null default false
);

-- Таблица мер товара в разных измерениях
create table "measuresOfItem"
(
    "Id"            bigserial primary key,
    "itemId"        bigint  not null references "Items" ("Id"),
--     В чем измеряется
    "MeasurementId" bigint  not null references "Measurements" ("Id"),
--     Количество товара в данном измерении
    "Amount"        decimal not null
);

-- Цены на товар
create table "PricesOfItem"
(
    "Id"           bigserial primary key,
    "itemId"       bigint    not null references "Items" ("Id"),
--     Цена товара
    "Price"        decimal   not null,
--     Дата изменения цены на товар
    "DateOfChange" timestamp not null
);

-- Элемент заказа
create table "ElementsOfOrder"
(
    "Id"      bigserial primary key,
    "OrderId" bigint  not null references "Orders" ("Id"),
    "itemId"  bigint  not null references "Items" ("Id"),
--     Общая сумма
    "Sum"     decimal not null default 0,
--     Количество
    "Amount"  int     not null default 0
);