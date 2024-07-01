package com.khooch.carsalesportal.repository;

import com.khooch.carsalesportal.entity.Car;
import com.khooch.carsalesportal.entity.User;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findByMakeAndModelAndYearAndPriceBetween(String make, String model, Integer year, Integer minPrice, Integer maxPrice);
    List<Car> findByUserUsername(String username);
    List<Car> findByUser(User user);
    List<Car> findAllByActiveTrue();
    
    @EntityGraph(attributePaths = "bids")
    Optional<Car> findById(Long id);
        @Query("SELECT c FROM Car c WHERE " +
           "(:make IS NULL OR c.make LIKE %:make%) AND " +
           "(:model IS NULL OR c.model LIKE %:model%) AND " +
           "(:year IS NULL OR c.year = :year) AND " +
           "(:priceMin IS NULL OR c.price >= :priceMin) AND " +
           "(:priceMax IS NULL OR c.price <= :priceMax)")
    List<Car> searchCars(@Param("make") String make,
                         @Param("model") String model,
                         @Param("year") Integer year,
                         @Param("priceMin") Integer priceMin,
                         @Param("priceMax") Integer priceMax);
    List<Car> findAll(); // Add this method
}
