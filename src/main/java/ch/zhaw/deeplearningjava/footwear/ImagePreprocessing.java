





package ch.zhaw.deeplearningjava.footwear;

import ai.djl.Application;
import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.ImageFactory;
import ai.djl.modality.cv.transform.Resize;
import ai.djl.modality.cv.transform.ToTensor;
import ai.djl.ndarray.NDList;
import ai.djl.translate.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class ImagePreprocessing {

    public static void main(String[] args) {
        Path dirPath = Paths.get("C:\\Users\\jucke\\Desktop\\Juckesam\\projectjuckesam\\dump\\model\\djl-footwear_classification\\newpic\\cars_test\\cars_test");

        try (Stream<Path> paths = Files.walk(dirPath)) {
            paths.filter(Files::isRegularFile)
                 .forEach(ImagePreprocessing::processAndSaveImage);
        } catch (IOException e) {
            System.err.println("Error processing images: " + e.getMessage());
        }
    }

    private static void processAndSaveImage(Path imagePath) {
        try {
            Image img = ImageFactory.getInstance().fromFile(imagePath);

            // Direct image transformation without using Translator
            img = img.toTensor();
            img = img.resize(100, 100);

            // Save the image using Java's ImageIO
            BufferedImage bufferedImage = (BufferedImage) img.getWrappedImage();
            File outputFile = new File(imagePath.getParent().toString(), "processed_" + imagePath.getFileName());
            ImageIO.write(bufferedImage, "png", outputFile);
        } catch (TranslateException | IOException e) {
            System.err.println("Failed to process image " + imagePath + ": " + e.getMessage());
        }
    }
}




///////////////

// package ch.zhaw.deeplearningjava.footwear;

// import ai.djl.Application;
// import ai.djl.modality.cv.Image;
// import ai.djl.modality.cv.ImageFactory;
// import ai.djl.modality.cv.output.Joints;
// import ai.djl.modality.cv.transform.Resize;
// import ai.djl.modality.cv.transform.ToTensor;
// import ai.djl.translate.*;
// import ai.djl.ndarray.NDList;


// import javax.imageio.ImageIO;
// import java.awt.image.BufferedImage;
// import java.io.File;
// import java.io.IOException;
// import java.nio.file.Files;
// import java.nio.file.Path;
// import java.nio.file.Paths;
// import java.util.stream.Stream;

// public class ImagePreprocessing {

//     public static void main(String[] args) {
//         Path dirPath = Paths.get("C:\\Users\\jucke\\Desktop\\Juckesam\\projectjuckesam\\dump\\model\\djl-footwear_classification\\newpic\\cars_test\\cars_test");

//         try (Stream<Path> paths = Files.walk(dirPath)) {
//             paths.filter(Files::isRegularFile)
//                  .forEach(ImagePreprocessing::processAndSaveImage);
//         } catch (IOException e) {
//             System.err.println("Error processing images: " + e.getMessage());
//         }
//     }

//     private static void processAndSaveImage(Path imagePath) {
//         try {
//             Image img = ImageFactory.getInstance().fromFile(imagePath);

//             Translator<Image, Image> translator = new SimpleImageTranslator();

//             // Use the translator to process the image
//             Image processedImage = translator.process(null, new NDList(img)).singletonOrThrow();

//             // Save the image using Java's ImageIO
//             BufferedImage bufferedImage = (BufferedImage) processedImage.getWrappedImage();
//             File outputFile = new File(imagePath.getParent().toString(), "processed_" + imagePath.getFileName());
//             ImageIO.write(bufferedImage, "png", outputFile);
//         } catch (TranslateException | IOException e) {
//             System.err.println("Failed to process image " + imagePath + ": " + e.getMessage());
//         }
//     }

//     private static class SimpleImageTranslator implements Translator<Image, Image> {

//         @Override
//         public NDList processInput(TranslatorContext ctx, Image input) {
//             Pipeline pipeline = new Pipeline();
//             pipeline.add(new Resize(100, 100));
//             pipeline.add(new ToTensor());
//             input = input.transform(pipeline);
//             return new NDList(input);
//         }

//         @Override
//         public Image processOutput(TranslatorContext ctx, NDList list) {
//             return (Image) list.singletonOrThrow();
//         }

//         @Override
//         public Batchifier getBatchifier() {
//             return Batchifier.STACK;
//         }
//     }
// }



// ////////////////////////////


// // package ch.zhaw.deeplearningjava.footwear;

// // import ai.djl.Application;
// // import ai.djl.modality.cv.Image;
// // import ai.djl.modality.cv.ImageFactory;
// // import ai.djl.modality.cv.transform.Resize;
// // import ai.djl.modality.cv.transform.ToTensor;
// // import ai.djl.translate.Pipeline;
// // import ai.djl.translate.Translator;
// // import ai.djl.translate.TranslatorContext;

// // import javax.imageio.ImageIO;
// // import java.awt.image.BufferedImage;
// // import java.io.File;
// // import java.io.IOException;
// // import java.nio.file.Files;
// // import java.nio.file.Path;
// // import java.nio.file.Paths;
// // import java.util.stream.Stream;

// // public class ImagePreprocessing {

// //     public static void main(String[] args) {
// //         Path dirPath = Paths.get("C:\\Users\\jucke\\Desktop\\Juckesam\\projectjuckesam\\dump\\model\\djl-footwear_classification\\newpic\\cars_test\\cars_test");
        
// //         try (Stream<Path> paths = Files.walk(dirPath)) {
// //             paths.filter(Files::isRegularFile)
// //                  .forEach(ImagePreprocessing::processAndSaveImage);
// //         } catch (IOException e) {
// //             System.err.println("Error processing images: " + e.getMessage());
// //         }
// //     }

// //     private static void processAndSaveImage(Path imagePath) {
// //         try {
// //             Image img = ImageFactory.getInstance().fromFile(imagePath);

// //             // Create a pipeline of transformations
// //             Pipeline pipeline = new Pipeline();
// //             pipeline.add(new Resize(100, 100));
// //             pipeline.add(new ToTensor());

// //             Translator<Image, Image> translator = Translator.builder()
// //                 .setPipeline(pipeline)
// //                 .build();

// //             // Process the image with the translator
// //             Image processedImage = translator.process(null, img);

// //             // Save the image using Java's ImageIO
// //             BufferedImage bufferedImage = (BufferedImage) processedImage.getWrappedImage();
// //             File outputFile = new File(imagePath.getParent().toString(), "processed_" + imagePath.getFileName());
// //             ImageIO.write(bufferedImage, "png", outputFile);
// //         } catch (Exception e) {
// //             System.err.println("Failed to process image " + imagePath + ": " + e.getMessage());
// //         }
// //     }
// // }




// // /////////////////////////////////////////


// // // package ch.zhaw.deeplearningjava.footwear;

// // // import ai.djl.Application;
// // // import ai.djl.modality.cv.Image;
// // // import ai.djl.modality.cv.ImageFactory;
// // // import ai.djl.modality.cv.transform.Resize;
// // // import ai.djl.modality.cv.transform.ToTensor;
// // // import ai.djl.translate.Pipeline;
// // // import ai.djl.translate.TranslateException;
// // // import ai.djl.util.Utils;

// // // import java.nio.file.Files;
// // // import java.nio.file.Path;
// // // import java.nio.file.Paths;
// // // import java.io.IOException;
// // // import java.util.stream.Stream;

// // // public class ImagePreprocessing {

// // //     public static void main(String[] args) {
// // //         Path dirPath = Paths.get("C:\\Users\\jucke\\Desktop\\Juckesam\\projectjuckesam\\dump\\model\\djl-footwear_classification\\newpic\\cars_test\\cars_test");
        
// // //         try (Stream<Path> paths = Files.walk(dirPath)) {
// // //             paths.filter(Files::isRegularFile)
// // //                  .forEach(ImagePreprocessing::processAndSaveImage);
// // //         } catch (IOException e) {
// // //             System.err.println("Error processing images: " + e.getMessage());
// // //         }
// // //     }

// // //     private static void processAndSaveImage(Path imagePath) {
// // //         try {
// // //             Image img = ImageFactory.getInstance().fromFile(imagePath);

// // //             Pipeline pipeline = new Pipeline();
// // //             pipeline.add(new Resize(100, 100)); // Resize the image to 100x100 pixels
// // //             pipeline.add(new ToTensor()); // Convert the image to Tensor

// // //             Image transformedImage = img.transform(pipeline);

// // //             Path savePath = Paths.get(imagePath.getParent().toString(), "processed");
// // //             Files.createDirectories(savePath); // Ensure directory exists

// // //             Utils.saveImage(transformedImage, savePath.resolve(imagePath.getFileName()).toString(), "png");
// // //         } catch (TranslateException | IOException e) {
// // //             System.err.println("Failed to process image " + imagePath + ": " + e.getMessage());
// // //         }
// // //     }
// // // }











// // // // package ch.zhaw.deeplearningjava.footwear;
// // // // import ai.djl.Application;
// // // // import ai.djl.modality.cv.Image;
// // // // import ai.djl.modality.cv.ImageFactory;
// // // // import ai.djl.modality.cv.transform.Resize;
// // // // import ai.djl.modality.cv.transform.ToTensor;
// // // // import ai.djl.translate.Pipeline;
// // // // import ai.djl.translate.TranslateException;
// // // // import ai.djl.util.Utils;

// // // // import java.nio.file.Paths;

// // // // public class ImagePreprocessing {

// // // //     public static void main(String[] args) throws TranslateException {
// // // //         // Load an image file from disk
// // // //         Path imagePath = Paths.get("C:\\Users\\jucke\\Desktop\\Juckesam\\projectjuckesam\\dump\\model\\djl-footwear_classification\\n" + //
// // // //                         "ewpic\\cars_test\\cars_test");
// // // //         Image img = ImageFactory.getInstance().fromFile(imagePath);

// // // //         // Create a pipeline of transformations
// // // //         Pipeline pipeline = new Pipeline();
// // // //         pipeline.add(new Resize(100, 100)); // Resize the image to 100x100 pixels
// // // //         pipeline.add(new ToTensor()); // Convert the image to Tensor, which automatically scales the pixel values to [0, 1]

// // // //         // Apply the transformations
// // // //         Image transformedImage = img.transform(pipeline);

// // // //         // Optionally, save the transformed image to check the result
// // // //         Utils.saveImage(transformedImage, "path/to/save/transformed_image.png", "png");
// // // //     }
// // // // }

