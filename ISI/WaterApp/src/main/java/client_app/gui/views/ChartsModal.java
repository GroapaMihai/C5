package client_app.gui.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import client_app.appl.domain.entities.WaterSensor;
import client_app.chart.WaterMonitoringChartBuilder;
import client_app.picture.Icon;
import client_app.picture.ImageLoader;
import net.miginfocom.swing.MigLayout;

public class ChartsModal extends View {
	private static final long serialVersionUID = 1L;
	private static ChartsModal instance = null;

	private WaterSensor waterSensor;
	private List<JFreeChart> waterSensorCharts;
	private int displayedChartIndex = 0;

	private ChartPanel chartPanel;
	private JButton prevButton;
	private JButton nextButton;

	private ChartsModal() {
		viewType = ViewType.CHARTS_MODAL;
		width = 500;
		height = 450;

		setLayout(new MigLayout("insets 20, align 50% 50%"));

		initGUIElements();
		addGUIElements();
		addListeners();
	}
	
	public static ChartsModal getInstance() {
		if (instance == null) {
			instance = new ChartsModal();
		}

		return instance;
	}

	private void initGUIElements() {
		chartPanel = new ChartPanel(null);

		prevButton = new JButton("Prev");
		prevButton.setIcon(new ImageIcon(
    		ImageLoader.getInstance().getScaledImageFromDB(Icon.PREV_ICON.getIconName(), 25, 25))
		);

		nextButton = new JButton("Next");
		nextButton.setIcon(new ImageIcon(
    		ImageLoader.getInstance().getScaledImageFromDB(Icon.NEXT_ICON.getIconName(), 25, 25))
		);
	}

	private void addGUIElements() {
		add(chartPanel, "push, grow, wrap");
		add(prevButton, "split3");
		add(Box.createHorizontalGlue(), "push, grow");
		add(nextButton);
	}

	private void addListeners() {
		prevButtonListener();
		nextButtonListener();
	}

	private void prevButtonListener() {
		prevButton.addActionListener(new ActionListener () {
            @Override
		    public void actionPerformed(ActionEvent e) {
            	displayedChartIndex--;
            	if (displayedChartIndex < 0) {
            		displayedChartIndex = waterSensorCharts.size() - 1;
            	}

        		chartPanel.setChart(waterSensorCharts.get(displayedChartIndex));
		    }
		});
	}

	private void nextButtonListener() {
		nextButton.addActionListener(new ActionListener () {
            @Override
		    public void actionPerformed(ActionEvent e) {
            	displayedChartIndex++;
            	if (displayedChartIndex >= waterSensorCharts.size()) {
            		displayedChartIndex = 0;
            	}

        		chartPanel.setChart(waterSensorCharts.get(displayedChartIndex));
		    }
		});
	}

	@Override
	public void populateView(Object arg) {
		displayedChartIndex = 0;

		if (arg instanceof WaterSensor) {
			waterSensor = (WaterSensor) arg;
			waterSensorCharts = WaterMonitoringChartBuilder
				.getInstance()
				.getChartsForWaterSensor(waterSensor);
		} else {
			return;
		}

		chartPanel.setChart(waterSensorCharts.get(displayedChartIndex));
	}
}
