package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import controller.Client;
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
import model.data.beans.hibernate.Game;

public class Record extends VBox {
	private String levelName;
	private String playerName;
	private Client client;

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public Record(String levelName,Client client) {
		this.levelName = levelName;
		this.client=client;
		showCurrentLevelRecords();
	}
	
	public Record(Client client) {
		this.client=client;
		showWorldWideRecords();
	}

	public ArrayList<Game> filterRecordsByLevelName(ArrayList<Game> records , String levelName)
	{
		ArrayList<Game> arrayList = new ArrayList<>();
		for(Game g : records)
			if(g.getLevelName().toLowerCase().equals(levelName.toLowerCase()))
				arrayList.add(g);
		return arrayList;
	}
	
	public ArrayList<Game> filterRecordsByLevelNameContains(ArrayList<Game> records , String levelName)
	{
		ArrayList<Game> arrayList = new ArrayList<>();
		for(Game g : records)
			if(g.getLevelName().toLowerCase().contains(levelName.toLowerCase()))
				arrayList.add(g);
		return arrayList;
	}
	
	public ArrayList<Game> filterRecordsByPlayerNameContains(ArrayList<Game> records , String playerName)
	{
		ArrayList<Game> arrayList = new ArrayList<>();
		for(Game g : records)
			if(g.getPlayerName().toLowerCase().contains(playerName.toLowerCase()))
				arrayList.add(g);
		return arrayList;
	}
	
	public ArrayList<Game> filterRecordsByPlayerName(ArrayList<Game> records , String playerName)
	{
		ArrayList<Game> arrayList = new ArrayList<>();
		for(Game g : records)
			if(g.getPlayerName().toLowerCase().equals(playerName.toLowerCase()))
				arrayList.add(g);
		return arrayList;
	}
	
	public ArrayList<Game> filterRecordsByBoth(ArrayList<Game> records,String levelName,String playerName)
	{
		ArrayList<Game> arrayList = filterRecordsByLevelName(records, levelName);
		arrayList = filterRecordsByPlayerNameContains(arrayList, playerName);
		return arrayList;
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

				ArrayList<Game> recordsArrayList=null;
				try {
					client.getOutToServer().writeObject("REQUEST");
					client.getOutToServer().flush();
					recordsArrayList = (ArrayList<Game>)client.getServerInput().readObject();
					currentLevelTable.setItems(FXCollections.observableArrayList(filterRecordsByLevelName(recordsArrayList, levelName)));
				} catch (IOException | ClassNotFoundException e1) {
					e1.printStackTrace();
				}
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
							ArrayList<Game> recordsArrayList=null;
							try {
								client.getOutToServer().writeObject("REQUEST");
								client.getOutToServer().flush();
								recordsArrayList = (ArrayList<Game>)client.getServerInput().readObject();
							} catch (IOException | ClassNotFoundException e1) {
								e1.printStackTrace();
							}
							if(searchField.getText().isEmpty())
							{
								currentLevelTable.setItems(FXCollections.observableArrayList(filterRecordsByLevelName(recordsArrayList, levelName)));
								currentLevelTable.getSortOrder().add(moveColumn);
								currentLevelTable.getSortOrder().add(timeColumn);
							}
							else
							{
								currentLevelTable.setItems(FXCollections.observableArrayList(filterRecordsByBoth(recordsArrayList, levelName, searchField.getText())));
								currentLevelTable.getSortOrder().add(moveColumn);
								currentLevelTable.getSortOrder().add(timeColumn);
							}
						}
					}
				});
			}

		});


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

				ArrayList<Game> recordsArrayList=null;
				try {
					client.getOutToServer().writeObject("REQUEST");
					client.getOutToServer().flush();
					recordsArrayList = (ArrayList<Game>)client.getServerInput().readObject();
				} catch (IOException | ClassNotFoundException e1) {
					e1.printStackTrace();
				}
				worldWideTable.setItems(FXCollections.observableArrayList(recordsArrayList));
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
								ArrayList<Game> recordsArrayList=null;
								try {
									client.getOutToServer().writeObject("REQUEST");
									client.getOutToServer().flush();
									recordsArrayList = (ArrayList<Game>)client.getServerInput().readObject();
								} catch (IOException | ClassNotFoundException e1) {
									e1.printStackTrace();
								}
								worldWideTable.setItems(FXCollections.observableArrayList(recordsArrayList));
								worldWideTable.getSortOrder().add(moveColumn);
								worldWideTable.getSortOrder().add(timeColumn);
							}
							else
							{
								ArrayList<Game> recordsArrayList=null;
								try {
									client.getOutToServer().writeObject("REQUEST");
									client.getOutToServer().flush();
									recordsArrayList = (ArrayList<Game>)client.getServerInput().readObject();
								} catch (IOException | ClassNotFoundException e1) {
									e1.printStackTrace();
								}
								if(levelRadioButton.isSelected())
								{
									worldWideTable.setItems(FXCollections.observableArrayList(filterRecordsByLevelNameContains(recordsArrayList, searchField.getText())));
									worldWideTable.getSortOrder().add(moveColumn);
									worldWideTable.getSortOrder().add(timeColumn);
								}
								else if(playerRadioButton.isSelected())
								{
									worldWideTable.setItems(FXCollections.observableArrayList(filterRecordsByPlayerNameContains(recordsArrayList, searchField.getText())));
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

				ArrayList<Game> recordsArrayList=null;
				try {
					client.getOutToServer().writeObject("REQUEST");
					client.getOutToServer().flush();
					recordsArrayList = (ArrayList<Game>)client.getServerInput().readObject();
				} catch (IOException | ClassNotFoundException e1) {
					e1.printStackTrace();
				}
				playerTable.setItems(FXCollections.observableArrayList(filterRecordsByLevelName(recordsArrayList, playerName)));
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
							ArrayList<Game> recordsArrayList=null;
							try {
								client.getOutToServer().writeObject("REQUEST");
								client.getOutToServer().flush();
								recordsArrayList = (ArrayList<Game>)client.getServerInput().readObject();
							} catch (IOException | ClassNotFoundException e1) {
								e1.printStackTrace();
							}
							if(searchField.getText().isEmpty())
							{
								playerTable.setItems(FXCollections.observableArrayList(filterRecordsByPlayerName(recordsArrayList, playerName)));
								playerTable.getSortOrder().add(moveColumn);
								playerTable.getSortOrder().add(timeColumn);
							}
							else
							{

								playerTable.setItems(FXCollections.observableArrayList(filterRecordsByBoth(recordsArrayList,levelName, playerName)));
								playerTable.getSortOrder().add(moveColumn);
								playerTable.getSortOrder().add(timeColumn);
							}
						}
					}
				});
			}
		});
	}


	

	
}
