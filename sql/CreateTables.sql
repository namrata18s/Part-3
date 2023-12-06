
drop schema if exists Pizzeria;
create schema Pizzeria;

use Pizzeria;

create table baseprice(
    BasepriceSize varchar(20) not null,
    BasepriceCrustType varchar(20) not null,
    BasepriceToCustomer decimal(8,2) not null,
    BasepriceToBusiness decimal(8,2) not null,
    PRIMARY KEY(BasepriceCrustType, BasepriceSize)
);


create table customer(
    CustomerID int not null auto_increment,
    CustomerFirstName varchar(20) not null,
    CustomerLastName varchar(20) not null,
    CustomerPhone varchar(15) not null,
    PRIMARY KEY(CustomerID)
);

create table topping(
    ToppingID int not null auto_increment,
    ToppingName varchar(20) not null,
    ToppingPriceToCustomer decimal(8,2) not null,
    ToppingCostToBusiness decimal(8,2) not null,
    ToppingCurInventoryLevel int not null,
    ToppingMinInventoryLevel int not null,
    ToppingSmall decimal(8,2) not null,
    ToppingMedium decimal(8,2) not null,
    ToppingLarge decimal(8,2) not null,
    ToppingXLarge decimal(8,2) not null,
    PRIMARY KEY(ToppingID)
);


create table ordert(
    OrdertID int not null auto_increment,
    OrdertTimeStamp varchar(255) not null,
    IsCompleted boolean NOT NULL,
    OrdertTotalCostToBusiness decimal(8,2) not null,
    OrdertTotalPriceToCustomer decimal(8,2) not null,
    OrdertType varchar(10) not null,
    OrdertCustomerID int,
    PRIMARY KEY(OrdertID),
    FOREIGN KEY(OrdertCustomerID) references customer(CustomerID)
);

create table dinein(
    DineinOrderID int not null,
    DineinTableNumber int not null,
    PRIMARY KEY(DineinOrderID),
    FOREIGN KEY(DineinOrderID) references ordert(OrdertID)
);
    
create table pickup(
    PickupOrderID int not null,
    PRIMARY KEY(PickupOrderID),
    FOREIGN KEY(PickupOrderID) references ordert(OrdertID) 
);

create table delivery(
    DeliveryOrderID int not null,
    DeliveryAddress varchar(155) NOT NULL,
    PRIMARY KEY(DeliveryOrderID),
    FOREIGN KEY(DeliveryOrderID) references ordert(OrdertID) 
);

create table pizza(
    PizzaID int not null auto_increment,
    PizzaOrderID int not null,
    PizzaKitchenStatus varchar(20) not null,
    PizzaPrice decimal(8,2) not null,
    PizzaCost decimal(8,2) not null,
    PizzaSize varchar(20) not null,
    PizzaCrustType varchar(20) not null,
    PRIMARY KEY(PizzaID),
    FOREIGN KEY(PizzaOrderID) REFERENCES ordert(OrdertID),
    FOREIGN KEY(PizzaCrustType, PizzaSize) REFERENCES baseprice(BasepriceCrustType, BasepriceSize)
);

create table pizzatopping(
    PizzaToppingPizzaID int not null,
    PizzaToppingToppingID int not null,
    PizzaToppingExtraTopping boolean,
    PRIMARY KEY(PizzatoppingPizzaID, PizzatoppingToppingID),
    FOREIGN KEY(PizzaToppingPizzaID) references pizza(PizzaID),
    FOREIGN KEY(PizzaToppingToppingID) references topping(ToppingID)
);


create table discount(
    DiscountID int not null auto_increment,
    DiscountName varchar(20),
    DiscountPercentage boolean,
    DiscountDollars decimal(8,2),
    PRIMARY KEY(DiscountID)
);

create table pizzadiscount
(
    PizzadiscountPizzaId int not null,
    PizzadiscountDiscountID int not null,
    PRIMARY KEY(PizzadiscountPizzaId, PizzadiscountDiscountID),
    FOREIGN KEY(PizzadiscountPizzaId) REFERENCES pizza(PizzaID),
    FOREIGN KEY(PizzadiscountDiscountID) REFERENCES discount(DiscountID)
);

create table orderdiscount
(
    OrderdiscountOrdertID int not null,
    OrderdiscountDiscountID int not null,
    PRIMARY KEY(OrderdiscountOrdertID, OrderdiscountDiscountID),
    FOREIGN KEY(OrderdiscountOrdertID) REFERENCES ordert(OrdertID),
    FOREIGN KEY(OrderdiscountDiscountID) REFERENCES discount(DiscountID)
);