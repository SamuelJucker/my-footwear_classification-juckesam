package ch.zhaw.deeplearningjava.footwear;

import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.djl.MalformedModelException;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.translate.TranslateException;


@RestController
public class ClassificationController {

    @GetMapping("/")
    public String ping() {
        return "Sentiment app is up and running!";
    }

    @GetMapping("/count")
    public int count() {
        return 42;
    }

    @GetMapping("/predict")
    public String predict(@RequestParam(name="text", required = true) String text) throws MalformedModelException, ModelNotFoundException, IOException, TranslateException {
        /* var result = SentimentAnalysis.predict(text);
        return result.getAsString();*/
        return "dummy";
    }
    
}