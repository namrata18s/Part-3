
use Pizzeria;

insert into baseprice (BasepriceSize, BasepriceCrustType, BasepriceToCustomer, BasepriceToBusiness) values ('Small', 'Thin', 3, 0.5);
insert into baseprice (BasepriceSize, BasepriceCrustType, BasepriceToCustomer, BasepriceToBusiness) values ('Small', 'Original', 3, 0.75);
insert into baseprice (BasepriceSize, BasepriceCrustType, BasepriceToCustomer, BasepriceToBusiness) values ('Small', 'Pan', 3.5, 1);
insert into baseprice (BasepriceSize, BasepriceCrustType, BasepriceToCustomer, BasepriceToBusiness) values ('Small', 'Gluten-Free', 4, 2);
insert into baseprice (BasepriceSize, BasepriceCrustType, BasepriceToCustomer, BasepriceToBusiness) values ('Medium', 'Thin', 5, 1);
insert into baseprice (BasepriceSize, BasepriceCrustType, BasepriceToCustomer, BasepriceToBusiness) values ('Medium', 'Original', 5, 1.5);
insert into baseprice (BasepriceSize, BasepriceCrustType, BasepriceToCustomer, BasepriceToBusiness) values ('Medium', 'Pan', 6, 2.25);
insert into baseprice (BasepriceSize, BasepriceCrustType, BasepriceToCustomer, BasepriceToBusiness) values ('Medium', 'Gluten-Free', 6.25, 3);
insert into baseprice (BasepriceSize, BasepriceCrustType, BasepriceToCustomer, BasepriceToBusiness) values ('Large', 'Thin', 8, 1.25);
insert into baseprice (BasepriceSize, BasepriceCrustType, BasepriceToCustomer, BasepriceToBusiness) values ('Large', 'Original', 8, 2);
insert into baseprice (BasepriceSize, BasepriceCrustType, BasepriceToCustomer, BasepriceToBusiness) values ('Large', 'Pan', 9, 3);
insert into baseprice (BasepriceSize, BasepriceCrustType, BasepriceToCustomer, BasepriceToBusiness) values ('Large', 'Gluten-Free', 9.5, 4);
insert into baseprice (BasepriceSize, BasepriceCrustType, BasepriceToCustomer, BasepriceToBusiness) values ('XLarge', 'Thin', 10, 2);
insert into baseprice (BasepriceSize, BasepriceCrustType, BasepriceToCustomer, BasepriceToBusiness) values ('XLarge', 'Original', 10, 3);
insert into baseprice (BasepriceSize, BasepriceCrustType, BasepriceToCustomer, BasepriceToBusiness) values ('XLarge', 'Pan', 11.5, 4.5);
insert into baseprice (BasepriceSize, BasepriceCrustType, BasepriceToCustomer, BasepriceToBusiness) values ('XLarge', 'Gluten-Free', 12.5, 6);


insert into discount (DiscountName, DiscountDollars, DiscountPercentage) values ('Employee', 15, true);
insert into discount (DiscountName, DiscountDollars, DiscountPercentage) values ('Lunch Special Medium', 1.00, false);
insert into discount (DiscountName, DiscountDollars, DiscountPercentage) values ('Lunch Special Large', 2.00, false);
insert into discount (DiscountName, DiscountDollars, DiscountPercentage) values ('Specialty Pizza', 1.50, false);
insert into discount (DiscountName, DiscountDollars, DiscountPercentage) values ('Happy Hour',10, true);
insert into discount (DiscountName, DiscountDollars, DiscountPercentage) values ('Gameday Special', 20, true);


insert into topping (ToppingName, ToppingPriceToCustomer, ToppingCostToBusiness , ToppingCurInventoryLevel, ToppingMinInventoryLevel, ToppingSmall, 
ToppingMedium, ToppingLarge, ToppingXLarge) values ('Pepperoni', 1.25, 0.2, 100, 50, 2, 2.75, 3.5, 4.5);
insert into topping (ToppingName, ToppingPriceToCustomer, ToppingCostToBusiness , ToppingCurInventoryLevel, ToppingMinInventoryLevel, ToppingSmall,
ToppingMedium, ToppingLarge, ToppingXLarge) values ('Sausage', 1.25, 0.15, 100, 50, 2.5, 3, 3.5, 4.25);
insert into topping (ToppingName, ToppingPriceToCustomer, ToppingCostToBusiness , ToppingCurInventoryLevel, ToppingMinInventoryLevel, ToppingSmall,
ToppingMedium, ToppingLarge, ToppingXLarge)
values ('Ham', 1.5, 0.15, 78, 25, 2, 2.5, 3.25, 4);
insert into topping (ToppingName, ToppingPriceToCustomer, ToppingCostToBusiness , ToppingCurInventoryLevel, ToppingMinInventoryLevel, ToppingSmall,
ToppingMedium, ToppingLarge, ToppingXLarge)
values ('Chicken', 1.75, 0.25, 56, 25, 1.5, 2, 2.25, 3);
insert into topping (ToppingName, ToppingPriceToCustomer, ToppingCostToBusiness , ToppingCurInventoryLevel, ToppingMinInventoryLevel, ToppingSmall,
ToppingMedium, ToppingLarge, ToppingXLarge)
values ('Green Pepper', 0.5, 0.02, 79, 25, 1, 1.5, 2, 2.5);
insert into topping (ToppingName, ToppingPriceToCustomer, ToppingCostToBusiness , ToppingCurInventoryLevel, ToppingMinInventoryLevel, ToppingSmall,
ToppingMedium, ToppingLarge, ToppingXLarge)
values ('Onion', 0.5, 0.02, 85, 25, 1, 1.5, 2, 2.75);
insert into topping (ToppingName, ToppingPriceToCustomer, ToppingCostToBusiness , ToppingCurInventoryLevel, ToppingMinInventoryLevel, ToppingSmall,
ToppingMedium, ToppingLarge, ToppingXLarge)
values ('Roma Tomato', 0.75, 0.03, 86, 10, 2, 3, 3.5, 4.5);
insert into topping (ToppingName, ToppingPriceToCustomer, ToppingCostToBusiness , ToppingCurInventoryLevel, ToppingMinInventoryLevel, ToppingSmall,
ToppingMedium, ToppingLarge, ToppingXLarge)
values ('Mushrooms', 0.75, 0.1, 52, 50, 1.5, 2, 2.5, 3);
insert into topping (ToppingName, ToppingPriceToCustomer, ToppingCostToBusiness , ToppingCurInventoryLevel, ToppingMinInventoryLevel, ToppingSmall,
ToppingMedium, ToppingLarge, ToppingXLarge)
values ('Black Olives', 0.6, 0.1, 39, 25, 0.75, 1, 1.5, 2);
insert into topping (ToppingName, ToppingPriceToCustomer, ToppingCostToBusiness , ToppingCurInventoryLevel, ToppingMinInventoryLevel, ToppingSmall,
ToppingMedium, ToppingLarge, ToppingXLarge)
values ('Pineapple', 1, 0.25, 15, 0, 1, 1.25, 1.75, 2);
insert into topping (ToppingName, ToppingPriceToCustomer, ToppingCostToBusiness , ToppingCurInventoryLevel, ToppingMinInventoryLevel, ToppingSmall,
ToppingMedium, ToppingLarge, ToppingXLarge)
values ('Jalapenos', 0.5, 0.05, 64, 0, 0.5, 0.75, 1.25, 1.75);
insert into topping (ToppingName, ToppingPriceToCustomer, ToppingCostToBusiness , ToppingCurInventoryLevel, ToppingMinInventoryLevel, ToppingSmall,
ToppingMedium, ToppingLarge, ToppingXLarge)
values ('Banana Peppers', 0.5, 0.05, 36, 0, 0.6, 1, 1.3, 1.75);
insert into topping (ToppingName, ToppingPriceToCustomer, ToppingCostToBusiness , ToppingCurInventoryLevel, ToppingMinInventoryLevel, ToppingSmall,
ToppingMedium, ToppingLarge, ToppingXLarge)
values ('Regular Cheese', 1.5, 0.12, 250, 50, 2, 3.5, 5, 7);
insert into topping (ToppingName, ToppingPriceToCustomer, ToppingCostToBusiness , ToppingCurInventoryLevel, ToppingMinInventoryLevel, ToppingSmall,
ToppingMedium, ToppingLarge, ToppingXLarge)
values ('Four Cheese Blend', 2, 0.15, 150, 25, 2, 3.5, 5, 7);
insert into topping (ToppingName, ToppingPriceToCustomer, ToppingCostToBusiness , ToppingCurInventoryLevel, ToppingMinInventoryLevel, ToppingSmall,
ToppingMedium, ToppingLarge, ToppingXLarge)
values ('Feta Cheese', 2, 0.18, 75, 0, 1.75, 3, 4, 5.5);
insert into topping (ToppingName, ToppingPriceToCustomer, ToppingCostToBusiness , ToppingCurInventoryLevel, ToppingMinInventoryLevel, ToppingSmall,
ToppingMedium, ToppingLarge, ToppingXLarge)
values ('Goat Cheese', 2, 0.2, 54, 0, 1.6, 2.75, 4, 5.5);
insert into topping (ToppingName, ToppingPriceToCustomer, ToppingCostToBusiness , ToppingCurInventoryLevel, ToppingMinInventoryLevel, ToppingSmall,
ToppingMedium, ToppingLarge, ToppingXLarge)
values ('Bacon', 1.5, 0.25, 89, 0, 1, 1.5, 2, 3);


insert into customer (CustomerFirstName, CustomerLastName, CustomerPhone) values ('Andrew', 'Wilkes-Krier','8642545861');
insert into customer (CustomerFirstName, CustomerLastName, CustomerPhone) values ('Matt','Engers','8644749953');
insert into customer (CustomerFirstName, CustomerLastName, CustomerPhone) values ('Frank','Turner', '8642328944');
insert into customer (CustomerFirstName, CustomerLastName, CustomerPhone) values ('Milo', 'Auckerman','8648785679');


insert into ordert(OrdertCustomerID, OrdertTimeStamp, OrdertTotalPriceToCustomer, OrdertTotalCostToBusiness, OrdertType, IsCompleted) 
values (null, '2023-03-05 12:03:00', 20.75, 3.68, 'dinein',1);
insert into ordert(OrdertCustomerID, OrdertTimeStamp, OrdertTotalPriceToCustomer, OrdertTotalCostToBusiness, OrdertType, IsCompleted) 
values (null, '2023-04-03 12:05:00', 19.78, 4.63, 'dinein',1);
insert into ordert(OrdertCustomerID, OrdertTimeStamp, OrdertTotalPriceToCustomer, OrdertTotalCostToBusiness, OrdertType, IsCompleted) 
values (1, '2023-03-03 21:30:00', 89.28, 19.80, 'pickup',1);
insert into ordert(OrdertCustomerID, OrdertTimeStamp, OrdertTotalPriceToCustomer, OrdertTotalCostToBusiness, OrdertType, IsCompleted) 
values (1, '2023-04-20 19:11:00', 86.19, 23.62, 'delivery',1);
insert into ordert(OrdertCustomerID, OrdertTimeStamp, OrdertTotalPriceToCustomer, OrdertTotalCostToBusiness, OrdertType, IsCompleted) 
values (2, '2023-03-02 17:30:00', 27.45, 7.88, 'pickup',1);
insert into ordert(OrdertCustomerID, OrdertTimeStamp, OrdertTotalPriceToCustomer, OrdertTotalCostToBusiness, OrdertType, IsCompleted) 
values (3, '2023-03-02 18:17:00', 25.81, 4.24, 'delivery',1);
insert into ordert(OrdertCustomerID, OrdertTimeStamp, OrdertTotalPriceToCustomer, OrdertTotalCostToBusiness, OrdertType, IsCompleted) 
values (4, '2023-04-13 20:32:00', 37.25, 6.00, 'delivery',1);


insert into orderdiscount values (4, 6), (7, 1);
 

insert into pizza(PizzaOrderID, PizzaPrice, PizzaCost, PizzaKitchenStatus, PizzaSize, PizzaCrustType) 
values(1, 20.75, 3.68, 'Processing', 'Large', 'Thin'), 
(2, 12.85, 3.23, 'Processing', 'Medium', 'Pan'),
(2, 6.93, 1.40, 'Processing', 'Small', 'Original'),
(3, 14.88, 3.30, 'Processing', 'Large', 'Original'),
(3, 14.88, 3.30, 'Processing', 'Large', 'Original'),
(3, 14.88, 3.30, 'Processing', 'Large', 'Original'),
(3, 14.88, 3.30, 'Processing', 'Large', 'Original'),
(3, 14.88, 3.30, 'Processing', 'Large', 'Original'),
(3, 14.88, 3.30, 'Processing', 'Large', 'Original'),
(4, 27.94, 9.19, 'Processing', 'XLarge', 'Original'),
(4, 31.50, 6.25, 'Processing', 'XLarge', 'Original'),
(4, 26.75, 8.18, 'Processing', 'XLarge', 'Original'),
(5, 27.45, 7.88, 'Processing', 'XLarge', 'Gluten-Free'),
(6, 25.81, 4.24, 'Processing', 'Large', 'Thin'),
(7, 18.00, 2.75, 'Processing', 'Large', 'Thin'),
(7, 19.25, 3.25, 'Processing', 'Large', 'Thin');

insert into pizzadiscount(PizzadiscountPizzaId,PizzadiscountDiscountId) values(1,3),(2,2),(2,4),(11,4),(13,4);

insert into dinein values(1, 21), (2, 4);

insert into pickup values(3),(5);

insert into delivery (DeliveryOrderID, DeliveryAddress)
values(4, '115 Party Blvd, Anderson SC 29621'),
(6,'6745 Wessex St, Anderson SC 29621'),
(7, '8879 Suburban Home, Anderson SC 29621');


insert into pizzatopping values(1, 13, True), (1, 1, False), (1, 2, False), 
(2, 15, False), (2, 9, False), (2, 7, False), (2, 8, False), (2, 12, False), 
(3, 13, False), (3, 4, False), (3, 12, False), 
(4, 13, False), (4, 1, False), 
(5, 13, False), (5, 1, False),
(6, 13, False), (6, 1, False),
(7, 13, False), (7, 1, False),
(8, 13, False), (8, 1, False),
(9, 13, False), (9, 1, False),
(10, 1, False), (10, 2, False), (10, 14, False),
(11, 3, True), (11, 10, True), (11, 14, False),
(12, 4, False), (12, 17, False), (12, 14, False),
(13, 5, False), (13, 6, False), (13, 7, False), (13, 8, False), (13, 9, False), (13, 16, False),
(14, 4, False), (14, 5, False), (14, 6, False), (14, 8, False), (14, 14, True),
(15, 14, True),
(16, 13, False), (16, 1, True);