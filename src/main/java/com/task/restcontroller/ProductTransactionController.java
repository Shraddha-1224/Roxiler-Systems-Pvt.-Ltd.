package com.task.restcontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.task.model.ProductTransaction;
import com.task.serviceimpl.ProductTransactionServiceImpl;

@RestController
@RequestMapping("/api/transactions")
public class ProductTransactionController {
	
//---------------------------------------------------------------------------------------------
	@Autowired
	private ProductTransactionServiceImpl productTransactionService;
//---------------------------------------------------------------------------------------------

	@PostMapping("/initialize")
	public ResponseEntity<String> initializeDatabase() {
		productTransactionService.initializeDatabase();
		return ResponseEntity.ok("Database initialized with seed data.");
	}

	@GetMapping
	public ResponseEntity<List<ProductTransaction>> getAllTransactions(@RequestParam int month,
			@RequestParam(required = false) String search, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
	//	PageRequest pageable = PageRequest.of(page, size);
		List<ProductTransaction> transactions = productTransactionService.getAllTransactions(month, search, null);
		return ResponseEntity.ok(transactions);
	}

	@GetMapping("/statistics")
	public ResponseEntity<Map<String, Object>> getStatistics(@RequestParam int month) {
		Map<String, Object> statistics = productTransactionService.getStatistics(month);
		return ResponseEntity.ok(statistics);
	}
//---------------------------------------------------------------------------------------------
	// Endpoints for bar chart and pie chart...

	@GetMapping("/barchart")
	public ResponseEntity<Map<String, Long>> getBarChart(@RequestParam int month) {
		Map<String, Long> barChart = productTransactionService.getBarChart(month);
		return ResponseEntity.ok(barChart);
	}

	@GetMapping("/piechart")
	public ResponseEntity<Map<String, Long>> getPieChart(@RequestParam int month) {
		Map<String, Long> pieChart = productTransactionService.getPieChart(month);
		return ResponseEntity.ok(pieChart);
	}
//---------------------------------------------------------------------------------------------
	@GetMapping("/combined")
	public ResponseEntity<Map<String, Object>> getCombinedData(@RequestParam int month) {
		Map<String, Object> combinedData = new HashMap<>();
//		combinedData.put("transactions", productTransactionService.getAllTransactions(month, null, Pageable.unpaged()));
		combinedData.put("transactions", productTransactionService.getAllTransactions(month, null, null));

		combinedData.put("statistics", productTransactionService.getStatistics(month));
		combinedData.put("barChart", productTransactionService.getBarChart(month));
		combinedData.put("pieChart", productTransactionService.getPieChart(month));
		return ResponseEntity.ok(combinedData);
	}
//---------------------------------------------------------------------------------------------
}
