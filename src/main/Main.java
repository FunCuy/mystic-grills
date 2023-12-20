package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import view.Login;
import view.Register;

public class Main extends Application
{
    public static void main(String[] args)
    {
    	launch(args);
    }

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		primaryStage.setTitle("Mystic Grills");

        VBox vbox = new VBox(10);
        vbox.setAlignment(javafx.geometry.Pos.CENTER);

        Text title = new Text("Mystic Grills");
        title.setFont(Font.font(null, FontWeight.BOLD, 18));
        Button loginButton = new Button("Login");
        Button registerButton = new Button("Register");

        loginButton.setOnAction(e ->
        {
            new Login().show();
            primaryStage.close();
        });

        registerButton.setOnAction(e ->
        {
            new Register().show();
            primaryStage.close();
        });

        vbox.getChildren().addAll(title, loginButton, registerButton);

        Scene scene = new Scene(vbox, 324, 182);
        primaryStage.setScene(scene);

        primaryStage.show();
	}
}
