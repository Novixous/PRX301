import daos.StudentDAO;
import khanglna.dtos.StudentDTO;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/student")
public class StudentService {
    // The Java method will process HTTP GET requests
    @GET
    // The Java method will produce content identified by the MIME Media type "text/plain"
    @Produces("application/xml")
    public StudentDTO getStudent() {
        // Return some cliched textual content
        StudentDAO studentDAO = new StudentDAO();
        return studentDAO.findEntity();
    }
}
