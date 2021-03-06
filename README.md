# Store Checkout

##SALES TAXES

**Basic sales tax** is applicable at a rate of **10%** on all goods, _except_ **books**,
**food**, and **medical products** that are exempt. Import duty is an additional
sales tax applicable on **all imported goods at a rate of 5%**, with no
exemptions.

When I purchase items I receive a receipt which lists the name of all the
items and their price (including tax), finishing with the total cost of
the items, and the total amounts of sales taxes paid. The **rounding rules**
for sales tax are that for a tax rate of n%, a shelf price of p contains
(np/100 rounded up to the **nearest 0.05**) amount of sales tax.

Write an application that prints out the receipt details for these
shopping baskets…

## INPUT

### # 1

```text
| Qty | Product       | Price |
| 1   | book          | 12.49 |
| 1   | music CD      | 14.99 |
| 1   | chocolate bar | 0.85  |
```

### # 2

```text
| Qty | Product                    | Price |
| 1   | imported box of chocolates | 10.00 |
| 1   | imported bottle of perfume | 47.50 |
```

### # 3

```text
| Qty | Product                    | Price |
| 1   | imported bottle of perfume | 27.99 |
| 1   | bottle of perfume          | 18.99 |
| 1   | packet of headache pills   | 9.75  |
| 1   | imported box of chocolates | 11.25 |
```

## OUTPUT

### # 1
```text
| Qty | Product       | Price |
| 1   | book          | 12.49 |
| 1   | music CD      | 16.49 |
| 1   | chocolate bar | 0.85  |
| Sales Taxes         | 1.50  |
| Total               | 29.83 |
```

### #2
```text
| Qty | Product                    | Price |
| 1   | imported box of chocolates | 10.50 |
| 1   | imported bottle of perfume | 54.65 |
| Sales Taxes                      | 7.65  |
| Total                            | 65.15 |
```

### #3
```text
| Qty | Product                    | Price |
| 1   | imported bottle of perfume | 32.19 |
| 1   | bottle of perfume          | 20.89 |
| 1   | packet of headache pills   | 9.75  |
| 1   | imported box of chocolates | 11.85 |
| Sales Taxes                      | 6.70  |
| Total                            | 74.68 |
```


## Running application

### Required dependencies

- JDK 11 <=

#### Build JAR

```shell
./gradlew jar
```

#### Run JAR

```shell
java -jar build/libs/mock_interview-1.0-SNAPSHOT.jar
```

#### Running test

```shell
./gradlew test
```

To get more detailed test result can open
`build/reports/tests/test/index.html`

#### Running test with code coverage

```shell
./gradlew jacocoTestReport
```

To get more detailed test result can open
`build/reports/jacoco/test/html/index.html`