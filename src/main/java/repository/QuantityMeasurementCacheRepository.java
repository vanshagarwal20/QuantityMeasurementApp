package repository;

import java.util.ArrayList;
import java.util.List;

import entity.QuantityMeasurementEntity;

public class QuantityMeasurementCacheRepository implements IQuantityMeasurementRepository {

    private final List<QuantityMeasurementEntity> records = new ArrayList<>();

    @Override
    public void save(QuantityMeasurementEntity entity) {
        records.add(entity);
    }

    public List<QuantityMeasurementEntity> findAll() {
        return new ArrayList<>(records);
    }

    public List<QuantityMeasurementEntity> getMeasurementsByOperation(String operation) {
        List<QuantityMeasurementEntity> result = new ArrayList<>();

        for (QuantityMeasurementEntity entity : records) {
            if (entity.getOperation() != null &&
                entity.getOperation().equalsIgnoreCase(operation)) {
                result.add(entity);
            }
        }

        return result;
    }

    public List<QuantityMeasurementEntity> getMeasurementsByType(String type) {
        List<QuantityMeasurementEntity> result = new ArrayList<>();

        for (QuantityMeasurementEntity entity : records) {
            if (entity.getMeasurementType() != null &&
                entity.getMeasurementType().equalsIgnoreCase(type)) {
                result.add(entity);
            }
        }

        return result;
    }

    public int getTotalCount() {
        return records.size();
    }

    public void deleteAll() {
        records.clear();
    }

    public String getPoolStatistics() {
        return "Cache repository - no connection pool";
    }

    public void releaseResources() {
        System.out.println("No resources to release in cache repository");
    }
}