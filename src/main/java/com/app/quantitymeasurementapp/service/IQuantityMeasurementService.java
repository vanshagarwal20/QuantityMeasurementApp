package com.app.quantitymeasurementapp.service;

import java.util.List;

import com.app.quantitymeasurementapp.dto.QuantityInputDTO;
import com.app.quantitymeasurementapp.model.QuantityMeasurementEntity;

public interface IQuantityMeasurementService {

    QuantityMeasurementEntity add(QuantityInputDTO input);
    QuantityMeasurementEntity subtract(QuantityInputDTO input);
    QuantityMeasurementEntity multiply(QuantityInputDTO input);
    QuantityMeasurementEntity divide(QuantityInputDTO input);
    QuantityMeasurementEntity compare(QuantityInputDTO input);
    QuantityMeasurementEntity convert(QuantityInputDTO input);

    List<QuantityMeasurementEntity> getHistoryByOperation(String operation);
    List<QuantityMeasurementEntity> getHistoryByType(String type);
    long getOperationCount(String operation);
}
