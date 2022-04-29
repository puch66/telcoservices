/********** CLEAN VIEWS ******************/
drop view if exists totalPurchasesPerPackage;
drop view if exists totalPurchasesPerPackageAndValidityPeriod;
drop view if exists salesValue;
drop view if exists averageProductsSoldForPackage;
drop view if exists insolventUsers;
drop view if exists suspendedOrders;
drop view if exists alerts;
drop view if exists bestSellerProduct;

create view totalPurchasesPerPackage as
select s.name as package, count(o.id) as total
from servicePackage s left join customOrder o on s.id = o.servicePackage
where o.isValid = 0 or o.isValid is null
group by s.id;

create view totalPurchasesPerPackageAndValidityPeriod as
select s.name as package, v.duration as validityPeriod, count(o.id) as total
from servicePackage s join validityPeriod v on v.servicePackage = s.id 
					  left join customOrder o on o.validity = v.id
where o.isValid = 0 or o.isValid is null and v.servicePackage = s.id
group by s.id, v.duration;

create view salesValue as
select s.name as package, coalesce(sum(o.totalvalue),0) as totalSale, sum(case when v.id = o.validity then v.duration*v.fee else 0 end) as saleWithoutProd
from servicePackage s join validityPeriod v on v.servicePackage = s.id 
					  left join customOrder o on o.validity = v.id
where o.isValid = 0 or o.isValid is null and v.servicePackage = s.id
group by s.id;

create view averageProductsSoldForPackage as
select package as package, avg(count) as averageProducts
from (	select o.servicePackage as package, count(sp.product) as count
		from customOrder o left join selectedProductsForOrder sp on o.id = sp.customOrder
        where o.isValid = 0 or o.isValid is null
		group by o.id
	 ) as counts
group by package;

drop view if exists bestSellerProduct;
create view bestSellerProduct as
select p.name as product, sum(p.fee*v.duration) as totalSales
from customOrder o join selectedProductsForOrder sp on o.id = sp.customOrder
				   join product p on sp.product = p.name
				   join validityPeriod v on v.id = o.validity
where o.isValid = 0
group by p.name
order by totalSales desc
limit 1;

#select * from totalPurchasesPerPackage;
#select * from totalPurchasesPerPackageAndValidityPeriod;
#select * from salesValue;
#select * from averageProductsSoldForPackage;
#select * from bestSellerProduct;