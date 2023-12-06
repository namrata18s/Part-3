
use Pizzeria;

create or replace view ToppingPopularity as
select ToppingName as Topping, coalesce((count(ToppingID) + sum(PizzaToppingExtraTopping)),0) as ToppingCount 
from topping left join pizzatopping on topping.ToppingID = pizzatopping.PizzaToppingToppingID 
group by ToppingID 
order by ToppingCount desc;

create or replace view ProfitByPizza as
select PizzaSize as Size,PizzaCrustType as Crust, sum(PizzaPrice-PizzaCost) as Profit, min(ordert.OrdertTimeStamp) as 'LastOrderDate' 
from pizza join ordert on pizza.PizzaOrderID = ordert.OrdertID 
group by PizzaSize,PizzaCrustType order by Profit desc;


create or replace view ProfitByOrderType as 
select ordert.OrdertType as 'customerType', 
date_format(ordert.OrdertTimeStamp, '%Y-%M') as 'OrderMonth',
round(sum(ordert.OrdertTotalPriceToCustomer), 2) as 'TotalOrderPrice',
round(sum(ordert.OrdertTotalCostToBusiness), 2) as 'TotalOrderCost',
round(sum(ordert.OrdertTotalPriceToCustomer-ordert.OrdertTotalCostToBusiness), 2) as 'Profit'
from ordert
group by CustomerType,OrderMonth
union select ' ', 'Grand Total' as 'OrderMonth',
round(sum(ordert.OrdertTotalPriceToCustomer), 2) as 'TotalOrderPrice',
round(sum(ordert.OrdertTotalCostToBusiness), 2) as 'TotalOrderCost',
round(sum(ordert.OrdertTotalPriceToCustomer-ordert.OrdertTotalCostToBusiness), 2) as 'Profit'
from ordert; 

select * from ToppingPopularity;
select * from ProfitByPizza;
select * from ProfitByOrderType;
