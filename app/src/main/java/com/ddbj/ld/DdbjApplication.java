package com.ddbj.ld;

import com.ddbj.ld.dao.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import lombok.AllArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.XML;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.io.BufferedWriter;

import com.ddbj.ld.common.FileNameEnum;
import com.ddbj.ld.common.FileTypeEnum;
import com.ddbj.ld.common.Settings;

//  TODO
//   - 冒頭のSystem.outは削除予定
//   - Exceptionが飛んだときのエラーハンドリング(スキップして飛ばす)
//   - ログ出力
//   - 中間テーブルの処理
/**
 * ddbjのメタデータをXMLからJSONにするバッチの中心となるクラス.
 */
@SpringBootApplication
@AllArgsConstructor
public class DdbjApplication implements CommandLineRunner {
    private final Settings settings;

    /** BioProject */
    private final BioProject bioProject;
    /** Submission */
    private final Submission submission;
    /** Analysis */
    private final Analysis analysis;
    /** Experiment */
    private final Experiment experiment;
    /** BioSample */
    private final BioSample bioSample;
    /** Run */
    private final Run run;
    /** Study */
    private final Study study;
    /** Sample */
    private final Sample sample;

    /**
     * mainメソッド.
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(DdbjApplication.class, args);
    }

    /**
     * コマンドラインから実行されるメソッド.
     *
     * @param args
     * @throws IOException
     */
    @Override
    public void run(String... args) throws IOException {
        System.out.println("Batch run!!!");

        String xmlPath = settings.getXmlPath();
        String jsonPath = settings.getJsonPath();

        String bioProjectXml = xmlPath + FileNameEnum.BIO_PROJECT_XML.getFileName();
        String bioProjectJson = jsonPath + FileNameEnum.BIO_PROJECT_JSON.getFileName();

        JSONObject bioProjectObj = xmlToJson(bioProjectXml, bioProjectJson);

        JSONObject packageSet = bioProjectObj.getJSONObject("PackageSet");
        JSONArray projects = packageSet.getJSONArray("Package");

        for(Object project: projects) {
            // TODO 一括でインサートする必要性と方法は要検討
            String bioProjectAccession = getAccession((JSONObject)project, FileTypeEnum.BIO_PROJECT);
            bioProject.insert(bioProjectAccession);
        }

        String bioSampleXml = xmlPath + FileNameEnum.BIO_SAMPLE_XML.getFileName();
        String bioSampleJson = jsonPath + FileNameEnum.BIO_SAMPLE_JSON.getFileName();

        JSONObject bioSampleObj = xmlToJson(bioSampleXml, bioSampleJson);

        JSONObject bioSampleSet = bioSampleObj.getJSONObject("BioSampleSet");
        JSONArray samples = bioSampleSet.getJSONArray("BioSample");

        for(Object sample: samples) {
            // TODO 一括でインサートする必要性と方法は要検討
            String bioSampleAccession = getAccession((JSONObject) sample, FileTypeEnum.BIO_SAMPLE);
            bioSample.insert(bioSampleAccession);
        }

        File targetDir = new File(xmlPath);
        File[] childrenDirList = targetDir.listFiles();

        for (File childrenDir : childrenDirList) {
            String childrenDirName = childrenDir.getName();

            if(childrenDirName.equals("bioproject")
            || childrenDirName.equals("biosample")) {
                // 必要ないので処理を飛ばす
                continue;
            }

            String xmlDir = xmlPath + childrenDirName + "/";
            String jsonDir = jsonPath + childrenDirName + "/";

            File dir = new File(jsonDir);
            dir.mkdir();

            String submissionXml = xmlDir + childrenDirName + FileNameEnum.SUBMISSION_XML.getFileName();
            String submissionJson = jsonDir + childrenDirName + FileNameEnum.SUBMISSION_JSON.getFileName();

            JSONObject submissionObj = xmlToJson(submissionXml, submissionJson);
            String submissionAccession = getAccession(submissionObj, FileTypeEnum.SUBMISSION);

            submission.insert(submissionAccession);

            String analysisXml = xmlDir + childrenDirName + FileNameEnum.ANALYSIS_XML.getFileName();
            String analysisJson = jsonDir + childrenDirName + FileNameEnum.ANALYSIS_JSON.getFileName();

            JSONObject analysisObj = xmlToJson(analysisXml, analysisJson);
            String analysisAccession = getAccession(analysisObj, FileTypeEnum.ANALYSIS);

            analysis.insert(analysisAccession);

            String experimentXml = xmlDir + childrenDirName + FileNameEnum.EXPERIMENT_XML.getFileName();
            String experimentJson = jsonDir + childrenDirName + FileNameEnum.EXPERIMENT_JSON.getFileName();

            JSONObject experimentObj = xmlToJson(experimentXml, experimentJson);
            String experimentAccession = getAccession(experimentObj, FileTypeEnum.EXPERIMENT);

            experiment.insert(experimentAccession);

            String runXml = xmlDir + childrenDirName + FileNameEnum.RUN_XML.getFileName();
            String runJson = jsonDir + childrenDirName + FileNameEnum.RUN_JSON.getFileName();

            JSONObject runObj = xmlToJson(runXml, runJson);
            String runAccession = getAccession(runObj, FileTypeEnum.RUN);

            run.insert(runAccession);

            String studyXml = xmlDir + childrenDirName + FileNameEnum.STUDY_XML.getFileName();
            File studyXmlFile = new File(studyXml);

            if(studyXmlFile.exists()) {
                String studyJson = jsonDir + childrenDirName + FileNameEnum.STUDY_JSON.getFileName();

                JSONObject studyObj = xmlToJson(studyXml, studyJson);
                String studyAccession = getAccession(studyObj, FileTypeEnum.STUDY);

                study.insert(studyAccession);
            }

            String sampleXml = xmlDir + childrenDirName + FileNameEnum.SAMPLE_XML.getFileName();
            File sampleXmlFile = new File(sampleXml);

            if(sampleXmlFile.exists()) {
                String sampleJson = jsonDir + childrenDirName + FileNameEnum.SAMPLE_JSON.getFileName();

                JSONObject sampleObj = xmlToJson(sampleXml, sampleJson);
                String sampleAccession = getAccession(sampleObj, FileTypeEnum.SAMPLE);

                sample.insert(sampleAccession);
            }
        }
    }

    /**
     * XMLをJSONに変換するメソッド.
     *
     * @param xmlFile
     * @param jsonFile
     * @return jsonObject
     * @throws IOException
     */
    private static JSONObject xmlToJson (String xmlFile, String jsonFile) throws IOException {
        FileWriter file = new FileWriter(jsonFile , false);

        try (InputStream inputStream = new FileInputStream(new File(xmlFile));
             PrintWriter pw = new PrintWriter(new BufferedWriter(file))
        ) {
            String xml = IOUtils.toString(inputStream);

            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);

            JSONObject jsonObject = XML.toJSONObject(xml);
            Object json = mapper.readValue(jsonObject.toString(), Object.class);
            String output = mapper.writeValueAsString(json);

            // jsonを書き込む
            pw.println(output);

            return jsonObject;
        }
    }

    /**
     * JSONからAccessionを取得するメソッド.
     *
     * @param jsonObject
     * @param fileType
     * @return accession
     */
    private static String getAccession(JSONObject jsonObject, FileTypeEnum fileType) {
        String accession = "";

        switch (fileType) {
            case BIO_PROJECT:
                JSONObject project = jsonObject.getJSONObject("Project");
                JSONObject nestedProject = project.getJSONObject("Project");
                JSONObject projectID = nestedProject.getJSONObject("ProjectID");
                JSONObject archiveID = projectID.getJSONObject("ArchiveID");
                accession = archiveID.getString("accession");
                break;
            case SUBMISSION:
                JSONObject submission = jsonObject.getJSONObject("SUBMISSION");
                accession = submission.getString("accession");
                break;
            case ANALYSIS:
                JSONObject analysisSet = jsonObject.getJSONObject("ANALYSIS_SET");
                JSONObject analysis = analysisSet.getJSONObject("ANALYSIS");
                accession = analysis.getString("accession");
                break;
            case EXPERIMENT:
                JSONObject experimentSet = jsonObject.getJSONObject("EXPERIMENT_SET");
                JSONObject experiment = experimentSet.getJSONObject("EXPERIMENT");
                accession = experiment.getString("accession");
                break;
            case BIO_SAMPLE:
                accession = jsonObject.getString("accession");
                break;
            case RUN:
                JSONObject runSet = jsonObject.getJSONObject("RUN_SET");
                JSONObject run = runSet.getJSONObject("RUN");
                accession = run.getString("accession");
                break;
            case STUDY:
                JSONObject studySet = jsonObject.getJSONObject("STUDY_SET");
                JSONObject study = studySet.getJSONObject("STUDY");
                accession = study.getString("accession");
                break;
            case SAMPLE:
                JSONObject sampleSet = jsonObject.getJSONObject("SAMPLE_SET");
                JSONObject sample = sampleSet.getJSONObject("SAMPLE");
                accession = sample.getString("accession");
                break;
            default:
                accession = null;
        }

        return accession;
    }
}
