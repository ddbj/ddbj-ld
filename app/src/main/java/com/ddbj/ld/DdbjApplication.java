package com.ddbj.ld;

import com.ddbj.ld.common.BulkHelper;
import com.ddbj.ld.dao.*;
import com.ddbj.ld.service.SRAAccessionsService;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
// TODO
import org.elasticsearch.common.xcontent.XContentType;
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
import com.ddbj.ld.common.TypeEnum;
import com.ddbj.ld.common.Settings;

//  TODO
//   - Exceptionが飛んだときのエラーハンドリング(スキップして飛ばす)
//   - ログ出力
//   - 中間テーブルの処理
//   - ElasticSearchのJsonを作る処理
//   - tabのデータを登録する処理
//   - クラスを切り分ける
/**
 * converting XML of DRA metadata to JSON batch class.
 */
@SpringBootApplication
@AllArgsConstructor
public class DdbjApplication implements CommandLineRunner {
//    private final Settings settings;
    private final SRAAccessionsService sraAccessionsService;

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
    public void run(String... args) {
        sraAccessionsService.registerSRAAccessions();
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
     * @param type
     * @return accession
     */
    private static String getAccession(JSONObject jsonObject, TypeEnum type) {
        String accession = null;

        switch (type) {
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
