import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.SubtitleTrack;
import javafx.util.Duration;
public class MediaBar extends VBox
{
	private MediaPlayer player;
	private Slider timeslider=new Slider();
	private Slider volslider=new Slider();
	private Label volume=new Label("Volume: ");
	private Button playButton=new Button("||");
	private ClockLabel clock=new ClockLabel();
	private Button fast=new Button(">>");
	private Button slow=new Button("<<");
	double forw=1;
	StackPane pane=new StackPane();
	HBox box=new HBox();
	HBox box1=new HBox();
	public MediaBar(MediaPlayer play)
	{
		player=play;
		setAlignment(Pos.CENTER);
		//setPadding(new Insets(5,10,5, 10));
		HBox.setHgrow(timeslider, Priority.ALWAYS);
		volslider.setPrefWidth(70);
		volslider.setMinWidth(30);
		volslider.setValue(100);
		playButton.setPrefWidth(30);
		fast.setPrefWidth(40);
		slow.setPrefWidth(40);
		clock.setStyle("-fx-text-fill: white");
		Label total=new Label(timeConversion(play.getTotalDuration().toSeconds()));
		System.out.println(play.getCycleDuration());
		Label spacer=new Label("                                                                           ");
		box1.getChildren().add(clock);
		box1.getChildren().add(timeslider);
		//box1.getChildren().add(total);
		box.getChildren().add(slow);
		box.getChildren().add(playButton);
		box.getChildren().add(fast);
		box.getChildren().add(spacer);
		//getChildren().add(timeslider);
		//pane.getChildren().add(clock);
		//box.getChildren().add(spacer);
		box.getChildren().add(volume);
		box.getChildren().add(volslider);
		getChildren().add(box1);
		getChildren().add(box);
		//player.setCycleCount(MediaPlayer.INDEFINITE);
		fast.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				forw=forw*2;
				player.setRate(forw);
			}
		});
		slow.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				forw=forw/2;
				player.setRate(forw);
			}
		});
		playButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				Status status=player.getStatus();
				if(status==Status.PLAYING)
				{
					if(player.getCurrentTime().greaterThanOrEqualTo(player.getTotalDuration()))
					{
						player.seek(player.getStartTime());
						player.play();
					}
					else
					{
						player.pause();
						playButton.setText(">");
					}
				}
				if(status==Status.PAUSED||status==Status.HALTED)
				{
					player.play();
					playButton.setText("||");
				}
			}

		});
		player.currentTimeProperty().addListener(new InvalidationListener() {

			@Override
			public void invalidated(Observable arg0) {
				// TODO Auto-generated method stub
				update();
			}
		});
		timeslider.valueProperty().addListener(new InvalidationListener() {

			@Override
			public void invalidated(Observable arg0) {
				// TODO Auto-generated method stub
				if(timeslider.isPressed())
					player.seek(player.getMedia().getDuration().multiply(timeslider.getValue()/100));
			}
		});
		volslider.valueProperty().addListener(new InvalidationListener() {

			@Override
			public void invalidated(Observable arg0) {
				// TODO Auto-generated method stub
				if(volslider.isPressed())
					player.setVolume(volslider.getValue()/100);
			}
		});
	}
	protected void update()
	{
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				timeslider.setValue(player.getCurrentTime().toMillis()/player.getTotalDuration().toMillis()*100);
			}
		});
	}
	public class ClockLabel extends Label{
		public ClockLabel()
		{
			bindToTime();
		}
		public void bindToTime()
		{
			Timeline timeline = new Timeline(
					new KeyFrame(Duration.seconds(0),
							new EventHandler<ActionEvent>() {
						@Override public void handle(ActionEvent actionEvent) {
							double seconds=player.getCurrentTime().toSeconds();
							setText(timeConversion(seconds));
						}
					}
							),
					new KeyFrame(Duration.seconds(1))
					);
			timeline.setCycleCount(Animation.INDEFINITE);
			timeline.play();
		}
	}
	private static String timeConversion(double totalSeconds) {

		final int MINUTES_IN_AN_HOUR = 60;
		final int SECONDS_IN_A_MINUTE = 60;
		double seconds = totalSeconds % SECONDS_IN_A_MINUTE;
		double totalMinutes = totalSeconds / SECONDS_IN_A_MINUTE;
		double minutes = totalMinutes % MINUTES_IN_AN_HOUR;
		double hours = totalMinutes / MINUTES_IN_AN_HOUR;
		String tim=(String)((int)hours	+ ":" + (int)minutes + " :" + (int)seconds);
		if(hours==0&&minutes!=0&&seconds!=0)
			tim=(String)((int)minutes + " :" + (int)seconds);
		if(minutes==0&&seconds!=0)
			tim=(String)(""+(int)seconds);
		return tim;
	}
}
