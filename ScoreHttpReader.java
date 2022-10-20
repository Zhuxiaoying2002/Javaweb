package cn.edu.swu.zxy.student;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class ScoreHttpReader extends ScoreAbstractReader {

    private URL url;

    public ScoreHttpReader(URL url) {
        this.setUrl(url);
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        URLConnection connection = this.getUrl().openConnection();
        InputStream inputStream = connection.getInputStream();
        return inputStream;
    }
}
