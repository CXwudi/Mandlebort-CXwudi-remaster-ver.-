package Remaster_Ver;

import java.awt.Toolkit;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Optional;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class MainApp extends Application {
	private GridPane aPane = new GridPane();
	private HBox buttomPane = new HBox();
	private Canvas drawingCanvas;
	private Button changeRepeated = new Button("Change times"), 
			changeScale = new Button("Change scale");

	private int repeated = 4000, scale = 70;
	private double manHH = 2.0, 
			manHL = -2.0, 
			manL ,//= manHL * drawingCanvas.getWidth() / drawingCanvas.getHeight(), 
			manR ;//= manHH * drawingCanvas.getWidth() / drawingCanvas.getHeight();
	// the numbers in Xtrans and Ytrans are exactly coordinate point
	private BigDecimal Xtrans = new BigDecimal("-0.7499713900591012", new MathContext(scale)), 
			Ytrans = new BigDecimal("-0.010014291972323218", new MathContext(scale));

	private double zoomPow = 0, zoom = Math.pow(2, zoomPow);

	// BigDecimal Mathematics Part
	// Now, the key function!!
	private int isEscape3(SuperComplexNumber n) {
		SuperComplexNumber c = new SuperComplexNumber(scale);
		for (int i = 0; i < repeated; i++) {
			c = c.mult(c).add(n);
			// System.out.println(c);
			if (c.modSquare().doubleValue() > 4) {
				return i;
			}
		}
		return 0;
	}

	// main drawing function
	private void DrawMandlebort() {
		
	}

	// Four honor functions that tranfer between mandelbort coordinate and Java coordinate
	private int mandelbrotToJavaCoordX(double mandelbrotX) {
		double length = drawingCanvas.getWidth();
		return (int) Math.round(mandelbrotX * length / 2.0 / manR + length / 2.0);
	}
	
	private int mandelbrotToJavaCoordY(double mandelbrotY) {
		double high = drawingCanvas.getHeight();
		return (int) Math.round(-(mandelbrotY * high / 2.0 / manHH + high / 2.0) + high);
	}
	
	private double javaToMandelbetCoordX (double JavaX){
		double length = drawingCanvas.getWidth();
		return 2.0 * manR * JavaX / length - manR;
	}
	
	private double javaToMandelbetCoordY (double JavaY){
		double high = drawingCanvas.getHeight();
		return -2.0 * manHH * JavaY / high + manHH;
	}
	// JavaFX GUI Part
	// @Override
	public void start(Stage primaryStage) throws Exception {

		// TODO here is a bunch of Mathematics testing.
		System.out.println("Test start");
		long start = System.nanoTime();
		SuperComplexNumber test = new SuperComplexNumber("-0.7499713900591012", "-0.010014291972323218", scale);
		System.out.println(isEscape3(test));
		long stop = System.nanoTime();
		System.out.println("drawtime : " + 10e-9 * (stop - start) * 1200 * 800);

		// TODO building main application framework
		System.out.println("Program initialization start");
		int length = (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.65), 
				high = (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 0.8);
		drawingCanvas = new Canvas(length, high);
		manL = manHL * drawingCanvas.getWidth() / drawingCanvas.getHeight();
		manR = manHH * drawingCanvas.getWidth() / drawingCanvas.getHeight();
		
		drawingCanvas.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO handle mouse click event.
				int x = (int) event.getX(), y = (int) event.getY();
				System.out.print(x + ", " + y + " ");
				if (event.isPrimaryButtonDown()) { // zoom in
					System.out.println("left clicked");
				} else if (event.isSecondaryButtonDown()) { // zoom out
					System.out.println("right clicked");
				} else if (event.isMiddleButtonDown()) { // back to origin graph
					System.out.println("middle clicked");
				}
				double xx = javaToMandelbetCoordX(x),
						yy = javaToMandelbetCoordY(y);
				System.out.println(xx + ", " + yy);
				System.out.println(mandelbrotToJavaCoordX(xx) + ", " + mandelbrotToJavaCoordY(yy));
				DrawMandlebort();

			}}
		);

		changeRepeated.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO handle changes on calculation repeat time.
				int change = repeated;
				while (change == repeated) {
					TextInputDialog inputDialog = new TextInputDialog();
					inputDialog.setTitle("Input Required");
					inputDialog.setHeaderText(null);
					inputDialog.setContentText("Please enter the times that each point is been calculated: \n"
							+ "(leave blank to restore default value 4000, the current value is " + repeated + ")");
					Optional<String> result = inputDialog.showAndWait();
					if (result.isPresent()) {
						try {
							change = result.get().equals("") ? 4000 : Integer.parseInt(result.get());
							repeated = change;
							DrawMandlebort();
							break;
						} catch (Exception e) {
							// TODO: handle exception
							Alert alert = new Alert(Alert.AlertType.ERROR);
							alert.setTitle("Error !");
							alert.setHeaderText(null);
							alert.setContentText("Improper number inputed, try again");
							alert.showAndWait();

						}
					} else {
						break;
					}
				}
				System.out.println(repeated);
			}

		});

		changeScale.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				int change = scale;
				while (change == scale) {
					TextInputDialog inputDialog = new TextInputDialog();
					inputDialog.setTitle("Input Required");
					inputDialog.setHeaderText(null);
					inputDialog.setContentText("Please enter the scale of X and Y in complex number: \n"
							+ "(leave blank to restore default value 70, the current value is " + scale + ")");
					Optional<String> result = inputDialog.showAndWait();
					if (result.isPresent()) {
						try {
							change = result.get().equals("") ? 70 : Integer.parseInt(result.get());
							scale = change;
							DrawMandlebort();
							break;
						} catch (Exception e) {
							// TODO: handle exception
							Alert alert = new Alert(Alert.AlertType.ERROR);
							alert.setTitle("Error !");
							alert.setHeaderText(null);
							alert.setContentText("Improper number inputed, try again");
							alert.showAndWait();

						}
					} else {
						break;
					}
				}
				System.out.println(scale);
			}
		});
		buttomPane.getChildren().addAll(changeRepeated, changeScale);
		aPane.add(buttomPane, 0, 0);
		aPane.add(drawingCanvas, 0, 1);
		
		primaryStage.widthProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				// TODO Auto-generated method stub
				double x = primaryStage.getWidth(), y = primaryStage.getHeight(),
						o = (double) oldValue, n = (double) newValue;
				drawingCanvas.setScaleX(x);
				drawingCanvas.setScaleY(y);
				manL = manHL * drawingCanvas.getWidth() / drawingCanvas.getHeight();
				manR = manHH * drawingCanvas.getWidth() / drawingCanvas.getHeight();
				 System.out.println("Height: " + y + " Width: " + x + " oldValue: " + o + " newValue: " + n);
			}
		});
		primaryStage.setScene(new Scene(aPane));
		//primaryStage.setResizable(false);
		primaryStage.setMinHeight(600);
		primaryStage.setMinWidth(800);
		primaryStage.setTitle("CX无敌の最高智慧结晶 2017高清重制版---Mandelbort set by BigDecimal and JavaFX");
		primaryStage.show();
		// first image drawing process start.
		DrawMandlebort();

	}

	public static void main(String[] args) {
		launch(args);
	}

}
