package com.neu.util;

import com.neu.autoparams.mvc.entity.AlgorithmParam;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConfUtil {

  public static void createConf(List<ConfItem> confItems, String filePath) {
    FileWriter fwQuote;
    try {
      fwQuote = new FileWriter(filePath);
      fwQuote.write("\n");
      for (ConfItem c : confItems) {
        fwQuote.write("automodel.params." + c.getAlgorithmLabel() + "=" + "\"" + c.toString().replaceAll("\"", "\\\\\"") + "\"");
        fwQuote.write("\n");
      }
      fwQuote.write("\n");
      for (ConfItem c : confItems) {
        String label = c.getAlgorithmLabel();
        fwQuote.write("automodel.algor." + label + "=" + "\"" +
                c.getAlgorithmName() + ":" + getLibrary(label) + ":" + getType(label) + "\"");
        fwQuote.write("\n");
      }
      fwQuote.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void createIni(List<ConfItem> confItems, String filePath) {
    IniTool iniTool = new IniTool();

    for (ConfItem c : confItems) {
      iniTool.setValue("params", "automodel.params." + c.getAlgorithmLabel(), c.toString());
    }
    for (ConfItem c : confItems) {
      String label = c.getAlgorithmLabel();
      iniTool.setValue("algor", "automodel.algor." + c.getAlgorithmLabel(), c.getAlgorithmName() + ":" + getLibrary(label) + ":" + getType(label));
    }
    try {
      iniTool.write(filePath);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static String getLibrary(String label) {
    if (label.toCharArray()[0] == '1') {
      return "XGBoost";
    } else if (label.toCharArray()[0] == '2') {
      return "sklearn";
    } else {
      return "Unknown";
    }
  }

  public static String getType(String label) {
    if (label.toCharArray()[1] == '1') {
      return "Classification";
    } else if (label.toCharArray()[1] == '2') {
      return "Cluster";
    } else if (label.toCharArray()[1] == '3') {
      return "Regression";
    } else {
      return "Unknown";
    }
  }

  public static void main(String[] args) throws IOException {
    List<ConfItem> algorList = new ArrayList<ConfItem>();

    algorList.add(getDecisionTreeClassifier());
    algorList.add(getRandomForestClassifier());
    algorList.add(getLogisticRegressionCV());
    algorList.add(getXgbClassifier());
    algorList.add(getMLPClassifier());
    algorList.add(getLogisticRegression());

    algorList.add(getLinearRegression());
    algorList.add(getRidge());
    algorList.add(getRidgeCV());
    algorList.add(getLasso());
    algorList.add(getLassoCV());
    algorList.add(getLassoLarsCV());
    algorList.add(getXgbRegressor());
    algorList.add(getRandomForestRegressor());
    algorList.add(getGradientBoostingRegressor());
    algorList.add(getSVR());
    algorList.add(getLinearSVR());
    algorList.add(getMLPRegressor());

    algorList.add(getKMeans());

//    JSONArray json = JSONArray.fromObject(algorList);
//    String str = json.toString();
//
//    FileWriter fwJson = new FileWriter("src/main/resources/algor_params.json");
//    fwJson.write(new JsonFormatTool().formatJson(str));
//    fwJson.close();
//
//    FileWriter fw = new FileWriter("src/main/resources/algor_params_str.txt");
//    fw.write(str);
//    fw.close();
//
//    String escapeStrQuote = str.replaceAll("\"", "\\\\\"");
//    String escapeStrQuoteComma = str.replaceAll("\"", "\\\\\"").replaceAll(",", "\\\\,");
//    FileWriter fwQuote = new FileWriter("src/main/resources/algor_params_str_quote.txt");
//    fwQuote.write(escapeStrQuote);
//    fwQuote.close();
//
//    FileWriter fwQuoteComma = new FileWriter("src/main/resources/algor_params_str_quote_comma.txt");
//    fwQuoteComma.write(escapeStrQuoteComma);
//    fwQuoteComma.close();

    String os = System.getProperty("os.name");
    String confPath;
    String iniPath;
    if(os.toLowerCase().startsWith("win")){
      confPath = "C:\\Users\\akbbx\\IdeaProjects\\AutoParams\\AutoParamsWeb\\src\\main\\resources\\algor_params.conf";
      iniPath = "C:\\Users\\akbbx\\IdeaProjects\\AutoParams\\AutoParamsWeb\\src\\main\\resources\\algor_params.ini";
    }else {

      confPath = "/Users/enbo/IdeaProjects/AutoParams/AutoParamsWeb/src/main/resources/algor_params.conf";
      iniPath = "/Users/enbo/IdeaProjects/AutoParams/AutoParamsWeb/src/main/resources/algor_params.ini";
    }

    ConfUtil.createConf(algorList, confPath);
    ConfUtil.createIni(algorList, iniPath);


  }

  /**
   * 21001 DecisionTreeClassifier
   */
  public static ConfItem getDecisionTreeClassifier(){
    ConfItem decisionTreeClassifier = new ConfItem("21001", "DecisionTreeClassifier");

    List<String> criterionValuesList = new ArrayList<>();
    criterionValuesList.add("gini");
    criterionValuesList.add("entropy");
    AlgorithmParam dtCriterion = new AlgorithmParam("criterion", "", criterionValuesList);

    List<String> splitterValuesList = new ArrayList<>();
    splitterValuesList.add("best");
    splitterValuesList.add("random");
    AlgorithmParam dtSplitter = new AlgorithmParam("splitter", "", splitterValuesList);

    AlgorithmParam dtMaxDepth = new AlgorithmParam("max_depth", "", 0, 10, Integer.MAX_VALUE, 100, 18);

    List<String> maxFeaturesValuesList = new ArrayList<>();
    maxFeaturesValuesList.add("auto");
    maxFeaturesValuesList.add("sqrt");
    AlgorithmParam dtMaxFeatures = new AlgorithmParam("max_features", "", maxFeaturesValuesList);
    dtMaxFeatures.setIsFloat(true);
    dtMaxFeatures.setFloatMin(0f);
    dtMaxFeatures.setFloatDownValue(0.5f);
    dtMaxFeatures.setFloatMax(1f);
    dtMaxFeatures.setFloatMaxInclusive(true);
    dtMaxFeatures.setFloatUpValue(1f);
    dtMaxFeatures.setFloatNum(20);

    decisionTreeClassifier.addParam(dtCriterion);
    decisionTreeClassifier.addParam(dtSplitter);
    decisionTreeClassifier.addParam(dtMaxDepth);
    decisionTreeClassifier.addParam(dtMaxFeatures);
    return decisionTreeClassifier;
  }

  /**
   * 21002 RandomForestClassifier
   */
  public static ConfItem getRandomForestClassifier(){
    ConfItem randomForestClassifier = new ConfItem("21002", "RandomForestClassifier");

    AlgorithmParam rsNEstimators = new AlgorithmParam("n_estimators", "", 0, 1, Integer.MAX_VALUE, 100, 100);

    List<String> valuesList = new ArrayList<>();
    valuesList.add("auto");
    valuesList.add("sqrt");
    AlgorithmParam rsMaxFeatures = new AlgorithmParam("max_features", "", valuesList);
    rsMaxFeatures.setIsFloat(true);
    rsMaxFeatures.setFloatMin(0f);
    rsMaxFeatures.setFloatDownValue(0.5f);
    rsMaxFeatures.setFloatMax(1f);
    rsMaxFeatures.setFloatMaxInclusive(true);
    rsMaxFeatures.setFloatUpValue(1f);
    rsMaxFeatures.setFloatNum(5);

    AlgorithmParam rsMaxDepth = new AlgorithmParam("max_depth", "", 0, 1, Integer.MAX_VALUE, 100, 100);
    AlgorithmParam rsMinSamplesSplit = new AlgorithmParam("min_samples_split", "", 0, 2, Integer.MAX_VALUE, 10, 9);

    randomForestClassifier.addParam(rsNEstimators);
    randomForestClassifier.addParam(rsMaxFeatures);
    randomForestClassifier.addParam(rsMaxDepth);
    randomForestClassifier.addParam(rsMinSamplesSplit);
    return randomForestClassifier;
  }


  /**
   * 21003 LogisticRegression
   */
  public static ConfItem getLogisticRegression(){
    ConfItem logisticRegression = new ConfItem("21003", "LogisticRegression");
        AlgorithmParam C = new AlgorithmParam("C", "", 0, 1f, Float.MAX_VALUE, 10f, 100);

    List<String> lrPenaltyValues = new ArrayList<>();
    lrPenaltyValues.add("l1");
    lrPenaltyValues.add("l2");
    AlgorithmParam lrPenalty = new AlgorithmParam("penalty", "", lrPenaltyValues);

    List<String> lrSolverValues = new ArrayList<>();
    lrSolverValues.add("newton-cg");
    lrSolverValues.add("lbfgs");
    lrSolverValues.add("liblinear");
    lrSolverValues.add("sag");
    lrSolverValues.add("saga");
    AlgorithmParam lrSolver = new AlgorithmParam("solver", "", lrSolverValues);

    AlgorithmParam lrMaxIter = new AlgorithmParam("max_iter", "", 0, 100, Integer.MAX_VALUE, 2000, 100);

    List<String> lrMultiClassValues = new ArrayList<>();
    lrMultiClassValues.add("multinomial");
    AlgorithmParam lrMultiClass = new AlgorithmParam("multi_class", "", lrMultiClassValues);

    logisticRegression.addParam(C);
    logisticRegression.addParam(lrPenalty);
    logisticRegression.addParam(lrSolver);
    logisticRegression.addParam(lrMaxIter);
    logisticRegression.addParam(lrMultiClass);
    return logisticRegression;
  }


  /**
   * 21004 LogisticRegressionCV
   */
  public static ConfItem getLogisticRegressionCV(){
    ConfItem logisticRegressionCV = new ConfItem("21004", "LogisticRegressionCV");

    AlgorithmParam lrCS = new AlgorithmParam("Cs", "", Integer.MIN_VALUE, 5, Integer.MAX_VALUE,10, 6);

    AlgorithmParam lrCV = new AlgorithmParam("cv", "", 0, 5, Integer.MAX_VALUE, 10, 6);

    List<String> lrPenaltyValues = new ArrayList<>();
    lrPenaltyValues.add("l1");
    lrPenaltyValues.add("l2");
    AlgorithmParam lrPenalty = new AlgorithmParam("penalty", "", lrPenaltyValues);

    List<String> lrSolverValues = new ArrayList<>();
    lrSolverValues.add("newton-cg");
    lrSolverValues.add("lbfgs");
    lrSolverValues.add("liblinear");
    lrSolverValues.add("sag");
    lrSolverValues.add("saga");
    AlgorithmParam lrSolver = new AlgorithmParam("solver", "", lrSolverValues);

    AlgorithmParam lrMaxIter = new AlgorithmParam("max_iter", "", 0, 100, Integer.MAX_VALUE, 200, 10);

    List<String> lrMultiClassValues = new ArrayList<>();
    lrMultiClassValues.add("multinomial");
    AlgorithmParam lrMultiClass = new AlgorithmParam("multi_class", "", lrMultiClassValues);

    logisticRegressionCV.addParam(lrCS);
    logisticRegressionCV.addParam(lrCV);
    logisticRegressionCV.addParam(lrPenalty);
    logisticRegressionCV.addParam(lrSolver);
    logisticRegressionCV.addParam(lrMaxIter);
    logisticRegressionCV.addParam(lrMultiClass);
    return logisticRegressionCV;
  }


  /**
   * 22001 KMeans
   */
  public static ConfItem getKMeans(){
    ConfItem kMeans = new ConfItem("22001", "KMeans");

    AlgorithmParam kMClusters = new AlgorithmParam("n_clusters", "", 0, 2, Integer.MAX_VALUE, 15, 14);

    AlgorithmParam kMNInit = new AlgorithmParam("n_init", "", 0, 1, Integer.MAX_VALUE, 10, 10);

    List<String> kMInitValues = new ArrayList<>();
    kMInitValues.add("k-means++");
    kMInitValues.add("random");
    AlgorithmParam KMInit = new AlgorithmParam("init", "", kMInitValues);

    AlgorithmParam kMMaxIter = new AlgorithmParam("max_iter", "", 0, 200, Integer.MAX_VALUE, 400, 20);

    List<String> KMAlgorithmValues = new ArrayList<>();
    KMAlgorithmValues.add("auto");
    KMAlgorithmValues.add("full");
    KMAlgorithmValues.add("elkan");
    AlgorithmParam KMAlgorithm = new AlgorithmParam("algorithm", "", KMAlgorithmValues);

    kMeans.addParam(kMClusters);
    kMeans.addParam(kMNInit);
    kMeans.addParam(KMInit);
    kMeans.addParam(kMMaxIter);
    kMeans.addParam(KMAlgorithm);
    return kMeans;
  }


  /**
   * 23001 LinearRegression
   */
  public static ConfItem getLinearRegression(){
    ConfItem linearRegression = new ConfItem("23001", "LinearRegression");
    AlgorithmParam lrFitIntercept = new AlgorithmParam("fit_intercept", "", true);
    AlgorithmParam lrNormalize = new AlgorithmParam("normalize", "", true);
    linearRegression.addParam(lrFitIntercept);
    linearRegression.addParam(lrNormalize);
    return linearRegression;
  }


  /**
   * 23002 Ridge
   */
  public static ConfItem getRidge(){
    ConfItem ridge = new ConfItem("23002", "Ridge");
    AlgorithmParam alpha = new AlgorithmParam("alpha", "", Float.MIN_VALUE, 1e-10f, Float.MAX_VALUE, 1e-2f,100);

    AlgorithmParam fit_intercept = new AlgorithmParam("fit_intercept", "", true);

    AlgorithmParam max_iter = new AlgorithmParam("max_iter", "", 0, 100, Integer.MAX_VALUE, 5000,10);

    AlgorithmParam normalize = new AlgorithmParam("normalize", "", true);

    List<String> solverValuesList = new ArrayList<>();
    solverValuesList.add("auto");
    solverValuesList.add("svd");
    solverValuesList.add("cholesky");
    solverValuesList.add("lsqr");
    solverValuesList.add("sparse_cg");
    solverValuesList.add("sag");
    AlgorithmParam solver = new AlgorithmParam("solver", "", solverValuesList);

    ridge.addParam(alpha);
    ridge.addParam(fit_intercept);
    ridge.addParam(max_iter);
    ridge.addParam(normalize);
    ridge.addParam(solver);
    return ridge;
  }


  /**
   * 23003 RidgeCV
   */
  public static ConfItem getRidgeCV(){
    ConfItem ridgeCV = new ConfItem("23003", "RidgeCV");

    AlgorithmParam fit_intercept = new AlgorithmParam("fit_intercept", "", true);

    AlgorithmParam normalize = new AlgorithmParam("normalize", "", true);

    List<String> scoringValuesList = new ArrayList<>();
    scoringValuesList.add("mean_squared_error");
    scoringValuesList.add("r2");
    scoringValuesList.add("median_absolute_error");
    AlgorithmParam scoring = new AlgorithmParam("scoring", "", scoringValuesList);

    AlgorithmParam cv = new AlgorithmParam("cv", "", 0, 5, Integer.MAX_VALUE, 10,6);
    cv.setIsNull(true);

    List<String> gcvModeValuesList = new ArrayList<>();
    gcvModeValuesList.add("auto");
    gcvModeValuesList.add("svd");
    gcvModeValuesList.add("eigen");
    AlgorithmParam gcv_mode = new AlgorithmParam("gcv_mode", "", gcvModeValuesList);

    AlgorithmParam store_cv_values = new AlgorithmParam("store_cv_values", "", true);
    ridgeCV.addParam(fit_intercept);
    ridgeCV.addParam(normalize);
    ridgeCV.addParam(scoring);
    ridgeCV.addParam(cv);
    ridgeCV.addParam(gcv_mode);
    ridgeCV.addParam(store_cv_values);
    return ridgeCV;
  }


  /**
   * 23004 Lasso
   */
  public static ConfItem getLasso(){
    ConfItem lasso = new ConfItem("23004", "Lasso");
    AlgorithmParam alpha = new AlgorithmParam("alpha", "", Float.MIN_VALUE, 0f, 1f, 1f,100);

    AlgorithmParam fit_intercept = new AlgorithmParam("fit_intercept", "", true);
    AlgorithmParam max_iter = new AlgorithmParam("max_iter", "", 0, 100, Integer.MAX_VALUE, 5000,10);
    AlgorithmParam normalize = new AlgorithmParam("normalize", "", true);
    AlgorithmParam warm_start = new AlgorithmParam("warm_start", "", true);
    AlgorithmParam positive = new AlgorithmParam("positive", "", true);
    List<String> selectionValuesList = new ArrayList<>();
    selectionValuesList.add("cyclic");
    selectionValuesList.add("random");
    AlgorithmParam selection = new AlgorithmParam("selection", "", selectionValuesList);

    lasso.addParam(alpha);
    lasso.addParam(fit_intercept);
    lasso.addParam(max_iter);
    lasso.addParam(normalize);
    lasso.addParam(warm_start);
    lasso.addParam(positive);
    lasso.addParam(selection);
    return lasso;
  }

  /**
   * 23005 LassoCV
   */
  public static ConfItem getLassoCV(){
    ConfItem lassoCV = new ConfItem("23005", "LassoCV");

    AlgorithmParam max_iter = new AlgorithmParam("max_iter", "", 0, 100, Integer.MAX_VALUE, 5000,10);

    AlgorithmParam fit_intercept = new AlgorithmParam("fit_intercept", "", true);

    AlgorithmParam normalize = new AlgorithmParam("normalize", "", true);

    AlgorithmParam cv = new AlgorithmParam("cv", "", 0, 5, Integer.MAX_VALUE, 10,6);
    cv.setIsNull(true);

    List<String> selectionValuesList = new ArrayList<>();
    selectionValuesList.add("cyclic");
    selectionValuesList.add("random");
    AlgorithmParam selection = new AlgorithmParam("selection", "", selectionValuesList);

    lassoCV.addParam(max_iter);
    lassoCV.addParam(fit_intercept);
    lassoCV.addParam(normalize);
    lassoCV.addParam(cv);
    lassoCV.addParam(selection);
    return lassoCV;
  }

  /**
   * 23006 LassoLarsCV
   */
  public static ConfItem getLassoLarsCV(){
    ConfItem lassoLarsCV = new ConfItem("23006", "LassoLarsCV");

    AlgorithmParam max_iter = new AlgorithmParam("max_iter", "", 0, 100, Integer.MAX_VALUE, 5000,10);

    AlgorithmParam fit_intercept = new AlgorithmParam("fit_intercept", "", true);

    AlgorithmParam normalize = new AlgorithmParam("normalize", "", true);

    AlgorithmParam cv = new AlgorithmParam("cv", "", 0, 5, Integer.MAX_VALUE, 10,6);
    cv.setIsNull(true);

    AlgorithmParam positive = new AlgorithmParam("positive", "", true);

    lassoLarsCV.addParam(max_iter);
    lassoLarsCV.addParam(fit_intercept);
    lassoLarsCV.addParam(normalize);
    lassoLarsCV.addParam(cv);
    lassoLarsCV.addParam(positive);
    return lassoLarsCV;
  }

  /**
   * 23007 SVR
   */
  public static ConfItem getSVR(){
    ConfItem svr = new ConfItem("23007", "SVR");

    AlgorithmParam gamma = new AlgorithmParam("gamma", "", 0f, 0.001f, Float.MAX_VALUE, 0.01f,100);

    AlgorithmParam C = new AlgorithmParam("C", "", 0f, 0.5f, Float.MAX_VALUE, 1f,10);

    AlgorithmParam coef0 = new AlgorithmParam("coef0", "", Float.MIN_VALUE, 0f, Float.MAX_VALUE, 5f,10);

    List<String> kernelValuesList = new ArrayList<>();
    // kernelValuesList.add("linear");
    // kernelValuesList.add("poly");
    kernelValuesList.add("rbf");
    kernelValuesList.add("sigmoid");
    // kernelValuesList.add("precomputed");
    AlgorithmParam kernel = new AlgorithmParam("kernel", "", kernelValuesList);

    svr.addParam(gamma);
    svr.addParam(C);
    svr.addParam(coef0);
    svr.addParam(kernel);
    return svr;
  }

  /**
   * 23008 LinearSVR
   */
  public static ConfItem getLinearSVR(){
    ConfItem linearSVR = new ConfItem("23008", "LinearSVR");

    AlgorithmParam C = new AlgorithmParam("C", "", 0f, 0.5f, Float.MAX_VALUE, 1f,100);

    linearSVR.addParam(C);
    return linearSVR;
  }

  /**
   * 23666 RandomForestRegressor
   */
  public static ConfItem getRandomForestRegressor(){
    ConfItem randomForestRegressor = new ConfItem("23666", "RandomForestRegressor");

    AlgorithmParam rsNEstimators = new AlgorithmParam("n_estimators", "", 0, 1, Integer.MAX_VALUE, 100, 100);

    List<String> valuesList = new ArrayList<>();
    valuesList.add("auto");
    valuesList.add("sqrt");
    valuesList.add("log2");
    AlgorithmParam rsMaxFeatures = new AlgorithmParam("max_features", "", valuesList);
    rsMaxFeatures.setIsFloat(true);
    rsMaxFeatures.setFloatMin(0);
    rsMaxFeatures.setFloatDownValue(0.5f);
    rsMaxFeatures.setFloatMax(1f);
    rsMaxFeatures.setFloatMaxInclusive(true);
    rsMaxFeatures.setFloatUpValue(1.0f);
    rsMaxFeatures.setFloatNum(5);

    AlgorithmParam rsMaxDepth = new AlgorithmParam("max_depth", "", 0, 1, Integer.MAX_VALUE, 100, 100);

    AlgorithmParam rsMinSamplesSplit = new AlgorithmParam("min_samples_split", "", 0, 2, Integer.MAX_VALUE, 10, 9);

    randomForestRegressor.addParam(rsNEstimators);
    randomForestRegressor.addParam(rsMaxFeatures);
    randomForestRegressor.addParam(rsMaxDepth);
    randomForestRegressor.addParam(rsMinSamplesSplit);
    return randomForestRegressor;
  }

  /**
   * 23667 GradientBoostingRegressor
   */
  public static ConfItem getGradientBoostingRegressor(){
    ConfItem gradientBoostingRegressor = new ConfItem("23667", "GradientBoostingRegressor");
    AlgorithmParam n_estimators = new AlgorithmParam("n_estimators", "", 0, 51, Integer.MAX_VALUE, 150, 100);
    AlgorithmParam learning_rate = new AlgorithmParam("learning_rate", "", 0f, 0.1f,Float.MAX_VALUE, 1f, 10);

    List<String> lossValuesList = new ArrayList<>();
    lossValuesList.add("ls");
    lossValuesList.add("lad");
    lossValuesList.add("huber");
    lossValuesList.add("quantile");
    AlgorithmParam loss = new AlgorithmParam("loss", "", lossValuesList);

    AlgorithmParam alpha = new AlgorithmParam("alpha", "", Float.MIN_VALUE, 0.6f, Float.MAX_VALUE, 0.9f, 4);

    AlgorithmParam max_depth = new AlgorithmParam("max_depth", "", 0, 21, Integer.MAX_VALUE, 50, 30);
    AlgorithmParam min_samples_split = new AlgorithmParam("min_samples_split", "", 0, 2,Integer.MAX_VALUE, 5, 4);
    gradientBoostingRegressor.addParam(n_estimators);
    gradientBoostingRegressor.addParam(learning_rate);
    gradientBoostingRegressor.addParam(loss);
    gradientBoostingRegressor.addParam(alpha);
    gradientBoostingRegressor.addParam(max_depth);
    gradientBoostingRegressor.addParam(min_samples_split);
    return gradientBoostingRegressor;
  }

  /**
   * 23999 MLPRegressor
   */
  public static ConfItem getMLPRegressor(){
    ConfItem mlpRegressor = new ConfItem("23999", "MLPRegressor");
    AlgorithmParam n_layers = new AlgorithmParam("n_layers", "", 0, 3,Integer.MAX_VALUE, 103, 100);
    AlgorithmParam hidden_layer_size = new AlgorithmParam("hidden_layer_size", "", 0, 2,Integer.MAX_VALUE, 11, 10);

    List<String> activationValuesList = new ArrayList<>();
    activationValuesList.add("identity");
    activationValuesList.add("logistic");
    activationValuesList.add("tanh");
    activationValuesList.add("relu");
    AlgorithmParam activation = new AlgorithmParam("activation", "", activationValuesList);

    List<String> solverValuesList = new ArrayList<>();
    solverValuesList.add("lbfgs");
    solverValuesList.add("sgd");
    solverValuesList.add("adam");
    AlgorithmParam solver = new AlgorithmParam("solver", "", solverValuesList);


    // AlgorithmParam alpha = new AlgorithmParam("alpha", "", true, 0.0001f, 0.1f, 100);
    // AlgorithmParam learning_rate = new AlgorithmParam("learning_rate", "", true, 0.0001f, 0.1f, 100);

    mlpRegressor.addParam(n_layers);
    mlpRegressor.addParam(hidden_layer_size);
    mlpRegressor.addParam(activation);

    // 某些 solver 解不出解，所以此参数暂时不可用
    // mlpRegressor.addParam(solver);

    // 参数太多 8g 内存放不下了
    // mlpRegressor.addParam(alpha);
    // mlpRegressor.addParam(learning_rate);
    return mlpRegressor;
  }

  /**
   * 21999 MLPClassifier
   */
  public static ConfItem getMLPClassifier(){
    ConfItem mlpClassifier = new ConfItem("21999", "MLPClassifier");
    AlgorithmParam n_layers = new AlgorithmParam("n_layers", "", 0, 3,Integer.MAX_VALUE, 103, 100);
    AlgorithmParam hidden_layer_size = new AlgorithmParam("hidden_layer_size", "", 0, 2,Integer.MAX_VALUE, 100, 99);

    List<String> activationValuesList = new ArrayList<>();
    activationValuesList.add("identity");
    activationValuesList.add("logistic");
    activationValuesList.add("tanh");
    activationValuesList.add("relu");
    AlgorithmParam activation = new AlgorithmParam("activation", "", activationValuesList);

    List<String> solverValuesList = new ArrayList<>();
    solverValuesList.add("lbfgs");
    solverValuesList.add("sgd");
    solverValuesList.add("adam");
    AlgorithmParam solver = new AlgorithmParam("solver", "", solverValuesList);


    AlgorithmParam alpha = new AlgorithmParam("alpha", "", Float.MIN_VALUE, 0.0001f, Float.MAX_VALUE, 0.1f, 100);
    AlgorithmParam learning_rate = new AlgorithmParam("learning_rate", "", 0, 0.0001f, Float.MAX_VALUE, 0.1f, 100);

    mlpClassifier.addParam(n_layers);
    mlpClassifier.addParam(hidden_layer_size);
    mlpClassifier.addParam(activation);
    mlpClassifier.addParam(solver);
    mlpClassifier.addParam(alpha);
    mlpClassifier.addParam(learning_rate);
    return mlpClassifier;
  }

  /**
   * 11001 XGBClassifier
   */
  public static ConfItem getXgbClassifier(){
    ConfItem xgbClassifier = new ConfItem("11001", "XGBClassifier");
    AlgorithmParam xgbNEstimators = new AlgorithmParam("n_estimators", "", 0, 50, Integer.MAX_VALUE, 200, 10);

    List<String> xgbBoosterValuesList = new ArrayList<>();
    xgbBoosterValuesList.add("gbtree");
    xgbBoosterValuesList.add("gblinear");
    xgbBoosterValuesList.add("dart");
    AlgorithmParam xgbBooster = new AlgorithmParam("booster", "", xgbBoosterValuesList);


    AlgorithmParam xgbEta = new AlgorithmParam("learning_rate", "", 0, 0.01f, Float.MAX_VALUE, 0.2f, 5);
    AlgorithmParam xgbGamma = new AlgorithmParam("min_split_loss", "", 0, 0, Integer.MAX_VALUE, 20, 10);
    AlgorithmParam xgbMaxDepth = new AlgorithmParam("max_depth", "", 0, 1, Integer.MAX_VALUE, 25, 5);
    AlgorithmParam xgbSubsample = new AlgorithmParam("subsample", "", 0, 0.5f, 1.0000001f, 1f, 10);
    // AlgorithmParam xgbColsample = new AlgorithmParam("colsample_bytree", "", true, 0.5f, 1f, 10);

    AlgorithmParam xgbLambda = new AlgorithmParam("reg_lambda", "", Integer.MIN_VALUE, 0, Integer.MIN_VALUE, 10, 5);
    // AlgorithmParam xgbAlpha = new AlgorithmParam("reg_alpha", "", true, 0, 10, 5);
    // AlgorithmParam xgbLambda_bias = new AlgorithmParam("reg_lambda_bias", "", true, 0, 10, 5);

    List<String> xgbSampleTypeValues = new ArrayList<>();
    xgbSampleTypeValues.add("uniform");
    xgbSampleTypeValues.add("weighted");
    AlgorithmParam xgbSampleType = new AlgorithmParam("sample_type", "", xgbSampleTypeValues);

    List<String> xgbNormalizeTypeValues = new ArrayList<>();
    xgbNormalizeTypeValues.add("tree");
    xgbNormalizeTypeValues.add("forest");
    AlgorithmParam xgbNormalizeType = new AlgorithmParam("normalize_type", "", xgbNormalizeTypeValues);
    // AlgorithmParam xgbRateDrop  = new AlgorithmParam("rate_drop", "", true,0f, 1f, 10);
    // AlgorithmParam xgbSkipDrop  = new AlgorithmParam("skip_drop", "", true,0f, 1f, 10);

    xgbClassifier.addParam(xgbNEstimators);
    xgbClassifier.addParam(xgbBooster);
    xgbClassifier.addParam(xgbEta);
    xgbClassifier.addParam(xgbGamma);
    xgbClassifier.addParam(xgbMaxDepth);
    xgbClassifier.addParam(xgbSubsample);
    // xgbClassifier.addParam(xgbColsample);
    xgbClassifier.addParam(xgbLambda);
    // xgbClassifier.addParam(xgbAlpha);
    // xgbClassifier.addParam(xgbLambda_bias);
    xgbClassifier.addParam(xgbSampleType);
    xgbClassifier.addParam(xgbNormalizeType);
    // xgbClassifier.addParam(xgbRateDrop);
    // xgbClassifier.addParam(xgbSkipDrop);
    return xgbClassifier;
  }


  /**
   * 13001 XGBRegressor
   */
  public static ConfItem getXgbRegressor(){
    ConfItem xgbRegressor = new ConfItem("13001", "XGBRegressor");
    AlgorithmParam xgbNEstimators = new AlgorithmParam("n_estimators", "", 0, 50, Integer.MAX_VALUE, 200, 10);

    List<String> xgbBoosterValuesList = new ArrayList<>();
    xgbBoosterValuesList.add("gbtree");
    xgbBoosterValuesList.add("gblinear");
    xgbBoosterValuesList.add("dart");
    AlgorithmParam xgbBooster = new AlgorithmParam("booster", "", xgbBoosterValuesList);


    AlgorithmParam xgbEta = new AlgorithmParam("learning_rate", "", 0, 0.01f,Float.MAX_VALUE, 0.2f, 5);
    AlgorithmParam xgbGamma = new AlgorithmParam("min_split_loss", "", 0, 0, Integer.MAX_VALUE, 20, 10);
    AlgorithmParam xgbMaxDepth = new AlgorithmParam("max_depth", "", 0, 1, Integer.MAX_VALUE, 25, 5);
    AlgorithmParam xgbSubsample = new AlgorithmParam("subsample", "", 0, 0.5f, Float.MAX_VALUE, 1f, 10);
    // AlgorithmParam xgbColsample = new AlgorithmParam("colsample_bytree", "", true, 0.5f, 1f, 10);

    AlgorithmParam xgbLambda = new AlgorithmParam("reg_lambda", "", Float.MIN_VALUE, 0, Float.MAX_VALUE, 10, 5);
    // AlgorithmParam xgbAlpha = new AlgorithmParam("reg_alpha", "", true, 0, 10, 5);
    // AlgorithmParam xgbLambda_bias = new AlgorithmParam("reg_lambda_bias", "", true, 0, 10, 5);

    List<String> xgbSampleTypeValues = new ArrayList<>();
    xgbSampleTypeValues.add("uniform");
    xgbSampleTypeValues.add("weighted");
    AlgorithmParam xgbSampleType = new AlgorithmParam("sample_type", "", xgbSampleTypeValues);
    List<String> xgbNormalizeTypeValues = new ArrayList<>();
    xgbNormalizeTypeValues.add("tree");
    xgbNormalizeTypeValues.add("forest");
    AlgorithmParam xgbNormalizeType = new AlgorithmParam("normalize_type", "", xgbNormalizeTypeValues);
    // AlgorithmParam xgbRateDrop  = new AlgorithmParam("rate_drop", "", true,0f, 1f, 10);
    // AlgorithmParam xgbSkipDrop  = new AlgorithmParam("skip_drop", "", true,0f, 1f, 10);

    xgbRegressor.addParam(xgbNEstimators);
    xgbRegressor.addParam(xgbBooster);
    xgbRegressor.addParam(xgbEta);
    xgbRegressor.addParam(xgbGamma);
    xgbRegressor.addParam(xgbMaxDepth);
    xgbRegressor.addParam(xgbSubsample);
    // xgbRegressor.addParam(xgbColsample);
    xgbRegressor.addParam(xgbLambda);
    // xgbRegressor.addParam(xgbAlpha);
    // xgbRegressor.addParam(xgbLambda_bias);
    xgbRegressor.addParam(xgbSampleType);
    xgbRegressor.addParam(xgbNormalizeType);
    // xgbRegressor.addParam(xgbRateDrop);
    // xgbRegressor.addParam(xgbSkipDrop);
    return xgbRegressor;
  }


}
