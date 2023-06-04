package com.inpt.reservation;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

import javafx.scene.layout.Priority;



	class etudiant {
		public etudiant(String n, String p, String em, String ps) {
			nom = n ; 
			prenom = p ; 
			email = em ; 
			password = ps ; 
		}
		String nom , prenom ; 
		String email , password ; 
	}
	
	class reservation{

	
		String terrain , date ;
		public reservation( String terrain, String date) {
			this.terrain = terrain;
			this.date = date;
		}
		@Override
		public int hashCode() {
			return Objects.hash(date, terrain);
		}
	
	
		public String getTerrain() {
			return terrain;
		}
		public void setTerrain(String terrain) {
			this.terrain = terrain;
		}
		public String getDate() {
			return date;
		}
		public void setDate(String date) {
			this.date = date;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			reservation other = (reservation) obj;
			return Objects.equals(date, other.date) && Objects.equals(terrain, other.terrain);
		} 
		
		
	}

public class Main extends Application {

	public static void main(String[] args) {
		launch(args) ; 

	}
	
	ArrayList<etudiant> arretudiant = new ArrayList() ; 
	
	ArrayList<reservation> arrReservation = new ArrayList() ; 
	String url = "jdbc:mysql://localhost:3306/terrrainreservation";

		
	
	
	

	
	  
	@Override
	    public void start(Stage primaryStage) throws SQLException, ClassNotFoundException {
		 	
		arretudiant.add(new etudiant("soufiane", "siffar", "test@gmail.com", "123"));
	        primaryStage.setTitle("Gestion des réservations");
	        
	        Class.forName("com.mysql.cj.jdbc.Driver");  
	
			Connection connexion=null;
				connexion = DriverManager.getConnection(url,"root","azer12");
				Statement st = connexion.createStatement();
				String requeteEtud = "select * from etudiant";
				PreparedStatement pSt= connexion.prepareStatement(requeteEtud);
				ResultSet resultat =pSt.executeQuery();
				while(resultat.next()) {
					try {
						arretudiant.add(new etudiant(resultat.getString("nom") , resultat.getString("prenom " ) , resultat.getString("email") , resultat.getString("password"))) ; 
						
					} catch (Exception e) {
				
					}
					
				}
				
				GridPane gp = new GridPane() ; 
				VBox vb = new VBox() ; 
				TextField user  = new TextField() ; 
				user.setPromptText("email");
				PasswordField pass = new PasswordField() ; 
				pass.setPromptText("password");
				Label userlab = new Label("email :") ;
				Label passlab = new Label("password :") ;
				
		        GridPane.setHalignment(userlab, HPos.LEFT);
		        GridPane.setHalignment(passlab, HPos.LEFT);
		        
		        GridPane.setHalignment(user, HPos.RIGHT);
		        GridPane.setHalignment(pass, HPos.RIGHT);
		        Label error = new Label("") ;
		        Button btconnecter = new Button("se connecter") ; 
		        
		        
		        gp.add(userlab , 1 , 1) ; 
		        gp.add(passlab, 1 , 2);
		        
		        
		        gp.add(user , 2 , 1) ; 
		        gp.add(pass , 2 , 2) ;
		        
		        
		        vb.getChildren().add(gp) ; 
		        vb.getChildren().add(error) ; 
		        vb.getChildren().add(btconnecter) ; 
		        vb.setAlignment(Pos.CENTER);
		        gp.setAlignment(Pos.CENTER);
		        gp.setVgap(15);
		        gp.setHgap(10);
		        vb.setMargin(btconnecter, new Insets(5,5,5,5) );
		        vb.setMargin(btconnecter, new Insets(10,10,10,10) );
		        
		        GridPane gpres = new GridPane()  ; 
		        Scene ajres = new Scene(gpres , 450 , 450) ;
		        
		        
		        
		        btconnecter.setOnAction((e)->{
		        	boolean exist = false ; 
		        	for(int i = 0  ; i < arretudiant.size() ; i++) {
		        		if(arretudiant.get(i).email.equals(user.getText().toString()) && arretudiant.get(i).password.equals(pass.getText().toString())) exist = true ; 
		        		
		        	}
		        	if(exist) primaryStage.setScene(ajres);
		        	else error.setText("les données sont incorrectes") ; 
		        }) ;
		        
		        

		   
		        Label terrainl = new Label("choisir un terrain : ") ; 
		        ObservableList<String> terrainnames = FXCollections.observableArrayList();
		        terrainnames.add("Foot"); 
		        terrainnames.add("Volley");
		        terrainnames.add("Basket");
		   
		        ChoiceBox<String> terrainbox = new ChoiceBox<String>(terrainnames) ; 
		        terrainbox.setValue(terrainnames.get(0));
		        Label datel = new Label("Date de reservation :") ; 
		        TextField date = new TextField() ;
		        Button ajouter = new Button("Ajouter reservation") ; 
		        Label errorRes = new Label("") ; 
		        gpres.add(terrainl, 1, 1);
		        gpres.add(terrainbox, 2, 1);
		        gpres.add(datel, 1, 2);
		        gpres.add(date, 2, 2);
		        gpres.add(ajouter, 1,3 );
		        gpres.add(errorRes, 2, 3);
		        gpres.setAlignment(Pos.CENTER);
		        gpres.setVgap(20);
		        gpres.setHgap(20);
		        
		        ajouter.setOnAction((e)->{
		        	try {
		        		ajouterRes(terrainbox.getValue().toString() , date.getText().toString()) ; 
		        		errorRes.setText("la reservation été ajouté") ; 
		        	}
		        	catch(TerrainReserveException | SQLException ex ) {
		        		errorRes.setText(ex.getMessage());
		        	}
		        }) ;
		        
		         
		        
		       
		        
		        
		        
		        
		        
				
	        
				
				Scene s = new Scene(vb , 450 , 450 ) ; 
				
		
	        primaryStage.setScene(s);
	        primaryStage.show();
	    }
	
	public void ajouterRes(String ter , String d) throws TerrainReserveException, SQLException {
		reservation res = new reservation( ter ,  d) ; 
			for(int i =0 ; i<arrReservation.size() ; i++) {
				if(arrReservation.get(i).equals(res)) throw new TerrainReserveException() ; 
			}
			
			arrReservation.add(res) ; 
			Connection connexion = DriverManager.getConnection(url,"root","azer12");
			Statement st = connexion.createStatement();
			String requete= "insert into reservation(terrain , dateres) values ('" + res.getTerrain()+"','" + res.getDate()+"');" ;  
			st.executeUpdate(requete);
				
		
	}
	 

}
