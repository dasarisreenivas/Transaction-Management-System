# Transaction-Management📌 Description
MoneyTransaction is a Java-based console application that simulates a secure money transfer between two users in a banking system. It connects to a MySQL database using JDBC and performs transactional updates to ensure atomicity and consistency during the transfer process.

The application allows a user (sender) to send a specified amount to another user (receiver), only after verifying sufficient balance and confirming a PIN. The transaction is carried out using SQL UPDATE statements inside a single JDBC transaction block, which commits only if all conditions are met — otherwise, it performs a rollback to prevent partial updates.

This project is a practical example of:
Using JDBC with MySQL
Implementing transaction control (commit, rollback)
Enforcing transaction isolation (TRANSACTION_SERIALIZABLE) for consistency
Bsic input validation and user authentication (via PIN)
Safe SQL execution using PreparedStatement
It can serve as a foundational model for developing more complex financial transaction systems or as a learning exercise for understanding JDBC transaction handling in Java.
