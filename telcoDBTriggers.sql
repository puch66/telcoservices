drop trigger if exists package_insert_totPurchases;
create trigger package_insert_totPurchases
after insert on servicePackage
for each row
insert into totalPurchasesPerPackage values (new.name,0);

drop trigger if exists package_delete_totPurchases;
create trigger package_delete_totPurchases
after delete on servicePackage
for each row
delete from totalPurchasesPerPackage where package = old.name;

drop trigger if exists package_updatepkg_totPurchases;
delimiter $$
create trigger package_updatepkg_totPurchases
after update on servicePackage
for each row
begin
	if new.name <> old.name then
		update totalPurchasesPerPackage set package = new.name where package = old.name;
	end if;
end $$
delimiter ;

drop trigger if exists package_insertorder_totPurchases;
delimiter $$
create trigger package_insertorder_totPurchases
after insert on customOrder
for each row
begin
	if new.isValid = 0 then
		update totalPurchasesPerPackage set total = total + 1 where package = (select name from servicePackage where id = new.servicePackage);
	end if;
end $$
delimiter ;

drop trigger if exists package_updateorder_totPurchases;
delimiter $$
create trigger package_updateorder_totPurchases
after update on customOrder
for each row
begin
	if new.isValid = 0 and new.isValid <> old.isValid then
		update totalPurchasesPerPackage set total = total + 1 where package = (select name from servicePackage where id = new.servicePackage);
	end if;
end $$
delimiter ;

drop trigger if exists package_deleteorder_totPurchases;
delimiter $$
create trigger package_deleteorder_totPurchases
after delete on customOrder
for each row
begin
	if old.isValid = 0 then
		update totalPurchasesPerPackage set total = total - 1 where package = (select name from servicePackage where id = old.servicePackage);
	end if;
end $$
delimiter ;

drop trigger if exists validity_insert_totPurchasesAndVP;
create trigger validity_insert_totPurchasesAndVP
after insert on validityPeriod
for each row
insert into totalPurchasesPerPackageAndValidityPeriod values(default,(select name from servicePackage where id = new.servicePackage),new.duration,0);


drop trigger if exists validity_delete_totPurchasesAndVP;
create trigger validity_delete_totPurchasesAndVP
after delete on validityPeriod
for each row
delete from totalPurchasesPerPackageAndValidityPeriod where package = (select name from servicePackage where id = old.servicePackage) and validity = old.duration;

drop trigger if exists validity_updatepkg_totPurchasesAndVP;
delimiter $$
create trigger validity_updatepkg_totPurchasesAndVP
after update on validityPeriod
for each row
begin
	if new.duration <> old.duration then
		update totalPurchasesPerPackageAndValidityPeriod set validity = new.duration where validity = old.duration and package = (select name from servicePackage where id = old.servicePackage);
	end if;
end $$
delimiter ;

drop trigger if exists package_delete_totPurchasesAndVP;
create trigger package_delete_totPurchasesAndVP
after delete on servicePackage
for each row
delete from totalPurchasesPerPackageAndValidityPeriod where package = old.name;

drop trigger if exists package_updatepkg_totPurchasesAndVP;
delimiter $$
create trigger package_updatepkg_totPurchasesAndVP
after update on servicePackage
for each row
begin
	if new.name <> old.name then
		update totalPurchasesPerPackageAndValidityPeriod set package = new.name where package = old.name;
	end if;
end $$
delimiter ;

drop trigger if exists package_insertorder_totPurchasesAndVP;
delimiter $$
create trigger package_insertorder_totPurchasesAndVP
after insert on customOrder
for each row
begin
	if new.isValid = 0 then
		update totalPurchasesPerPackageAndValidityPeriod set total = total + 1 where package = (select name from servicePackage where id = new.servicePackage)
																			   and validity = (select duration from validityPeriod where id = new.validity);
	end if;
end $$
delimiter ;

drop trigger if exists package_updateorder_totPurchasesAndVP;
delimiter $$
create trigger package_updateorder_totPurchasesAndVP
after update on customOrder
for each row
begin
	if new.isValid = 0 and new.isValid <> old.isValid then
		update totalPurchasesPerPackageAndValidityPeriod set total = total + 1 where package = (select name from servicePackage where id = new.servicePackage)
																			   and validity = (select duration from validityPeriod where id = new.validity);
	end if;
end $$
delimiter ;

drop trigger if exists package_deleteorder_totPurchasesAndVP;
delimiter $$
create trigger package_deleteorder_totPurchasesAndVP
after delete on customOrder
for each row
begin
	if old.isValid = 0 then
		update totalPurchasesPerPackageAndValidityPeriod set total = total - 1 where package = (select name from servicePackage where id = old.servicePackage)
																			   and validity = (select duration from validityPeriod where id = old.validity);
	end if;
end $$
delimiter ;

drop trigger if exists package_insert_salesValue;
create trigger package_insert_salesValue
after insert on servicePackage
for each row
insert into salesValue values (new.name,0,0);

drop trigger if exists package_delete_salesValue;
create trigger package_delete_salesValue
after delete on servicePackage
for each row
delete from salesValue where package = old.name;

drop trigger if exists package_updatepkg_salesValue;
delimiter $$
create trigger package_updatepkg_salesValue
after update on servicePackage
for each row
begin
	if new.name <> old.name then
		update salesValue set package = new.name where package = old.name;
	end if;
end $$
delimiter ;

drop trigger if exists package_insertorder_salesValue;
delimiter $$
create trigger package_insertorder_salesValue
after insert on customOrder
for each row
begin
	if new.isValid = 0 then
		update salesValue set totalSale = totalSale + new.totalValue, 
							  saleWithoutProd = saleWithoutProd + (select fee*duration from validityPeriod v where v.id = new.validity)
		where package = (select name from servicePackage where id = new.servicePackage);
	end if;
end $$
delimiter ;

drop trigger if exists package_updateorder_salesValue;
delimiter $$
create trigger package_updateorder_salesValue
after update on customOrder
for each row
begin
	if new.isValid = 0 and new.isValid <> old.isValid then
		update salesValue set totalSale = totalSale + new.totalValue, 
							  saleWithoutProd = saleWithoutProd + (select fee*duration from validityPeriod v where v.id = new.validity)
		where package = (select name from servicePackage where id = new.servicePackage);
	end if;
end $$
delimiter ;

drop trigger if exists package_deleteorder_salesValue;
delimiter $$
create trigger package_deleteorder_salesValue
after delete on customOrder
for each row
begin
	if old.isValid = 0 then
		update salesValue set totalSale = totalSale - old.totalValue, 
							  saleWithoutProd = saleWithoutProd - (select fee*duration from validityPeriod v where v.id = old.validity)
		where package = (select name from servicePackage where id = old.servicePackage);
	end if;
end $$
delimiter ;

drop trigger if exists product_updateoptproduct_avgProdPackage;
delimiter $$
create trigger product_updateoptproduct_avgProdPackage
after insert on selectedProductsForOrder
for each row #DOVREBBE ESSERE STATEMENT MA NON SUPPPORTATO DA MYSQL
begin
	declare packId int;
	if (select o.isValid from customOrder o where o.id = new.customOrder) = 0 then
		set packId = (select o.servicePackage from customOrder o where o.id = new.customOrder);
		update averageProductsSoldForPackage set averageProducts =
		( select avg(count) from (  select count(sp.product) as count
									from customOrder o left join selectedProductsForOrder sp on o.id = sp.customOrder
									where o.isValid = 0 and o.servicePackage = packId
									group by o.id ) as counts)
		where package = (select name from servicePackage where id = packId);
	end if;
end $$
delimiter ;

drop trigger if exists product_deleteoptproduct_avgProdPackage;
delimiter $$
create trigger product_deleteoptproduct_avgProdPackage
after delete on selectedProductsForOrder
for each row
begin
	declare packId int;
	if (select o.isValid from customOrder o where o.id = old.customOrder) = 0 then
		set packId = (select o.servicePackage from customOrder o where o.id = old.customOrder);
		update averageProductsSoldForPackage set averageProducts =
		( select avg(count) from (  select count(sp.product) as count
									from customOrder o left join selectedProductsForOrder sp on o.id = sp.customOrder
									where o.isValid = 0 and o.servicePackage = packId
									group by o.id ) as counts)
		where package = (select name from servicePackage where id = packId);
	end if;
end $$
delimiter ;

/* NECESSARIO PERCHÉ POTREBBERO ESSERCI ORDINI CON 0 PRODOTTI */
drop trigger if exists order_insertoptproduct_avgProdPackage;
delimiter $$
create trigger order_insertoptproduct_avgProdPackage
after insert on customOrder
for each row
begin
	if new.isValid = 0 then
		update averageProductsSoldForPackage set averageProducts =
		( select avg(count) from (  select count(sp.product) as count
									from customOrder o left join selectedProductsForOrder sp on o.id = sp.customOrder
									where o.isValid = 0 and o.servicePackage = new.servicePackage
									group by o.id ) as counts)
		where package = (select name from servicePackage where id = new.servicePackage);
	end if;
end $$
delimiter ;

drop trigger if exists order_updateoptproduct_avgProdPackage;
delimiter $$
create trigger order_updateoptproduct_avgProdPackage
after update on customOrder
for each row
begin
	if new.isValid = 0 and new.isValid <> old.isValid then
		update averageProductsSoldForPackage set averageProducts =
		( select avg(count) from (  select count(sp.product) as count
									from customOrder o left join selectedProductsForOrder sp on o.id = sp.customOrder
									where o.isValid = 0 and o.servicePackage = new.servicePackage
									group by o.id ) as counts)
		where package = (select name from servicePackage where id = new.servicePackage);
	end if;
end $$
delimiter ;

drop trigger if exists package_createoptproduct_avgProdPackage;
create trigger package_createoptproduct_avgProdPackage
after insert on servicePackage
for each row
insert into averageProductsSoldForPackage values (new.name,0);

drop trigger if exists package_deleteoptproduct_avgProdPackage;
create trigger package_deleteoptproduct_avgProdPackage
after delete on servicePackage
for each row
delete from averageProductsSoldForPackage where package = old.name;

drop trigger if exists package_updatepkg_avgProdPackage;
delimiter $$
create trigger package_updatepkg_avgProdPackage
after update on servicePackage
for each row
begin
	if new.name <> old.name then
		update averageProductsSoldForPackage set package = new.name where package = old.name;
	end if;
end $$
delimiter ;

drop trigger if exists insert_selprod_bestSeller;
delimiter $$
create trigger insert_selprod_bestSeller
after insert on selectedProductsForOrder
for each row
begin
	declare currentMax int;
    declare newValue int;
	if (select o.isValid from customOrder o where o.id = new.customOrder) = 0 then			
		set currentMax = (select max(totalSales) from bestSellerProduct);
        set newValue = (select sum(p.fee*v.duration) 
						from customOrder o join selectedProductsForOrder sp on o.id = sp.customOrder
						join product p on sp.product = p.name
						join validityPeriod v on v.id = o.validity
						where o.isValid = 0 and p.name = new.product
						group by p.name);
        if currentMax is null then
			insert into bestSellerProduct values(new.product,newValue);
        elseif currentMax < newValue then
			update bestSellerProduct set product = new.product, totalSales = newValue where product is not null;
		end if;
	end if;
end $$
delimiter ;

drop trigger if exists update_order_bestSeller;
delimiter $$
create trigger update_order_bestSeller
after update on customOrder
for each row
begin
	declare currentMax int;
    declare newValue int;
    declare newProd varchar(50);
	if new.isValid = 0 and new.isValid <> old.isValid then			
		set currentMax = (select max(totalSales) from bestSellerProduct);
        set newValue = (select max(val) from (select sum(p.fee*v.duration) as val
						from customOrder o join selectedProductsForOrder sp on o.id = sp.customOrder
						join product p on sp.product = p.name
						join validityPeriod v on v.id = o.validity
						where o.isValid = 0
						group by p.name) as s);
        if currentMax is null then
			set newProd = (select product from(select p.name as product, sum(p.fee*v.duration) as val
						from customOrder o join selectedProductsForOrder sp on o.id = sp.customOrder
						join product p on sp.product = p.name
						join validityPeriod v on v.id = o.validity
						where o.isValid = 0
						group by p.name) as t where val = newValue);
			insert into bestSellerProduct values(newProd,newValue);
        elseif currentMax < newValue then
			set newProd = (select product from(select p.name as product, sum(p.fee*v.duration) as val
						from customOrder o join selectedProductsForOrder sp on o.id = sp.customOrder
						join product p on sp.product = p.name
						join validityPeriod v on v.id = o.validity
						where o.isValid = 0
						group by p.name) as t where val = newValue);
			update bestSellerProduct set product = newProd, totalSales = newValue where product is not null;
		end if;
	end if;
end $$
delimiter ;

drop trigger if exists update_product_bestSeller;
delimiter $$
create trigger update_product_bestSeller
after update on product
for each row
begin
	if new.name <> old.name then
		update bestSellerProduct set product = new.name where product = old.name;
	end if;
end $$
delimiter ;

drop trigger if exists delete_product_bestSeller;
create trigger delete_product_bestSeller
after delete on product
for each row
delete from bestSellerProduct where product = old.name;

#select * from totalPurchasesPerPackage;
#select * from totalPurchasesPerPackageAndValidityPeriod;
#select * from salesValue;
#select * from averageProductsSoldForPackage;
#select * from bestSellerProduct;