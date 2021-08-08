package Borman.cbbbluechips.controllers.api;

import Borman.cbbbluechips.controllers.AuthenticatedController;
import Borman.cbbbluechips.models.Transaction;
import Borman.cbbbluechips.services.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TransactionController extends AuthenticatedController {


    TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @RequestMapping("/transactions")
    public ResponseEntity<List<Transaction>> transactions() {
        List<Transaction> transactions = transactionService.allTransactions();
        return ResponseEntity.ok(transactions);
    }

}