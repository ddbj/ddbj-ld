package com.ddbj.ld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import lombok.AllArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
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
//   - 登録順序はBioProject→BioSample→Submission→Analysis→Experiment→Runとする
//   - Runは表に存在しないため、XMLを見てみてから改めて聞く
//   - 必ずあるわけではないファイルがあるので、存在チェックをする
//   - Exceptionが飛んだときのエラーハンドリング(スキップして飛ばす？)
//   - ログ出力
//   - System.outは削除予定
//   - jsonオブジェクトからaccessionを取得する
//   - JSONObject.get("項目名")でできるが、項目名が登録単位によって違う可能性があるため要確認
//   - accessionの要素は子供の項目名としてJSON化される
//   - 子供の要素にアクセスするには更にgetすればできる
//   - それをtoStringしてあげればOK
//   - accessionがあるタグはXMLにより違うようなのでcaseで分岐
/**
 * ddbjのメタデータをXMLからJSONにするバッチの中心となるクラス.
 */
@SpringBootApplication
@AllArgsConstructor
public class DdbjApplication implements CommandLineRunner {
    private final Settings settings;

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

        String bioProjectXmlFile = xmlPath + FileNameEnum.BIO_PROJECT_XML.getFileName();
        String bioProjectJsonFile = jsonPath + FileNameEnum.BIO_PROJECT_JSON.getFileName();

        String bioProjectAccession = xmlToJson(bioProjectXmlFile, bioProjectJsonFile, FileTypeEnum.BIO_PROJECT);

        String bioSampleXmlFile = xmlPath + FileNameEnum.BIO_SAMPLE_XML.getFileName();
        String bioSampleJsonFile = jsonPath + FileNameEnum.BIO_SAMPLE_JSON.getFileName();

        String bioSampleAccession = xmlToJson(bioSampleXmlFile, bioSampleJsonFile, FileTypeEnum.BIO_SAMPLE);

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
            String submissionJson = xmlDir + childrenDirName + FileNameEnum.SUBMISSION_JSON.getFileName();

            String submissionAccession = xmlToJson(submissionXml, submissionJson, FileTypeEnum.SUBMISSION);

            String analysisXml = xmlDir + submissionAccession + FileNameEnum.ANALYSIS_XML.getFileName();
            String analysisJson = xmlDir + submissionAccession + FileNameEnum.ANALYSIS_JSON.getFileName();

            String analysisAccession = xmlToJson(analysisXml, analysisJson, FileTypeEnum.ANALYSIS);

            String experimentXml = xmlDir + submissionAccession + FileNameEnum.EXPERIMENT_XML.getFileName();
            String experimentJson = xmlDir + submissionAccession + FileNameEnum.EXPERIMENT_JSON.getFileName();

            String experimentAccession = xmlToJson(experimentXml, experimentJson, FileTypeEnum.EXPERIMENT);

            String runXml = xmlDir + submissionAccession + FileNameEnum.RUN_XML.getFileName();
            String runJson = xmlDir + submissionAccession + FileNameEnum.RUN_JSON.getFileName();

            String runAccession = xmlToJson(runXml, runJson, FileTypeEnum.RUN);
        }
    }

    /**
     * XMLをJSONに変換するメソッド.
     *
     * @param xmlFile
     * @param jsonFile
     * @param fileType
     * @return
     * @throws IOException
     */
    private static String xmlToJson (String xmlFile, String jsonFile, FileTypeEnum fileType) throws IOException {
        FileWriter file = new FileWriter(jsonFile , false);

        try (InputStream inputStream = new FileInputStream(new File(xmlFile));
             PrintWriter pw = new PrintWriter(new BufferedWriter(file))
        ) {
            String xml = IOUtils.toString(inputStream);
            JSONObject jsonObject = XML.toJSONObject(xml);
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            Object json = mapper.readValue(jsonObject.toString(), Object.class);
            String output = mapper.writeValueAsString(json);

            // jsonを書き込む
            pw.println(output);

            String accession = "";

            switch (fileType) {
                case BIO_PROJECT:
                    // TODO
                    break;
                case SUBMISSION:
                    // TODO
                    break;
                case ANALYSIS:
                    // TODO
                    break;
                case EXPERIMENT:
                    // TODO
                    break;
                case BIO_SAMPLE:
                    // TODO
                    break;
                case RUN:
                    // TODO
                    break;
                case STUDY:
                    // TODO
                    break;
                default:
                    // TODO
            }

            return accession;
        }
    }
}
