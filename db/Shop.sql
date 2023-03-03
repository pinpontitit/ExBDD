--1.1

DROP DATABASE IF EXISTS Shop;
CREATE DATABASE Shop;
USE Shop;

CREATE TABLE T_Articles (
IdArticle int(4) PRIMARY KEY AUTO_INCREMENT,
Description varchar(30) NOT NULL,
Brand varchar(30) NOT NULL,
UnitaryPrice float(8) NOT NULL
) ENGINE = InnoDB;


--1.2
SHOW DATABASES;

--1.3
DESCRIBE T_Articles;

--1.4
select * from t_articles;

INSERT INTO T_Articles ( Description, Brand, UnitaryPrice ) VALUES ('Souris', 'Logitoch', 65);
INSERT INTO T_Articles ( Description, Brand, UnitaryPrice ) VALUES ('Clavier', 'Microhard', 49.5);
INSERT INTO T_Articles ( Description, Brand, UnitaryPrice ) VALUES ('Systeme d''exploitation', 'Fenetres', 150);
INSERT INTO T_Articles ( Description, Brand, UnitaryPrice ) VALUES ('Tapis souris', 'Chapeau', 5);
INSERT INTO T_Articles ( Description, Brand, UnitaryPrice ) VALUES ('Cle USB 8 To', 'Syno', 8);
INSERT INTO T_Articles ( Description, Brand, UnitaryPrice ) VALUES ('Webcam', 'Logitoch', 0);
INSERT INTO T_Articles ( Description, Brand, UnitaryPrice ) VALUES ('Casque Audio', 'Syno', 105);
INSERT INTO T_Articles ( Description, Brand, UnitaryPrice ) VALUES ('Office', 'Microsoft', 150);
INSERT INTO T_Articles ( Description, Brand, UnitaryPrice ) VALUES ('Galaxy S10', 'Samsung', 65);
INSERT INTO T_Articles ( Description, Brand, UnitaryPrice ) VALUES ('Macbook', 'Apple', 65);
INSERT INTO T_Articles ( Description, Brand, UnitaryPrice ) VALUES ('Galaxy S22', 'Samsung', 1234);
INSERT INTO T_Articles ( Description, Brand, UnitaryPrice ) VALUES ('Iphone50', 'Apple', 20000);
INSERT INTO T_Articles ( Description, Brand, UnitaryPrice ) VALUES ('Iphone11', 'Apple', 1126);


select * from t_articles;

--1.5
update t_articles set UnitaryPrice=5599 where Description='Iphone50';
select * from t_articles;

--1.6
delete from t_articles where Brand='Logitoch';
select * from t_articles;

--1.7
select * from t_articles where UnitaryPrice>100;

--1.8
select * from t_articles where UnitaryPrice>50 and UnitaryPrice<150;

--1.9
select * from t_articles order by UnitaryPrice asc;

--1.10
select Description from t_articles;

--1.11
select SUM(UnitaryPrice) from t_articles;

--1.12
create table T_Categories (
IdCategory int(4) PRIMARY KEY AUTO_INCREMENT,
CatName varchar(30) NOT NULL,
Description varchar(100) NOT NULL
);

INSERT INTO T_Categories ( CatName, Description ) VALUES ('Logiciel', 'Regroupe tous les logiciels');
INSERT INTO T_Categories ( CatName, Description ) VALUES ('Materiel Info', 'Tout materiel informatique a l''exclusion des PC');
INSERT INTO T_Categories ( CatName, Description ) VALUES ('Smartphone', 'des smartphones');

select * from t_categories;
select * from t_articles;

alter table t_articles add column IdCategory int(4);
alter table t_articles add foreign key (IdCategory) References T_Categories(IdCategory);

select * from t_articles;

update t_articles set IdCategory=3 where Description like '%iphone%' or Description like '%galaxy%';

select * from t_articles;

select IdArticle, t_articles.Description,Brand,UnitaryPrice, catname  from t_articles inner join t_categories on t_articles.idcategory=t_categories.idcategory order by UnitaryPrice;

CREATE
TABLE T_Users (
IdUser int(4) PRIMARY KEY AUTO_INCREMENT,
Login varchar(20) NOT NULL,
Password varchar(20) NOT NULL
) ENGINE = InnoDB;

DESCRIBE T_Users;

select * from t_users;

INSERT INTO T_Users ( Login, Password ) VALUES ('robert@gmail.com', 'robertlebogossdu64');
INSERT INTO T_Users ( Login, Password ) VALUES ('julie@gmail.com', 'joliejulie40');

select * from t_users;

CREATE
TABLE T_Orders (
IdOrder int(4) PRIMARY KEY AUTO_INCREMENT,
Date date NOT NULL,
TotalAmount double(10) NOT NULL,
UserId int(4) NOT NULL,
ListArticles varchar(1000) NOT NULL
) ENGINE = InnoDB;