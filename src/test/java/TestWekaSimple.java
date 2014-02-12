import org.junit.Test;
import weka.classifiers.bayes.NaiveBayesUpdateable;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.CSVLoader;

import java.io.*;
import java.net.URL;

public class TestWekaSimple {

    @Test
    public void testSimpleWekaRun() throws Exception {
        URL file = Thread.currentThread().getContextClassLoader().getResource("iris.data");

        CSVLoader loader = new CSVLoader();
        loader.setSource(new File(file.getPath()));
        Instances irisData = loader.getDataSet();
        irisData.setClassIndex(4);

        NaiveBayesUpdateable nb = new NaiveBayesUpdateable();
        nb.buildClassifier(irisData);
        for (Instance instance : irisData) {
            nb.updateClassifier(instance);
        }

        double correctCount = 0.0;
        for (Instance instance : irisData) {
            if(nb.classifyInstance(instance) == instance.classValue())
                correctCount++;
        }

        System.out.println("" + correctCount / irisData.size());
    }

    @Test
    public void testDeSerialization() throws IOException, ClassNotFoundException {
        URL file = Thread.currentThread().getContextClassLoader().getResource("serializedModel");
        FileInputStream fis = new FileInputStream(file.getPath());
        ObjectInputStream ois = new ObjectInputStream(fis);
        NaiveBayesUpdateable naiveBayesUpdateable = (NaiveBayesUpdateable)ois.readObject();
        System.out.println(naiveBayesUpdateable.toString());
    }

}
