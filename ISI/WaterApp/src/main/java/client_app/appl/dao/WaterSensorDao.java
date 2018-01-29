package client_app.appl.dao;

import client_app.api.em.EntityManager;
import client_app.api.em.EntityManagerImpl;
import client_app.api.em.EntityUtils;
import client_app.appl.domain.entities.WaterSensor;

import java.util.List;

public class WaterSensorDao implements BaseDao<WaterSensor> {
    private static WaterSensorDao instance = null;
    private EntityManager em;

    private WaterSensorDao() {
        em = new EntityManagerImpl();
    }

    public static WaterSensorDao getInstance() {
        if (instance == null) {
        	instance = new WaterSensorDao();
        }

        return instance;
    }

    @Override
    public WaterSensor findById(Integer id) {
        return em.findById(WaterSensor.class, id);
    }

    @Override
    public Integer getNextIdVal(String columnIdName) {
        return em.getNextIdVal(EntityUtils.getTableName(WaterSensor.class), columnIdName);
    }

    @Override
    public WaterSensor insert(WaterSensor waterSensor) {
        return (WaterSensor) em.insert(waterSensor);
    }

    @Override
    public List<WaterSensor> findAll() {
        return em.findAll(WaterSensor.class);
    }

    @Override
    public WaterSensor update(WaterSensor waterSensor) {
        return em.update(waterSensor);
    }

    @Override
    public void delete(WaterSensor waterSensor) {
        em.delete(waterSensor);
    }

    @Override
    public List<WaterSensor> findMatchingEntities(WaterSensor patternWaterSensor) {
    	return em.findMatchingEntities(WaterSensor.class, patternWaterSensor);
    }
}

