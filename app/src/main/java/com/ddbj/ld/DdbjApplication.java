package com.ddbj.ld;

import com.ddbj.ld.common.FileNameEnum;
import com.ddbj.ld.common.Settings;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import lombok.AllArgsConstructor;
// TODO 分離予定
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Iterator;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.*;

@SpringBootApplication
@AllArgsConstructor
public class DdbjApplication implements CommandLineRunner {
    // TODO 移動予定
    private final Settings settings;

    public static void main(String[] args) {
        SpringApplication.run(DdbjApplication.class, args);
    }

    @Override
    // TODO DBのリレーション、https://ddbj-dev.atlassian.net/wiki/spaces/OV/pages/1310753/DRA+2019
    public void run(String... args) throws FileNotFoundException, XMLStreamException {
        // TODO 削除予定
        System.out.println("Batch run!!!");

        // TODO 移動予定
        String targetPath = settings.getTargetPath();

        File targetDir = new File(targetPath);
        File[] childrenDirList = targetDir.listFiles();

        for (File childrenDir : childrenDirList) {
            // TODO 開始ログ

            String path = childrenDir.getAbsolutePath() + "/";
            String submissionAccession = childrenDir.getName();

            // TODO analysisは現行の検索画面に入っていなかった、要確認
            // TODO BioProjectとBioSample(不要？)も
            File submission = new File(path  + submissionAccession + FileNameEnum.SUBMISSION.getFileName());
            File run        = new File(path  + submissionAccession + FileNameEnum.RUN.getFileName());
            File analysis   = new File(path  + submissionAccession + FileNameEnum.ANALYSIS.getFileName());
            File study      = new File(path  + submissionAccession + FileNameEnum.STUDY.getFileName());
            File experiment = new File(path  + submissionAccession + FileNameEnum.EXPERIMENT.getFileName());

            parser(submission);
            // TODO
            //  - ディレクトリ配下のXMLを読み込む
            //  - DBに接続(新たな子供のトランザクションを開始する)
            //  - DBに書き出す

            // TODO 終了ログ
        }
    }

    // TODO
    public static void parser(File file) throws FileNotFoundException, XMLStreamException {
        XMLInputFactory factory    = XMLInputFactory.newInstance();
        XMLEventReader eventReader = factory.createXMLEventReader(new FileReader(file));

        while(eventReader.hasNext()) {
            // TODO
            //  - 参考：https://www.geeksforgeeks.org/stax-xml-parser-java/
            //  - Accessionは各単位で異なり、紐付けを行う中間テーブルのためにも必要
            //  - 現状はAccessionと他全部をDBに登録する
        }
    }
}
