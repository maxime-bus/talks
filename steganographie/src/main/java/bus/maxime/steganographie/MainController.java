package bus.maxime.steganographie;

import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MainController {

    private FileChooser fileChooser;

    @FXML
    private ImageView sourceImageView;

    @FXML
    private ImageView toHideImageView;

    @FXML
    public ImageView resultImageView;

    @FXML
    public ImageView extractedImageView;

    @FXML
    public Slider slider;

    public MainController() {
        this.fileChooser = new FileChooser();
    }

    public void initialize() {
        slider.valueProperty().addListener((observable, oldValue, depth) -> {
            System.out.println();

            Image imageSource = sourceImageView.getImage();

            WritableImage outputImg = new WritableImage((int) imageSource.getWidth(), (int) imageSource.getHeight());
            WritableImage extractImg = new WritableImage((int) imageSource.getWidth(), (int) imageSource.getHeight());

            hide(imageSource, toHideImageView.getImage(), outputImg, depth.intValue());
            extract(outputImg, extractImg, depth.intValue());

            resultImageView.setImage(outputImg);
            extractedImageView.setImage(extractImg);
        });
    }

    @FXML
    protected void selectSourceImage() throws FileNotFoundException {

        File file = fileChooser.showOpenDialog(sourceImageView.getScene().getWindow());

        Image img = new Image(new FileInputStream(file));

        sourceImageView.setImage(img);
    }

    @FXML
    protected void selectImageToHide() throws FileNotFoundException {

        File file = fileChooser.showOpenDialog(sourceImageView.getScene().getWindow());

        Image img = new Image(new FileInputStream(file));

        toHideImageView.setImage(img);
    }

    private void hide(Image imgTarget, Image imgToHide, WritableImage outputImg, int depth) {
        for (int i = 0; i < imgTarget.getWidth(); i++) {
            for (int j = 0; j < imgTarget.getHeight(); j++) {
                RgbPixel targetPixel = RgbPixel.fromInteger(imgTarget.getPixelReader().getArgb(i, j));
                RgbPixel pixelToHide = RgbPixel.fromInteger(imgToHide.getPixelReader().getArgb(i, j));
                RgbPixel finalPixel = targetPixel.hidePixel(pixelToHide, depth);

                outputImg.getPixelWriter().setArgb(i, j, finalPixel.toInteger());
            }
        }
    }

    private void extract(Image image, WritableImage extracted, int depth) {
        for (int i = 0; i < extracted.getWidth(); i++) {
            for (int j = 0; j < extracted.getHeight(); j++) {
                RgbPixel targetPixel = RgbPixel.fromInteger(image.getPixelReader().getArgb(i, j));
                RgbPixel unhide = targetPixel.extract(depth);

                extracted.getPixelWriter().setArgb(i, j, unhide.toInteger());
            }
        }
    }
}
