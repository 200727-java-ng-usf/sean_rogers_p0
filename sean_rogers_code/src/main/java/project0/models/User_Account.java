package project0.models;

import java.util.Objects;

public class User_Account {

    private int userId;
    private int savingsAccountId;

    public User_Account() {

    }

    public User_Account(int userId, int savingsAccountId) {
        this.userId = userId;
        this.savingsAccountId = savingsAccountId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getSavingsAccountId() {
        return savingsAccountId;
    }

    public void setSavingsAccountId(int savingsAccountId) {
        this.savingsAccountId = savingsAccountId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User_Account that = (User_Account) o;
        return userId == that.userId &&
                savingsAccountId == that.savingsAccountId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, savingsAccountId);
    }

    @Override
    public String toString() {
        return "User_Account{" +
                "userId=" + userId +
                ", savingsAccountId=" + savingsAccountId +
                '}';
    }
}
