/********** CLEAN DB ***************************/
drop table if exists telcodb.servicesForActivationSchedule;
drop table if exists telcodb.productsForActivationSchedule;
drop table if exists telcodb.serviceActivationSchedule;
drop table if exists telcodb.auditingTable;
drop table if exists telcodb.employee;
drop table if exists telcodb.selectedProductsForOrder;
drop table if exists telcodb.customOrder;
drop table if exists telcodb.optProductsForPackage;
drop table if exists telcodb.product;
drop table if exists telcodb.service;
drop table if exists telcodb.validityPeriod;
drop table if exists telcodb.servicePackage;
drop table if exists telcodb.customer;

create table telcodb.customer 
	( 
		username varchar(50) NOT NULL,
        password varchar(50) NOT NULL,
        email varchar(50) UNIQUE NOT NULL,
        isInsolvent tinyint(1) NOT NULL DEFAULT 0,
        PRIMARY KEY (username),
        CONSTRAINT positive_insolvent CHECK (isInsolvent >= 0)
	);
    
create table telcodb.servicePackage
	(
		id int NOT NULL auto_increment,
        name varchar(50) UNIQUE NOT NULL,
        PRIMARY KEY (id)
    );
    
create table validityPeriod
	(	
		id int NOT NULL auto_increment,
        duration int NOT NULL,
        fee int NOT NULL,
        servicePackage int,
        PRIMARY KEY (id),
        CONSTRAINT id_package FOREIGN KEY (servicePackage) REFERENCES telcodb.servicePackage (id) ON DELETE CASCADE ON UPDATE CASCADE,
        CONSTRAINT duration_12_24_36 CHECK (duration = 12 or duration = 24 or duration = 36)
    );
    
create table telcodb.service
	(
		id int NOT NULL auto_increment,
        serPackage int,
        service_type varchar(50) NOT NULL,
        num_minutes int,
        num_SMS int,
        fee_extra_min int,
        fee_extra_SMS int,
        num_GB int,
        fee_extra_GB int,
        PRIMARY KEY (id),
        CONSTRAINT id_package2 FOREIGN KEY (serPackage) REFERENCES telcodb.servicePackage (id) ON DELETE CASCADE ON UPDATE CASCADE 
    );

create table telcodb.product
	(
		name varchar(50) NOT NULL,
        fee int NOT NULL,
        PRIMARY KEY (name)
    );
    
create table telcodb.optProductsForPackage
	(
		product varchar(50) NOT NULL,
        servicepackage int NOT NULL,
        PRIMARY KEY (product, servicepackage),
        CONSTRAINT id_product FOREIGN KEY (product) REFERENCES telcodb.product (name) ON DELETE CASCADE ON UPDATE CASCADE,
        CONSTRAINT id_package3 FOREIGN KEY (servicepackage) REFERENCES telcodb.servicePackage (id) ON DELETE CASCADE ON UPDATE CASCADE 
    );

create table telcodb.customOrder
	(
		id int NOT NULL auto_increment,
        creationDate datetime NOT NULL,
        username varchar(50) NOT NULL,
        servicePackage int NOT NULL,
        validity int NOT NULL,
        totalValue int NOT NULL,
        startDate date NOT NULL,
        isValid tinyint(1) NOT NULL,
        PRIMARY KEY(id),
        CONSTRAINT id_user FOREIGN KEY (username) REFERENCES telcodb.customer (username) ON DELETE CASCADE ON UPDATE CASCADE,
        CONSTRAINT id_service4 FOREIGN KEY (servicePackage) REFERENCES telcodb.servicePackage (id) ON DELETE CASCADE ON UPDATE CASCADE,
        CONSTRAINT id_validity FOREIGN KEY (validity) REFERENCES telcodb.validityPeriod (id) ON DELETE CASCADE ON UPDATE CASCADE
    );
    
create table telcodb.selectedProductsForOrder
	(
		product varchar(50) NOT NULL,
        customOrder int NOT NULL,
        PRIMARY KEY (product, customOrder),
        CONSTRAINT id_product2 FOREIGN KEY (product) REFERENCES telcodb.product (name) ON DELETE CASCADE ON UPDATE CASCADE,
        CONSTRAINT id_order FOREIGN KEY (customOrder) REFERENCES telcodb.customOrder (id) ON DELETE CASCADE ON UPDATE CASCADE
    );

create table  telcodb.serviceActivationSchedule
	(
		id int NOT NULL auto_increment,
        username varchar(50) NOT NULL,
        activationDate date NOT NULL,
        deactivationDate date NOT NULL,
        CONSTRAINT id_customer FOREIGN KEY (username) REFERENCES telcodb.customer (username) ON DELETE CASCADE ON UPDATE CASCADE,
        PRIMARY KEY(id)
    );
    
create table  telcodb.productsForActivationSchedule
	(
		product varchar(50) NOT NULL,
        activationSchedule int NOT NULL,
        PRIMARY KEY (product, activationSchedule),
        CONSTRAINT id_product3 FOREIGN KEY (product) REFERENCES telcodb.product (name) ON DELETE CASCADE ON UPDATE CASCADE,
        CONSTRAINT id_schedule FOREIGN KEY (activationSchedule) REFERENCES telcodb.serviceActivationSchedule (id) ON DELETE CASCADE ON UPDATE CASCADE
    );
    
create table  telcodb.servicesForActivationSchedule
	(
		service int NOT NULL,
        activationSchedule int NOT NULL,
        PRIMARY KEY (service, activationSchedule),
        CONSTRAINT id_service FOREIGN KEY (service) REFERENCES telcodb.service (id) ON DELETE CASCADE ON UPDATE CASCADE,
        CONSTRAINT id_schedule2 FOREIGN KEY (activationSchedule) REFERENCES telcodb.serviceActivationSchedule (id) ON DELETE CASCADE ON UPDATE CASCADE
    );
    
create table  telcodb.auditingTable
	(
		id int NOT NULL auto_increment,
        username varchar(50) NOT NULL,
        email varchar(50) NOT NULL,
        amount int NOT NULL,
        rejectionDateTime datetime NOT NULL,
        PRIMARY KEY(id)
    );
    
create table employee
	( 
		username varchar(50) NOT NULL,
        password varchar(50) NOT NULL,
        PRIMARY KEY (username)
	);
