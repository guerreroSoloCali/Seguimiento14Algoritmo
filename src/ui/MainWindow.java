package ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;

public class MainWindow implements Initializable {

	@FXML
	private Canvas canvas;

	private ArrayList<Double> ejeX = new ArrayList<>();
	private ArrayList<Double> ejeY = new ArrayList<>();
	
	private GraphicsContext gc;
	private BufferedReader bf;

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		gc = canvas.getGraphicsContext2D();

		pintado();
	}

	//METODO DE PINTADO QUE HAGA QUE SALGAN LAS RAYITAS HACIA LOS PUNTOS PA QUE SE PINTEN
	
	public void pintado() {
		leerArchivos();
		double[] resX = getMinMax(ejeX);
		double minX = resX[0];
		double maxX = resX[1];

		
		System.out.println(maxX);
		double[] resY = getMinMax(ejeY);
		double minY = resY[0];
		double maxY = resY[1];
		System.out.println(maxY);
		double deltaPx = canvas.getWidth() - 15;
		double deltaDias = maxX - minX;

		
		double pendienteX = deltaPx / deltaDias;

		double intercepto = pendienteX * minX * (-1);

		double deltaPy = -canvas.getHeight() + 25;

		double deltaAccidentes = maxY - minY;

		double pendienteY = deltaPy / deltaAccidentes;
		double interceptoY = pendienteY * maxY * (-1);
		

		gc.setStroke(Color.rgb(151, 188, 250));
		gc.setLineWidth(1);
		
		
		
		//FOR CON CADA UNO DE LOS INTERCEPTOS DE LOS PUNTOS GRAFICOS DADOS AL ALGORITMO
		
		
		for (int i = 0; i < ejeX.size() - 1; i++) {
			
			
			gc.moveTo(conversionismo(ejeX.get(i), pendienteX, intercepto) + 3,
					conversionismo(ejeY.get(i), pendienteY, interceptoY) + 3);
			gc.lineTo(conversionismo(ejeX.get(i + 1), pendienteX, intercepto) + 3,
					conversionismo(ejeY.get(i + 1), pendienteY, interceptoY) + 3);
		}
		
		
		
		/*El DANE quiere graficar datos demográficos y lo ha contratado a usted
		 *  para que realice una primera versión de un programa que sea capaz de 
		 *  dibujar un gráfico a partir de datos que suministre la mencionada entidad del estado.
		 */
		
		
		
		gc.stroke();
		pintarlo(maxX, maxY);
		gc.setFill(Color.rgb(54, 115, 216));
		
		for (int i = 0; i < ejeX.size(); i++) {
			
			gc.fillOval(conversionismo(ejeX.get(i), pendienteX, intercepto),
					conversionismo(ejeY.get(i), pendienteY, interceptoY), 6, 6);
			
		}
		

	}
	
	/*El DANE quiere graficar datos demográficos y lo ha contratado a usted
	 *  para que realice una primera versión de un programa que sea capaz de 
	 *  dibujar un gráfico a partir de datos que suministre la mencionada entidad del estado.
	 */
	
	
	
	//METODO DE PINTADO PA PINTAR LO QUE SE TIENE QUE PINTAR
	//O SEA LOS CUADROS 4x4
	
	private void pintarlo(double maxX, double maxY) {
		

		gc.setStroke(Color.rgb(204, 204, 204));
		gc.setLineWidth(0.5);
		gc.setFill(Color.rgb(80, 40, 40));
		double y = canvas.getHeight() / 4;
		
		double x = canvas.getWidth() / 4;
		
		for (int i = 0; i < 4; i++) {
			
			
			gc.moveTo(0, y);
			gc.lineTo(canvas.getWidth(), y);
			if(i!=3)
			gc.fillText((int) (maxY * (1-(25*0.01 * (i+1)))) + "", 10, y - 5);
			gc.moveTo(x, 0);
			gc.lineTo(x, canvas.getHeight());
			gc.fillText((int) (maxX * (25*0.01 * (i+1))) + "", x + 5, canvas.getHeight() -15);
			y = y + (canvas.getHeight() / 4);
			x = x + (canvas.getWidth() / 4);
			
			
		}
		
		gc.stroke();
	}

	
	
	//METODO CLASE DE COMO LEER EL ARCHIVO DE LA DATA NECESARIO PA LA DESERIALIZACION
	
	public void leerArchivos() {
		try {
			
			
			
			//DESERIALIZACION DE LA DATA
			bf = new BufferedReader(new FileReader("data/data.csv"));

			
			
			String currentLine = bf.readLine();

			
			
			int i = 0;
			
			
			while (currentLine != null) {
				
				
				if (i != 0) {
					
					
					String[] recordSplit = currentLine.split(",");
					recordSplit[1] = recordSplit[1].replace(";", "");
					ejeX.add(Double.parseDouble(recordSplit[0]));
					ejeY.add(Double.parseDouble(recordSplit[1]));
					
					
				}
				
				currentLine = bf.readLine();
				i++;
			}

		} catch (IOException e) {
			
			e.printStackTrace();
			
			
		} finally {


			if (bf != null) {
				try {
					bf.close();
				} catch (IOException e) {
					
					e.printStackTrace();
					
				}
				
			}
			
		}
		

		
	}
	
	
	/*El DANE quiere graficar datos demográficos y lo ha contratado a usted
	 *  para que realice una primera versión de un programa que sea capaz de 
	 *  dibujar un gráfico a partir de datos que suministre la mencionada entidad del estado.
	 */
	
	
	//SACAR EL MIN MAX CON METODO PLANO

	public double[] getMinMax(ArrayList<Double> eje) {
		ArrayList<Double> aux = new ArrayList<>();
		aux.addAll(eje);
		Collections.sort(aux);
		double min = aux.get(0);
		double max = aux.get(aux.size() - 1);
		return new double[] { min, max };
	}

	 
	//CONVERSIONISMO
	
	private double conversionismo(double x, double m, double b) {

		return m * x + b;
	}

}
