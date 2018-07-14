import java.io.File;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Main extends Application
{

	Player player;
	public CheckMenuItem checkMenuItem;
	FileChooser chooser;
	public Stage stage;
	public boolean full;
	public void start(final Stage primaryStage) throws Exception 
	{
		// TODO Auto-generated method stub
		stage=primaryStage;
		MenuBar menuBar=new MenuBar();
		MenuItem item=new MenuItem("Open");
		CheckMenuItem fullS=new CheckMenuItem("Full Screen");
		checkMenuItem=new CheckMenuItem("Loop");
		Menu menu=new Menu("File");
		
		menu.getItems().add(item);
		menu.getItems().add(checkMenuItem);
		menu.getItems().add(fullS);
		menuBar.getMenus().add(menu);
		chooser=new FileChooser();
		item.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				player.player.pause();
				File file=chooser.showOpenDialog(primaryStage);
				//String workingDir = System.getProperty("user.dir");
				//File file = new File(workingDir, file1.getAbsolutePath());
				System.out.println(file.getAbsolutePath());
				if(file!=null)
				{
					try 
					{
						//	player=new Player(file.toURI().toURL().toExternalForm());
						player=new Player(file.toURI().toString());
						player.setTop(menuBar);
						Scene scene =new Scene(player,1000,800,Color.BLACK);
						primaryStage.setScene(scene);
						//primaryStage.show();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		fullS.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(fullS.isSelected())
					primaryStage.setFullScreen(true);
				else
					primaryStage.setFullScreen(false);
			}
		});
		player=new Player("file:///D:/Movies/English/Batman.v.Superman.Dawn.of.Justice.2016.EXTENDED.1080p.BRRip.x264.AAC-ETRG/Bvs.mp4");
		player.setTop(menuBar);
		if(checkMenuItem.isSelected())
			player.flag=true;
		else
			player.flag=false;
		Scene scene =new Scene(player,1000,800,Color.BLACK);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Brat Media Player");
		primaryStage.show();

	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}




}
