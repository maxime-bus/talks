package bus.maxime.steganographie;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.stream.Stream;

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

    @FXML
    private CheckBox bit8;

    @FXML
    private CheckBox bit7;

    @FXML
    private CheckBox bit6;

    @FXML
    private CheckBox bit5;

    @FXML
    private CheckBox bit4;

    @FXML
    private CheckBox bit3;

    @FXML
    private CheckBox bit2;

    @FXML
    private CheckBox bit1;

    @FXML
    private Canvas canvas;

    public MainController() {
        this.fileChooser = new FileChooser();
    }

    public void initialize() {
        slider.valueProperty().addListener((observable, oldValue, depth) -> {
            Image imageSource = sourceImageView.getImage();

            WritableImage outputImg = new WritableImage((int) imageSource.getWidth(), (int) imageSource.getHeight());
            WritableImage extractImg = new WritableImage((int) imageSource.getWidth(), (int) imageSource.getHeight());

            hide(imageSource, toHideImageView.getImage(), outputImg, depth.intValue());
            extract(outputImg, extractImg, depth.intValue());

            resultImageView.setImage(outputImg);
            extractedImageView.setImage(extractImg);
        });

        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setFill(Color.color(0, 0, 0));

        gc.fillRect(0, 0, 200, 200);

        Stream.of(bit1, bit2, bit3, bit4, bit5, bit6, bit7, bit8)
                .forEach(checkbox -> checkbox.selectedProperty().addListener((observable, oldValue, newValue) -> {

                    int color = booleanToInt(bit8.isSelected()) << 7
                            | (booleanToInt(bit7.isSelected()) << 6)
                            | (booleanToInt(bit6.isSelected()) << 5)
                            | (booleanToInt(bit5.isSelected()) << 4)
                            | (booleanToInt(bit4.isSelected()) << 3)
                            | (booleanToInt(bit3.isSelected()) << 2)
                            | (booleanToInt(bit2.isSelected()) << 1)
                            | booleanToInt(bit1.isSelected());

                    gc.setFill(Color.color(color / 256.0, color / 256.0, color / 256.0));

                    gc.fillRect(0, 0, 200, 200);
                }));
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

    private int booleanToInt(boolean b) {
        return b ? 1 : 0;
    }
}
