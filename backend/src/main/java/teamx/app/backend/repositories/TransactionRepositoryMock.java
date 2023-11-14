package teamx.app.backend.repositories;

import teamx.app.backend.models.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TransactionRepositoryMock implements ModelRepository<Transaction> {
    private List<Transaction> transactions;

    public TransactionRepositoryMock() {
        this.transactions = new ArrayList<>();
    }

    @Override
    public List<Transaction> findAll() {
        return this.transactions;
    }

    @Override
    public Transaction findById(int id) {
        return transactions.stream().filter(transaction -> transaction.getId() == id).findFirst().orElse(null);
    }

    @Override
    public Transaction save(Transaction transaction) {
        if (transactions.stream().anyMatch(transaction1 -> transaction1.getId() == transaction.getId())) {
            transactions.set(transactions.indexOf(transaction), transaction);
            return transaction;
        }
        transactions.add(transaction);
        return transaction;
    }

    @Override
    public Transaction deleteById(int id) {
        Transaction transaction =
                transactions.stream().filter(transaction1 -> transaction1.getId() == id).findFirst().orElse(null);
        if (transaction != null) {
            transactions.remove(transaction);
            return transaction;
        }
        return null;
    }


}
