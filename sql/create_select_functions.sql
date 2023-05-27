--////////////////////////////////////////////////////////////////////////////////////////////////
--
-- заказы
--
--////////////////////////////////////////////////////////////////////////////////////////////////

create or replace function get_orders()
    returns setof "Orders"
    language plpgsql
as
$$
begin
    return query select "Id", "EmployeeId", "TableId", "Sum", "Date"
                 from "Orders";
end;
$$;

create or replace function get_by_id_orders(id bigint)
    returns setof "Orders"
    language plpgsql
as
$$
begin
    return query select "Id", "EmployeeId", "TableId", "Sum", "Date"
                 from "Orders"
                 where "Id" = id;
end;
$$;

--////////////////////////////////////////////////////////////////////////////////////////////////
--
-- Элементы заказа
--
--////////////////////////////////////////////////////////////////////////////////////////////////

create or replace function get_elem_order(orderId bigint)
    returns setof "ElementsOfOrder"
    language plpgsql
as
$$
begin
    return query select "Id", "OrderId", "ItemId", "priceOfItemId", "Sum", "Amount"
                 from "ElementsOfOrder"
                 where "OrderId" = orderId;
end;
$$;

create or replace function get_by_id_elem_order(orderId bigint, id bigint)
    returns setof "ElementsOfOrder"
    language plpgsql
as
$$
begin
    return query select "Id", "OrderId", "ItemId", "priceOfItemId", "Sum", "Amount"
                 from "ElementsOfOrder"
                 where "OrderId" = orderId
                   and "Id" = id;
end;
$$;

--////////////////////////////////////////////////////////////////////////////////////////////////
--
-- Цены на товар
--
--////////////////////////////////////////////////////////////////////////////////////////////////

create or replace function get_price_of_item(itemId bigint)
    returns setof "PricesOfItem"
    language plpgsql
as
$$
begin
    return query select "Id", "ItemId", "Price", "DateOfChange"
                 from "PricesOfItem"
                 where "ItemId" = itemId;
end;
$$;

create or replace function get_by_id_price_of_item(id bigint)
    returns setof "PricesOfItem"
    language plpgsql
as
$$
begin
    return query select "Id", "ItemId", "Price", "DateOfChange"
                 from "PricesOfItem"
                 where "Id" = id;
end;
$$;

create or replace function get_price_of_item_ordered(itemId bigint)
    returns setof "PricesOfItem"
    language plpgsql
as
$$
begin
    return query select "Id", "ItemId", "Price", "DateOfChange"
                 from "PricesOfItem"
                 where "ItemId" = itemId
                 order by "DateOfChange" desc
                 limit 1;
end;
$$;

--////////////////////////////////////////////////////////////////////////////////////////////////
--
-- Сотрудники
--
--////////////////////////////////////////////////////////////////////////////////////////////////

create or replace function get_employees()
    returns setof "Employees"
    language plpgsql
as
$$
begin
    return query select "Id", "Name", "PhoneNumber"
                 from "Employees";
end;
$$;

create or replace function get_by_id_employees(id bigint)
    returns setof "Employees"
    language plpgsql
as
$$
begin
    return query select "Id", "Name", "PhoneNumber"
                 from "Employees"
                 where "Id" = id;
end;
$$;

--////////////////////////////////////////////////////////////////////////////////////////////////
--
-- Столы
--
--////////////////////////////////////////////////////////////////////////////////////////////////

create or replace function get_tables()
    returns setof "Tables"
    language plpgsql
as
$$
begin
    return query select "Id", "TableNumber"
                 from "Tables";
end;
$$;

create or replace function get_by_id_tables(id bigint)
    returns setof "Tables"
    language plpgsql
as
$$
begin
    return query select "Id", "TableNumber"
                 from "Tables"
                 where "Id" = id;
end;
$$;


--////////////////////////////////////////////////////////////////////////////////////////////////
--
-- Меры измерения
--
--////////////////////////////////////////////////////////////////////////////////////////////////

create or replace function get_measures()
    returns setof "Measurements"
    language plpgsql
as
$$
begin
    return query select "Id", "Name"
                 from "Measurements";
end;
$$;

create or replace function get_by_id_measures(id bigint)
    returns setof "Measurements"
    language plpgsql
as
$$
begin
    return query select "Id", "Name"
                 from "Measurements"
                 where "Id" = id;
end;
$$;

--////////////////////////////////////////////////////////////////////////////////////////////////
--
-- Категории
--
--////////////////////////////////////////////////////////////////////////////////////////////////

create or replace function get_categories()
    returns setof "Categories"
    language plpgsql
as
$$
begin
    return query select "Id", "Name"
                 from "Categories";
end;
$$;

create or replace function get_by_id_categories(id bigint)
    returns setof "Categories"
    language plpgsql
as
$$
begin
    return query select "Id", "Name"
                 from "Categories"
                 where "Id" = id;
end;
$$;


--////////////////////////////////////////////////////////////////////////////////////////////////
--
-- Товары
--
--////////////////////////////////////////////////////////////////////////////////////////////////

create or replace function get_items()
    returns setof "Items"
    language plpgsql
as
$$
begin
    return query select "Id", "CategoryId", "Name", "Description", "IsNotForSale"
                 from "Items";
end;
$$;

create or replace function get_by_id_items(id bigint)
    returns setof "Items"
    language plpgsql
as
$$
begin
    return query select "Id", "CategoryId", "Name", "Description", "IsNotForSale"
                 from "Items"
                 where "Id" = id;
end;
$$;


--////////////////////////////////////////////////////////////////////////////////////////////////
--
-- Меры измерения на товаре
--
--////////////////////////////////////////////////////////////////////////////////////////////////

create or replace function get_measures_of_item(itemId bigint)
    returns setof "MeasuresOfItem"
    language plpgsql
as
$$
begin
    return query select "Id", "MeasurementId", "ItemId", "Amount"
                 from "MeasuresOfItem"
                 where "ItemId" = itemId;
end;
$$;

create or replace function get_by_id_measures_of_item(id bigint)
    returns setof "MeasuresOfItem"
    language plpgsql
as
$$
begin
    return query select "Id", "MeasurementId", "ItemId", "Amount"
                 from "MeasuresOfItem"
                 where "Id" = id;
end;
$$;

--////////////////////////////////////////////////////////////////////////////////////////////////
--
-- Бронирование столов
--
--////////////////////////////////////////////////////////////////////////////////////////////////

create or replace function get_bookings()
    returns setof "Bookings"
    language plpgsql
as
$$
begin
    return query select "Id", "TableId", "ContactData", "Start", "End", "IsCanceled"
                 from "Bookings";
end;
$$;

create or replace function get_by_id_bookings(id bigint)
    returns setof "Bookings"
    language plpgsql
as
$$
begin
    return query select "Id", "TableId", "ContactData", "Start", "End", "IsCanceled"
                 from "Bookings"
                 where "Id" = id;
end;
$$;
