/********** CLEAN MATERIALIZED VIEWS ******************/
drop table if exists totalPurchasesPerPackage;
drop table if exists totalPurchasesPerPackageAndValidityPeriod;
drop table if exists salesValue;
drop table if exists averageProductsSoldForPackage;
drop table if exists insolventUsers;
drop table if exists suspendedOrders;
drop table if exists alerts;
drop table if exists bestSellerProduct;

create table telcodb.totalPurchasesPerPackage
	(
		package varchar(50) UNIQUE NOT NULL,
        total int NOT NULL,
        PRIMARY KEY (package)
    );
    
create table telcodb.totalPurchasesPerPackageAndValidityPeriod
	(
		id int NOT NULL auto_increment,
		package varchar(50) NOT NULL,
        validity int NOT NULL,
        total int NOT NULL,
        PRIMARY KEY (id)
    );
    
create table telcodb.salesValue
	(
		package varchar(50) UNIQUE NOT NULL,
        totalSale int NOT NULL,
        saleWithoutProd int NOT NULL,
        PRIMARY KEY (package)
    );
    
create table averageProductsSoldForPackage
	(
		package varchar(50) UNIQUE NOT NULL,
        averageProducts float NOT NULL,
        PRIMARY KEY (package)
    );
    
create table bestSellerProduct
	(
		product varchar(50) NOT NULL,
        totalSales int NOT NULL,
        PRIMARY KEY (product)
    );
    
#select * from totalPurchasesPerPackage;
#select * from totalPurchasesPerPackageAndValidityPeriod;
#select * from salesValue;
#select * from averageProductsSoldForPackage;
#select * from bestSellerProduct;