package com.app.qmaservice.service;

import com.app.qmaservice.dto.OperationResponse;
import com.app.qmaservice.dto.QuantityInputDTO;
import com.app.qmaservice.entity.QuantityOperation;

import java.util.List;

public interface IQuantityService {

    OperationResponse add(QuantityInputDTO input);

    OperationResponse subtract(QuantityInputDTO input);

    OperationResponse multiply(QuantityInputDTO input);

    OperationResponse divide(QuantityInputDTO input);

    OperationResponse compare(QuantityInputDTO input);

    OperationResponse convert(QuantityInputDTO input);

    List<QuantityOperation> getHistoryByOperation(String operation);

    List<QuantityOperation> getHistoryByType(String type);

    long countByOperation(String operation);
}
