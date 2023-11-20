package teamx.app.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teamx.app.backend.models.Transaction;
import teamx.app.backend.repositories.TransactionRepository;

import java.util.List;

@Service
public class TransactionService {
    TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

//    public List<Transaction> getTransactionsByOrderId(Long id) {
//        return transactionRepository.findByOrderId(id);
//    }
//
//    public List<Transaction> getTransactionsByProductId(Long id) {
//        return transactionRepository.findByProductId(id);
//    }
//
//    public List<Transaction> getTransactionsByProjectId(Long id) {
//        return transactionRepository.findByProjectId(id);
//    }

//    public List<Integer> getTransactionQuantitiesByProductIdAndCategory(Long id, String category) {
//        return transactionRepository.findTransactionQuantitiesByProductIdAndCategory(id, category);
//    }

    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id).orElse(null);
    }

    public Transaction addTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }
}
