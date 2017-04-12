package view;

import db.Game;
import db.HibernateUtil;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import org.hibernate.*;

public class Record extends VBox {
	private String levelName;

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public Record(String levelName) {
		this.levelName = levelName;
		showCurrentLevelRecords();
	}

	public void showCurrentLevelRecords() {
		Platform.runLater(new Runnable() {
			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				TableView<Game> currentLevelTable;

				TableColumn<Game, String> playerNameColumn = new TableColumn<>("Player Name");
				playerNameColumn.setMinWidth(150);
				playerNameColumn.setCellValueFactory(new PropertyValueFactory<>("playerName"));

				TableColumn<Game, String> levelNameColumn = new TableColumn<>("Level Name");
				levelNameColumn.setMinWidth(150);
				levelNameColumn.setCellValueFactory(new PropertyValueFactory<>("levelName"));

				TableColumn<Game, String> timeColumn = new TableColumn<>("Time");
				timeColumn.setMinWidth(100);
				timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
				timeColumn.setCellValueFactory(new Callback<CellDataFeatures<Game, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Game, String> c) {
						return new SimpleStringProperty(c.getValue().getTimeAsString());
					}
				});

				TableColumn<Game, Integer> moveColumn = new TableColumn<>("Moves");
				moveColumn.setMinWidth(100);
				moveColumn.setCellValueFactory(new PropertyValueFactory<>("moves"));

				currentLevelTable = new TableView<>();

				currentLevelTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> {
					@SuppressWarnings("rawtypes")
					TableViewSelectionModel selectionModel = currentLevelTable.getSelectionModel();
					@SuppressWarnings("rawtypes")
					ObservableList selectedCells = selectionModel.getSelectedCells();
					@SuppressWarnings("rawtypes")
					TablePosition tablePosition = (TablePosition) selectedCells.get(0);

					if (tablePosition.getTableColumn().getText().equals("Player Name"))
						showPlayerRecord(tablePosition.getTableColumn().getCellData(newValue).toString());
				});

				currentLevelTable.setItems(getLevelRecords(levelName));
				currentLevelTable.getColumns().addAll(playerNameColumn, timeColumn, moveColumn);
				currentLevelTable.getSortOrder().add(moveColumn);
				currentLevelTable.getSortOrder().add(timeColumn);

				Label searchBy = new Label("Seach by player name :");
				HBox hb1 = new HBox();
				hb1.getChildren().addAll(searchBy);
				HBox.setMargin(searchBy, new Insets(5, 5, 5, 5));

				TextField searchField = new TextField();
				HBox hb2 = new HBox();
				hb2.getChildren().addAll(searchField);
				HBox.setMargin(searchField, new Insets(10, 10, 10, 10));
				hb2.setSpacing(15);

				VBox vBox = new VBox();
				vBox.getChildren().addAll(hb1, hb2, currentLevelTable);
				vBox.setSpacing(10);
				Scene scene = new Scene(vBox);

				Stage tableStage = new Stage();
				tableStage.setTitle(levelName + " Records");
				try {
					tableStage.getIcons().add(new Image(new FileInputStream("resources/records.png")));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				tableStage.setResizable(false);

				tableStage.setScene(scene);
				tableStage.show();

				searchField.setOnKeyPressed(new EventHandler<KeyEvent>() {

					@Override
					public void handle(KeyEvent event) {
						if(event.getCode().equals(KeyCode.ENTER))
						{
							if(searchField.getText().isEmpty())
							{
								currentLevelTable.setItems(getLevelRecords(levelName));
								currentLevelTable.getSortOrder().add(moveColumn);
								currentLevelTable.getSortOrder().add(timeColumn);
							}
							else
							{
								currentLevelTable.setItems(getPlayerRecordsContain(searchField.getText(),levelName));
								currentLevelTable.getSortOrder().add(moveColumn);
								currentLevelTable.getSortOrder().add(timeColumn);
							}
						}
					}
				});
			}

		});


	}


	public Record() {
		showWorldWideRecords();
	}


	public void showWorldWideRecords() {
		Platform.runLater(new Runnable() {
			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				TableView<Game> worldWideTable;

				TableColumn<Game, String> playerNameColumn = new TableColumn<>("Player Name");
				playerNameColumn.setMinWidth(150);
				playerNameColumn.setCellValueFactory(new PropertyValueFactory<>("playerName"));

				TableColumn<Game, String> levelNameColumn = new TableColumn<>("Level Name");
				levelNameColumn.setMinWidth(150);
				levelNameColumn.setCellValueFactory(new PropertyValueFactory<>("levelName"));

				TableColumn<Game, String> timeColumn = new TableColumn<>("Time");
				timeColumn.setMinWidth(100);
				timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
				timeColumn.setCellValueFactory(new Callback<CellDataFeatures<Game, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Game, String> c) {
						return new SimpleStringProperty(c.getValue().getTimeAsString());
					}
				});

				TableColumn<Game, Integer> moveColumn = new TableColumn<>("Moves");
				moveColumn.setMinWidth(100);
				moveColumn.setCellValueFactory(new PropertyValueFactory<>("moves"));

				worldWideTable = new TableView<>();

				worldWideTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> {
					@SuppressWarnings("rawtypes")
					TableViewSelectionModel selectionModel = worldWideTable.getSelectionModel();
					@SuppressWarnings("rawtypes")
					ObservableList selectedCells = selectionModel.getSelectedCells();
					@SuppressWarnings("rawtypes")
					TablePosition tablePosition = (TablePosition) selectedCells.get(0);

					if (tablePosition.getTableColumn().getText().equals("Player Name"))
					{
						showPlayerRecord(tablePosition.getTableColumn().getCellData(newValue).toString());
						worldWideTable.getSelectionModel().clearSelection();
					}
				});

				worldWideTable.setItems(getWorldWideRecords());
				worldWideTable.getColumns().addAll(playerNameColumn, levelNameColumn, timeColumn, moveColumn);
				worldWideTable.getSortOrder().add(moveColumn);
				worldWideTable.getSortOrder().add(timeColumn);

				Label searchBy = new Label("Seach by :\n");
				final ToggleGroup group = new ToggleGroup();
				RadioButton playerRadioButton = new RadioButton("Player Name");
				playerRadioButton.setToggleGroup(group);
				RadioButton levelRadioButton = new RadioButton("Level Name");
				levelRadioButton.setToggleGroup(group);
				HBox hb1 = new HBox();
				hb1.getChildren().addAll(searchBy, playerRadioButton, levelRadioButton);
				HBox.setMargin(searchBy, new Insets(5, 5, 5, 5));
				HBox.setMargin(playerRadioButton, new Insets(5, 5, 5, 15));
				HBox.setMargin(levelRadioButton, new Insets(5, 5, 5, 15));

				TextField searchField = new TextField();
				HBox hb2 = new HBox();
				hb2.getChildren().addAll(searchField);
				HBox.setMargin(searchField, new Insets(10, 10, 10, 10));
				hb2.setSpacing(15);

				VBox vBox = new VBox();
				vBox.getChildren().addAll(hb1, hb2, worldWideTable);
				vBox.setSpacing(10);
				Scene scene = new Scene(vBox);

				Stage tableStage = new Stage();
				tableStage.setTitle("World Wide Records");
				try {
					tableStage.getIcons().add(new Image(new FileInputStream("resources/records.png")));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				tableStage.setResizable(false);

				tableStage.setScene(scene);
				tableStage.show();

				searchField.setOnKeyPressed(new EventHandler<KeyEvent>() {

					@Override
					public void handle(KeyEvent event) {
						if(event.getCode().equals(KeyCode.ENTER))
						{
							if(searchField.getText().isEmpty())
							{
								worldWideTable.setItems(getWorldWideRecords());
								worldWideTable.getSortOrder().add(moveColumn);
								worldWideTable.getSortOrder().add(timeColumn);
							}
							else
							{
								if(levelRadioButton.isSelected())
								{
									worldWideTable.setItems(getLevelWorldWideRecordsContain(searchField.getText()));
									worldWideTable.getSortOrder().add(moveColumn);
									worldWideTable.getSortOrder().add(timeColumn);
								}
								else if(playerRadioButton.isSelected())
								{
									worldWideTable.setItems(getPlayerWorldWideRecordsContain(searchField.getText()));
									worldWideTable.getSortOrder().add(moveColumn);
									worldWideTable.getSortOrder().add(timeColumn);
								}
							}
						}
					}
				});
			}
		});
	}



	public void showPlayerRecord(String playerName) {
		Platform.runLater(new Runnable() {
			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				TableView<Game> playerTable;

				TableColumn<Game, String> playerNameColumn = new TableColumn<>("Player Name");
				playerNameColumn.setMinWidth(150);
				playerNameColumn.setCellValueFactory(new PropertyValueFactory<>("playerName"));

				TableColumn<Game, String> levelNameColumn = new TableColumn<>("Level Name");
				levelNameColumn.setMinWidth(150);
				levelNameColumn.setCellValueFactory(new PropertyValueFactory<>("levelName"));

				TableColumn<Game, String> timeColumn = new TableColumn<>("Time");
				timeColumn.setMinWidth(100);
				timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
				timeColumn.setCellValueFactory(new Callback<CellDataFeatures<Game, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Game, String> c) {
						return new SimpleStringProperty(c.getValue().getTimeAsString());
					}
				});

				TableColumn<Game, Integer> moveColumn = new TableColumn<>("Moves");
				moveColumn.setMinWidth(100);
				moveColumn.setCellValueFactory(new PropertyValueFactory<>("moves"));

				playerTable = new TableView<>();

				playerTable.setItems(getPlayerRecord(playerName));
				playerTable.getColumns().addAll(levelNameColumn, timeColumn, moveColumn);
				playerTable.getSortOrder().add(moveColumn);
				playerTable.getSortOrder().add(timeColumn);

				Label searchBy = new Label("Seach by level name:");
				HBox hb1 = new HBox();
				hb1.getChildren().addAll(searchBy);
				HBox.setMargin(searchBy, new Insets(5, 5, 5, 5));

				TextField searchField = new TextField();
				HBox hb2 = new HBox();
				hb2.getChildren().addAll(searchField);
				HBox.setMargin(searchField, new Insets(10, 10, 10, 10));
				hb2.setSpacing(15);

				VBox vBox = new VBox();
				vBox.getChildren().addAll(hb1, hb2, playerTable);
				vBox.setSpacing(10);
				Scene scene = new Scene(vBox);

				Stage tableStage = new Stage();
				tableStage.setTitle(playerName + " Records");
				try {
					tableStage.getIcons().add(new Image(new FileInputStream("resources/records.png")));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				tableStage.setResizable(false);

				tableStage.setScene(scene);
				tableStage.show();

				searchField.setOnKeyPressed(new EventHandler<KeyEvent>() {

					@Override
					public void handle(KeyEvent event) {
						if(event.getCode().equals(KeyCode.ENTER))
						{
							if(searchField.getText().isEmpty())
							{
								playerTable.setItems(getPlayerRecord(playerName));
								playerTable.getSortOrder().add(moveColumn);
								playerTable.getSortOrder().add(timeColumn);
							}
							else
							{

								playerTable.setItems(getLevelRecordsContain(searchField.getText(),playerName));
								playerTable.getSortOrder().add(moveColumn);
								playerTable.getSortOrder().add(timeColumn);
							}
						}
					}
				});
			}
		});
	}


	@SuppressWarnings({ "deprecation", "unchecked" })
	private ObservableList<Game> getPlayerRecord(String playerName) {
		ObservableList<Game> levelRecordsList = FXCollections.observableArrayList();
		Query<Game> query = HibernateUtil.getSessionFactory().openSession().createQuery("from Games");
		List<Game> gameList = query.list();
		for (Game g : gameList) {
			if (g.getPlayerName().equalsIgnoreCase(playerName))
				levelRecordsList.add(g);
		}
		return levelRecordsList;
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	private ObservableList<Game> getPlayerRecordsContain(String playerName,String levelName) {
		ObservableList<Game> playerRecordList = FXCollections.observableArrayList();
		Query<Game> query = HibernateUtil.getSessionFactory().openSession().createQuery("from Games");
		List<Game> gameList = query.list();
		for (Game g : gameList) {
			if (g.getPlayerName().toLowerCase().contains(playerName.toLowerCase()) && g.getLevelName().equalsIgnoreCase(levelName))
				playerRecordList.add(g);
		}
		return playerRecordList;
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	private ObservableList<Game> getLevelRecordsContain(String levelName, String playerName) {
		ObservableList<Game> levelRecordsList = FXCollections.observableArrayList();
		Query<Game> query = HibernateUtil.getSessionFactory().openSession().createQuery("from Games");
		List<Game> gameList = query.list();
		for (Game g : gameList) {
			if (g.getLevelName().toLowerCase().contains(levelName.toLowerCase()) && g.getPlayerName().equalsIgnoreCase(playerName))
				levelRecordsList.add(g);
		}
		return levelRecordsList;
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	private ObservableList<Game> getLevelWorldWideRecordsContain(String levelName) {
		ObservableList<Game> levelRecordsList = FXCollections.observableArrayList();
		Query<Game> query = HibernateUtil.getSessionFactory().openSession().createQuery("from Games");
		List<Game> gameList = query.list();
		for (Game g : gameList) {
			if (g.getLevelName().toLowerCase().contains(levelName.toLowerCase()))
				levelRecordsList.add(g);
		}
		return levelRecordsList;
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	private ObservableList<Game> getPlayerWorldWideRecordsContain(String playerName) {
		ObservableList<Game> levelRecordsList = FXCollections.observableArrayList();
		Query<Game> query = HibernateUtil.getSessionFactory().openSession().createQuery("from Games");
		List<Game> gameList = query.list();
		for (Game g : gameList) {
			if (g.getPlayerName().toLowerCase().contains(playerName.toLowerCase()))
				levelRecordsList.add(g);
		}
		return levelRecordsList;
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	private ObservableList<Game> getWorldWideRecords() {
		ObservableList<Game> worldWideRecordsList = FXCollections.observableArrayList();
		Query<Game> query = HibernateUtil.getSessionFactory().openSession().createQuery("from Games");
		List<Game> gameList = query.list();
		for (Game g : gameList) {
			worldWideRecordsList.add(g);
		}
		return worldWideRecordsList;
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	private ObservableList<Game> getLevelRecords(String levelName) {
		ObservableList<Game> levelRecordsList = FXCollections.observableArrayList();
		Query<Game> query = HibernateUtil.getSessionFactory().openSession().createQuery("from Games");
		List<Game> gameList = query.list();
		for (Game g : gameList) {
			if (g.getLevelName().equalsIgnoreCase(levelName))
				levelRecordsList.add(g);
		}
		return levelRecordsList;
	}
}
