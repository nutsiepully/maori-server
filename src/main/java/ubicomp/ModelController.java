package ubicomp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import weka.classifiers.bayes.NaiveBayesUpdateable;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.CSVLoader;

import java.io.File;
import java.net.URL;

@Controller
@RequestMapping(value = "/model")
public class ModelController {

    @RequestMapping(method = RequestMethod.GET, value = "/get")
    @ResponseBody
    public NaiveBayesUpdateable getClassifier() throws Exception {
        URL file = Thread.currentThread().getContextClassLoader().getResource("iris.data");

        CSVLoader loader = new CSVLoader();
        loader.setSource(new File(file.getPath()));
        Instances irisData = loader.getDataSet();
        irisData.setClassIndex(4);

        NaiveBayesUpdateable naiveBayes = new NaiveBayesUpdateable();
        naiveBayes.buildClassifier(irisData);
        for (Instance instance : irisData) {
            naiveBayes.updateClassifier(instance);
        }

        return naiveBayes;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/test")
    @ResponseBody
    public String get() {
        return "test";
    }

}
