/*************************** POPULATE DB *****************************/

insert into telcodb.customer values ('test','test','test@123.com', default);

insert into telcodb.servicePackage values (1,'Basic');
insert into telcodb.servicePackage values (2,'Family');
insert into telcodb.servicePackage values (3,'Business');
insert into telcodb.servicePackage values (4,'All Inclusive');

insert into telcodb.validityPeriod values (1, 12, 20, 1), (2, 24, 18, 1), (3, 36, 15, 1);
insert into telcodb.validityPeriod values (4, 12, 30, 2), (5, 24, 24, 2), (6, 36, 22, 2);
insert into telcodb.validityPeriod values (7, 12, 25, 3), (8, 24, 24, 3), (9, 36, 23, 3);
insert into telcodb.validityPeriod values (10, 12, 20, 4), (11, 24, 18, 4), (12, 36, 15, 4);

insert into telcodb.service values (1,1,'Mobilephoneservice',600,1000,10,10,null,null), (2,2,'Mobilephoneservice',1200,100,20,5,null,null),
								   (3,2,'Fixedphoneservice',null,null,null,null,null,null), (4,3,'Fixedinternetservice',null,null,null,null,50,20), 
                                   (5,4,'Mobilephoneservice',2000,2000,30,30,null,null), (6,4,'Fixedinternetservice',null,null,null,null,100,30),
                                   (7,4,'Mobileinternetservice',null,null,null,null,80,25);
                                   

insert into telcodb.product values('SMS news feed', 10), ('Internet TV channel', 20), ('5G connection', 6), ('Formula1', 15), ('Football', 30);


insert into telcodb.optProductsForPackage values ('SMS news feed', 2), ('5G connection', 2);
insert into telcodb.optProductsForPackage values ('Internet TV channel', 3), ('Formula1', 3), ('Football', 3);
insert into telcodb.optProductsForPackage values ('SMS news feed', 4), ('5G connection', 4), ('Internet TV channel', 4), ('Formula1', 4), ('Football', 4);

insert into employee values ('admin','admin');