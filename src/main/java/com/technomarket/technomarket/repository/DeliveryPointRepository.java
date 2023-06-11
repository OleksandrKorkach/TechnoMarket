package com.technomarket.technomarket.repository;

import com.technomarket.technomarket.entity.order.DeliveryPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryPointRepository extends JpaRepository<DeliveryPoint, Long> {
    List<DeliveryPoint> findDeliveryPointByCity(String city);
}
