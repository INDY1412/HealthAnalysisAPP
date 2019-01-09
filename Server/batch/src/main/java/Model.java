import org.dmg.pmml.FieldName;
import org.dmg.pmml.PMML;
import org.jpmml.evaluator.*;
import org.jpmml.model.PMMLUtil;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Model {
    private Evaluator modelEvaluator;

    /**
     * 通过传入 PMML 文件路径来生成机器学习模型
     *
     * @param pmmlFileName pmml 文件路径
     */
    public Model(String pmmlFileName) {
        PMML pmml = null;

        try {
            if (pmmlFileName != null) {
                InputStream is = new FileInputStream(pmmlFileName);
                pmml = PMMLUtil.unmarshal(is);
                try {
                    is.close();
                } catch (IOException e) {
                    System.out.println("InputStream close error!");
                }

                ModelEvaluatorFactory modelEvaluatorFactory = ModelEvaluatorFactory.newInstance();

                this.modelEvaluator = (Evaluator) modelEvaluatorFactory.newModelEvaluator(pmml);
                modelEvaluator.verify();
                System.out.println("加载模型成功！");
            }
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    // 获取模型需要的特征名称
    public List<String> getFeatureNames() {
        List<String> featureNames = new ArrayList<String>();

        List<InputField> inputFields = modelEvaluator.getInputFields();

        for (InputField inputField : inputFields) {
            featureNames.add(inputField.getName().toString());
        }
        return featureNames;
    }

    // 获取目标字段名称
    public String getTargetName() {
        return modelEvaluator.getTargetFields().get(0).getName().toString();
    }

    // 使用模型生成概率分布
    private ProbabilityDistribution getProbabilityDistribution(Map<FieldName, ?> arguments) {
        Map<FieldName, ?> evaluateResult = modelEvaluator.evaluate(arguments);

        FieldName fieldName = new FieldName(getTargetName());

        return (ProbabilityDistribution) evaluateResult.get(fieldName);

    }

    // 预测不同分类的概率
    public ValueMap<String, Number> predictProba(Map<FieldName, Number> arguments) {
        ProbabilityDistribution probabilityDistribution = getProbabilityDistribution(arguments);
        return probabilityDistribution.getValues();
    }

    // 预测结果分类
    public Object predict(Map<FieldName, ?> arguments) {
        ProbabilityDistribution probabilityDistribution = getProbabilityDistribution(arguments);

        return probabilityDistribution.getPrediction();
    }

    public static void main(String[] args) {
        Model clf = new Model("D:\\homework\\batch\\RandomForestClassifier_diabete.pmml");
        Model clf2 = new Model("D:\\homework\\batch\\RandomForestClassifier_hypertension.pmml");

        List<String> featureNames = clf.getFeatureNames();
        System.out.println("feature: " + featureNames);

        // 构建待预测数据
        Map<FieldName, Number> waitPreSample = new HashMap<FieldName, Number>();
        waitPreSample.put(new FieldName("age_at_study_date"), 20);
        waitPreSample.put(new FieldName("drink_tea"), 0);
        waitPreSample.put(new FieldName("drink_alcohol"), 0);
        waitPreSample.put(new FieldName("is_smokker"), 0);
        waitPreSample.put(new FieldName("met_hours"), 2);
        waitPreSample.put(new FieldName("standing_height_cm"), 180);
        waitPreSample.put(new FieldName("waist_kg"), 70);
        waitPreSample.put(new FieldName("is_female"), 1);

        System.out.println("diabete predict result: " + clf.predict(waitPreSample).toString());
        System.out.println("diabete predictProba result: " + clf.predictProba(waitPreSample).toString());

        System.out.println("hypertension predict result: " + clf2.predict(waitPreSample).toString());
        System.out.println("hypertension predictProba result: " + clf2.predictProba(waitPreSample).toString());

    }

}

