package example.minehunt.client;

import eu.hansolo.enzo.clock.Clock;
import eu.hansolo.enzo.clock.ClockBuilder;
import example.minehunt.Grid;
import example.minehunt.MinehuntService;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.util.converter.NumberStringConverter;
import org.controlsfx.control.NotificationPane;

/**
 *
 */
public final class GameNode extends Parent {

    private static final String GAME_ID = "gameId";

    static GameNode getCurrentGame(final Node node) {
        return (GameNode) node.getScene().lookup("#" + GAME_ID);
    }

    private final NotificationPane notificationPane;
    private final Clock clock;

    public GameNode(final MinehuntService minehuntService, final String backgroundImage) {
        setId(GAME_ID);
        final Image background = new Image("mer-surface.jpg");
        //final Image background = new Image("etoile-mer.jpg");
        final Grid grid = minehuntService.createGrid(10, 10, 8);
        final GridNode gridNode = new GridNode(grid, background);
        final ToolBar toolbar = new ToolBar();
        toolbar.setOrientation(Orientation.VERTICAL);

        final Image image = new Image("mine.png", 32, 32, true, true);
        final Label mineLabel = new Label(String.valueOf(gridNode.hiddenMinesCountProperty().get()), new ImageView(image));
        mineLabel.textProperty().bindBidirectional(gridNode.hiddenMinesCountProperty(), new NumberStringConverter());

        toolbar.getItems().add(mineLabel);

        clock = ClockBuilder.create()
                .prefSize(150, 150)
                .design(Clock.Design.DB)
                .nightMode(true)
                .build();
        clock.start();

        toolbar.getItems().add(new Separator(Orientation.HORIZONTAL));
        toolbar.getItems().add(clock);
        toolbar.getItems().add(new Separator(Orientation.HORIZONTAL));

        final Button newGameButton = new Button("New Game");
        newGameButton.setOnAction(event -> getScene().setRoot(new GameNode(minehuntService, backgroundImage)));
        toolbar.getItems().add(newGameButton);

        final BorderPane pane = new BorderPane();
        pane.setLeft(toolbar);
        pane.setCenter(gridNode);

        notificationPane = new NotificationPane(pane);
        notificationPane.getStyleClass().add(NotificationPane.STYLE_CLASS_DARK);
        notificationPane.setShowFromTop(false);
        getChildren().add(notificationPane);
    }

    void displayMessage(final String message) {
        notificationPane.setText(message);
        notificationPane.show();
    }

    long stopClock() {
        return clock.stop();
    }


}
