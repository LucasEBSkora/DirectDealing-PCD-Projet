package eu.telecomnancy.labfx;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import eu.telecomnancy.labfx.model.Ad;
import eu.telecomnancy.labfx.model.JSONDatabase;
import eu.telecomnancy.labfx.model.User;

public class ViewCompteController {

    @FXML private Label userName;
    @FXML private ImageView userPhoto;
    @FXML private ListView<String> offersListView;
    @FXML private ListView<String> demandsListView;
    @FXML private Button addPhotoButton;
    @FXML private Button backToMainButton;

    private Main main;
    private User user;

    public void setMain(Main main){
        this.main = main;
    }

    public void setUser(User user){
        this.user = user;
        
        userName.setText(user.getUserName());

        
        loadUserPhoto();

        
        loadUserOffersAndDemands();
    }

    private void loadUserPhoto() {
        String photoPath = user.getPhotoPath();
        if (photoPath != null && !photoPath.isEmpty()) {
            File photoFile = new File(photoPath);
            if (photoFile.exists()) {
                Image image = new Image(photoFile.toURI().toString());
                userPhoto.setImage(image);
            }
        }
    }

    private void loadUserOffersAndDemands() {
        List<Ad> allAds = JSONDatabase.getInstance().getAdsAsList();

        
        List<Ad> userAds = allAds.stream()
                .filter(ad -> ad.userID == user.UID)
                .collect(Collectors.toList());

        
        List<String> userOffers = userAds.stream()
                .filter(ad -> ad.isOffer)
                .map(Ad::getName)
                .collect(Collectors.toList());

        List<String> userDemands = userAds.stream()
                .filter(ad -> !ad.isOffer)
                .map(Ad::getName)
                .collect(Collectors.toList());

        offersListView.getItems().addAll(userOffers);
        demandsListView.getItems().addAll(userDemands);
    }

    @FXML
    private void addPhoto() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a photo");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg")
        );
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            String pathImage = file.getAbsolutePath();
            
            user.setPhotoPath(pathImage);

            
            loadUserPhoto();
        }
    }

    @FXML
    private void backToMain() throws IOException {
        
        main.mainScreen(user);
    }
}