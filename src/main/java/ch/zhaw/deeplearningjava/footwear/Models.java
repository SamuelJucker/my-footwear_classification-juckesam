/*
 * Copyright 2020 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in compliance
 * with the License. A copy of the License is located at
 *
 * http://aws.amazon.com/apache2.0/
 *
 * or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES
 * OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */
package ch.zhaw.deeplearningjava.footwear;

import ai.djl.Model;
import ai.djl.basicmodelzoo.cv.classification.ResNetV1;
import ai.djl.ndarray.types.Shape;
import ai.djl.nn.Block;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/** A helper class loads and saves model. */
public final class Models {

    // the number of classification labels: boots, sandals, shoes, slippers
    // public static final int NUM_OF_OUTPUT = 4;
    public static final int NUM_OF_OUTPUT = 50;


    // the height and width for pre-processing of the image
    public static final int IMAGE_HEIGHT = 100;
    public static final int IMAGE_WIDTH = 100;

    // the name of the model
    public static final String MODEL_NAME = "carclassy";

    private Models() {}

    public static Model getModel() {
        // create new instance of an empty model
        Model model = Model.newInstance(MODEL_NAME);

        // Block is a composable unit that forms a neural network; combine them like Lego blocks
        // to form a complex network
        Block resNet50 =
                ResNetV1.builder() // construct the network
                        .setImageShape(new Shape(3, IMAGE_HEIGHT, IMAGE_WIDTH))
                        .setNumLayers(50)
                        .setOutSize(NUM_OF_OUTPUT)
                        .build();

        // set the neural network to the model
        model.setBlock(resNet50);
        return model;
    }

    public static void saveSynset(Path modelDir, List<String> synset) throws IOException {
        Path synsetFile = modelDir.resolve("synset.txt");
        try (Writer writer = Files.newBufferedWriter(synsetFile)) {
            writer.write(String.join("\n", synset));
        } catch (IOException e) {
            System.err.println("Failed to save synset to " + synsetFile);
            throw e; // rethrow to allow higher-level handlers to deal with it
        }
    }
    
}












// package ch.zhaw.deeplearningjava.footwear;

// import ai.djl.Model;
// import ai.djl.inference.Predictor;
// import ai.djl.modality.cv.Image;
// import ai.djl.translate.Translator;
// import ai.djl.translate.TranslatorContext;

// import java.nio.file.Path;
// import java.nio.file.Paths;
// import java.util.List;

// public class FootwearClassifier {
//     private Model model;

//     public FootwearClassifier() {
//         this.model = Models.getModel();
//     }

//     public void trainModel(List<Path> imagePaths) {
//         // Assuming you have a method to train your model
//         for (Path imagePath : imagePaths) {
//             Image img = ImagePreprocessing.preprocessImage(imagePath);
//             // Train your model with the preprocessed image
//             // model.fit(img, label); // Pseudocode
//         }
//     }

//     public String predict(Path imagePath) {
//         Image img = ImagePreprocessing.preprocessImage(imagePath);
//         try (Predictor<Image, String> predictor = model.newPredictor(getTranslator())) {
//             return predictor.predict(img);
//         }
//     }

//     private Translator<Image, String> getTranslator() {
//         return new Translator<>() {
//             @Override
//             public Image processInput(TranslatorContext ctx, Image input) {
//                 return input; // Already preprocessed
//             }

//             @Override
//             public String processOutput(TranslatorContext ctx, ai.djl.ndarray.NDList list) {
//                 // Process and return the prediction
//                 return list.singletonOrThrow().toString(); // Pseudocode
//             }
//         };
//     }

//     public static void main(String[] args) {
//         FootwearClassifier classifier = new FootwearClassifier();
//         Path testImagePath = Paths.get("path/to/test/image.jpg");
//         String prediction = classifier.predict(testImagePath);
//         System.out.println("Predicted class: " + prediction);
//     }
// }
