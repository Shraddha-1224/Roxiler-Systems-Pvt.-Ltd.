package com.task.serviceimpl;

import java.awt.print.Pageable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.task.model.ProductTransaction;
import com.task.repository.ProductTransactionRepository;

@Service
public class ProductTransactionServiceImpl {
//-------------------------------------------------------------------
	@Autowired
	private ProductTransactionRepository productTransactionRepository;
	@Autowired
	private RestTemplate restTemplate;

//-------------------------------------------------------------------
	public void initializeDatabase() {
		String url = "https://s3.amazonaws.com/roxiler.com/product_transaction.json";
		ResponseEntity<List<ProductTransaction>> response = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<ProductTransaction>>() {
				});
		List<ProductTransaction> transactions = response.getBody();
		productTransactionRepository.saveAll(transactions);
	}

//-------------------------------------------------------------------
	public List<ProductTransaction> getAllTransactions(int month, String search, Pageable pageable) {
		if (search != null && !search.isEmpty()) {
			return productTransactionRepository
					.findByDateOfSaleMonthAndTitleContainingOrDescriptionContainingOrPriceContaining(month, search,
							search, Double.parseDouble(search), pageable);
		} else {
			return productTransactionRepository.findByDateOfSaleMonth(month, pageable);
		}
	}

//-------------------------------------------------------------------

	public Map<String, Object> getStatistics(int month) {
		Map<String, Object> stats = new HashMap<>();
		List<ProductTransaction> allProducts = productTransactionRepository.findByDateOfSaleMonth(month);
		long totalSoldItems = allProducts.stream().filter(ProductTransaction::getIsSold).count();
		long totalNotSoldItems = allProducts.stream().filter(p -> !p.getIsSold()).count();
		double totalSaleAmount = allProducts.stream().filter(ProductTransaction::getIsSold)
				.mapToDouble(ProductTransaction::getPrice).sum();

		stats.put("totalSaleAmount", totalSaleAmount);
		stats.put("totalSoldItems", totalSoldItems);
		stats.put("totalNotSoldItems", totalNotSoldItems);
		return stats;
	}
//-------------------------------------------------------------------

	public Map<String, Long> getBarChart(int month) {
		List<ProductTransaction> transactions = productTransactionRepository.findByDateOfSaleMonth(month);
		Map<String, Long> barChartData = transactions.stream().collect(Collectors.groupingBy(transaction -> {
			if (transaction.getPrice() <= 100)
				return "0-100";
			else if (transaction.getPrice() <= 200)
				return "101-200";
			// Continue for other ranges...
			else
				return "901-above";
		}, Collectors.counting()));
		return barChartData;
	}

	public Map<String, Long> getPieChart(int month) {
		List<ProductTransaction> transactions = productTransactionRepository.findByDateOfSaleMonth(month);
		return transactions.stream()
				.collect(Collectors.groupingBy(ProductTransaction::getCategory, Collectors.counting()));
	}
//-------------------------------------------------------------------
}
