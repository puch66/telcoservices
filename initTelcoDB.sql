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

/*************** populate the db with some data *****************/
INSERT INTO customOrder VALUES (1,'2022-05-09:15:50:41.319', 'test', 4, 10, 1212, '2022-06-01', 1);
INSERT INTO selectedProductsForOrder VALUES ('5G connection',1), ('Football',1), ('Formula1',1), ('Internet TV channel',1), ('SMS news feed',1);
UPDATE customer SET isInsolvent = 1 WHERE (username = 'test');

INSERT INTO customOrder VALUES (2,'2022-05-09 15:51:42.021', 'test', 4, 10, 1092, '2022-07-15', 0);
INSERT INTO selectedProductsForOrder VALUES ('5G connection',2), ('Football',2), ('Formula1',2), ('Internet TV channel',2);
INSERT INTO serviceActivationSchedule VALUES (1, 'test', '2022-07-15', '2023-07-15');
INSERT INTO productsForActivationSchedule VALUES ('5G connection', 1), ('Football', 1), ('Formula1', 1), ('Internet TV channel', 1);
INSERT INTO servicesForActivationSchedule VALUES (5,1), (6,1), (7,1);

INSERT INTO customOrder VALUES (3,'2022-05-09 15:55:15.669', 'test', 1, 2, 432, '2022-05-14', 1);
UPDATE customer SET isInsolvent = 2 WHERE (username = 'test');

UPDATE customer SET isInsolvent = 3 WHERE (username = 'test');
UPDATE customOrder SET isValid = 2 WHERE (id = 1);
INSERT INTO auditingTable (AMOUNT, EMAIL, REJECTIONDATETIME, USERNAME) VALUES (2856, 'test@123.com', '2022-05-09 16:25:00.771', 'test');

INSERT INTO customOrder VALUES (4, '2022-05-09 16:29:56.618', 'test', 3, 9, 1368, '2022-08-05', 0);
INSERT INTO selectedProductsForOrder VALUES ('Formula1',4);
INSERT INTO serviceActivationSchedule VALUES (2, 'test', '2022-08-05', '2025-08-05');
INSERT INTO productsForActivationSchedule VALUES ('Formula1', 2);
INSERT INTO servicesForActivationSchedule VALUES (4,2);

INSERT INTO customOrder VALUES (5, '2022-05-09 16:35:48.857', 'test', 1, 2, 432, '2022-05-28', 0);
INSERT INTO serviceActivationSchedule VALUES (3, 'test', '2022-05-28', '2024-05-28');
INSERT INTO servicesForActivationSchedule VALUES (1, 3);

INSERT INTO customOrder VALUES (6, '2022-05-09 16:39:39.591', 'test', 4, 10, 420, '2022-09-03', 0);
INSERT INTO selectedProductsForOrder VALUES ('Formula1', 6);
INSERT INTO serviceActivationSchedule VALUES (4, 'test', '2022-09-03', '2023-09-03');
INSERT INTO productsForActivationSchedule VALUES ('Formula1', 4);
INSERT INTO servicesForActivationSchedule VALUES (5, 4), (6, 4), (7, 4);

INSERT INTO customOrder VALUES (7, '2022-05-09 16:47:46.876', 'test', 1, 3, 540, '2022-05-31', 0);
INSERT INTO serviceActivationSchedule VALUES (5, 'test', '2022-05-31', '2025-05-31');
INSERT INTO servicesForActivationSchedule VALUES (1, 5);