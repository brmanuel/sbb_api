import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by emfuthal on 25/10/16.
 */
public class pull {

    public static void main(String [] args) throws IOException {
        List<sbb.Stationboard> test = nextDeps("Schlieren", "S");
        for(sbb.Stationboard a : test){
            System.out.printf("from %10s to %10s Platform %d at %s \n", a.stop.station.name, a.to, a.stop.platform, a.stop.departure );
        }
    }

    public static sbb pullRequest(String depart) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        URL url = new URL("http://transport.opendata.ch/v1/stationboard?station=" + depart + "&limit=10");
        sbb sbb1 = mapper.readValue(url, sbb.class);
        return sbb1;
    }

    public static sbb pullRequest(String depart, int limit) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        URL url = new URL("http://transport.opendata.ch/v1/stationboard?station=" + depart + "&limit=" + limit);
        sbb sbb1 = mapper.readValue(url, sbb.class);
        return sbb1;
    }

    public static List<sbb.Stationboard> nextDeps(String depart, String means) throws IOException {
        int searchDepth = 100;
        List<sbb.Stationboard> result = new LinkedList<sbb.Stationboard>();
        sbb request = pullRequest(depart, searchDepth);
        for(sbb.Stationboard a : request.stationboard) {
            if (a.category.equals(means)) {
                result.add(a);
            }
        }
        return result;
    }
}
