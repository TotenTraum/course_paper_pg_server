--////////////////////////////////////////////////////////////////////////////////////////////////
--
-- Логи
--
--////////////////////////////////////////////////////////////////////////////////////////////////

create or replace procedure add_log(source varchar(64), created date, role_creator name)
    language plpgsql
as
$$
begin
    insert into "Logs"("Source", "Created", "RoleCreated") values ($1, $2, $3);
end;
$$;

create or replace function get_logs()
    returns setof "Logs"
    language plpgsql
as
$$
begin
    perform add_log('get_logs', now(), current_user);
    return query select l."Id", l."Source", l."RoleCreated", l."Created"
                 from "Logs" l;
end;
$$;

--////////////////////////////////////////////////////////////////////////////////////////////////
--
-- заказы
--
--////////////////////////////////////////////////////////////////////////////////////////////////

-- Добавить заказ
create or replace function add_order(tableId bigint, employeeId bigint, sum decimal, date timestamp)
    returns bigint
    language plpgsql
as
$$
declare
    newId bigint;
begin
    perform add_log('add_order', now(), current_user);
    insert into "Orders"("TableId", "EmployeeId", "Sum", "Date") values ($1, $2, $3, $4) returning "Id" into newId;
    return newId;
end;
$$;

-- Удалить заказ
create or replace procedure delete_order(orderId bigint)
    language plpgsql
as
$$
begin
    perform add_log('delete_order', now(), current_user);
    delete from "Orders" where "Orders"."Id" = $1;
end;
$$;

-- Обновить заказ
create or replace procedure update_order(id bigint, tableId bigint, employeeId bigint, sum decimal, date timestamp)
    language plpgsql
as
$$
begin
    perform add_log('update_order', now(), current_user);
    update "Orders"
    set "TableId"    = tableId,
        "EmployeeId" = employeeId,
        "Date"       = date,
        "Sum"        = sum
    where "Id" = id;
end;
$$;

--////////////////////////////////////////////////////////////////////////////////////////////////
--
-- Элементы заказа
--
--////////////////////////////////////////////////////////////////////////////////////////////////


-- Добавить элемент заказа
create or replace function add_elem_order(orderId bigint, itemId bigint, amount int)
    returns bigint
    language plpgsql
as
$$
declare
    priceId bigint;
    newId   bigint;
    price   decimal;
    sum     decimal;
begin
    select "Id", "Price"
    into priceId, price
    from "PricesOfItem"
    where "ItemId" = itemId
    order by "DateOfChange";

    sum := price + amount::decimal;

    insert into "ElementsOfOrder"("OrderId", "ItemId", "priceOfItemId", "Sum", "Amount")
    values (orderId, itemId, priceId, sum, amount)
    returning "Id" into newId;

    update "Orders"
    set "Sum" = "Sum" + sum
    where "Id" = orderId;

    perform add_log('add_elem_order', now(), current_user);

    return newId;
end;
$$;

-- Удалить элемент заказа
create or replace procedure delete_elem_order(id bigint, orderId bigint)
    language plpgsql
as
$$
declare
    oldSum decimal;
begin
    select "Sum" into oldSum from "ElementsOfOrder";

    delete from "ElementsOfOrder" where "ElementsOfOrder"."Id" = id;

    update "Orders"
    set "Sum" = "Sum" - oldSum
    where "Id" = orderId;

    perform add_log('delete_elem_order', now(), current_user);
end;
$$;

-- Обновить элемент заказа
create or replace procedure update_elem_order(id bigint, orderId bigint, itemId bigint, amount int)
    language plpgsql
as
$$
declare
    priceId bigint;
    price   decimal;
    diff    decimal;
    sum     decimal;
    oldSum  decimal;
begin
    select "Id", "Price"
    into priceId, price
    from "PricesOfItem"
    where "ItemId" = itemId
    order by "DateOfChange";

    select "Sum" into oldSum from "ElementsOfOrder" where "Id" = id;

    sum := price + amount::decimal;
    diff := sum - oldSum;

    update "ElementsOfOrder"
    set "OrderId"       = orderId,
        "ItemId"        = itemId,
        "Amount"        = amount,
        "priceOfItemId" = priceId,
        "Sum"           = sum
    where "Id" = id;

    update "Orders"
    set "Sum" = "Sum" + diff
    where "Id" = orderId;

    perform add_log('update_elem_order', now(), current_user);
end;
$$;

--////////////////////////////////////////////////////////////////////////////////////////////////
--
-- Цены на товар
--
--////////////////////////////////////////////////////////////////////////////////////////////////

-- Смена цены на товар
create or replace function add_price_of_item(itemId bigint, price decimal)
    returns bigint
    language plpgsql
as
$$
declare
    newId bigint;
begin
    insert into "PricesOfItem"("ItemId", "Price")
    values ($1, $2)
    returning "Id"
        into newId;
    perform add_log('add_price_of_item', now(), current_user);
    return newId;
end;
$$;

--////////////////////////////////////////////////////////////////////////////////////////////////
--
-- Сотрудники
--
--////////////////////////////////////////////////////////////////////////////////////////////////

-- Добавить сотрудника
create or replace function add_employee(name varchar(128), phoneNumber varchar(32))
    returns bigint
    language plpgsql
as
$$
declare
    newId bigint;
begin
    insert into "Employees"("Name", "PhoneNumber") values ($1, $2) returning "Id" into newId;
    perform add_log('add_employee', now(), current_user);
    return newId;
end;
$$;

-- Удалить сотрудника
create or replace procedure delete_employee(id bigint)
    language plpgsql
as
$$
begin
    delete from "Employees" where "Employees"."Id" = $1;
    perform add_log('delete_order', now(), current_user);
end;
$$;

-- Обновить сотрудника
create or replace procedure update_employee(id bigint, name varchar(128), phoneNumber varchar(32))
    language plpgsql
as
$$
begin
    update "Employees"
    set "Name"        = name,
        "PhoneNumber" = phoneNumber
    where "Id" = id;
    perform add_log('update_employee', now(), current_user);
end;
$$;

--////////////////////////////////////////////////////////////////////////////////////////////////
--
-- Столы
--
--////////////////////////////////////////////////////////////////////////////////////////////////

-- Добавить стол
create or replace function add_table(tableNumber int)
    returns bigint
    language plpgsql
as
$$
declare
    newId bigint;
begin
    insert into "Tables"("TableNumber") values ($1) returning "Id" into newId;
    perform add_log('add_table', now(), current_user);
    return newId;
end;
$$;

-- Удалить стол
create or replace procedure delete_table(id bigint)
    language plpgsql
as
$$
begin
    delete from "Tables" where "Tables"."Id" = $1;
    perform add_log('delete_table', now(), current_user);
end;
$$;

-- Обновить стол
create or replace procedure update_table(id bigint, tableNumber int)
    language plpgsql
as
$$
begin
    update "Tables"
    set "TableNumber" = tableNumber
    where "Id" = id;
    perform add_log('update_table', now(), current_user);
end;
$$;

--////////////////////////////////////////////////////////////////////////////////////////////////
--
-- Меры измерения
--
--////////////////////////////////////////////////////////////////////////////////////////////////

-- Добавить меру измерения
create or replace function add_measurement(name varchar(64))
    returns bigint
    language plpgsql
as
$$
declare
    newId bigint;
begin
    insert into "Measurements"("Name") values ($1) returning "Id" into newId;
    perform add_log('add_measurement', now(), current_user);
    return newId;
end;
$$;

-- Удалить меру измерения
create or replace procedure delete_measurement(id bigint)
    language plpgsql
as
$$
begin
    delete from "Measurements" where "Measurements"."Id" = $1;
    perform add_log('delete_measurement', now(), current_user);
end;
$$;

-- Обновить меру измерения
create or replace procedure update_measurement(id bigint, name varchar(64))
    language plpgsql
as
$$
begin
    update "Measurements"
    set "Name" = name
    where "Id" = id;
    perform add_log('update_measurement', now(), current_user);
end;
$$;

--////////////////////////////////////////////////////////////////////////////////////////////////
--
-- Категории
--
--////////////////////////////////////////////////////////////////////////////////////////////////

-- Добавить категорию
create or replace function add_category(name varchar(64))
    returns bigint
    language plpgsql
as
$$
declare
    newId bigint;
begin
    insert into "Categories"("Name") values ($1) returning "Id" into newId;
    perform add_log('add_category', now(), current_user);
    return newId;
end;
$$;

-- Удалить категорию
create or replace procedure delete_category(id bigint)
    language plpgsql
as
$$
begin
    delete from "Categories" where "Categories"."Id" = $1;
    perform add_log('delete_category', now(), current_user);
end;
$$;

-- Обновить категорию
create or replace procedure update_category(id bigint, name varchar(64))
    language plpgsql
as
$$
begin
    update "Categories"
    set "Name" = name
    where "Id" = id;
    perform add_log('update_category', now(), current_user);
end;
$$;

--////////////////////////////////////////////////////////////////////////////////////////////////
--
-- Товары
--
--////////////////////////////////////////////////////////////////////////////////////////////////

-- Добавить товар
create or replace function add_item(categoryId bigint, name varchar(64), description varchar(256), isNotForSale boolean)
    returns bigint
    language plpgsql
as
$$
declare
    newId bigint;
begin
    insert into "Items"("CategoryId", "Name", "Description", "IsNotForSale")
    values ($1, $2, $3, $4)
    returning "Id" into newId;
    perform add_log('add_item', now(), current_user);
    return newId;
end;
$$;

-- Удалить товар
create or replace procedure delete_item(id bigint)
    language plpgsql
as
$$
begin
    delete from "Items" where "Items"."Id" = $1;
    perform add_log('delete_item', now(), current_user);
end;
$$;

-- Обновить товар
create or replace procedure update_item(id bigint, categoryId bigint, name varchar(64), description varchar(256),
                                        isNotForSale boolean)
    language plpgsql
as
$$
begin
    update "Items"
    set "Name"         = name,
        "CategoryId"   = categoryId,
        "Description"  = description,
        "IsNotForSale" = isNotForSale
    where "Id" = id;
    perform add_log('update_item', now(), current_user);
end;
$$;

--////////////////////////////////////////////////////////////////////////////////////////////////
--
-- Меры измерения на товаре
--
--////////////////////////////////////////////////////////////////////////////////////////////////

-- Добавить меру измерения на товар
create or replace function add_measure_of_item(itemId bigint, measurementId bigint, amount decimal)
    returns bigint
    language plpgsql
as
$$
declare
    newId bigint;
begin
    insert into "measuresOfItem"("ItemId", "MeasurementId", "Amount")
    values ($1, $2, $3)
    returning "Id" into newId;
    perform add_log('add_measure_of_item', now(), current_user);
    return newId;
end;
$$;

-- Удалить меру измерения с товара
create or replace procedure delete_measure_of_item(orderId bigint)
    language plpgsql
as
$$
begin
    delete from "measuresOfItem" where "measuresOfItem"."Id" = $1;
    perform add_log('delete_measure_of_item', now(), current_user);
end;
$$;

-- Обновить меру измерения на товаре
create or replace procedure update_measure_of_item(id bigint, itemId bigint, measurementId bigint, amount decimal)
    language plpgsql
as
$$
begin
    update "measuresOfItem"
    set "MeasurementId" = measurementId,
        "ItemId"        = itemId,
        "Amount"        = amount
    where "Id" = id;
    perform add_log('update_measure_of_item', now(), current_user);
end;
$$;

--////////////////////////////////////////////////////////////////////////////////////////////////
--
-- Бронирование столов
--
--////////////////////////////////////////////////////////////////////////////////////////////////

-- Добавить бронь
create or replace function add_booking(tableId bigint, contactData jsonb, start timestamp)
    returns bigint
    language plpgsql
as
$$
declare
    newId bigint;
begin
    insert into "Bookings"("TableId", "ContactData", "Start", "End")
    values (tableId, contactData, start, start + interval '30 minutes')
    returning "Id" into newId;
    perform add_log('add_booking', now(), current_user);
    return newId;
end;
$$;

-- Удалить бронь
create or replace procedure delete_booking(id bigint)
    language plpgsql
as
$$
begin
    update "Bookings"
        set "IsCanceled" = true
    where "Id" = id;
    perform add_log('delete_booking', now(), current_user);
end;
$$;