package project0.models;

import java.util.Objects;

public class Transaction {

    private int id;
    private double amount;
    private int userId;
    private int accountId;

    public Transaction() {

    }

    public Transaction(int id, double amount, int userId, int accountId) {
        this.id = id;
        this.amount = amount;
        this.userId = userId;
        this.accountId = accountId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return id == that.id &&
                Double.compare(that.amount, amount) == 0 &&
                userId == that.userId &&
                accountId == that.accountId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, userId, accountId);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", amount=" + amount +
                ", userId=" + userId +
                ", accountId=" + accountId +
                '}';
    }
}
