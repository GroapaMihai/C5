package client_app.chart;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import client_app.api.annotations.Range;
import client_app.appl.dao.SampleDao;
import client_app.appl.domain.entities.Sample;
import client_app.appl.domain.entities.WaterSensor;
import client_app.api.annotations.Column;

public class WaterMonitoringChartBuilder {
	private static WaterMonitoringChartBuilder instance = null;
	private final int NUMBER_OF_SAMPLES_FOR_PLOTTING = 30;
	private SampleDao sampleDao;

	private WaterMonitoringChartBuilder() {
		sampleDao = SampleDao.getInstance();
	}

	public static WaterMonitoringChartBuilder getInstance() {
		if (instance == null) {
			instance = new WaterMonitoringChartBuilder();
		}

		return instance;
	}

	/**
	 * Obtine o lista cu charts, cate unul pentru fiecare parametru masurat de un Senzor,
	 * avand 'NUMBER_OF_SAMPLES_FOR_PLOTTING' intrari per grafic (maxim)
	 */
	public List<JFreeChart> getChartsForWaterSensor(WaterSensor waterSensor) {
		Map<String, DefaultCategoryDataset> datasetsAssocWithParameterName = new HashMap<>();
		List<JFreeChart> charts = new ArrayList<>();
		List<Sample> lastSamplesOfWaterSensor = sampleDao
			.getSamplesOfWaterSensorOrderedByTime(waterSensor);
		Field[] sampleFields = Sample.class.getDeclaredFields();
		Object fieldValue;
		Column fieldColumn;
		Double doubleValue;
		Integer intValue;
		JFreeChart chart;
		CategoryPlot categoryPlot;
		CategoryAxis domainAxis;

		if (lastSamplesOfWaterSensor == null || lastSamplesOfWaterSensor.isEmpty()) {
			return null;
		}

		// pastrez cele mai recente 'NUMBER_OF_SAMPLES_FOR_PLOTTING' mostre
		lastSamplesOfWaterSensor = lastSamplesOfWaterSensor
			.subList(0, Math.min(lastSamplesOfWaterSensor.size(), NUMBER_OF_SAMPLES_FOR_PLOTTING));

		// inversez ordinea celor mai recente probe, intrucat cea mai recenta va fi afisata ultima pe grafic
		Collections.reverse(lastSamplesOfWaterSensor);

		// parcurg probele, caut campurile cu adnotarea @Range (pentru ca doar pentru ele construiesc grafice),
		// adaug valorile la dataset corespunzator fiecarui parametru masurat
		for (Sample sample : lastSamplesOfWaterSensor) {
			for (Field field : sampleFields) {
				field.setAccessible(true);
	
				// field-ul nu este parametru de masurat
				if (!(field.isAnnotationPresent(Range.class))) {
					continue;
				}

				// field-ul nu este asociat unei coloane din baza
				if (!(field.isAnnotationPresent(Column.class))) {
					continue;
				}

				fieldColumn = field.getAnnotation(Column.class);

				// daca in map nu exista un dataset asociat campului ce are ambele adnotari, il creez
				if (!datasetsAssocWithParameterName.containsKey(fieldColumn.name())) {
					datasetsAssocWithParameterName.put(fieldColumn.name(), new DefaultCategoryDataset());
				}

				// adaug in dataset corespunzator parametrului masurat valoarea sa, pentru mostra curenta
				try {
					fieldValue = field.get(sample);
					
					if (fieldValue instanceof Double) {
						doubleValue = (Double) fieldValue;
						datasetsAssocWithParameterName
							.get(fieldColumn.name())
							.setValue(doubleValue, fieldColumn.name(), sample.getSampleTime().toString());
					} else if (fieldValue instanceof Integer) {
						intValue = (Integer) fieldValue;
						datasetsAssocWithParameterName
							.get(fieldColumn.name())
							.setValue(intValue, fieldColumn.name(), sample.getSampleTime().toString());
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		// construiesc graficele pornind de la datasets
		for (Map.Entry<String, DefaultCategoryDataset> entry : datasetsAssocWithParameterName.entrySet()) {
			chart = ChartFactory.createBarChart(
				entry.getKey() + " chart for last " + lastSamplesOfWaterSensor.size() + " samples",
				"Sample time",
				"Value",
				entry.getValue(), 
				PlotOrientation.VERTICAL,
				false,
				true,
				false
			);

			chart.setBackgroundPaint(Color.yellow);
			chart.getTitle().setPaint(Color.blue);

			categoryPlot = chart.getCategoryPlot(); 
			categoryPlot.setRangeGridlinePaint(Color.red);

			domainAxis = categoryPlot.getDomainAxis();
			domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);

			charts.add(chart);
		}

		return charts;
	}
}
