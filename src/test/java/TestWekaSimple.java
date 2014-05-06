import org.junit.Test;
import weka.classifiers.AbstractClassifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayesUpdateable;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;
import weka.core.converters.ConverterUtils.DataSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
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

    @Test
    public void testReduceArffDatasetSize() throws Exception {
        URL dataFile = Thread.currentThread().getContextClassLoader().getResource("accelerometer_data.arff");

        DataSource dataSource = new DataSource(dataFile.getPath());
        Instances accelerometerInstances = dataSource.getDataSet();
        if (accelerometerInstances.classIndex() == -1) accelerometerInstances.setClassIndex(accelerometerInstances.numAttributes() - 1);

        Instances limitedInstances = new Instances(accelerometerInstances, 0, 5);

        ArffSaver saver = new ArffSaver();
        saver.setInstances(limitedInstances);
        saver.setFile(new File("accelerometer_data_top_5.arff"));
//        saver.setDestination(new File("./data/test.arff"));
        saver.writeBatch();
    }

    @Test
    public void testDeSerializationNaiveBayes() throws Exception {
        URL file = Thread.currentThread().getContextClassLoader().getResource("naive-bayes-model.model");
        FileInputStream fis = new FileInputStream(file.getPath());
        ObjectInputStream ois = new ObjectInputStream(fis);
        AbstractClassifier model = (AbstractClassifier)ois.readObject();

        URL dataFile = Thread.currentThread().getContextClassLoader().getResource("accelerometer_data.arff");

        DataSource dataSource = new DataSource(dataFile.getPath());
        Instances accelerometerInstances = dataSource.getDataSet();
        if (accelerometerInstances.classIndex() == -1) accelerometerInstances.setClassIndex(accelerometerInstances.numAttributes() - 1);

        Evaluation evaluation = new Evaluation(accelerometerInstances);
        evaluation.evaluateModel(model, accelerometerInstances);
        String classDetailsString = evaluation.toSummaryString();

        System.out.println(model.getClass().toString());
        System.out.println(model.toString());
        System.out.println(classDetailsString);
    }

}
