package com.task.repository;

import java.awt.print.Pageable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.task.model.ProductTransaction;

@Repository
public interface ProductTransactionRepository extends JpaRepository<ProductTransaction, Long> {
    List<ProductTransaction> findByDateOfSaleMonth(int month, Pageable pageable);
    List<ProductTransaction> findByDateOfSaleMonthAndTitleContainingOrDescriptionContainingOrPriceContaining(int month, String title, String description, Double price, Pageable pageable);
    Long countByIsSoldAndDateOfSaleMonth(Boolean isSold, int month);
    @Query("SELECT p FROM ProductTransaction p WHERE MONTH(p.dateOfSale) = :month")
    List<ProductTransaction> findByDateOfSaleMonth(@Param("month") int month);
}
