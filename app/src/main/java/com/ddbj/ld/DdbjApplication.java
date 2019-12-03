package com.ddbj.ld;

import com.ddbj.ld.common.BulkHelper;
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
import java.util.ArrayList;
import java.util.List;

import com.ddbj.ld.common.FileNameEnum;
import com.ddbj.ld.common.FileTypeEnum;
import com.ddbj.ld.common.Settings;

//  TODO
//   - 冒頭のSystem.outは削除予定
//   - Exceptionが飛んだときのエラーハンドリング(スキップして飛ばす)
//   - ログ出力
//   - 中間テーブルの処理
/**
 * converting XML of DRA metadata to JSON batch class.
 */
@SpringBootApplication
@AllArgsConstructor
public class DdbjApplication implements CommandLineRunner {
    private final Settings settings;

    /** BioProject */
    private final BioProjectDao bioProjectDao;
    /** Submission */
    private final SubmissionDao submissionDao;
    /** Analysis */
    private final AnalysisDao analysisDao;
    /** Experiment */
    private final ExperimentDao experimentDao;
    /** BioSample */
    private final BioSampleDao bioSampleDao;
    /** Run */
    private final RunDao runDao;
    /** Study */
    private final StudyDao studyDao;
    /** Sample */
    private final SampleDao sampleDao;

    /**
     * main method.
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(DdbjApplication.class, args);
    }

    /**
     * exec from main.
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

        List<Object[]> bioProjectAccessionList = new ArrayList<>();

        for(Object project: projects) {
            Object bioProjectAccession[] = new Object[1];
            bioProjectAccession[0] = getAccession((JSONObject)project, FileTypeEnum.BIO_PROJECT);

            bioProjectAccessionList.add(bioProjectAccession);
        }

        BulkHelper.extract(bioProjectAccessionList, 100, _bioProjectAccessionList -> {
            bioProjectDao.bulkInsert(_bioProjectAccessionList);
        });

        String bioSampleXml = xmlPath + FileNameEnum.BIO_SAMPLE_XML.getFileName();
        String bioSampleJson = jsonPath + FileNameEnum.BIO_SAMPLE_JSON.getFileName();

        JSONObject bioSampleObj = xmlToJson(bioSampleXml, bioSampleJson);

        JSONObject bioSampleSet = bioSampleObj.getJSONObject("BioSampleSet");
        JSONArray samples = bioSampleSet.getJSONArray("BioSample");

        List<Object[]> bioSampleAccessionList = new ArrayList<>();

        for(Object sample: samples) {
            Object bioSampleAccession[] = new Object[1];
            bioSampleAccession[0] = getAccession((JSONObject) sample, FileTypeEnum.BIO_SAMPLE);

            bioSampleAccessionList.add(bioSampleAccession);
        }

        BulkHelper.extract(bioSampleAccessionList, 100, _bioSampleAccessionList -> {
            bioSampleDao.bulkInsert(_bioSampleAccessionList);
        });

        File targetDir = new File(xmlPath);
        File[] childrenDirList = targetDir.listFiles();

        List<Object[]> submissionAccessionList = new ArrayList<>();
        List<Object[]> analysisAccessionList = new ArrayList<>();
        List<Object[]> experimentAccessionList = new ArrayList<>();
        List<Object[]> runAccessionList = new ArrayList<>();
        List<Object[]> studyAccessionList = new ArrayList<>();
        List<Object[]> sampleAccessionList = new ArrayList<>();

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
            Object submissionAccessionArray[] = new Object[1];
            submissionAccessionArray[0] = submissionAccession;
            submissionAccessionList.add(submissionAccessionArray);

            // TODO このへんに中間テーブル用の配列を作り出す処理を入れる

            String analysisXml = xmlDir + childrenDirName + FileNameEnum.ANALYSIS_XML.getFileName();
            String analysisJson = jsonDir + childrenDirName + FileNameEnum.ANALYSIS_JSON.getFileName();

            JSONObject analysisObj = xmlToJson(analysisXml, analysisJson);
            String analysisAccession = getAccession(analysisObj, FileTypeEnum.ANALYSIS);
            Object analysisAccessionArray[] = new Object[1];
            analysisAccessionArray[0] = analysisAccession;
            analysisAccessionList.add(analysisAccessionArray);

            String experimentXml = xmlDir + childrenDirName + FileNameEnum.EXPERIMENT_XML.getFileName();
            String experimentJson = jsonDir + childrenDirName + FileNameEnum.EXPERIMENT_JSON.getFileName();

            JSONObject experimentObj = xmlToJson(experimentXml, experimentJson);
            String experimentAccession = getAccession(experimentObj, FileTypeEnum.EXPERIMENT);
            Object experimentAccessionArray[] = new Object[1];
            experimentAccessionArray[0] = experimentAccession;
            experimentAccessionList.add(experimentAccessionArray);

            String runXml = xmlDir + childrenDirName + FileNameEnum.RUN_XML.getFileName();
            String runJson = jsonDir + childrenDirName + FileNameEnum.RUN_JSON.getFileName();

            JSONObject runObj = xmlToJson(runXml, runJson);
            String runAccession = getAccession(runObj, FileTypeEnum.RUN);
            Object runAccessionArray[] = new Object[1];
            runAccessionArray[0] = runAccession;

            runAccessionList.add(runAccessionArray);

            String studyXml = xmlDir + childrenDirName + FileNameEnum.STUDY_XML.getFileName();
            File studyXmlFile = new File(studyXml);

            if(studyXmlFile.exists()) {
                String studyJson = jsonDir + childrenDirName + FileNameEnum.STUDY_JSON.getFileName();

                JSONObject studyObj = xmlToJson(studyXml, studyJson);
                String studyAccession = getAccession(studyObj, FileTypeEnum.STUDY);
                Object studyAccessionArray[] = new Object[1];
                studyAccessionArray[0] = studyAccession;
                studyAccessionList.add(studyAccessionArray);
            }

            String sampleXml = xmlDir + childrenDirName + FileNameEnum.SAMPLE_XML.getFileName();
            File sampleXmlFile = new File(sampleXml);

            if(sampleXmlFile.exists()) {
                String sampleJson = jsonDir + childrenDirName + FileNameEnum.SAMPLE_JSON.getFileName();

                JSONObject sampleObj = xmlToJson(sampleXml, sampleJson);
                String sampleAccession = getAccession(sampleObj, FileTypeEnum.SAMPLE);
                Object sampleAccessionArray[] = new Object[1];
                sampleAccessionArray[0] = sampleAccession;
                sampleAccessionList.add(sampleAccessionArray);
            }
        }

        BulkHelper.extract(submissionAccessionList, 100, _submissionAccessionList -> {
            submissionDao.bulkInsert(_submissionAccessionList);
        });

        BulkHelper.extract(analysisAccessionList, 100, _analysisAccessionList -> {
            analysisDao.bulkInsert(_analysisAccessionList);
        });

        BulkHelper.extract(experimentAccessionList, 100, _experimentAccessionList -> {
            experimentDao.bulkInsert(_experimentAccessionList);
        });

        BulkHelper.extract(runAccessionList, 100, _runAccessionList -> {
            runDao.bulkInsert(_runAccessionList);
        });

        BulkHelper.extract(studyAccessionList, 100, _studyAccessionList -> {
            studyDao.bulkInsert(_studyAccessionList);
        });

        BulkHelper.extract(sampleAccessionList, 100, _sampleAccessionList -> {
            sampleDao.bulkInsert(_sampleAccessionList);
        });
    }

    /**
     * convert xml to json.
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
     * get accession from json.
     *
     * @param jsonObject
     * @param fileType
     * @return accession
     */
    private static String getAccession(JSONObject jsonObject, FileTypeEnum fileType) {
        String accession = null;

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
                break;
        }

        return accession;
    }
}
