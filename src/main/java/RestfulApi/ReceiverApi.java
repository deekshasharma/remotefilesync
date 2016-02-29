package RestfulApi;

import rsync.ChecksumPair;
import rsync.GenerateChecksum;
import rsync.Util;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.io.FileNotFoundException;
import java.util.List;

@Path("receivermachine")
public class ReceiverApi {
    private static final String path = "/Users/deeksha/IdeaProjects/remotefilesynchronization/abc.txt";

    @POST
    public Response sendChecksums() throws FileNotFoundException {
        // Convert the file at the given path to stream of bytes
        byte[] byteStream = Util.convertToBytes(path);
        GenerateChecksum generateChecksum = new GenerateChecksum();
        List<ChecksumPair> checksumPairs = generateChecksum.getCheckSumPairs(byteStream);
        return Response.status(Response.Status.OK).entity(checksumPairs).build();
    }
}
