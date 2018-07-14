import java.io.File;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.transform.Scale;
import javafx.util.Duration;

public class Player extends BorderPane {
	Media media;
	MediaPlayer player;
	MediaView view;
	StackPane mpane;
	MediaBar bar;
	boolean flag;
	public Player(String file)
	{
		//String workdir=System.getProperty("user.dir");
		//File f=new File(workdir,"file:///D:/Videos/Flight.mp4");
		media=new Media(file);
		player=new MediaPlayer(media);
		view=new MediaView(player);
		//view.setFitHeight(media.getHeight());
		//view.setFitWidth(media.getWidth());
		bar=new MediaBar(player);
		mpane=new StackPane();
		//mpane.setPrefWidth(1080);
		//mpane.setPrefHeight(720);
		//Scale scale = new Scale(1, 1, 0, 0);
	    //scale.xProperty().bind(mpane.widthProperty().divide(1080));     //must match with the one in the controller
	    //scale.yProperty().bind(mpane.heightProperty().divide(720));   //must match with the one in the controller
	    //mpane.getTransforms().add(scale);
		DoubleProperty width=view.fitWidthProperty();
		DoubleProperty height=view.fitHeightProperty();
		width.bind(Bindings.selectDouble(view.sceneProperty(), "width"));
		height.bind(Bindings.selectDouble(view.sceneProperty(), "height"));
		view.setPreserveRatio(true);
		/*final DoubleProperty width=view.fitWidthProperty();
		final DoubleProperty height=view.fitHeightProperty();
		width.bind(Bindings.selectDouble(view.sceneProperty(), "width"));
		height.bind(Bindings.selectDouble(view.sceneProperty(), "height"));
		view.setPreserveRatio(true);
		*/
		mpane.getChildren().add(view);
		setCenter(mpane);
		setBottom(bar);
		setStyle("-fx-background-color: black");
		player.setOnEndOfMedia(new Runnable() {
				@Override
				public void run() {
					player.seek(Duration.ZERO);
				}
			});
		player.play();
	}
}
