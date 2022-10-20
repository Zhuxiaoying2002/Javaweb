package cn.edu.swu.zxy.student;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public abstract class ScoreAbstractReader implements IScoreReader {

    public abstract InputStream getInputStream() throws IOException;

    @Override
    public Map<String, Integer> read() throws IOException {
        Map<String, Integer> scores = new HashMap<>();
        try (InputStreamReader inputStreamReader = new InputStreamReader(this.getInputStream()))
        {
            try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
                String line = null;
                while ((line = reader.readLine()) != null) {
                    if (line.trim().isEmpty())  continue;

                    String[] data = line.split("\t");
                    if ( data.length >= 2) {
                        scores.put(data[0], Integer.valueOf(data[1]));
                    }
                }
            }
        }
        return scores;
    }

}
