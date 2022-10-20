package cn.edu.swu.zxy.student;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class ScoreReaderFactory {
    public static IScoreReader create(String uri) throws MalformedURLException {
        IScoreReader reader;
        if (uri.toLowerCase().startsWith("http")) {
            reader = new ScoreHttpReader(new URL(uri));
        } else {
            reader = new ScoreFileReader(new File(uri));
        }
        return reader;
    }
}