package com.sparta_express.order_shipment.domain.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.StringPath;
import com.sparta_express.order_shipment.domain.entity.QShipment;
import com.sparta_express.order_shipment.domain.entity.Shipment;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import java.util.*;

public interface ShipmentRepository extends JpaRepository<Shipment, UUID>,
        QuerydslPredicateExecutor<Shipment>,
        QuerydslBinderCustomizer<QShipment> {

    @Override
    default void customize(QuerydslBindings querydslBindings, @NotNull QShipment qShipment) {
        querydslBindings.bind(String.class).all((StringPath path, Collection<? extends String> values) -> {
            List<String> valueList = new ArrayList<>(values.stream().map(String::trim).toList());
            if (valueList.isEmpty()) {
                return Optional.empty();
            }
            BooleanBuilder booleanBuilder = new BooleanBuilder();
            for (String s : valueList) {
                booleanBuilder.or(path.containsIgnoreCase(s));
            }
            return Optional.of(booleanBuilder);
        });
    }

    Optional<Shipment> findByIdAndIsDeleteFalse(UUID shipmentId);

}
